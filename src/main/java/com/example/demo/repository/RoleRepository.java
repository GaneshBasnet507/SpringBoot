package com.example.demo.repository;
import com.example.demo.model.Roles;
import jakarta.persistence.criteria.CriteriaBuilder;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Roles,Integer>{
    Optional<Roles> findByName(String name);


}
