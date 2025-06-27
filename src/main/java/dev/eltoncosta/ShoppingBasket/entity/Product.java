package dev.eltoncosta.ShoppingBasket.entity;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Builder
public class Product {

    private Long id;
    private String title;
    private BigDecimal price;
    private Integer quantity;

}
