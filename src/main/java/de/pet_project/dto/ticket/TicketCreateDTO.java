package de.pet_project.dto.ticket;

import de.pet_project.domain.Game;
import lombok.*;

import java.time.LocalDateTime;
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TicketCreateDTO {
    private Game game;
    private String number;
    private double price;
    private String state;
    private LocalDateTime createAt;
}
