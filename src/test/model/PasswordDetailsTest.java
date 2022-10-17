package model;

import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;

public class PasswordDetailsTest {

    @Test
    public void constructorTest() {
        PasswordDetails password = new PasswordDetails(
                "testPassword",
                "testEmail",
                "testAccountSite"
        );

        assertEquals("testPassword", password.getPassword());
        assertEquals("testEmail", password.getEmail());
        assertEquals("testAccountSite", password.getAccountSite());
    }
}
