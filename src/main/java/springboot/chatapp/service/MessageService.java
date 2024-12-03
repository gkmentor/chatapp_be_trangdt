package springboot.chatapp.service;

import org.springframework.stereotype.Service;
import springboot.chatapp.entity.Message;

import java.util.List;

@Service
public interface MessageService {
    public List<Message> getMessages();
}
