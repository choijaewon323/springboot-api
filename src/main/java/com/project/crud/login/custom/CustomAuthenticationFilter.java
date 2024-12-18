package com.project.crud.login.custom;

import com.fasterxml.jackson.databind.ObjectMapper;
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
    private final ObjectMapper objectMapper = new ObjectMapper();

    public CustomAuthenticationFilter(AuthenticationManager manager) {
        super.setAuthenticationManager(manager);
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        try {
            LoginRequestDto requestDto = objectMapper.readValue(request.getInputStream(), LoginRequestDto.class);
            UsernamePasswordAuthenticationToken token = UsernamePasswordAuthenticationToken.unauthenticated(requestDto.username(), requestDto.password());
            setDetails(request, token);
            return this.getAuthenticationManager().authenticate(token);
        } catch (Exception e) {
            exceptionHandler(response, e);

            return null;
        }
    }

    private void exceptionHandler(HttpServletResponse response, Exception e) {
        try {
            response.setCharacterEncoding("utf-8");
            response.setStatus(401);
            response.getWriter().write("실패 : " + e.getMessage());
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
    }
}
