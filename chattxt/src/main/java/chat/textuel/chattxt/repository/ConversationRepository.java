package chat.textuel.chattxt.repository;

import chat.textuel.chattxt.model.Conversation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ConversationRepository extends JpaRepository<Conversation, Integer> {
}
