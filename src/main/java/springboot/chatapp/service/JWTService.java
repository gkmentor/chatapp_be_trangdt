//package springboot.chatapp.service;
//
//import org.springframework.security.core.Authentication;
//import org.springframework.security.core.userdetails.UserDetails;
//import springboot.chatapp.entity.User;
//
//import java.util.Map;
//
//public interface JWTService {
//    String extractUsername(String token);
//    String generateToken(UserDetails userDetails);
//    boolean isValidToken(String token, UserDetails userDetails);
//    String generateRefreshToken(Map<String, Object> extraClaims, UserDetails userDetails);
//}
