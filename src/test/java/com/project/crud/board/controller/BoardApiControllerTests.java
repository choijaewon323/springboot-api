package com.project.crud.board.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.crud.board.dto.BoardRequestDto;
import com.project.crud.board.dto.BoardResponseDto;
import com.project.crud.board.repository.BoardRepository;
import com.project.crud.board.service.BoardSearchService;
import com.project.crud.board.service.BoardService;
import com.project.crud.common.ExceptionApiController;
import com.project.crud.security.config.WebMvcConfig;
import com.project.crud.security.config.WebSecurityConfig;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.data.jpa.mapping.JpaMetamodelMappingContext;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.filter.CharacterEncodingFilter;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doNothing;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@MockBean(JpaMetamodelMappingContext.class)
@WebMvcTest(controllers = {BoardApiController.class, ExceptionApiController.class},
            excludeFilters = {
                    @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = WebSecurityConfig.class),
                    @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = WebMvcConfig.class)
            })
@ActiveProfiles("test")
@WithMockUser
public class BoardApiControllerTests {
    @MockBean
    BoardService boardService;

    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @BeforeEach
    void setup() {
        mockMvc = MockMvcBuilders.standaloneSetup(new BoardApiController(boardService), new ExceptionApiController())
                .addFilter(new CharacterEncodingFilter("UTF-8", true))
                .alwaysDo(print())
                .build();
    }

    @DisplayName("게시물 하나 게시하기 테스트")
    @Test
    void createTest() throws Exception {
        // given
        final BoardRequestDto dto = makeRequestDto(1L);
        final String content = objectMapper.writeValueAsString(dto);

        doNothing().when(boardService).create(dto);

        // when
        mockMvc.perform(post("/api/v1/board")
                .contentType(MediaType.APPLICATION_JSON)
                .content(content)
                        .with(csrf())
        )
                // then
                .andExpect(status().isOk());
    }

    @DisplayName("게시물 하나 받아오기 테스트")
    @Test
    void readOneTest() throws Exception {
        // given
        final BoardResponseDto dto = makeResponseDto(1L);
        final String expected = objectMapper.writeValueAsString(dto);

        given(boardService.readOne(anyLong())).willReturn(dto);

        // when
        mockMvc.perform(get("/api/v1/board/{boardId}", dto.getId())
                .accept(MediaType.APPLICATION_JSON)
                        .with(csrf())
        )
                // then
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json;charset=utf-8"))
                .andExpect(content().string(expected));
    }

    @DisplayName("게시물 전체 받아오기 테스트")
    @Test
    void readAllTest() throws Exception {
        // given
        final long SIZE = 2L;
        final String expect = expectResponseDtoList(SIZE);

        given(boardService.readAll()).willReturn(makeResponseDtoList(SIZE));

        // when
        mockMvc.perform(
                get("/api/v1/board/list")
                        .accept(MediaType.APPLICATION_JSON)
                        .with(csrf())
        )
                // then
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json;charset=utf-8"))
                .andExpect(content().string(expect));
    }

    @DisplayName("게시물 수정 테스트")
    @Test
    void updateTest() throws Exception {
        // given
        final BoardRequestDto dto = makeRequestDto(2L);
        final String content = objectMapper.writeValueAsString(dto);

        doNothing().when(boardService).update(0L, dto);

        // when
        mockMvc.perform(put("/api/v1/board/{boardId}", 0L)
                .contentType(MediaType.APPLICATION_JSON)
                .content(content)
                        .with(csrf())
        )
                // then
                .andExpect(status().isOk());
    }

    @DisplayName("게시물 삭제 테스트")
    @Test
    void deleteTest() throws Exception {
        // given
        doNothing().when(boardService).delete(0L);

        // when
        mockMvc.perform(delete("/api/v1/board/{boardId}", 0L)
                        .with(csrf())
        )
                // then
                .andExpect(status().isOk());
    }

    @DisplayName("게시물 페이징 음수 예외 테스트")
    @Test
    void readAllByPagingException() throws Exception {
        // when
        mockMvc.perform(get("/api/v1/board/list/{pageIndex}", -1)
                .param("order", "desc")
                .with(csrf())
        )
                // then
                .andExpect(status().isBadRequest());
    }

    @DisplayName("게시물 페이징 order null 테스트")
    @Test
    void readAllByPagingOrderNull() throws Exception {
        // given
        final long SIZE = 2L;
        final String expect = expectResponseDtoList(SIZE);

        given(boardService.readAllByPagingDesc(anyInt(), anyInt())).willReturn(makeResponseDtoList(SIZE));

        // when
        mockMvc.perform(get("/api/v1/board/list/{pagingIndex}", 1)
                .with(csrf())
        )
                // then
                .andExpect(status().isOk())
                .andExpect(content().string(expect));
    }

    private List<BoardResponseDto> makeResponseDtoList(final long count) {
        List<BoardResponseDto> results = new ArrayList<>();

        for (long i = 1; i <= count; i++) {
            results.add(makeDto(i));
        }

        return results;
    }

    private String expectResponseDtoList(final long count) throws Exception {
        return objectMapper.writeValueAsString(makeResponseDtoList(count));
    }

    private BoardResponseDto makeDto(final long number) {
        return BoardResponseDto.builder()
                .id(number)
                .title("제목" + number)
                .content("내용" + number)
                .writer("작성자" + number)
                .likeCount(0L)
                .cnt(0L)
                .build();
    }

    private BoardRequestDto makeRequestDto(final long number) {
        return BoardRequestDto.builder()
                .title("제목" + number)
                .content("내용" + number)
                .writer("작성자" + number)
                .build();
    }

    private BoardResponseDto makeResponseDto(final long number) {
        return BoardResponseDto.builder()
                .id(number)
                .title("제목" + number)
                .content("내용" + number)
                .writer("작성자" + number)
                .likeCount(0L)
                .cnt(0L)
                .build();
    }
}
