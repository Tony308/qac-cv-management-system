package com.qa.tests.unit.service.tests;

import com.qa.domain.User;
import com.qa.repository.UserRepository;
import com.qa.service.UserService;
import com.qa.utility.BCryptUtil;
import com.qa.utility.JwtUtil;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class UserServicesTests {

    @Mock
    private UserRepository userRepository;

    @Mock
    private JwtUtil tokenUtil;

    @Mock
    private BCryptUtil cryptUtil;

    @InjectMocks
    private UserService userService;

    private User user;
    private Optional<User> foundUser;
    final private String username = "user";
    final private String pwd = "test";

    public UserServicesTests() {
    }

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        foundUser = Optional.empty();
    }

    @Test
    public void testCreateUserCREATED() {

        when(userRepository.findByUsername(username)).thenReturn(foundUser);

        ResponseEntity<String> actual = userService.createUser(username, pwd);

        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .buildAndExpand().toUri();

        verify(userRepository).findByUsername(username);

        ResponseEntity<?> expected = ResponseEntity.created(location).build();

        assertEquals(expected, actual);
    }

    @Test
    public void testCreateUserCONFLICT() {

        user = new User(username, pwd);
        foundUser = Optional.of(user);

        when(userRepository.findByUsername(username)).thenReturn(foundUser);

        ResponseEntity<?> actual = userService.createUser(username, pwd);

        verify(userRepository).findByUsername(username);

        ResponseEntity<?> expected =
                new ResponseEntity<>("Username already exists.", HttpStatus.CONFLICT);

        assertEquals(expected, actual);
    }

    @Test
    public void testLoginFail() {

        when(userRepository.findByUsername(username))
                .thenReturn(foundUser);

        ResponseEntity<?> actual = userService.authenticateUser(username, pwd);

        verify(userRepository).findByUsername(username);

        ResponseEntity<?> expected =
                new ResponseEntity<>("Incorrect credentials", HttpStatus.UNAUTHORIZED);

        assertEquals(expected, actual);

    }

    @Test
    public void testLoginWrongPwd() {

        user = new User(username, pwd);
        foundUser = Optional.of(user);

        when(userRepository.findByUsername(username))
                .thenReturn(foundUser);

        ResponseEntity<String> expected =
                new ResponseEntity<>("Incorrect credentials", HttpStatus.UNAUTHORIZED);

        ResponseEntity actual = userService.authenticateUser(username, "wrong");

        verify(userRepository).findByUsername(username);

        assertEquals(expected, actual);
    }

    @Test
    public void testLoginServiceACCEPTED() throws JSONException {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

        String hashedPwd = encoder.encode(pwd);
        user = new User(username, hashedPwd);
        foundUser = Optional.of(user);

        when(userRepository.findByUsername(username)).thenReturn(foundUser);
        when(cryptUtil.verifyPwd(pwd, hashedPwd)).thenReturn(true);

        ResponseEntity<?> actual = userService.authenticateUser(username, pwd);

        verify(userRepository).findByUsername(username);
        verify(cryptUtil).verifyPwd(pwd, hashedPwd);

        JSONObject body = new JSONObject();
        body.put("message", "Login Successful");

        ResponseEntity expected =
                ResponseEntity.status(HttpStatus.ACCEPTED).body(body.toString());

        assertEquals(expected, actual);
    }

}
