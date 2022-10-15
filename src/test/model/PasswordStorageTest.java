package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;

public class PasswordStorageTest {

    private PasswordStorage password;

    @BeforeEach
    public void setup() {
        this.password = new PasswordStorage();
    }

    @Test
    public void addPasswordTest() {
        String expectedPassword = "testPW";
        String expectedEmail = "testEmail";
        String expectedWebsite = "testWebsite";

        password.addPassword(expectedPassword, expectedEmail, expectedWebsite);
        PasswordDetails expectedDetails = password.getDetails(expectedWebsite);

        assertEquals(expectedPassword, expectedDetails.getPassword());
        assertEquals(expectedEmail, expectedDetails.getEmail());
        assertEquals(expectedWebsite, expectedDetails.getWebsite());
    }

    @Test
    public void removeEmptyPasswordTest() {
        assertNull(password.removePassword("removePasswordWebsite"));
    }

    @Test
    public void removePasswordTest() {
        String expectedPassword = "removePasswordPW";
        String expectedEmail = "removePasswordEmail";
        String expectedWebsite = "removePasswordWebsite";

        password.addPassword(expectedPassword, expectedEmail, expectedWebsite);
        PasswordDetails removePW = password.removePassword(expectedWebsite);

        assertEquals(expectedPassword, removePW.getPassword());
        assertEquals(expectedEmail, removePW.getEmail());
        assertEquals(expectedWebsite, removePW.getWebsite());

    }

    @Test
    public void getEmptyDetailsTest() {
        assertNull(password.getDetails("Test"));
    }

    @Test
    public void getDetailsTest() {
        String expectedPassword = "getDetailsPW";
        String expectedEmail = "getDetailsEmail";
        String expectedWebsite = "getDetailsWebsite";

        password.addPassword(expectedPassword, expectedEmail, expectedWebsite);
        PasswordDetails expectedDetails = password.getDetails(expectedWebsite);

        assertEquals(expectedPassword, expectedDetails.getPassword());
        assertEquals(expectedEmail, expectedDetails.getEmail());
        assertEquals(expectedWebsite, expectedDetails.getWebsite());
    }
}
