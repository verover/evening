package com.enigmacamp.evening.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Data;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Where;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.*;
import java.util.*;

@Data
@Entity
@Table(name="mst_event")
@JsonIgnoreProperties(value = { "eventDetails" })
public class Event {
    @Id
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name="system-uuid",strategy = "uuid")
    @Column(name = "event_id",nullable = false)
    private String eventId;

    private String organizerId;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String bannerImage;

    @ManyToOne
    @JoinColumn(name="category_id")
    private Category category;

    @ManyToOne
    @JoinColumn(name="topics_id")
    private Topics topics;


    @CreatedDate
    @Column(updatable = false)
    private Date createdAt;

    @LastModifiedDate
    @JsonIgnore
    private Date updatedAt;

    @PrePersist
    private void createdDate(){
        if(this.createdAt == null) this.createdAt = new Date();
        if(this.updatedAt == null) this.updatedAt = new Date();
    }

    @PreUpdate
    private void updatedDate(){
        this.updatedAt = new Date();
    }

//    @JsonIgnore
    private Boolean isDeleted = Boolean.FALSE;

    @OneToMany(targetEntity = EventDetail.class,fetch = FetchType.EAGER)
    @JsonManagedReference
    List<EventDetail> eventDetails;

}
