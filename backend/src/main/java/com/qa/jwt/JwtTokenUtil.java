package com.qa.jwt;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.json.JSONObject;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.Date;

@Component
public class JwtTokenUtil {

    private String secret = "secret";

    private JWTVerifier verifier;

    final private Algorithm algorithm;

    public JwtTokenUtil() {
        algorithm = Algorithm.HMAC512(secret);
    }

    public String generateToken(String username) {
        return JWT.create()
                .withIssuedAt(new Date())
                .withClaim("username", username)
                .withIssuer("huang")
                .sign(algorithm);
    }

    public void verifyToken(String token) throws JWTVerificationException {
        DecodedJWT decodedJWT = JWT.decode(token);

        JSONObject jsonObject = new JSONObject(new String(
                Base64.getDecoder().decode(decodedJWT.getPayload()),
                StandardCharsets.UTF_8)
        );

        verifier = JWT.require(algorithm)
                .withClaim("username", jsonObject.getString("username"))
                .withIssuer("huang")
                .acceptLeeway(3600)
                .build();

        verifier.verify(token);
    }
}