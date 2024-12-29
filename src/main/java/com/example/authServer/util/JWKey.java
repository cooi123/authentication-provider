package com.example.authServer.util;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.security.*;
import java.security.interfaces.RSAKey;
import java.security.interfaces.RSAPublicKey;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

public class JWKey {
    private PublicKey publicKey;
    private PrivateKey privateKey;

    public JWKey(String algorithm) {
        keyPairGenerator(algorithm);
        Map<String, String> keyMap = new HashMap<>();
        Base64.Encoder base64UrlEncoder = Base64.getUrlEncoder().withoutPadding();
        String n = base64UrlEncoder.encodeToString(((RSAKey) publicKey).getModulus().toByteArray());
        String e = base64UrlEncoder.encodeToString(((RSAPublicKey) publicKey).getPublicExponent().toByteArray());
        keyMap.put("n", n);
        keyMap.put("e", e);
        keyMap.put("kty", "RSA");
        keyMap.put("alg", publicKey.getAlgorithm());
        keyMap.put("use", "sig");
        keyMap.put("kid", "1");
    }

    public PrivateKey getPrivateKey() {
        return privateKey;
    }

    public void keyPairGenerator(String algorithm) {
        KeyPairGenerator keyPairGenerator = null;
        try {
            keyPairGenerator = KeyPairGenerator.getInstance(algorithm);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
        keyPairGenerator.initialize(2048);
        KeyPair keyPair = keyPairGenerator.generateKeyPair();
        publicKey = keyPair.getPublic();

        privateKey = keyPair.getPrivate();
    }

    public Object getPublicKey() {
        return publicKey;
    }
}
