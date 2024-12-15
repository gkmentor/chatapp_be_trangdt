package springboot.chatapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import springboot.chatapp.entity.ChatRoom;

@Repository
public interface ChatGroupRepository extends JpaRepository<ChatRoom, Long> {
}
