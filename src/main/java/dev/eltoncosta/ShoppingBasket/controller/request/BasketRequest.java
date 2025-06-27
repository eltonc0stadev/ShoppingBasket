package dev.eltoncosta.ShoppingBasket.controller.request;

import dev.eltoncosta.ShoppingBasket.entity.PaymentMethod;
import lombok.Builder;

import java.util.List;

public record BasketRequest(Long clientId, List<ProductRequest> products) {
}
