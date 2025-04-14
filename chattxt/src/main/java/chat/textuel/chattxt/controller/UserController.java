package chat.textuel.chattxt.controller;

import chat.textuel.chattxt.model.Role;
import chat.textuel.chattxt.model.User;
import chat.textuel.chattxt.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/users")
public class UserController {
    @Autowired
    private UserService userService;

    @GetMapping
    public List<User> getAllUsers() {
        return userService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> getUserById(@PathVariable Integer id) {
        return userService.findById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public User createUser(@RequestBody User user) {
        return userService.save(user);
    }

    @PutMapping("/{id}")
    public ResponseEntity<User> updateUser(@PathVariable Integer id, @RequestBody User userDetails) {
        Optional<User> u = userService.findById(id);

        if (u.isPresent()) {
            User currentUser = u.get();

            String name = currentUser.getName();
            if (name != null) {
                currentUser.setName(name);
            }

            String firstName = currentUser.getFirstName();
            if (firstName != null) {
                currentUser.setName(firstName);
            }

            String email = currentUser.getEmail();
            if (email != null) {
                currentUser.setEmail(email);
            }

            String password = currentUser.getPassword();
            if (password != null) {
                currentUser.setPassword(password);
            }

            Role role = currentUser.getRole();
            if (role != null) {
                currentUser.setRole(role);
            }

            return ResponseEntity.ok(userService.save(currentUser));
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Integer id) {
        return userService.findById(id)
                .map(user -> {
                    userService.deleteById(id);
                    return ResponseEntity.ok().<Void>build();
                })
                .orElseGet(() -> ResponseEntity.notFound().build());
    }
}
