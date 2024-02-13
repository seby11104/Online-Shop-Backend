package org.sda.finalbackend.controller;

import dto.LoginDto;
import dto.UserDto;
import org.aspectj.lang.annotation.Around;
import org.sda.finalbackend.entity.User;
import org.sda.finalbackend.errors.InvalidCredentialsException;
import org.sda.finalbackend.errors.InvalidEmailOrUsernameException;
import org.sda.finalbackend.errors.UserNotFoundException;
import org.sda.finalbackend.service.AuthService;
import org.sda.finalbackend.utils.ApiResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin("http://localhost:4200")
@RequestMapping("/auth")
public class AuthController {
    private AuthService authService;
    public AuthController(AuthService authService)
    {
        this.authService= authService;
    }

    @PostMapping("/login")
    public ResponseEntity<ApiResponse> login(@RequestBody LoginDto loginDto)
    {
        try {
            UserDto userDto=this.authService.login(loginDto.getEmail(), loginDto.getPassword());
            ApiResponse response = new ApiResponse.Builder()
                    .status(200)
                    .message("Welcome! "+ userDto.getUsername())
                    .data(userDto)
                    .build();
            return ResponseEntity.ok(response);
        }catch (InvalidCredentialsException | UserNotFoundException exception){
            ApiResponse response = new ApiResponse.Builder()
                    .status(400)
                    .message(exception.getMessage())
                    .data(null)
                    .build();
            return ResponseEntity.status(400).body(response);

        }
    }

    @PostMapping("/register")
    public ResponseEntity<ApiResponse> register(@RequestBody User userBody)
    {
        try {
            UserDto userDto=this.authService.register(userBody);
            ApiResponse response = new ApiResponse.Builder()
                    .status(200)
                    .message("Welcome! "+ userDto.getUsername())
                    .data(userDto)
                    .build();
            return ResponseEntity.ok(response);
        }catch (InvalidCredentialsException | InvalidEmailOrUsernameException exception){
            ApiResponse response = new ApiResponse.Builder()
                    .status(400)
                    .message(exception.getMessage())
                    .data(null)
                    .build();
            return ResponseEntity.status(400).body(response);

        }
    }


}
