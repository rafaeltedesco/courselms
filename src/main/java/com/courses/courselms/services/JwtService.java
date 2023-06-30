package com.courses.courselms.services;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.courses.courselms.models.User;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class JwtService {

    @Value("${api.security.token.secret}")
    private String secret;

    public String generateToken(User user) {
        try {

            System.out.println(secret);
            var algorithm = Algorithm.HMAC256(secret);
            return JWT.create()
                    .withIssuer("course lms")
                    .withSubject(user.getUsername())
                    .sign(algorithm);
        } catch (JWTCreationException ex) {
            throw new RuntimeException("Error while generating JWT", ex);
        }

    }
}
