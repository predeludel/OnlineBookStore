package com.example.OnlineBookStore.model;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

import java.io.Serializable;

@Embeddable
class UserFavoriteBookKey implements Serializable {

    @Column(name = "user_id")
    Long userId;

    @Column(name = "book_id")
    Long bookId;

}
