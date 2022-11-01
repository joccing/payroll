package com.example.payroll.Controllers;

import com.example.payroll.Models.*;
import com.example.payroll.Repo.CSVFileStorageService;
import com.example.payroll.Repo.EmployeeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

@RestController
public class ApiControllers {

    private static final Logger log = LoggerFactory.getLogger(ApiControllers.class);

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
    public ResponseEntity<SuccessModel> saveEmployee(@RequestBody Employee employee){

        // Only records with unique names are allowed
        employeeService.saveOrUpdateEmployee(employee);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(new SuccessModel(1));
    }

    @PostMapping(value = "/upload")
    public ResponseEntity<ResponseMessage> uploadFile(
            @RequestParam(value = "file") MultipartFile file) {
        String message;
        try {
            csvFileStorageService.save(file);

            message = "Uploaded the file successfully: " + file.getOriginalFilename();
            return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage(message));
        } catch (Exception e) {
            message = "Could not upload the file: " + file.getOriginalFilename() + "!";
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(new ResponseMessage(message));
        }
    }
}
