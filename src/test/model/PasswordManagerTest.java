package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

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
        assertEquals("Added Account: testWebsite",
                new Event("Added Account: testWebsite").getDescription());
    }

    @Test
    public void addDetailEntryGeneratePWTest() {
        String inputEmail = "testEmail";
        String inputAccountSite = "testWebsite";
        int passwordLength = 20;

        try {
            password.addDetailEntry(passwordLength, true, true, inputEmail, inputAccountSite);
            String generatedPassword = PasswordUtils.generatePassword(
                    passwordLength,
                    true,
                    true
            );
            PasswordDetails passwordDetails = new PasswordDetails(generatedPassword, inputEmail, inputAccountSite);

            assertEquals(generatedPassword, passwordDetails.getPassword());
            assertEquals("testWebsite", passwordDetails.getAccountSite());
            assertEquals("testEmail", passwordDetails.getEmail());
            assertEquals("Added Account: testWebsite",
                    new Event("Added Account: testWebsite").getDescription());
        } catch (Exception e){
            fail("Exception thrown");
        }
    }

    @Test
    public void verifyDetailEntryTest() {
        password.addDetailEntry("inputPassword", "inputEmail", "account1");
        password.addDetailEntry("3k(&jJK<", "email2", "account2");
        password.addDetailEntry("3k(&jJK<*ks&", "email3", "account3");
        password.addDetailEntry("ba12bA3kK9827", "email2", "account4");
        password.addDetailEntry("aB%$kIlw*@K*B", "email3", "account5");
        password.addDetailEntry("ABCDEFGH", "email2", "account6");
        password.addDetailEntry("!@#$%^&*", "email3", "account7");
        password.addDetailEntry("bAkdImlA", "email2", "account8");
        password.addDetailEntry("12345678", "email3", "account9");
        password.addDetailEntry("abcdefgh", "email3", "account10");

        assertEquals(PasswordStrength.WEAK, password.verifyDetailEntry("account1"));
        assertEquals("account1 has a WEAK password",
                new Event("account1 has a WEAK password").getDescription());
        assertEquals(PasswordStrength.MEDIUM, password.verifyDetailEntry("account2"));
        assertEquals(PasswordStrength.STRONG, password.verifyDetailEntry("account3"));
        assertEquals(PasswordStrength.MEDIUM, password.verifyDetailEntry("account4"));
        assertEquals(PasswordStrength.MEDIUM, password.verifyDetailEntry("account5"));
        assertEquals(PasswordStrength.WEAK, password.verifyDetailEntry("account6"));
        assertEquals(PasswordStrength.WEAK, password.verifyDetailEntry("account7"));
        assertEquals(PasswordStrength.WEAK, password.verifyDetailEntry("account8"));
        assertEquals(PasswordStrength.WEAK, password.verifyDetailEntry("account9"));
        assertEquals(PasswordStrength.WEAK, password.verifyDetailEntry("account10"));
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
        assertEquals("Removed account1", new Event("Removed account1").getDescription());
    }

    @Test
    public void removeMultipleDetailTest() {
        password.addDetailEntry("inputPassword", "inputEmail", "account1");
        password.addDetailEntry("3k(&jJK<", "email2", "account2");
        password.addDetailEntry("3k(&jJK<*ks&", "email3", "account3");

        password.removeDetail("account1");
        assertEquals("Removed account1", new Event("Removed account1").getDescription());
        password.removeDetail("account2");
        assertEquals("Removed account2", new Event("Removed account2").getDescription());

        HashMap<String, List<String>> expectedOuput = new HashMap<>();
        List<String> expectedList = new ArrayList<>();
        expectedList.add("Account: account3");
        expectedList.add("Email: email3");
        expectedList.add("Password: 3k(&jJK<*ks&");

        expectedOuput.put("account3", expectedList);
        assertEquals(expectedOuput, password.getAllDetails());
    }

    @Test
    public void getDetailTest() {
        password.addDetailEntry("inputPassword", "inputEmail", "account1");

        List<String> expectedOutput = new ArrayList<>();

        expectedOutput.add("Account: account1");
        expectedOutput.add("Email: inputEmail");
        expectedOutput.add("Password: inputPassword");

        assertEquals(expectedOutput, password.getDetail("account1"));
        assertEquals("Account displayed: account1",
                new Event("Account displayed: account1").getDescription());
        assertEquals(1, password.getAllDetails().size());
    }

    @Test
    public void getAllDetailsTest() {
        password.addDetailEntry("inputPassword", "inputEmail", "account1");
        password.addDetailEntry("3k(&jJK<", "email2", "account2");

        HashMap<String, List<String>> expectedOutput = new HashMap<>();

        List<String> expectedList1 = new ArrayList<>();
        expectedList1.add("Account: account1");
        expectedList1.add("Email: inputEmail");
        expectedList1.add("Password: inputPassword");

        List<String> expectedList2 = new ArrayList<>();
        expectedList2.add("Account: account2");
        expectedList2.add("Email: email2");
        expectedList2.add("Password: 3k(&jJK<");

        expectedOutput.put("account1", expectedList1);
        expectedOutput.put("account2", expectedList2);

        assertEquals(expectedOutput, password.getAllDetails());
        assertEquals("All accounts displayed",
                new Event("All accounts displayed").getDescription());
    }
}
