package springboot.chatapp.config.security.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import springboot.chatapp.config.security.custom.CustomAuthenticationProvider;
import springboot.chatapp.repository.PermissionRepository;
import springboot.chatapp.repository.RolePermissionRepository;
import springboot.chatapp.repository.RoleRepository;
import springboot.chatapp.repository.UserRepository;
import springboot.chatapp.repository.UserRoleRepository;

@RequiredArgsConstructor
@Configuration
public class AuthConfig {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final UserRoleRepository userRoleRepository;
    private final PermissionRepository permissionRepository;
    private final RolePermissionRepository rolePermissionRepository;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationProvider customAuthenticationProvider() {
        return new CustomAuthenticationProvider(userRepository, roleRepository, userRoleRepository,
                permissionRepository, rolePermissionRepository, passwordEncoder());
    }

}
