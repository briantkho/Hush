package persistence;

import model.PasswordManager;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

public class JsonWriterTest extends JsonTest {
    @Test
    void testWriterInvalidFile() {
        try {
            PasswordManager passwords = new PasswordManager();
            JsonWriter writer = new JsonWriter("./data/my\0illegal:fileName.json");
            writer.open();
            fail("IOException was expected");
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    void testWriterEmptyPasswords() {
        try {
            PasswordManager passwords = new PasswordManager();
            JsonWriter writer = new JsonWriter("./data/testWriterEmptyWorkroom.json");
            writer.open();
            writer.write(passwords);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterEmptyWorkroom.json");
            passwords = reader.read();

            assertEquals(0, passwords.getPasswordsNumber());
        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }

    @Test
    void testWriterGeneralWorkroom() {
        try {
            PasswordManager passwords = new PasswordManager();
            List<String> expectedOutput1 = new ArrayList<>();
            List<String> expectedOutput2 = new ArrayList<>();

            JsonWriter writer = new JsonWriter("./data/testWriterPasswords.json");
            writer.open();
            passwords.addDetailEntry("testPassword1", "testEmail1", "testWebsite1");
            passwords.addDetailEntry("testPassword2", "testEmail2", "testWebsite2");
            writer.write(passwords);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterPasswords.json");
            passwords = reader.read();

            expectedOutput1.add("Account: testWebsite1");
            expectedOutput1.add("Email: testEmail1");
            expectedOutput1.add("Password: testPassword1");

            expectedOutput2.add("Account: testWebsite2");
            expectedOutput2.add("Email: testEmail2");
            expectedOutput2.add("Password: testPassword2");

            assertEquals(expectedOutput1, passwords.getDetail("testWebsite1"));
            assertEquals(2, passwords.getAllDetails().size());

            checkPasswords(expectedOutput1, "testWebsite1", passwords);
            checkPasswords(expectedOutput2, "testWebsite2", passwords);

        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }
}
