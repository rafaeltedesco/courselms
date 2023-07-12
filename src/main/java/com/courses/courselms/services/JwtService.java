package com.courses.courselms.services;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.courses.courselms.models.User;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.Instant;

@Service
public class JwtService {

    @Value("${api.security.token.secret}")
    private String secret;
    private String issuer = "course-mls";

    public String generateToken(User user) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(secret);
            String token = JWT.create()
                    .withIssuer(issuer)
                    .withSubject(user.getUsername())
                    .withClaim("id", user.getId())
                    .withExpiresAt(Instant.now().plus(Duration.ofMinutes(10)))
                    .sign(algorithm);
            return token;
        } catch (JWTCreationException exception){
            System.out.println(exception);
            throw new RuntimeException("Erro ao gerar token", exception);
        }
    }

    public String getSubject(String token) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(secret);
            return JWT.require(algorithm)
                    .withIssuer(issuer)
                    .build()
                    .verify(token)
                    .getSubject();

        } catch (JWTVerificationException exception){
            throw new RuntimeException("Token inválido!", exception);
            // Invalid signature/claims
        }
    }
}
