package com.example.demo.util;

import org.springframework.security.crypto.encrypt.Encryptors;
import org.springframework.security.crypto.encrypt.TextEncryptor;

public class AESUtil {

    private static final String PASSWORD = "yourPassword"; // 사용자 지정 암호
    private static final String SALT = "5dc9b4a83c55897c"; // 사용자 지정 솔트

    private AESUtil() {
        throw new IllegalStateException("Utility class");
    }

    public static String encrypt(String input) {
        TextEncryptor encryptor = Encryptors.text(PASSWORD, SALT);
        return encryptor.encrypt(input);
    }

    public static String decrypt(String encryptedInput) {
        TextEncryptor encryptor = Encryptors.text(PASSWORD, SALT);
        return encryptor.decrypt(encryptedInput);
    }
}
