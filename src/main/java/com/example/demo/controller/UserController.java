package com.example.demo.controller;

import com.example.demo.DTO.UpdatePassword;
import com.example.demo.DTO.UpdateUsername;
import com.example.demo.model.Roles;
import com.example.demo.model.User;
import com.example.demo.service.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping("api/userDetails")
public class UserController {
    private final UserService userService;
    @Autowired
    private EmailService emailservice;
    @Autowired
    private CodeStoreService codeStoreService;
    //    @Autowired
//    private PasswordEncoder passwordEncoder;
    @Autowired
    private final RoleService roleService;
    private CodeGenerator codeGenerator;

    public UserController(UserService userService, RoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }

    @GetMapping("")
    public String print() {
        return "testing ";
    }

    @PostMapping("/save")
    public String saveUser(@RequestBody User user) {
        userService.saveUserDetails(user);
        return "successfully save";
    }

    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody User user) {
        if (userService.existsByEmail(user.getEmail())) {
            return ResponseEntity.badRequest().body("Email already exists");
        }
        if (userService.existsByUserName(user.getUserName())) {
            return ResponseEntity.badRequest().body("Username already exists");
        }
        if (userService.existsByPhoneNo(user.getPhoneNo())) {
            return ResponseEntity.badRequest().body("Phone number already exists");
        }
        if (!userService.isPasswordValid(user.getPassword())) {
            return ResponseEntity.badRequest().body("Password is invalid");
        }
        if (!userService.isPhoneNoValid(user.getPhoneNo())) {
            return ResponseEntity.badRequest().body("PhoneNo is invalid");
        }
        if (!userService.isUserNameValid(user.getUserName())) {
            return ResponseEntity.badRequest().body("Username is invalid");
        }
//        if (!userService.isFullNameValid(user.getFullName())) {
//            return ResponseEntity.badRequest().body("Full name is invalid");
//        }
        Set<Roles> roles = new HashSet<>();

        // Check role from the user request, for example default is "USER"
        String role = user.getRole();
        if (role.equalsIgnoreCase("ADMIN")) {
            Roles adminRole = roleService.findByName("ADMIN"); // Fetch "ADMIN" role from the database
            roles.add(adminRole);
        } else {
            Roles userRole = roleService.findByName("USER"); // Fetch "USER" role from the database
            roles.add(userRole);
        }

        // Set roles for the user
        user.setRoles(roles);
//        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userService.saveUserDetails(user);
        return ResponseEntity.ok("User registered successfully");
    }

    @PostMapping("/create-user")
    public ResponseEntity<String> createUser(@RequestBody User user) {
        if (userService.existsByUserName(user.getUserName())) {
            return ResponseEntity.badRequest().body("Username already exists");
        }
        if (userService.existsByEmail(user.getUserName())) {
            return ResponseEntity.badRequest().body("Email already exists");
        }
        if (!userService.isPasswordValid(user.getPassword())) {
            return ResponseEntity.badRequest().body("Password is invalid");
        }
        Set<Roles> roles = new HashSet<>();
        String role = user.getRole();
        if (role.equalsIgnoreCase("ADMIN")) {
            Roles adminRole = roleService.findByName("ADMIN");
            roles.add(adminRole);
        } else {
            Roles userRole = roleService.findByName("USER");
            roles.add(userRole);
        }
        user.setRoles(roles);
        userService.saveUserDetails(user);
        return ResponseEntity.ok("User registered successfully");

    }

    @PostMapping("/generateCode")
    public ResponseEntity<String> generateCode(@RequestParam String email) {
        Optional<User> userOptional = userService.findUserByEmail((email));
        if (userOptional.isPresent()) {
            String code = codeGenerator.generateCode();
            codeStoreService.storeCode(email, code);
            emailservice.sendEmail(email, "your verification code:", "your code:" + code);
            return ResponseEntity.ok("code sent via email");
        } else {
            return ResponseEntity.status(404).body("email not found");
        }
    }

    @PostMapping("/updateUsername")
    public ResponseEntity<String> verifyCode(@RequestBody UpdateUsername request) {
        String storedCode = codeStoreService.getCode(request.getEmail());
        if (storedCode != null && storedCode.equals(request.getCode())) {
            codeStoreService.removeCode(request.getEmail());
            Optional<User> userOptional = userService.findUserByEmail(request.getEmail());
            if (userOptional.isPresent()) {
                User user = userOptional.get();
                user.setUserName(request.getNewUsername());
                userService.saveUserDetails(user);
                return ResponseEntity.ok("Username updated successfully.");
            } else {
                return ResponseEntity.status(404).body("User not found.");
            }
        } else {
            return ResponseEntity.status(400).body("Invalid or expired code.");
        }
    }

    @PostMapping("/updatePassword")
    public ResponseEntity<String> verifycodeforPassword(@RequestBody UpdatePassword request) {
        String storedCode = codeStoreService.getCode(request.getEmail());
        if (storedCode != null && storedCode.equals(request.getCode())) {
            codeStoreService.removeCode(request.getEmail());
            Optional<User> userOptional = userService.findUserByEmail(request.getEmail());
            if (userOptional.isPresent()) {
                User user = userOptional.get();
                user.setPassword(request.getNewPassword());
                userService.saveUserDetails(user);
                return ResponseEntity.ok("password updated successfully.");
            } else {
                return ResponseEntity.status(404).body("User not found.");
            }
        } else {
            return ResponseEntity.status(400).body("Invalid or expired code.");
        }
    }

    @PostMapping("/login")
    public ResponseEntity<String> authenticate(@RequestBody Map<String, String> loginData, HttpServletRequest request) {
        String identifier = loginData.get("identifier");
        String password = loginData.get("password");
        Optional<User> existingUser = userService.findUserByEmailUserNamePhoneNo(identifier);
        if (existingUser.isPresent()) {
            User foundUser = existingUser.get();
            if (foundUser.getPassword().equals(password)) {
                HttpSession session = request.getSession(true);
                session.setAttribute("username", identifier);
                session.setAttribute("role", foundUser.getRoles().stream()
                        .map(Roles::getName)
                        .collect(Collectors.toSet()));
                System.out.println("Session Created: " + session.getId());
                System.out.println("Session Username after login: " + session.getAttribute("username"));
                System.out.println("Session Roles after login: " + session.getAttribute("role"));
                return ResponseEntity.ok("Login successful");
            } else {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid password");
            }
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("You are not registered");
    }

    @GetMapping("/search")
    public ResponseEntity<?> search(@RequestParam String username) {
        Optional<User> existingUser = userService.findByUser(username);
        if (existingUser.isPresent()) {

            return ResponseEntity.ok(existingUser.get());
        } else {
            return ResponseEntity.badRequest().body("User Doesnot Exits");
        }

    }

    @PostMapping("/logout")
    public ResponseEntity<String> logout(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session != null) {
            session.invalidate();
            return ResponseEntity.ok("Successfully logout");
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("You are not login");
        }

    }

    @PostMapping("/changePassword")
    public ResponseEntity<String> changePassword(@RequestBody Map<String, String> password) {
        String identifier = password.get("identifier");
        String oldPassword = password.get("oldPassword");
        String newPassword = password.get("newPassword");
        Optional<User> existingUser = userService.findUserByEmailUserNamePhoneNo(identifier);
        if (existingUser.isPresent()) {
            User foundUser = existingUser.get();
            if (foundUser.getPassword().equals(oldPassword)) {
                if (!userService.isPasswordValid(newPassword)) {
                    return ResponseEntity.badRequest().body("Password is invalid");
                }
                foundUser.setPassword(newPassword);
                userService.saveUserDetails(foundUser);
                emailservice.sendEmailForPassport(identifier, "your passport sucessfully", " password changed");
                return ResponseEntity.ok("Successfully change password");
            } else {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid password");
            }
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("You are not registered");
    }

    @PostMapping("/update")
    public ResponseEntity<String> updateUser(@RequestBody User user, HttpServletRequest request) {
        int result = userService.updateUser(user, request);
        if (result > 0) {
            return ResponseEntity.ok("User details update successfully");
        } else {
            return ResponseEntity.status(400).body("failed to update user details");
        }
    }

    @GetMapping("/getUserList")
    public List<User> getUser() {
        return userService.getAllUser();

    }

    @DeleteMapping("/delete")
    public ResponseEntity<String> deleteUser(@RequestParam String email, HttpServletRequest request) {

        try {
            int result = userService.deleteUser(email, request);
            if (result > 0) {
                return ResponseEntity.ok("User deleted successfully");
            } else {
                return ResponseEntity.status(400).body("Failed to delete user");
            }
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(e.getMessage());
        }
    }

}

