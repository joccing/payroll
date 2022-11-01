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
    }

    @Override
    public void run(String... arg) throws RuntimeException {
        csvFileStorageService.deleteAll();
        csvFileStorageService.init();
    }
}