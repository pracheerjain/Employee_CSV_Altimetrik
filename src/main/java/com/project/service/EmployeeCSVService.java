package com.project.service;

import com.project.model.Employee;
import com.project.model.dto.EmployeeDto;
import com.project.repository.EmployeeRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class EmployeeCSVService {

    @Autowired
    EmployeeRepo repository;

    public Employee save(EmployeeDto employeeDto) {
        Employee employee = Employee.builder().name(employeeDto.getName()).age(employeeDto.getAge())
                .experience(employeeDto.getExperience()).doj(employeeDto.getDoj()).build();
        Employee employeeInserted = repository.save(employee);
        return employeeInserted;
    }

    public Employee update(String id,Employee employee) {

        Optional<Employee> existingEmployee = repository.findById(Long.valueOf(id));
        if(existingEmployee.isPresent()) {
            Employee updatedEmployee = existingEmployee.get();
            updatedEmployee.setName(employee.getName());
            updatedEmployee.setAge(employee.getAge());
            updatedEmployee.setExperience(employee.getExperience());
            updatedEmployee.setDoj(employee.getDoj());
            Employee employeeUpdated = repository.save(updatedEmployee);
            return employeeUpdated;
        }
        return null;
    }

    public List<Employee> getAllEmployees() {
        return repository.findAll();
    }


    public List<Employee> getEmployeeByName(String employeeName) {

        return repository.findAll().stream()
                .filter(s -> s.getName().contentEquals(employeeName))
                .collect(Collectors.toList());
    }

    public List<Employee> getEmployeeByAge(String employeeAge) {

        return repository.findAll().stream()
                .filter(s -> s.getAge() == Integer.parseInt(employeeAge))
                .collect(Collectors.toList());
    }

    public List<Employee> getEmployeeById(String employeeId) {

        Optional<Employee> employee = repository.findById(Long.valueOf(employeeId));
        if(employee.isPresent()){
            return Arrays.asList(employee.get());
        }
        return Collections.emptyList();
    }

    public List<Employee> getEmployeeByExperience(String employeeExp) {

        return repository.findAll().stream()
                .filter(s -> s.getExperience() == Integer.parseInt(employeeExp))
                .collect(Collectors.toList());
    }

    public List<Employee> getEmployeeByDOJ(String employeeDOJ) {

        return repository.findAll().stream()
                .filter(s -> s.getDoj().isEqual(LocalDate.parse(employeeDOJ)))
                .collect(Collectors.toList());
    }

    public void deleteEmployees(String employeeFieldType, String employeeFieldTypeValue) {

        repository.deleteEmployeesByType(employeeFieldType, employeeFieldTypeValue);
    }

}

