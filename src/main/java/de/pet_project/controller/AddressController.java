package de.pet_project.controller;

import de.pet_project.dto.address.AddressDTO;
import de.pet_project.dto.address.CityDTO;
import de.pet_project.service.AddressService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/address")
@Slf4j
public class AddressController {
    private final AddressService addressService;

    @GetMapping("/addresses")
    public List<AddressDTO> findAll() {
        log.info("Fetching all addresses");
        return addressService.findAll();
    }

    @GetMapping("/{city}")
    public List<AddressDTO> findAllAddressByCity(@PathVariable String city) {
        log.info("Fetching all addresses for city: {}", city);
        return addressService.findAllAddressByCity(city);
    }

    @GetMapping("/cities")
    public List<CityDTO> findAllCity() {
        log.info("Fetching all cities");
        return addressService.findAllCity();
    }

    @PostMapping()
    public AddressDTO create(@RequestBody AddressDTO addressDTO) {
        log.info("Creating new address: {}", addressDTO);
        return addressService.save(addressDTO);
    }

    @PutMapping()
    public ResponseEntity<AddressDTO> update(@RequestBody AddressDTO addressDTO) {
        log.info("Updating address with ID: {}", addressDTO.getId());
        AddressDTO response = addressService.update(addressDTO);
        if (response == null) {
            log.warn("Failed to update the address");
            return ResponseEntity.notFound().build();
        }
        log.info("Address updated successfully");
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<AddressDTO> delete(@PathVariable Integer id) {
        log.info("Deleting address with ID: {}", id);
        AddressDTO response = addressService.delete(id);
        if (response == null) {
            log.warn("Failed to delete the address");
            return ResponseEntity.notFound().build();
        }
        log.info("Address deleted successfully");
        return ResponseEntity.ok(response);
    }
}
