package com.qa.tests.integration.test;

import com.qa.domain.Cv;
import com.qa.repository.ICvRepository;
import org.apache.tomcat.util.codec.binary.Base64;
import org.bson.BsonBinarySubType;
import org.bson.types.Binary;
import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration
public class CvIntegrationTest {

    @Autowired
    private ICvRepository cvRepository;

    @Autowired
    private WebApplicationContext context;

    private MockMvc mockMvc;

    final private String[] data = new String[]{"Initial File\n" +
            "Feel free to delete this file when the database is setup.",
            "Other data"};

    private Binary binary =
            new Binary(BsonBinarySubType.BINARY, data[0].getBytes());

    private MockMultipartFile file;
    private Cv mockCv;

    @Before
    public void setUp() throws Exception {
        mockMvc = MockMvcBuilders
                .webAppContextSetup(context)
                .build();

        HttpServletRequest mockRequest = new MockHttpServletRequest();
        ServletRequestAttributes servletRequestAttributes = new ServletRequestAttributes(mockRequest);
        RequestContextHolder.setRequestAttributes(servletRequestAttributes);

        file = new MockMultipartFile(
                "file",
                "fileName.pdf",
                "multipart/form-data",
                data[0].getBytes()
        );

        mockCv = new Cv("1","user", binary, file.getOriginalFilename());
        cvRepository.save(mockCv);

        binary = new Binary(BsonBinarySubType.BINARY, data[1].getBytes());
        mockCv = new Cv("user", binary, "MI5.pdf");
        cvRepository.save(mockCv);
    }

    @After
    public void tearDown() throws Exception {
        RequestContextHolder.resetRequestAttributes();
        cvRepository.deleteAll();
    }

    @Test
    public void testGetCv() throws Exception {

        RequestBuilder request = MockMvcRequestBuilders
                .get("/cvsystem/retrieve/1")
                .header("Content-Type", "application/json");

        MvcResult actual = mockMvc.perform(request)
                .andExpect(status().isOk())
                .andReturn();

        MockHttpServletResponse response  = actual.getResponse();

        JSONObject jsonObj = new JSONObject(response.getContentAsString());
        String encodeData = jsonObj.getJSONObject("cvFile").getString("data");

        byte[] content = Base64.decodeBase64(encodeData);
        String dbData = new String(content, StandardCharsets.UTF_8);

        Optional<Cv> found = cvRepository.findById("1");
        assertTrue(found.isPresent());
        Cv cv = found.get();

        String converted = new String(cv.getCvFile().getData());

        assertEquals(data[0], dbData);
        assertEquals(converted,dbData);
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

    @Test
    public void testUploadCv() throws Exception{

        List<Cv> list = cvRepository.findAllByName("user");

        assertEquals(2, list.size());

        RequestBuilder request = multipart("/cvsystem/upload-cv")
                .file(file)
                .param("user", "user")
                .param("fileName", file.getOriginalFilename())
                .contentType(MediaType.MULTIPART_FORM_DATA);


        MvcResult actual = mockMvc.perform(request)
                .andExpect(status().isCreated())
                .andExpect(content().string("File successfully uploaded"))
                .andReturn();

        list = cvRepository.findAllByName("user");
        assertEquals(3, list.size());

        Cv cv = list.get(list.size() - 1);

        assertEquals(file.getOriginalFilename(), cv.getFileName());
        assertEquals("user", cv.getName());
    }

    @Test
    public void testDeleteCv() throws Exception{

        Optional<Cv> found = cvRepository.findById("1");
        assertTrue(found.isPresent());

        RequestBuilder request = MockMvcRequestBuilders
                .delete("/cvsystem/delete/1")
                .contentType("application/json");

        mockMvc.perform(request)
                .andExpect(status().isOk())
                .andExpect(content().string("CV successfully deleted"))
                .andReturn();

        found = cvRepository.findById("1");
        assertFalse(found.isPresent());
    }

    @Test
    public void testUpdateCv() throws Exception{

        MockMultipartFile file = new MockMultipartFile(
                "file",
                "Changed name.txt",
                "multipart/form-data",
                data[1].getBytes()
        );

        MockHttpServletRequestBuilder request = multipart("/cvsystem/update-cv/1")
                .file(file)
                .param("fileName",file.getOriginalFilename())
                .contentType("multipart/form-data");

        request.with(servletRequest -> {
            servletRequest.setMethod("PUT");
            return servletRequest;
        });

        mockMvc.perform(request)
                .andExpect(status().isOk())
                .andExpect(content().string("CV successfully updated."))
                .andReturn();

    }
}
