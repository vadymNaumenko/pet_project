package de.pet_project.mail;

import de.pet_project.controller.auth.AuthenticationRequest;
import de.pet_project.controller.auth.AuthenticationResponse;
import de.pet_project.controller.auth.AuthenticationService;
import de.pet_project.domain.ConfirmationCode;
import de.pet_project.domain.User;
import de.pet_project.repository.ConfirmationCodeRepository;
import de.pet_project.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("/mail")
public class MailSenderController {

    private final UserService userService;
    private final ConfirmationCodeRepository confirmationCodeRepository;

//    private final AuthenticationService authenticationService;
//    @GetMapping("/cod/{cod}")
//    public AuthenticationResponse checkCod(@PathVariable String cod){
//        Optional<ConfirmationCode> byCode = confirmationCodeRepository.findByCode(cod);
//        if (byCode.isPresent()) {
//          if (userService.codIsValid(cod)){
//              User user = byCode.get().getUser();
//              AuthenticationResponse response =  authenticationService.authenticate(new AuthenticationRequest(user.getEmail(), user.getPassword()));
//              System.out.println();
//              return response;
//          };
//
//        }
//       return null;
//    }

    @GetMapping("/cod/{cod}")
    public ResponseEntity<?> checkCod(@PathVariable String cod){
        Optional<ConfirmationCode> byCode = confirmationCodeRepository.findByCode(cod);
        if (byCode.isPresent()) {
            if (userService.codIsValid(cod)){
                return  ResponseEntity.status(HttpStatus.OK).body("User: is Confirmed");
            };
        }
        // todo jwt token
        return  ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body("User is not Confirmed: The verification code has expired, please register again");
    }

}
