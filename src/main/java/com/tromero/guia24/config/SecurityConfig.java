package com.tromero.guia24.config;
import org.springframework.context.annotation.Bean;
import com.tromero.guia24.jwt.JwtAuthenticationFilter;
import com.tromero.guia24.jwt.JwtTokenProvider;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;


import java.util.Arrays;

@Configuration
public class SecurityConfig {
    private final JwtTokenProvider jwtTokenProvider;

    public SecurityConfig(JwtTokenProvider jwtTokenProvider) {
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http,
                                                   AuthenticationManager authenticationManager) throws Exception {
        JwtAuthenticationFilter jwtAuthenticationFilter = new
                JwtAuthenticationFilter(authenticationManager, jwtTokenProvider);
        http.authorizeHttpRequests(authorize -> authorize
                        .requestMatchers("/swagger-iu/**", "/v3/api-docs/**").permitAll()
                        .requestMatchers("/api/auth/**").permitAll()
                        .requestMatchers("/api/visitante/**").permitAll()
                        .requestMatchers("/api/usuario/**").hasAnyRole("USER", "ADMIN")
                        .requestMatchers("/api/admin/**").hasRole("ADMIN")
                        .anyRequest().authenticated()
                )
                .csrf(csrf -> csrf.disable())

                .sessionManagement(session ->

                        session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))

                .addFilterBefore(jwtAuthenticationFilter,

                        UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }

    @Bean

    public AuthenticationManager
    authenticationManager(AuthenticationConfiguration authenticationConfiguration)
            throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public CorsFilter corsFilter() {
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowedOrigins(Arrays.asList("http://localhost:4200")); //Permite solicitudes desde localhost:4200
        config.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE",
                "OPTIONS")); // Métodos permitidos
        config.setAllowedHeaders(Arrays.asList("Authorization",
                "Content-Type"));
        config.setAllowCredentials(true); // Permitir credenciales (opcional)
        org.springframework.web.cors.UrlBasedCorsConfigurationSource source = new
                UrlBasedCorsConfigurationSource();

        source.registerCorsConfiguration("/**", config); // Aplica laconfiguración de CORS a todas las rutas
        return new CorsFilter(source);
    }
}

