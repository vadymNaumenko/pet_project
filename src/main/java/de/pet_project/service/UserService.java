package de.pet_project.service;

import de.pet_project.convertor.UserDtoConvert;
import de.pet_project.domain.ConfirmationCode;
import de.pet_project.dto.user.*;
import de.pet_project.domain.User;
import de.pet_project.repository.ConfirmationCodeRepository;
import de.pet_project.repository.user.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.userdetails.UserDetails;
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
    public Optional<UserEditeDTO> update(UserEditeDTO userUpdateDTO, MultipartFile image) {
        return userRepository.findById(userUpdateDTO.getId())
                .map(user -> {
                    userUpdateDTO.setAvatar(uploadImage(image));
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
    private String uploadImage(MultipartFile image) {
        if (!image.isEmpty()) {
            imageService.upload(image.getOriginalFilename(), image.getInputStream());
            return image.getOriginalFilename();
        }
        return null;
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
        return userRepository.findByEmail(email)
                .map(userDtoConvert::convertToUserDTO);
    }

    public Page<UserReadDTO> findByFilter(UserFilter filter, Integer pageNumber, Integer pageSize) {
        Sort.TypedSort<User> sort = Sort.sort(User.class);
        sort.by(User::getFirstname);
        PageRequest pageable = PageRequest.of(pageNumber, pageSize, sort.descending());
        return userRepository.findByFilter(filter, pageable)
                .map(userDtoConvert::convertToUserReadDto);
    }

    public Optional<UserThisDTO> findThisUser(UserDetails userDetails) {
       Optional <User> user = userRepository.findByEmail(userDetails.getUsername());
        Optional<UserThisDTO> userThisDTO = user.map(userDtoConvert::convertToUserThisDTO);
        if (userThisDTO.isPresent()){
            userThisDTO.get().setRole(user.get().getRole().name());
            userThisDTO.get().setAvatar(imageService.getPath(user.get().getAvatar()));
            return userThisDTO;
        }
        return Optional.empty();
    }


//    public Page<UserReadDTO> findByFilter(UserFilter filter,Integer pageNumber,Integer pageSize) {
//        Sort.TypedSort<User> sort = Sort.sort(User.class);
//        sort.by(User::getFirstname);
//        PageRequest pageable = PageRequest.of(pageNumber,pageSize,sort.descending());
//        return userRepository.findByFilter(filter);
//    }

//    public Page<UserReadDTO> findByFilter(UserFilter filter,Integer pageNumber,Integer pageSize) {
//        Sort.TypedSort<User> sort = Sort.sort(User.class);
//        sort.by(User::getFirstname);
//        PageRequest pageable = PageRequest.of(pageNumber,pageSize,sort.descending());
////        return userRepository.findByFilter(filter);
//    }

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
