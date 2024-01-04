package de.pet_project.dto.ticket;

import de.pet_project.dto.game.GameDTO;
import de.pet_project.dto.user.UserDTO;
import lombok.*;

import java.time.LocalDateTime;
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TicketEditeDTO {
    private GameDTO gameDTO;
    private double price;
    private String state;
}
