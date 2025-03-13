package com.example.my_spring_boot_demo1.util;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;

import java.security.Key;
import java.time.*;

public class JwtUtil {

    private static final String SECRET_KEY = "";
    private static final int EXPIRE_TIME = 10; //sec

    public static String generateToken(String userId) throws Exception {
        Algorithm algorithm = Algorithm.HMAC256(SECRET_KEY);
        Instant expiresInstant = Instant.now().plusSeconds(EXPIRE_TIME);
        String token = JWT.create()
                .withClaim("user_id", userId)
                .withExpiresAt(expiresInstant)
                .sign(algorithm);
        return token;
    }
}
