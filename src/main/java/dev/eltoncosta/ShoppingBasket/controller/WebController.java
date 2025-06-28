package dev.eltoncosta.ShoppingBasket.controller;

import dev.eltoncosta.ShoppingBasket.service.ProductService;
import dev.eltoncosta.ShoppingBasket.service.BasketService;
import dev.eltoncosta.ShoppingBasket.entity.Basket;
import dev.eltoncosta.ShoppingBasket.entity.PaymentMethod;
import dev.eltoncosta.ShoppingBasket.controller.request.PaymentRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class WebController {
    @Autowired
    private ProductService productService;
    @Autowired
    private BasketService basketService;

    @GetMapping("/")
    public String home() {
        return "home";
    }

    @GetMapping("/baskets")
    public String listBaskets(Model model) {
        java.util.List<Basket> baskets = basketService.getAll();
        model.addAttribute("baskets", baskets);
        return "baskets";
    }

    @GetMapping("/baskets/new")
    public String newBasketForm(Model model) {
        model.addAttribute("products", productService.getAllProducts());
        return "new-basket";
    }

    @PostMapping("/baskets")
    public String createBasket(@RequestParam Long customerId, @RequestParam(value = "productId", required = false) Long[] productIds, @RequestParam(value = "quantity", required = false) Integer[] quantities) {
        java.util.List<dev.eltoncosta.ShoppingBasket.controller.request.ProductRequest> products = new java.util.ArrayList<>();
        if (productIds != null && quantities != null) {
            for (int i = 0; i < productIds.length; i++) {
                if (quantities[i] != null && quantities[i] > 0) {
                    products.add(new dev.eltoncosta.ShoppingBasket.controller.request.ProductRequest(productIds[i], quantities[i]));
                }
            }
        }
        dev.eltoncosta.ShoppingBasket.controller.request.BasketRequest basketRequest =
            new dev.eltoncosta.ShoppingBasket.controller.request.BasketRequest(customerId, products);
        basketService.createBasket(basketRequest);
        return "redirect:/baskets";
    }

    @GetMapping("/baskets/{id}")
    public String viewBasket(@PathVariable String id, Model model) {
        Basket basket = basketService.getBasketById(id);
        model.addAttribute("basket", basket);
        model.addAttribute("products", productService.getAllProducts());
        return "basket-details";
    }

    @GetMapping("/baskets/{id}/payment")
    public String paymentForm(@PathVariable String id, Model model) {
        model.addAttribute("basketId", id);
        model.addAttribute("methods", PaymentMethod.values());
        return "payment";
    }

    @PostMapping("/baskets/{id}/payment")
    public String payBasket(@PathVariable String id, @RequestParam PaymentMethod paymentMethod) {
        PaymentRequest paymentRequest = new PaymentRequest(paymentMethod);
        basketService.payBasket(id, paymentRequest);
        return "redirect:/baskets/" + id;
    }

    @PostMapping("/baskets/{id}/add-product")
    public String addProductToBasket(@PathVariable String id, @RequestParam Long productId, @RequestParam int quantity) {
        Basket basket = basketService.getBasketById(id);
        // Busca o produto pelo id
        var platziProduct = productService.getProductById(productId);
        // Verifica se o produto já está na cesta
        boolean found = false;
        for (var prod : basket.getProducts()) {
            if (prod.getId().equals(productId)) {
                prod.setQuantity(prod.getQuantity() + quantity);
                found = true;
                break;
            }
        }
        if (!found) {
            basket.getProducts().add(
                dev.eltoncosta.ShoppingBasket.entity.Product.builder()
                    .id(platziProduct.id())
                    .title(platziProduct.title())
                    .price(platziProduct.price())
                    .quantity(quantity)
                    .build()
            );
        }
        basket.calculateTotalPrice();
        basketService.updateBasket(id, new dev.eltoncosta.ShoppingBasket.controller.request.BasketRequest(basket.getClient(), basket.getProducts().stream().map(p -> new dev.eltoncosta.ShoppingBasket.controller.request.ProductRequest(p.getId(), p.getQuantity())).toList()));
        return "redirect:/baskets/" + id;
    }
}
