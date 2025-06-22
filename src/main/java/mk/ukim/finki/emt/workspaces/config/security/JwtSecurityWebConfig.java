package mk.ukim.finki.emt.workspaces.config.security;

import mk.ukim.finki.emt.workspaces.web.filters.JwtFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;

import java.util.List;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class JwtSecurityWebConfig {

    private final JwtFilter jwtFilter;

    public JwtSecurityWebConfig(JwtFilter jwtFilter) {
        this.jwtFilter = jwtFilter;
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration corsConfiguration = new CorsConfiguration();
        corsConfiguration.setAllowedOrigins(List.of("http://localhost:3000"));
        corsConfiguration.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE"));
        corsConfiguration.setAllowedHeaders(List.of("*"));
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", corsConfiguration);
        return source;
    }


    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)
                .cors(corsCustomizer ->
                        corsCustomizer.configurationSource(corsConfigurationSource())
                )
                .headers(headers -> headers
                        .frameOptions(HeadersConfigurer.FrameOptionsConfig::sameOrigin)
                )
                .authorizeHttpRequests(authorizeHttpRequestsCustomizer ->
                        authorizeHttpRequestsCustomizer
                                .requestMatchers(
                                        "/swagger-ui/**",
                                        "/v3/api-docs/**",
                                        "/h2/**",
                                        "/api/user/register",
                                        "/api/user/login",
                                        "/api/user",
                                        "/api/user/{id}",
                                        "/api/workspace",
                                        "/api/workspace/{id}",
                                        "/api/workspace/membership",
                                        "/api/workspace/membership/{workspaceId}",
                                        "/api/workspace/membership/{workspaceId}/{userId}"
                                )
                                .permitAll()
                                .requestMatchers("/api/user/add",
                                        "/api/user/edit/{id}",
                                        "/api/user/delete/{id}",
                                        "/api/user/me",
                                        "/api/workspace/add",
                                        "/api/workspace/edit/{id}",
                                        "/api/workspace/delete/{id}",
                                        "/api/workspace/membership/add",
                                        "/api/workspace/membership/create",
                                        "/api/workspace/membership/edit/{workspaceId}/{memberId}",
                                        "/api/workspace/membership/delete/{workspaceId}/{userId}")
                                .authenticated()
                )
                .sessionManagement(sessionManagementConfigurer ->
                        sessionManagementConfigurer.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                )
                .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }

}
