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
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;
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

    final private Binary fileToBinaryStorage =
            new Binary(BsonBinarySubType.BINARY, data.getBytes());

    private Optional<Cv> foundCv = Optional.empty();

    final private Cv testEinz = new Cv("1","bob",
            fileToBinaryStorage,"testFile.txt");

    final private Cv testZwei = new Cv("2","alex",
            fileToBinaryStorage, "testFile.txt");

    private MultipartFile multipartFile;


    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @After
    public void tearDown() {
    }

    @Test
    public void testGetCvSuccess() {

        foundCv = Optional.of(testEinz);

        when(iCvRepository.findById("1")).thenReturn(foundCv);

        ResponseEntity<?> actual = cvService.getCV("1");

        verify(iCvRepository).findById("1");

        ResponseEntity<?> expected = ResponseEntity.ok(testEinz);

        assertEquals(expected, actual);

    }

    @Test
    public void testGetCvFail() {
        when(iCvRepository.findById("1")).thenReturn(foundCv);

        ResponseEntity<?> actual = cvService.getCV("1");

        verify(iCvRepository).findById("1");

        ResponseEntity<?> expected = ResponseEntity.notFound().build();

        assertEquals(expected, actual);
    }

    @Test
    public void testGetAllCVsSuccess() {
        String user = "user";

        List<Cv> found = new ArrayList<>();

        Cv eins = new Cv(user, fileToBinaryStorage, "random.pdf");
        Cv zwei = new Cv(user, fileToBinaryStorage,"testFile.txt");

        found.add(eins);
        found.add(zwei);

        when(iCvRepository.findAllByName(user)).thenReturn(found);

        ResponseEntity<?> actual = cvService.getUserCVs(user);

        verify(iCvRepository).findAllByName(user);

        List<Cv> expectedList = new ArrayList<>();

        expectedList.add(eins);
        expectedList.add(zwei);

        ResponseEntity<?> expected = ResponseEntity.ok(expectedList);

        assertEquals(expected, actual);

    }

    @Test
    public void testGetAllCvsFail() {

        List<Cv> found =  new ArrayList<>();

        when(iCvRepository.findAllByName("jesus")).thenReturn(found);

        ResponseEntity<?> actual = cvService.getUserCVs("jesus");

        verify(iCvRepository).findAllByName("jesus");

        ResponseEntity<?> expected = ResponseEntity.notFound().build();

        assertEquals(expected, actual);

    }

    @Test
    public void testUploadCvSuccess() {

        multipartFile = new MockMultipartFile(
                "file",
                "fileName.pdf",
                "text/plain",
                data.getBytes()
        );

        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path(
                "/upload-cv").build().toUri();

        ResponseEntity<?> actual = cvService
                .uploadCv(multipartFile,"Bob", "fileName.pdf");

        ResponseEntity<?> expected =
                ResponseEntity.created(location).body("File successfully uploaded");

        assertEquals(expected, actual);

    }

    @Test
    public void testUploadNoCv() {
        multipartFile = null;

        ResponseEntity<?> actual = cvService.uploadCv(multipartFile, "bob", "testFail.pdf");
        ResponseEntity<?> expected = ResponseEntity.badRequest().build();

        assertEquals(expected, actual);

    }


    @Test
    public void testUpdateCvSuccess() {

        multipartFile = new MockMultipartFile(
                "file", "fileName.pdf", "text/plain", data.getBytes()
        );

        foundCv = Optional.of(testEinz);

        when(iCvRepository.findById("1")).thenReturn(foundCv);

        ResponseEntity<?> actual = cvService.updateCv(
                "1", multipartFile, multipartFile.getOriginalFilename()
        );

        ResponseEntity<String> expected = new ResponseEntity<>
                ("CV successfully updated.", HttpStatus.OK);

        verify(iCvRepository).findById("1");

        assertEquals(expected, actual);
    }

    @Test
    public void testUpdateFail() {

        foundCv = Optional.of(testEinz);

        when(iCvRepository.findById("1")).thenReturn(foundCv);

        ResponseEntity<?> actual = cvService.updateCv(
                testEinz.getId(),
                multipartFile,
                "fileName.pdf"
        );

        ResponseEntity<String> expected = new ResponseEntity<>("Failed to update.", HttpStatus.BAD_REQUEST);

        verify(iCvRepository).findById("1");

        assertEquals(expected, actual);

    }

    @Test
    public void testUpdateNoCv() {

        multipartFile = new MockMultipartFile(
                "file", "fileName.pdf", "text/plain", data.getBytes()
        );

        when(iCvRepository.findById("1")).thenReturn(foundCv);

        ResponseEntity<?> actual = cvService.updateCv(
                testEinz.getId(), multipartFile, multipartFile.getOriginalFilename()
        );

        ResponseEntity<String> expected = new ResponseEntity<>(
                "Unable to find CV", HttpStatus.NOT_FOUND);

        assertEquals(expected, actual);
    }

    @Test
    public void testDeleteCv() {

        foundCv = Optional.of(testEinz);

        when(iCvRepository.findById("1")).thenReturn(foundCv);

        ResponseEntity<?> actual = cvService.deleteCv("1");

        verify(iCvRepository).findById("1");

        ResponseEntity<?> expected = ResponseEntity.ok("CV successfully deleted");

        assertEquals(expected, actual);

    }

    @Test
    public void testDeleteCvFail() {
        when(iCvRepository.findById("1")).thenReturn(foundCv);

        ResponseEntity<?> actual = cvService.deleteCv("1");

        verify(iCvRepository).findById("1");

        ResponseEntity<?> expected = ResponseEntity.notFound().build();

        assertEquals(expected, actual);
    }
}
