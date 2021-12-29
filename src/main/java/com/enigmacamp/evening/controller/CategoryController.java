package com.enigmacamp.evening.controller;

import com.enigmacamp.evening.entity.Category;
import com.enigmacamp.evening.service.CategoryService;
import com.enigmacamp.evening.util.PageResponse;
import com.enigmacamp.evening.util.RequestResponse;
import com.enigmacamp.evening.util.WebResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping(value = "/categories")
public class CategoryController {

    @Autowired
    CategoryService categoryService;

    @PostMapping
    public ResponseEntity<RequestResponse<Category>> create(@Valid @RequestBody Category category, Errors errors){
        RequestResponse<Category> response = new RequestResponse<>();
        if(errors.hasErrors()){
            for (ObjectError err : errors.getAllErrors()) {
                response.getMessages().add(err.getDefaultMessage());
            }
            response.setStatus(false);
            response.setData(null);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
        response.getMessages().add("Successfuly Created Category");
        response.setStatus(true);
        response.setData(categoryService.save(category));


        return ResponseEntity.ok(response);
    }

    @GetMapping
    public ResponseEntity<WebResponse<PageResponse<Category>>> listPage(
            @RequestParam(name = "size",defaultValue = "7") Integer size,
            @RequestParam(name = "page",defaultValue = "0") Integer page
    ){
        Pageable pageable = PageRequest.of(page,size);

        Page<Category> pageCategories = categoryService.listPage(pageable);
        PageResponse<Category> categoryPageResonse = new PageResponse<>(
                pageCategories.getContent(),
                pageCategories.getTotalElements(),
                pageCategories.getTotalPages(),
                page,
                size
        );
        WebResponse<PageResponse<Category>> response = new WebResponse<>("Success to get all Categories",categoryPageResonse);
        return  ResponseEntity
                .status(HttpStatus.OK)
                .body(response);
    }

    @GetMapping(value = "/{categoryId}")
    public ResponseEntity<WebResponse<Category>> getById(@PathVariable("categoryId") String id){
        Category category = categoryService.getById(id);
        return ResponseEntity.status(HttpStatus.OK).body(new WebResponse<>(" Category has been found",category));
    }

    @PutMapping(value = "/{categoryId}")
    public  ResponseEntity<RequestResponse<Category>>  updateById(@PathVariable("categoryId") String id,@Valid @RequestBody Category category,Errors errors){
        RequestResponse<Category> response = new RequestResponse<>();
        if(errors.hasErrors()){
            for (ObjectError err : errors.getAllErrors()) {
                response.getMessages().add(err.getDefaultMessage());
            }
            response.setStatus(false);
            response.setData(null);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
        response.getMessages().add("Successfuly Update Category");
        response.setStatus(true);
        response.setData(categoryService.update(id,category));

        return ResponseEntity.ok(response);

    }
}
