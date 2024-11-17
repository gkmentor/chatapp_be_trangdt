package springboot.chatapp.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;
import springboot.chatapp.service.UserService;
import springboot.chatapp.service.JWTService;

import java.io.IOException;

@Component
@RequiredArgsConstructor
//OncePerRequestFilter: đảm bảo rằng bộ lọc được thực thi đúng một lần cho mỗi yêu cầu HTTP
public class JWTAuthenticationFilter extends OncePerRequestFilter {
    private final JWTService jwtService;
    private final UserService userService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        final String authorizationHeader = request.getHeader("Authorization");
        final String jwtToken;
        final String subject;
        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            jwtToken = authorizationHeader.substring(7);
            subject = jwtService.extractUsername(jwtToken);// Extract the subject from the token, could be username or email

            if(!StringUtils.isEmpty(subject) && SecurityContextHolder.getContext().getAuthentication() == null){
                UserDetails userDetails = userService.loadUserByUsername(subject);
                if(jwtService.isValidToken(jwtToken, userDetails)){
                    userDetails.getAuthorities().forEach(grantedAuthority -> grantedAuthority.getAuthority());
                    Authentication auth = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                    SecurityContextHolder.getContext().setAuthentication(auth);
                }
            }

        }
        filterChain.doFilter(request, response);
    }
}
