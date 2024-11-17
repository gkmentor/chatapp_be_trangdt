package springboot.chatapp.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import springboot.chatapp.entity.Message;
import springboot.chatapp.repository.MessageRepository;
import springboot.chatapp.service.MessageService;

import java.time.LocalDateTime;
import java.util.List;

@RestController// @RestController dùng cho API, còn return View HTML thì dùng @Controller
@RequestMapping("/api/v1/message")//endpoint gốc
@RequiredArgsConstructor
public class MessageController {
    private final MessageService messageService;

    @GetMapping("/")
    public List<Message> getAllMessages() {
        return messageService.getMessages();
    }
}
