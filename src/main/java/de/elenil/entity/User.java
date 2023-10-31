package de.elenil.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {
    private Long id;
    private String name;
    private String Surname;
    private String email;
    private String phone;

    //        История заказов (может быть отношение "многие-к- 1" между клиентами и заказами)
   // private List<Order> orderList = new ArrayList<>();

}
