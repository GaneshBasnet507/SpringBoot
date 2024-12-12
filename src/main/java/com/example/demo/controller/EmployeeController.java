package com.example.demo.controller;

import com.example.demo.model.Employee;
import com.example.demo.service.EmployeeService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/user")
public class EmployeeController {

    private final EmployeeService employeeService;

    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }
    @GetMapping("")
    public String print(){
        return "testing ";
    }
    @GetMapping("/retrieve")
    public List<Employee> getAllUsers() {
        return  employeeService.getAllEmployees();
    }

    @PostMapping("/save")
    public String saveUserDetails(@RequestBody Employee employee) {
        employeeService.saveUser(employee);
        return "successfully save";
    }

//    @PostMapping("/delete")
//    public String deleteUser(@RequestBody Employee employee){
//        employeeService.hashCode();
//        return "Generate hashcode";
//    }

    @PostMapping("/update")
    public String updateEmployee(@RequestBody Employee employee){
        employeeService.updateEmployee(employee);
        return "Sucessfully update";
    }


}
