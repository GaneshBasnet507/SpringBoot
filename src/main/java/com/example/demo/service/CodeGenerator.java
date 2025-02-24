package com.example.demo.service;
import java.security.SecureRandom;
import java.util.Base64;
public class CodeGenerator {
    private static final SecureRandom secureRandom = new SecureRandom();
    private static final Base64.Encoder base64Encoder = Base64.getUrlEncoder();
    public static String generateCode() {
        byte[] randomBytes = new byte[6];
        secureRandom.nextBytes(randomBytes);
        return base64Encoder.encodeToString(randomBytes);
    }
}

