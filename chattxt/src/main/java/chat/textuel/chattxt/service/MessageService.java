package chat.textuel.chattxt.service;

import chat.textuel.chattxt.model.Message;
import chat.textuel.chattxt.repository.MessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class MessageService {
    @Autowired
    private MessageRepository messageRepository;
    
    public List<Message> findAll() {
        return messageRepository.findAll();
    }
    
    public Optional<Message> findById(Integer id) {
        return messageRepository.findById(id);
    }
    
    public Message save(Message message) {
        return messageRepository.save(message);
    }
    
    public void deleteById(Integer id) {
        messageRepository.deleteById(id);
    }

    public List<Message> getConversationsByUserId(Long conversationId) {
        return messageRepository.findByConversationId(conversationId);
    }
}
