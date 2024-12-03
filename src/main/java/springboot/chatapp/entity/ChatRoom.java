package springboot.chatapp.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;
import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(
        name = "chatrooms",
        indexes = {
                @Index(name = "idx_type", columnList = "type"),
                @Index(name = "idx_created_by", columnList = "created_by")
        }
)
public class ChatRoom {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long chatRoomId;

    @Size(max = 100)
    private String name;

    @NotBlank
    @Column(nullable = false)
    private String type; // GROUP or PRIVATE

    @Column(name = "created_by")
    private Long createdBy;

    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt = LocalDateTime.now();

    private LocalDateTime updatedAt;

    @Column(nullable = false)
    private Boolean isDeleted = false;
}
