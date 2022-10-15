package model;

public class PasswordDetails {
    private String password;
    private String email;
    private String website;

    public PasswordDetails(String password, String email, String website) {
        this.password = password;
        this.email = email;
        this.website = website;
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
    public String getWebsite() {
        return this.website;
    }
}
