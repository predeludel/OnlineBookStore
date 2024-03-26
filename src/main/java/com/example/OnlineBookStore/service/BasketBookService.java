package com.example.OnlineBookStore.service;

import com.example.OnlineBookStore.model.Basket;
import com.example.OnlineBookStore.model.BasketBook;
import com.example.OnlineBookStore.model.MyUser;
import com.example.OnlineBookStore.repository.BasketBookRepository;
import com.example.OnlineBookStore.repository.BasketRepository;
import com.example.OnlineBookStore.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class BasketBookService {

    @Autowired
    private BasketBookRepository basketBookRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BasketRepository basketRepository;

    public BasketBook save(BasketBook basketBook) {
        return basketBookRepository.save(basketBook);
    }


    public boolean deleteBasketBookById(Long bookId, String username) {
        Optional<MyUser> myUser = userRepository.findByUsername(username);
        Long basket = basketRepository.findBasketIdByMyUserIdAndIsActiveTrue(myUser.orElseThrow().getId());
        return basketBookRepository.deleteByBasketIdAndBookId(bookId, basket);

    }

    public Iterable<BasketBook> getAllBasketsBooks() {
        return basketBookRepository.findAll();
    }

    public Iterable<BasketBook> getBasketBookMyUser(String username) {
        Optional<MyUser> myUser = userRepository.findByUsername(username);
        Long basket = basketRepository.findBasketIdByMyUserIdAndIsActiveTrue(myUser.orElseThrow().getId());
        return basketBookRepository.findByBasketId(basket);
    }

    public Iterable<BasketBook> getAllBasketBooksByBasketId(Long id) {
        return basketBookRepository.findByBasketId(id);
    }

    public Iterable<BasketBook> getAllBasketBooksByBookId(Long id) {
        return basketBookRepository.findByBookId(id);
    }

    public Page<BasketBook> getAllBasketBooksPage(int page, int size) {
        return basketBookRepository.findAll(PageRequest.of(page, size));
    }

    public Page<BasketBook> getAllBasketBooksPageSortByField(int page, int size, String field) {
        return basketBookRepository.findAll(PageRequest.of(page, size, Sort.by(field)));
    }


}
