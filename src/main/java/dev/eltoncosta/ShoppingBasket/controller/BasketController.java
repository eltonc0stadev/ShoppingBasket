package dev.eltoncosta.ShoppingBasket.controller;

import dev.eltoncosta.ShoppingBasket.controller.request.BasketRequest;
import dev.eltoncosta.ShoppingBasket.controller.request.PaymentRequest;
import dev.eltoncosta.ShoppingBasket.entity.Basket;
import dev.eltoncosta.ShoppingBasket.service.BasketService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/basket")
@RequiredArgsConstructor
public class BasketController {

    private final BasketService basketService;

    @GetMapping("/{id}")
    public ResponseEntity<Basket> getBasketById(@PathVariable String id) {
        Basket basket = basketService.getBasketById(id);
        return ResponseEntity.ok(basket);
    }

    @PostMapping
    public ResponseEntity<Basket> createBasket(@RequestBody BasketRequest basket) {
        Basket createdBasket = basketService.createBasket(basket);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdBasket);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Basket> updateBasket(@PathVariable String id, @RequestBody BasketRequest basket) {
        return ResponseEntity.ok(basketService.updateBasket(id, basket));
    }

    @PutMapping("/{id}/payment")
    public ResponseEntity<Basket> payBasket(@PathVariable String id, @RequestBody PaymentRequest request) {
        return ResponseEntity.ok(basketService.payBasket(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBasket(@PathVariable String id) {
        basketService.deleteBasket(id);
        return ResponseEntity.noContent().build();
    }

}
