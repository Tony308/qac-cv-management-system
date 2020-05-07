package com.qa.tests.repository.tests;

import com.qa.domain.Cv;
import com.qa.repository.ICvRepository;
import de.flapdoodle.embed.mongo.MongodExecutable;
import de.flapdoodle.embed.mongo.MongodStarter;
import de.flapdoodle.embed.mongo.config.IMongodConfig;
import de.flapdoodle.embed.mongo.config.MongodConfigBuilder;
import de.flapdoodle.embed.mongo.config.Net;
import de.flapdoodle.embed.mongo.distribution.Version;
import de.flapdoodle.embed.process.runtime.Network;
import org.bson.BsonBinarySubType;
import org.bson.types.Binary;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
@EnableMongoRepositories(basePackageClasses = {ICvRepository.class})
public class CvRepositoryTests {

    @Autowired
    ICvRepository iCvRepository;

    final private String byteData = "Initial File\n" +
            "Feel free to delete this file when the database is setup.";

    final private Binary fileToBinaryStorage = new Binary(BsonBinarySubType.BINARY, byteData.getBytes());

    @BeforeClass
    public static void beforeClass() throws Exception {
        MongodStarter starter = MongodStarter.getDefaultInstance();
        String bindIp = "localhost";
        int port = 27017;
        MongodConfigBuilder mongodConfigBuilder = new MongodConfigBuilder();
        mongodConfigBuilder.version(Version.Main.DEVELOPMENT);
        mongodConfigBuilder.net(new Net(bindIp, port, Network.localhostIsIPv6()));
        IMongodConfig mongodConfig = mongodConfigBuilder
                .build();

        MongodExecutable mongodExecutable = starter.prepare(mongodConfig);

        if (mongodExecutable != null){
            mongodExecutable.stop();
        }

    }

    @Before
    public void setUp() {
        iCvRepository.save(new Cv("1","bob", fileToBinaryStorage, "testFile.txt"));
        iCvRepository.save(new Cv("2","alex", fileToBinaryStorage, "testFile.txt"));
    }

    @After
    public void tearDown() {
        iCvRepository.deleteAll();
    }

    @Test
    public void findAllByName() {

        List<Cv> list = iCvRepository.findAllByName("bob");

        assertEquals(1, list.size());

        Cv newCv = new Cv("bob", fileToBinaryStorage, "testFile.txt");

        iCvRepository.save(newCv);

        list = iCvRepository.findAllByName("bob");

        assertEquals(2, list.size());

        newCv = new Cv("bob", fileToBinaryStorage, "cv.pdf");
        iCvRepository.save(newCv);

        list = iCvRepository.findAllByName("bob");

        assertEquals(3, list.size());

    }

    @Test
    public void testFindById() {

        Cv example = new Cv("3", "alex", fileToBinaryStorage, "file.txt");
        iCvRepository.save(example);

        Optional<Cv> found = iCvRepository.findById("3");

        assertTrue(found.isPresent());
        Cv cv = found.get();

        String expected = "3";
        String actual = cv.getId();
        assertEquals(expected, actual);

    }

    @Test
    public void testUpdateCv() {
        Optional<Cv> found = iCvRepository.findById("1");

        assertTrue(found.isPresent());

        Cv cv = found.get();

//        cv.setId("3");
        cv.setName("Bob Morley");
        cv.setFileName("New CV.pdf");
        cv.setLastModified(new Date());
        cv.setCvFile(new Binary(BsonBinarySubType.BINARY, "For cv email me".getBytes()));

        iCvRepository.save(cv);

        found = iCvRepository.findById("1");

        assertTrue(found.isPresent());

        Cv actual = found.get();

        assertEquals(cv.getId(), actual.getId());
        assertEquals(cv.getName(), actual.getName());
        assertEquals(cv.getCvFile(), actual.getCvFile());
        assertEquals(cv.getFileName(), actual.getFileName());
        assertEquals(cv.getLastModified(), actual.getLastModified());

    }

    @Test
    public void testDeleteCv() {

        Optional<Cv> found = iCvRepository.findById("1");
        assertTrue(found.isPresent());

        iCvRepository.deleteById("1");

        found = iCvRepository.findById("1");

        assertFalse(found.isPresent());

    }
}
