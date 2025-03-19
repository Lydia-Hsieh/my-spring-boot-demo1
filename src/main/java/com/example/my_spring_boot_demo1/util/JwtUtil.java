package com.example.my_spring_boot_demo1.util;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.*;

public class JwtUtil {

    private static final String SECRET_KEY = "";
    private static final int EXPIRE_TIME = 60; //sec

    public static String generateToken(String userId) {
        Algorithm algorithm = Algorithm.HMAC256(SECRET_KEY);
        Instant expiresInstant = Instant.now().plusSeconds(EXPIRE_TIME);
        String token = JWT.create()
                .withClaim("user_id", userId)
                .withExpiresAt(expiresInstant)
                .sign(algorithm);
        return token;
    }

    public static String parseToken(String token) throws JWTDecodeException {
        Algorithm algorithm = Algorithm.HMAC256(SECRET_KEY);
        JWTVerifier verifier = JWT.require(algorithm).build();

        DecodedJWT decodedJWT = null;
        try {
            decodedJWT = verifier.verify(token);
        } catch (JWTVerificationException e) {
            throw new JWTVerificationException("Invalid or expired token");
        }
        return decodedJWT.getClaim("user_id").asString();
    }
}
