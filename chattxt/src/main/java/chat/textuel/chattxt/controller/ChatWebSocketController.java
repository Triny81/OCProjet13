package chat.textuel.chattxt.controller;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

import chat.textuel.chattxt.model.Message;

@Controller
public class ChatWebSocketController {

    @MessageMapping("/sendMessage") // Reçoit les messages envoyés sur /app/sendMessage
    @SendTo("/topic/messages") // Diffuse à tous les abonnés de /topic/messages
    public Message sendMessage(@Payload Message message) {
        return message;
    }
}
