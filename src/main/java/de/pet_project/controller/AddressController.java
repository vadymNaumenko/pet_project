package de.pet_project.controller;

import de.pet_project.dto.address.AddressDTO;
import de.pet_project.dto.address.CityDTO;
import de.pet_project.service.AddressService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/address")
public class AddressController {
    public final AddressService addressService;

    @GetMapping("/all/addresses")
    public List<AddressDTO> findAll() {
        return addressService.findAll();
    }

    @GetMapping("/address{city}")
    public List<AddressDTO> findAllAddressByCity(@PathVariable String city) {
        return addressService.findAllAddressByCity(city);
    }

    @GetMapping("/all/city")
    public List<CityDTO> findAllCity() {;
        return addressService.findAllCity();
    }

    @PostMapping("/createNew")
    public AddressDTO create(@RequestBody AddressDTO addressDTO) {
        return addressService.save(addressDTO);
    }

    @PutMapping("/update")
    public ResponseEntity<AddressDTO> update(@RequestBody AddressDTO addressDTO) {
        AddressDTO response = addressService.update(addressDTO);
        if (response == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(response);
    }
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<AddressDTO> delete(@PathVariable Integer id) {
        AddressDTO response = addressService.delete(id);
        if (response == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(response);
    }
}
