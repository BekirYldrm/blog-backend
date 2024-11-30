package com.pinsoft.intern.config;

import com.pinsoft.intern.jwt.JwtRequestFilter;
import com.pinsoft.intern.service.CustomUserDetailsService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final CustomUserDetailsService customUserDetailsService;
    private final JwtRequestFilter jwtRequestFilter;

    public SecurityConfig(CustomUserDetailsService customUserDetailsService, JwtRequestFilter jwtRequestFilter) {
        this.customUserDetailsService = customUserDetailsService;
        this.jwtRequestFilter = jwtRequestFilter;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http .cors(cors -> cors.configurationSource(corsConfigurationSource()))
                .authorizeHttpRequests(authorize -> authorize

                        .requestMatchers("/swagger-ui/**", "/v3/api-docs/**", "/apı-docs/**").permitAll()

                        .requestMatchers("/login/**").permitAll()

                        .requestMatchers(HttpMethod.GET, "/authors").permitAll()
                        .requestMatchers(HttpMethod.GET, "/authors/my-info/**").hasAnyRole("AUTHOR", "ADMIN")
                        .requestMatchers(HttpMethod.GET, "/authors/**").permitAll()
                        .requestMatchers(HttpMethod.POST, "/authors").permitAll()
                        .requestMatchers(HttpMethod.PATCH, "/authors/**").hasRole("AUTHOR")
                        .requestMatchers(HttpMethod.DELETE, "/authors/**").hasAnyRole("AUTHOR", "ADMIN")

                        .requestMatchers(HttpMethod.GET, "/blogs").permitAll()
                        .requestMatchers(HttpMethod.GET, "/blogs/**").permitAll()
                        .requestMatchers(HttpMethod.POST, "/blogs").hasRole("AUTHOR")
                        .requestMatchers(HttpMethod.PUT, "/blogs/**").hasRole("AUTHOR")
                        .requestMatchers(HttpMethod.DELETE, "/blogs/**").hasAnyRole("AUTHOR", "ADMIN")

                        .requestMatchers(HttpMethod.GET, "/categories").permitAll()
                        .requestMatchers(HttpMethod.GET, "/categories/**").permitAll()
                        .requestMatchers(HttpMethod.POST, "/categories").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.DELETE, "/categories/**").hasRole("ADMIN")

                        .requestMatchers(HttpMethod.GET, "/comments/user/**").hasAnyRole("ADMIN", "USER")
                        .requestMatchers(HttpMethod.GET, "/comments/blog/**").permitAll()
                        .requestMatchers(HttpMethod.GET, "/comments").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.GET, "/comments/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.POST, "/comments").hasRole("USER")
                        .requestMatchers(HttpMethod.DELETE, "/comments/**").hasAnyRole("USER", "ADMIN")

                        .requestMatchers(HttpMethod.GET, "/like/user/**").hasAnyRole("ADMIN", "USER")
                        .requestMatchers(HttpMethod.GET, "/like/blog/**").hasAnyRole("ADMIN", "AUTHOR")
                        .requestMatchers(HttpMethod.GET, "/like").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.GET, "/like/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.POST, "/like").hasRole("USER")

                        .requestMatchers(HttpMethod.GET, "/save/user/**").hasAnyRole("ADMIN", "USER")
                        .requestMatchers(HttpMethod.GET, "/save").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.GET, "/save/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.POST, "/save").hasRole("USER")

                        .requestMatchers("/roles", "/roles/**").hasRole("ADMIN")


                        .requestMatchers(HttpMethod.GET, "/users").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.GET, "/users/comment/**").permitAll()
                        .requestMatchers(HttpMethod.GET, "/users/**").hasAnyRole("ADMIN", "USER")
                        .requestMatchers(HttpMethod.POST, "/users").permitAll()
                        .requestMatchers(HttpMethod.PATCH, "/users/**").hasRole("USER")
                        .requestMatchers(HttpMethod.DELETE, "/users/**").hasAnyRole("USER", "ADMIN")


                        .anyRequest().authenticated()
                )
                .sessionManagement(session -> session
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                )
                .csrf(csrf -> csrf.disable());


        http.addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    @Bean
    public AuthenticationManager authManager(HttpSecurity http) throws Exception {
        AuthenticationManagerBuilder authenticationManagerBuilder =
                http.getSharedObject(AuthenticationManagerBuilder.class);
        authenticationManagerBuilder
                .userDetailsService(customUserDetailsService);
        return authenticationManagerBuilder.build();
    }

    @Bean
    public UrlBasedCorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration corsConfig = new CorsConfiguration();
        corsConfig.addAllowedOrigin("http://localhost:5173");
        corsConfig.addAllowedOrigin("https://blogger-pinsoft.vercel.app");
        corsConfig.addAllowedMethod("GET");
        corsConfig.addAllowedMethod("POST");
        corsConfig.addAllowedMethod("PUT");
        corsConfig.addAllowedMethod("DELETE");
        corsConfig.addAllowedMethod("OPTIONS");
        corsConfig.addAllowedHeader("Authorization");
        corsConfig.addAllowedHeader("Content-Type");
        corsConfig.addAllowedHeader("Accept");
        corsConfig.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", corsConfig);
        return source;
    }
}
