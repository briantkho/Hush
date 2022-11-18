package model;

import exceptions.InvalidFieldsException;
import org.json.JSONArray;
import org.json.JSONObject;
import persistence.Writable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

// HushApp class calls directly from here (only instantiate 1 field). Contains all necessary function calls for UI
public class PasswordManager implements Writable {
    private final PasswordStorage passwords = new PasswordStorage();
    private PasswordDetails detailEntry;

    // MODIFIES: this
    // EFFECTS: Adds an account to passwordStorage
    public void addDetailEntry(String password, String email, String accountSite) {
        this.detailEntry = new PasswordDetails(password, email, accountSite);
        passwords.addPassword(detailEntry.getPassword(), detailEntry.getEmail(), detailEntry.getAccountSite());
    }

    // MODIFIES: this
    // EFFECTS: Generates a password with given fields, then adds an account to passwordStorage
    public void addDetailEntry(
            int length,
            boolean specialChar,
            boolean numberChar,
            String email,
            String accountSite
    ) throws InvalidFieldsException {
        String genPassword = PasswordUtils.generatePassword(length, specialChar, numberChar);
        detailEntry = new PasswordDetails(genPassword, email, accountSite);
        passwords.addPassword(detailEntry.getPassword(), detailEntry.getEmail(), detailEntry.getAccountSite());
    }

    // EFFECTS: Determines password strength from a given account
    public PasswordStrength verifyDetailEntry(String accountSite) {
        String retrievePassword = passwords.getDetails(accountSite).getPassword();

        return PasswordUtils.validatePassword(retrievePassword);
    }

    // MODIFIES: this.passwords
    // EFFECTS: Removes an account from PasswordStorage
    public void removeDetail(String accountSite) {
        passwords.removeAccount(accountSite);
    }

    // EFFECTS: Returns all account details in a list
    public List<String> getDetail(String accountSite) {
        List<String> displayDetails = new ArrayList<>();
        displayDetails.add("Account: ".concat(passwords.getDetails(accountSite).getAccountSite()));
        displayDetails.add("Email: ".concat(passwords.getDetails(accountSite).getEmail()));
        displayDetails.add("Password: ".concat(passwords.getDetails(accountSite).getPassword()));

        return displayDetails;
    }

    // EFFECTS: Returns all password details in a hashmap
    public HashMap<String, List<String>> getAllDetails() {
        HashMap<String, List<String>> listAllPasswords = new HashMap<>();

        for (int i = 0; i < passwords.getAllDetails().size(); i++) {
            List<String> displayDetails = new ArrayList<>();
            displayDetails.add("Account: ".concat(passwords.getAllDetails().get(i).getAccountSite()));
            displayDetails.add("Email: ".concat(passwords.getAllDetails().get(i).getEmail()));
            displayDetails.add("Password: ".concat(passwords.getAllDetails().get(i).getPassword()));

            listAllPasswords.put(passwords.getAllDetails().get(i).getAccountSite(), displayDetails);
        }
        return listAllPasswords;
    }

    // EFFECTS: gets number of passwords in the list from PasswordStorage
    public int getPasswordsNumber() {
        return passwords.getPasswordNumber();
    }

    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("Passwords", passwordsToJson());
        return json;
    }

    // EFFECTS: returns a password in passwords as a JSON array
    public JSONArray passwordsToJson() {
        JSONArray jsonArray = new JSONArray();
        for (PasswordDetails password : passwords.getAllDetails()) {
            jsonArray.put(password.toJson());
        }
        return jsonArray;
    }
}
