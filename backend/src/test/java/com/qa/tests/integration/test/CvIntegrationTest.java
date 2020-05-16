package com.qa.tests.integration.test;

import com.qa.controller.CvController;
import com.qa.domain.Cv;
import com.qa.repository.ICvRepository;
import org.bson.BsonBinarySubType;
import org.bson.types.Binary;
import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest
@RunWith(SpringJUnit4ClassRunner.class)
@AutoConfigureMockMvc
public class CvIntegrationTest {

    @Autowired
    CvController cvController;

    @Autowired
    private ICvRepository cvRepository;

    @Autowired
    private MockMvc mockMvc;

    final private String[] data = new String[]{"Initial File\n" +
            "Feel free to delete this file when the database is setup.",
            "Other data"};

    final private Binary binary =
            new Binary(BsonBinarySubType.BINARY, data[0].getBytes());

    private MockMultipartFile file;
    private Cv mockCv;

    @Before
    public void setUp() throws Exception {
        HttpServletRequest mockRequest = new MockHttpServletRequest();
        ServletRequestAttributes servletRequestAttributes = new ServletRequestAttributes(mockRequest);
        RequestContextHolder.setRequestAttributes(servletRequestAttributes);

        file = new MockMultipartFile(
                "file",
                "fileName.pdf",
                "multipart/form-data",
                data[0].getBytes()
        );

        mockCv = new Cv("user", binary, file.getOriginalFilename());

        cvRepository.save(mockCv);
        mockCv = new Cv("user", binary, "MI5.pdf");
        cvRepository.save(mockCv);
    }

    @After
    public void tearDown() throws Exception {
        RequestContextHolder.resetRequestAttributes();
        cvRepository.deleteAll();
    }

    @Test
    public void testGetUserCvs() throws Exception  {

        RequestBuilder request = MockMvcRequestBuilders
                .get("/cvsystem/get")
                .param("name", "user")
                .contentType(MediaType.APPLICATION_JSON);

        MvcResult actual = mockMvc.perform(request)
                .andExpect(status().isOk())
                .andReturn();

        MockHttpServletResponse response = actual.getResponse();

        JSONArray jsonArray = new JSONArray(response.getContentAsString());

        List<Cv> cvList = cvRepository.findAllByName("user");

        assertEquals(2,jsonArray.length());
        assertEquals(cvList.size(),jsonArray.length());

        for (int x = 0; x < jsonArray.length(); x++) {

            JSONObject jsonObject = jsonArray.getJSONObject(x);
            Cv cvIndex = cvList.get(x);

            assertEquals(cvIndex.toString(), jsonObject.toString());
        }


    }
}
