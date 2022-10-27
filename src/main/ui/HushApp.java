package ui;

import model.PasswordDetails;
import model.PasswordManager;
import model.PasswordStorage;
import persistence.JsonReader;
import persistence.JsonWriter;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.*;

// Password manager application
public class HushApp {
    private PasswordManager passwords = new PasswordManager();
    private static final String JSON_STORE = "./data/passwords.json";
    private JsonWriter jsonWriter;
    private JsonReader jsonReader;
    private Scanner input;

    // EFFECTS: runs the password manager application
    public HushApp() throws FileNotFoundException, Exception {
        input = new Scanner(System.in);
        jsonWriter = new JsonWriter(JSON_STORE);
        jsonReader = new JsonReader(JSON_STORE);
        runHush();
    }

    // MODIFIES: this
    // EFFECTS: processes user input
    private void runHush() throws FileNotFoundException, Exception {
        boolean keepGoing = true;
        String command = null;

        init();

        while (keepGoing) {
            lineBreak();
            welcome();
            lineBreak();
            displayMenu();
            command = input.next();
            command = command.toLowerCase().trim();

            if (command.equals("quit")) {
                keepGoing = false;
            } else {
                processCommand(command);
            }
        }
        System.out.println("\nGoodbye!");
    }

    // MODIFIES: this
    // EFFECTS: processes user command
    private void processCommand(String command) throws Exception {
        if (command.equals("add")) {
            doAddPassword();
        } else if (command.equals("remove")) {
            doRemovePassword();
        } else if (command.equals("view")) {
            doViewPassword();
        } else if (command.equals("viewall")) {
            doViewAllPassword();
        } else if (command.equals("verify")) {
            doVerifyPassword();
        } else if (command.equals("help")) {
            displayMenu();
        } else if (command.equals("save")) {
            savePassword();
        } else if (command.equals("load")) {
            loadPassword();
        } else {
            System.out.println("Command not found: run \"help\" for a list of commands.");
        }
    }

    // MODIFIES: this
    // EFFECTS: initializes accounts
    private void init() {
        PasswordManager passwords = new PasswordManager();
        input = new Scanner(System.in);
        input.useDelimiter("\n");
    }

    // EFFECTS: displays program introduction
    private void welcome() {
        System.out.println("Welcome to Hush, a safe and secure password manager!");
    }

    // EFFECTS: linebreak for better console UI
    private void lineBreak() {
        System.out.println(" ");
    }

    // EFFECTS: displays menu of options to user
    private void displayMenu() {
        System.out.println("Select from:");
        System.out.println("add -> add an account");
        System.out.println("remove -> remove an account");
        System.out.println("view -> view account details");
        System.out.println("viewall -> view all account details");
        System.out.println("verify -> check password strength");
        System.out.println("save -> save passwords");
        System.out.println("load -> load passwords");
        System.out.println("quit -> close application");
    }

    // MODIFIES: this
    // EFFECTS: adds a password and generates a password if desired
    private void doAddPassword() throws Exception {
        System.out.print("Do you want to generate a new password? true/false: ");
        Boolean genPassword = input.nextBoolean();

        if (genPassword == true) {
            System.out.print("Enter a password length greater or equal to 8: ");
            int passwordLength = input.nextInt();

            System.out.print("Do you want special characters? true/false: ");
            Boolean specialChars = input.nextBoolean();

            System.out.print("Do you want numbers? true/false: ");
            Boolean numberChars = input.nextBoolean();

            System.out.print("Enter your email: ");
            String emailInput = input.next();
            System.out.print("Enter your website: ");
            String websiteInput = input.next();

            passwords.addDetailEntry(passwordLength, specialChars, numberChars, emailInput, websiteInput);
        } else if (genPassword == false) {
            System.out.print("Enter your password: ");
            String passwordInput = input.next();
            System.out.print("Enter your email: ");
            String emailInput = input.next();
            System.out.print("Enter your website: ");
            String websiteInput = input.next();

            passwords.addDetailEntry(passwordInput, emailInput, websiteInput);
        }
    }

    // MODIFIES: this
    // EFFECTS: removes a given account
    private void doRemovePassword() {
        System.out.print("Account to remove: ");
        String removePassword = input.next();
        passwords.removeDetail(removePassword);
    }

    // EFFECTS: determines password strength on given account
    private void doVerifyPassword() {
        System.out.print("Account to check: ");
        String verifyPassword = input.next();
        System.out.println(passwords.verifyDetailEntry(verifyPassword));
    }

    // EFFECTS: gets account details from a single account
    private void doViewPassword() {
        System.out.print("Account to view: ");
        String viewPassword = input.next();

        System.out.println(passwords.getDetail(viewPassword));
    }

    // EFFECTS: gets all account details from the user
    private void doViewAllPassword() {
        System.out.println(passwords.getAllDetails());
    }

    // EFFECTS: saves passwords to file
    private void savePassword() {
        try {
            jsonWriter.open();
            jsonWriter.write(passwords);
            jsonWriter.close();
            System.out.println("Saved passwords to " + JSON_STORE);
        } catch (FileNotFoundException e) {
            System.out.println("Unable to write to file: " + JSON_STORE);
        }
    }

    // MODIFIES: this
    // EFFECTS: loads passwords from file
    private void loadPassword() {
        try {
            passwords = jsonReader.read();
            System.out.println("Loaded passwords from " + JSON_STORE);
        } catch (IOException e) {
            System.out.println("Unable to read from file: " + JSON_STORE);
        }
    }
}