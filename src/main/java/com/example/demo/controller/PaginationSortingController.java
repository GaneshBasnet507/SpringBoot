package com.example.demo.controller;

import com.example.demo.model.User;
import com.example.demo.service.UserService;
import com.example.demo.DTO.APIResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@SpringBootApplication
@RestController
@RequestMapping("/users")
public class PaginationSortingController {

    @Autowired
    private UserService userService;

    @GetMapping
    public APIResponse<List<User>> getUsers() {
        List<User> allUsers = userService.getAllUser();
        return new APIResponse<>(allUsers.size(), allUsers);
    }
    @GetMapping("/{field}")
    public APIResponse<List<User>> getUsersWithSorting(@PathVariable String field) {
        List<User> allUsers = userService.findUserBySorting(field);
        return new APIResponse<>(allUsers.size(), allUsers);
    }
    @GetMapping("/pagination/{offset}/{pageSize}")
    public APIResponse<Page<User>> getUsersWithPagination(@PathVariable int offset, @PathVariable int pageSize) {
        Page<User> allUsersWithPagination = userService.findUserByPagination(offset,pageSize);
        return new APIResponse<>(allUsersWithPagination.getSize(), allUsersWithPagination );
    }
    @GetMapping("/paginationAndSorting/{offset}/{pageSize}/{field}")
    public APIResponse<Page<User>> getUsersWithPaginationAndSorting(@PathVariable int offset, @PathVariable int pageSize,@PathVariable String field) {
        Page<User> allUsersWithPaginationAndSort = userService.findUserByPaginationAndSorting(offset,pageSize,field);
        return new APIResponse<>(allUsersWithPaginationAndSort.getSize(), allUsersWithPaginationAndSort );
    }
}
