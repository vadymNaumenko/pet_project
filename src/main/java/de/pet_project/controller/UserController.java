package de.pet_project.controller;

import de.pet_project.domain.User;
import de.pet_project.domain.dto.UserDto;
import de.pet_project.repository.UserRepository;
import de.pet_project.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/users")
public class UserController {
    private final UserService userService;
    private final UserRepository userRepository;

//    @GetMapping
//    public List<UserDto>getUsers(){
//        return userService.findAll();
//    }
    @GetMapping
    public List<User>getUsers(){
        return userRepository.findAll();
    }
}
