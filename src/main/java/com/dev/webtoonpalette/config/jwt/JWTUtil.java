package com.dev.webtoonpalette.config.jwt;


import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.time.ZonedDateTime;
import java.util.Date;
import java.util.Map;

@Component
public class JWTUtil {

    private static SecretKey secretKey;

    public JWTUtil(@Value("${jwt.secret}")String secret) {
        secretKey = new SecretKeySpec(secret.getBytes(StandardCharsets.UTF_8), Jwts.SIG.HS256.key().build().getAlgorithm());
    }

    /**
     * JWT 생성
     */
    public String createJwt(Map<String,Object> valueMap, int min) {

        String jwtStr = Jwts.builder()
                .setHeader(Map.of("typ","JWT"))
                .setClaims(valueMap)
                .setIssuedAt(Date.from(ZonedDateTime.now().toInstant()))
                .setExpiration(Date.from(ZonedDateTime.now().plusMinutes(min).toInstant()))
                .signWith(secretKey)
                .compact();
        return jwtStr;
    }

    /**
     * 토큰 유효시간 검증
     */
    public static Map<String, Object> validateToken(String token) {
        Map<String, Object> claim = null;
        try{
            claim = Jwts.parser()
                    .setSigningKey(secretKey)
                    .build()
                    .parseClaimsJws(token) // 파싱 및 검증, 실패 시 에러
                    .getBody();

        }catch(MalformedJwtException malformedJwtException){
            throw new CustomJWTException("MalFormed");
        }catch(ExpiredJwtException expiredJwtException){
            throw new CustomJWTException("Expired");
        }catch(InvalidClaimException invalidClaimException){
            throw new CustomJWTException("Invalid");
        }catch(JwtException jwtException){
            throw new CustomJWTException("JWTError");
        }catch(Exception e){
            throw new CustomJWTException("Error");
        }
        return claim;
    }
}