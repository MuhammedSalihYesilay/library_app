package com.library.library_app.config.securityConfig;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final AuthenticationProvider authenticationProvider;
    private final JwtAuthenticationFilter jwtAuthenticationFilter;

    public SecurityConfig(
            AuthenticationProvider authenticationProvider,
            JwtAuthenticationFilter jwtAuthenticationFilter
    ) {
        this.authenticationProvider = authenticationProvider;
        this.jwtAuthenticationFilter = jwtAuthenticationFilter;
    }

    @Bean
    SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable()) // CSRF koruması devre dışı
                .cors(cors -> cors.configurationSource(corsConfigurationSource())) // CORS yapılandırması
                .sessionManagement(session -> session
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS) // JWT için oturum yönetimi stateless
                )
                .authorizeHttpRequests(configurer -> configurer
                        .requestMatchers("/auth/login", "/auth/register/student", "/auth/register/admin").permitAll()
                        .requestMatchers(HttpMethod.GET, "/api/library-info").permitAll()
                        .requestMatchers(HttpMethod.POST, "/auth/login").permitAll() // Giriş herkese açık
                        .requestMatchers("/auth/user/{userId}").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.POST,"/api/room_types").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.DELETE,"/api/room_types/{roomTypeId}").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.PUT,"/api/room_types/{roomTypeId}").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.POST,"/api/rooms").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.DELETE,"/api/rooms/{roomId}").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.PUT,"/api/rooms/{roomId}").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.POST,"/api/category").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.DELETE,"/api/rooms/{Id}").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.POST,"/api/book").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.DELETE,"/api/book/{bookId}").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.POST,"/api/books/checkouts").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.PUT,"/api/books/checkouts/{checkoutId}/return").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.PUT,"/api/books/checkouts/{checkoutId}/extend").hasRole("ADMIN")
                        .anyRequest().authenticated() // Diğer tüm istekler kimlik doğrulama gerektirir
                )
                .authenticationProvider(authenticationProvider) // Authentication provider
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class); // JWT filtresi

        return http.build();
    }

    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(List.of("http://localhost:8080")); // CORS yapılandırması
        configuration.setAllowedMethods(List.of("GET", "POST", "DELETE", "PUT"));
        configuration.setAllowedHeaders(List.of("Authorization", "Content-Type"));

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(); // Parola güvenliği için şifreleyici
    }
}
