package com.enigmacamp.evening.entity;

import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Data
@Entity
@Table(name="mst_category",uniqueConstraints = {@UniqueConstraint(columnNames = {"name"})})
public class Category {
    @Id
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name="system-uuid",strategy = "uuid")
    @Column(name = "category_id")
    private String categoryId;


    @NotBlank(message = "Name is Mandatory")
    @NotEmpty(message = "Name is Required")
    @Size(min = 5, max = 30)
    @Column(nullable = false,unique = true)
    private String name;
//
//    @JsonIgnore
//    @OneToMany(targetEntity = Event.class, fetch = FetchType.EAGER)
//    @JsonManagedReference
//    private Set<Event> events;

}
