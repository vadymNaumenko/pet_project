package de.pet_project.controller.dto.address;

import de.pet_project.domain.Address;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AddressDTO {
    private Integer id;
    private String country;
    private String city;
    private String street;
    private String streetNumber;
    private Integer houseNumber;

    public static AddressDTO getInstance(Address address) {
        return new AddressDTO(address.getId(), address.getCountry(), address.getCity(),
                address.getStreet(), address.getStreetNumber(), address.getHouseNumber());
    }
}
