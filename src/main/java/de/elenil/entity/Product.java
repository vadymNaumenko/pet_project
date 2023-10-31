package de.elenil.entity;

import de.elenil.entity.enums.ProductCategory;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Product {
    private Long id;
    private String name;
    private Set<ProductCategory> category = new HashSet<>();
    private boolean price;
    private List<Image> images = new ArrayList<>();


}
//    Товары (Products):
//
//        ID товара
//        Название товара
//        Бренд
//        Категория (например, мужская одежда, женская одежда, обувь и т.д.)
//        Цена
//        Описание товара
//        Ссылка на изображение товара
//        Складское количество