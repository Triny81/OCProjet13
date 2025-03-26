package chat.textuel.chattxt.service;

import chat.textuel.chattxt.model.Role;
import chat.textuel.chattxt.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class RoleService {
    @Autowired
    private RoleRepository roleRepository;
    
    public List<Role> findAll() {
        return roleRepository.findAll();
    }
    
    public Optional<Role> findById(Integer id) {
        return roleRepository.findById(id);
    }
    
    public Role save(Role role) {
        return roleRepository.save(role);
    }
    
    public void deleteById(Integer id) {
        roleRepository.deleteById(id);
    }
}
