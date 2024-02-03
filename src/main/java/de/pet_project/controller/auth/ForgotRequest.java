package de.pet_project.controller.auth;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ForgotRequest {
    @NotBlank(message = "code: должен быть заполнен")
    @NotNull
    private String code;

    @NotBlank(message = "password: должен быть заполнен")
    @Pattern(regexp = "^[a-zA-Z0-9а-яА-Я.,:;_?!+=/'\\\\\"*(){}\\[\\]\\-]{8,49}$",message = "password: должна быть не меньше 8 символов," +
                                                                                           " содержать как минимум 1 большую букву," + " одну маленькую букву и  спец. символ либо цифру.")
    private String password;
}
