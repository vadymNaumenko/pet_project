package de.pet_project.controller;

import de.pet_project.dto.user.UserEditeDTO;
import de.pet_project.dto.user.UserReadDTO;
import de.pet_project.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

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
