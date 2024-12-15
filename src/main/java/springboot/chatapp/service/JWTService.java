package springboot.chatapp.service;

import io.jsonwebtoken.Claims;
import java.util.function.Function;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import springboot.chatapp.entity.User;

import java.util.Map;

public interface JWTService {

    <T> T extractClaim(String token, Function<Claims, T> claimsResolvers);

    String extractUsername(String token);
    String generateToken(UserDetails userDetails);
    boolean isValidToken(String token, UserDetails userDetails);
    String generateToken(Map<String, Object> claims, Authentication authentication);
}
