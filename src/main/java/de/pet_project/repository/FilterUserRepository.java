package de.pet_project.repository;

import de.pet_project.domain.User;
import de.pet_project.dto.user.UserFilter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface FilterUserRepository {
    Page<User> findByFilter(UserFilter filter, Pageable pageable);
}
