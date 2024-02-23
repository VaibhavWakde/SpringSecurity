package spring.security.service;

import org.springframework.security.core.userdetails.UserDetails;
import spring.security.entity.User;

import java.util.HashMap;

public interface JWTService {

    String extractUserName(String token);

    String generateToken(User userDetails);

    boolean isTokenValid(String token, UserDetails userDetails);

    String generateRefreshToken(HashMap<String, Object> extraclaims, UserDetails userDetails);
}
