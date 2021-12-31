package com.enigmacamp.evening.entity;

import java.util.Date;
import java.util.List;
import java.util.Set;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonSetter;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.annotation.CreatedDate;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "mst_ticket")
@Setter @Getter @NoArgsConstructor @AllArgsConstructor
public class Ticket {
    @Id
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid")
    private String id;

    @ManyToOne(targetEntity = Event.class, fetch = FetchType.LAZY)
    @JoinColumn(name = "event_id")
    private Event event;
    
    @Column(nullable = false)
    private String title;
    
    private String description;
    
    @Column (nullable = false)
    private Integer price;
    
    @Column (nullable = false)
    private Integer stock;
    
    @Column (nullable = false)
    private Integer minAmmount;
    
    @Column (nullable = false)
    private Integer maxAmmount;

    @OneToMany(targetEntity = TicketDetail.class, fetch = FetchType.EAGER)
    @JsonManagedReference
    private Set<TicketDetail> ticketDetail;

    @CreatedDate
    @JsonIgnore
    @Column(updatable = false)
    private Date createdAt;

    @UpdateTimestamp
    @JsonIgnore
    private Date updatedAt;

    @JsonIgnore
    @JsonSetter
    private Boolean isDeleted;

    @PrePersist
    private void createdDate(){
        if(this.createdAt == null) this.createdAt = new Date();
        if(this.updatedAt == null) this.updatedAt = new Date();
        if(this.isDeleted == null) this.isDeleted = false;
    }

    @PreUpdate
    private void updatedDate(){
        this.updatedAt = new Date();
    }

    public Ticket(Event event, String title, String description, Integer price, Integer stock, Integer minAmmount,
                  Integer maxAmmount) {
        this.event = event;
        this.title = title;
        this.description = description;
        this.price = price;
        this.stock = stock;
        this.minAmmount = minAmmount;
        this.maxAmmount = maxAmmount;
    }

}
