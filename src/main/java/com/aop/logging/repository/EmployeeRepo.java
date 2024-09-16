package com.aop.logging.repository;

import com.aop.logging.model.Employee;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class EmployeeRepo {


    public List<Employee> getEmployeeList() {
//        Employee employee1 = new Employee(1001, "J0E");
//        Employee employee2 = new Employee(1002, "DAVE");
//
        List<Employee> employeeList = new ArrayList<>();
//
//        employeeList.add(employee1);
//        employeeList.add(employee2);
        return employeeList;
    }
}