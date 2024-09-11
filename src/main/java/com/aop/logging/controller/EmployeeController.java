package com.aop.logging.controller;

import com.aop.logging.model.Employee;
import com.aop.logging.service.EmployeeService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@AllArgsConstructor
public class EmployeeController {

//    @Autowired
    EmployeeService service;


    @GetMapping("/getEmployees")
    public ResponseEntity<List<Employee>> getEmployee(){
        List<Employee> result = service.getEmployeeService();
        if(result.isEmpty()){
            throw new NullPointerException();
        }
        return  new ResponseEntity<>(result, HttpStatus.FOUND);
    }
}



