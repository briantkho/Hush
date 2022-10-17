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
        String expectedAccountSite = "testWebsite";

        password.addPassword(expectedPassword, expectedEmail, expectedAccountSite);
        PasswordDetails expectedDetails = password.getDetails(expectedAccountSite);

        assertEquals(expectedPassword, expectedDetails.getPassword());
        assertEquals(expectedEmail, expectedDetails.getEmail());
        assertEquals(expectedAccountSite, expectedDetails.getAccountSite());
    }

    @Test
    public void removeEmptyPasswordTest() {
        assertNull(password.removeAccount("removePasswordWebsite"));
    }

    @Test
    public void removePasswordTest() {
        String expectedPassword = "removePasswordPW";
        String expectedEmail = "removePasswordEmail";
        String expectedAccountSite = "removePasswordWebsite";

        password.addPassword(expectedPassword, expectedEmail, expectedAccountSite);
        PasswordDetails removePW = password.removeAccount(expectedAccountSite);

        assertEquals(expectedPassword, removePW.getPassword());
        assertEquals(expectedEmail, removePW.getEmail());
        assertEquals(expectedAccountSite, removePW.getAccountSite());

    }

    @Test
    public void getEmptyDetailsTest() {
        assertNull(password.getDetails("Test"));
    }

    @Test
    public void getDetailsTest() {
        String expectedPassword = "getDetailsPW";
        String expectedEmail = "getDetailsEmail";
        String expectedAccountSite = "getDetailsWebsite";

        password.addPassword(expectedPassword, expectedEmail, expectedAccountSite);
        PasswordDetails expectedDetails = password.getDetails(expectedAccountSite);

        assertEquals(expectedPassword, expectedDetails.getPassword());
        assertEquals(expectedEmail, expectedDetails.getEmail());
        assertEquals(expectedAccountSite, expectedDetails.getAccountSite());
    }
}
