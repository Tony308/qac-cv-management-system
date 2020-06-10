package com.qa.tests.integration.service;


import com.qa.domain.User;
import com.qa.repository.UserRepository;
import com.qa.service.UserService;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import javax.validation.ConstraintViolationException;
import java.util.Optional;

import static org.junit.Assert.assertEquals;

@SpringBootTest
@RunWith(SpringRunner.class)
public class UserServiceIntegrationTest {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserService userService;

    private User user;
    private Optional<User> foundUser = Optional.empty();
    final private String username = "jimmy";
    final private String pwd = "password";

    @Before
    public void setUp() throws Exception {
        user = new User(username, pwd);
        userRepository.save(user);

    }

    @After
    public void tearDown() throws Exception {
        userRepository.deleteAll();
    }

    @Test
    public void testLoginBadRequest() {
        ResponseEntity actual;
        try {
            actual = userService.authenticateUser("", "");
        } catch(ConstraintViolationException e) {
            actual = ResponseEntity.badRequest().body(e.getMessage());
        }
        assertEquals(HttpStatus.BAD_REQUEST, actual.getStatusCode());
    }

    @Test
    public void testLoginSuccess() {
        ResponseEntity actual = userService.authenticateUser(username, pwd);
        assertEquals(HttpStatus.ACCEPTED, actual.getStatusCode());
    }

    @Test
    public void testLoginUnauthorized() {
        ResponseEntity actual = userService.authenticateUser(username, "wrong");
        assertEquals(HttpStatus.UNAUTHORIZED, actual.getStatusCode());

    }

}
