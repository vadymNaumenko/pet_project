package de.pet_project.service;

import de.pet_project.convertor.UserDtoConvert;
import de.pet_project.domain.ConfirmationCode;
import de.pet_project.dto.user.*;
import de.pet_project.domain.User;
import de.pet_project.repository.ConfirmationCodeRepository;
import de.pet_project.repository.user.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserService /*implements UserDetailsService*/ {
    private final UserRepository userRepository;
    private final ImageService imageService;
    private final ConfirmationCodeRepository confirmationCodeRepository;
    private final UserDtoConvert userDtoConvert;

    public Page<UserReadDTO> findAll(Pageable pageable) {
        log.info("Fetching all users with pageable: {}", pageable);
        return userRepository.findAll(pageable).map(UserReadDTO::getInstance);
    }

    public Optional<UserDTO> findById(Integer id) {
        log.info("Fetching user by ID: {}", id);
        return userRepository.findById(id)
                .map(UserDTO::getInstance);
    }

    @Transactional
    public Optional<UserEditeDTO> update(UserEditeDTO userUpdateDTO) {
        log.info("Updating user: {}", userUpdateDTO);
        return userRepository.findById(userUpdateDTO.getId())
                .map(user -> userDtoConvert.convertToUser(userUpdateDTO, user))
                .map(userRepository::save)
                .map(userDtoConvert::convertToUserEditeDTO);
    }

    public boolean existsNickname(String nickname) {
        log.info("Checking if nickname exists: {}", nickname);
        return userRepository.existsByNickname(nickname);
    }

    @Transactional
    public boolean delete(Integer id) {
        log.info("Deleting user by ID: {}", id);
        return userRepository.findById(id)
                .map(user -> {
                    user.setState(User.State.DELETED);
                    userRepository.save(user);
                    return true;
                }).orElse(false);
    }

    @Transactional
    public boolean ban(Integer id) {
        log.info("Banning user by ID: {}", id);
        return userRepository.findById(id)
                .map(user -> {
                    user.setState(User.State.BANNED);
                    userRepository.save(user);
                    return true;
                }).orElse(false);
    }

    public Optional<byte[]> findAvatar(Integer id) {
        log.info("Fetching avatar for user ID: {}", id);
        return userRepository.findById(id)
                .map(User::getAvatar)
                .filter(StringUtils::hasText)
                .flatMap(imageService::get);
    }

    @SneakyThrows
    private String uploadImage(MultipartFile image) {
        if (!image.isEmpty()) {
            log.info("Uploading image: {}", image.getOriginalFilename());
            imageService.upload(image.getOriginalFilename(), image.getInputStream());
            return image.getOriginalFilename();
        }
        return null;
    }

    public boolean codIsValid(String code) {
        log.info("Validating confirmation code: {}", code);
        Optional<ConfirmationCode> confirmationCode = confirmationCodeRepository.findByCode(code);
        return confirmationCode.map(c -> {
            if (c.getExpiredDateTime().isBefore(LocalDateTime.now())) {
                c.getUser().setState(User.State.CONFIRMED);
                userRepository.save(c.getUser());
            }
            return true;
        }).orElse(false);
    }

    public Optional<UserDTO> findByEmail(String email) {
        log.info("Fetching user by email: {}", email);
        return userRepository.findByEmail(email)
                .map(userDtoConvert::convertToUserDTO);
    }

    public List<UserReadDTO> findByFilter(UserFilter filter) {
        log.info("Fetching users by filter: {}", filter);
        return userRepository.findByFilter(filter).stream()
                .map(userDtoConvert::convertToUserReadDto)
                .collect(Collectors.toList());
    }

    public Optional<UserThisDTO> findThisUser(UserDetails userDetails) {
        if (userDetails != null) {
            log.info("Fetching current user details for: {}", userDetails.getUsername());
            Optional<User> user = userRepository.findByEmail(userDetails.getUsername());
            Optional<UserThisDTO> userThisDTO = user.map(userDtoConvert::convertToUserThisDTO);
            if (userThisDTO.isPresent()) {
                userThisDTO.get().setRole(user.get().getRole().name());
                userThisDTO.get().setAvatar(imageService.getPath(user.get().getAvatar()));
                return userThisDTO;
            }
        }
        return Optional.empty();
    }

    public boolean setAvatar(MultipartFile multipartFile, UserDetails userDetails) {
        if (userDetails != null) {
            log.info("Setting avatar for user: {}", userDetails.getUsername());
            Optional<User> user = userRepository.findByEmail(userDetails.getUsername());
            if (user.isPresent()) {
                user.get().setAvatar(uploadImage(multipartFile));
                userRepository.save(user.get());
            }
            return true;
        }
        return false;
    }
}
