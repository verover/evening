package com.enigmacamp.evening.controller;

import com.enigmacamp.evening.dto.OrganizerDTO;
import com.enigmacamp.evening.entity.Organizer;
import com.enigmacamp.evening.service.OrganizerService;
import com.enigmacamp.evening.util.PageResponse;
import com.enigmacamp.evening.util.WebResponse;
import org.aspectj.weaver.ast.Or;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/organizers")
public class OrganizerController {

    @Autowired
    private OrganizerService organizerService;

    @PostMapping
    public ResponseEntity<WebResponse<Organizer>> createOrganizer(@RequestBody Organizer request) {
        Organizer organizer = this.organizerService.create(request);
        WebResponse<Organizer> webResponse = new WebResponse<>("Successfully created new organizer", organizer);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(webResponse);

    }

    @GetMapping("/{organizerId}")
    public ResponseEntity<WebResponse<Organizer>> getOrganizerById(@PathVariable("organizerId") String id) {
        Organizer organizer = organizerService.getOrganizerById(id);
        WebResponse<Organizer> response = new WebResponse<>(String.format("Customer with id %s found", id), organizer);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

//    @GetMapping
//    public List<Organizer> getListOrganizer(){
//        return organizerService.list();
//    }

    @GetMapping

    public ResponseEntity<WebResponse<PageResponse<Organizer>>> listWithPage(
            @RequestParam(name = "size", defaultValue = "2") Integer size,
            @RequestParam(name = "page", defaultValue = "0") Integer page,
            @RequestParam(name = "name", required = false) String name,
            @RequestParam(name = "email", required = false) String organization_email
            ) {

        Pageable pageable = PageRequest.of(page,size);
        OrganizerDTO organizerDTO = new OrganizerDTO(name,organization_email);

        Page<Organizer> organizers = this.organizerService.listWithPage(pageable, organizerDTO);
        PageResponse<Organizer> pageResponse = new PageResponse<>(
                organizers.getContent(),
                organizers.getTotalElements(),
                organizers.getTotalPages(),
                page,
                size
        );

        WebResponse<PageResponse<Organizer>> response = new WebResponse<>("Successfully get data organizer", pageResponse);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(response);
    }

    @PutMapping
    public ResponseEntity<WebResponse<Organizer>> updateOrganizerByid (
            @RequestBody Organizer request) {
        Organizer update = this.organizerService.updateOrganizerById(request);
        WebResponse<Organizer> webResponse = new WebResponse<>("Successfully update organizer", update);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(webResponse);

    }

    @DeleteMapping("/{organizerId}")
    public ResponseEntity<WebResponse<String>> deleteOrganizerByid(@PathVariable ("organizerId") String id) {
        String message = this.organizerService.delete(id);
        WebResponse<String> webResponse = new WebResponse<>(message, id);


        return ResponseEntity
                .status(HttpStatus.OK)
                .body(webResponse);
    }



}
