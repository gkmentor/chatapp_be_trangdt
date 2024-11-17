package springboot.chatapp.entity;
import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "messages")
public class Message {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "sender_id", nullable = false)
    private User sender;

    @ManyToOne
    @JoinColumn(name = "group_id")
    private ChatGroup chatGroup;

    @Column(name = "content", length = 1000)
    private String content;

    //TO_DO: add Index
    @Column(name = "created_at")
    private LocalDateTime createdAt;
    private String type = "TEXT";
}
