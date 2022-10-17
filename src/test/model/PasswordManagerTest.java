package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class PasswordManagerTest {
    private PasswordManager password;

    @BeforeEach
    public void setup() {
        this.password = new PasswordManager();
    }

    @Test
    public void addDetailEntryTest() {
        String inputPassword = "testPW";
        String inputEmail = "testEmail";
        String inputAccountSite = "testWebsite";

        password.addDetailEntry(inputPassword, inputEmail, inputAccountSite);
        List<String> expectedDetails = password.getDetail(inputAccountSite);

        assertEquals("Account: testWebsite", expectedDetails.get(0));
        assertEquals("Email: testEmail", expectedDetails.get(1));
        assertEquals("Password: testPW", expectedDetails.get(2));
    }

    @Test
    public void verifyDetailEntryTest() {
        password.addDetailEntry("inputPassword", "inputEmail", "account1");
        password.addDetailEntry("3k(&jJK<", "email2", "account2");
        password.addDetailEntry("3k(&jJK<*ks&", "email3", "account3");

        assertEquals(PasswordStrength.WEAK, password.verifyDetailEntry("account1"));
        assertEquals(PasswordStrength.MEDIUM, password.verifyDetailEntry("account2"));
        assertEquals(PasswordStrength.STRONG, password.verifyDetailEntry("account3"));
    }

    @Test
    public void removeDetailTest() {
        password.addDetailEntry("inputPassword", "inputEmail", "account1");
        password.addDetailEntry("3k(&jJK<", "email2", "account2");

        password.removeDetail("account1");
        HashMap<String, List<String>> expectedOuput = new HashMap<>();
        List<String> expectedList = new ArrayList<>();
        expectedList.add("Account: account2");
        expectedList.add("Email: email2");
        expectedList.add("Password: 3k(&jJK<");

        expectedOuput.put("account2", expectedList);
        assertEquals(expectedOuput, password.getAllDetails());
    }
}
