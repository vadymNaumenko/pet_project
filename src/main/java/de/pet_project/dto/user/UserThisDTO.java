package de.pet_project.dto.user;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserThisDTO {
    private String avatar;
    private String nickname;
    private String email;
    private String phone;
    private String role;
}
