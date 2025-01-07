package com.example.demo.service;

import com.example.demo.model.Books;
import com.example.demo.model.Order;
import com.example.demo.model.ShoppingCart;
import com.example.demo.model.User;
import com.example.demo.repository.BooksRepository;
import com.example.demo.repository.ShoppingCartRepository;
import com.example.demo.repository.UserRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
public class ShoppingCartService {
    @Autowired
    private final ShoppingCartRepository shoppingCartRepository;
    @Autowired
    private final UserRepository userRepository;
    @Autowired
    private final BooksRepository booksRepository;

    public ShoppingCartService(ShoppingCartRepository shoppingCartRepository, UserRepository userRepository, BooksRepository booksRepository) {
        this.shoppingCartRepository = shoppingCartRepository;
        this.userRepository = userRepository;
        this.booksRepository = booksRepository;
    }
    public List<ShoppingCart> getAll() {
        return shoppingCartRepository.findAll();
    }

    @Transactional
    public ShoppingCart addToCart(HttpServletRequest request, String title) {
        HttpSession session = request.getSession(false);
        if (session == null) {
            throw new IllegalArgumentException("Session does not exist. You have to login first.");
        }

        Optional<Books> bookOptional = booksRepository.findByTitle(title);
        if (!bookOptional.isPresent()) {
            throw new IllegalArgumentException("No book found with title: " + title);
        }
        Books book = bookOptional.get();

        String userName = (String) session.getAttribute("username");
        Optional<User> userOptional = userRepository.findByUserName(userName);
        if (!userOptional.isPresent()) {
            throw new IllegalArgumentException("No user found with username: " + userName);
        }
        User user = userOptional.get();

        Optional<ShoppingCart> cartOptional = shoppingCartRepository.findByUserId(user.getId());
        ShoppingCart cart;
        if (cartOptional.isPresent()) {
            cart = cartOptional.get();
            cart.getBooks().add(book);
            cart.setTotalAmount(cart.getTotalAmount() + book.getPrice());
        } else {
            cart = new ShoppingCart(user, new ArrayList<>(Collections.singletonList(book)), book.getPrice());
        }

        return shoppingCartRepository.save(cart);
    }


    public ShoppingCart removeBook(HttpServletRequest request, String title) {
        HttpSession session = request.getSession(false);
        if (session == null) {
            throw new IllegalArgumentException("Session does not exist. You have to login first.");
        }
        Optional<Books> bookOptional = booksRepository.findByTitle(title);
        if (!bookOptional.isPresent()) {
            throw new IllegalArgumentException("No book found with title: " + title);
        }
        Books book = bookOptional.get();
        String userName = (String) session.getAttribute("username");
        Optional<User> userOptional = userRepository.findByUserName(userName);
        if (!userOptional.isPresent()) {
            throw new IllegalArgumentException("No user found with username: " + userName);
        }
        User user = userOptional.get();
        Optional<ShoppingCart> cartOptional = shoppingCartRepository.findByUserId(user.getId());
        ShoppingCart cart;
        if (cartOptional.isEmpty()) {
            throw new IllegalArgumentException("No shopping cart found for user");
        }
        ShoppingCart cartValue = cartOptional.get();
        if (!cartValue.getBooks().remove(book)) {
            throw new IllegalArgumentException("Book not found in the cart");
        }
        cartValue.setTotalAmount(cartValue.getTotalAmount() - book.getPrice());
        return shoppingCartRepository.save(cartValue);
    }

    public ShoppingCart clearCart(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session == null) {
            throw new IllegalArgumentException("Session does not exist. You have to login first.");
        }
        String userName = (String) session.getAttribute("username");
        Optional<User> userOptional = userRepository.findByUserName(userName);
        if (!userOptional.isPresent()) {
            throw new IllegalArgumentException("No user found with username: " + userName);

        }
        User user = userOptional.get();
        Optional<ShoppingCart> shoppingCarts = shoppingCartRepository.findByUserId(user.getId());
        if (!shoppingCarts.isPresent()) {
            throw new IllegalArgumentException("No shopping cart found with username: " + userName);
        }
        ShoppingCart cart = shoppingCarts.get();
        cart.getBooks().clear();
        cart.setTotalAmount(0);
        return shoppingCartRepository.save(cart);


    }



}
//    public String saveBookDetails(ShoppingCart shoppingCart, HttpServletRequest request, String title) {
//        HttpSession session = request.getSession(false);
//        System.out.println(title);
//        System.out.println(shoppingCart);
//
//        if (session == null) {
//            throw new IllegalArgumentException("Session does not exist. You have to login first.");
//        }
//
//        String userName = (String) session.getAttribute("username");
//        Set<String> roles = (Set<String>) session.getAttribute("role");
//
//        if (userName == null || roles == null || !roles.contains("USER")) {
//            throw new IllegalArgumentException("You do not have USER role privileges.");
//        }
//
//        // Retrieve the user by username
//        Optional<User> userOptional = userRepository.findByUserName(userName);
//        if (!userOptional.isPresent()) {
//            throw new IllegalArgumentException("User not found.");
//        }
//
//        User user = userOptional.get();
//        shoppingCart.setUser(user);
//
//        // Retrieve the book by title
//        Optional<Books> bookOptional = booksRepository.findByTitle(title);
//        if (bookOptional.isPresent()) {
//            Books book = bookOptional.get();
//
//            // Add the book and recalculate the total
//            shoppingCart.addBook(book);
//
//            // Save the shopping cart
//            shoppingCartRepository.save(shoppingCart);
//            return "Book added to the cart successfully.";
//        } else {
//            return "Book not found.";
//        }
//    }


//public int deleteCart(int id, HttpServletRequest request) {
//    HttpSession session = request.getSession(false);
//    if (session == null) {
//        System.out.println("Session does not exist");
//        throw new IllegalStateException("Session does not exist. User might not be logged in.");
//    }
//    String adminUsername = (String) session.getAttribute("username");
//    Set<String> roles = (Set<String>) session.getAttribute("role");
//    if (adminUsername == null || roles == null || !roles.contains("ADMIN")) {
//        throw new IllegalArgumentException("User does not have admin privileges.");
//    }
//    return shoppingCartRepository.deleteByCartId(id);
//}


