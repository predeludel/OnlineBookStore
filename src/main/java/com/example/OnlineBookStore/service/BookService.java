package com.example.OnlineBookStore.service;

import com.example.OnlineBookStore.model.Book;
import com.example.OnlineBookStore.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;
import java.util.Optional;

@Service
public class BookService {
    @Autowired
    private BookRepository bookRepository;
    @Autowired
    private FileService fileService;

    protected static final String IMAGE_FILE_NAME_FORMAT = "%d.png";

    public Book save(Book book) {
        return bookRepository.save(book);
    }

    public boolean deleteBook(Long id) {
        if (bookRepository.existsById(id)) {
            bookRepository.deleteById(id);
            return true;
        } else {
            return false;
        }
    }

    public Book getBookById(Long id) {
        Optional<Book> book = bookRepository.findById(id);
        if (book.isPresent()) {
            return book.get();
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, String.format("Image with id %d not found", id));
        }
    }

    public Iterable<Book> getAllBooks() {
        return bookRepository.findAll();
    }

    public Page<Book> getAllBooksPage(int page, int size) {
        return bookRepository.findAll(PageRequest.of(page, size));
    }

    public Page<Book> getAllBooksPageSortByField(int page, int size, String field) {
        return bookRepository.findAll(PageRequest.of(page, size, Sort.by(field)));
    }

    public Book attachFile(Long id, MultipartFile file) {
        Book book = getBookById(id);
        try {
            String path = fileService.saveFile(file.getBytes(), String.format(IMAGE_FILE_NAME_FORMAT, book.getId()));
            book.setImgAttachFilePath(path);
            bookRepository.save(book);
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
        return book;
    }

    public Resource getImageResource(Long bookId) {
        Book book = getBookById(bookId);
        return fileService.loadFile(book.getImgAttachFilePath());
    }


}
