package de.pet_project.controller;

import de.pet_project.dto.user.UserEditeDTO;
import de.pet_project.dto.user.UserFilter;
import de.pet_project.dto.user.UserReadDTO;
import de.pet_project.service.UserService;
import jakarta.servlet.annotation.MultipartConfig;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.validation.FieldError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/users")
public class UserController {

    private final UserService userService;

    //todo add filter for user and game
    @GetMapping()
    public Page<UserReadDTO> getUsers(Pageable pageable) {
        return userService.findAll(pageable);
    }

    @PostMapping("/filter")
    public Page<UserReadDTO> findByFilter(@RequestBody UserFilter filter, @RequestParam Integer pageNumber, @RequestParam Integer pageSize){
        return userService.findByFilter(filter,pageNumber,pageSize);
    }
    @GetMapping(value = "/{id}/avatar")
    public ResponseEntity<byte[]> findAvatar(@PathVariable Integer id) {

        return userService.findAvatar(id)
                .map(content -> ResponseEntity.ok()
                        .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_OCTET_STREAM_VALUE)
                        .contentLength(content.length)
                        .body(content))
                .orElseGet(ResponseEntity.notFound()::build);

    }

    @PostMapping(
            value = "/avatar",
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public void update2( @RequestPart MultipartFile multipartFile, @AuthenticationPrincipal UserDetails userDetails) {
    }


        @PutMapping("/{id}")
//        @PutMapping(value = "/{id}")
    public ResponseEntity<UserEditeDTO> update(@Validated @RequestBody UserEditeDTO userEditeDTO/*,MethodArgumentNotValidException ex */) {

//        if (userService.existsNickname(userEditeDTO.getNickname())){
//        ex.getBindingResult().addError( new ObjectError(userEditeDTO.getNickname(),"nickname: уже существует") );
//        }

//        Optional<UserEditeDTO> user = userService.update(userEditeDTO);
//        if (user.isPresent()){
//            return ResponseEntity.status(HttpStatus.OK).body(user.get());
//        }

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(userEditeDTO);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Integer id) { //todo void or UserDTO
        if (userService.delete(id)) {
            throw new ResponseStatusException(HttpStatus.OK);
        }
        throw new ResponseStatusException(HttpStatus.NOT_FOUND);
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
