package com.enigmacamp.evening.controller;

import com.enigmacamp.evening.entity.Organizer;
import com.enigmacamp.evening.service.OrganizerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/organizers")
public class OrganizerController {

    @Autowired
    private OrganizerService organizerService;

    @PostMapping
    public Organizer createOrganizer(@RequestBody Organizer organizer){
        return organizerService.create(organizer);
    }


}
