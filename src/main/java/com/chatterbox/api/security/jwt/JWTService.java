package com.chatterbox.api.security.jwt;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


@Service
public class JWTService {
    private final UserDetailsService userDetailsService;
    private static final Logger logger = LoggerFactory.getLogger(JWTService.class);
    @Autowired
    public JWTService(UserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }
    public List<GrantedAuthority> getRolesFromJWT(String token) {
        Claims claims = Jwts.parser()
                .verifyWith(getSigningKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();

        // Extract roles as a List<String>
        List<String> roles = (List<String>) claims.get("roles");

        // Convert to List<GrantedAuthority>
        return roles.stream()
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());
    }

    public String generateToken(UserDetails user){
        String username = user.getUsername();
        Map<String, Object> claims = new HashMap<>();
        claims.put("roles", user.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority).toList());

        return Jwts
                .builder()
                .claims(claims)
                .subject(username)
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + 60*60*30))
                .signWith(getSigningKey())
                .compact();
    }

    public String extractUsernameFromJwt(String token){
        return Jwts
                .parser()
                .verifyWith(getSigningKey())
                .build()
                .parseSignedClaims(token)
                .getPayload()
                .getSubject();
    }

    public Claims extractClaims(String token){
        return Jwts.parser()
                .verifyWith(getSigningKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    public String getJwtFromHeader(HttpServletRequest request){
        String authHeader = request.getHeader("Authorization");
        String token = "";
        //TODO: throw exception if header is null
        if(authHeader != null && authHeader.startsWith("Bearer ")){
            token = authHeader.substring(7);
        }

        return token;
    }


    public void verifyTokenSignature(String token){

    }

    private SecretKey getSigningKey() {
        String jwtSecret = "ThisIsA256BitLongKey1234567890123456";
        byte[] keyBytes = jwtSecret.getBytes(StandardCharsets.UTF_8);
        return Keys.hmacShaKeyFor(keyBytes);
    }


    public boolean validateToken(String token) {
        Jwts
                .parser()
                .verifyWith(getSigningKey())
                .build()
                .parseSignedClaims(token);
        return true;
    }


}
