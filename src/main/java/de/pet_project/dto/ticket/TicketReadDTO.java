package de.pet_project.dto.ticket;

import de.pet_project.domain.Game;
import de.pet_project.domain.TicketOrder;
import de.pet_project.domain.User;
import lombok.*;

import java.time.LocalDateTime;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TicketReadDTO {
    private Integer id;
    private Game game;

    private User user;

    private String number;
    private double price;

    private String state;

    private LocalDateTime createAt;
    public static TicketReadDTO getInstance(TicketOrder ticket){
        TicketReadDTO ticketReadDTO = new TicketReadDTO();
        ticketReadDTO.setCreateAt(ticket.getCreateAt());
        ticketReadDTO.setGame(ticket.getGame());
        ticketReadDTO.setPrice(ticket.getPrice());
        ticketReadDTO.setState(ticket.getState().name());
        ticketReadDTO.setNumber(ticket.getNumber());
        ticketReadDTO.setId(ticket.getId());
//        ticketReadDTO.setUser(ticket.getUser());
        return ticketReadDTO;
    }

}
