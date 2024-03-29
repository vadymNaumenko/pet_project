package de.pet_project.dto.user;

import de.pet_project.domain.User;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO {

    private Integer id;
    private String avatar;
    private String nickname;
    private String firstname;
    private String lastname;
    private LocalDate birthDate;
    private String email;
    private String phone;
    private String role;
    private String state;
    private LocalDateTime createdAt;

    public static UserDTO getInstance(User user){
        return new UserDTO(
                user.getId(), user.getAvatar(), user.getNickname(), //todo should be default avatar
                user.getFirstname(), user.getLastname(), user.getBirthDate(),
                user.getEmail(), user.getPhone(), user.getRole().name(),user.getState().name(),user.getCreatedAt()
        );
    }

}
