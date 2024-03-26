package com.example.OnlineBookStore.repository;

import com.example.OnlineBookStore.model.Basket;
import com.example.OnlineBookStore.model.BasketBook;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BasketBookRepository extends CrudRepository<BasketBook, Long> {

    Page<BasketBook> findAll(Pageable pageable);
    Iterable<BasketBook> findByBasketId(Long basketId);
    Iterable<BasketBook> findByBookId(Long bookId);

    boolean deleteByBasketIdAndBookId(Long basketId, Long bookId);

}
