package com.enigmacamp.evening.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Data
@Entity
@Table(name="trx_event_detail")
public class EventDetail {

    @Id
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name="system-uuid",strategy = "uuid")
    @Column(nullable = false)
    private String eventDetailId;

    @ManyToOne(targetEntity = Event.class,fetch = FetchType.LAZY,cascade = CascadeType.ALL)
    @JoinColumn(name = "event_id")
    @JsonBackReference
    private Event event;

    @NotNull(message = "Please provide a date.")
    @JsonFormat(pattern = "dd-MM-yyyy")
    @Column(nullable = false)
    private Date date;

    @NotBlank(message = "Location is Mandatory")
    @NotEmpty(message = "Location is Required")
    @Column(nullable = false)
    private String location;

    @JsonIgnore
    private Boolean isDeleted = Boolean.FALSE;
}
