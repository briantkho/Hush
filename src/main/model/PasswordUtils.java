package model;

import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.regex.Pattern;

public class PasswordUtils {

    private static final String LETTERS = "abcdefghigklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
    private static final String NUMBERS = "1234567890";
    private static final String SYMBOLS = "~`!@#$%^&*()-_+={}\\[\\]|/:;\"\\'<>,.?";

    private static final Integer strongPasswordLength = 12;

    private static final Pattern CASE_PATTERN = Pattern.compile("(?=.*[a-z])(?=.*[A-Z])");
    private static final Pattern NUMERIC_PATTERN = Pattern.compile("\\d");
    private static final Pattern SPECIAL_CHARS_PATTERN = Pattern.compile("[^a-zA-Z\\d]");
    private static final Pattern LENGTH_PATTERN = Pattern.compile(
            String.format("[%s]{%d,}", String.format("a-zA-Z\\d%s", SYMBOLS), strongPasswordLength)
    );

    private static final List<Pattern> PASSWORD_CRITERIA_PATTERNS = Arrays.asList(
            CASE_PATTERN,
            NUMERIC_PATTERN,
            SPECIAL_CHARS_PATTERN,
            LENGTH_PATTERN
    );

    private static final Integer WEAK_MAX_SCORE = 2;
    private static final Integer MEDIUM_MAX_SCORE = 3;

    // REQUIRES: passwordLength >= 8, a true or false values for addSpecialChars and addNumbers
    // EFFECTS: generates a random password with a given length, and an option to include special characters/numbers
    protected static String generatePassword(
            int passwordLength,
            boolean addSpecialChars,
            boolean addNumbers
    ) throws Exception {
        StringBuilder randomPassword = new StringBuilder();
        Random random = new Random();
        String usedChars = LETTERS;

        if (addSpecialChars) {
            usedChars = usedChars.concat(SYMBOLS);
        }

        if (addNumbers) {
            usedChars = usedChars.concat(NUMBERS);
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

        for (Pattern pattern : PASSWORD_CRITERIA_PATTERNS) {
            if (pattern.matcher(password).find()) {
                passwordScore++;
            }
        }

        if (passwordScore > MEDIUM_MAX_SCORE) {
            return PasswordStrength.STRONG;
        } else if (passwordScore > WEAK_MAX_SCORE) {
            return PasswordStrength.MEDIUM;
        } else {
            return PasswordStrength.WEAK;
        }
    }
}
