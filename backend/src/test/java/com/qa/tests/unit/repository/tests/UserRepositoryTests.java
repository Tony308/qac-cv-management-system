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
import org.springframework.test.context.junit4.SpringRunner;

import javax.validation.ConstraintViolationException;
import java.util.Optional;

import static org.junit.Assert.*;

@SpringBootTest
@RunWith(SpringRunner.class)
public class UserRepositoryTests {

    @Autowired
    private UserRepository userRepository;

    private User maximus;
    private User jimmy;

	@Before
    public void setUp()  {
		maximus = new User("maximus", "desparado");
		jimmy = new User("jimmy", "password");
		userRepository.save(maximus);
		userRepository.save(jimmy);
	}

    @After
    public void teardown() {
        userRepository.deleteAll();
    }

	@Test
	public void testSaveUser() {

		String username = "anikan";
		String pwd = "skywalker";
		User expected = new User(username, pwd);
		userRepository.save(expected);

		Optional<User> found = userRepository
				.findByUsernameAndPassword(username, pwd);

		assertTrue(found.isPresent());

	}

	@Test
	public void testSaveUserException() {

		try {
			String username = "too";
			String pwd = "small";
			User expected = new User(username, pwd);
			userRepository.save(expected);
		} catch (ConstraintViolationException e) {

		}


	}

	@Test
	public void testFindByUsername() {
		Optional<User> found = userRepository.findByUsername("jimmy");
		assertTrue(found.isPresent());
	}

	@Test
	public void testFindByUsernameException() throws ConstraintViolationException {

		boolean status = true;
		try {
			userRepository.findByUsername("");
		} catch (ConstraintViolationException e) {
			status = false;
		}

		assertFalse(status);
	}

	@Test
	public void testCreatingDuplicateUsername() {
		boolean state;
		User expected = new User("jimmy", "password");
		try {
			userRepository.save(expected);
			state = true;
		} catch (DuplicateKeyException e) {
			state = false;
		}

		assertFalse(state);
	}

	@Test
	public void testUpdateUser() {

		Optional<User> foundUser = userRepository.findByUsername("maximus");
		String expected;
		String actual;

		assertTrue("Check user is present.", foundUser.isPresent());

		User user = foundUser.get();
		user.setUsername("hendrix");
		user.setPassword("password");

		userRepository.save(user);

		foundUser = userRepository.findByUsername("hendrix");
		expected = "hendrix";

		assertTrue(foundUser.isPresent());

		user = foundUser.get();
		actual = user.getUsername();

		assertEquals("Test username are equal.",expected, actual);

		expected = "password";
		actual = user.getPassword();

		assertEquals("Test updated password are equal", expected, actual);

	}

	@Test
	public void testGetUser() {

	    Optional<User> found = userRepository.findByUsername(maximus.getUsername());
	    User user;

	    assertTrue(found.isPresent());

		user = found.get();

		String actual = user.getUsername();
	    String expected = maximus.getUsername();

		assertEquals("maximus", actual);
	    assertEquals(expected, actual);

		assertEquals("desparado", user.getPassword());
		assertEquals(maximus.getPassword(), user.getPassword());

		found = userRepository.findByUsername("jimmy");

		assertTrue(found.isPresent());
		user = found.get();

		expected = jimmy.getUsername();
		actual = user.getUsername();

		assertEquals("jimmy", actual);
		assertEquals(expected, actual);

		assertEquals("password", user.getPassword());
		assertEquals(jimmy.getPassword(), user.getPassword());

    }

	@Test
	public void testDeleteUser() {

		Optional<User> foundUser = userRepository.findByUsername("maximus");
		assertTrue(foundUser.isPresent());

		userRepository.delete(foundUser.get());
		foundUser = userRepository.findByUsername("maximus");

		assertFalse(foundUser.isPresent());

	}


}
