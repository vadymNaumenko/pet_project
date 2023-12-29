package de.pet_project.service;

import de.pet_project.controller.dto.address.AddressDTO;
import de.pet_project.controller.dto.address.CityDTO;
import de.pet_project.domain.Address;
import de.pet_project.repository.AddressRepository;
import liquibase.util.Validate;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class AddressService {
    private final AddressRepository addressRepository;

    public List<AddressDTO> findAll() {
        return addressRepository.findAll().stream().map(AddressDTO::getInstance).toList();
    }

    public List<AddressDTO> findAllAddressByCity(String city) {
        return addressRepository.findAllAddressByCity(city).stream().map(AddressDTO::getInstance).toList();
    }

    public List<CityDTO> findAllCity() {                 //TODO
        return addressRepository.findAllCity().stream().map(CityDTO::getInstance).toList();
    }

    @Transactional
    public AddressDTO save(AddressDTO addressDTO) {
        return Optional.of(fillAndSave(addressDTO, new Address())).orElseThrow();
    }

    @Transactional
    public AddressDTO update(AddressDTO addressDTO) {
        Validate.notNull(addressDTO.getId(), "Field id can't be null");
        Address address = addressRepository.findById(addressDTO.getId()).orElse(null);
        if (address != null) {
            return fillAndSave(addressDTO, address);
        }
        log.error("Item from address table not found, addressId={}", addressDTO.getId());
        return null;
    }

    @Transactional
    public AddressDTO delete(Integer addressId) {
        Address address = addressRepository.findById(addressId).orElse(null);
        if (address != null) {
            addressRepository.delete(address);
            return AddressDTO.getInstance(address);
        }
        log.error("Item from address table not found, addressId={}", addressId);
        return null;
    }

    private AddressDTO fillAndSave(AddressDTO addressDTO, Address address) {
        address.setCountry(addressDTO.getCountry());
        address.setCity(addressDTO.getCity());
        address.setStreet(addressDTO.getStreet());
        address.setStreetNumber(addressDTO.getStreetNumber());
        address.setHouseNumber(addressDTO.getHouseNumber());
        address = addressRepository.save(address);
        return AddressDTO.getInstance(address);
    }
}
