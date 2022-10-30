package com.example.payroll.Repo;

import com.example.payroll.Models.Employee;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class DataMapper {

    public static List<Employee> removeID( List<Employee> list){
        return list;
    }
}
