package spring.security.service.impl;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import spring.security.entity.User;
import spring.security.service.JWTService;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Service
public class JWTServiceImpl implements JWTService {

    private static final Key SIGNING_KEY = generateSecureKey();

    public String generateToken(User userDetails) {
        return Jwts.builder().setSubject(userDetails.getEmail())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 1000 *60 * 24))
                .signWith(getSiginkey(), SignatureAlgorithm.HS256)
                .compact();
    }


    public String generateRefreshToken(HashMap<String, Object> extraclaims, UserDetails userDetails) {
        return Jwts.builder().setClaims(extraclaims).setSubject(userDetails.getUsername())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 604800000))
                .signWith(getSiginkey(), SignatureAlgorithm.HS256)
                .compact();
    }




    public String extractUserName(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    private <T> T extractClaim(String token, Function<Claims, T> claimsResolvers) {
        final Claims claims = extractAllclaims(token);
        return claimsResolvers.apply(claims);
    }


    private static Key generateSecureKey() {
        // Generate a secure key with a size of 256 bits
        return Keys.secretKeyFor(SignatureAlgorithm.HS256);
    }

    private Key getSiginkey() {
        return SIGNING_KEY;
    }


    private Claims extractAllclaims(String token) {
        return Jwts.parserBuilder().setSigningKey(getSiginkey()).build().parseClaimsJws(token).getBody();
    }




    public boolean isTokenValid(String token, UserDetails userDetails) {
        final String username = extractUserName(token);
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }



    private boolean isTokenExpired(String token) {
        return extractClaim(token, Claims::getExpiration).before(new Date());
    }


}
