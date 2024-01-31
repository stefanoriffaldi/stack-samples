package com.example.cantinjectrepo.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@Entity
@Table
public class Staff {

    @Id
    @SequenceGenerator(
            name = "staff_sequence",
            sequenceName = "staff_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "staff_sequence"
    )
    private Long id;
    private String name;
    private String email;
    private String number;
    private LocalDate birthday;
    private Integer age;
    private String job;

    public Staff() {

    }

    public Staff(Long id, String name, String email, String number, LocalDate birthday, Integer age, String job) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.number = number;
        this.birthday = birthday;
        this.age = age;
        this.job = job;
    }

    public Staff(String name, String email, String number, LocalDate birthday, Integer age, String job) {
        this.name = name;
        this.email = email;
        this.number = number;
        this.birthday = birthday;
        this.age = age;
        this.job = job;
    }
}
