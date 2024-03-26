package com.example.OnlineBookStore.model;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Schema(description = "Избранные книги пользователей")
public class UserFavoriteBook {

    @EmbeddedId
    UserFavoriteBookKey id;

    private Boolean isActive;

    @ManyToOne
    @MapsId("userId")
    @JoinColumn(name = "user_id")
    private MyUser myUser;

    @ManyToOne
    @MapsId("bookId")
    @JoinColumn(name = "book_id")
    private Book book;

}
