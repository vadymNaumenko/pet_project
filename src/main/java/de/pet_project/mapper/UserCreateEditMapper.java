package de.pet_project.mapper;

import de.pet_project.dto.user.UserCreateDTO;
import de.pet_project.dto.user.UserDTO;
import de.pet_project.dto.user.UserEditeDTO;
import de.pet_project.domain.User;
import de.pet_project.service.ImageService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.function.Predicate;

@Component
@RequiredArgsConstructor
public class UserCreateEditMapper implements Mapper<UserCreateDTO, User> {

    private final ImageService imageService;

    @Override
    public User map(UserCreateDTO userCreateDTO) {
//        Optional.ofNullable(object.getImage())
//                .filter(Predicate.not(MultipartFile::isEmpty))
//                .ifPresent(image -> user.setImage(image.getOriginalFilename()));
        return new User(
                userCreateDTO.getNickname(), userCreateDTO.getEmail(),
                userCreateDTO.getPassword(), User.State.NOT_CONFIRM, LocalDateTime.now(),
                User.Role.USER);
    }

    public UserEditeDTO UserToUserEditeDTO(User user) {
        if (!user.getAvatar().isEmpty()) {

        }
        return new UserEditeDTO(
                user.getId(), user.getNickname(), //todo add defould image
                user.getFirstname(), user.getLastname(), "******", user.getBirthDate(), //todo password *****
                user.getEmail(), user.getPhone()
        );
    }

    public User map(UserEditeDTO userUpdateDTO, User toUser) {

        Optional.ofNullable(userUpdateDTO.getAvatar())
                .filter(Predicate.not(MultipartFile::isEmpty))
                .ifPresent(image -> toUser.setAvatar(image.getOriginalFilename()));

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

    public User mapUserDtoToUser(UserDTO userDTO, User toUser) {
        toUser.setAvatar(userDTO.getAvatar());
        toUser.setEmail(userDTO.getEmail());
        toUser.setFirstname(userDTO.getFirstname());
        toUser.setLastname(userDTO.getLastname());
        toUser.setNickname(userDTO.getNickname());
        toUser.setBirthDate(userDTO.getBirthDate());
        toUser.setPhone(userDTO.getPhone());
        toUser.setState(User.State.valueOf(userDTO.getState().toUpperCase()));
        toUser.setRole(User.Role.valueOf(userDTO.getRole().toUpperCase()));
        return toUser;
    }
}
