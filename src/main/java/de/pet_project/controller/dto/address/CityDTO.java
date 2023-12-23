package de.pet_project.controller.dto.address;

import de.pet_project.domain.Address;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CityDTO {
    private String city;

    public static CityDTO getInstance(String address) {
        return new CityDTO(address);
    }
}
