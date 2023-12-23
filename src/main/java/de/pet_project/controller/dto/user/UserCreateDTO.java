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
    @NotBlank(message = "nickname: должен быть заполнен")
    @Pattern(regexp = "^[a-zA-Z0-9а-яА-Я. _-]{4,20}$",message = "nickname: Некорректный должна быть не меньше 4 символов и не больше 20")
    private String nickname;
    @NotBlank(message = "email: должен быть заполнен и без пробелов")
    @Email(message = "email: Некорректный")
    private String email;
    @NotBlank(message = "password: должен быть заполнен")

    @Pattern(regexp = "^[a-zA-Z0-9а-яА-Я.,:;_?!+=/'\\\\\"*(){}\\[\\]\\-]{8,49}$",message = "password: должна быть не меньше 8 символов," +
                                                                                  " содержать как минимум 1 большую букву," + " одну маленькую букву и  спец. символ либо цифру.")
    private String password;

}
