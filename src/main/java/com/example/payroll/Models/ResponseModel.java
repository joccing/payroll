package com.example.payroll.Models;

import java.util.List;

public class ResponseModel {

    private List<EmployeeView> results;

    public ResponseModel(List<EmployeeView> results) {
        this.results = results;
    }

    public List<EmployeeView> getResults() {
        return results;
    }

    public void setResults(List<EmployeeView> results) {
        this.results = results;
    }
}
