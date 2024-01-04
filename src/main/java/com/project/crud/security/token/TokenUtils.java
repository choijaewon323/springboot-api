package com.project.crud.security.token;

import com.project.crud.security.dto.UserTokenRequest;
import com.project.crud.security.dto.UserTokenResponse;
import com.project.crud.security.enums.AccountRole;
import com.project.crud.security.enums.KeyInfo;
import io.jsonwebtoken.*;
import lombok.extern.log4j.Log4j2;

import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;
import java.security.Key;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Log4j2
public final class TokenUtils {
    public static String generateJwtToken(UserTokenResponse userToken) {
        JwtBuilder builder = Jwts.builder()
                .setSubject(userToken.getUsername())
                .setHeader(createHeader())
                .setClaims(createClaims(userToken))
                .setExpiration(createExpireDateForOneYear())
                .signWith(SignatureAlgorithm.HS256, createSigningKey());

        return builder.compact();
    }

    public static boolean isValidToken(String token) {
        try {
            Claims claims = getClaimsFormToken(token);
            log.info("expireTime :" + claims.getExpiration());
            log.info("email :" + claims.get("email"));
            log.info("role :" + claims.get("role"));
            return true;

        } catch (ExpiredJwtException exception) {
            log.error("Token Expired");
            return false;
        } catch (JwtException exception) {
            log.error("Token Tampered");
            return false;
        } catch (NullPointerException exception) {
            log.error("Token is null");
            return false;
        }
    }

    public static String getTokenFromHeader(String header) {
        return header.split(" ")[1];
    }

    private static Date createExpireDateForOneYear() {
        Calendar c = Calendar.getInstance();
        c.add(Calendar.DATE, 30);
        return c.getTime();
    }

    private static Map<String, Object> createHeader() {
        Map<String, Object> header = new HashMap<>();

        header.put("typ", "JWT");
        header.put("alg", "HS256");
        header.put("regDate", System.currentTimeMillis());

        return header;
    }

    private static Map<String, Object> createClaims(UserTokenResponse userToken) {
        Map<String, Object> claims = new HashMap<>();

        claims.put("username", userToken.getUsername());
        claims.put("role", userToken.getRole());

        return claims;
    }

    private static Key createSigningKey() {
        byte[] apiKeySecretBytes = DatatypeConverter.parseBase64Binary(KeyInfo.SECRET_KEY.get());
        return new SecretKeySpec(apiKeySecretBytes, SignatureAlgorithm.HS256.getJcaName());
    }

    private static Claims getClaimsFormToken(String token) {
        return Jwts.parser().setSigningKey(DatatypeConverter.parseBase64Binary(KeyInfo.SECRET_KEY.get()))
                .parseClaimsJws(token).getBody();
    }

    private static String getUsernameFromToken(String token) {
        Claims claims = getClaimsFormToken(token);
        return (String) claims.get("username");
    }

    private static AccountRole getRoleFromToken(String token) {
        Claims claims = getClaimsFormToken(token);
        return (AccountRole) claims.get("role");
    }

}
