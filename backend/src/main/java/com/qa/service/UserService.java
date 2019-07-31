package com.qa.service;

import com.mongodb.DuplicateKeyException;
import com.mongodb.MongoWriteException;
import com.qa.domain.User;
import com.qa.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public ResponseEntity<String> createUser(String username, String password) {
        try {
            User user = new User();
            user.setUsername(username);
            user.setPassword(password);
            userRepository.save(user);

            return new ResponseEntity<>(HttpStatus.CREATED);
        } catch(MongoWriteException e) {
            e.printStackTrace();
            return new ResponseEntity<>("Username in use.", HttpStatus.BAD_REQUEST);
        }
}

    public ResponseEntity<Object> authenticateUser(String username, String password) {

        Optional<User> user = userRepository.findByUsernameAndPassword(username, password);
        if (user.isPresent()) {
            return new ResponseEntity<>("Login Successful", HttpStatus.OK);
        }
        return new ResponseEntity<>("Incorrect credentials", HttpStatus.BAD_REQUEST);
    }
}
