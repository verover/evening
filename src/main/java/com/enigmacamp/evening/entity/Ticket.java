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

import com.fasterxml.jackson.annotation.JsonManagedReference;

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
    String id;

    @Column(name="event_id", nullable = false)
    String eventId;
    
    @Column(nullable = false)
    String title;
    
    String description;
    
    @Column (nullable = false)
    Integer price;
    
    @Column (nullable = false)
    Integer stock;
    
    @Column (nullable = false)
    Integer minAmmount;
    
    @Column (nullable = false)
    Integer maxAmmount;

    @OneToMany(targetEntity = TicketDetail.class, fetch = FetchType.EAGER)
    @JsonManagedReference
    List<TicketDetail> ticketDetails;

    @CreatedDate
    Date createdAt;

    @UpdateTimestamp
    Date updatedAt;

    Boolean isDeleted;

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
