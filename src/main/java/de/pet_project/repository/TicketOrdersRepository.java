package de.pet_project.repository;

import de.pet_project.domain.TicketOrders;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TicketOrdersRepository extends JpaRepository<TicketOrders,Integer> {

}
