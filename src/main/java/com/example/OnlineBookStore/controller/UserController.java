package com.example.OnlineBookStore.controller;

import com.example.OnlineBookStore.helper.UserHelper;
import com.example.OnlineBookStore.model.MyUser;
import com.example.OnlineBookStore.model.Payment;
import org.springframework.security.core.userdetails.User;
import com.example.OnlineBookStore.repository.UserRepository;
import com.example.OnlineBookStore.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/user")
@Tag(name = "Пользователи",
        description = "Все методы для работы с пользователями системы")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;

    @PostMapping("/save")
    @Operation(summary = "Сохранить пользователя")
    public MyUser save(@Parameter(description = "Пользователь") @RequestBody MyUser myUser) {
        return userService.save(myUser);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Удалить пользователя")
    public Boolean delete(@AuthenticationPrincipal User user,
                          @Parameter(description = "Id Пользователя") @PathVariable Long id) throws Exception {
        if (!UserHelper.isAdmin(user)) {
            throw new Exception("Not admin!");
        }
        return userService.delete(id);
    }

    @GetMapping("/read/{id}")
    @Operation(summary = "Получить пользователя по ID")
    public Optional<MyUser> read(@Parameter(description = "Id Пользователя") @PathVariable Long id) {
        return userService.getMyUserById(id);
    }

    @GetMapping()
    @Operation(summary = "Получить всех пользователей")
    public Iterable<MyUser> read(@AuthenticationPrincipal User user) throws Exception {
        if (!UserHelper.isAdmin(user)) {
            throw new Exception("Not admin!");
        }
        return userService.getAllUsers();
    }

    @GetMapping("/{page}/{size}")
    @Operation(summary = "Получить страницы с пользователями (пагинация)")
    public Page<MyUser> read(@AuthenticationPrincipal User user,
                             @Parameter(description = "Количество страниц") @PathVariable Integer page,
                             @Parameter(description = "Количество пользователей на странице") @PathVariable Integer size) throws Exception {
        if (!UserHelper.isAdmin(user)) {
            throw new Exception("Not admin!");
        }
        return userService.getAllUsersPage(page, size);
    }

    @GetMapping("/sort/{page}/{size}/{field}")
    @Operation(summary = "Получить страницы с пользователями отсортированными по полю (пагинация)")
    public Page<MyUser> read(@AuthenticationPrincipal User user,
                             @Parameter(description = "Количество страниц") @PathVariable Integer page,
                             @Parameter(description = "Количество пользователей на странице") @PathVariable Integer size,
                             @Parameter(description = "Поле сортировки") @PathVariable String field) throws Exception {
        if (!UserHelper.isAdmin(user)) {
            throw new Exception("Not admin!");
        }
        return userService.getAllUsersPageSortByField(page, size, field);
    }


}
