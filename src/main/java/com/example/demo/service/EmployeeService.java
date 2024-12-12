package com.example.demo.service;

import com.example.demo.model.Employee;
import com.example.demo.repository.EmployeeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmployeeService {
    private final EmployeeRepository employeeRepository;

    public EmployeeService(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    public String saveUser(Employee employee) {
        employeeRepository.save(employee);
        return "successfully save";
    }

    public List<Employee> getAllEmployees() {
        return employeeRepository.findAll();
    }
    public  Employee updateEmployee(Employee employee){
        if(employeeRepository.existsById(employee.getId())){
            return employeeRepository.save(employee);
        }
        else {
            throw new IllegalArgumentException("Employee not found");

        }
    }
}
