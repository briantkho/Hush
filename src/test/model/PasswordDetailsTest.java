package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;

public class PasswordDetailsTest {

    @Test
    public void constructorTest() {
        PasswordDetails password = new PasswordDetails("testPassword", "testEmail", "testWebsite");

        assertEquals("testPassword", password.getPassword());
        assertEquals("testEmail", password.getEmail());
        assertEquals("testWebsite", password.getWebsite());
    }
}
