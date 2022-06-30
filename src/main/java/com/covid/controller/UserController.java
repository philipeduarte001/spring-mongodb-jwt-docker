package com.covid.controller;

import com.covid.document.User;
import com.covid.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<User>> getAllUser(){
        return ResponseEntity.ok().body(userService.getAllUsers());
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> getUserById(@PathVariable String id){
        return ResponseEntity.ok().body(userService.getUserById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<User> updateUser(@PathVariable String id, @RequestBody @Valid User user){
        user.setId(id);
        return ResponseEntity.ok().body(this.userService.updateUser(user));
    }

    @DeleteMapping("/{id}")
    public HttpStatus deleteUser(@PathVariable String id){
        this.userService.deleteUserById(id);
        return HttpStatus.OK;
    }
}
