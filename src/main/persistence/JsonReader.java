package persistence;

import model.PasswordManager;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;

// Represents a reader that reads Passwords from JSON data stored in file
public class JsonReader {
    private String source;

    // EFFECTS: constructs reader to read from source file
    public JsonReader(String source) {
        this.source = source;
    }

    // EFFECTS: reads passwords from file and returns it;
    // throws IOException if an error occurs reading data from file
    public PasswordManager read() throws IOException {
        String jsonData = readFile(source);
        JSONObject jsonObject = new JSONObject(jsonData);
        return parsePasswords(jsonObject);
    }

    // EFFECTS: reads source file as string and returns it
    private String readFile(String source) throws IOException {
        StringBuilder contentBuilder = new StringBuilder();

        try (Stream<String> stream = Files.lines(Paths.get(source), StandardCharsets.UTF_8)) {
            stream.forEach(s -> contentBuilder.append(s));
        }

        return contentBuilder.toString();
    }

    // EFFECTS: parses passwords from JSON object and returns it
    private PasswordManager parsePasswords(JSONObject jsonObject) {
        PasswordManager password = new PasswordManager();
        addPasswords(password, jsonObject);
        return password;
    }

    // MODIFIES: password
    // EFFECTS: parses a password from JSON object and adds them to passwords
    private void addPasswords(PasswordManager password, JSONObject jsonObject) {
        JSONArray jsonArray = jsonObject.getJSONArray("Passwords");
        for (Object json : jsonArray) {
            JSONObject nextPassword = (JSONObject) json;
            addPassword(password, nextPassword);
        }
    }

    // MODIFIES: pw
    // EFFECTS: parses password details from JSON object and adds it to password
    private void addPassword(PasswordManager pw, JSONObject jsonObject) {
        String account = jsonObject.getString("Account");
        String password = jsonObject.getString("Password");
        String email = jsonObject.getString("Email");

        pw.addDetailEntry(password, email, account);
    }
}
