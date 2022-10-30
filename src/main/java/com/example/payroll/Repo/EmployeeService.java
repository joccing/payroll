package com.example.payroll.Repo;

import com.example.payroll.Models.Employee;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class EmployeeService {

    @Autowired
    private EmployeeRepository employeeRepository;

/*    public List<Employee> findAll() {

        List<Employee> result = employeeRepository.findAll();
        return DataMapper.removeID(result);
    }

    /*public List<Employee> findBySalaryGreaterThanEqual(float min){
        return employeeRepository.findBySalaryGreaterThanEqual(min);
    }*/

    public List<Employee> findBySalaryBetweenWithPagination(float min, float max, Pageable pageable) {
        return employeeRepository.findBySalaryBetweenWithPagination( min, max, pageable).getContent();
    }
}
