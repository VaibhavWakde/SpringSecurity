package spring.security.service;

import spring.security.dto.SignupRequest;
import spring.security.entity.User;

public interface AuthenticationService {

    User signup(SignupRequest signupRequest);
}
