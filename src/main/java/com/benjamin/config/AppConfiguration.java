package com.benjamin.config;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;

import java.util.Collections;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class AppConfiguration {

    @Bean
    SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.sessionManagement(management -> management.sessionCreationPolicy(
                SessionCreationPolicy.STATELESS
        )).authorizeHttpRequests(auth -> auth
                        .requestMatchers("api/v1/auth/register").permitAll()
                        .requestMatchers("api/v1/products/reviews").permitAll()
                        .anyRequest().authenticated()
                ).addFilterBefore(new JwtTokenValidator(), BasicAuthenticationFilter.class)
                .csrf(AbstractHttpConfigurer::disable)
                .cors(cors -> cors.configurationSource(corsConfigurationSource()));

        return http.build();
    }

    private CorsConfigurationSource corsConfigurationSource() {
        return request -> {
            CorsConfiguration cfg = new CorsConfiguration();
            cfg.setAllowedOrigins(Collections.singletonList("*"));
            cfg.setAllowedMethods(Collections.singletonList("*"));
            cfg.setAllowedHeaders(Collections.singletonList("*"));
            cfg.setAllowCredentials(true);
            cfg.setExposedHeaders(Collections.singletonList("Authorization"));
            cfg.setMaxAge(3600L);
            return cfg;
        };
    }

    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }
}
