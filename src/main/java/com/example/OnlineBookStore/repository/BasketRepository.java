package com.example.OnlineBookStore.repository;

import com.example.OnlineBookStore.model.Basket;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BasketRepository extends CrudRepository<Basket, Long> {
    Iterable<Basket> findBasketByMyUserId(Long userId);

    Page<Basket> findAll(Pageable pageable);
    Iterable<Basket> findBasketByMyUserIdAndIsActiveTrue(@Param("myUserId") Long myUserId);

    Long findBasketIdByMyUserIdAndIsActiveTrue(@Param("myUserId") Long myUserId);

    Iterable<Basket> findAllBasketByIsActiveTrue();
}
