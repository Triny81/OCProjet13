package chat.textuel.chattxt.service;

import chat.textuel.chattxt.configuration.SecurityConfig;
import chat.textuel.chattxt.model.User;
import chat.textuel.chattxt.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    @Autowired
    private SecurityConfig ssc;

    @Autowired
    private UserRepository userRepository;
    
    public List<User> findAll() {
        return userRepository.findAll();
    }
    
    public Optional<User> findById(Integer id) {
        return userRepository.findById(id);
    }

    public Optional<User> findByEmail(String eamil) {
        return userRepository.findByEmail(eamil);
    }
    
    public User save(User user) { 
        if (user.getId() == null || !isPasswordEncoded(user.getPassword())) {
            user.setPassword(ssc.passwordEncoder().encode(user.getPassword()));
        }
    
        User savedUser = userRepository.save(user);
        return savedUser;
    }
    
    public void deleteById(Integer id) {
        userRepository.deleteById(id);
    }

    public Optional<User> authenticate(String email, String password) {
        return userRepository.findByEmail(email)
                .filter(user -> user.getPassword().equals(password));
    }

    private boolean isPasswordEncoded(String password) {
        return password != null && password.startsWith("$2");
    }
}
