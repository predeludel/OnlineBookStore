package com.example.OnlineBookStore.controller;

import com.example.OnlineBookStore.helper.UserHelper;
import com.example.OnlineBookStore.model.Basket;
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
@RequestMapping("/basket")
@Tag(name = "Корзина пользователя",
        description = "Все методы для работы с корзинами пользователей")
public class BasketController {

    @Autowired
    private BasketService basketService;

    @PostMapping
    @Operation(summary = "Сохранить или обновить корзину пользователя")
    public Basket save(@RequestBody Basket basket) {
        return basketService.save(basket);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Удалить корзину пользователя")
    public Boolean delete(@Parameter(description = "Id корзины") @PathVariable Long id) throws Exception {
        return basketService.deleteBasket(id);
    }

    @GetMapping("/read")
    @Operation(summary = "Получить все корзины")
    public Iterable<Basket> read(@AuthenticationPrincipal User user) throws Exception {
        if (!UserHelper.isAdmin(user)) {
            throw new Exception("Not admin!");
        }
        return basketService.getAllBaskets();
    }

    @GetMapping("/read/active")
    @Operation(summary = "Получить все активные корзины")
    public Iterable<Basket> readActive(@AuthenticationPrincipal User user) throws Exception {
        if (!UserHelper.isAdmin(user)) {
            throw new Exception("Not admin!");
        }
        return basketService.getAllActiveBaskets();
    }

    @GetMapping("/read/{id}")
    @Operation(summary = "Получить все корзины по Id пользователя")
    public Iterable<Basket> readBasketByUserId(@AuthenticationPrincipal User user,
                                               @Parameter(description = "Id пользователя") @PathVariable Long id) throws Exception {
        if (!UserHelper.isAdmin(user)) {
            throw new Exception("Not admin!");
        }
        return basketService.getBasketsByUserId(id);
    }

    @GetMapping("/read/user/active")
    @Operation(summary = "Получить активную корзину пользователя")
    public Iterable<Basket> readActiveUserBasket(@AuthenticationPrincipal User user) throws Exception {
        return basketService.getMyUserActiveBasket(user.getUsername());
    }

    @GetMapping("/read/user")
    @Operation(summary = "Получить все корзины пользователя")
    public Iterable<Basket> readUserBaskets(@AuthenticationPrincipal User user) throws Exception {
        return basketService.getAllMyUserBaskets(user.getUsername());
    }

    @GetMapping("/{page}/{size}")
    @Operation(summary = "Получить страницы с корзинами пользователей(пагинация)")
    public Page<Basket> read(@AuthenticationPrincipal User user,
                             @Parameter(description = "Количество страниц") @PathVariable Integer page,
                             @Parameter(description = "Количество корзин на странице") @PathVariable Integer size) throws Exception {
        if (!UserHelper.isAdmin(user)) {
            throw new Exception("Not admin!");
        }
        return basketService.getAllBasketsPage(page, size);
    }

    @GetMapping("/sort/{page}/{size}/{field}")
    @Operation(summary = "Получить страницы с корзинами пользователей отсортированными по полю (пагинация)")
    public Page<Basket> read(@AuthenticationPrincipal User user,
                             @Parameter(description = "Количество страниц") @PathVariable Integer page,
                             @Parameter(description = "Количество корзин на странице") @PathVariable Integer size,
                             @Parameter(description = "Поле сортировки") @PathVariable String field) throws Exception {
        if (!UserHelper.isAdmin(user)) {
            throw new Exception("Not admin!");
        }
        return basketService.getAllBasketsPageSortByField(page, size, field);
    }

}
