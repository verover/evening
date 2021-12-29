package com.enigmacamp.evening.entity;

import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@Data
@Entity
@Table(name = "files")
public class File {
    @Id
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name="system-uuid",strategy = "uuid")
    @Column(name = "file_id",nullable = false)
    private String fileId;
    private String name;
    private String type;

    @Lob
    private byte[] data;

}
