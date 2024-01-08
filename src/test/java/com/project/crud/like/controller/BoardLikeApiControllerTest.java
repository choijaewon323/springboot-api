package com.project.crud.like.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.crud.like.dto.BoardLikeRequestDto;
import com.project.crud.like.service.BoardLikeService;
import com.project.crud.like.service.BoardLikeServiceImpl;
import com.project.crud.reply.controller.ReplyApiController;
import com.project.crud.reply.service.ReplyService;
import com.project.crud.security.config.WebMvcConfig;
import com.project.crud.security.config.WebSecurityConfig;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.filter.CharacterEncodingFilter;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doNothing;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = BoardLikeApiController.class,
        excludeFilters = {
                @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = WebSecurityConfig.class),
                @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = WebMvcConfig.class)
})
@ActiveProfiles("test")
@WithMockUser
public class BoardLikeApiControllerTest {
    @MockBean
    BoardLikeServiceImpl boardLikeService;

    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @BeforeEach
    void setup() {
        mockMvc = MockMvcBuilders.standaloneSetup(new BoardLikeApiController(boardLikeService))
                    .addFilter(new CharacterEncodingFilter("UTF-8", true))
                    .alwaysDo(print())
                    .build();
    }

    @DisplayName("좋아요 추가 테스트")
    @Test
    void upTest() throws Exception {
        // given
        doNothing().when(boardLikeService).up(any());
        String content = objectMapper.writeValueAsString(new BoardLikeRequestDto(0L, "유저"));

        // when
        mockMvc.perform(post("/api/v1/board/like")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(content)
                )
                // then
                .andExpect(status().isOk());
    }

    @DisplayName("좋아요 삭제 테스트")
    @Test
    void downTest() throws Exception {
        // given
        doNothing().when(boardLikeService).down(any());
        String content = objectMapper.writeValueAsString(new BoardLikeRequestDto(0L, "유저"));

        // when
        mockMvc.perform(delete("/api/v1/board/like")
                .with(csrf())
                .content(content)
                .contentType(MediaType.APPLICATION_JSON)
        )
                // then
                .andExpect(status().isOk());
    }
}
