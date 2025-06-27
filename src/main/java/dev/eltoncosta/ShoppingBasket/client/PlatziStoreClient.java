package dev.eltoncosta.ShoppingBasket.client;

import dev.eltoncosta.ShoppingBasket.client.response.PlatziProductResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@FeignClient(name = "PlatziStoreClient", url = "${basket.client.platzi}")
public interface PlatziStoreClient {

    @GetMapping("/products")
    List<PlatziProductResponse> getAllProducts();

    @GetMapping("/products/{id}")
    PlatziProductResponse getAllProductsById(@PathVariable Long id);

}
