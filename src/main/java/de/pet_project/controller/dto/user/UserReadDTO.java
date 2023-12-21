package de.pet_project.controller.dto.user;
import de.pet_project.domain.User;
import lombok.*;

import java.time.LocalDateTime;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserReadDTO {
    private Integer id;
    private String avatar;
    private String nickname;
    private LocalDateTime createdAt;

    public static UserReadDTO getInstance(User user) {
        return new UserReadDTO(user.getId(), user.getAvatar(), user.getNickname(), user.getCreatedAt());
    }

}
