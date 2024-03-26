package com.example.OnlineBookStore.model;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

import java.io.Serializable;

@Embeddable
public class BasketBookKey implements Serializable {

    @Column(name = "book_id")
    Long bookId;

    @Column(name = "basket_id")
    Long basketId;
}
