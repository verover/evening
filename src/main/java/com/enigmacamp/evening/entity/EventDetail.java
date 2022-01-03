package com.enigmacamp.evening.entity;

import com.fasterxml.jackson.annotation.*;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.lang.Nullable;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.util.Date;


@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
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
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Column(nullable = false)
    private Date date;

    private Boolean isOnline;

    private String location;

    private String link;

//    @JsonIgnore
    private Boolean isDeleted = Boolean.FALSE;
}
