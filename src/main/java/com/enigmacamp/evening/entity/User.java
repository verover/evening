package com.enigmacamp.evening.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Table(name = "users", uniqueConstraints = { @UniqueConstraint(columnNames = { "email" }) })
public class User {

    @Id
    @Column
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(strategy = "uuid", name = "uuid")
    private String id;

    @Column
    @NotBlank(message = "User email cannot be null")
    private String email;

    @Column
    @NotNull(message = "Password cannot be null")
    private String password;

    @Column
    @NotBlank(message = "Name can not be blank")
    private String name;

    @JsonFormat(pattern = "yyyy-MM-dd")
    @Column(nullable = false)
    private Date birthDate;

    @Column(nullable = false)
    private Boolean active;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "user_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<Role> roles = new HashSet<>();

    @CreatedDate
    private Date createdAt;

    @LastModifiedDate
    private Date updatedAt;

    @PrePersist
    public void createdDate() {
        if (createdAt == null) createdAt = new Date();
        if (updatedAt == null) updatedAt = new Date();
    }

    @PreUpdate
    public void updatedDate() {
        updatedAt = new Date();
    }

    public void activate() {
        this.active = true;
    }

    public void deactivate() {
        this.active = false;
    }
}
