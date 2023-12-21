package de.pet_project.controller;


import de.pet_project.controller.dto.user.UserCreateDTO;
import de.pet_project.controller.dto.user.UserEditeDTO;
import de.pet_project.controller.dto.user.UserReadDTO;
import de.pet_project.domain.User;
import de.pet_project.repository.UserRepository;
import de.pet_project.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/users")
public class UserController {
    private final UserService userService;


    //    @GetMapping
//    public List<UserDto>getUsers(){
//        return userService.findAll();
//    }
    @GetMapping
    public List<UserReadDTO> getUsers() {
        return userService.findAll();
    }

    @PostMapping
    public UserReadDTO create(@RequestBody UserCreateDTO userCreateDTO) {
        return userService.save(userCreateDTO);
    }

    @PutMapping("/{id}")
    public UserEditeDTO update(@PathVariable Integer id, @RequestBody UserEditeDTO userEditeDTO) {
        return userService.update(id,userEditeDTO)
                .orElseThrow();
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Integer id) { //todo void or boolean
        if (!userService.delete(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        throw new ResponseStatusException(HttpStatus.OK);
    }


}
