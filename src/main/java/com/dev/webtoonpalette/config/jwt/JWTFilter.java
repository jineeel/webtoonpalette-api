package com.dev.webtoonpalette.config.jwt;


import com.dev.webtoonpalette.constant.Genre;
import com.dev.webtoonpalette.constant.Role;
import com.dev.webtoonpalette.dto.MemberDTO;
import com.dev.webtoonpalette.dto.oauth.CustomOAuth2User;
import com.nimbusds.jose.shaded.gson.Gson;
import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.Map;

@Log4j2
@RequiredArgsConstructor
public class JWTFilter extends OncePerRequestFilter {

    private final JWTUtil jwtUtil;

    /**
     * JWT 경로별 필터링
     */
    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
        String path = request.getRequestURI();

        // /api/member 와 /api/webtoon 경로는 JWT 필터링 하지 않는다.
        if(path.startsWith("/api/member/") || path.startsWith("/api/webtoon/")){
            return true;
        }

        return false;
    }

    /**
     * JWT 문자열 검사
     */
    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {

        log.info("------------------------");
        log.info("------------------------");

        String authHeaderStr = request.getHeader("Authorization");
        try{
            String accessToken = authHeaderStr.substring(7);
            Map<String, Object> claims = JWTUtil.validateToken(accessToken);// 토큰 검사

            Integer tmpId = (Integer) claims.get("id");

            Long id = tmpId.longValue();
            String username = (String) claims.get("username");
            String nickname = (String) claims.get("nickname");
            Role role = (Role) claims.get("Role");
            String social = (String) claims.get("social");
            String providerId = (String) claims.get("providerId");
            String uploadFileName = (String)claims.get("uploadFileName");
            List<Genre> genreNames = (List<Genre>) claims.get("genreNames");

            MemberDTO memberDTO = new MemberDTO(id, username, nickname, role, social, providerId, uploadFileName, genreNames);
            //사용자의 권한 확인

            CustomOAuth2User customOAuth2User = new CustomOAuth2User(memberDTO);

            Authentication authToken = new UsernamePasswordAuthenticationToken(customOAuth2User, null, customOAuth2User.getAuthorities());
            SecurityContextHolder.getContext().setAuthentication(authToken);

            filterChain.doFilter(request, response);


        }catch(Exception e){
            log.error("JWT Check Error -------------------------");
            log.error(e.getMessage());

            Gson gson = new Gson();
            String msg = gson.toJson(Map.of("error", "ERROR_ACCESS_TOKEN"));

            response.setContentType("application/json");
            PrintWriter printWriter = response.getWriter();
            printWriter.println(msg);
            printWriter.close();
        }

    }

}