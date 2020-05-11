package com.qa.tests.cv.mock.tests;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.qa.controller.CvController;
import com.qa.domain.Cv;
import com.qa.service.CvService;
import org.bson.BsonBinarySubType;
import org.bson.types.Binary;
import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.MultipartBodyBuilder;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.servlet.http.HttpServletRequest;
import java.net.URI;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(CvController.class)
public class CvControllerTestSuite {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    CvService cvService;

    @InjectMocks
    CvController cvController;


    final private String data = "Initial File\n" +
            "Feel free to delete this file when the database is setup.";

    final private Binary fileToBinaryStorage =
            new Binary(BsonBinarySubType.BINARY, data.getBytes());

    private Optional<Cv> foundCv = Optional.empty();

    private MultipartFile multipartFile;
    private Cv mockCv;

    @Before
    public void setUp() throws Exception {

        HttpServletRequest mockRequest = new MockHttpServletRequest();
        ServletRequestAttributes servletRequestAttributes = new ServletRequestAttributes(mockRequest);
        RequestContextHolder.setRequestAttributes(servletRequestAttributes);

        mockCv = new Cv("1","mock",
                fileToBinaryStorage,"MockFile.txt");

        MockitoAnnotations.initMocks(this);
    }

    @After
    public void tearDown() throws Exception {
        RequestContextHolder.resetRequestAttributes();

    }

    @Test
    @Ignore
    public void testGetCv() throws Exception {


//        when(cvService.getCV("1")).thenReturn(mockCv);

        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .get("/cvsystem/retrieve/1")
                .accept(MediaType.APPLICATION_JSON);

        MvcResult actual = mockMvc.perform(requestBuilder)
                .andExpect(status().isOk())
                .andReturn();

        verify(cvService).getCV("1");

        MockHttpServletResponse response = actual.getResponse();
        assertEquals(HttpStatus.OK.value(), response.getStatus());

//        assertEquals(mockCv, actual.);

    }

    @Test
    public void testUploadCv() throws Exception {

        multipartFile = new MockMultipartFile(
                "mock",
                "mockFile.pdf",
                "multipart/form-data",
                data.getBytes()
        );


        URI location = ServletUriComponentsBuilder.fromCurrentRequestUri().path("/upload-cv")
                .buildAndExpand().toUri();

        when(cvService.uploadCv(multipartFile, "mock", "mockFile.pdf"))
                .thenReturn(ResponseEntity.created(location).build());

        MockHttpServletRequestBuilder builder = MockMvcRequestBuilders
                .post("/cvsystem/upload-cv")
                .param("file", multipartFile)
                .param("user", "mock")
                .param("fileName", "mockFile.pdf")
                .contentType(MediaType.MULTIPART_FORM_DATA);

        MvcResult actual = mockMvc.perform(builder)
                .andExpect(status().isCreated())
                .andReturn();

        verify(cvService).uploadCv(multipartFile, "mock", "mockFile.pdf");

        System.out.println(actual);

//        assertEquals();


    }
}
