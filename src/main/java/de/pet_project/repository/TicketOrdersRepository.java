package de.pet_project.repository;

import de.pet_project.domain.TicketOrder;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TicketOrdersRepository extends JpaRepository<TicketOrder, Integer> {
    Page<TicketOrder> findAllTicketOrderByUserId(Integer id, Pageable pageable);
    boolean existsById(Integer id);
}
