package de.pet_project.convertor;

import de.pet_project.domain.User;
import de.pet_project.dto.user.UserReadDTO;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserDtoConvert {

    private final ModelMapper modelMapper;

    public UserReadDTO convertToUserReadDto(User user){
        return modelMapper.map(user,UserReadDTO.class);
    }


}
