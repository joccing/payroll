package com.example.payroll.Repo;

import com.example.payroll.Models.Employee;
import com.example.payroll.Models.EmployeeView;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Component;

import java.util.List;
@Component
public interface EmployeeRepository extends JpaRepository<Employee, Long> {
    /*@Query(value = "SELECT * FROM employees t WHERE t.salary >= :min", nativeQuery = true)
    List<Employee> findBySalaryGreaterThanEqual(@Param("min") float min);*/

    @Query(value = "SELECT * FROM employees WHERE salary BETWEEN :min AND :max", nativeQuery = true)
    Page<EmployeeView> findBySalaryBetweenWithPagination(@Param("min") float min, @Param("max") float max, Pageable pageable);
}
