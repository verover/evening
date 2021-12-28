package com.enigmacamp.evening.entity;

import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonSetter;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.annotation.CreatedDate;

import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "mst_ticket")
@Data @NoArgsConstructor
public class Ticket {
    @Id
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid")
    private String id;

    @Column(name="event_id", nullable = false)
    private String eventId;
    
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
    private List<TicketDetail> ticketDetails;

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
}
