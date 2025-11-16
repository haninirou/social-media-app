package com.example.questionapp.controllers;

import com.example.questionapp.dataAccess.UserRepository;
import com.example.questionapp.entities.User;
import com.example.questionapp.exceptions.UserNotFoundException;
import com.example.questionapp.responses.UserResponse;
import com.example.questionapp.services.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/users")
public class UserController {

    private UserService userService;

    public UserController(UserService userService){  //constructor injection
        this.userService=userService;
    }

    @GetMapping
    public List<User> getAllUsers(){
        return userService.getAllUsers();
    }

    @PostMapping
    public User createUser(@RequestBody User user){
        return userService.createUser(user);
    }

    @GetMapping("/{userId}")
    public UserResponse getUserById(@PathVariable Long userId){  //we will create a custom exception (ideally in all methods). Custom exception = e.g. when checking a user, if the user is not in the DB we should return a not found error
        User user = userService.getUserById(userId);
        if(user==null){
            throw new UserNotFoundException();
        }
        return new UserResponse(user);
    }

    @PutMapping("/{userId}")   //update
    public User updateUserById(@PathVariable Long userId, @RequestBody User newUser){
        return userService.updateUserById(userId, newUser);
    }

    @DeleteMapping("/{userId}") //delete
    public void deleteUserById(@PathVariable Long userId){
        userService.deleteUserById(userId);
    }

    @GetMapping("/activity/{userId}")
    public List<Object> getUserActivityById(@PathVariable Long userId){
        return userService.getUserActivityById(userId);
    }

    /*@ResponseBody*/
    @ExceptionHandler(UserNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    private void handleUserNotFoundException(){ //we wrote a UserNotFoundException handler (this method runs when a user is not found) and defined what it returns. If we want, we can also add a body inside this handleUserNotFoundException function.

    }
}
