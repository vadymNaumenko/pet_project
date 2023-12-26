package de.pet_project.dto.address;

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
