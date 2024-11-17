package springboot.chatapp.config;

import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {
    @Bean
    public Queue messageQueue() {
        return new Queue("message.queue", true); // name of queue: "message.queue", durable = true
    }
}
