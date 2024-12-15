package springboot.chatapp.model;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CredentialPayload {
    private Long userId;
    private String email;
}
