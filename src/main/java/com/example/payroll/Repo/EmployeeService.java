package com.example.payroll.Repo;

import com.example.payroll.Models.Employee;
import com.example.payroll.Models.EmployeeView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class EmployeeService {

    @Autowired
    private EmployeeRepository employeeRepository;

/*    public List<Employee> findAll() {

        List<Employee> result = employeeRepository.findAll();
        return DataMapper.removeID(result);
    }

    /*public List<Employee> findBySalaryGreaterThanEqual(float min){
        return employeeRepository.findBySalaryGreaterThanEqual(min);
    }*/

    public List<EmployeeView> findBySalaryBetweenWithPagination(float min, float max, long offset, int limit, String sort ) {

        Pageable pageable;
        String src = sort.toLowerCase();
        if (src.matches("name") || src.matches("salary"))
            pageable = new OffsetBasedPageRequest( offset, limit, Sort.Direction.ASC, src );
        else
            pageable = new OffsetBasedPageRequest( offset, limit );

        return employeeRepository.findBySalaryBetweenWithPagination( min, max, pageable ).getContent();
    }
}
