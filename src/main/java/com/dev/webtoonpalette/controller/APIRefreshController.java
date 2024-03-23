package com.dev.webtoonpalette.controller;

import com.dev.webtoonpalette.config.jwt.CustomJWTException;
import com.dev.webtoonpalette.config.jwt.JWTUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.Map;

@RestController
@Log4j2
@RequiredArgsConstructor
public class APIRefreshController {

    private final JWTUtil jwtUtil;

    /**
     * 토큰 재발급
     */
    @RequestMapping("/api/member/refresh")
    public Map<String, Object> refresh (@RequestHeader("Authorization") String authHeader,
                                        String refreshToken){

        if(refreshToken == null) {
            throw new CustomJWTException("NULL_REFRESH");
        }
        if(authHeader == null || authHeader.length() <7){
            throw new CustomJWTException("INVALID STRING");
        }

        String accessToken = authHeader.substring(7);

        if(!checkExpiredToken(accessToken)){

            return Map.of("accessToken",accessToken,"refreshToken",refreshToken);
        }

        Map<String, Object> claims = jwtUtil.validateToken(refreshToken);

        //AccessToken 만료
        //RefreshToken 검증
        Long tmpExp = (Long)claims.get("exp");
        Integer exp = tmpExp.intValue();

        String newAccess = jwtUtil.createJwt(claims,10);
        String newRefresh = checkTime(exp)? jwtUtil.createJwt(claims, 60*24) : refreshToken;

        return Map.of("accessToken", newAccess, "refreshToken", newRefresh);

    }

    // refreshToken 유효시간 계산
    public boolean checkTime(Integer exp){

        Date expDate = new Date((long) exp*(1000));
        long gap = expDate.getTime()-System.currentTimeMillis();
        long leftMin = gap / (1000 * 60);

        return leftMin < 60;

    }

    public boolean checkExpiredToken(String token){

        try {
            jwtUtil.validateToken(token);
        }catch (CustomJWTException e){
            if(e.getMessage().equals("Expired")){
                return true;
            }

        }
        return false;
    }
}
