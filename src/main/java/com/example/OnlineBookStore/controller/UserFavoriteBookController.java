package com.example.OnlineBookStore.controller;

import com.example.OnlineBookStore.helper.UserHelper;
import com.example.OnlineBookStore.model.Book;
import com.example.OnlineBookStore.model.UserFavoriteBook;
import com.example.OnlineBookStore.service.UserFavoriteBookService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
@RequestMapping("/favorite_book")
@Tag(name = "Избранные книги пользователя",
        description = "Все методы для работы с избранными книгами пользователя")
public class UserFavoriteBookController {
    @Autowired
    private UserFavoriteBookService userFavoriteBookService;

    @PostMapping
    @Operation(summary = "Сохранить или обновить избранную книгу пользователя")
    public UserFavoriteBook save(@RequestBody UserFavoriteBook userFavoriteBook) {
        return userFavoriteBookService.saveUserFavoriteBook(userFavoriteBook);
    }

//    @DeleteMapping("/{id}")
//    @Operation(summary = "Удалить избранную книгу пользователя")
//    public Boolean delete(@AuthenticationPrincipal User user,
//                          @Parameter(description = "Id поля") @PathVariable Long id) throws Exception {
//        if (!UserHelper.isAdmin(user)) {
//            throw new Exception("Not admin!");
//        }
//        return userFavoriteBookService.deleteUserFavoriteBook(id);
//    }

    @GetMapping("/read")
    @Operation(summary = "Получить все избранные книги пользователей")
    public Iterable<UserFavoriteBook> readAll(@AuthenticationPrincipal User user) throws Exception {
        if (!UserHelper.isAdmin(user)) {
            throw new Exception("Not admin!");
        }
        return userFavoriteBookService.getAllUserFavoriteBooks();
    }

    @GetMapping("/read/user")
    @Operation(summary = "Получить все избранные книги пользователя")
    public Iterable<UserFavoriteBook> read(@AuthenticationPrincipal User user) throws Exception {
        return userFavoriteBookService.getAllUserFavoriteBooksByUsername(user.getUsername());
    }


//    @GetMapping("/sort/{field}")
//    @Operation(summary = "Получить избранные книги пользователей отсортированными по полю")
//    public Iterable<UserFavoriteBook> read(@AuthenticationPrincipal User user,
//                                           @Parameter(description = "Поле сортировки") @PathVariable String field) throws Exception {
//        if (!UserHelper.isAdmin(user)) {
//            throw new Exception("Not admin!");
//        }
//        return userFavoriteBookService.getAllUserFavoriteBookSortByField(field);
//    }

    @GetMapping("/{page}/{size}")
    @Operation(summary = "Получить страницы с избранными книгами пользователей(пагинация)")
    public Page<UserFavoriteBook> read(@AuthenticationPrincipal User user,
                                       @Parameter(description = "Количество страниц") @PathVariable Integer page,
                                       @Parameter(description = "Количество полей на странице") @PathVariable Integer size) throws Exception {
        if (!UserHelper.isAdmin(user)) {
            throw new Exception("Not admin!");
        }
        return userFavoriteBookService.getAllUserFavoriteBookPage(page, size);
    }

    @GetMapping("/sort/{page}/{size}/{field}")
    @Operation(summary = "Получить страницы с избранными книгами пользователей отсортированными по полю (пагинация)")
    public Page<UserFavoriteBook> read(@AuthenticationPrincipal User user,
                                       @Parameter(description = "Количество страниц") @PathVariable Integer page,
                                       @Parameter(description = "Количество полей на странице") @PathVariable Integer size,
                                       @Parameter(description = "Поле сортировки") @PathVariable String field) throws Exception {
        if (!UserHelper.isAdmin(user)) {
            throw new Exception("Not admin!");
        }
        return userFavoriteBookService.getAllUserFavoriteBookPageSortByField(page, size, field);
    }


}
