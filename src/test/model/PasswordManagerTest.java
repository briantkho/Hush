package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.provider.Arguments;

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
        password.addDetailEntry("ba12bA3kK9827", "email2", "account4");
        password.addDetailEntry("aB%$kIlw*@K*B", "email3", "account5");
        password.addDetailEntry("ABCDEFGH", "email2", "account6");
        password.addDetailEntry("!@#$%^&*", "email3", "account7");
        password.addDetailEntry("bAkdImlA", "email2", "account8");
        password.addDetailEntry("12345678", "email3", "account9");
        password.addDetailEntry("abcdefgh", "email3", "account10");

        assertEquals(PasswordStrength.WEAK, password.verifyDetailEntry("account1"));
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
    }

    @Test
    public void getDetailTest() {
        password.addDetailEntry("inputPassword", "inputEmail", "account1");

        List<String> expectedOutput = new ArrayList<>();

        expectedOutput.add("Account: account1");
        expectedOutput.add("Email: inputEmail");
        expectedOutput.add("Password: inputPassword");

        assertEquals(expectedOutput, password.getDetail("account1"));
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
    }
}
