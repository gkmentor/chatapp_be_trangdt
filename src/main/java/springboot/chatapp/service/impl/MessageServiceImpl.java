package springboot.chatapp.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import springboot.chatapp.entity.Message;
import springboot.chatapp.repository.MessageRepository;
import springboot.chatapp.service.MessageService;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MessageServiceImpl implements MessageService {
    private final MessageRepository messageRepository;

    @Override
    public List<Message> getMessages() {
        return List.of();
    }
}
