package springboot.chatapp.config.security;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import springboot.chatapp.entity.*;
import springboot.chatapp.model.CredentialPayload;
import springboot.chatapp.repository.*;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Configuration
@RequiredArgsConstructor
public class CustomAuthenticationProvider implements AuthenticationProvider {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final UserRoleRepository userRoleRepository;
    private final PermissionRepository permissionRepository;
    private final RolePermissionRepository rolePermissionRepository;


    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
    //lưu trữ thông tin xác thực (credentials)/ thông tin đại diện người dùng (principal)/ danh sách các quyền (authorities)
    //Sau khi xác thực thành công, đối tượng Authentication được lưu trong SecurityContext
    //Input (Trước khi xác thực):
    //authentication được truyền vào bởi Spring Security và chứa:
    //authentication.getPrincipal(): Thông tin đầu vào của người dùng (email hoặc username).
    //authentication.getCredentials(): Mật khẩu đầu vào của người dùng.
    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        final String email = authentication.getPrincipal().toString();//Trả về thông tin đại diện cho người dùng.(username/ obj UserDetails)
        final String password = authentication.getCredentials().toString();//Trả về thông tin xác thực(password)
        final User user = userRepository.findByEmail(email);

        if (password == null || !passwordEncoder().matches(password, user.getPassword())) {
            throw new BadCredentialsException("Wrong password");
        }
        final CredentialPayload payload = CredentialPayload.builder().
                userId(user.getUserId())
                .email(user.getEmail())
                .build();
        List<SimpleGrantedAuthority> roles = getRoles(user);
        return new UsernamePasswordAuthenticationToken(email, payload, roles);//why must be use getRoles function ???
    }

    @Override//????mục đích của hàm này để làm gì
    //Gọi supports() của mỗi AuthenticationProvider để xác định AuthenticationProvider nào sẽ xử lý xác thực
    //cho UsernamePasswordAuthenticationToken.
    public boolean supports(Class<?> authentication) {
        //đối tượng Authentication được cung cấp
        return authentication.equals(UsernamePasswordAuthenticationToken.class);
    }

    //tại sao hàm getRoles này gộp cả roles + permissions để return
    private List<SimpleGrantedAuthority> getRoles(User user) {
        Set<UserRole> userRoleEntities = userRoleRepository.findByUser_UserId(user.getUserId());

        //trích xuất ids của user roles
        Set<Long> ids = userRoleEntities.stream().map(userRoleEntity -> userRoleEntity.getRole().getId()).collect(Collectors.toSet());

        Set<Role> roleEntities = roleRepository.findAllByIdIn(ids);
        //get Role có id thuộc ids
        List<SimpleGrantedAuthority> rolesAuthorities = roleEntities.stream()
                .map(item -> new SimpleGrantedAuthority(item.getName())).toList();

        List<SimpleGrantedAuthority> permissionAuthorities;

        if (rolesAuthorities.stream()
                .anyMatch(item -> item.getAuthority().equals("ROLE_ADMIN"))) {
            permissionAuthorities = permissionRepository.findAll().stream()
                    .map(item -> new SimpleGrantedAuthority(item.getName()))
                    .toList();
        } else {
            //trích xuất id của roles
            Set<Long> roleIds = roleEntities.stream().map(roleEntity -> roleEntity.getId()).collect(Collectors.toSet());
            //get role_permission has contained roleIds
            Set<RolePermission> rolePermissions = rolePermissionRepository.findAllByRoleIdIn(roleIds);
            //get list permission_id in role_permission
            Set<Long> permissionIds = rolePermissions.stream().map(rolePermissionEntity -> rolePermissionEntity.getPermission().getId()).collect(Collectors.toSet());
            Set<Permission> permissionEntities = permissionRepository.findAllByIdIn(permissionIds);

            permissionAuthorities = permissionEntities.stream()
                    .map(item -> new SimpleGrantedAuthority(item.getName()))
                    .toList();
        }
        return Stream.concat(rolesAuthorities.stream(), permissionAuthorities.stream()).toList();
    }
}
