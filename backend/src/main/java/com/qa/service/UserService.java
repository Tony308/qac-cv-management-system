package com.qa.service;

import com.qa.domain.User;
import com.qa.jwt.JwtTokenUtil;
import com.qa.repository.UserRepository;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.ConstraintViolationException;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.net.URI;
import java.util.Optional;

@Service
@Validated
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JwtTokenUtil tokenUtil;

    public ResponseEntity<String> createUser (
            @NotBlank @Size(min = 5) String username,
            @NotBlank @Size(min = 7) String password)
            throws ConstraintViolationException {

    	try {
            Optional<User> foundUser = userRepository.findByUsername(username);
            if (foundUser.isPresent()) {
	        	return new ResponseEntity<>("Username already exists.", HttpStatus.CONFLICT);
		    }
            userRepository.save(new User(username, password));

            URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                    .buildAndExpand().toUri();
            return ResponseEntity.created(location).build();
        } catch (Exception e) {
        	e.printStackTrace();
        	return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    public ResponseEntity authenticateUser (@NotBlank String username, @NotBlank String password)
            throws ConstraintViolationException {
        try {
            Optional<User> user = userRepository.findByUsernameAndPassword(username, password);

            if (!user.isPresent()) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Incorrect credentials");
            }

            String token = tokenUtil.generateToken(username);

            JSONObject body = new JSONObject();
            body.put("message", "Login Successful");
            body.put("token", token);

            return ResponseEntity.accepted().body(body.toString());
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
