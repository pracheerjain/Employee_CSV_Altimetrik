package com.project.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Component
public class EmployeeDto {

    @NonNull
    private String name;

    @NonNull
    private int age;

    @NonNull
    private int experience;

    @NonNull
    private LocalDate doj;

}
