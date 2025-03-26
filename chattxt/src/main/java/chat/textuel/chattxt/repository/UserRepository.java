package chat.textuel.chattxt.repository;

import chat.textuel.chattxt.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Integer> {
}
