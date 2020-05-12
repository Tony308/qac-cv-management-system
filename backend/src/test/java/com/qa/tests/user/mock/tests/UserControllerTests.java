package com.qa.tests.user.mock.tests;

import com.qa.controller.UserController;
import com.qa.service.UserService;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.servlet.http.HttpServletRequest;
import java.net.URI;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(UserController.class)
public class UserControllerTests {

    @InjectMocks
    private UserController userController;

    @MockBean
    private UserService userService;

    @Autowired
    private MockMvc mockMvc;

    final private String username = "username";
    final private String pwd = "password";

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
    public void testCreateAccountSuccess() throws Exception {

        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/create-account").buildAndExpand().toUri();

        when(userService.createUser(username, pwd))
                .thenReturn(ResponseEntity.created(location).build());

        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .post("/cvsystem/create-account")
                .param("username", username)
                .param("password", pwd);

        MvcResult actual = mockMvc.perform(requestBuilder)
                .andExpect(status().isCreated())
                .andReturn();

        verify(userService).createUser(username, pwd);

        MockHttpServletResponse response = actual.getResponse();

        assertEquals(HttpStatus.CREATED.value(), response.getStatus());

    }

    @Test
    public void testCreateAccFail() throws Exception {

        when(userService.createUser(username, pwd))
                .thenReturn(
                        new ResponseEntity<>("Username already exists.", HttpStatus.CONFLICT)
                );

        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .post("/cvsystem/create-account")
                .param("username", username)
                .param("password", pwd);

        MvcResult actual = mockMvc.perform(requestBuilder)
                .andExpect(status().isConflict())
                .andReturn();

        verify(userService).createUser(username, pwd);

        MockHttpServletResponse response = actual.getResponse();

        assertEquals(HttpStatus.CONFLICT.value(), response.getStatus());

    }


    @Test
    public void testAuthenticationSuccess() throws Exception {

        when(userService.authenticateUser(username, pwd))
                .thenReturn(new ResponseEntity<>("Login Successful", HttpStatus.ACCEPTED)
        );

        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .post("/cvsystem/login")
                .param("username", username)
                .param("password", pwd)
                .contentType(MediaType.APPLICATION_JSON);

        MvcResult actual = mockMvc.perform(requestBuilder)
                .andExpect(status().isAccepted())
                .andReturn();

        verify(userService).authenticateUser(username, pwd);

        MockHttpServletResponse response = actual.getResponse();

        System.out.println(response.getContentAsString());

        assertEquals(HttpStatus.ACCEPTED.value(), response.getStatus());

    }

    @Test
    public void testAuthenticationFail() throws Exception {

        when(userService.authenticateUser(username, pwd))
                .thenReturn(new ResponseEntity<>(
                        "Incorrect credentials",
                        HttpStatus.UNAUTHORIZED)
                );

        RequestBuilder request = MockMvcRequestBuilders
                .post("/cvsystem/login")
                .param("username", username)
                .param("password", pwd);


        MvcResult actual = mockMvc.perform(request)
                .andExpect(status().isUnauthorized())
                .andExpect(content().string("Incorrect credentials"))
                .andReturn();

        verify(userService).authenticateUser(username, pwd);

    }
}