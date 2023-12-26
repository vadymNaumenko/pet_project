package de.pet_project.dto.ticket;

import de.pet_project.domain.Game;
import de.pet_project.domain.User;
import lombok.*;
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TicketUpdateDTO {
    private Integer id;
    private Game game;

    private User user;

    private String number;
    private double price;

    private String state;
}
