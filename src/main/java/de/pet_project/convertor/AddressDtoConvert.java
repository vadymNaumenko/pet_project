package de.pet_project.convertor;

import de.pet_project.domain.Address;
import de.pet_project.domain.enums.State;
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
        AddressDTO addressDTO = modelMapper.map(address, AddressDTO.class);
        addressDTO.setState(address.getState().name());
        return addressDTO;
    }

    public CityDTO convertToCityDTO(String address){
        return modelMapper.map(address, CityDTO.class);
    }

    public AddressDTO convertToAddress(AddressDTO addressDTO){
        Address address = modelMapper.map(addressDTO, Address.class);
        address.setState(State.valueOf(addressDTO.getState()));
        return convertToAddressDTO(address);
    }
}
