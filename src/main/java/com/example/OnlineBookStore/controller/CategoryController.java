package com.example.OnlineBookStore.controller;


import com.example.OnlineBookStore.helper.UserHelper;
import com.example.OnlineBookStore.model.Book;
import com.example.OnlineBookStore.model.Category;
import com.example.OnlineBookStore.repository.CategoryRepository;
import com.example.OnlineBookStore.service.CategoryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/category")
@Tag(name = "Категории книг",
        description = "Все методы для работы с категориями книг магазина")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private CategoryRepository categoryRepository;

    @PostMapping()
    @Operation(summary = "Добавить или обновить категорию книги")
    public Category save(@AuthenticationPrincipal User user,
                         @Parameter(description = "Категория") @RequestBody Category category) throws Exception {
        if (!UserHelper.isAdmin(user)) {
            throw new Exception("Not admin!");
        }
        return categoryService.save(category);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Удалить категорию книги")
    public Boolean delete(@AuthenticationPrincipal User user,
                          @Parameter(description = "Id категории") @PathVariable Long id) throws Exception {
        if (!UserHelper.isAdmin(user)) {
            throw new Exception("Not admin!");
        }
        return categoryService.deleteCategory(id);
    }

    @GetMapping("/public/read")
    @Operation(summary = "Получить все категории книг")
    public Iterable<Category> read() {
        return categoryService.getAllCategories();

    }

    @GetMapping("/public/read/{id}")
    @Operation(summary = "Получить категорию книгу по ID")
    public Optional<Category> read(@Parameter(description = "Id категории") @PathVariable Long id) throws Exception {
        return categoryService.getCategoryById(id);
    }

    @GetMapping("/{page}/{size}")
    @Operation(summary = "Получить страницы с категориями книг (пагинация)")
    public Page<Category> read(@AuthenticationPrincipal User user,
                           @Parameter(description = "Количество страниц") @PathVariable Integer page,
                           @Parameter(description = "Количество категорий книг на странице") @PathVariable Integer size) throws Exception {
        if (!UserHelper.isAdmin(user)) {
            throw new Exception("Not admin!");
        }
        return categoryService.getAllCategoriesPage(page, size);
    }

    @GetMapping("/sort/{page}/{size}/{field}")
    @Operation(summary = "Получить страницы с категориями книг отсортированными по полю (пагинация)")
    public Page<Category> read(@AuthenticationPrincipal User user,
                           @Parameter(description = "Количество страниц") @PathVariable Integer page,
                           @Parameter(description = "Количество катнгорий книг на странице") @PathVariable Integer size,
                           @Parameter(description = "Поле сортировки") @PathVariable String field) throws Exception {
        if (!UserHelper.isAdmin(user)) {
            throw new Exception("Not admin!");
        }
        return categoryService.getAllCategoriesPageSortByField(page, size, field);
    }

}
