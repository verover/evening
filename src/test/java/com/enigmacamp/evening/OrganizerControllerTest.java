package com.enigmacamp.evening;

import com.enigmacamp.evening.controller.OrganizerController;
import com.enigmacamp.evening.entity.Organizer;
import com.enigmacamp.evening.service.OrganizerService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(OrganizerController.class)
public class OrganizerControllerTest {

     @MockBean
     OrganizerService service;

     @Autowired
     OrganizerController organizerController;

     @Autowired
     MockMvc mockMvc;

     @Autowired
     ObjectMapper objectMapper;

     private Organizer organizer;

     @BeforeEach
     void setup(){
         organizer = new Organizer();
         organizer.setId("asd12");
         organizer.setName("event pku");
         organizer.setAddress("pekanbaru");
         organizer.setOrganization_email("eventpku@gmail.com");
         organizer.setOrganization_phone("081278469837");
         organizer.setWebsite("eventpku.com");
     }

     @Test
     void shouldSave_OrganizerSuccess() throws Exception{
         when(service.create(any(Organizer.class))).thenReturn(organizer);

         RequestBuilder requestBuilder = post("/organizers")
                 .contentType(MediaType.APPLICATION_JSON)
                 .content("{"+
                         "\"id\" : " + "\"asd12\","+
                        "\"name\" : " + "\"event pku\","+
                        "\"address\" : " + "\"pekanbaru\","+
                        "\"organization_email\" : " + "\"eventpku@gmail.com\","+
                        "\"organization_phone\" : " + "\"081278469837\","+
                        "\"website\" : " + "\"eventpku.com\""+
                         "}");

         mockMvc.perform(requestBuilder)
                 .andExpect (status().isCreated())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.data.name", Matchers.is(organizer.getName())));

     }

     @Test
    void getOrganizerById_Success() throws Exception{
         when(service.getOrganizerById("asd12")).thenReturn(organizer);

         RequestBuilder requestBuilder = get("/organizers/asd12").contentType(MediaType.APPLICATION_JSON);

         mockMvc.perform(requestBuilder)
                 .andExpect (status().isOk())
                 .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                 .andExpect(jsonPath("$.data.name", Matchers.is(organizer.getName())));


     }

     @Test
    void deleteOrganizerById_Success() throws Exception{
         String organizerId ="asd12";
         Organizer newOrganizer = new Organizer(organizerId,"event pku","pekanbaru","eventpku@gmail.com", "081278469837","eventpku.com");

         given(service.getOrganizerById(organizerId)).willReturn(newOrganizer);

         mockMvc.perform(delete("/organizers/{id}",newOrganizer.getId()))
                 .andExpect(status().isOk());
     }
}
