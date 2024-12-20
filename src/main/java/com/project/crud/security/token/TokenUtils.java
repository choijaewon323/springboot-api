package com.project.crud.security.token;

import com.project.crud.security.dto.UserTokenResponse;
import com.project.crud.account.domain.AccountRole;
import com.project.crud.security.enums.KeyInfo;
import io.jsonwebtoken.*;
import lombok.extern.slf4j.Slf4j;

import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;
import java.security.Key;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Slf4j
public final class TokenUtils {
    private TokenUtils() {}

    public static String generateJwtToken(final UserTokenResponse userToken) {
        JwtBuilder builder = Jwts.builder()
                .setSubject(userToken.getUsername())
                .setHeader(createHeader())
                .setClaims(createClaims(userToken))
                .setExpiration(createExpireDateForOneMonth())
                .signWith(SignatureAlgorithm.HS256, createSigningKey());

        return builder.compact();
    }

    public static boolean isValidToken(final String token) {
        try {
            Claims claims = getClaimsFormToken(token);
            log.info("expireTime : {}", claims.getExpiration());
            log.info("username : {}", claims.get("username"));
            log.info("role : {}", claims.get("role"));
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

    public static String getTokenFromHeader(final String header) {
        return header.split(" ")[1];
    }

    private static Date createExpireDateForOneMonth() {
        Calendar c = Calendar.getInstance();
        c.add(Calendar.MINUTE, 30);
        return c.getTime();
    }

    private static Map<String, Object> createHeader() {
        Map<String, Object> header = new HashMap<>();

        header.put("typ", "JWT");
        header.put("alg", "HS256");
        header.put("regDate", System.currentTimeMillis());

        return header;
    }

    private static Map<String, Object> createClaims(final UserTokenResponse userToken) {
        Map<String, Object> claims = new HashMap<>();

        claims.put("username", userToken.getUsername());
        claims.put("role", userToken.getRole());

        return claims;
    }

    private static Key createSigningKey() {
        byte[] apiKeySecretBytes = DatatypeConverter.parseBase64Binary(KeyInfo.SECRET_KEY.get());
        return new SecretKeySpec(apiKeySecretBytes, SignatureAlgorithm.HS256.getJcaName());
    }

    private static Claims getClaimsFormToken(final String token) {
        return Jwts.parser().setSigningKey(DatatypeConverter.parseBase64Binary(KeyInfo.SECRET_KEY.get()))
                .parseClaimsJws(token).getBody();
    }

    public static String getUsernameFromToken(final String token) {
        Claims claims = getClaimsFormToken(token);
        return (String) claims.get("username");
    }

    public static AccountRole getRoleFromToken(final String token) {
        Claims claims = getClaimsFormToken(token);
        return AccountRole.valueOf((String) claims.get("role"));
    }

}
