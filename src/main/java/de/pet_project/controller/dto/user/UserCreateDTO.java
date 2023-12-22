package de.pet_project.controller.dto.user;

import de.pet_project.domain.User;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserCreateDTO {
    @NotBlank(message = "nickname должен быть заполнен и без пробелов")
    @Pattern(regexp = "^[a-zA-Z0-9а-яА-Я. _-]{4,15}$",message = "Некорректный nickname")
    private String nickname;
    @NotBlank(message = "email должен быть заполнен и без пробелов")
    @Email(message = "Некорректный email")
    private String email;
    @NotBlank(message = "password должен быть заполнен и без пробелов")

    @Pattern(regexp = "^[a-zA-Z0-9а-яА-Я.,:; _?!+=/'\\\\\"*(){}\\[\\]\\-]{8,100}$",message = "password должна быть не меньше 8 символов," +
                                                                                  " содержать как минимум 1 большую букву," + " одну маленькую букву и  спец. символ либо цифру.")
    private String password;

}
