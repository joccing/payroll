package com.example.payroll.Repo;

import com.example.payroll.Models.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Component;

import java.util.List;
@Component
public interface EmployeeRepository extends JpaRepository<Employee, Long> {
    @Query(value = "SELECT * FROM employees t WHERE t.salary >= ?1", nativeQuery = true)
    List<Employee> findBySalaryGreaterThanEqual(float salary);
}
