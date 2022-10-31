package com.example.payroll.Controllers;

import com.example.payroll.ErrorHandling.DataRetrievalException;
import com.example.payroll.Models.EmployeeView;
import com.example.payroll.Repo.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.server.ResponseStatusException;

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
    public ResponseEntity<List<EmployeeView>> getEmployees(
            @RequestParam (required = false, defaultValue = "0.0") String min,
            @RequestParam (required = false, defaultValue = "4000.0") String max,
            @RequestParam (required = false, defaultValue = "0") String offset,
            @RequestParam (required = false, defaultValue = "10000") String limit,
            @RequestParam (required = false, defaultValue = "none") String sort) throws ResponseStatusException {

        List<EmployeeView> employees;
        try {
            employees = employeeService.findBySalaryBetweenWithPagination(
                    Float.parseFloat(min), Float.parseFloat(max), Long.parseLong(offset), Integer.parseInt(limit), sort);
        }
        catch( DataRetrievalException e ){
            e.printStackTrace();
            throw new ResponseStatusException( HttpStatus.BAD_REQUEST, e.getMessage(), e );
        }
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(employees);
    }
}
