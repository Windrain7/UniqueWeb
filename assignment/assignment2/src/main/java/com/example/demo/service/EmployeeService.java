package com.example.demo.service;

import com.example.demo.model.Employee;

import java.util.List;

public interface EmployeeService {

    Employee selectByName(String name);
    List<Employee> getAllEmployee();
}
