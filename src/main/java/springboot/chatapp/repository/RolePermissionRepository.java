package springboot.chatapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import springboot.chatapp.entity.RolePermission;

import java.util.Set;

@Repository
public interface RolePermissionRepository extends JpaRepository<RolePermission, Long> {
    Set<RolePermission> findAllByRoleIdIn(Set<Long> roleIds);
}
