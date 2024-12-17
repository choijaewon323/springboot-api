package com.project.crud.security.custom;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.crud.security.dto.UserTokenRequest;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.io.IOException;

public class CustomAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    public CustomAuthenticationFilter(final AuthenticationManager authenticationManager) {
        super.setAuthenticationManager(authenticationManager);
    }

    @Override
    public Authentication attemptAuthentication(final HttpServletRequest request, final HttpServletResponse response)
            throws AuthenticationException {

        UsernamePasswordAuthenticationToken authenticationToken = null;

        try {
            final UserTokenRequest user = new ObjectMapper().readValue(request.getInputStream(), UserTokenRequest.class);
            authenticationToken = new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword());

        } catch (IOException e) {
            e.printStackTrace();
        }

        setDetails(request, authenticationToken);

        return this.getAuthenticationManager().authenticate(authenticationToken);
    }
}