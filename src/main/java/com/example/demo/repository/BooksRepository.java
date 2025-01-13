package com.example.demo.repository;

import com.example.demo.model.Books;
import com.example.demo.model.User;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface BooksRepository extends JpaRepository<Books, Integer> {
        Optional<Books> findById(int id);

    @Transactional
    @Modifying
    @Query("UPDATE Books u SET u.title = :title, u.author = :author, u.genre = :genre, u.price = :price,u.quantity = :quantity where u.id = :id")
    int updateBookDetails(int id, String title, String author, String genre, double price, int quantity);

    @Transactional
    @Modifying
    @Query("DELETE FROM Books u WHERE u.id = :id")
    int deleteUserById(int id);

    @Query("SELECT u FROM Books u WHERE u.title LIKE %:title% OR u.author LIKE %:author% OR u.genre LIKE %:genre%")
    List<Books> findByTitleAuthorGenre(@Param("title") String title, @Param("author") String author, @Param("genre") String genre);
    @Query("SELECT  u FROM Books u  WHERE u.title = :title")
    Optional<Books> findByTitle(String title);
//    @Query("SELECT u.id FROM Books u WHERE u.title = :title")
//    List<Long> findById(@Param("title") String title);

}

