package com.project.crud.reply.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.crud.reply.dto.ReplyRequestDto;
import com.project.crud.reply.dto.ReplyResponseDto;
import com.project.crud.reply.service.ReplyService;
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

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doNothing;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@MockBean(JpaMetamodelMappingContext.class)
@WebMvcTest(controllers = ReplyApiController.class,
        excludeFilters = {
                @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = WebSecurityConfig.class),
                @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = WebMvcConfig.class)
        })
@ActiveProfiles("test")
@WithMockUser
public class ReplyApiControllerTests {
    @MockBean
    ReplyService replyService;

    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @BeforeEach
    void setup() {
        mockMvc = MockMvcBuilders.standaloneSetup(new ReplyApiController(replyService))
                .addFilter(new CharacterEncodingFilter("UTF-8", true))
                .alwaysDo(print())
                .build();
    }

    @DisplayName("댓글 생성 테스트")
    @Test
    void createTest() throws Exception {
        // given
        doNothing().when(replyService).create(0L, new ReplyRequestDto("내용", "댓글"));
        ReplyRequestDto replyRequestDto = new ReplyRequestDto("내용1", "작성자1");
        String content = objectMapper.writeValueAsString(replyRequestDto);

        // when
        mockMvc.perform(
                post("/api/v1/reply/{boardId}", 0L)
                .contentType(MediaType.APPLICATION_JSON)
                .content(content)
                        .with(csrf())
        )
                // then
                .andExpect(status().isOk());
    }

    @DisplayName("댓글 하나 읽어오기 테스트")
    @Test
    void readOneTest() throws Exception {
        // given
        ReplyResponseDto dto = new ReplyResponseDto(0L, "내용", "작성자");
        given(replyService.readOne(anyLong())).willReturn(dto);
        String expect = objectMapper.writeValueAsString(dto);

        // when
        mockMvc.perform(
                get("/api/v1/reply/{replyId}", 0L)
                        .with(csrf())
                )
                // then
                .andExpect(status().isOk())
                .andExpect(content().string(expect));
    }

    @DisplayName("댓글 여러개 읽어오기 테스트")
    @Test
    void readAllTest() throws Exception {
        // given
        given(replyService.readAll(0L)).willReturn(dtoList());
        String expect = objectMapper.writeValueAsString(dtoList());

        // when
        mockMvc.perform(
                get("/api/v1/reply/list/{boardId}", 0L)
                        .with(csrf())
                )
                // then
                .andExpect(status().isOk())
                .andExpect(content().string(expect));
    }

    @DisplayName("댓글 수정 테스트")
    @Test
    void updateTest() throws Exception {
        // given
        ReplyRequestDto dto = new ReplyRequestDto("내용2", "작성자2");
        doNothing().when(replyService).update(0L, dto);
        String content = objectMapper.writeValueAsString(dto);

        // when
        mockMvc.perform(
                put("/api/v1/reply/{replyId}", 0L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(content)
                        .with(csrf())
        )
                // then
                .andExpect(status().isOk());
    }

    @DisplayName("댓글 삭제 테스트")
    @Test
    void deleteTest() throws Exception {
        // given
        doNothing().when(replyService).delete(0L);

        // when
        mockMvc.perform(
                delete("/api/v1/reply/{replyId}", 0L)
                        .with(csrf())
        )
                // then
                .andExpect(status().isOk());
    }

    private List<ReplyResponseDto> dtoList() {
        List<ReplyResponseDto> results = new ArrayList<>();
        results.add(new ReplyResponseDto(0L, "내용1", "작성자1"));
        results.add(new ReplyResponseDto(1L, "내용2", "작성자2"));

        return results;
    }
}
