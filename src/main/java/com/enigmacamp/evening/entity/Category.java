package com.enigmacamp.evening.entity;

import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import javax.validation.constraints.*;


@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
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

}
