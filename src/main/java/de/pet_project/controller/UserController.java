package de.pet_project.controller;


import de.pet_project.controller.dto.user.UserCreateDTO;
import de.pet_project.controller.dto.user.UserDTO;
import de.pet_project.controller.dto.user.UserEditeDTO;
import de.pet_project.controller.dto.user.UserReadDTO;
import de.pet_project.service.UserService;
import lombok.RequiredArgsConstructor;
import org.apache.catalina.connector.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.StreamingHttpOutputMessage;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/users")
public class UserController {

    private final UserService userService;


    @GetMapping
    public List<UserReadDTO> getUsers() {
        userService.findById()
        return userService.findAll();
    }

    @PostMapping
    public ResponseEntity<?> registration(@Validated @RequestBody UserCreateDTO userCreateDTO) {
        return ResponseEntity.status(HttpStatus.OK).body(userService.save(userCreateDTO));
    }
    @GetMapping("/{id}") // todo for admin
    public UserDTO update(@PathVariable Integer id) {
        return userService.findById(id);
    }
    @PutMapping("/{id}")
    public UserEditeDTO update(@PathVariable Integer id, @Validated @RequestBody UserEditeDTO userEditeDTO) {
        return userService.update(id, userEditeDTO)
                .orElseThrow();
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Integer id) { //todo void or boolean
        if (!userService.delete(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        throw new ResponseStatusException(HttpStatus.OK);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Map<String, String> handlerValidationExceptions(MethodArgumentNotValidException ex) {

        Map<String, String> errors = new HashMap<>();

        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMassage = error.getDefaultMessage();
            errors.put(fieldName, errorMassage);
        });
        return errors;
    }

}
