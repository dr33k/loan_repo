package com.agicomputers.LoanAPI.controllers.user_controllers;

import com.agicomputers.LoanAPI.models.request.UserRequest;
import com.agicomputers.LoanAPI.models.response.UserResponse;
import org.springframework.web.bind.annotation.*;

import java.util.HashSet;

@RequestMapping("/user")
public interface UserController {
    
    @GetMapping
    public HashSet<UserResponse> getAllUsers();

    @GetMapping("/{userId}")
    public UserResponse getUser(@PathVariable String userId);

    @PostMapping
    public UserResponse createUser(@RequestBody UserRequest request);

    @DeleteMapping("/{userId}")
    public String deleteUser(@PathVariable String userId);

    @PutMapping("/{userId}")
    public UserResponse updateUser(@PathVariable String userId, @RequestBody UserRequest request);
}
