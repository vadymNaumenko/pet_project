package de.pet_project.mapper;

import de.pet_project.controller.dto.user.UserCreateDTO;
import de.pet_project.controller.dto.user.UserDTO;
import de.pet_project.controller.dto.user.UserEditeDTO;
import de.pet_project.domain.User;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class UserCreateEditMapper implements Mapper<UserCreateDTO, User>{
    @Override
    public User map(UserCreateDTO userCreateDTO) {
        return new User(
                userCreateDTO.getNickname(), userCreateDTO.getEmail(),
                userCreateDTO.getPassword(), User.State.NOT_CONFIRM, LocalDateTime.now(),
                User.Role.USER);
    }

    public User map(UserEditeDTO userUpdateDTO, User toUser){
        toUser.setAvatar(userUpdateDTO.getAvatar());
        toUser.setEmail(userUpdateDTO.getEmail());
        toUser.setFirstname(userUpdateDTO.getFirstname());
        toUser.setLastname(userUpdateDTO.getLastname());
        toUser.setNickname(userUpdateDTO.getNickname());
        toUser.setBirthDate(userUpdateDTO.getBirthDate());
        toUser.setPassword(userUpdateDTO.getPassword()); // todo add passwordEncoder
        toUser.setPhone(userUpdateDTO.getPhone());
        return toUser;
    }
}
