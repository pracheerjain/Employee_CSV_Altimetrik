package com.project.model;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class Employee {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @NonNull
    private String name;

    @NonNull
    private int age;

    @NonNull
    private int experience;

    @NonNull
    private LocalDate doj;

    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("YYYY-MM-DD");

   public Employee(String name, int age, int experience, String doj) {
        this.doj = LocalDate.parse(doj,FORMATTER);
        this.experience = experience;
        this.age = age;
        this.name = name;
    }


}
