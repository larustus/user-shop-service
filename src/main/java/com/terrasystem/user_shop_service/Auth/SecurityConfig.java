package com.terrasystem.user_shop_service.Auth;

import com.terrasystem.user_shop_service.Auth.JWT.JWTAuthFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.header.writers.ReferrerPolicyHeaderWriter;

@Configuration
@EnableMethodSecurity
public class SecurityConfig {

    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    SecurityFilterChain filterChain(HttpSecurity http, JWTAuthFilter jwtAuthFilter) throws Exception {
        http
            .csrf(csrf -> csrf.disable())
            .cors(cors -> {})
            .sessionManagement(sm -> sm.sessionCreationPolicy(SessionCreationPolicy.STATELESS))

            // Wyłączamy mechanizmy "loginowe" Spring Security
            .formLogin(form -> form.disable())
            .httpBasic(basic -> basic.disable())

            // Minimalny zestaw nagłówków (bez ryzyka dla frontu)
            .headers(h -> h
                .frameOptions(f -> f.deny())
                .contentTypeOptions(c -> {}) // X-Content-Type-Options: nosniff
                .referrerPolicy(r -> r.policy(ReferrerPolicyHeaderWriter.ReferrerPolicy.STRICT_ORIGIN_WHEN_CROSS_ORIGIN))
            )

            .authorizeHttpRequests(auth -> auth
                .requestMatchers("/auth/**").permitAll()
                .requestMatchers(HttpMethod.GET, "/items/**").permitAll()
                .requestMatchers("/orders/**").authenticated()
                .requestMatchers(HttpMethod.GET, "/items/*/comments/**").permitAll()
                .requestMatchers(HttpMethod.POST, "/items/*/comments/**").authenticated()

                // zamknij wszystko inne (ważne!)
                .anyRequest().denyAll()
            );

        http.addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
}
