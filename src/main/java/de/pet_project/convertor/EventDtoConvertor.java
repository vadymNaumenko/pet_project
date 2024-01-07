package de.pet_project.convertor;

import de.pet_project.domain.Event;
import de.pet_project.dto.event.EventDTO;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class EventDtoConvertor {
    private final ModelMapper modelMapper;

    public EventDTO convertToEventDTO(Event event){
        EventDTO eventDTO = modelMapper.map(event,EventDTO.class);
        //todo add datetime util
        return eventDTO;
    }

}
