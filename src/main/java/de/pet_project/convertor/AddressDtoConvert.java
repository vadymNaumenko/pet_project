package de.pet_project.convertor;

import de.pet_project.domain.Address;
import de.pet_project.dto.address.AddressDTO;
import de.pet_project.dto.address.CityDTO;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AddressDtoConvert {
    private final ModelMapper modelMapper;

    public AddressDTO convertToAddressDTO(Address address){
        return modelMapper.map(address, AddressDTO.class);
    }

    public CityDTO convertToCityDTO(String address){
        return new CityDTO(address);
    }

    public Address convertToAddress(AddressDTO addressDTO){
        return modelMapper.map(addressDTO, Address.class);
    }
}
