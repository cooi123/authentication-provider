package com.example.authServer.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.security.*;
import java.util.Base64;
import java.util.Map;

public class JWTRSA implements JWTAlgo {
    private final String ALGORITHM = "RSA";

    public static String generateJWT(JWTHeader header, Map<String, String> body, JWKey key) {
        ObjectMapper objectMapper = new ObjectMapper();
        String headerString = null;
        String bodyString = null;
        try {
            headerString = objectMapper.writeValueAsString(header);
            bodyString = objectMapper.writeValueAsString(body);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        Base64.Encoder base64UrlEncoder = Base64.getUrlEncoder().withoutPadding();

        String headerEncoded = base64UrlEncoder.encodeToString(headerString.getBytes());
        String bodyEncoded = base64UrlEncoder.encodeToString(bodyString.getBytes());

        String token = headerEncoded + "." + bodyEncoded + "." + signJWT(headerEncoded, bodyEncoded, key);
        return token;
    }

    public static String signJWT(String headerEncoded, String bodyEncoded, JWKey key) {
        String signature = null;
        try {
            Signature sign = Signature.getInstance("SHA256withRSA");
            sign.initSign(key.getPrivateKey());
            sign.update((headerEncoded + "." + bodyEncoded).getBytes());
            byte[] signatureBytes = sign.sign();
            Base64.Encoder base64UrlEncoder = Base64.getUrlEncoder().withoutPadding();
            signature = base64UrlEncoder.encodeToString(signatureBytes);
        } catch (NoSuchAlgorithmException | InvalidKeyException | SignatureException e) {
            throw new RuntimeException(e);
        }
        return signature;
    }


    public static void main(String[] args) {

        JWKey key1 = new JWKey("RSA");
        JWTRSA jwt = new JWTRSA();
        Map<String, String> body = Map.of("sub", "1234567890", "name", "John Doe", "admin", "true");
        JWTHeader header = new JWTHeader("RS256", "JWT", "1");
        System.out.println(jwt.generateJWT(header, body, key1));

    }
}
