package com.javarush.task.task33.task3310;

import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.Random;

public class Helper {


//    public static String generateRandomString() {
//        String lowerAlphabet = "abcdefghijklmnopqrstuvwxyz";
//        String numbers = "0123456789";
//        String alphaNumeric = lowerAlphabet + numbers;
//        StringBuilder builder = new StringBuilder();
//        Random random = new Random();
//        int length = 26;
//        for (int i = 0; i < 26; i++) {
//            char randomChar = numbers.charAt(random.nextInt(numbers.length()));
//            builder.append(randomChar);
//        }
//        char randomChar = (lowerAlphabet.charAt(random.nextInt(lowerAlphabet.length())));
//        int phraseIndex = random.nextInt(26);
//        builder.replace(phraseIndex - 1, phraseIndex, Character.toString(randomChar));
//        return builder.toString();
//    }

    public static String generateRandomString() {
        return new BigInteger(130, new SecureRandom()).toString(32);
    }

    public static void printMessage(String message) {
        System.out.println(message);
    }
}
