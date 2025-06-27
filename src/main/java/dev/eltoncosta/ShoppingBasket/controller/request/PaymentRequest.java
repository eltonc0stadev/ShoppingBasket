package dev.eltoncosta.ShoppingBasket.controller.request;

import dev.eltoncosta.ShoppingBasket.entity.PaymentMethod;

public record PaymentRequest(PaymentMethod paymentMethod) {
}
