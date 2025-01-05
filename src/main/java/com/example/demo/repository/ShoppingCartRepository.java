package com.example.demo.repository;

import com.example.demo.model.ShoppingCart;
import com.example.demo.model.User;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface ShoppingCartRepository extends JpaRepository<ShoppingCart, Integer> {
    @Transactional
    @Modifying
    @Query("DELETE FROM ShoppingCart u WHERE u.id = :id")
    int deleteByCartId(int id);

    @Query("SELECT u FROM User u WHERE u.userName = :userName")
    Optional<User> findByUserName(@Param("userName") String userName);
    @Query("SELECT u FROM ShoppingCart u WHERE u.user.id = :userId")
    Optional<ShoppingCart> findByUserId(@Param("userId") Long userId);
}
