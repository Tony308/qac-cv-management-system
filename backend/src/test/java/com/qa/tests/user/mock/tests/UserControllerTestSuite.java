package com.qa.tests.user.mock.tests;

import com.qa.controller.UserController;
import com.qa.repository.UserRepository;
import com.qa.service.UserService;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class UserControllerTestSuite {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserController userController;

    @Mock
    private UserService userService;

    final private String username = "username";
    final private String pwd = "password";

    private ResponseEntity<String> actual, expected;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @After
    public void tearDown() {

    }

    @Test
    public void testCreateAccountSuccess() {

        when(userService.createUser(username, pwd))
                .thenReturn(
                        new ResponseEntity<>(HttpStatus.CREATED)
                );

        actual = userController.createAccount(username, pwd);

        verify(userService).createUser(username, pwd);

        expected = new ResponseEntity<>(HttpStatus.CREATED);
    }

    @Test
    public void testCreateAccFail() {

        when(userService.createUser(username, pwd))
                .thenReturn(
                        new ResponseEntity<>("Username already exists.", HttpStatus.CONFLICT)
                );

        actual = userController.createAccount(username, pwd);

        verify(userService).createUser(username, pwd);
        expected = new ResponseEntity<>("Username already exists.", HttpStatus.CONFLICT);

        assertEquals(expected, actual);
    }


    @Test
    public void testAuthenticationSuccess() {
        when(userService.authenticateUser(username, pwd))
                .thenReturn(new ResponseEntity<>("Login Successful", HttpStatus.ACCEPTED)
        );

        actual = userController.authenticateLogin(username, pwd);

        verify(userService).authenticateUser(username, pwd);

        expected = new ResponseEntity<>("Login Successful", HttpStatus.ACCEPTED);

        assertEquals(expected, actual);

    }

    @Test
    public void testAuthenticationFail() {

        when(userService.authenticateUser(username, pwd))
                .thenReturn(
                        new ResponseEntity<>("Incorrect credentials", HttpStatus.UNAUTHORIZED)
                );

        actual = userController.authenticateLogin(username, pwd);

        verify(userService).authenticateUser(username, pwd);

        expected = new ResponseEntity<>("Incorrect credentials", HttpStatus.UNAUTHORIZED);

        assertEquals(expected, actual);


    }
}
