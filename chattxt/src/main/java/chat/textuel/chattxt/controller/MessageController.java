package chat.textuel.chattxt.controller;

import chat.textuel.chattxt.dto.MessageRequest;
import chat.textuel.chattxt.model.Conversation;
import chat.textuel.chattxt.model.Message;
import chat.textuel.chattxt.model.User;
import chat.textuel.chattxt.service.ConversationService;
import chat.textuel.chattxt.service.MessageService;
import chat.textuel.chattxt.service.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/messages")
public class MessageController {
    @Autowired
    private MessageService messageService;

    @Autowired
    private UserService userService;

    @Autowired
    private ConversationService conversationService;

    @GetMapping
    public List<Message> getAllMessages() {
        return messageService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Message> getMessageById(@PathVariable Integer id) {
        return messageService.findById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Message> createMessage(@RequestBody MessageRequest request) {
        User author = userService.findById(request.getAuthorId())
                .orElseThrow(() -> new RuntimeException("Auteur non trouvé"));

        Conversation conversation = conversationService.findById(request.getConversationId())
                .orElseThrow(() -> new RuntimeException("Conversation non trouvée"));

        Message message = new Message();
        message.setMessage(request.getMessage());
        message.setAuthor(author);
        message.setConversation(conversation);

        Message savedMessage = messageService.save(message);
        return ResponseEntity.ok(savedMessage);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Message> updateMessage(@PathVariable Integer id, @RequestBody Message messageDetails) {
        return messageService.findById(id)
                .map(message -> {
                    message.setMessage(messageDetails.getMessage());
                    message.setAuthor(messageDetails.getAuthor());
                    message.setConversation(messageDetails.getConversation());
                    return ResponseEntity.ok(messageService.save(message));
                })
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMessage(@PathVariable Integer id) {
        return messageService.findById(id)
                .map(message -> {
                    messageService.deleteById(id);
                    return ResponseEntity.ok().<Void>build();
                })
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/conversation/{id}")
    public ResponseEntity<List<Message>> getConversationMessages(@PathVariable Long id) {
        List<Message> messages = messageService.getConversationsByUserId(id);
        return ResponseEntity.ok(messages);
    }
}
