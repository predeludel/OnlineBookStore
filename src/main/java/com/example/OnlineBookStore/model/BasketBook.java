package com.example.OnlineBookStore.model;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Schema(description = "Корзины с выбранными книгами позьзователей")
public class BasketBook {

    @EmbeddedId
    BasketBookKey id;

    private Integer quantity;

    @ManyToOne
    @MapsId("bookId")
    @JoinColumn(name = "book_id")
    private Book book;

    @ManyToOne
    @MapsId("basketId")
    @JoinColumn(name = "basket_id")
    private Basket basket;

}
