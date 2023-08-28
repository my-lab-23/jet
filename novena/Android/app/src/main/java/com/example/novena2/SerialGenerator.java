package com.example.novena2;

import java.security.SecureRandom;

public class SerialGenerator {

    private static final String ALLOWED_CHARACTERS = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
    private static final int SERIAL_LENGTH = 12;

    public static String generateSerial() {
        StringBuilder sb = new StringBuilder(SERIAL_LENGTH);
        SecureRandom random = new SecureRandom();

        for (int i = 0; i < SERIAL_LENGTH; i++) {
            int randomIndex = random.nextInt(ALLOWED_CHARACTERS.length());
            char randomChar = ALLOWED_CHARACTERS.charAt(randomIndex);
            sb.append(randomChar);
        }

        return sb.toString();
    }
}
