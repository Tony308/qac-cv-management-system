package com.qa.tests.user.mock.tests;

import com.qa.controller.UserController;
import com.qa.domain.User;
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
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.servlet.http.HttpServletRequest;
import java.net.URI;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class UserServicesTestSuite {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;

    @Mock
    private UserController userController;

    private User user;
    private Optional<User> foundUser = Optional.empty();
    final private String username = "user";
    final private String pwd = "test";

    @Before
    public void setUp() {
        HttpServletRequest mockRequest = new MockHttpServletRequest();
        ServletRequestAttributes servletRequestAttributes = new ServletRequestAttributes(mockRequest);
        RequestContextHolder.setRequestAttributes(servletRequestAttributes);


        MockitoAnnotations.initMocks(this);
    }

    @After
    public void tearDown() {
        RequestContextHolder.resetRequestAttributes();

    }

    @Test
    public void testServiceCreateUserCREATED() {

        when(userRepository.findByUsername(username)).thenReturn(foundUser);

        ResponseEntity<String> actual = userService.createUser(username, pwd);

        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/create-account").buildAndExpand().toUri();

        verify(userRepository).findByUsername(username);

        ResponseEntity<String> expected = ResponseEntity.created(location).build();

        assertEquals(expected, actual);
    }

    @Test
    public void testCreateUserCONFLICT() {

        user = new User(username, pwd);
        foundUser = Optional.of(user);

        when(userRepository.findByUsername(username)).thenReturn(foundUser);

        ResponseEntity<?> actual = userService.createUser(username, pwd);

        verify(userRepository).findByUsername(username);

        ResponseEntity<?> expected = new ResponseEntity<>("Username already exists.", HttpStatus.CONFLICT);

        assertEquals(expected, actual);
    }

    @Test
    public void testLoginFail() {

        when(userRepository.findByUsernameAndPassword(username, pwd))
                .thenReturn(foundUser);

        ResponseEntity<?> actual = userService.authenticateUser(username, pwd);

        verify(userRepository).findByUsernameAndPassword(username, pwd);

        ResponseEntity<?> expected =
                new ResponseEntity<>("Incorrect credentials", HttpStatus.UNAUTHORIZED);

        assertEquals(expected, actual);

    }

    @Test
    public void testLoginWrongPwd() {

        user = new User(username, pwd);
        foundUser = Optional.of(user);

        when(userRepository.findByUsernameAndPassword(username, pwd))
                .thenReturn(foundUser);

        ResponseEntity<String> expected =
                new ResponseEntity<>("Incorrect credentials", HttpStatus.UNAUTHORIZED);

        ResponseEntity<String> actual = userService.authenticateUser(username, "wrong");

        verify(userRepository).findByUsernameAndPassword(username, "wrong");

        assertEquals(expected, actual);
    }

    @Test
    public void testLoginServiceACCEPTED() {

        user = new User(username, pwd);
        foundUser = Optional.of(user);

        when(userRepository.findByUsernameAndPassword(username, pwd)).thenReturn(foundUser);
        ResponseEntity<String> actual = userService.authenticateUser(username, pwd);
        verify(userRepository).findByUsernameAndPassword(username, pwd);

        ResponseEntity<String> expected =
                new ResponseEntity<>("Login Successful", HttpStatus.ACCEPTED);

        assertEquals(expected, actual);

    }

}
