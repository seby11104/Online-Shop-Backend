package org.sda.finalbackend.controller;

import dto.CartDto;
import org.sda.finalbackend.entity.Cart;
import org.sda.finalbackend.errors.CartNotFoundException;
import org.sda.finalbackend.service.CartService;
import org.sda.finalbackend.utils.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin("http://localhost:4200")
public class CartController {

    @Autowired
    private CartService cartService;

    CartController(CartService cartService)
    {
        this.cartService=cartService;
    }

    @PostMapping("/cart")
    public ResponseEntity<ApiResponse> createCart(@RequestBody CartDto cartDtoBody)
    {
          try {
                Cart cart = this.cartService.createCart(cartDtoBody);
                ApiResponse  response = new ApiResponse.Builder()
                        .status(200)
                        .message("Cart created with succes")
                        .data(cart)
                        .build();
                return ResponseEntity.ok(response);
          }catch (Exception e){
                ApiResponse response = new ApiResponse.Builder()
                        .status(400)
                        .message(e.getMessage())
                        .data(null)
                        .build();
                return ResponseEntity.status(400).body(response);
          }
    }

    @GetMapping("/cart")
    public ResponseEntity<ApiResponse> getAllCarts()
    {
        List<Cart> cartList = this.cartService.getAllCarts();
        ApiResponse response = new ApiResponse.Builder()
                .status(200)
                .message("Aceasta este lista de cart-uri")
                .data(cartList)
                .build();
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("cart/{id}")
    public ResponseEntity<ApiResponse> deleteCart(@PathVariable("id") Integer id)
    {
        try {
            cartService.deleteCart(id);
            ApiResponse response = new ApiResponse.Builder()
                    .status(200)
                    .message("Cart sters cu succes")
                    .data(null)
                    .build();
            return ResponseEntity.ok(response);

        }catch (CartNotFoundException e)
        {
            ApiResponse response = new ApiResponse.Builder()
                    .status(400)
                    .message(e.getMessage())
                    .data(null)
                    .build();
            return ResponseEntity.status(400).body(response);
        }
    }

}
