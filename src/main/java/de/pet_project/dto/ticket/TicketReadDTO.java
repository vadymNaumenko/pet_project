package de.pet_project.dto.ticket;

import de.pet_project.dto.game.GameCreateDTO;
import lombok.*;

import java.time.LocalDateTime;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TicketReadDTO {

    private Integer id;
    private GameCreateDTO gameCreateDTO;
    private String fullName;
    private String number;
    private double price;
    private String state;
    private LocalDateTime createAt;

}
