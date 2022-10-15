package model;

import java.util.HashMap;

public class PasswordStorage {
    private HashMap<String, PasswordDetails> passwords = new HashMap<>();

    // REQUIRES: password.length >= 8
    // MODIFIES: this
    // EFFECTS: Adds a password with a username and website to the list
    public void addPassword(String password, String email, String website) {
        passwords.put(website, new PasswordDetails(password, email, website));
    }

    // MODIFIES: this
    // EFFECTS: Removes a password from the list
    public PasswordDetails removePassword(String website) {
        return passwords.remove(website);
    }

    // EFFECTS: Returns password details from the list
    public PasswordDetails getDetails(String website) {
        return passwords.get(website);
    }
}
