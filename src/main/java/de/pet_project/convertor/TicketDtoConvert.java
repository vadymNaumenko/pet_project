package de.pet_project.convertor;

import de.pet_project.domain.TicketOrder;
import de.pet_project.dto.ticket.TicketCreateDTO;
import de.pet_project.dto.ticket.TicketReadDTO;
import de.pet_project.dto.ticket.TicketUpdateDTO;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
@RequiredArgsConstructor
public class TicketDtoConvert {

    private final ModelMapper modelMapper;

    public TicketReadDTO convertToTicketReadDTO(TicketOrder ticketOrder){
        return modelMapper.map(ticketOrder,TicketReadDTO.class);
    }
    public TicketOrder convertToTicketOrder(TicketCreateDTO ticketCreateDTO){
        return  modelMapper.map(ticketCreateDTO, TicketOrder.class);


    }
    public TicketOrder convertToTicketOrder(TicketUpdateDTO ticketUpdateDTO){
        return modelMapper.map(ticketUpdateDTO,TicketOrder.class);
    }

}
