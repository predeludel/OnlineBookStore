package com.example.OnlineBookStore.service;

import com.example.OnlineBookStore.model.Book;
import com.example.OnlineBookStore.model.Category;
import com.example.OnlineBookStore.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    public Category save(Category category) {
        return categoryRepository.save(category);
    }

    public boolean deleteCategory(Long id) {
        if (categoryRepository.existsById(id)) {
            categoryRepository.deleteById(id);
            return true;
        } else {
            return false;
        }
    }

    public Optional<Category> getCategoryById(Long id) {
        return categoryRepository.findById(id);
    }

    public Iterable<Category> getAllCategories() {
        return categoryRepository.findAll();
    }

    public Page<Category> getAllCategoriesPage(int page, int size) {
        return categoryRepository.findAll(PageRequest.of(page, size));
    }

    public Page<Category> getAllCategoriesPageSortByField(int page, int size, String field) {
        return categoryRepository.findAll(PageRequest.of(page, size, Sort.by(field)));
    }

}
