package de.pet_project.dto.user;

import de.pet_project.domain.User;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserEditeDTO {
    private Integer id;
    private MultipartFile avatar;
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

    public UserEditeDTO(Integer id, String nickname,
                        String firstname, String lastname,
                        String password, LocalDate birthDate,
                        String email, String phone) {
        this.id = id;
        this.nickname = nickname;
        this.firstname = firstname;
        this.lastname = lastname;
        this.password = password;
        this.birthDate = birthDate;
        this.email = email;
        this.phone = phone;
    }
}
