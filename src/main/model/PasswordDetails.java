package model;

public class PasswordDetails {
    private String password;
    private String email;
    private String accountSite;

    // MODIFIES: this
    // EFFECTS: initialize password, email, and accountSite
    public PasswordDetails(String password, String email, String accountSite) {
        this.password = password;
        this.email = email;
        this.accountSite = accountSite;
    }

    // EFFECTS: Returns the user's password
    public String getPassword() {
        return this.password;
    }

    // EFFECTS: Returns the user's username
    public String getEmail() {
        return this.email;
    }

    // EFFECTS: Returns the user's website
    public String getAccountSite() {
        return this.accountSite;
    }
}
