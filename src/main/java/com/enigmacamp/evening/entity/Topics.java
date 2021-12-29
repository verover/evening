package com.enigmacamp.evening.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.util.Set;

@Data
@Entity
@Table(name = "mst_topics",uniqueConstraints={@UniqueConstraint(columnNames = {"name"})})

public class Topics {
    @Id
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "sytem-uuid",strategy = "uuid")
    @Column(name = "topics_id")
    private String topicsId;

    @NotBlank(message = "Name is Mandatory")
    @NotEmpty(message = "Name is Required")
    @Size(min = 5, max = 30)
    @Column(nullable = false ,unique = true)
    private String name;
//
//    @JsonIgnore
//    @OneToMany(targetEntity = Event.class, fetch = FetchType.EAGER)
//    @JsonManagedReference
//    private Set<Event> events;


}
