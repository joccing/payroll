package com.example.payroll.Controllers;

import com.example.payroll.Models.*;
import com.example.payroll.Repo.CSVFileStorageService;
import com.example.payroll.Repo.EmployeeService;
import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Path;

@RestController
public class ApiControllers {

    @Autowired
    private EmployeeService employeeService;

    @Autowired
    private CSVFileStorageService csvFileStorageService;

    @GetMapping(value = "/users")
    public ResponseEntity<ResponseModel> getEmployees(
            @RequestParam (required = false, defaultValue = "0.0") String min,
            @RequestParam (required = false, defaultValue = "4000.0") String max,
            @RequestParam (required = false, defaultValue = "0") String offset,
            @RequestParam (required = false, defaultValue = "10000") String limit,
            @RequestParam (required = false, defaultValue = "none") String sort) throws ResponseStatusException {

        ResponseModel results;
        results = new ResponseModel(employeeService.findBySalaryBetweenWithPagination(
                Float.parseFloat(min),
                Float.parseFloat(max),
                Long.parseLong(offset),
                Integer.parseInt(limit),
                sort));

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(results);
    }

    @PostMapping(value = "/save")
    public ResponseEntity<ResponseMessage> saveEmployee(@RequestBody Employee employee){

        // Only records with unique names are allowed
        employeeService.saveOrUpdateEmployee(employee,true);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(new ResponseMessage("Successfully saved " + employee.getName(),1));
    }

    @PostMapping(value = "/upload")
    public ResponseEntity<ResponseMessage> uploadFile(
            @RequestParam(value = "file") MultipartFile multipartFile) throws IOException, CsvValidationException {

        String message;

        // save csv to server store
        Path path = csvFileStorageService.save(multipartFile);

        // Check if file is valid CSV
        int size;
        int dbChanges=0;

        if( (size = csvFileStorageService.isValidCSV(path)) > 0 ) {

            // Store each line of the CSV into the DB
            File file = path.toFile();
            CSVReader reader = new CSVReader(new FileReader(file));

            reader.skip(1); // skip first line for titles
            String[] nextLine;
            Employee employee;
            float salary;

            while ((nextLine = reader.readNext()) != null) {

                salary = Float.parseFloat(nextLine[1]);
                if (salary >= 0.0f) { // skip if salary is negative
                    employee = new Employee(nextLine[0], salary);
                    employeeService.saveOrUpdateEmployee(employee, false);
                    dbChanges++;
                }
            }

            reader.close();
            employeeService.flush();
            csvFileStorageService.delete(path);
        }

        message = "Made " + dbChanges + " updates to database successfully. Skipped: " + (size - dbChanges);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(new ResponseMessage(message,1));
    }
}
