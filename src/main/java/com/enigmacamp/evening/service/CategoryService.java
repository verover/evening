package com.enigmacamp.evening.service;

import com.enigmacamp.evening.entity.Category;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface CategoryService {
    Category save(Category category);
    Category update(String id,Category category);
    List<Category> findAll();
    Page<Category> listPage(Pageable pageable);
    Category getById(String id);
}
