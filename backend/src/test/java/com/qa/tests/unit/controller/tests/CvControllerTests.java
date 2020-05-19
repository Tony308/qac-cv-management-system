package com.qa.tests.unit.controller.tests;

import com.qa.controller.CvController;
import com.qa.domain.Cv;
import com.qa.service.CvService;
import org.apache.tomcat.util.codec.binary.Base64;
import org.bson.BsonBinarySubType;
import org.bson.types.Binary;
import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.servlet.http.HttpServletRequest;
import java.net.URI;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringJUnit4ClassRunner.class)
@WebMvcTest(CvController.class)
public class CvControllerTests {

    @InjectMocks
    private CvController cvController;

    @MockBean
    private CvService cvService;

    @Autowired
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

        HttpServletRequest mockRequest = new MockHttpServletRequest();
        ServletRequestAttributes servletRequestAttributes = new ServletRequestAttributes(mockRequest);
        RequestContextHolder.setRequestAttributes(servletRequestAttributes);

        MockitoAnnotations.initMocks(this);
    }

    @After
    public void tearDown() throws Exception {
        RequestContextHolder.resetRequestAttributes();
    }

    @Test
    public void testGetCv() throws Exception {

        mockCv = new Cv("1","mock",
                binary,"MockFile.txt");

        when(cvService.getCV("1")).thenReturn(ResponseEntity.ok(mockCv));

        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .get("/cvsystem/retrieve/1");

        MvcResult actual = mockMvc.perform(requestBuilder)
                .andExpect(status().isOk())
                .andReturn();

        verify(cvService).getCV("1");

        MockHttpServletResponse response = actual.getResponse();

        JSONObject jsonObj = new JSONObject(response.getContentAsString());
        String encodeData = jsonObj.getJSONObject("cvFile").getString("data");

        byte[] content = Base64.decodeBase64(encodeData);
        String dbData = new String(content, StandardCharsets.UTF_8);

        assertEquals(data[0], dbData);
    }

    @Test
    public void testUploadCv() throws Exception {

        String user = "mock";
        String fileName = "mockFile.pdf";

        file = new MockMultipartFile(
                "file",
                fileName,
                "multipart/form-data",
                data[0].getBytes()
        );

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest().path("/cvsystem/upload-cv").build().toUri();

        when(cvService.uploadCv(file, user, fileName))
                .thenReturn(ResponseEntity.created(location).body("File successfully uploaded"));

        RequestBuilder request = multipart("/cvsystem/upload-cv")
                .file(file)
                .param("user", user)
                .param("fileName", fileName);

                mockMvc.perform(request)
                .andExpect(status().isCreated())
                .andExpect(content().string("File successfully uploaded"))
                .andReturn();

        verify(cvService).uploadCv(file, user, fileName);

    }

    @Test
    public void testGetUserCvs() throws Exception {
        List<Cv> cvList = new ArrayList<>();

        mockCv = new Cv("1","mock",
                binary,"MockFile.txt");
        cvList.add(mockCv);

        binary = new Binary(BsonBinarySubType.BINARY, data[1].getBytes());
        mockCv = new Cv("2","mock",
                binary,"testfile.txt");
        cvList.add(mockCv);


        when(cvService.getUserCVs("mock")).thenReturn(
                ResponseEntity.ok().body(cvList)
        );

        RequestBuilder request = MockMvcRequestBuilders
                .get("/cvsystem/get")
                .param("name", "mock")
                .contentType(MediaType.APPLICATION_JSON);

        MvcResult actual = mockMvc.perform(request)
                .andExpect(status().isOk())
                .andReturn();

        verify(cvService).getUserCVs("mock");

        MockHttpServletResponse response = actual.getResponse();

        JSONArray jsonArray = new JSONArray(response.getContentAsString());

        assertEquals(2, jsonArray.length());

        JSONObject cvFileJsonObj;

        StringBuilder builder = new StringBuilder();

        for (int x = 0;x < jsonArray.length();x++) {

            cvFileJsonObj = jsonArray.getJSONObject(x).getJSONObject("cvFile");
            builder.append(cvFileJsonObj.getString("data"));
            byte[] decodedData = Base64.decodeBase64(builder.toString());

            builder.setLength(0);
            builder.append(new String(decodedData, StandardCharsets.UTF_8));

            assertEquals(this.data[x], builder.toString());


            builder.setLength(0);
            builder.append(jsonArray.getJSONObject(x).getString("name"));

            assertEquals("mock", builder.toString());

            builder.setLength(0);

        }
        builder.setLength(0);

    }

    @Test
    public void testDeleteCv() throws Exception {

        RequestBuilder request = MockMvcRequestBuilders
                .delete("/cvsystem/delete/1");

        when(cvService.deleteCv("1")).thenReturn(ResponseEntity.ok("CV successfully deleted"));

        mockMvc.perform(request)
                .andExpect(status().isOk())
                .andExpect(content().string("CV successfully deleted"))
                .andReturn();

        verify(cvService).deleteCv("1");

    }

    @Test
    public void testUpdateCv() throws Exception {

        file = new MockMultipartFile(
                "file",
                "ReplacementFile.pdf",
                "multipart/form-data",
                data[1].getBytes()
        );

        when(cvService.updateCv("1", file, file.getOriginalFilename()))
                .thenReturn(ResponseEntity.ok("CV successfully updated."));

        MockHttpServletRequestBuilder request = multipart("/cvsystem/update-cv/1")
                .file(file)
                .header("Content-Type", MediaType.MULTIPART_FORM_DATA_VALUE)
                .param("fileName",file.getOriginalFilename())
                .contentType(MediaType.MULTIPART_FORM_DATA)
                .accept(MediaType.MULTIPART_FORM_DATA);

        request.with(servletRequest -> {
            servletRequest.setMethod("PUT");
            return servletRequest;
        });

        mockMvc.perform(request)
                .andExpect(status().isOk())
                .andExpect(content().string("CV successfully updated."))
                .andReturn();

        verify(cvService).updateCv("1", file, file.getOriginalFilename());

    }
}
