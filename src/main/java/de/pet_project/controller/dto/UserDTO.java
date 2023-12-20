package de.pet_project.controller.dto;

import de.pet_project.domain.User;
import lombok.*;

import java.time.LocalDateTime;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO {
    private Integer id;
    private String avatar;
    private String nickname;
    private LocalDateTime createdAt;

    public static UserDTO getInstance(User user){
        return new UserDTO(user.getId(), user.getAvatar(), user.getNickname(), user.getCreatedAt());
    }
}
