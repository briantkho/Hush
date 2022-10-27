package persistence;

import model.PasswordManager;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class JsonTest {
    protected void checkPasswords(List<String> expectedOut, String accountSite, PasswordManager password) {
        assertEquals(expectedOut, password.getDetail(accountSite));
    }
}
