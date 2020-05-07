package com.qa.tests.cv.mock.tests;

import com.qa.domain.Cv;
import com.qa.repository.ICvRepository;
import com.qa.service.CvService;
import org.bson.BsonBinarySubType;
import org.bson.types.Binary;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


@SpringBootTest
@RunWith(SpringJUnit4ClassRunner.class)
public class CvServiceTests {

    @InjectMocks
    private CvService cvService;

    @Mock
    private ICvRepository iCvRepository;

    final private String data = "Initial File\n" +
            "Feel free to delete this file when the database is setup.";

    final private Binary fileToBinaryStorage = new Binary(BsonBinarySubType.BINARY, data.getBytes());


//    private Cv cv;
    private Optional<Cv> foundCv = Optional.empty();

    final private Cv testEinz = new Cv("1","bob", fileToBinaryStorage, "testFile.txt");
//    final private Cv testZwei = new Cv("2","alex", fileToBinaryStorage, "testFile.txt");


    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @After
    public void tearDown() {

    }

    @Test
    public void testGetCv() {

        foundCv = Optional.of(testEinz);

        when(iCvRepository.findById("1")).thenReturn(foundCv);

        Cv actual = cvService.getCV("1");

        verify(iCvRepository).findById("1");

        Cv expected = foundCv.get();
        assertEquals(expected, actual);

    }
}
