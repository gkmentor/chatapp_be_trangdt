package springboot.chatapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import springboot.chatapp.entity.ChatGroup;

public interface ChatGroupRepository extends JpaRepository<ChatGroup, Long> {
}
