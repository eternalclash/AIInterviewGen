package com.example.aiinterviewgen.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter @Setter
public class Member {
    @Id @GeneratedValue
    private long id;
    private String name;
    private String password;
    private String phonenum;
    private String pattern;
    private String deviceId;
}
