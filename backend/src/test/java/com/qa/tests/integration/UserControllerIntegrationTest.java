package com.qa.tests.integration;

import com.qa.domain.User;
import com.qa.repository.UserRepository;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration
public class UserControllerIntegrationTest {


    @Autowired
    private UserRepository userRepository;

    @Autowired
    private WebApplicationContext context;

    private MockMvc mockMvc;

    @Before
    public void setUp() throws Exception {
        mockMvc = MockMvcBuilders
                .webAppContextSetup(context)
                .build();
        HttpServletRequest mockRequest = new MockHttpServletRequest();
        ServletRequestAttributes servletRequestAttributes = new ServletRequestAttributes(mockRequest);
        RequestContextHolder.setRequestAttributes(servletRequestAttributes);
        userRepository.save(new User("username", "password"));
    }

    @After
    public void tearDown() throws Exception {

        RequestContextHolder.resetRequestAttributes();
        userRepository.deleteAll();
    }


    @Test
    public void testUserLogin() throws Exception {

        RequestBuilder request = MockMvcRequestBuilders
                .post("/cvsystem/login")
                .param("username", "username")
                .param("password", "password");

        mockMvc.perform(request)
                .andExpect(status().isAccepted())
                .andExpect(content().string("Login Successful"));

    }

    @Test
    public void testLoginUnauthorized() throws Exception {
        RequestBuilder request = MockMvcRequestBuilders
                .post("/cvsystem/login")
                .param("username", "username")
                .param("password", "wrongpassword");

        mockMvc.perform(request)
                .andExpect(status().isUnauthorized());
    }

    @Test
    public void testLoginBadRequest() throws Exception {

        RequestBuilder request = MockMvcRequestBuilders
                .post("/cvsystem/login")
                .param("username", "")
                .param("password", "");

        mockMvc.perform(request)
                .andExpect(status().isBadRequest());
    }

    @Test
    public void testCreateUser() throws Exception {

        List<User> list = userRepository.findAll();

        assertEquals(1, list.size());

        RequestBuilder request = MockMvcRequestBuilders
                .post("/cvsystem/create-account")
                .param("username", "username1")
                .param("password", "password1")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON);

        mockMvc.perform(request)
                .andExpect(status().isCreated());

        list = userRepository.findAll();
        assertEquals(2, list.size());

    }

    @Test
    public void testCreateUserConflict() throws Exception {

        RequestBuilder request = MockMvcRequestBuilders
                .post("/cvsystem/create-account")
                .param("username", "username")
                .param("password", "password")
                .contentType("application/json");

        mockMvc.perform(request)
                .andExpect(status().isConflict())
                .andExpect(content().string("Username already exists."));
    }

    @Test
    public void testCreateUserBadRequest() throws Exception {

        RequestBuilder request = MockMvcRequestBuilders
                .post("/cvsystem/create-account")
                .param("username","")
                .param("password", "");

        mockMvc.perform(request)
                .andExpect(status().isBadRequest());

        request = MockMvcRequestBuilders
                .post("/cvsystem/create-account")
                .param("username","test")
                .param("password", "user");

        mockMvc.perform(request)
                .andExpect(status().isBadRequest());

        request = MockMvcRequestBuilders
                .post("/cvsystem/create-account")
                .param("username","LONGERUSERNAME")
                .param("password", "test");

        mockMvc.perform(request)
                .andExpect(status().isBadRequest());

    }
}
