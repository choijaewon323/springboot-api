package com.project.crud.security.token;

import com.project.crud.security.enums.AuthConstants;
import com.project.crud.security.token.TokenUtils;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.servlet.HandlerInterceptor;

import java.util.Objects;

public class JwtTokenInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        final String header = request.getHeader(AuthConstants.AUTH_HEADER.getType());
        Objects.requireNonNull(header);

        final String token = TokenUtils.getTokenFromHeader(header);

        if (TokenUtils.isValidToken(token)) {
            return true;
        }

        response.sendRedirect("/error/unauthorized");
        return false;
    }
}
