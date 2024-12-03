package springboot.chatapp.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(
        name = "message_status",
        indexes = {
                @Index(name = "idx_message_status", columnList = "message_id, status_id", unique = true)
        }
)
public class MessageStatus {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long messageStatusId;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "message_id", nullable = false)
    private Message message;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "status_id", nullable = false)
    private Status status;
}
