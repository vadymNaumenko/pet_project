package de.pet_project.controller.auth;

import de.pet_project.service.ConfirmationCodeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthenticationController {

    private final AuthenticationService service;
    private final ConfirmationCodeService confirmationCodeService;

    @PostMapping("/registration")
    public ResponseEntity<AuthenticationResponse> register(@Validated @RequestBody RegisterRequest request){
        return ResponseEntity.ok(service.register(request));
    }
    @PostMapping("/login")
    public ResponseEntity<AuthenticationResponse> authenticate(@RequestBody AuthenticationRequest request){
        // todo if email not found make redirect and find by nickname
        return ResponseEntity.ok(service.authenticate(request));
    }

    @PostMapping("/forgetPassword")
    public String forgetPassword (@RequestParam("email") String email){
        return service.forgotPassword(email);
    }

    @PostMapping("/new-password")
    public boolean createNewPassword(@RequestBody ForgotRequest forgotRequest){
        return service.setNewPassword(forgotRequest);
    }

    @GetMapping("/confirm/{cod}")
    public ResponseEntity<?> checkCod(@PathVariable String cod){

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
