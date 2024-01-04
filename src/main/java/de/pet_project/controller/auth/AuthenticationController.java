package de.pet_project.controller.auth;

import de.pet_project.domain.ConfirmationCode;
import de.pet_project.domain.User;
import de.pet_project.mapper.UserCreateEditMapper;
import de.pet_project.repository.ConfirmationCodeRepository;
import de.pet_project.service.ConfirmationCodeService;
import de.pet_project.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthenticationController {

    private final AuthenticationService service;
    private final UserService userService;
    private final ConfirmationCodeService confirmationCodeService;

    @PostMapping("/authenticate")
    public ResponseEntity<AuthenticationResponse> register(@Validated @RequestBody RegisterRequest request){
        return ResponseEntity.ok(service.register(request));
    }
    @PostMapping("/login")
    public ResponseEntity<AuthenticationResponse> authenticate(@RequestBody AuthenticationRequest request){
        // todo if email not found make redirect and find by nickname
        return ResponseEntity.ok(service.authenticate(request));
    }

    @GetMapping("/confirm/cod={cod}")
    public ResponseEntity<?> checkCod(@PathVariable String cod){

//        Optional<ConfirmationCode> byCode = confirmationCodeRepository.findByCode(cod); // todo mast be service
//        if (byCode.isPresent()) {
//            if (userService.codIsValid(cod)){
//                User user = byCode.get().getUser();
//                userService.setState(byCode.get());
//                user.setState(User.State.CONFIRMED);
//                return  ResponseEntity.status(HttpStatus.OK).body("User: is Confirmed");
//            };
//        }

       if (confirmationCodeService.setState(cod)){
           return  ResponseEntity.status(HttpStatus.OK).body("User: is Confirmed");
       }
        // todo jwt token
        return  ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body("User is not Confirmed: The verification code has expired, please register again");
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
