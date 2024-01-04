package de.pet_project.dto.ticket;

import de.pet_project.domain.Game;
import de.pet_project.domain.TicketOrder;
import de.pet_project.domain.User;
import de.pet_project.dto.game.GameDTO;
import lombok.*;

import java.time.LocalDateTime;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TicketReadDTO {

    private Integer id;
    private GameDTO gameDTO;
    private String fullName;
    private String number;
    private double price;
    private String state;
    private LocalDateTime createAt;

}
