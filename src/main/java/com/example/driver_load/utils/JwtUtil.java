package com.example.driver_load.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
public class JwtUtil {

    @Value("${jwt.secret.key}")
    private String SECRET_HS256_KEY ;
    private final Long ACCESS_EXPIRED_DATE = System.currentTimeMillis()+30*60*1000;
    private final Long REFRESH_EXPIRED_DATE = System.currentTimeMillis()+7*24*60*60*1000;
    public SecretKey getKeysHs256(){
        return Keys.hmacShaKeyFor(SECRET_HS256_KEY.getBytes());
    }

    public String generateAccessToken(String username){
        return createToken(username,new HashMap<>(),ACCESS_EXPIRED_DATE);
    }
    public String generateRefreshToken(String username){
        return createToken(username,new HashMap<>(),REFRESH_EXPIRED_DATE);
    }


    public String createToken(String subject, Map<String ,Object> claims,Long expiredDate){
        return Jwts.builder()
                .signWith(getKeysHs256(), SignatureAlgorithm.HS256)
                .setClaims(claims)
                .setSubject(subject)
                .setIssuedAt(new Date())
                .setExpiration(new Date(expiredDate))
                .compact();
    }

    public String getSubject(String token){
        Claims jwtBody = getJWTBody(token);
        return jwtBody.getSubject();
    }

    public Date getExpiration(String token){
        Claims jwtBody = getJWTBody(token);
        Date expiration = jwtBody.getExpiration();
        return expiration;
    }

    public Object getClaims(String token,String claimsName){
        Claims jwtBody = getJWTBody(token);
        return jwtBody.get(claimsName);
    }

    public Claims getJWTBody(String token){
        JwtParser parser = Jwts.parserBuilder()
                .setSigningKey(getKeysHs256())
                .build();
        return (Claims) parser
                .parse(token)
                .getBody();
    }
}
