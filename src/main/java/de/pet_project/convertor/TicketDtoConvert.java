package de.pet_project.convertor;

import de.pet_project.domain.TicketOrder;
import de.pet_project.dto.ticket.TicketReadDTO;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class TicketDtoConvert {

    private final ModelMapper modelMapper;
    private final GameDtoConvert gameDtoConvert;

    public TicketReadDTO convertToTicketReadDTO(TicketOrder ticketOrder){
        TicketReadDTO ticketReadDTO =  modelMapper.map(ticketOrder,TicketReadDTO.class);
        ticketReadDTO.setGameDTO(gameDtoConvert.convertToGameDTO(ticketOrder.getGame()));
        ticketReadDTO.setFullName(ticketOrder.getUser().getFirstname()+" "+ ticketOrder.getUser().getLastname());
        return ticketReadDTO;
    }

//    public TicketOrder updateTicketOrder(TicketOrder ticketOrder,TicketEditeDTO ticketEditeDTO){
//        modelMapper.map(ticketOrder,ticketEditeDTO);
//        System.out.println();
//
//    }

}
