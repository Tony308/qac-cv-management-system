package com.qa.tests;

import static org.junit.Assert.assertEquals;

import java.io.File;
import java.io.IOException;
import java.util.Optional;

import com.qa.repository.ICvRepository;

import org.apache.commons.io.FileUtils;
import org.bson.BsonBinarySubType;
import org.bson.types.Binary;
import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.qa.domain.Cv;
import com.qa.domain.User;
import com.qa.repository.UserRepository;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
@EnableMongoRepositories(basePackageClasses = {ICvRepository.class, UserRepository.class})
public class UserAndCvUnitTests {

    @Autowired
    private UserRepository userRepository;
    
    @Autowired
    private ICvRepository iCvRepository;

    private User alex;
    private User bob;
    
    @Before
    public void setUp() {

		String byteData = "Initial File\n" +
				"Feel free to delete this file when the database is setup.";

		Binary fileToBinaryStorage = new Binary(BsonBinarySubType.BINARY, byteData.getBytes());

		alex = new User("alex", "test");
		bob = new User("bob", "test");
		userRepository.save(alex);
		userRepository.save(bob);

		iCvRepository.save(new Cv("alex", fileToBinaryStorage, "testFile.txt"));
		iCvRepository.save(new Cv("bob", fileToBinaryStorage, "testFile.txt"));

	}

    @After
    public void teardown() {
        userRepository.deleteAll();
        iCvRepository.deleteAll();
    }
	
	@Test
	public void getUsername() {

	    Optional<User> found = userRepository.findByUsername("alex");
	    User user = null;
	    user = found.get();

	    String actual = user.getUsername();
	    String expected = alex.getUsername();

	    System.out.println(actual);
	    System.out.println(expected);

	    assertEquals(expected, actual);

	    found = userRepository.findByUsername("bob");

		expected = bob.getUsername();
		actual = found.get().getUsername();

		assertEquals(expected, actual);
	}

}
