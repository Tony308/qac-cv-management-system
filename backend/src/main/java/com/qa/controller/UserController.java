package com.qa.controller;

import com.qa.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@RestController
@RequestMapping("/cvsystem")
@CrossOrigin(origins = "*")
@Validated
public class UserController {

    @Autowired
    final private UserService userService;

    public UserController(UserService service) {
        this.userService = service;
    }

    @PostMapping("/create-account")
    public ResponseEntity<String> createAccount(
            @RequestParam("username") @NotBlank @Size(min = 5) String username,
            @RequestParam("password") @NotBlank @Size(min = 7) String password) {
        return userService.createUser(username, password);
    }

    @PostMapping("/login")
    public ResponseEntity<String> authenticateLogin(
            @RequestParam("username") @NotBlank String username,
             @RequestParam("password") @NotBlank String password) {
        return userService.authenticateUser(username, password);
    }
}
