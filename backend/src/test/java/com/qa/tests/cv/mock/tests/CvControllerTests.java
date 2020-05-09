package com.qa.tests.cv.mock.tests;

import com.qa.controller.CvController;
import com.qa.service.CvService;
import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

public class CvControllerTests {

    @Mock
    CvService cvService;

    @InjectMocks
    CvController cvController;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
    }

    @After
    public void tearDown() throws Exception {

    }

    @Test
    @Ignore
    public void testGetCv() {

        MockMvcRequestBuilders.post("/cvsystem/retrieve/1");
//        content(exampleCourseJson).contentType(MediaType.APPLICATION_JSON)

    }
}
