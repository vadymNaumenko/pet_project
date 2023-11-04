package de.pet_project.service;

import de.pet_project.entity.User;
import de.pet_project.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    public User findById(Integer id){
        return userRepository.findById(id).orElse(null);

    }

    public void create(User user1){
         userRepository.save(user1);

    }
    public List<User> findAll(){
        return userRepository.findAll();

    }

}
