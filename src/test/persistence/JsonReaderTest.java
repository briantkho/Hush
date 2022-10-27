package persistence;

import model.PasswordManager;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

public class JsonReaderTest extends JsonTest {

    @Test
    void testReaderNonExistentFile() {
        JsonReader reader = new JsonReader("./data/noSuchFile.json");
        try {
            PasswordManager passwords = reader.read();
            fail("IOException expected");
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    void testReaderEmptyPasswords() {
        JsonReader reader = new JsonReader("./data/testReaderEmptyPasswords.json");
        try {
            PasswordManager passwords = reader.read();
            assertEquals(0, passwords.getPasswordsNumber());
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }

    @Test
    void testReaderPasswords() {
        JsonReader reader = new JsonReader("./data/testReaderPasswords.json");
        try {
            PasswordManager passwords = reader.read();
            List<String> expectedOutput1 = new ArrayList<>();
            expectedOutput1.add("Account: testWebsite1");
            expectedOutput1.add("Email: testEmail1");
            expectedOutput1.add("Password: testPassword1");

            assertEquals(expectedOutput1, passwords.getDetail("testWebsite1"));

            List<String> expectedOutput2 = new ArrayList<>();
            expectedOutput2.add("Account: testWebsite2");
            expectedOutput2.add("Email: testEmail2");
            expectedOutput2.add("Password: testPassword2");

            List<String> expectedOutput3 = new ArrayList<>();
            expectedOutput3.add("Account: testWebsite3");
            expectedOutput3.add("Email: testEmail3");
            expectedOutput3.add("Password: testPassword3");

            HashMap<String, List<String>> expectedOutput = new HashMap<>();
            expectedOutput.put("testWebsite1", expectedOutput1);
            expectedOutput.put("testWebsite2", expectedOutput2);
            expectedOutput.put("testWebsite3", expectedOutput3);

            assertEquals(expectedOutput, passwords.getAllDetails());
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }
}
