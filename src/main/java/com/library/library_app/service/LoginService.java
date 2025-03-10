package com.library.library_app.service;

import com.library.library_app.dto.request.LoginRequest;
import com.library.library_app.exception.CustomUsernameNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LoginService {

    private final JwtTokenProviderService jwtTokenProvider;  // JwtTokenProviderService inject edilmiştir
    private final AuthenticationManager authenticationManager;

    public String login(LoginRequest loginRequest) {
        try {
            // Kullanıcı adı ve şifre ile authentication işlemi
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            loginRequest.getUsername(),
                            loginRequest.getPassword()
                    )
            );

            // Kullanıcı doğrulandıktan sonra, JwtTokenProviderService kullanılarak token oluşturuluyor
            return jwtTokenProvider.generateToken(authentication);
        } catch (AuthenticationException e) {
            throw new CustomUsernameNotFoundException();
        }
    }
}
