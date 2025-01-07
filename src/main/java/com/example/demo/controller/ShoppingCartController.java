package com.example.demo.controller;

import com.example.demo.DTO.CartDto;
import com.example.demo.model.ShoppingCart;
import com.example.demo.service.BooksService;
import com.example.demo.service.ShoppingCartService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/shoppingCart")

public class ShoppingCartController {
    @Autowired
    private final ShoppingCartService shoppingCartService;
    @Autowired
    private final BooksService booksService;

    public ShoppingCartController(ShoppingCartService shoppingCartService, BooksService booksService) {
        this.shoppingCartService = shoppingCartService;
        this.booksService = booksService;
    }

    @PostMapping("/add-book")
    public ShoppingCart addToCart(@RequestBody Map<String, String> request, HttpServletRequest httpRequest) {
        String title = request.get("title");
        return shoppingCartService.addToCart(httpRequest, title);
    }
    @PostMapping("/remove-book")
    public ShoppingCart removeBook(@RequestBody Map<String, String> request, HttpServletRequest httpRequest) {
        String title = request.get("title");
        return shoppingCartService.removeBook(httpRequest, title);
    }
    @PostMapping("/clear-cart")
    public void clearCart(HttpServletRequest request){
        shoppingCartService.clearCart(request);

    }
    @GetMapping("/carts")
    public List<CartDto> getAllCart(){
        List<ShoppingCart> carts = shoppingCartService.getAll();
        return carts.stream().map(CartDto.mapToCarts).collect(Collectors.toList());
    }
//    @PostMapping("/add-book")
//    public ResponseEntity<String> register(@RequestBody ShoppingCart shoppingCart, String title, HttpServletRequest request) {
//        System.out.println(title);
//        shoppingCartService.saveBookDetails(shoppingCart, request, title);
//        System.out.println(title);
//        return ResponseEntity.ok("Book added successfully");
//    }
//    @DeleteMapping("/delete")
//    public ResponseEntity<String>deleteCart(@RequestParam int id,HttpServletRequest request) {
//
//        try {
//            int result = shoppingCartService.deleteCart(id,request);
//            if (result > 0) {
//                return ResponseEntity.ok("Book deleted successfully");
//            } else {
//                return ResponseEntity.status(400).body("Failed to delete user");
//            }
//        } catch (IllegalArgumentException e) {
//            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(e.getMessage());
//        }
//    }

}
