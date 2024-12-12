package com.example.demo.repository;

import com.example.demo.model.User;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Integer> {
    Optional<User> findByUserName(String userName);
    Optional<User> findByPhoneNo(String phoneNo);
    Optional<User> findByEmail(String email);
    Optional<User> findByPassword(String password);

    @Transactional
    @Modifying
    @Query("UPDATE User u SET u.fullName = :fullName, u.address = :address, u.phoneNo = :phoneNo, u.password = :password WHERE u.email = :email")
    int updateUserDetails(String fullName, String address, String phoneNo, String password, String email);
    @Transactional
    @Modifying
    @Query("DELETE FROM User u WHERE u.email = :email")
    int deleteUserByEmail(String email);
    @Query("SELECT  u FROM User u WHERE u.userName = ?1 OR u.phoneNo = ?2 OR u.email = ?3")
    Optional<User> findByEmailOrPhoneNoOrUserName(String userName, String phoneNo, String email);

}

