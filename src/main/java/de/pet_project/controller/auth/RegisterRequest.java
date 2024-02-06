package de.pet_project.controller.auth;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RegisterRequest {
//    @NotBlank(message = "nickname: должен быть заполнен")
//    @Pattern(regexp = "^[a-zA-Z0-9а-яА-Я. _-]{4,20}$",message = "nickname: Некорректный должна быть не меньше 4 символов и не больше 20")
//    private String nickname;
//    private String firstName;
//    private String LastName;
    @NotBlank(message = "email: должен быть заполнен и без пробелов")
    @Email(message = "email: Некорректный")
    private String email;
    @NotBlank(message = "password: должен быть заполнен")

    @Pattern(regexp = "^[a-zA-Z0-9а-яА-Я.,:;_?!+=/'\\\\\"*(){}\\[\\]\\-]{8,49}$",message = "password: должна быть не меньше 8 символов," +
                                                                                           " содержать как минимум 1 большую букву," + " одну маленькую букву и  спец. символ либо цифру.")
    private String password;
}
