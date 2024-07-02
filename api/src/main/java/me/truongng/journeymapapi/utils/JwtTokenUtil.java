package me.truongng.journeymapapi.utils;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.interfaces.DecodedJWT;

import java.util.Date;

@Component
public class JwtTokenUtil {
    @Value("${spring.jwt.secret}")
    private String SECRET_KEY;

    public String generateToken(String payload) {
        System.out.println("SECRET_KEY: " + SECRET_KEY);
        String token = JWT.create().withSubject(payload)
                .withExpiresAt(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 24))
                .sign(com.auth0.jwt.algorithms.Algorithm.HMAC256(SECRET_KEY));

        return token;
    }

    public boolean validateToken(String token) {
        JWTVerifier verifier = JWT.require(com.auth0.jwt.algorithms.Algorithm.HMAC256(SECRET_KEY)).build();
        verifier.verify(token);
        return true;
    }

    public String extractPayload(String token) {
        DecodedJWT jwt = JWT.decode(token);
        return jwt.getSubject();
    }
}
