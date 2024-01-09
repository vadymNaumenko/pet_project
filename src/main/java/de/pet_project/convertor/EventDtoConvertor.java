package de.pet_project.convertor;

import de.pet_project.domain.Event;
import de.pet_project.dto.event.EventCreateDTO;
import de.pet_project.dto.event.EventDTO;
import de.pet_project.utils.DateUtils;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class EventDtoConvertor {
    private final ModelMapper modelMapper;

    public EventDTO convertToEventDTO(Event event){
        return modelMapper.map(event,EventDTO.class);
    }
    public Event convertToEvent(EventDTO eventDTO){
        return modelMapper.map(eventDTO,Event.class);
    }
    public Event convertToEvent(EventCreateDTO eventCreateDTO){
        Event event = modelMapper.map(eventCreateDTO,Event.class);
        event.setDate(DateUtils.convertDate(eventCreateDTO.getDateTime()));
        return event;
    }
}
