package com.qa.tests.unit.util.tests;

import com.qa.domain.User;
import com.qa.repository.UserRepository;
import com.qa.utility.BCryptUtil;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

@RunWith(SpringRunner.class)
@SpringBootTest
public class BCryptTests {

    private String username, password;

    @Autowired
    private UserRepository repository;

    @Autowired
    private BCryptUtil bCryptUtil;

    @Before
    public void setUp() throws Exception {
        username = "username";
        password = "password";
    }

    @After
    public void tearDown() throws Exception {
        repository.deleteAll();
    }

    @Test
    public void testEncrypt() {
        String hash = bCryptUtil.hashPassword(password);
        assertTrue(BCrypt.checkpw(password, hash));
    }

    @Test
    public void testVerifier() {
        User user = repository.save(new User(username, bCryptUtil.hashPassword(password)));
        assertTrue(bCryptUtil.verifyPwd(password, user.getPassword()));
    }

    public void testVerifierFailure() {
        User user = repository.save(new User(username, password));
        assertFalse(bCryptUtil.verifyPwd(password, user.getPassword()));
    }

    @Test
    public void testVerifierDiffPwd() {
        User user = repository.save(new User(username, bCryptUtil.hashPassword(password)));
        assertFalse(bCryptUtil.verifyPwd("different", user.getPassword()));
        assertTrue(bCryptUtil.verifyPwd(password, user.getPassword()));
    }
}