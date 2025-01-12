package com.example.demo.repository;

import com.example.demo.model.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ReviewRepository extends JpaRepository<Review,Integer> {
    @Query("SELECT AVG(r.rating)FROM Review r WHERE r.books.id = :bookId")//JPQL
    Double findAverageRating(@Param("bookId") int bookId);
}
