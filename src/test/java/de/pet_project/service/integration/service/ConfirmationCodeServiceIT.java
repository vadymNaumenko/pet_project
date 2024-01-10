package de.pet_project.service.integration.service;

import de.pet_project.domain.ConfirmationCode;
import de.pet_project.domain.User;
import de.pet_project.service.ConfirmationCodeService;
import de.pet_project.service.integration.annotation.IT;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Optional;

@IT
@RequiredArgsConstructor
public class ConfirmationCodeServiceIT {
    private final ConfirmationCodeService confirmationCodeService;

    @Test
    void findByCode() {
        String code = "e43fb7c0-16fb-4c4a-a329-8a731303d7c8";
        Optional<ConfirmationCode> confirm = confirmationCodeService.findByCode(code);
        assertTrue(confirm.isPresent());
        assertEquals(code, confirm.get().getCode());
    }

    @Test
    void isValid() {
        String code = "e43fb7c0-16fb-4c4a-a329-8a731303d7c8";
        assertTrue(confirmationCodeService.isValid(code));
        String notValid = "e43fb7c0-16-8a731303d7c8";
        assertFalse(confirmationCodeService.isValid(notValid));

    }

    @Test
    void setState() {
        String code = "5a9b1d06-2f3d-4571-95e4-827f6c4d0c49";
        Optional<ConfirmationCode> confirm = confirmationCodeService.findByCode(code);
        assertTrue(confirm.isPresent());
        assertEquals(code, confirm.get().getCode());
        assertEquals(User.State.NOT_CONFIRM, confirm.get().getUser().getState());

        assertTrue(confirmationCodeService.setState(code));
        confirm = confirmationCodeService.findByCode(code);

        assertTrue(confirm.isPresent());
        assertEquals(code, confirm.get().getCode());
        assertEquals(User.State.CONFIRMED, confirm.get().getUser().getState());
    }
}
