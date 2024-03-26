package com.example.OnlineBookStore.controller;

import com.example.OnlineBookStore.helper.UserHelper;
import com.example.OnlineBookStore.model.Basket;
import com.example.OnlineBookStore.model.BasketBook;
import com.example.OnlineBookStore.service.BasketBookService;
import com.example.OnlineBookStore.service.BasketService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/basket_book")
@Tag(name = "Корзины с книгами пользователя",
        description = "Все методы для работы с корзинами книг пользователей")
public class BasketBookController {

    @Autowired
    private BasketBookService basketBookService;

    @PostMapping
    @Operation(summary = "Сохранить или обновить корзину книг пользователя")
    public BasketBook save(@RequestBody BasketBook basketBook) {
        return basketBookService.save(basketBook);
    }

    @DeleteMapping("/{bookId}")
    @Operation(summary = "Удалить книгу из корзины пользователя")
    public Boolean delete(@AuthenticationPrincipal User user, @Parameter(description = "Id книги") @PathVariable Long bookId) throws Exception {
        return basketBookService.deleteBasketBookById(bookId, user.getUsername());
    }

    @GetMapping("/read")
    @Operation(summary = "Получить все корзины книг")
    public Iterable<BasketBook> read(@AuthenticationPrincipal User user) throws Exception {
        if (!UserHelper.isAdmin(user)) {
            throw new Exception("Not admin!");
        }
        return basketBookService.getAllBasketsBooks();
    }

    @GetMapping("/read/user")
    @Operation(summary = "Получить все акивные книги корзины пользователя")
    public Iterable<BasketBook> readBasketBookMyUser(@AuthenticationPrincipal User user) throws Exception {
        return basketBookService.getBasketBookMyUser(user.getUsername());
    }

    @GetMapping("/read/{basketId}")
    @Operation(summary = "Получить все книги по Id корзины")
    public Iterable<BasketBook> readBasket(@AuthenticationPrincipal User user,
                                           @Parameter(description = "Id корзины") @PathVariable Long basketId) throws Exception {
        if (!UserHelper.isAdmin(user)) {
            throw new Exception("Not admin!");
        }
        return basketBookService.getAllBasketBooksByBasketId(basketId);
    }

    @GetMapping("/read/{bookId}")
    @Operation(summary = "Получить все корзины с книгами по Id книги")
    public Iterable<BasketBook> readBook(@AuthenticationPrincipal User user,
                                         @Parameter(description = "Id книги") @PathVariable Long bookId) throws Exception {
        if (!UserHelper.isAdmin(user)) {
            throw new Exception("Not admin!");
        }
        return basketBookService.getAllBasketBooksByBookId(bookId);
    }

    @GetMapping("/{page}/{size}")
    @Operation(summary = "Получить страницы с корзинами книг пользователей(пагинация)")
    public Page<BasketBook> read(@AuthenticationPrincipal User user,
                             @Parameter(description = "Количество страниц") @PathVariable Integer page,
                             @Parameter(description = "Количество корзин с книгами на странице") @PathVariable Integer size) throws Exception {
        if (!UserHelper.isAdmin(user)) {
            throw new Exception("Not admin!");
        }
        return basketBookService.getAllBasketBooksPage(page, size);
    }

    @GetMapping("/sort/{page}/{size}/{field}")
    @Operation(summary = "Получить страницы с корзинами книг пользователей отсортированными по полю (пагинация)")
    public Page<BasketBook> read(@AuthenticationPrincipal User user,
                             @Parameter(description = "Количество страниц") @PathVariable Integer page,
                             @Parameter(description = "Количество корзин книг на странице") @PathVariable Integer size,
                             @Parameter(description = "Поле сортировки") @PathVariable String field) throws Exception {
        if (!UserHelper.isAdmin(user)) {
            throw new Exception("Not admin!");
        }
        return basketBookService.getAllBasketBooksPageSortByField(page, size, field);
    }

}
