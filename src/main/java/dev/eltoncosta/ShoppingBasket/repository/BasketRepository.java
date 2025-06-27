package dev.eltoncosta.ShoppingBasket.repository;

import dev.eltoncosta.ShoppingBasket.entity.Basket;
import dev.eltoncosta.ShoppingBasket.entity.Status;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface BasketRepository extends MongoRepository<Basket, String> {

    Optional<Basket> findByClientAndStatus(Long client, Status status);

}
