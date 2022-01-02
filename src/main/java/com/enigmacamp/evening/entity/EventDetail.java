package com.enigmacamp.evening.entity;

import com.fasterxml.jackson.annotation.*;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;

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
