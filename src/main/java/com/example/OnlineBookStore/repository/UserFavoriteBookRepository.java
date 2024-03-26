package com.example.OnlineBookStore.repository;

import com.example.OnlineBookStore.model.UserFavoriteBook;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserFavoriteBookRepository extends
        CrudRepository<UserFavoriteBook, Long> {

    Page<UserFavoriteBook> findAll(Pageable pageable);

    UserFavoriteBook findByBookId(Long bookId);


    Iterable<UserFavoriteBook> findByMyUserId(Long userId);
}
