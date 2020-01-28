package com.healthy.style.jwt;

import java.time.Instant;
import java.util.Date;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.healthy.style.security.CustomUserDetails;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.UnsupportedJwtException;

@Component
public class JwtTokenProvider {

    @Value("${application.jwt.header}")
    private static String tokenRequestHeader;

    @Value("${application.jwt.header.prefix}")
    private static String tokenRequestHeaderPrefix;

    @Value("${application.jwt.secret}")
    private static String secretKey;

    @Value("${application.jwt.expiration}")
    private static Long expiration;

    public String generateToken(CustomUserDetails customUserDetails) {
        return Jwts.builder()
                .setSubject(Long.toString(customUserDetails.getId()))
                .setIssuedAt(Date.from(Instant.now()))
                .setExpiration(Date.from(Instant.now().plusMillis(expiration)))
                .signWith(SignatureAlgorithm.HS512, secretKey)
                .compact();
    }

    public Long getUserIdFromJWT(String token) {
        Claims claims = Jwts.parser()
                .setSigningKey(secretKey)
                .parseClaimsJwt(token)
                .getBody();

        return Long.parseLong(claims.getSubject());
    }

    static void addAuthentication(HttpServletResponse response, String username) {
        String JwtToken = Jwts.builder()
                .setSubject(username)
                
                
                .setExpiration(new Date(System.currentTimeMillis() + 86400))
                .signWith(SignatureAlgorithm.HS512, "signingKey")
                .compact();
        response.addHeader("Authorization", "Bearer" + " " + JwtToken);
        response.addHeader("Access-Control-Expose-Headers", "Authorization");
    }

    public boolean validateToken(String authToken) {
        boolean isTokenVerified = false;
        try {
            Jwts.parser().setSigningKey(secretKey).parseClaimsJws(authToken);
            isTokenVerified = true;
        } catch (SignatureException ex) {
//            throw new InvalidTokenRequestException("JWT", authToken, "Incorrect signature");
        } catch (MalformedJwtException ex) {
//            throw new InvalidTokenRequestException("JWT", authToken, "Malformed jwt token");
        } catch (ExpiredJwtException ex) {
//            throw new InvalidTokenRequestException("JWT", authToken, "Token expired. Refresh required");
        } catch (UnsupportedJwtException ex) {
//            throw new InvalidTokenRequestException("JWT", authToken, "Unsupported JWT token");
        } catch (IllegalArgumentException ex) {
//            throw new InvalidTokenRequestException("JWT", authToken, "Illegal argument token");
        } 
//        finally {
        return isTokenVerified;
//        }
    }

    public Long getExpiryDuration() {
        return expiration;
    }
}
