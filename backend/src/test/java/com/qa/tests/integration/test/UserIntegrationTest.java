package com.qa.tests.integration.test;

import com.qa.domain.User;
import com.qa.repository.UserRepository;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class UserIntegrationTest {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private MockMvc mockMvc;


    @Before
    public void setUp() throws Exception {
        HttpServletRequest mockRequest = new MockHttpServletRequest();
        ServletRequestAttributes servletRequestAttributes = new ServletRequestAttributes(mockRequest);
        RequestContextHolder.setRequestAttributes(servletRequestAttributes);

        userRepository.save(new User("user", "test"));
    }

    @After
    public void tearDown() {
        RequestContextHolder.resetRequestAttributes();
        userRepository.deleteAll();
    }


    @Test
    public void testUserLogin() throws Exception {

        RequestBuilder request = MockMvcRequestBuilders
                .post("/cvsystem/login")
                .param("username", "user")
                .param("password", "test");

        mockMvc.perform(request)
                .andExpect(status().isAccepted())
                .andExpect(content().string("Login Successful"))
                .andReturn();

    }

    @Test
    public void testCreateUser() throws Exception {

        List<User> list = userRepository.findAll();

        assertEquals(1, list.size());

        RequestBuilder request = MockMvcRequestBuilders
                .post("/cvsystem/create-account")
                .param("username", "test")
                .param("password", "password")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON);

        mockMvc.perform(request)
                .andExpect(status().isCreated())
                .andReturn();

        list = userRepository.findAll();
        assertEquals(2, list.size());

    }
}
