package de.pet_project.controller.dto.user;

import de.pet_project.domain.User;
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
    private String nickname;
    private String email;
    private String password;


//    public static UserCreateDTO getInstance(User user){
//        return new UserCreateDTO(
//                user.getId(), user.getAvatar(), user.getNickname(),
//                user.getFirstname(), user.getLastname(), user.getBirthDate(),
//                user.getEmail(), user.getPhone(), user.getRole(),user.getState(),user.getCreatedAt()
//        );
//    }
}
