package com.example.slowdlvy.security.oauth;

import com.example.slowdlvy.security.dto.PrincipalDetails;
import com.example.slowdlvy.security.dto.TokenInfo;
import com.example.slowdlvy.security.jwt.JwtManager;
import com.example.slowdlvy.service.RefreshTokenService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
@RequiredArgsConstructor
public class OAuth2AuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    private final JwtManager jwtManager;
    private final RefreshTokenService refreshTokenService;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        TokenInfo tokenInfo = jwtManager.generateToken(authentication);
        response.addHeader("ACCESSTOKEN", tokenInfo.getAccessToken());
        response.addHeader("REFRESHTOKEN", tokenInfo.getRefreshToken());

        PrincipalDetails principalDetails = (PrincipalDetails) authentication.getPrincipal();
        String refreshToken = tokenInfo.extractRefreshToken();

        refreshTokenService.saveOrUpdateRefreshToken(principalDetails.getUsername(), refreshToken);

        String targetUrl = "/auth/success";
        RequestDispatcher dispatcher = request.getRequestDispatcher(targetUrl);
        dispatcher.forward(request, response);
    }

}
