package com.aop.logging.service;

import com.aop.logging.model.Employee;
import com.aop.logging.repository.EmployeeRepo;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class EmployeeService {

    EmployeeRepo repo;

    public List<Employee> getEmployeeService() {
        return repo.getEmployeeList();
    }

}
