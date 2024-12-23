package springboot.chatapp.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import springboot.chatapp.entity.Message;

@RestController
@RequestMapping("/api/messages")
public class ChatController {
    private final SimpMessagingTemplate messagingTemplate;

    public ChatController(SimpMessagingTemplate messagingTemplate) {
        this.messagingTemplate = messagingTemplate;
    }

    @PostMapping("/send")
    public ResponseEntity<?> sendMessage(@RequestBody Message message) {
        System.out.println("message: " + message.getContent());

        // Gửi tin nhắn đến các client đã subscribe vào topic
        messagingTemplate.convertAndSend("/topic/messages", message);

        // Trả về phản hồi HTTP
        return ResponseEntity.ok(message.getContent());
    }
}
