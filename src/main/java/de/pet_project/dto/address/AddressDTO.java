package de.pet_project.dto.address;

import de.pet_project.domain.Address;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AddressDTO {
    private Integer id;
    private String country;
    private String city;
    private String street;
    private String streetNumber;
    private Integer houseNumber;
    private Boolean isDeleted;
}
