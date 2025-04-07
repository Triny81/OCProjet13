package chat.textuel.chattxt.controller;

import chat.textuel.chattxt.dto.ConversationRequest;
import chat.textuel.chattxt.model.Conversation;
import chat.textuel.chattxt.model.User;
import chat.textuel.chattxt.service.ConversationService;
import chat.textuel.chattxt.service.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/conversations")
public class ConversationController {
    @Autowired
    private ConversationService conversationService;

    @Autowired
    private UserService userService;

    @GetMapping
    public List<Conversation> getAllConversations() {
        return conversationService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Conversation> getConversationById(@PathVariable Integer id) {
        return conversationService.findById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Conversation> createConversation(@RequestBody ConversationRequest request) {
        User admin = userService.findById(request.getAdminId())
                .orElseThrow(() -> new RuntimeException("Admin non trouvé"));
        User client = userService.findById(request.getClientId())
                .orElseThrow(() -> new RuntimeException("Client non trouvé"));
                
        Conversation conversation = new Conversation();
        conversation.setAdmin(admin);
        conversation.setClient(client);

        Conversation savedConversation = conversationService.save(conversation);
        return ResponseEntity.ok(savedConversation);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Conversation> updateConversation(@PathVariable Integer id,
            @RequestBody Conversation conversationDetails) {
        return conversationService.findById(id)
                .map(conversation -> {
                    conversation.setAdmin(conversationDetails.getAdmin());
                    conversation.setClient(conversationDetails.getClient());
                    return ResponseEntity.ok(conversationService.save(conversation));
                })
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteConversation(@PathVariable Integer id) {
        return conversationService.findById(id)
                .map(conversation -> {
                    conversationService.deleteById(id);
                    return ResponseEntity.ok().<Void>build();
                })
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/user/{id}")
    public ResponseEntity<List<Conversation>> getUserConversations(@PathVariable Long id) {
        List<Conversation> conversations = conversationService.getConversationsByUserId(id);
        return ResponseEntity.ok(conversations);
    }
}
