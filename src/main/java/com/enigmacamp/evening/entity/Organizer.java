package com.enigmacamp.evening.entity;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;


@Entity
@Table(name = "mst_organizer")
public class Organizer {

    @Id
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid")

    private String id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String address;

    @Column(nullable = false)
    private String organization_email;

    @Column(nullable = false)
    private String organization_phone;

    @Column(nullable = false)
    private String website;

    @Column(nullable = false)
    private String location;


    public Organizer(String id, String name, String address, String organization_email, String organization_phone, String website,String location) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.organization_email = organization_email;
        this.organization_phone = organization_phone;
        this.website = website;
        this.location = location;
    }

    public Organizer() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getOrganization_email() {
        return organization_email;
    }

    public void setOrganization_email(String organization_email) {
        this.organization_email = organization_email;
    }

    public String getOrganization_phone() {
        return organization_phone;
    }

    public void setOrganization_phone(String organization_phone) {
        this.organization_phone = organization_phone;
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }
}
