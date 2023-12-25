package de.pet_project.controller;

import de.pet_project.controller.dto.user.UserCreateDTO;
import de.pet_project.controller.dto.user.UserEditeDTO;
import de.pet_project.controller.dto.user.UserReadDTO;
import de.pet_project.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.FieldError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.HashMap;
import java.util.Map;

@Controller
@RequiredArgsConstructor
@RequestMapping("/mvc")
public class UserMvcController {

    private final UserService userService;

    @GetMapping()
    public Page<UserReadDTO> getUsers(Pageable pageable) {

        return userService.findAll(pageable);
    }
    @GetMapping("/test")
    public String update() {
        System.out.println();
//        if (userService.existsNickname(userEditeDTO.getNickname())){
//        ex.getBindingResult().addError( new ObjectError(userEditeDTO.getNickname(),"nickname: уже существует") );
//        }
        return "user";

    }

    @PostMapping("/{id}/update")
    public void update(@PathVariable Integer id,@ModelAttribute UserEditeDTO userEditeDTO, Model model /*,MethodArgumentNotValidException ex */) {
        userEditeDTO.setId(id);
        userService.update(userEditeDTO);
        System.out.println();
//        if (userService.existsNickname(userEditeDTO.getNickname())){
//        ex.getBindingResult().addError( new ObjectError(userEditeDTO.getNickname(),"nickname: уже существует") );
//        }

    }


}
