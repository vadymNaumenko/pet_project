package de.pet_project.dto.address;

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
    private boolean isDeleted;
}
