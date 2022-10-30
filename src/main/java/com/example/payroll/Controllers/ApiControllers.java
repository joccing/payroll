package com.example.payroll.Controllers;

import com.example.payroll.Models.Employee;
import com.example.payroll.Repo.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@RestController
public class ApiControllers {

    @Autowired
    private EmployeeService employeeService;

    @GetMapping(value = "/")
    public String getPage(){
        return "Welcome Joc";
    }

    @GetMapping(value = "/users")
    public List<Employee> getEmployees(){
        return employeeService.findAll();
    }

    @GetMapping(value = "/users/salary")
    public List<Employee> getSalaryAtLeast(@RequestParam (required = false, defaultValue = "0.0") String min ){
        return employeeService.findBySalaryGreaterThanEqual(Float.valueOf(min));
    }
}
