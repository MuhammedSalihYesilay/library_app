package com.library.library_app.config.securityConfig;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.library.library_app.dto.response.ErrorResponse;
import com.library.library_app.service.JwtTokenProviderService;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import io.jsonwebtoken.security.SignatureException;

import java.io.IOException;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter{

    private final JwtTokenProviderService jwtTokenProvider;

    public JwtAuthenticationFilter(JwtTokenProviderService jwtTokenProvider) {
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        try {
            String token = getTokenFromRequest(request);

            if (token != null && jwtTokenProvider.validateToken(token)) {
                var authentication = jwtTokenProvider.getAuthentication(token);
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }

            filterChain.doFilter(request, response);
        } catch (ExpiredJwtException e) {
            handleJwtException(response, "JWT token has expired", "TOKEN_EXPIRED");
        } catch (MalformedJwtException e) {
            handleJwtException(response, "Malformed JWT token", "TOKEN_MALFORMED");
        } catch (SignatureException e) {
            handleJwtException(response, "Invalid JWT signature", "SIGNATURE_INVALID");
        } catch (UnsupportedJwtException e) {
            handleJwtException(response, "Unsupported JWT token", "TOKEN_UNSUPPORTED");
        } catch (IllegalArgumentException e) {
            handleJwtException(response, "JWT token is empty", "TOKEN_EMPTY");
        } catch (Exception e) {
            handleJwtException(response, "Authentication failed", "AUTH_FAILED");
        }
    }

    private String getTokenFromRequest(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");
        if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7); // "Bearer " kısmını at
        }
        return null;
    }

    private void handleJwtException(HttpServletResponse response, String message, String errorCode) throws IOException {
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED); // 401 Unauthorized döndür
        ObjectMapper mapper = new ObjectMapper();
        ErrorResponse errorResponse = new ErrorResponse(message, errorCode);
        response.setContentType("application/json");
        response.getWriter().write(mapper.writeValueAsString(errorResponse)); // JSON formatında hata mesajı döndür
    }
}
