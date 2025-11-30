package com.example.springserver.authentication.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.crypto.spec.SecretKeySpec;
import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Service
public class JwtService {

    @Value("${jwt.secret.key}")
    private String secretKey;

    @Value("${jwt.access-token.expiration}")
    private long accessTokenExpiration;

    @Value("${jwt.refresh-token.expiration}")
    private long refreshTokenExpiration;


    public String extractUserName(String jwtToken) {
        return extractClaim(jwtToken,Claims::getSubject);
    }

    public boolean isTokenValid(String jwtToken,String username) {
        String extractedUserName = extractUserName(jwtToken);
        return extractedUserName.equals(username) && !isTokenExpired(jwtToken);
    }

    private boolean isTokenExpired(String jwtToken) {
        return extractedExpiration(jwtToken).before(new Date());
    }

    private Date extractedExpiration(String jwtToken) {
        return extractClaim(jwtToken, Claims::getExpiration);
    }

    private <T> T extractClaim(String jwtToken, Function<Claims, T> claimsResolver) {

        Claims claims = extractAllClaims(jwtToken);
        return claimsResolver.apply(claims);
    }

    private Claims extractAllClaims(String jwtToken) {
        Key key = new SecretKeySpec(secretKey.getBytes(), "HmacSHA256");
        return Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(jwtToken)
                .getBody();
    }

    public String getAccessToken(UserDetails userDetails) {
        String username = userDetails.getUsername();

        Map<String, Object> claims = new HashMap<>();
        claims.put("sub", username);
        claims.put("roles", userDetails.getAuthorities());
        claims.put("iat", new Date().getTime());
        claims.put("exp", new Date().getTime() + accessTokenExpiration);


        return createToken(username, claims, accessTokenExpiration);
    }

    public String getRefreshToken(UserDetails userDetails) {
        String username = userDetails.getUsername();

        Map<String, Object> claims = new HashMap<>();
        claims.put("sub", username);
        claims.put("iat", new Date().getTime());
        claims.put("exp", new Date().getTime() + refreshTokenExpiration);

        return createToken(username, claims, refreshTokenExpiration);
    }

    private String createToken(String username, Map<String,Object> claims,long expiration) {
        Key key = new SecretKeySpec(secretKey.getBytes(), "HmacSHA256");
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(username)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + expiration))
                .signWith(key)
                .compact();
    }
}
