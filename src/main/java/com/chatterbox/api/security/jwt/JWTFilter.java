package com.chatterbox.api.security.jwt;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;

@Component
public class JWTFilter extends OncePerRequestFilter {

    private final JWTService jwtService;
    private final UserDetailsService userDetailsService;
    @Autowired
    public JWTFilter(JWTService jwtService, UserDetailsService userDetailsService) {
        this.jwtService = jwtService;
        this.userDetailsService = userDetailsService;
    }

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain
    )
            throws ServletException, IOException {
        try{
            // extract token from header
            String token =
                    jwtService.getJwtFromHeader(request);

            // token validation
            if(token != null && jwtService.validateToken(token)) {
                // extract username from the token
                String username =
                        jwtService.extractUsernameFromJwt(token);
                // load user from database according to the username
                UserDetails user =
                        userDetailsService.loadUserByUsername(username);

                // fetch the granted authorities from the claims
                List<GrantedAuthority> authorities = jwtService.getRolesFromJWT(token);

                UsernamePasswordAuthenticationToken authentication =
                        new UsernamePasswordAuthenticationToken(
                                username,
                                null,
                                authorities
                        );
                authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                // setting authentication state
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        }
        catch (MalformedJwtException e){
            logger.error("Invalid JWT token");
        }
        catch (ExpiredJwtException e){
            logger.error("JWT token expired");
        }
        catch (UnsupportedJwtException e){
            logger.error("JWT token is not supported");
        }
        catch (IllegalArgumentException e){
            logger.error("JWT token claims string is empty");
        }
        filterChain.doFilter(request,response);
    }
}
