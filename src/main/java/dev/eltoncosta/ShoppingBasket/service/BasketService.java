package dev.eltoncosta.ShoppingBasket.service;

import dev.eltoncosta.ShoppingBasket.client.response.PlatziProductResponse;
import dev.eltoncosta.ShoppingBasket.controller.request.BasketRequest;
import dev.eltoncosta.ShoppingBasket.controller.request.PaymentRequest;
import dev.eltoncosta.ShoppingBasket.entity.Basket;
import dev.eltoncosta.ShoppingBasket.entity.Product;
import dev.eltoncosta.ShoppingBasket.entity.Status;
import dev.eltoncosta.ShoppingBasket.exceptions.BusinessException;
import dev.eltoncosta.ShoppingBasket.exceptions.DataNotFoundException;
import dev.eltoncosta.ShoppingBasket.repository.BasketRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class BasketService {

    private final BasketRepository basketRepository;
    private final ProductService productService;

    public Basket createBasket(BasketRequest basketRequest) {

        basketRepository.findByClientAndStatus(basketRequest.clientId(), Status.OPEN)
                .ifPresent(basket -> {
                    throw new BusinessException("There is already an open basket for this client.");
                });

        List<Product> products = getProducts(basketRequest);

        Basket basket = Basket.builder()
                .client(basketRequest.clientId())
                .status(Status.OPEN)
                .products(products)
                .build();
        basket.calculateTotalPrice();
        return basketRepository.save(basket);
    }

    private List<Product> getProducts(BasketRequest basketRequest) {
        List<Product> products = new ArrayList<>();
        basketRequest.products().forEach(product -> {
            PlatziProductResponse productById = productService.getProductById(product.id());

            products.add(
                    Product.builder()
                            .id(productById.id())
                            .title(productById.title())
                            .price(productById.price())
                            .quantity(product.quantity())
                            .build()
            );
        });
        return products;
    }

    public Basket getBasketById(String id) {
        return basketRepository.findById(id)
                .orElseThrow(() -> new DataNotFoundException("Basket not found with id: " + id));
    }

    public Basket updateBasket(String id, BasketRequest basketRequest) {
        Basket basket = getBasketById(id);

        if (!basket.getStatus().equals(Status.OPEN)) {
            throw new BusinessException("Cannot update a closed basket.");
        }

        List<Product> products = getProducts(basketRequest);

        basket.setProducts(products);
        basket.calculateTotalPrice();
        return basketRepository.save(basket);
    }

    public Basket payBasket(String id, PaymentRequest request) {
        Basket basket = getBasketById(id);

        if (!basket.getStatus().equals(Status.OPEN)) {
            throw new IllegalStateException("Cannot pay a closed basket.");
        }

        basket.setStatus(Status.CLOSED);
        basket.setPaymentMethod(request.paymentMethod());
        return basketRepository.save(basket);
    }

    public void deleteBasket(String id) {
        Basket basket = getBasketById(id);
        if (!basket.getStatus().equals(Status.OPEN)) {
            throw new BusinessException("Cannot close a closed basket.");
        }
        basket.setStatus(Status.CLOSED);
        basketRepository.delete(basket);
    }

    public List<Basket> getAll() {
        return basketRepository.findAll();
    }

}
