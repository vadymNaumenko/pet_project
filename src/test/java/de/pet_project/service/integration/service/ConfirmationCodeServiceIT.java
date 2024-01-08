package de.pet_project.service.integration.service;

import de.pet_project.service.ConfirmationCodeService;
import de.pet_project.service.integration.annotation.IT;
import lombok.RequiredArgsConstructor;

@IT
@RequiredArgsConstructor
public class ConfirmationCodeServiceIT {
    private final ConfirmationCodeService confirmationCodeService;
}
