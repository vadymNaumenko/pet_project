package de.pet_project.controller.dto.user;

import de.pet_project.domain.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.time.LocalDate;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserEditeDTO {
    private Integer id;
    private String avatar;
    private String nickname;
    private String firstname;
    private String lastname;
    private String password;
    private LocalDate birthDate;
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
