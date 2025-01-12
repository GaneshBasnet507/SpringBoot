package com.example.demo.service;

import com.example.demo.model.Employee;
import com.example.demo.model.User;
import com.example.demo.repository.RoleRepository;
import com.example.demo.repository.UserRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
//import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.regex.Pattern;
 import java.util.regex.Matcher;

@Service

public class UserService {
    private final UserRepository userRepository;
//    @Autowired
//    private PasswordEncoder passwordEncoder;
    @Autowired
    private RoleRepository roleRepository;
    public UserService(UserRepository userRepository){
        this.userRepository = userRepository;

    }
    public Optional<User> findUserByEmailUserNamePhoneNo(String identifier)
    {
        return userRepository.findByEmailOrPhoneNoOrUserName(identifier, identifier, identifier);
    }
    public Optional<User> findByUser(String username)
    {
        return userRepository.findByUserName(username);
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
    public int updateUser(User user,HttpServletRequest request){
        HttpSession session = request.getSession(false);
        if(session == null){
            System.out.println("Session does not exist");
            throw new IllegalArgumentException("Session does not exist. You have to login first");
        }
        String userName = (String) session.getAttribute("username");
        Set<String> roles = (Set<String>) session.getAttribute("role");
        if(userName == null || roles == null || !roles.contains("USER")){
            throw new IllegalArgumentException("You donot have User role privileges.");}
        if(!userName.equals(user.getUserName())){
            throw new IllegalArgumentException("You donot have Authorized .");
        }
        return userRepository.updateUserDetails(user.getFullName(), user.getUserName(),user.getAddress(), user.getPhoneNo(), user.getPassword(), user.getEmail());
    }
    public int deleteUser(String email, HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session == null) {
            System.out.println("Session does not exist");
            throw new IllegalStateException("Session does not exist. User might not be logged in.");
        }

        // Retrieve the username and roles from the session
        String adminUsername = (String) session.getAttribute("username");
        Set<String> roles = (Set<String>) session.getAttribute("role");

        // Log the session information for debugging
        System.out.println("Session ID: " + session.getId());
        System.out.println("Session Username: " + adminUsername);
        System.out.println("Session Roles: " + roles);

        // Check if the session contains valid username and roles
        if (adminUsername == null || roles == null || !roles.contains("ADMIN")) {
            throw new IllegalArgumentException("User does not have admin privileges.");
        }

        // Proceed with deleting the user from the repository
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
//        user.setPassword(passwordEncoder.encode(user.getPassword())); // Encode password
        userRepository.save(user);
        return "Insert successfully";
    }
    //for users roles
    public User createUser(User user)
    {
        return userRepository.save(user);
    }


}
