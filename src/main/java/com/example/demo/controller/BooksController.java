package com.example.demo.controller;

import com.example.demo.DTO.APIResponse;
import com.example.demo.DTO.BookDto;
import com.example.demo.DTO.OrderDto;
import com.example.demo.model.Books;
import com.example.demo.model.Order;
import com.example.demo.service.BooksService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/books")
public class BooksController {
    @Autowired
    private final BooksService booksService;

    public BooksController(BooksService booksService) {
        this.booksService = booksService;
    }

    @PostMapping("/add-book")
    public ResponseEntity<String> register(@RequestBody Books books, HttpServletRequest request) {
        booksService.saveBookDetails(books, request);
        return ResponseEntity.ok("Book added successfully");
    }

    @GetMapping("/books")
    public List<BookDto> getAllBooks() {
        List<Books> books = booksService.getAllBooks();
        return books.stream().map(BookDto:: mapToBooks).collect(Collectors.toList());
    }
        @PostMapping("/update")
        public ResponseEntity<String> updateBook (@RequestBody Books books, HttpServletRequest request){
            HttpSession session = request.getSession(false);
            int result = booksService.updateBook(books, request);
            if (result > 0) {
                return ResponseEntity.ok("Book details update successfully");
            } else {
                return ResponseEntity.status(400).body("failed to update book details");
            }
        }
        @DeleteMapping("/delete")
        public ResponseEntity<String> deleteBook ( @RequestParam int id, HttpServletRequest request){

            try {
                int result = booksService.deleteBook(id, request);
                if (result > 0) {
                    return ResponseEntity.ok("Book deleted successfully");
                } else {
                    return ResponseEntity.status(400).body("Failed to delete user");
                }
            } catch (IllegalArgumentException e) {
                return ResponseEntity.status(HttpStatus.FORBIDDEN).body(e.getMessage());
            }
        }
        @GetMapping("/search")
        public ResponseEntity<?> search (@RequestParam String identifier){
            System.out.println(identifier);
            List<Books> books = booksService.findByTitleAuthorGenre(identifier);

            if (!books.isEmpty()) {
                return ResponseEntity.ok(books);
            } else {
                return ResponseEntity.badRequest().body("No books found matching the search criteria");
            }
        }
        @GetMapping("/sorting/{field}")
        public APIResponse<List<Books>> getBooksWithSorting (@PathVariable String field){
            List<Books> allBooks = booksService.findBookBySorting(field);
            return new APIResponse<>(4, allBooks);
        }

    }


