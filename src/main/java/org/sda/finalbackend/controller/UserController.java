package org.sda.finalbackend.controller;

import org.aspectj.lang.annotation.Around;
import org.sda.finalbackend.entity.User;
import org.sda.finalbackend.errors.InvalidEmailOrUsernameException;
import org.sda.finalbackend.errors.UserNotFoundException;
import org.sda.finalbackend.service.UserService;
import org.sda.finalbackend.utils.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin("http://localhost:4200")
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
    @PatchMapping("/user")
public  ResponseEntity<ApiResponse> updateUser(@RequestBody User user){
    try{

        User userDb= this.userService.upedateUser(user);
        ApiResponse response = new ApiResponse.Builder()
                .status(200)
                .message("User upedate  with succes")
                .data(userDb)
                .build();
        return ResponseEntity.ok(response);


    }catch (UserNotFoundException userNotFoundException){
        ApiResponse response = new ApiResponse.Builder()
                .status(400)
                .message(userNotFoundException.getMessage())
                .data(null)
                .build();

        return ResponseEntity.status(HttpStatusCode.valueOf(400)).body(response);
    }
    catch (InvalidEmailOrUsernameException invalidEmailOrUsernameException){
        ApiResponse response = new ApiResponse.Builder()
                .status(400)
                .message(invalidEmailOrUsernameException.getMessage())
                .data(null)
                .build();

        return ResponseEntity.status(HttpStatusCode.valueOf(400)).body(response);
    }

}

@DeleteMapping("/user/{id}")
public ResponseEntity<ApiResponse> deleteUser(@PathVariable("id") Integer id){
        try{
            userService.deleteUser(id);
            ApiResponse response = new ApiResponse.Builder()
                    .status(200)
                    .message("User delete with success")
                    .data(null)
                    .build();
            return  ResponseEntity.ok(response);

        }catch (UserNotFoundException userNotFoundException){
            ApiResponse response = new ApiResponse.Builder()
                    .status(400)
                    .message(userNotFoundException.getMessage())
                    .data(null)
                    .build();

            return ResponseEntity.status(HttpStatusCode.valueOf(400)).body(response);

        }
}

@GetMapping("/user")
public ResponseEntity<ApiResponse> getAllUsers()
{
    List<User> users = userService.findAll();
    ApiResponse response = new ApiResponse.Builder()
            .status(200)
            .message("User list")
            .data(users)
            .build();
    return ResponseEntity.ok(response);
}
    @GetMapping("/user/username")
    public ResponseEntity<ApiResponse> getByUsername(@RequestParam(name="username") String username) {

        try {

            User userDB = userService.findByUsername(username);
            ApiResponse response = new ApiResponse.Builder()
                    .status(200)
                    .message("Found by username")
                    .data(userDB)
                    .build();

            return ResponseEntity.ok(response);

        } catch (UserNotFoundException e) {
            ApiResponse response = new ApiResponse.Builder()
                    .status(400)
                    .message(e.getMessage())
                    .data(null)
                    .build();

            return ResponseEntity.status(HttpStatusCode.valueOf(400)).body(response);
        }

    }
    @GetMapping("/user/email")
    public ResponseEntity<ApiResponse> getByEmail(@RequestParam(name="email") String email){

        try {

            User userDB = userService.findByEmail(email);
            ApiResponse response = new ApiResponse.Builder()
                    .status(200)
                    .message("Found by uemail")
                    .data(userDB)
                    .build();

            return ResponseEntity.ok(response);

        } catch (UserNotFoundException e) {
            ApiResponse response = new ApiResponse.Builder()
                    .status(400)
                    .message(e.getMessage())
                    .data(null)
                    .build();

            return ResponseEntity.status(HttpStatusCode.valueOf(400)).body(response);
        }
    }

}
