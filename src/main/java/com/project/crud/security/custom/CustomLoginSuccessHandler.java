package com.project.crud.security.custom;

import com.project.crud.security.dto.UserTokenResponse;
import com.project.crud.security.enums.AuthConstants;
import com.project.crud.security.token.TokenUtils;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;

import java.io.IOException;

public class CustomLoginSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler {
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws ServletException, IOException {
        final UserTokenResponse userToken = ((CustomUserDetails) authentication.getPrincipal()).getUserToken();
        final String token = TokenUtils.generateJwtToken(userToken);
        response.addHeader(AuthConstants.AUTH_HEADER.getType(), AuthConstants.TOKEN_TYPE.getType() + " " + token);
    }
}
