package springboot.chatapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import springboot.chatapp.entity.UserRole;

import java.util.Set;

@Repository
//why <UserRole, Long> ??? What means of using Long in here ?
public interface UserRoleRepository extends JpaRepository<UserRole, Long> {
    //Tại sao lại phải viết thế này? viết thế nào cho đúng chú pháp JPA ??
    Set<UserRole> findByUser_UserId(Long userId);
}
