package chat.textuel.chattxt.repository;

import chat.textuel.chattxt.model.Message;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MessageRepository extends JpaRepository<Message, Integer> {
}
