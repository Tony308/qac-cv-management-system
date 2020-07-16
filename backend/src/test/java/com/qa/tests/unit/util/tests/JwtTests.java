package com.qa.tests.unit.util.tests;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.qa.utility.JwtUtil;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.Instant;
import java.util.Date;

import static org.junit.Assert.assertEquals;

@SpringBootTest
@RunWith(SpringRunner.class)
public class JwtTests {

    private String token;

    private Date iat;
    private Date exp;

    @Value("${jwt.claim}")
    private String defaultClaim;
    @Value("${jwt.issuer}")
    private String issuer;
    private Algorithm algorithm;
    @Value("${jwt.secret}")
    private String secret;

    @Autowired
    private JwtUtil tokenUtil;

    @Before
    public void setUp() throws Exception {
        iat = Date.from(Instant.now());
        exp = Date.from(Instant.now().plusSeconds(3600));
        algorithm = Algorithm.HMAC512(secret);

    }

    @Test
    public void testTokenGeneration() throws JWTCreationException {
        token = tokenUtil.generateToken("example");
        String[] jwt = token.split("\\.");
        assertEquals(3, jwt.length);
    }

    @Test
    public void testTokenVerification() throws JWTVerificationException {
        String token = tokenUtil.generateToken("username");
        String username = tokenUtil.verifyToken(token);
        assertEquals("username", username);

        token = tokenUtil.generateToken("-1");
        username = tokenUtil.verifyToken(token);
        assertEquals("-1", username);
    }


    @Test(expected = JWTVerificationException.class)
    public void testIat() {

        iat = Date.from(Instant.now().plusSeconds(31));

        token = JWT.create()
                .withIssuedAt(iat)
                .withNotBefore(iat)
                .withExpiresAt(exp)
                .withClaim(defaultClaim, "username")
                .withIssuer(issuer)
                .sign(algorithm);

        tokenUtil.verifyToken(token);
    }

    @Test(expected = JWTVerificationException.class)
    public void testNbf() {

        Date nbf = Date.from(Instant.now().plusSeconds(31));

        token = JWT.create()
                .withIssuedAt(iat)
                .withNotBefore(nbf)
                .withExpiresAt(exp)
                .withClaim(defaultClaim, "username")
                .withIssuer(issuer)
                .sign(algorithm);

        tokenUtil.verifyToken(token);

    }

    @Test(expected = JWTVerificationException.class)
    public void testExp() {

        iat = Date.from(Instant.now().minusSeconds(3600));
        exp = Date.from(iat.toInstant().plusSeconds(61));

        token = JWT.create()
                .withIssuedAt(iat)
                .withNotBefore(iat)
                .withExpiresAt(exp)
                .withClaim(defaultClaim, "username")
                .withIssuer(issuer)
                .sign(algorithm);

        tokenUtil.verifyToken(token);
    }

    @Test(expected = JWTVerificationException.class)
    public void testInvalidClaim() throws JWTVerificationException{
        defaultClaim = "wrong";

        token = JWT.create()
                .withIssuedAt(iat)
                .withNotBefore(iat)
                .withExpiresAt(exp)
                .withClaim(defaultClaim, "username")
                .withIssuer(issuer)
                .sign(algorithm);

        tokenUtil.verifyToken(token);
    }

    @Test(expected = JWTVerificationException.class)
    public void testInvalidIssuer() {

        issuer = "wrong";
        token = JWT.create()
                .withIssuedAt(iat)
                .withNotBefore(iat)
                .withExpiresAt(exp)
                .withClaim(defaultClaim, "username")
                .withIssuer(issuer)
                .sign(algorithm);

        tokenUtil.verifyToken(token);
    }

    @Test(expected = JWTVerificationException.class)
    public void testInvalidAlgorithm() {
        algorithm = Algorithm.HMAC256(secret);
        token = JWT.create()
                .withIssuedAt(iat)
                .withNotBefore(iat)
                .withExpiresAt(exp)
                .withClaim(defaultClaim, "username")
                .withIssuer(issuer)
                .sign(algorithm);

        tokenUtil.verifyToken(token);

        algorithm = Algorithm.HMAC384(secret);
        token = JWT.create()
                .withIssuedAt(iat)
                .withNotBefore(iat)
                .withExpiresAt(exp)
                .withClaim(defaultClaim, "username")
                .withIssuer(issuer)
                .sign(algorithm);

        tokenUtil.verifyToken(token);

        algorithm = Algorithm.HMAC512("wrong");
        token = JWT.create()
                .withIssuedAt(iat)
                .withNotBefore(iat)
                .withExpiresAt(exp)
                .withClaim(defaultClaim, "username")
                .withIssuer(issuer)
                .sign(algorithm);
        tokenUtil.verifyToken(token);
    }
}
