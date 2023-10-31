package de.elenil.entity;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Order {
    private Long id;
    private int user_id;


    // add Статус заказа (например, оформлен, оплачен, отправлен, доставлен)
    private List<Product> products = new ArrayList<>();  // Список товаров в заказе (связь с таблицей товаров)
    private LocalDateTime localDateTime;
}
