package spring.security.service;

import spring.security.dto.JwtAuthenticationResponse;
import spring.security.dto.RefreshTokenRequest;
import spring.security.dto.SigninRequest;
import spring.security.dto.SignupRequest;
import spring.security.entity.User;

public interface AuthenticationService {

    User signup(SignupRequest signupRequest);

    JwtAuthenticationResponse signin(SigninRequest signinRequest);

    JwtAuthenticationResponse refreshToken(RefreshTokenRequest refreshTokenRequest);
}
