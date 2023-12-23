package de.pet_project.controller.dto.user;

import com.fasterxml.jackson.annotation.JsonFormat;
import de.pet_project.domain.User;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserEditeDTO {
    private Integer id;
    private String avatar;
    @NotBlank(message = "nickname должен быть заполнен")
    @Pattern(regexp = "^[a-zA-Z0-9а-яА-Я. _-]{4,20}$",message = "Некорректный nickname должна быть не меньше 4 символов и не больше 20")
    private String nickname;
    private String firstname;
    private String lastname;

    @Pattern(regexp = "^[a-zA-Z0-9а-яА-Я.,:; _?!+=/'\\\\\"*(){}\\[\\]\\-]{8,100}$",message = "password должна быть не меньше 8 символов,  содержать как минимум 1 большую букву, " +
                                                                                             " одну маленькую букву и  спец. символ либо цифру.")
    private String password;
    @Past(message = "Дата рождения должна быть в прошлом")
    @DateTimeFormat(pattern = "MM/dd/yyyy")
    private LocalDate birthDate;
    @NotBlank(message = "email должен быть заполнен")
    @Email(message = "Некорректный email")
    private String email;
    private String phone;


    public static UserEditeDTO getInstance(User user){
        return new UserEditeDTO(
                user.getId(), user.getAvatar(), user.getNickname(),
                user.getFirstname(), user.getLastname(),"******", user.getBirthDate(), //todo password *****
                user.getEmail(), user.getPhone()
        );
    }
}
