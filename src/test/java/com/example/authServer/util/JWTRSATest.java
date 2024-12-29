package com.example.authServer.util;

import com.nimbusds.jose.JOSEException;
import org.junit.jupiter.api.Test;

import java.security.interfaces.RSAPublicKey;
import java.text.ParseException;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import com.nimbusds.jose.crypto.RSASSAVerifier;
import com.nimbusds.jwt.SignedJWT;
class JWTRSATest {

    @Test
    void generateJWT() throws ParseException {
        JWKey key = new JWKey("RSA");
        JWTHeader header = new JWTHeader("RS256", "JWT", "1");
        Map<String,String> body = Map.of("sub", "1234567890", "name", "John Doe", "admin", "true");
        String jwt = JWTRSA.generateJWT(header, body, key);
        SignedJWT signedJWT = null;
        try {
            signedJWT = SignedJWT.parse(jwt);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
        assertEquals("RS256", signedJWT.getHeader().getAlgorithm().getName());
        assertEquals("JWT", signedJWT.getHeader().getType().toString());
        assertEquals("1", signedJWT.getHeader().getKeyID());
        assertEquals("1234567890", signedJWT.getJWTClaimsSet().getSubject());
        assertEquals("John Doe", signedJWT.getJWTClaimsSet().getStringClaim("name"));
        assertEquals("true", signedJWT.getJWTClaimsSet().getStringClaim("admin"));
        RSASSAVerifier verifier = new RSASSAVerifier((RSAPublicKey) key.getPublicKey());
        try {
            assertTrue(signedJWT.verify(verifier));
        } catch (JOSEException e) {
            throw new RuntimeException(e);
        }


    }

    @Test
    void signJWT() {
    }
}