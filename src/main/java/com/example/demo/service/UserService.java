package com.example.demo.service;

import com.example.demo.model.Employee;
import com.example.demo.model.User;
import com.example.demo.repository.UserRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;


import java.util.List;
import java.util.Optional;
import java.util.regex.Pattern;
 import java.util.regex.Matcher;

@Service

public class UserService {
    private final UserRepository userRepository;
    public UserService(UserRepository userRepository){
        this.userRepository = userRepository;

    }
    public Optional<User> findUserByEmailUserNamePhoneNo(String identifier)
    {
        return userRepository.findByEmailOrPhoneNoOrUserName(identifier, identifier, identifier);
    }
    public List<User> getAllUser() {
        return userRepository.findAll();
    }
    //for pagination sorting
    public List<User> findUserBySorting(String field){
        return userRepository.findAll(Sort.by(Sort.Direction.ASC,field));
    }
    //for pagination
    public Page<User> findUserByPagination(int offset,int pageSize){
        Page<User> users = userRepository.findAll(PageRequest.of(offset,pageSize));
        return users;
    }
    //pagination with sorting
    public Page<User> findUserByPaginationAndSorting(int offset,int pageSize,String field){
        Page<User> users = userRepository.findAll(PageRequest.of(offset,pageSize).withSort(Sort.by(field)));
        return users;
    }
    public int updateUser(User user){
        return userRepository.updateUserDetails(user.getFullName(), user.getAddress(), user.getPhoneNo(), user.getPassword(), user.getEmail());
    }
    public int deleteUser(String email)
    {
        return userRepository.deleteUserByEmail(email);
    }
    public boolean existsByUserName(String userName) {
        return userRepository.findByUserName(userName).isPresent();
    }
    public boolean existsByPhoneNo(String phoneNo) {
        return userRepository.findByPhoneNo(phoneNo).isPresent();
    }
    public boolean existsByEmail(String email) {
        return userRepository.findByEmail(email).isPresent();
    }
    public boolean isPasswordValid(String password){
        String passwordPattern = "^(?=.*[A-Z])(?=.*[@#$%^&+=]).{8,36}$";
        Pattern pattern = Pattern.compile(passwordPattern);
        return pattern.matcher(password).matches();
    }
    public boolean isUserNameValid(String userName){
        String usernamePattern = "^[A-Z][a-zA-Z]{2,}$";
        Pattern pattern = Pattern.compile(usernamePattern);
        Matcher matcher = pattern.matcher(userName);
        return matcher.matches();
    }
    public boolean isFullNameValid(String userName){
        String fullNamePattern = "^[A-Z][a-zA-Z]{2,}$";
        Pattern pattern = Pattern.compile(fullNamePattern);
        Matcher matcher = pattern.matcher(fullNamePattern);
        return matcher.matches();
    }
    public boolean isPhoneNoValid(String phoneNo){
        String phoneNoPattern = "^98\\d{8}$";
        Pattern pattern = Pattern.compile(phoneNoPattern);
        Matcher matcher = pattern.matcher(phoneNo);
        return matcher.matches();
    }
    public Optional<User> findUserByEmail(String email)
    {
        return userRepository.findByEmail(email);
    }
    public  String saveUserDetails(User user){
        userRepository.save(user);
        return "Insert successfully";
    }


}
