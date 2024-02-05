package de.pet_project.controller;

import de.pet_project.domain.User;
import de.pet_project.dto.user.UserEditeDTO;
import de.pet_project.dto.user.UserFilter;
import de.pet_project.dto.user.UserReadDTO;
import de.pet_project.dto.user.UserThisDTO;
import de.pet_project.service.UserService;
import jakarta.servlet.annotation.MultipartConfig;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
@Slf4j
public class UserController {

    private final UserService userService;

    @GetMapping("/this")
    public ResponseEntity<UserThisDTO> getThisUser(@AuthenticationPrincipal UserDetails userDetails){
        log.info("Fetching information for current user: {}", userDetails.getUsername());
        if (userDetails == null)
            return ResponseEntity.notFound().build();
        Optional<UserThisDTO> user = userService.findThisUser(userDetails);
        return user.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.badRequest().build());
    }

    @GetMapping()
    public Page<UserReadDTO> getUsers(Pageable pageable) {
        log.info("Fetching all users");
        return userService.findAll(pageable);
    }

    @PostMapping("/filter")
    public List<UserReadDTO> findByFilter(@RequestBody UserFilter filter){
        log.info("Fetching users by filter: {}", filter);
        return userService.findByFilter(filter);
    }

    @GetMapping(value = "/{id}/avatar")
    public ResponseEntity<byte[]> findAvatar(@PathVariable Integer id) {
        log.info("Fetching avatar for user with ID: {}", id);
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
    public HttpStatus update2( @RequestPart MultipartFile multipartFile, @AuthenticationPrincipal UserDetails userDetails) {
        log.info("Updating avatar for user: {}", userDetails.getUsername());
        if (userService.setAvatar(multipartFile,userDetails)) {
            return HttpStatus.OK;
        }
        return HttpStatus.BAD_REQUEST;
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserEditeDTO> update(@Validated @RequestBody UserEditeDTO userEditeDTO) {
        log.info("Updating user with ID: {}", userEditeDTO.getId());

        Optional<UserEditeDTO> user = userService.update(userEditeDTO);
        return user.map(editeDTO -> ResponseEntity.status(HttpStatus.OK).body(editeDTO))
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).body(userEditeDTO));

    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Integer id) {
        log.info("Deleting user with ID: {}", id);
        if (userService.delete(id)) {
            throw new ResponseStatusException(HttpStatus.OK);
        }
        throw new ResponseStatusException(HttpStatus.NOT_FOUND);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Map<String, String> handlerValidationExceptions(MethodArgumentNotValidException ex) {
        log.error("Validation error in user request", ex);
        Map<String, String> errors = new HashMap<>();

        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMassage = error.getDefaultMessage();
            errors.put(fieldName, errorMassage);
        });
        return errors;
    }
}
