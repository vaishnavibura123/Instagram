package com.example.Instagram.controller;

import com.example.Instagram.model.User;
import com.example.Instagram.service.UserService;
import jakarta.annotation.Nullable;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class UserController {
    @Autowired
    UserService userService;

    @PostMapping("/user")
    public ResponseEntity saveUser(@RequestBody String userData) {
        User user = setUser(userData);
        int userId = userService.saveUser(user);
        return new ResponseEntity("User saved with id-" + userId, HttpStatus.CREATED);
    }

    @GetMapping("/user")
    public ResponseEntity getUser(@Nullable @RequestParam String userId) {
        JSONArray userDetails = userService.getUser(userId);
        return new ResponseEntity(userDetails.toString(), HttpStatus.OK);
    }

    private User setUser(String userData) {
        JSONObject jsonObject = new JSONObject(userData);
        User user = new User();
        user.setFirstName(jsonObject.getString("firstName"));
        user.setLastName(jsonObject.getString("lastName"));
        user.setAge(jsonObject.getInt("age"));
        user.setEmail(jsonObject.getString("email"));
        user.setPhoneNumber(jsonObject.getString("phoneNumber"));
        return user;
    }

    @PutMapping("/user/{userId}")
    public ResponseEntity updateUser(@PathVariable String userId, @RequestBody String userData) {
        User user = setUser(userData);
        userService.updateUser(user, userId);
        return new ResponseEntity("user updated", HttpStatus.OK);
    }
}
