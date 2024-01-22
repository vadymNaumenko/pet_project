package de.pet_project.dto.user;

import java.time.LocalDate;

public record UserFilter(String nickname,
                         String firstname,
                         String lastname,
                         LocalDate birthDate) {
}
