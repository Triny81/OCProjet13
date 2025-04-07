package chat.textuel.chattxt.repository;

import chat.textuel.chattxt.model.Message;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface MessageRepository extends JpaRepository<Message, Integer> {
    List<Message> findByConversationId(Long conversationId);
}
