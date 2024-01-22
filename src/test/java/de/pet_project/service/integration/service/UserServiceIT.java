package de.pet_project.service.integration.service;

import de.pet_project.domain.User;
import de.pet_project.dto.user.UserDTO;
import de.pet_project.dto.user.UserReadDTO;
import de.pet_project.service.UserService;
import de.pet_project.service.integration.annotation.IT;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@IT
@RequiredArgsConstructor
public class UserServiceIT {
    private final UserService userService;

    @Test
    void findAllByPageable() {
        Sort.TypedSort<User> sort = Sort.sort(User.class);
        PageRequest pageable = PageRequest.of(0, 18, sort);
        Page<UserReadDTO> actual = userService.findAll(pageable);
        assertFalse(actual.getContent().isEmpty());
        assertEquals(5, actual.getContent().size());
    }

    @Test
    void findByEmail() {
        String email = "vlad@gmail.com";
        Optional<UserDTO> actual = userService.findByEmail(email);
        assertTrue(actual.isPresent());
        assertEquals(email, actual.get().getEmail());
    }

    @Test
    void findById() {
        String email = "kate@gmail.com";
        Optional<UserDTO> exp = userService.findByEmail(email);
        assertTrue(exp.isPresent());
        Optional<UserDTO> actual = userService.findById(exp.get().getId());
        assertTrue(actual.isPresent());
        assertEquals(exp, actual);

    }

    //    @Test todo make test for method update
//    void update() {
//
//    }
    @Test
    void existsNickname() {
        assertTrue(userService.existsNickname("Stalker"));
        assertFalse(userService.existsNickname("Stalkerasdfasdfasd"));
    }

    @Test
    void delete() {

    }

    @Test
    void ban() {
        Optional<UserDTO> actual = userService.findByEmail("sveta@gmail.com");
        assertTrue(actual.isPresent());
        assertEquals(User.State.NOT_CONFIRM, User.State.valueOf(actual.get().getState()));
        userService.ban(actual.get().getId());
        actual = userService.findById(actual.get().getId());
        assertTrue(actual.isPresent());
        assertEquals(User.State.BANNED, User.State.valueOf(actual.get().getState()));
    }

//    public Optional<byte[]> findAvatar(Integer id) { todo make test for method findAvatar
//
//    }

//  void uploadImage(MultipartFile image) {
//
//    }

}
