package de.pet_project.service;

import de.pet_project.convertor.UserDtoConvert;
import de.pet_project.domain.ConfirmationCode;
import de.pet_project.dto.user.UserDTO;
import de.pet_project.dto.user.UserEditeDTO;
import de.pet_project.dto.user.UserReadDTO;
import de.pet_project.domain.User;
import de.pet_project.repository.ConfirmationCodeRepository;
import de.pet_project.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService /*implements UserDetailsService*/ {
    private final UserRepository userRepository;
    private final ImageService imageService;
    private final ConfirmationCodeRepository confirmationCodeRepository;
    private final UserDtoConvert userDtoConvert;


    public Page<UserReadDTO> findAll(Pageable pageable) {

        return userRepository.findAll(pageable).map(UserReadDTO::getInstance);

    }

//    @PreAuthorize("hasRole('ADMIN')")
    public Optional<UserDTO> findById(Integer id) {
        return userRepository.findAll().stream()
                .filter(user -> user.getId().equals(id))
                .map(UserDTO::getInstance).findFirst();
    }

    @Transactional
    public Optional<UserEditeDTO> update(UserEditeDTO userUpdateDTO) {
        return userRepository.findById(userUpdateDTO.getId())
                .map(user -> {
                    uploadImage(userUpdateDTO.getAvatar());
                    return userDtoConvert.convertToUser(userUpdateDTO, user);
                })
                .map(userRepository::save)
                .map(userDtoConvert::convertToUserEditeDTO);

    }

    public boolean existsNickname(String nickname) { // todo mast be add check nickname
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
    @Transactional
//    @PreAuthorize("hasRole('ADMIN')")
    public boolean ban(Integer id) {
        return userRepository.findById(id)
                .map(user -> {
                    user.setState(User.State.BANNED);
                    userRepository.save(user);
                    return true;
                }).orElse(false);
    }
    public Optional<byte[]> findAvatar(Integer id) {
        return userRepository.findById(id)
                .map(User::getAvatar)
                .filter(StringUtils::hasText)
                .flatMap(imageService::get);
    }

    @SneakyThrows
    private void uploadImage(MultipartFile image) {
        if (!image.isEmpty()) {
            imageService.upload(image.getOriginalFilename(), image.getInputStream());
        }
    }

    public boolean codIsValid(String code) {
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
        return  userRepository.findByEmail(email)
                .map(userDtoConvert::convertToUserDTO);
    }

//    @Override
//    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
//
//        return userRepository.findByEmail(email) // todo nickname
//                .map(user -> new org.springframework.security.core.userdetails.User(
//                        user.getUsername(),
//                        user.getPassword(),
//                        Collections.singleton(user.getRole())
//                )).orElseThrow(() -> new UsernameNotFoundException("Failed to retrieve user with email: " + email));
//    }

//    public Optional<UserDTO> update(Integer id, UserDTO user){
//        return userRepository.findById(id)
//                .map()
//    }

}
