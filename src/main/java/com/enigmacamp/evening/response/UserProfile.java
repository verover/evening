package com.enigmacamp.evening.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserProfile {
    private String id;
    private String email;
    private String name;
    private Date birthdate;
    private Boolean active;
    private Date cratedAt;
    private Date updateAt;
}
