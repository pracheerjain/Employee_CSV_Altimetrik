package com.project.repository;

import com.project.model.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface EmployeeRepo extends JpaRepository<Employee,Long> {

    @Query(value = "delete from Employee where :employeeFieldType = :employeeFieldTypeValue",nativeQuery = true)
    void deleteEmployeesByType(String employeeFieldType, String employeeFieldTypeValue);

}
