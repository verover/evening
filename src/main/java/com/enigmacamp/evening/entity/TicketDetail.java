package com.enigmacamp.evening.entity;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonBackReference;

import org.hibernate.annotations.GenericGenerator;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data
@Entity
@Table(name = "trx_ticket_detail")
@NoArgsConstructor
@AllArgsConstructor
public class TicketDetail {
    @Id
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid")
    String id;
    
    @ManyToOne(targetEntity = Ticket.class, fetch = FetchType.LAZY)
    @JoinColumn(name = "ticket_id")
    @JsonBackReference
    Ticket ticket;

    @ManyToOne(targetEntity = EventDetail.class, fetch = FetchType.LAZY)
    @JoinColumn(name = "event_detail_id")
    @JsonBackReference
    EventDetail eventDetail;

    public TicketDetail(Ticket ticket, EventDetail eventDetail) {
        this.ticket = ticket;
        this.eventDetail = eventDetail;
    }
}
