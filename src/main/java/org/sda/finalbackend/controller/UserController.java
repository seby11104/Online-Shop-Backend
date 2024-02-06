package org.sda.finalbackend.controller;

import org.sda.finalbackend.entity.User;
import org.sda.finalbackend.errors.InvalidEmailOrUsernameException;
import org.sda.finalbackend.service.UserService;
import org.sda.finalbackend.utils.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {
    @Autowired
    private UserService userService;

    public UserController(UserService userService){
        this.userService=userService;
    }
    @PostMapping("/user")
    public ResponseEntity<ApiResponse>createUser(@RequestBody User user){
        try{

           User userDb= this.userService.createUser(user);
            ApiResponse response = new ApiResponse.Builder()
                    .status(200)
                    .message("User saved with success")
                    .data(userDb)
                    .build();
           return ResponseEntity.ok(response);

    }catch (InvalidEmailOrUsernameException invalidEmailOrUsernameException){
            ApiResponse response = new ApiResponse.Builder()
                    .status(400)
                    .message(invalidEmailOrUsernameException.getMessage())
                    .data(null)
                    .build();

            return ResponseEntity.status(HttpStatusCode.valueOf(400)).body(response);
        }
    }


}
