package com.example.payroll.Controllers;

import com.example.payroll.ErrorHandling.DataRetrievalException;
import com.example.payroll.ErrorHandling.DataSavingException;
import com.example.payroll.Models.Employee;
import com.example.payroll.Models.ResponseModel;
import com.example.payroll.Models.SuccessModel;
import com.example.payroll.Repo.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
public class ApiControllers {

    @Autowired
    private EmployeeService employeeService;

    @GetMapping(value = "/users")
    public ResponseEntity<ResponseModel> getEmployees(
            @RequestParam (required = false, defaultValue = "0.0") String min,
            @RequestParam (required = false, defaultValue = "4000.0") String max,
            @RequestParam (required = false, defaultValue = "0") String offset,
            @RequestParam (required = false, defaultValue = "10000") String limit,
            @RequestParam (required = false, defaultValue = "none") String sort) throws ResponseStatusException {

        ResponseModel results;
        try {
            results = new ResponseModel(employeeService.findBySalaryBetweenWithPagination(
                    Float.parseFloat(min), Float.parseFloat(max), Long.parseLong(offset), Integer.parseInt(limit), sort));
        }
        catch( DataRetrievalException e ){
            e.printStackTrace();
            throw new ResponseStatusException( HttpStatus.BAD_REQUEST, e.getMessage(), e );
        }
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(results);
    }

    @PostMapping(value = "/save")
    public ResponseEntity<SuccessModel> saveEmployee(@RequestBody Employee employee){

        try {
            employeeService.saveEmployee(employee);
        }catch( DataSavingException e ){
            e.printStackTrace();
            throw new ResponseStatusException( HttpStatus.BAD_REQUEST, e.getMessage(), e );
        }
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(new SuccessModel(1));
    }
}
