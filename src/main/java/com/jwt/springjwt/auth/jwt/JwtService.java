package com.jwt.springjwt.auth.jwt;

import java.util.Date;
import java.util.HashMap;
import java.util.function.Function;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

@Service
public class JwtService {
    
    @Value("${jwt.secret_key}")
    private String SECRET_KEY;

    public String getToken(UserDetails user){
        return createToken(new HashMap<>(), user);
    }

    private String createToken(HashMap<String, Object> claims, UserDetails user) {
        return Jwts.builder()
                .claims(claims)
                .subject(user.getUsername())
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis()+1000*60))
                .signWith(getSignKey())
                .compact();
    }

    /*Esta es la firma de jwt*/
    private SecretKey getSignKey(){
        byte[] keyBytes = Decoders.BASE64.decode(SECRET_KEY);
        return  Keys.hmacShaKeyFor(keyBytes); /*util class de io.jsonwebtoken para generar "secret keys"*/ 
    }

    public String getUsernameFromToken(String token) { 
        return getClaims(token, (claims) -> claims.getSubject());
    }

    private <T> T getClaims(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = getAllClaims(token);
        return claimsResolver.apply(claims);    
    }

    private Claims getAllClaims(String token) {
        return Jwts
            .parser()
            .verifyWith(getSignKey())
            .build()
            .parseSignedClaims(token)
            .getPayload();
    }

    public boolean isTokenValid(String token, UserDetails userDetails) {
        final String username = getUsernameFromToken(token);
        return username.equals(userDetails.getUsername()) && !isTokenExpired(token, userDetails);
    }

    private boolean isTokenExpired(String token, UserDetails userDetails) {
        return getExpirationToken(token).before(new Date());
    }

    private Date getExpirationToken(String token) {
        return getClaims(token, (claims) -> claims.getExpiration());
    }

    

    /*
        Jwts es una clase final con 3 clases staticas (SIG, KEY, ENC):
        - metodos de creacion de secret keys
        - algoritmos de codificacion 
        - otras
     */


}
