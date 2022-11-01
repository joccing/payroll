package com.example.payroll;

import com.example.payroll.Repo.CSVFileStorageService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.annotation.Resource;

@SpringBootApplication
public class PayrollApplication implements CommandLineRunner {

    @Resource
    CSVFileStorageService csvFileStorageService;

    public static void main(String... args) {

        SpringApplication.run(PayrollApplication.class, args);

/*
        CSVFileStorageService csvFileStorageService = new CSVFileStorageService();
        csvFileStorageService.init();

        int lines = csvFileStorageService.isValidCSV("test.csv");
        if( lines >= 0)
            System.out.println("Valid CSV! lines=" + lines);
*/
    }

    @Override
    public void run(String... arg) throws RuntimeException {
        csvFileStorageService.deleteAll();
        csvFileStorageService.init();
    }
}