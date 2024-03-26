package com.example.OnlineBookStore.service;

import com.example.OnlineBookStore.model.Basket;
import com.example.OnlineBookStore.model.Book;
import com.example.OnlineBookStore.model.MyUser;
import com.example.OnlineBookStore.repository.BasketRepository;
import com.example.OnlineBookStore.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class BasketService {
    @Autowired
    private BasketRepository basketRepository;

    @Autowired
    private UserRepository userRepository;

    public Basket save(Basket basket) {
        return basketRepository.save(basket);
    }

    public boolean deleteBasket(Long id) {
        if (basketRepository.existsById(id)) {
            basketRepository.deleteById(id);
            return true;
        } else {
            return false;
        }
    }

    public Iterable<Basket> getAllBaskets() {
        return basketRepository.findAll();
    }

    public Iterable<Basket> getAllActiveBaskets() {
        return basketRepository.findAllBasketByIsActiveTrue();
    }

    public Iterable<Basket> getBasketsByUserId(Long id) {
        return basketRepository.findBasketByMyUserId(id);
    }

    public Iterable<Basket> getAllMyUserBaskets(String username) {
        Optional<MyUser> myUser = userRepository.findByUsername(username);
        return basketRepository.findBasketByMyUserId(myUser.orElseThrow().getId());
    }

    public Iterable<Basket> getMyUserActiveBasket(String username) {
        Optional<MyUser> myUser = userRepository.findByUsername(username);
        return basketRepository.findBasketByMyUserIdAndIsActiveTrue(myUser.orElseThrow().getId());
    }

    public Page<Basket> getAllBasketsPage(int page, int size) {
        return basketRepository.findAll(PageRequest.of(page, size));
    }

    public Page<Basket> getAllBasketsPageSortByField(int page, int size, String field) {
        return basketRepository.findAll(PageRequest.of(page, size, Sort.by(field)));
    }

}
