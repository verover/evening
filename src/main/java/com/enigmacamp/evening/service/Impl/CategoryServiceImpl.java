package com.enigmacamp.evening.service.Impl;


import com.enigmacamp.evening.entity.Category;
import com.enigmacamp.evening.exception.NotFoundException;
import com.enigmacamp.evening.repository.CategoryRepository;
import com.enigmacamp.evening.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.TransactionScoped;
import java.util.List;
import java.util.Optional;

@Service
@TransactionScoped
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    CategoryRepository categoryRepository;



    @Override
    public Category save(Category category) {
        return categoryRepository.save(category);
    }

    @Override
    public Category update(String id, Category category) {
        category.setCategoryId(id);
        return categoryRepository.save(category);
    }

    @Override
    public List<Category> findAll() {
        return categoryRepository.findAll();
    }

    @Override
    public Page<Category> listPage(Pageable pageable) {
        return this.categoryRepository.findAll(pageable);
    }

    @Override
    public Category getById(String id) {
        return findByOrThrowNotFound(id);
    }


    public Category findByOrThrowNotFound(String id) {
        Optional<Category> category = this.categoryRepository.findById(id);
        if (!category.isPresent()) {
            throw new NotFoundException("Category is not found");
        }
        return category.get();
    }
}
