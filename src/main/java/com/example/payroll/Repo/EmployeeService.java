package com.example.payroll.Repo;

import com.example.payroll.Controllers.ApiControllers;
import com.example.payroll.Models.Employee;
import com.example.payroll.Models.EmployeeView;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class EmployeeService {
    private static final Logger log = LoggerFactory.getLogger(ApiControllers.class);

    @Autowired
    private EmployeeRepository employeeRepository;

    public List<EmployeeView> findBySalaryBetweenWithPagination(float min, float max, long offset, int limit, String sort) throws RuntimeException {

        Pageable pageable;
        String src = sort.toLowerCase();
        if (src.matches("name") || src.matches("salary"))
            pageable = new OffsetBasedPageRequest( offset, limit, Sort.Direction.ASC, src );
        else
            pageable = new OffsetBasedPageRequest( offset, limit );

        if( min < 0 || max > 4000 )
            throw new RuntimeException("Allowed values for min and max are 0 and 4000.");

        return employeeRepository.findBySalaryBetweenWithPagination( min, max, pageable ).getContent();
    }

    public void saveOrUpdateEmployee(Employee employee) throws RuntimeException {

        float salary = employee.getSalary();
        String name = employee.getName();

        if( salary < 0 || salary > 4000)
            throw new RuntimeException("Salary range needs to be between 0 and 4000.");

        List<Employee> resultSearch = findByName(name);
        int size = resultSearch.size();

        if( size == 0 ){

            log.info("No identical name found.. proceeding to save");
            employeeRepository.saveAndFlush(employee);

        } else if (size == 1) {

            Employee employeeFound = resultSearch.get(0);
            log.info(employeeFound.getName() + " found at id = " + employeeFound.getId());
            log.info("1 identical name found.. updating record");

            employeeRepository.updateEmployee(employeeFound.getId(),employee.getName(),employee.getSalary());
            employeeRepository.flush();

        } else {
            throw new RuntimeException("Unexpected: " + size + " records found with name = " + name);
        }
    }

    private List<Employee> findByName(String name) throws RuntimeException {
        return employeeRepository.findByName(name);
    }
}
