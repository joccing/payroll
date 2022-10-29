package com.example.payroll.Controllers;

import com.example.payroll.Models.Employee;
import com.example.payroll.Repo.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@RestController
public class ApiControllers {

    @Autowired
    private EmployeeRepository employeeRepository;

    @GetMapping(value = "/")
    public String getPage(){
        return "Welcome Joc";
    }

    @GetMapping(value = "/users")
    public List<Employee> getEmployees(){
        return employeeRepository.findAll();
    }
}
