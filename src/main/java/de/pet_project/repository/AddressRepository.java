package de.pet_project.repository;

import de.pet_project.domain.Address;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface AddressRepository extends JpaRepository<Address, Integer> {
    @Query("SELECT a FROM Address a WHERE a.city LIKE :city")
    List<Address> findAllAddressByCity(@Param("city") String city);

    @Query("SELECT DISTINCT a.city FROM Address a")
    List<String> findAllCity();
}
