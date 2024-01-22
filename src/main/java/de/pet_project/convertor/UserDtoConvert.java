package de.pet_project.convertor;

import de.pet_project.domain.User;
import de.pet_project.dto.user.UserDTO;
import de.pet_project.dto.user.UserEditeDTO;
import de.pet_project.dto.user.UserReadDTO;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.util.Optional;
import java.util.function.Predicate;

@Component
@RequiredArgsConstructor
public class UserDtoConvert {

    private final ModelMapper modelMapper;

    public UserReadDTO convertToUserReadDto(User user){
        return modelMapper.map(user,UserReadDTO.class);
    }

    public User convertToUser(UserEditeDTO userUpdateDTO, User toUser) {

//        Optional.ofNullable(userUpdateDTO.getAvatar())
//                .filter(Predicate.not(MultipartFile::isEmpty))
//                .ifPresent(image -> toUser.setAvatar(image.getOriginalFilename()));

        if (userUpdateDTO.getEmail() != null)
            toUser.setEmail(userUpdateDTO.getEmail());
        if (userUpdateDTO.getFirstname() != null)
            toUser.setFirstname(userUpdateDTO.getFirstname());
        if (userUpdateDTO.getLastname() != null)
            toUser.setLastname(userUpdateDTO.getLastname());
        if (userUpdateDTO.getNickname() != null)
            toUser.setNickname(userUpdateDTO.getNickname());
        toUser.setBirthDate(userUpdateDTO.getBirthDate());
        if (userUpdateDTO.getPassword() != null)
            toUser.setPassword(userUpdateDTO.getPassword()); // todo add passwordEncoder
        if (userUpdateDTO.getPhone() != null)
            toUser.setPhone(userUpdateDTO.getPhone());

        return toUser;
    }

    public UserEditeDTO convertToUserEditeDTO(User user) {
        if (!user.getAvatar().isEmpty()) {

        }
        return new UserEditeDTO(
                user.getId(), user.getNickname(), //todo add defould image
                user.getFirstname(), user.getLastname(), "******", user.getBirthDate(), //todo password *****
                user.getEmail(), user.getPhone()
        );
    }
    public UserDTO convertToUserDTO(User user) {
       UserDTO userDTO = modelMapper.map(user, UserDTO.class);
       userDTO.setRole(user.getRole().name());
       userDTO.setState(user.getState().name());
       return userDTO;
    }

}
