package springboot.chatapp.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import springboot.chatapp.config.security.CustomAuthenticationProvider;
import springboot.chatapp.dto.LoginRequest;
import springboot.chatapp.dto.LoginResponse;
import springboot.chatapp.model.CredentialPayload;
import springboot.chatapp.service.AuthService;
import springboot.chatapp.service.JWTService;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
@Service
public class AuthServiceImpl implements AuthService {
    private final CustomAuthenticationProvider customAuthenticationProvider;
    private final JWTService jwtService;

    @Override
    public LoginResponse login(LoginRequest loginRequest) {
        final LoginResponse loginResponse = new LoginResponse();
        final Authentication authentication = this.customAuthenticationProvider.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getEmail(),
                        loginRequest.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        List<String> roles = authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .toList();
        //Tạo Claims cho JWT
        Map<String, Object> claims = new LinkedHashMap<>();
        if (authentication.getCredentials() instanceof CredentialPayload credentialPayload) {
            claims = buildClaimsFromAuthentication(credentialPayload.getEmail(),
                    credentialPayload.getUserId(), roles);
        }
        //Thêm thông tin loại token vào Claims:
        claims.put("token_type", "access_token");
        // Tạo Access Token
        loginResponse.setAccessToken(jwtService.generateToken(claims, authentication));
        loginResponse.setExpiresIn(System.currentTimeMillis() + 30);
        loginResponse.setRoles(roles);
        return loginResponse;
    }

    private Map<String, Object> buildClaimsFromAuthentication(String email, Long userId,
                                                              List<String> roles) {
        final Map<String, Object> claims = new LinkedHashMap<>();
        claims.put("u_id", userId);
        claims.put("u_email", email);
        claims.put("u_roles", roles);
        return claims;
    }
}

