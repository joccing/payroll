package com.example.payroll.Repo;

import com.example.payroll.ErrorHandling.DataRetrievalException;
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

    public List<EmployeeView> findBySalaryBetweenWithPagination(float min, float max, long offset, int limit, String sort) throws DataRetrievalException {

        Pageable pageable;
        String src = sort.toLowerCase();
        if (src.matches("name") || src.matches("salary"))
            pageable = new OffsetBasedPageRequest( offset, limit, Sort.Direction.ASC, src );
        else
            pageable = new OffsetBasedPageRequest( offset, limit );

        if( min < 0 || max > 4000 )
            throw new DataRetrievalException("Allowed values for min and max are 0 and 4000.");

        return employeeRepository.findBySalaryBetweenWithPagination( min, max, pageable ).getContent();
    }
}
