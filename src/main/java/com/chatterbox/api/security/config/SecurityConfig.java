package com.chatterbox.api.security.config;

import com.chatterbox.api.security.jwt.JWTEntryPoint;
import com.chatterbox.api.security.jwt.JWTFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity // lets us do our custom security configuration on top of the default filter chain
public class SecurityConfig {

    private final UserDetailsService authService;
    private final BCryptPasswordEncoder encoder;
    private final JWTFilter jwtFilter;
    private final JWTEntryPoint entryPoint;

    @Autowired
    public SecurityConfig(UserDetailsService authService, BCryptPasswordEncoder encoder, JWTFilter filter, JWTEntryPoint entryPoint) {
        this.encoder = encoder;
        this.authService = authService;
        this.jwtFilter = filter;
        this.entryPoint = entryPoint;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity security) throws Exception {
        security.csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(customizer->
                        customizer
                                .requestMatchers("/api/v*/auth/**")
                                .permitAll()
                                .anyRequest()
                                .authenticated()

                )
               // .httpBasic(Customizer.withDefaults())
                .sessionManagement(sessionCustomizer->
                        sessionCustomizer.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .exceptionHandling(exception->exception.authenticationEntryPoint(entryPoint))
                .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);

        return security.build();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }

    @Bean
    public AuthenticationProvider authenticationProvider(){
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setPasswordEncoder(encoder);
        provider.setUserDetailsService(authService);
        return provider;
    }
}
