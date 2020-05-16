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
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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

        String uri = "http://localhost:8081/cvsystem/login";
        RequestBuilder request = MockMvcRequestBuilders
                .post(uri)
                .param("username", "user")
                .param("password", "test");

        MvcResult actual = mockMvc.perform(request)
                .andExpect(status().isAccepted())
                .andExpect(content().string("Login Successful"))
                .andReturn();

        HttpServletResponse response = actual.getResponse();

        assertEquals(HttpStatus.ACCEPTED.value(), response.getStatus());
        assertEquals("Login Successful", actual.getResponse().getContentAsString());


    }


    @Test
    public void testCreateUser() throws Exception {
        String uri = "http://localhost:8081/cvsystem/create-account";

        RequestBuilder request = MockMvcRequestBuilders
                .post(uri)
                .param("username", "test")
                .param("password", "password")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON);

        MvcResult actual = mockMvc.perform(request)
                .andExpect(status().isCreated())
                .andReturn();

        assertEquals(201, actual.getResponse().getStatus());
        assertEquals("", actual.getResponse().getContentAsString());

    }
}
