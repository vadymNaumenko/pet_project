package de.pet_project.service;

import de.pet_project.controller.dto.user.UserCreateDTO;
import de.pet_project.controller.dto.user.UserDTO;
import de.pet_project.controller.dto.user.UserEditeDTO;
import de.pet_project.controller.dto.user.UserReadDTO;
import de.pet_project.domain.User;
import de.pet_project.mapper.UserCreateEditMapper;
import de.pet_project.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final UserCreateEditMapper userCreateEditMapper;


    public Page<UserReadDTO> findAll(Pageable pageable) {

        return userRepository.findAll(pageable).map(UserReadDTO::getInstance);

    }

    //todo this method for admin
    public UserDTO findById(Integer id) {
        return userRepository.findAll().stream()
                .filter(user -> user.getId().equals(id))
                .map(UserDTO::getInstance)
                .findFirst().orElse(null);
    }

    @Transactional
    public UserReadDTO save(UserCreateDTO userCreateDTO) {
        return Optional.of(userCreateDTO)
                .map(userCreateEditMapper::map)
                .map(userRepository::save)
                .map(UserReadDTO::getInstance)
                .orElseThrow();
    }

    @Transactional
    public Optional<UserEditeDTO> update(UserEditeDTO userUpdateDTO) {
        return userRepository.findById(userUpdateDTO.getId())
                .map(user -> userCreateEditMapper.map(userUpdateDTO, user))
                .map(userRepository::save)
                .map(UserEditeDTO::getInstance);

    }
    public boolean existsNickname(String nickname){
        return userRepository.existsByNickname(nickname);
    }

    @Transactional
    public boolean delete(Integer id) {
        return userRepository.findById(id)
                .map(user -> {
                    user.setState(User.State.DELETED);
                    userRepository.save(user);
                    return true;
                }).orElse(false);
    }

//    public Optional<UserDTO> update(Integer id, UserDTO user){
//        return userRepository.findById(id)
//                .map()
//    }

}
