package chat.textuel.chattxt.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

import chat.textuel.chattxt.dto.MessageRequest;
import chat.textuel.chattxt.model.Conversation;
import chat.textuel.chattxt.model.Message;
import chat.textuel.chattxt.model.User;
import chat.textuel.chattxt.service.ConversationService;
import chat.textuel.chattxt.service.MessageService;
import chat.textuel.chattxt.service.UserService;

@Controller
public class ChatWebSocketController {

    @Autowired
    private SimpMessagingTemplate messagingTemplate;

    @Autowired
    private MessageService messageService;

    @Autowired
    private UserService userService;

    @Autowired
    private ConversationService conversationService;

    @MessageMapping("/chat.send") // Destination front : /app/chat.send
    public void sendMessage(@Payload MessageRequest messageRequest) {

        User author = userService.findById(messageRequest.getAuthorId())
                .orElseThrow(() -> new RuntimeException("Auteur non trouvé"));

        Conversation conversation = conversationService.findById(messageRequest.getConversationId())
                .orElseThrow(() -> new RuntimeException("Conversation non trouvée"));

        Message message = new Message();
        message.setMessage(messageRequest.getMessage());
        message.setAuthor(author);
        message.setConversation(conversation);

        messageService.save(message);

        Message savedMessage = messageService.save(message);
        
        messagingTemplate.convertAndSend("/topic/conversation/" + messageRequest.getConversationId(), savedMessage);
    }
}
