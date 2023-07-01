package kea.alog.user.util;

import java.util.Date;

import io.jsonwebtoken.*;

public class JwtUtil {

    public static String createJwt(Long userPk, String userName, String secretKey, Long expireMs) {
        Claims claims = Jwts.claims();
        claims.put("userName", userName);
        // claims.put("userPk", userPk);

        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + expireMs))
                .signWith(SignatureAlgorithm.HS256, secretKey)
                .compact();
    }

    public static boolean isExpired(String token, String secretKey) {
        return Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody().getExpiration()
                .before(new Date());

    }

    public static String getUserId(String token, String secretKey) {
        return Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody().get("userName").toString();
    }

    public static String getUserPk(String token, String secretKey) {
        return Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody().get("userPk").toString();
    }
}