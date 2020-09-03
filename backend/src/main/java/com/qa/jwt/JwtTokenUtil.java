package com.qa.jwt;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.Date;
import java.util.Map;

@Component
@PropertySource("classpath:/application.properties")
public class JwtTokenUtil {

    private final String issuer;
    private final String defaultClaim;
    private final Algorithm algorithm;

    public JwtTokenUtil(
            @Value("${jwt.secret}") String secret,
            @Value("${jwt.issuer}") String issuer,
            @Value("${jwt.claim}") String defaultClaim) {
        this.issuer = issuer;
        this.defaultClaim = defaultClaim;
        algorithm = Algorithm.HMAC512(secret);
    }

    private DecodedJWT getDecodedJwt(String token) {
        return JWT.decode(token);
    }

    private Map<String,Claim> getAllClaimsFromToken(String token) {
        DecodedJWT decodedJWT = getDecodedJwt(token);
        return decodedJWT.getClaims();
    }

    private Claim getClaimFromToken(String token) {
        final Map<String, Claim> claims = getAllClaimsFromToken(token);
        return claims.get(defaultClaim);
    }

    private String getUsernameFromToken(String token) {
        try {
            return getClaimFromToken(token).asString();
        } catch (NullPointerException e) {
            throw new JWTVerificationException("NullPointerException");
        }
    }

    public String generateToken(String username) throws JWTCreationException {
        Date iat = Date.from(Instant.now());
        Date exp = Date.from(iat.toInstant().plusSeconds(3600));

        return JWT.create()
                .withIssuedAt(iat)
                .withNotBefore(iat)
                .withExpiresAt(exp)
                .withClaim(defaultClaim, username)
                .withIssuer(issuer)
                .sign(algorithm);
    }

    public String verifyToken(String token) throws JWTVerificationException,
            JWTDecodeException {
        String username = getUsernameFromToken(token);
        JWTVerifier verifier = JWT.require(algorithm)
                .withClaim(defaultClaim, username)
                .withIssuer(issuer)
                .acceptLeeway(30)
                .acceptExpiresAt(60)
                .build();

        verifier.verify(token);
        return username;
    }
}