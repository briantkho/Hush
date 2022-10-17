package model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class PasswordStorage {
    private HashMap<String, PasswordDetails> passwords = new HashMap<>();

    // REQUIRES: password.length >= 8
    // MODIFIES: this
    // EFFECTS: Adds a password with a username and website to the list
    public void addPassword(String password, String email, String accountSite) {
        passwords.put(accountSite, new PasswordDetails(password, email, accountSite));
    }

    // MODIFIES: this
    // EFFECTS: Removes a password from the list
    public PasswordDetails removeAccount(String account) {
        return passwords.remove(account);
    }

    // EFFECTS: Returns password details from the list
    public PasswordDetails getDetails(String account) {
        return passwords.get(account);
    }

    // EFFECTS: Returns all passwords
    public List<PasswordDetails> getAllDetails() {
        return new ArrayList<>(passwords.values());
    }
}
