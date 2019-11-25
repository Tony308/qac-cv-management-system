package com.qa.controller;

import com.qa.service.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/cvsystem")
@CrossOrigin(origins = "*")
public class UserController {

    @Autowired
    private UserService userService;

    public UserController(UserService service) {
        this.userService = service;
    }

    @PostMapping("/create-account")
    public ResponseEntity<String> createAccount(@RequestParam("username") String username,
                                                    @RequestParam("password") String password) {

        return userService.createUser(username, password);
    }

    @PostMapping("/login")
    @ResponseBody
    public ResponseEntity<String> authenticateLogin(@RequestParam("username") String username,
                                                        @RequestParam("password") String password) {

        return userService.authenticateUser(username, password);
    }
}
