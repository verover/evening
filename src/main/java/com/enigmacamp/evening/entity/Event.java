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
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
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

    @NotBlank(message = "Name is Mandatory")
    @NotEmpty(message = "Name is Required")
    @Size(min = 9, max = 50)
    @Pattern(regexp = "^[A-Za-z0-9? ,_-]+$",message = "Name accepts only Alphanumeric value")
    @Column(nullable = false)
    private String name;

    @NotBlank(message = "Banner or Image is Mandatory")
    @NotEmpty(message = "Banner or Image Required")
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
