package springboot.chatapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import springboot.chatapp.entity.ChatRoom;

public interface ChatGroupRepository extends JpaRepository<ChatRoom, Long> {
}
