package de.pet_project.service;

import de.pet_project.controller.dto.UserDTO;
import de.pet_project.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserService {
   private final UserRepository userRepository;

    public List<UserDTO> findAll() {
        return userRepository.findAll().stream()
                .map(UserDTO::getInstance)
                .collect(Collectors.toList());
    }
}
