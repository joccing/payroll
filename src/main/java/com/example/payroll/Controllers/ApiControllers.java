package com.example.payroll.Controllers;

import com.example.payroll.Models.Employee;
import com.example.payroll.Models.EmployeeView;
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

/*    @GetMapping(value = "/users")
    public List<Employee> getEmployees(){
        return employeeService.findAll();
    }

    @GetMapping(value = "/users/salary")
    public List<Employee> getSalaryAtLeast(@RequestParam (required = false, defaultValue = "0.0") String min ){
        return employeeService.findBySalaryGreaterThanEqual(Float.valueOf(min));
    }*/

    @GetMapping(value = "/users")
    public List<EmployeeView> getEmployees(
            @RequestParam (required = false, defaultValue = "0.0") String min,
            @RequestParam (required = false, defaultValue = "4000.0") String max,
            @RequestParam (required = false, defaultValue = "0") String offset,
            @RequestParam (required = false, defaultValue = "10000") String limit,
            @RequestParam (required = false, defaultValue = "none") String sort) {

        return employeeService.findBySalaryBetweenWithPagination(
                Float.parseFloat(min), Float.parseFloat(max), Long.parseLong(offset), Integer.parseInt(limit), sort);
    }
}
