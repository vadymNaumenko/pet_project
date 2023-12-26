package de.pet_project.repository;

import de.pet_project.domain.TicketOrder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TicketOrdersRepository extends JpaRepository<TicketOrder,Integer> {

}
