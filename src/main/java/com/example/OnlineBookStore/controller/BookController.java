package com.example.OnlineBookStore.controller;

import com.example.OnlineBookStore.helper.UserHelper;
import com.example.OnlineBookStore.model.Book;
import com.example.OnlineBookStore.repository.BookRepository;
import com.example.OnlineBookStore.service.BookService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Page;
import org.springframework.http.MediaType;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@CrossOrigin
@RequestMapping("/book")
@Tag(name = "Книги",
        description = "Все методы для работы с книгами магазина")
public class BookController {

    @Autowired
    private BookService bookService;

    @Autowired
    private BookRepository bookRepository;

    @PostMapping()
    @Operation(summary = "Сохранить или обновить книгу")
    public Book save(@AuthenticationPrincipal User user,
                     @Parameter(description = "Книга") @RequestBody Book book) throws Exception {
        if (!UserHelper.isAdmin(user)) {
            throw new Exception("Not admin!");
        }
        return bookService.save(book);
    }

    @PostMapping("/attach/{id}")
    @Operation(summary = "Сохранить изображение книги")
    public Book attachFile(@AuthenticationPrincipal User user,
                           @Parameter(description = "Id Книги") @PathVariable Long id,
                           @Parameter(description = "Изображение книги") @RequestParam("file") MultipartFile file) throws Exception {
        if (!UserHelper.isAdmin(user)) {
            throw new Exception("Not admin!");
        }
        return bookService.attachFile(id, file);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Удалить книгу")
    public Boolean delete(@AuthenticationPrincipal User user,
                          @Parameter(description = "Id Книги") @PathVariable Long id) throws Exception {
        if (!UserHelper.isAdmin(user)) {
            throw new Exception("Not admin!");
        }
        return bookService.deleteBook(id);
    }

    @GetMapping("/public/read")
    @Operation(summary = "Получить все книги")
    public Iterable<Book> read() {
        return bookService.getAllBooks();

    }

    @GetMapping("/public/read/{id}")
    @Operation(summary = "Получить книгу по ID")
    public Book read(@Parameter(description = "Id Книги") @PathVariable Long id) throws Exception {
        return bookService.getBookById(id);
    }

    @GetMapping(value = "/public/read/file/{id}", produces = MediaType.IMAGE_JPEG_VALUE)
    @Operation(summary = "Получить изображение книги")
    public Resource getFile(@Parameter(description = "ID книги") @PathVariable Long id) {
        return bookService.getImageResource(id);
    }


    @GetMapping("/{page}/{size}")
    @Operation(summary = "Получить страницы с книгами (пагинация)")
    public Page<Book> read(@AuthenticationPrincipal User user,
                           @Parameter(description = "Количество страниц") @PathVariable Integer page,
                           @Parameter(description = "Количество книг на странице") @PathVariable Integer size) throws Exception {
        if (!UserHelper.isAdmin(user)) {
            throw new Exception("Not admin!");
        }
        return bookService.getAllBooksPage(page, size);
    }

    @GetMapping("/sort/{page}/{size}/{field}")
    @Operation(summary = "Получить страницы с книгами отсортированными по полю (пагинация)")
    public Page<Book> read(@AuthenticationPrincipal User user,
                           @Parameter(description = "Количество страниц") @PathVariable Integer page,
                           @Parameter(description = "Количество книг на странице") @PathVariable Integer size,
                           @Parameter(description = "Поле сортировки") @PathVariable String field) throws Exception {
        if (!UserHelper.isAdmin(user)) {
            throw new Exception("Not admin!");
        }
        return bookService.getAllBooksPageSortByField(page, size, field);
    }

}
