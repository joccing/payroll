package com.example.payroll.Models;

import java.util.Objects;

import javax.persistence.*;

@Entity
@Table(name="employees")
public class Employee {

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private long id;

    @Column
    private String name;
    @Column
    private float salary;

    protected Employee() {

    }

    public Employee(String name, float salary) {
        super();
        this.name = name;
        this.salary = salary;
    }

    public long getId() {
        return this.id;
    }

    public String getName() {
        return this.name;
    }

    public float getSalary() {
        return this.salary;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setSalary(float salary) {
        this.salary = salary;
    }

    @Override
    public boolean equals(Object o) {

        if (this == o)
            return true;
        if (!(o instanceof Employee employee))
            return false;
        return Objects.equals(this.id, employee.id) && Objects.equals(this.name, employee.name)
                && Objects.equals(this.salary, employee.salary);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id, this.name, this.salary);
    }

    @Override
    public String toString() {
        return "Employee{" + "id=" + this.id + ", name='" + this.name + '\'' + ", salary='" + this.salary + '\'' + '}';
    }
}