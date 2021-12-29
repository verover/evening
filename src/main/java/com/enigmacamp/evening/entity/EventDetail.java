package com.enigmacamp.evening.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
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

    @Column(nullable = false)
    private Date date;

    @Column(nullable = false)
    private String location;
}
