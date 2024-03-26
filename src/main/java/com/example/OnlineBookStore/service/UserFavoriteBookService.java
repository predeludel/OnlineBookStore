package com.example.OnlineBookStore.service;

import com.example.OnlineBookStore.model.MyUser;
import com.example.OnlineBookStore.model.UserFavoriteBook;
import com.example.OnlineBookStore.repository.UserFavoriteBookRepository;
import com.example.OnlineBookStore.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class UserFavoriteBookService {

    @Autowired
    private UserFavoriteBookRepository userFavoriteBookRepository;

    @Autowired
    private UserRepository userRepository;

    public UserFavoriteBook saveUserFavoriteBook(UserFavoriteBook userFavoriteBook) {
        return userFavoriteBookRepository.save(userFavoriteBook);
    }

    public boolean deleteUserFavoriteBook(Long id) {
        if (userFavoriteBookRepository.existsById(id)) {
            userFavoriteBookRepository.deleteById(id);
            return true;
        } else {
            return false;
        }
    }


    public Iterable<UserFavoriteBook> getAllUserFavoriteBooks() {
        return userFavoriteBookRepository.findAll();
    }

    public Iterable<UserFavoriteBook> getAllUserFavoriteBooksByUsername(String username) {
        Optional<MyUser> myUser = userRepository.findByUsername(username);
        return userFavoriteBookRepository.findByMyUserId(myUser.orElseThrow().getId());
    }

    public Iterable<UserFavoriteBook> getAllUserFavoriteBookSortByField(String field) {
        return userFavoriteBookRepository.findAll((Pageable) Sort.by(field));
    }

    public Optional<UserFavoriteBook> getUserFavoriteBookById(Long id) {
        return userFavoriteBookRepository.findById(id);
    }

    public Page<UserFavoriteBook> getAllUserFavoriteBookPage(int page, int size) {
        return userFavoriteBookRepository.findAll(PageRequest.of(page, size));
    }

    public Page<UserFavoriteBook> getAllUserFavoriteBookPageSortByField(int page, int size, String field) {
        return userFavoriteBookRepository.findAll(PageRequest.of(page, size, Sort.by(field)));
    }

}
