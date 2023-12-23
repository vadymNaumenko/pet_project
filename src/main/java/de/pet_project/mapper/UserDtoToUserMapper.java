package de.pet_project.mapper;

import de.pet_project.controller.dto.user.UserDTO;
import de.pet_project.domain.User;
import org.springframework.stereotype.Component;

@Component
public class UserDtoToUserMapper implements Mapper<UserDTO, User>{
    @Override
    public User map(UserDTO userDTO) {
        return new User();
    }
}
