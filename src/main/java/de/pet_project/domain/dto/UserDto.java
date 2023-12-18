package de.pet_project.domain.dto;

import de.pet_project.domain.User;
import jakarta.persistence.Entity;
import lombok.*;

import java.time.LocalDateTime;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserDto {
    private Integer id;
    private String avatar;
    private String nickname;
    private LocalDateTime createdAt;

    public static UserDto getInstance(User user){
        return new UserDto(user.getId(), user.getAvatar(), user.getNickname(), user.getCreatedAt());
    }
}
