package com.example.demo.service;

import com.example.demo.DTO.BookDto;
import com.example.demo.model.Books;
import com.example.demo.repository.BooksRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;

import static java.lang.Integer.parseInt;

@Service
public class BooksService {
    @Autowired
    private  final BooksRepository booksRepository;

    public BooksService(BooksRepository booksRepository) {
        this.booksRepository = booksRepository;
    }
    public  String saveBookDetails(Books books, HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session == null) {
            System.out.println("Session does not exist");
            throw new IllegalArgumentException("Session does not exist. You have to login first");
        }
        String userName = (String) session.getAttribute("username");
        Set<String> roles = (Set<String>) session.getAttribute("role");
        if(userName == null || roles == null || !roles.contains("ADMIN")){
            throw new IllegalArgumentException("You donot have ADMIN role privileges.");}
        else {

            booksRepository.save(books);
            return "Insert successfully";
            }
    }
    @Transactional
    public int updateBook(Books books, HttpServletRequest request){
        HttpSession session = request.getSession(false);
        if(session == null){
            System.out.println("Session does not exist");
            throw new IllegalArgumentException("Session does not exist. You have to login first");
        }
        String userName = (String) session.getAttribute("username");
        System.out.println(userName);
        Set<String> roles = (Set<String>) session.getAttribute("role");
        if(userName == null || roles == null || !roles.contains("ADMIN")){
            throw new IllegalArgumentException("You don't have Admin role privileges.");
        }
        int quantity = parseInt(books.getQuantity());
        double price = books.getPrice();
        return booksRepository.updateBookDetails(books.getId(),books.getTitle(),books.getAuthor(), books.getGenre(), price, quantity);
    }
    public int deleteBook(int id, HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session == null) {
            System.out.println("Session does not exist");
            throw new IllegalStateException("Session does not exist. User might not be logged in.");
        }
        String adminUsername = (String) session.getAttribute("username");
        Set<String> roles = (Set<String>) session.getAttribute("role");
        if (adminUsername == null || roles == null || !roles.contains("ADMIN")) {
            throw new IllegalArgumentException("User does not have admin privileges.");
        }
        return booksRepository.deleteUserById(id);
    }
    public List<Books>findByTitleAuthorGenre(String title)
    {
        return booksRepository.findByTitleAuthorGenre(title,title,title);
    }

    public List<Books> getAllBooks() {
        return booksRepository.findAll();
    }
    public List<Books> findBookBySorting(String field){
        return booksRepository.findAll(Sort.by(Sort.Direction.ASC,field));
    }
//    public List<Long> getBookId(@RequestBody String request) {
//        String title = request.get("title");
//        return booksService.findBookIdByTitle(title);
//    }
}
