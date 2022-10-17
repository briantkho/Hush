package model;

import java.util.*;
import java.util.Random;
import java.util.regex.Pattern;

public class PasswordUtils {

    private static final String letters = "abcdefghigklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
    private static final String numbers = "1234567890";
    private static final String symbols = "~`!@#$%^&*()-_+={}\\[\\]|/:;\"\\'<>,.?";

    private static final Integer strongPasswordLength = 12;

    private static final Pattern passwordCasePattern = Pattern.compile("(?=.*[a-z])(?=.*[A-Z])");
    private static final Pattern passwordNumericPattern = Pattern.compile("\\d");
    private static final Pattern passwordSpecialCharsPattern = Pattern.compile("[^a-zA-Z\\d]");
    private static final Pattern passwordLengthPattern = Pattern.compile(
            String.format("[%s]{%d,}", String.format("a-zA-Z\\d%s", symbols), strongPasswordLength)
    );

    private static final List<Pattern> passwordCriteriaPatterns = List.of(
            passwordCasePattern,
            passwordNumericPattern,
            passwordSpecialCharsPattern,
            passwordLengthPattern
    );

    private static final Integer weakPasswordMaxScore = 2;
    private static final Integer mediumPasswordMaxScore = 3;

    // REQUIRES: passwordLength >= 8, a true or false values for addSpecialChars and addNumbers
    // EFFECTS: generates a random password with a given length, and an option to include special characters/numbers
    protected static String generatePassword(
            int passwordLength,
            boolean addSpecialChars,
            boolean addNumbers
    ) throws Exception {
        StringBuilder randomPassword = new StringBuilder();
        Random random = new Random();
        String usedChars = letters;

        if (addSpecialChars) {
            usedChars = usedChars.concat(symbols);
        }

        if (addNumbers) {
            usedChars = usedChars.concat(numbers);
        }

        if (passwordLength >= 8) {
            for (int i = 0; i < passwordLength; i++) {
                randomPassword.append(usedChars.charAt(random.nextInt(usedChars.length())));
            }
        } else {
            throw new Exception(String.format("Invalid length: %d", passwordLength));
        }
        return randomPassword.toString();
    }

    // EFFECTS: returns if the password is strong, medium, or weak
    public static PasswordStrength validatePassword(String password) {
        int passwordScore = 0;

        for (Pattern pattern : passwordCriteriaPatterns) {
            if (pattern.matcher(password).find()) {
                passwordScore++;
            }
        }

        if (passwordScore > mediumPasswordMaxScore) {
            return PasswordStrength.STRONG;
        } else if (passwordScore > weakPasswordMaxScore) {
            return PasswordStrength.MEDIUM;
        } else {
            return PasswordStrength.WEAK;
        }
    }
}
