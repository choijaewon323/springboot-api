package com.project.crud.login.custom;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.crud.exception.CustomException;
import com.project.crud.exception.ErrorResponseDto;
import com.project.crud.login.dto.LoginRequestDto;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.io.IOException;

public class CustomAuthenticationFilter extends UsernamePasswordAuthenticationFilter {
    private final ObjectMapper objectMapper;

    public CustomAuthenticationFilter(AuthenticationManager manager, ObjectMapper objectMapper) {
        super.setAuthenticationManager(manager);
        this.objectMapper = objectMapper;
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        try {
            LoginRequestDto requestDto = objectMapper.readValue(request.getInputStream(), LoginRequestDto.class);
            UsernamePasswordAuthenticationToken token = UsernamePasswordAuthenticationToken.unauthenticated(requestDto.username(), requestDto.password());
            setDetails(request, token);
            return this.getAuthenticationManager().authenticate(token);
        } catch (CustomException e) {
            exceptionHandler(response, e);
            return null;
        } catch (IOException e) {
            throw new IllegalStateException(e);
        }
    }

    private void exceptionHandler(HttpServletResponse response, CustomException e) {
        try {
            String message = objectMapper.writeValueAsString(ErrorResponseDto.of(e));
            response.setCharacterEncoding("utf-8");
            response.setStatus(401);
            response.getWriter().write(message);
        } catch (IOException ex) {
            throw new IllegalStateException(ex);
        }
    }
}
