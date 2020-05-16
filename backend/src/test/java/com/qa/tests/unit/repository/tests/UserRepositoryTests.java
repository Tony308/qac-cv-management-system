package com.qa.tests.unit.repository.tests;

import com.qa.domain.User;
import com.qa.repository.UserRepository;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Optional;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class UserRepositoryTests {

    @Autowired
    private UserRepository userRepository;

    private User alex;
    private User bob;

	@Before
    public void setUp()  {
		alex = new User("alex", "test");
		bob = new User("bob", "password");
		userRepository.save(alex);
		userRepository.save(bob);

	}

    @After
    public void teardown() {
        userRepository.deleteAll();
    }

	@Test
	public void testCreateUser() {

		User expected = new User("groot", "i am");
		userRepository.save(expected);

		Optional<User> found = userRepository
				.findByUsernameAndPassword("groot", "i am");

		assertTrue(found.isPresent());

	}

	@Test
	public void testCreatingDuplicateUsername() {
		Boolean state = null;
		User expected = new User("bob", "i am");
		try {
			userRepository.save(expected);
			state = true;
		} catch (DuplicateKeyException e) {
			e.printStackTrace();
			state = false;
		}

		assertFalse(state);
	}

	@Test
	public void testUpdateUser() {

		Optional<User> foundUser = userRepository.findByUsername("alex");
		String expected;
		String actual;

		assertTrue("Check user is present.", foundUser.isPresent());

		User user = foundUser.get();
		user.setUsername("hendrix");
		user.setPassword("pwd");

		userRepository.save(user);

		foundUser = userRepository.findByUsername("hendrix");
		expected = "hendrix";

		assertTrue(foundUser.isPresent());

		user = foundUser.get();
		actual = user.getUsername();

		assertEquals("Test username are equal.",expected, actual);

		expected = "pwd";
		actual = user.getPassword();

		assertEquals("Test updated password are equal", expected, actual);

	}

	@Test
	public void testGetUser() {

	    Optional<User> found = userRepository.findByUsername("alex");
	    User user;

	    assertTrue(found.isPresent());

		user = found.get();

		String actual = user.getUsername();
	    String expected = alex.getUsername();

		assertEquals("alex", actual);
	    assertEquals(expected, actual);

		assertEquals("test",user.getPassword());
		assertEquals(alex.getPassword(), user.getPassword());

		found = userRepository.findByUsername("bob");

		assertTrue(found.isPresent());
		user = found.get();

		expected = bob.getUsername();
		actual = user.getUsername();

		assertEquals("bob", actual);
		assertEquals(expected, actual);

		assertEquals("password", user.getPassword());
		assertEquals(bob.getPassword(), user.getPassword());

    }

	@Test
	public void testDeleteUser() {

		Optional<User> foundUser = userRepository.findByUsername("alex");
		assertTrue(foundUser.isPresent());

		userRepository.delete(foundUser.get());
		foundUser = userRepository.findByUsername("alex");

		assertFalse(foundUser.isPresent());

	}


}
