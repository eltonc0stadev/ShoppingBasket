package dev.eltoncosta.ShoppingBasket.client.response;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

public record PlatziProductResponse(Long id, String title, BigDecimal price) implements Serializable {
}
