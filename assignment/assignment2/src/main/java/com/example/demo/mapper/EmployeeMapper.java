package com.example.demo.mapper;

import com.example.demo.model.Employee;
import org.apache.ibatis.annotations.Mapper;


import java.util.List;

@Mapper
public interface EmployeeMapper {
    Employee selectEmployeeByName(String name);
    List<Employee> getAllEmployee();
}
