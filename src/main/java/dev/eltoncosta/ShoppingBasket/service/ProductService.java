package dev.eltoncosta.ShoppingBasket.service;

import dev.eltoncosta.ShoppingBasket.client.PlatziStoreClient;
import dev.eltoncosta.ShoppingBasket.client.response.PlatziProductResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class ProductService {

    private final PlatziStoreClient client;

    @Cacheable(value = "products")
    public List<PlatziProductResponse> getAllProducts() {
        log.info("Getting all products");
        return client.getAllProducts();
    }

    @Cacheable(value = "product", key = "#productId")
    public PlatziProductResponse getProductById(Long productId) {
        log.info("Getting product with id: {}", productId);
        return client.getAllProductsById(productId);
    }

}
