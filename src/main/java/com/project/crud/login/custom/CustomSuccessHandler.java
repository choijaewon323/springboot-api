package com.project.crud.login.custom;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.crud.login.dto.LoginResponseDto;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import java.io.IOException;

@RequiredArgsConstructor
public class CustomSuccessHandler implements AuthenticationSuccessHandler {

    private final ObjectMapper objectMapper;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        System.out.println(auth.getAuthorities());

        System.out.println(auth.getName());

        response.setContentType("application/json; charset=utf-8");

        LoginResponseDto responseDto = new LoginResponseDto(true, "로그인 성공");

        String content = objectMapper.writeValueAsString(responseDto);

        response.getWriter().write(content);
    }
}
