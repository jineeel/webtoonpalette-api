package com.dev.webtoonpalette.config.handler;

import com.dev.webtoonpalette.config.jwt.JWTUtil;
import com.dev.webtoonpalette.dto.MemberDTO;
import com.dev.webtoonpalette.dto.oauth.CustomOAuth2User;
import com.nimbusds.jose.shaded.gson.Gson;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;

@Log4j2
@RequiredArgsConstructor
@Component
public class CustomSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    private final JWTUtil jwtUtil;

    /**
     * 로그인 성공 Handler
     */
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {

        CustomOAuth2User customUserDetails = (CustomOAuth2User) authentication.getPrincipal();
        MemberDTO memberDTO = OauthUserToDTO(customUserDetails);

        Map<String, Object> claims = memberDTO.getClaims();

        // AccessToken과 RefreshToken을 생성하여 claims에 추가한다.
        String accessToken = jwtUtil.createJwt(claims, 10);
        String refreshToken = jwtUtil.createJwt(claims, 60*24);

        claims.put("accessToken", accessToken);
        claims.put("refreshToken", refreshToken);

        Gson gson = new Gson();

        Long id = (Long) claims.get("id");
        String jsonStr = gson.toJson(claims);

        response.setContentType("application/json; charset=UTF-8");

        PrintWriter printWriter = response.getWriter();
        printWriter.println(jsonStr);

        response.sendRedirect("http://localhost:3000/member/redirect?id="+id);
    }

    public MemberDTO OauthUserToDTO(CustomOAuth2User customOAuth2User){

        MemberDTO memberDTO = MemberDTO.builder()
                .id(customOAuth2User.getId())
                .username(customOAuth2User.getUsername())
                .nickname(customOAuth2User.getName())
                .providerId(customOAuth2User.getProviderId())
                .role(customOAuth2User.getRole())
                .social(customOAuth2User.getSocial())
                .uploadFileName(customOAuth2User.getUploadFileName())
                .build();

        return memberDTO;
    }

}
