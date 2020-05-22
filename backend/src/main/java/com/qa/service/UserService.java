package com.qa.service;

import com.qa.domain.User;
import com.qa.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public ResponseEntity<String> createUser(String username, String password) {


    	try {
            Optional<User> foundUser = userRepository.findByUsername(username);

            if (foundUser.isPresent()){
	        	return new ResponseEntity<>("Username already exists.", HttpStatus.CONFLICT);
		    } else {
	    	    User user = new User(username, password);
				userRepository.save(user);

                URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                        .buildAndExpand().toUri();

                return ResponseEntity.created(location).build();
		    }
        } catch (Exception e) {
        	e.printStackTrace();
        	return ResponseEntity.badRequest().body(e.getMessage());
        }

    }

    public ResponseEntity<String> authenticateUser(String username, String password) {

        try {
            Optional<User> user = userRepository.findByUsernameAndPassword(username, password);

            if (user.isPresent()) {
                return ResponseEntity.accepted().body("Login Successful");
            }
        } catch (Exception e) {
        	e.printStackTrace();
        	return ResponseEntity.badRequest().body(e.getMessage());
        }

        return new ResponseEntity<>("Incorrect credentials", HttpStatus.UNAUTHORIZED);
    }
}
