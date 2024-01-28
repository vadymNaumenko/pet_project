package de.pet_project.service;

import de.pet_project.convertor.AddressDtoConvert;
import de.pet_project.domain.enums.game.State;
import de.pet_project.dto.address.AddressDTO;
import de.pet_project.dto.address.CityDTO;
import de.pet_project.domain.Address;
import de.pet_project.repository.AddressRepository;
import liquibase.util.Validate;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class AddressService {
    private final AddressRepository addressRepository;
    private final AddressDtoConvert addressDtoConverter;

    public List<AddressDTO> findAll() {
        return addressRepository.findAll().stream()
                .map(addressDtoConverter::convertToAddressDTO).toList();
    }

    public List<AddressDTO> findAllAddressByCity(String city) {
        return addressRepository.findAllAddressByCity(city).stream()
                .map(addressDtoConverter::convertToAddressDTO).toList();
    }

    public List<CityDTO> findAllCity() {                 //TODO
        return addressRepository.findAllCity().stream()
                .map(addressDtoConverter::convertToCityDTO).toList();
    }

    @Transactional
//    @PreAuthorize("hasRole('ADMIN')")
    public AddressDTO save(AddressDTO addressDTO) {
        return Optional.of(addressDtoConverter.convertToAddress(addressDTO))
                .map(addressRepository::save).map(addressDtoConverter::convertToAddressDTO).orElseThrow();
    }

    @Transactional
//    @PreAuthorize("hasRole('ADMIN')")
    public AddressDTO update(AddressDTO addressDTO) {
        Validate.notNull(addressDTO.getId(), "Field id can't be null");
        Address address = addressRepository.findById(addressDTO.getId()).orElse(null);
        if (address != null) {
            return Optional.of(addressDtoConverter.convertToAddress(addressDTO))
                    .map(addressRepository::save).map(addressDtoConverter::convertToAddressDTO).orElseThrow();
        }
        log.error("Item from address table not found, addressId={}", addressDTO.getId());
        return null;
    }

    @Transactional
//    @PreAuthorize("hasRole('ADMIN')")
    public AddressDTO delete(Integer addressId) {
        Address address = addressRepository.findById(addressId).orElse(null);
        if (address != null) {
            address.setIsDeleted(true);
            addressRepository.save(address);
            return addressDtoConverter.convertToAddressDTO(address);
        }
        log.error("Item from address table not found, addressId={}", addressId);
        return null;
    }
}