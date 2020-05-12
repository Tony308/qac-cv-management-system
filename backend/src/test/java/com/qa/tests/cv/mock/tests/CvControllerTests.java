package com.qa.tests.cv.mock.tests;

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
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.print.attribute.standard.Media;
import javax.servlet.http.HttpServletRequest;

import java.net.URI;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(CvController.class)
public class CvControllerTests {

    @InjectMocks
    private CvController cvController;

    @MockBean
    private CvService cvService;

    @Autowired
    private MockMvc mockMvc;

    final private String data = "Initial File\n" +
            "Feel free to delete this file when the database is setup.";

    final private Binary fileToBinaryStorage =
            new Binary(BsonBinarySubType.BINARY, data.getBytes());

    private MockMultipartFile mockMultipartFile;
    private Cv mockCv;

    private HttpServletRequest mockRequest;
    private ServletRequestAttributes servletRequestAttributes;

    @Before
    public void setUp() throws Exception {

        mockRequest = new MockHttpServletRequest();
        servletRequestAttributes = new ServletRequestAttributes(mockRequest);
        RequestContextHolder.setRequestAttributes(servletRequestAttributes);

        MockitoAnnotations.initMocks(this);
    }

    @After
    public void tearDown() throws Exception {
        RequestContextHolder.resetRequestAttributes();

    }

    @Test
    @Ignore
    public void testGetCv() throws Exception {

        mockCv = new Cv("1","mock",
                fileToBinaryStorage,"MockFile.txt");

        ResponseEntity responseEntity = ResponseEntity.ok(mockCv);

        when(cvService.getCV("1")).thenReturn(responseEntity);

        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .get("/cvsystem/retrieve/1")
                .accept(MediaType.APPLICATION_JSON);

        MvcResult actual = mockMvc.perform(requestBuilder)
                .andExpect(status().isOk())
                .andReturn();

        verify(cvService).getCV("1");

        MockHttpServletResponse response = actual.getResponse();

        assertEquals(HttpStatus.OK.value(), response.getStatus());
        System.out.println(response.getContentType());

    }

    @Test
    public void testUploadCv() throws Exception {

        String user = "mock";
        String fileName = "mockFile.pdf";

        mockMultipartFile = new MockMultipartFile(
                "mock",
                "mockFile.pdf",
                "application/json",
                data.getBytes()
        );

        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/upload-cv").build().toUri();

        ResponseEntity responseEntity = ResponseEntity.created(location)
                .body("File successfully uploaded");


//        when(cvService.uploadCv(mockMultipartFile, user, fileName))
//                .thenReturn(responseEntity);

        RequestBuilder request = MockMvcRequestBuilders
                .multipart("/cvsystem/upload-cv")
                .file("file", mockMultipartFile.getBytes())
                .param("user", user)
                .param("fileName", fileName)
                .accept("application/json", "multipart/form-data");

        MvcResult actual = mockMvc.perform(request)
//                .andExpect(status().isCreated())
//                .andExpect(content().string("File successfully uploaded."))
                .andReturn();

//        verify(cvService).uploadCv(mockMultipartFile, user, fileName);

        MockHttpServletResponse response = actual.getResponse();
    }
}
