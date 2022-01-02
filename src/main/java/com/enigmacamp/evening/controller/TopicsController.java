

package com.enigmacamp.evening.controller;

import com.enigmacamp.evening.entity.Topics;
import com.enigmacamp.evening.service.TopicsService;
import com.enigmacamp.evening.util.PageResponse;
import com.enigmacamp.evening.payload.response.EventResponse;
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
@RequestMapping(value = "/topics")
public class TopicsController {

    @Autowired
    TopicsService topicsService;

    @PostMapping
    public ResponseEntity<EventResponse<Topics>> createTopics(@Valid @RequestBody Topics topics, Errors errors){
        EventResponse<Topics> response = new EventResponse<>();
        if(errors.hasErrors()){
            for (ObjectError err : errors.getAllErrors()) {
                response.getMessages().add(err.getDefaultMessage());
            }
            response.setStatus(false);
            response.setData(null);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
        response.getMessages().add("Successfuly Created Topics");
        response.setStatus(true);
        response.setData(topicsService.save(topics));
        return ResponseEntity.ok(response);
    }

    @GetMapping
    public ResponseEntity<WebResponse<PageResponse<Topics>>> listPage(
            @RequestParam(name = "size",defaultValue = "7") Integer size,
            @RequestParam(name = "page",defaultValue = "0") Integer page
    ){
        Pageable pageable = PageRequest.of(page,size);

        Page<Topics> pageCategories = topicsService.listPage(pageable);
        PageResponse<Topics> topicsPageResponse = new PageResponse<>(
                pageCategories.getContent(),
                pageCategories.getTotalElements(),
                pageCategories.getTotalPages(),
                page,
                size
        );
        WebResponse<PageResponse<Topics>> response = new WebResponse<>("Success to get all Topics",topicsPageResponse);
        return  ResponseEntity
                .status(HttpStatus.OK)
                .body(response);
    }

    @PutMapping(value = "/{topicsId}")
    public ResponseEntity<EventResponse<Topics>> updateTopics(@PathVariable("topicsId") String id, @Valid @RequestBody Topics topics, Errors errors){
        EventResponse<Topics> response = new EventResponse<>();
        if(errors.hasErrors()){
            for (ObjectError err : errors.getAllErrors()) {
                response.getMessages().add(err.getDefaultMessage());
            }
            response.setStatus(false);
            response.setData(null);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
        response.getMessages().add("Successfuly Updated Topics");
        response.setStatus(true);
        response.setData(topicsService.update(id,topics));
        return ResponseEntity.ok(response);
    }

    @GetMapping(value = "/{topicsId}")
    public ResponseEntity<WebResponse<Topics>> getById(@PathVariable("topicsId") String id){
        Topics topics = topicsService.getById(id);
        return ResponseEntity.status(HttpStatus.OK).body(new WebResponse<>(" Topics has been found",topics));
    }

}