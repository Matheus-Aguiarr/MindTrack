package com.api.mindtrack.infra.security;

import com.api.mindtrack.domain.user.UserModel;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

//This is a class to generate the JWT Tokens.
@Service
public class TokenService {


//    This is the secret key, used to sign the token. Without this key, nobody can generate or validate the tokens.
    @Value("${api.security.token.secret}")
    private String secret;

/*
    Method to generate tokens.
    This method receive an Object UserModel as parameter and generate a token JWT for this user.

    Algorithm algorithm = Algorithm.HMAC256(secret);
        - Define the sign algorithm of the token: HMAC256 with the secret key.
        - This guarantee that only who has this secret can generate or validate tokens.

    String token = JWT.create() => Create the token JWT
    .withIssuer("API auth") => Who issued the token (quem emitiu)
    .withSubject(user.getLogin()) => The user owner of the token, here we use the login.
    .withExpiresAt(generateExpirationDate()) => Date and hour of the expiration
    .sign(algorithm); => Sign the token with the algorithm and the secret key (assina)
        - The result is a String JWT: eyJhbGciOiJIUzI1NiIsInR5cCI6...
*/
    public String generateToken(UserModel user) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(secret);
            String token = JWT.create()
                    .withIssuer("API auth")
                    .withSubject(user.getLogin())
                    .withExpiresAt(generateExpirationDate())
                    .sign(algorithm);
            return token;
        } catch (JWTCreationException ex) {
            throw new RuntimeException("Error to generate token JWT", ex);
        }
    }

/*
    Method to validate the token
    Receive a tokenJWT as parameter and try to validate it.
    Algorithm algorithm = Algorithm.HMAC256(secret);
        - Use the same secret key and algorithm (it has to be the same)

    return JWT.require(algorithm)
    .withIssuer("API auth") => Verify id the issuer is the same ("API auth")
    .build()
    .verify(tokenJWT) => Verify if the token is not expired.
    .getSubject(); => If is everything ok, return the subject, in this case, the login of the user.
*/
    public String validateToken(String tokenJWT) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(secret);
            return JWT.require(algorithm)
                    .withIssuer("API auth")
                    .build()
                    .verify(tokenJWT)
                    .getSubject();
        } catch (JWTVerificationException ex) {
            return "";
        }
    }

//    Create an hour of expiration for the token: 2 hours starting from now, considering the Brazillian Time Zone. (Horario de brasilia)
    private Instant generateExpirationDate() {
        return LocalDateTime.now().plusHours(2).toInstant(ZoneOffset.of("-03:00"));
    }
}
