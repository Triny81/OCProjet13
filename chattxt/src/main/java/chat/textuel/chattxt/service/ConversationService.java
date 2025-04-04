package chat.textuel.chattxt.service;

import chat.textuel.chattxt.model.Conversation;
import chat.textuel.chattxt.repository.ConversationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class ConversationService {
    @Autowired
    private ConversationRepository conversationRepository;
    
    public List<Conversation> findAll() {
        return conversationRepository.findAll();
    }
    
    public Optional<Conversation> findById(Integer id) {
        return conversationRepository.findById(id);
    }
    
    public Conversation save(Conversation conversation) {
        return conversationRepository.save(conversation);
    }
    
    public void deleteById(Integer id) {
        conversationRepository.deleteById(id);
    }

    public List<Conversation> getConversationsByUserId(Long userId) {
        return conversationRepository.findByClientIdOrAdminId(userId, userId);
    }
}
