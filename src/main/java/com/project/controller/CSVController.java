package com.project.controller;

import com.project.model.Employee;
import com.project.model.dto.EmployeeDto;
import com.project.service.EmployeeCSVService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@CrossOrigin("http://localhost:8080")
@Controller
@RequestMapping("/api/employee")
public class CSVController {

    @Autowired
    EmployeeCSVService employeeCSVService;

    @PostMapping(consumes = "application/json")
    public ResponseEntity<Employee> addEmployee(@RequestBody EmployeeDto employee) {

        return new ResponseEntity<>(employeeCSVService.save(employee), HttpStatus.CREATED);
    }

    @PutMapping(value = "/{id}", consumes = "application/json")
    public ResponseEntity<Employee> updateEmployee(@PathVariable(name = "id") String id, @RequestBody Employee employee) {

        if(null != employeeCSVService.update(id, employee)) {
            return new ResponseEntity<>(employeeCSVService.update(id, employee), HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping()
    public ResponseEntity<List<Employee>> getAllEmployees() {
        try {
            List<Employee> employees = employeeCSVService.getAllEmployees();

            if (employees.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }

            return new ResponseEntity<>(employees, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(value = "/{fieldType}/{fieldTypeValue}")
    public ResponseEntity<List<Employee>> getEmployeeByType(@PathVariable(name = "fieldType") String employeeFieldType,
                                                            @PathVariable(name = "fieldTypeValue") String employeeFieldTypeValue) {
        if (employeeFieldType.isEmpty() || employeeFieldTypeValue.isEmpty()) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
        try {
            List<Employee> employees = new ArrayList<>();
            employees = getEmployees(employeeFieldType, employeeFieldTypeValue, employees);

            if (employees.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }

            return new ResponseEntity<>(employees, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(value = "/{fieldType}/{fieldTypeValue}/{count}")
    public ResponseEntity<List<Employee>> getEmployeeByTypeWithCount(@PathVariable(name = "fieldType") String employeeFieldType,
                                                                     @PathVariable(name = "fieldTypeValue") String employeeFieldTypeValue,
                                                                     @PathVariable(name = "count") Long count) {
        if (employeeFieldType.isEmpty() || employeeFieldTypeValue.isEmpty()) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
        try {
            List<Employee> employees = new ArrayList<>();
            employees = getEmployees(employeeFieldType, employeeFieldTypeValue, employees);

            if (employees.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }

            return new ResponseEntity<>(employees.stream().limit(count).collect(Collectors.toList()), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping(value = "delete/{fieldType}/{fieldTypeValue}")
    public ResponseEntity<Object> deleteEmployeeByType(@PathVariable(name = "fieldType") String employeeFieldType,
                                                       @PathVariable(name = "fieldTypeValue") String employeeFieldTypeValue) {
        if (employeeFieldType.isEmpty() || employeeFieldTypeValue.isEmpty()) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
        try {
            employeeCSVService.deleteEmployees(employeeFieldType, employeeFieldTypeValue);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    private List<Employee> getEmployees(String employeeFieldType, String employeeFieldTypeValue, List<Employee> employees) {
        if (employeeFieldType.equalsIgnoreCase("id")) {
            employees = employeeCSVService.getEmployeeById(employeeFieldTypeValue);
        } else if (employeeFieldType.equalsIgnoreCase("name")) {
            employees = employeeCSVService.getEmployeeByName(employeeFieldTypeValue);
        } else if (employeeFieldType.equalsIgnoreCase("age")) {
            employees = employeeCSVService.getEmployeeByAge(employeeFieldTypeValue);
        } else if (employeeFieldType.equalsIgnoreCase("experience")) {
            employees = employeeCSVService.getEmployeeByExperience(employeeFieldTypeValue);
        } else if (employeeFieldType.equalsIgnoreCase("doj")) {
            employees = employeeCSVService.getEmployeeByDOJ(employeeFieldTypeValue);
        }
        return employees;
    }


}
