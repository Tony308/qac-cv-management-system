package com.qa.tests.integration;

import com.qa.domain.Cv;
import com.qa.domain.User;
import com.qa.repository.ICvRepository;
import com.qa.repository.UserRepository;
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
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration
public class CvControllerIntegrationTest {

    @Autowired
    private ICvRepository cvRepository;

    @Autowired
    private UserRepository userRepository;

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

    private RequestBuilder request;

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

        userRepository.save(new User("jimmy", "password"));

        mockCv = new Cv("1","jimmy", file.getOriginalFilename(), binary);
        cvRepository.save(mockCv);

        binary = new Binary(BsonBinarySubType.BINARY, data[1].getBytes());
        mockCv = new Cv("jimmy","MI5.pdf", binary);
        cvRepository.save(mockCv);
    }

    @After
    public void tearDown() throws Exception {
        RequestContextHolder.resetRequestAttributes();
        userRepository.deleteAll();
        cvRepository.deleteAll();
    }

    @Test
    public void testGetCv() throws Exception {

         request = MockMvcRequestBuilders
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
    public void testGetCv_NotFound() throws Exception {

        Optional<Cv> foundUser = cvRepository.findById("150");
        assertFalse(foundUser.isPresent());

         request = MockMvcRequestBuilders
                .get("/cvsystem/retrieve/{id}", 150);

        mockMvc.perform(request)
                .andExpect(status().isNotFound());
    }

    @Test
    public void testGetUserCvs() throws Exception  {

         request = MockMvcRequestBuilders
                .get("/cvsystem/get")
                .param("name", "jimmy")
                .contentType(MediaType.APPLICATION_JSON);

        MvcResult actual = mockMvc.perform(request)
                .andExpect(status().isOk())
                .andReturn();

        MockHttpServletResponse response = actual.getResponse();

        JSONArray jsonArray = new JSONArray(response.getContentAsString());

        List<Cv> cvList = cvRepository.findAllByName("jimmy");

        assertEquals(2,jsonArray.length());
        assertEquals(cvList.size(),jsonArray.length());

        for (int x = 0; x < jsonArray.length(); x++) {

            JSONObject jsonObject = jsonArray.getJSONObject(x);
            Cv cvIndex = cvList.get(x);

            assertEquals(cvIndex.toString(), jsonObject.toString());
        }
    }

    @Test
    public void testGetUserCvs_NotFound() throws Exception {

        String username = "something made up";
        List<Cv> cv = cvRepository.findAllByName(username);
        assertTrue(cv.isEmpty());

        request = MockMvcRequestBuilders
                .get("/cvsystem/get")
                .param("name", username);

        mockMvc.perform(request)
                .andExpect(status().isNotFound());

    }

    @Test
    public void testUploadCv() throws Exception{

        List<Cv> list = cvRepository.findAllByName("jimmy");

        assertEquals(2, list.size());

         request = multipart("/cvsystem/upload-cv")
                .file(file)
                .param("user", "jimmy")
                .param("fileName", file.getOriginalFilename())
                .contentType(MediaType.MULTIPART_FORM_DATA);


        mockMvc.perform(request)
                .andExpect(status().isCreated())
                .andExpect(content().string("File successfully uploaded"))
                .andReturn();

        list = cvRepository.findAllByName("jimmy");
        assertEquals(3, list.size());

        Cv cv = list.get(list.size() - 1);

        assertEquals(file.getOriginalFilename(), cv.getFileName());
        assertEquals("jimmy", cv.getName());
    }

    @Test
    public void testUploadCv_UserNotFound() throws Exception {

        Optional<User> found = userRepository.findByUsername("nobody");
        assertFalse(found.isPresent());

        request = MockMvcRequestBuilders
                .multipart("/cvsystem/upload-cv")
                .file(file)
                .param("user", "nobody")
                .param("fileName", file.getOriginalFilename());

        mockMvc.perform(request)
                .andExpect(status().isNotFound());
    }

    @Test
    public void testUploadCv_BadRequest() throws Exception {

        request = MockMvcRequestBuilders
                .multipart("/cvsystem/upload-cv")
                .file(file)
                .param("user", "")
                .param("fileName", file.getOriginalFilename());

        mockMvc.perform(request)
                .andExpect(status().isBadRequest());
        
        request = MockMvcRequestBuilders
                .multipart("/cvsystem/upload-cv")
                .file(file)
                .param("user", "jimmy")
                .param("fileName", "");

        mockMvc.perform(request)
                .andExpect(status().isBadRequest());

    }

    @Test
    @Ignore
    public void testUploadCv_ServerError() throws Exception {

        file = new MockMultipartFile("file",data[0].getBytes());
        request = MockMvcRequestBuilders.multipart("/cvsystem/upload-cv")
                .file(file)
                .param("user", "asfddsafasd")
                .param("fileName", "fsadfdasfasd");

        mockMvc.perform(request)
                .andExpect(status().isInternalServerError());
    }

    @Test
    public void testDeleteCv() throws Exception{

        Optional<Cv> found = cvRepository.findById("1");
        assertTrue(found.isPresent());

         request = MockMvcRequestBuilders
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
    public void testDeleteCv_NotFound() throws Exception {

        Optional<Cv> cv = cvRepository.findById("300");
        assertFalse(cv.isPresent());

        request = delete("/cvsystem/delete/{id}", 300);

        mockMvc.perform(request)
                .andExpect(status().isNotFound());

    }
    
    @Test
    public void testUpdateCv() throws Exception{

        Optional<Cv> found = cvRepository.findById("1");
        assertTrue(found.isPresent());

        file = new MockMultipartFile(
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

    @Test
    public void testUpdateCv_NotFound() throws Exception {

        Optional<Cv> found = cvRepository.findById("150");
        assertFalse(found.isPresent());

        file = new MockMultipartFile(
                "file",
                "Changed name.txt",
                "multipart/form-data",
                data[1].getBytes()
        );

        MockHttpServletRequestBuilder request =
                multipart("/cvsystem/update-cv/{id}", 150)
                .file(file)
                .param("fileName","random.pdf")
                .contentType("multipart/form-data");

        request.with(servletRequest -> {
            servletRequest.setMethod("PUT");
            return servletRequest;
        });

        mockMvc.perform(request)
                .andExpect(status().isNotFound())
                .andReturn();
    }

    @Test
    public void testUpdateCv_BadRequest() throws Exception {

        //Todo status 200 -> 400
        Optional<Cv> found = cvRepository.findById("1");
        assertTrue(found.isPresent());

        file = new MockMultipartFile(
                "file",
                "Changed name.txt",
                "multipart/form-data",
                data[1].getBytes()
        );

        MockHttpServletRequestBuilder request = multipart("/cvsystem/update-cv/1")
                .file(file)
                .param("fileName","")
                .contentType("multipart/form-data");

        request.with(servletRequest -> {
            servletRequest.setMethod("PUT");
            return servletRequest;
        });

        mockMvc.perform(request)
                .andExpect(status().isBadRequest())
                .andReturn();

        file = new MockMultipartFile(
                "wrongParam",
                "Changed name.txt",
                "multipart/form-data",
                data[1].getBytes()
        );

        request = multipart("/cvsystem/update-cv/1")
                .file(file)
                .param("fileName",file.getOriginalFilename())
                .contentType("multipart/form-data");

        request.with(servletRequest -> {
            servletRequest.setMethod("PUT");
            return servletRequest;
        });

        mockMvc.perform(request)
                .andExpect(status().isBadRequest());
    }
}
