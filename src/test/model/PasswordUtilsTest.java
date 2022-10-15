package model;

import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

public class PasswordUtilsTest {

    private static final String letters = "abcdefghigklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
    private static final String numbers = "1234567890";
    private static final String symbols = "~`!@#$%^&*()-_+={}[]|/:;\\\"'<>,.?";

    private static final String lettersAndSymbols = letters.concat(symbols);
    private static final String lettersAndNumbers = letters.concat(numbers);
    private static final String allChars = lettersAndSymbols.concat(numbers);

    private PasswordUtils password;

    @BeforeEach
    public void setup() {
        this.password = new PasswordUtils();
    }

    @Test
    public void generatePasswordLengthTest() throws Exception {
        int pwLength = 8;
        String generatedPassword = PasswordUtils.generatePassword(
                pwLength,
                false,
                false
        );

        assertEquals(pwLength, generatedPassword.length());
    }

    @Test
    public void generatePasswordLessThanLengthTest() throws Exception {
        int pwLength = 7;

        assertThrows(Exception.class, () -> PasswordUtils.generatePassword(
                pwLength,
                false,
                false
        ));
    }

    @Test
    public void generateDefaultPasswordTest() throws Exception {
        int pwLength = 10;
        String generatedPassword = PasswordUtils.generatePassword(
                pwLength,
                false,
                false
        );

        assertPasswordInSet(generatedPassword, letters);
    }

    @Test
    public void generateSpecialCharsPasswordTest() throws Exception {
        int pwLength = 20;
        String generatedPassword = PasswordUtils.generatePassword(
                pwLength,
                true,
                false
        );

        assertPasswordInSet(generatedPassword, lettersAndSymbols);
    }

    @Test
    public void generateNumbersPasswordTest() throws Exception {
        int pwLength = 20;
        String generatedPassword = PasswordUtils.generatePassword(
                pwLength,
                false,
                true
        );

        assertPasswordInSet(generatedPassword, lettersAndNumbers);
    }

    @Test
    public void generateAllCharsPasswordTest() throws Exception {
        int pwLength = 20;
        String generatedPassword = PasswordUtils.generatePassword(
                pwLength,
                true,
                true
        );

        assertPasswordInSet(generatedPassword, allChars);
    }

    private void assertPasswordInSet(String generatedPassword, String charSet) {
        for (int i = 0; i < generatedPassword.length(); i++) {
            assertNotEquals(-1, charSet.indexOf(generatedPassword.charAt(i)));
        }
    }

    @ParameterizedTest
    @MethodSource("providePasswordStrengthParameters")
    public void passwordStrengthTest (String password, PasswordStrength expectedStrength) {
        assertEquals(expectedStrength, PasswordUtils.validatePassword(password));
    }

    private static Stream<Arguments> providePasswordStrengthParameters() {
        return Stream.of(
                Arguments.of("aB1!Cc2@Dd3#", PasswordStrength.STRONG),
                Arguments.of("ba12bA3kK9827", PasswordStrength.MEDIUM),
                Arguments.of("aB%$kIlw*@K*B", PasswordStrength.MEDIUM),
                Arguments.of("ABCDEFGH", PasswordStrength.WEAK),
                Arguments.of("!@#$%^&*", PasswordStrength.WEAK),
                Arguments.of("bAkdImlA", PasswordStrength.WEAK),
                Arguments.of("12345678", PasswordStrength.WEAK),
                Arguments.of("abcdefgh", PasswordStrength.WEAK)
        );
    }
}
