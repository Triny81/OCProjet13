package chat.textuel.chattxt.repository;

import chat.textuel.chattxt.model.Conversation;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ConversationRepository extends JpaRepository<Conversation, Integer> {
    List<Conversation> findByClientIdOrAdminId(Long clientId, Long adminId);
}
