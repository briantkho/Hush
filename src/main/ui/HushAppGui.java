package ui;

import exceptions.InvalidFieldsException;
import model.PasswordManager;
import persistence.JsonReader;
import persistence.JsonWriter;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.image.BufferedImage;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;
import java.util.Scanner;

// Represents the main window where the password manager can be used
public class HushAppGui extends JFrame {
    private PasswordManager passwords = new PasswordManager();
    private static final String JSON_STORE = "./data/passwords.json";
    private JsonWriter jsonWriter;
    private JsonReader jsonReader;

    private final JFrame frame;
    private JFrame menuFrame;

    private JFrame addAccountMenuFrame;

    // EFFECTS: runs the HushApp application
    public HushAppGui() throws IOException {
        super("Hush - Password Manager");

        jsonWriter = new JsonWriter(JSON_STORE);
        jsonReader = new JsonReader(JSON_STORE);


        frame = new JFrame();
        frame.setLayout(new GridLayout(0, 1));

        JButton menuActionButton = new JButton(new MainMenu());

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(0, 1));
        BufferedImage splashImage = ImageIO.read(getClass().getResource("/resources/images/splashLogo.png"));
        JLabel imageHeader = new JLabel(new ImageIcon(splashImage));

        panel.add(imageHeader);
        panel.add(menuActionButton);

        pageSetup(frame, panel, "Hush - Password Manager");
        init();
    }

    // MODIFIES: this
    // EFFECTS: initializes the password manager
    private void init() {
        PasswordManager passwords = new PasswordManager();
        Scanner input = new Scanner(System.in);
        input.useDelimiter("\n");
    }

    // Displays the main menu for the GUI
    private class MainMenu extends AbstractAction {
        MainMenu() {
            super("Main Menu");
        }

        // EFFECTS: Performs the action of displaying the menu
        @Override
        public void actionPerformed(ActionEvent evt) {
            displayMenuSetup();
            displayMenuBody();
        }

        // EFFECTS: sets up the main information for MainMenu()
        private void displayMenuSetup() {
            frame.dispose();
            menuFrame = new JFrame();
        }

        // EFFECTS: sets up the Body for MainMenu()
        private void displayMenuBody() {
            JButton addAccountButton = new JButton(new AddAccountMenu());
            JButton removeAccountButton = new JButton(new RemoveAccount());
            JButton viewAccountButton = new JButton(new ViewAccount());
            JButton viewAllAccountButton = new JButton("View All Accounts");
            JButton verifyPasswordButton = new JButton(new VerifyPassword());
            JButton saveAccountsButton = new JButton("Save Accounts");
            JButton loadAccountsButton = new JButton("Load Accounts");

            JPanel menuPanel = new JPanel();
            menuPanel.setLayout(new GridLayout(0, 1));

            menuSetter(menuPanel, addAccountButton, removeAccountButton, viewAccountButton, viewAllAccountButton,
                    verifyPasswordButton, saveAccountsButton, loadAccountsButton);

            pageSetup(menuFrame, menuPanel, "Main Menu");
        }

        // Adds the required GUI elements to the panel of MainMenu()
        private void menuSetter(JPanel panel, JButton addBtn, JButton removeBtn, JButton viewBtn, JButton viewAllBtn,
                                JButton verifyBtn, JButton saveBtn, JButton loadBtn) {
            panel.add(addBtn);
            panel.add(removeBtn);
            panel.add(viewBtn);
            panel.add(viewAllBtn);
            panel.add(verifyBtn);
            panel.add(saveBtn);
            panel.add(loadBtn);

            saveBtn.addActionListener(e -> {
                saveAccounts();
                JOptionPane.showMessageDialog(null, "Accounts Saved!");
            });

            loadBtn.addActionListener(e -> {
                loadAccounts();
                JOptionPane.showMessageDialog(null, "Accounts Loaded!");
            });

            viewAllBtn.addActionListener(e -> {
                viewAllAccounts();
            });
        }
    }

    // Displays add account menu for the GUI
    private class AddAccountMenu extends AbstractAction {
        AddAccountMenu() {
            super("Add Account");
        }

        // EFFECTS: performs the action of AddAccountMenu
        @Override
        public void actionPerformed(ActionEvent e) {
            menuFrame.dispose();

            addAccountMenuFrame = new JFrame();
            JPanel addAccountMenuPanel = new JPanel();

            JLabel genPasswordLabel = new JLabel("Generate a Password?");
            JButton genPasswordTrue = new JButton(new AddAccountGen());
            JButton genPasswordFalse = new JButton(new AddAccountNoGen());

            JPanel accountMenuBody = new JPanel();
            accountMenuBody.setLayout(new GridLayout(0, 1));
            accountMenuBody.add(genPasswordLabel);
            accountMenuBody.add(genPasswordTrue);
            accountMenuBody.add(genPasswordFalse);

            addAccountMenuPanel.add(accountMenuBody);

            addAccountMenuFrame.setTitle("Add Account");
            pageSetup(addAccountMenuFrame, addAccountMenuPanel, "Add Account");
        }
    }

    // Displays add account generator page for the GUI
    private class AddAccountGen extends AbstractAction {
        private final Boolean specSelect;
        private final Boolean numSelect;
        private JFrame addAccountGenFrame;

        AddAccountGen() {
            super("Yes");
            specSelect = false;
            numSelect = false;
        }

        // EFFECTS: performs the action of AddAccountGen()
        @Override
        public void actionPerformed(ActionEvent e) {
            addAccountMenuFrame.dispose();

            addAccountGenFrame = new JFrame();
            JPanel addAccountPanel = new JPanel();
            addAccountPanel.setLayout(new GridLayout(0, 1));

            JLabel passwordLengthLabel = new JLabel("Password Length (Greater or equal to 8): ");
            JTextField passwordLengthInput = new JTextField();

            JLabel passwordSpecialCharLabel = new JLabel("Include special characters: ");
            JRadioButton passwordSpecialCharTrue = new JRadioButton("Yes");
            JRadioButton passwordSpecialCharFalse = new JRadioButton("No");

            JLabel passwordNumberLabel = new JLabel("Include numbers: ");
            JRadioButton passwordNumberTrue = new JRadioButton("Yes");
            JRadioButton passwordNumberFalse = new JRadioButton("No");

            JLabel emailLabel = new JLabel("Email: ");
            JLabel websiteLabel = new JLabel("Website: ");
            JTextField emailField = new JTextField();
            JTextField websiteField = new JTextField();

            userGenInteraction(specSelect, numSelect, addAccountPanel, passwordLengthLabel, passwordLengthInput,
                    passwordSpecialCharLabel, passwordSpecialCharTrue, passwordSpecialCharFalse, passwordNumberLabel,
                    passwordNumberTrue, passwordNumberFalse, emailLabel, emailField, websiteLabel, websiteField
            );

            addAccountGenFrame.setTitle("Add Account");
            pageSetup(addAccountGenFrame, addAccountPanel, "Add Account");
        }

        // Handles the user interactions to generate a password
        private void userGenInteraction(Boolean specSelect, Boolean numSelect, JPanel panel, JLabel pwLenLabel,
                                        JTextField pwLen, JLabel pwSpecCharLabel, JRadioButton pwSpecCharTrue,
                                        JRadioButton pwSpecCharFalse, JLabel pwNumLabel, JRadioButton pwNumTrue,
                                        JRadioButton pwNumFalse, JLabel emLabel, JTextField emIn,
                                        JLabel siteLabel, JTextField siteIn) {
            JPanel inputPanel = new JPanel();
            JPanel radioPanel = new JPanel();


            ButtonGroup specCharGroup = new ButtonGroup();
            handleRadioBtns(specCharGroup, pwSpecCharTrue, pwSpecCharFalse);

            ButtonGroup numGroup = new ButtonGroup();
            handleRadioBtns(numGroup, pwNumTrue, pwNumFalse);

            JButton submitBtn = new JButton("Submit");
            submitBtn.addActionListener(e -> {
                try {
                    handleGenPassword(specSelect, numSelect, pwLen, emIn, siteIn, pwSpecCharTrue, pwSpecCharFalse,
                            pwNumTrue, pwNumFalse);
                    toMainMenu(addAccountMenuFrame);
                } catch (Exception ex) {
                    throw new RuntimeException(ex);
                }
            });

            addAccountSetter(inputPanel, radioPanel, panel, pwLenLabel, pwLen, pwSpecCharLabel, pwSpecCharTrue,
                    pwSpecCharFalse, pwNumLabel, pwNumTrue, pwNumFalse, emLabel, emIn, siteLabel, siteIn, submitBtn);
        }

        // Adds the required GUI elements to the panel of AddAccountGen()
        private void addAccountSetter(JPanel inputPanel, JPanel radioPanel, JPanel mainPanel, JLabel pwLenLabel,
                                      JTextField pwLen, JLabel pwSpecCharLabel, JRadioButton pwSpecCharTrue,
                                      JRadioButton pwSpecCharFalse, JLabel pwNumLabel, JRadioButton pwNumTrue,
                                      JRadioButton pwNumFalse, JLabel emLabel, JTextField emIn, JLabel siteLabel,
                                      JTextField siteIn, JButton submitBtn) {
            inputPanel.setLayout(new GridLayout(3, 2));
            inputPanel.add(pwLenLabel);
            inputPanel.add(pwLen);
            inputPanel.add(emLabel);
            inputPanel.add(emIn);
            inputPanel.add(siteLabel);
            inputPanel.add(siteIn);

            radioPanel.setLayout(new GridLayout(0, 3));
            radioPanel.add(pwSpecCharLabel);
            radioPanel.add(pwSpecCharTrue);
            radioPanel.add(pwSpecCharFalse);
            radioPanel.add(pwNumLabel);
            radioPanel.add(pwNumTrue);
            radioPanel.add(pwNumFalse);

            JButton homeBtn = new JButton("Main Menu");
            homeBtn.addActionListener(event -> toMainMenu(addAccountGenFrame));

            JPanel btnPanel = new JPanel();
            btnPanel.setLayout(new GridLayout(0, 2));
            btnPanel.add(submitBtn);
            btnPanel.add(homeBtn);

            mainPanel.add(inputPanel);
            mainPanel.add(radioPanel);
            mainPanel.add(btnPanel);
        }

        // Groups radio buttons in AddAccountGen()
        private void handleRadioBtns(ButtonGroup group, JRadioButton inOne, JRadioButton inTwo) {
            group.add(inOne);
            group.add(inTwo);
        }

        // Adds user inputted details and generated password into the list of accounts
        private void handleGenPassword(Boolean specSelect, Boolean numSelect, JTextField pwLen, JTextField emIn,
                                       JTextField siteIn, JRadioButton pwSpecCharTrue, JRadioButton pwSpecCharFalse,
                                       JRadioButton pwNumTrue, JRadioButton pwNumFalse) {
            if (pwNumTrue.isSelected()) {
                numSelect = true;
            } else if (pwNumFalse.isSelected()) {
                numSelect = false;
            }

            if (pwSpecCharTrue.isSelected()) {
                specSelect = true;
            } else if (pwSpecCharFalse.isSelected()) {
                specSelect = false;
            }

            int passwordLength = Integer.parseInt(pwLen.getText());

            try {
                passwords.addDetailEntry(passwordLength, specSelect, numSelect, emIn.getText(), siteIn.getText());
                JOptionPane.showMessageDialog(null, "Successfully added password!");
                addAccountGenFrame.dispose();
            } catch (InvalidFieldsException e) {
                JOptionPane.showMessageDialog(null, e.getMessage());
            }
        }
    }

    // Displays add account page for the GUI
    private class AddAccountNoGen extends AbstractAction {
        AddAccountNoGen() {
            super("No");
        }

        // EFFECTS: performs the action of AddAccountNoGen()
        @Override
        public void actionPerformed(ActionEvent e) {
            addAccountMenuFrame.dispose();

            JFrame addAccountNoGenFrame = new JFrame();

            JPanel addAccountPanel = new JPanel();
            JLabel emailLabel = new JLabel("Email: ");
            JLabel websiteLabel = new JLabel("Website: ");
            JLabel passwordLabel = new JLabel("Password: ");
            JTextField emailField = new JTextField();
            JTextField websiteField = new JTextField();
            JTextField passwordField = new JTextField();

            JButton submitBtn = new JButton("Submit");
            submitBtn.addActionListener(event -> {
                passwords.addDetailEntry(passwordField.getText(), emailField.getText(), websiteField.getText());
            });

            JButton homeBtn = new JButton("Main Menu");
            homeBtn.addActionListener(event -> toMainMenu(addAccountNoGenFrame));

            addAccountNoGenSetter(addAccountPanel, emailLabel, websiteLabel, passwordLabel, emailField, websiteField,
                    passwordField, submitBtn, homeBtn);

            pageSetup(addAccountNoGenFrame, addAccountPanel, "Add Account");
        }

        // Adds the required GUI elements to the panel of AddAccountNOGen()
        private void addAccountNoGenSetter(JPanel panel, JLabel emLabel, JLabel siteLabel, JLabel pwLabel,
                                           JTextField emIn, JTextField siteIn, JTextField pwIn, JButton submitBtn,
                                           JButton homeBtn) {
            JPanel inputPanel = new JPanel();
            JPanel btnPanel = new JPanel();

            inputPanel.setLayout(new GridLayout(3, 2));
            inputPanel.add(emLabel);
            inputPanel.add(emIn);
            inputPanel.add(siteLabel);
            inputPanel.add(siteIn);
            inputPanel.add(pwLabel);
            inputPanel.add(pwIn);

            btnPanel.setLayout(new GridLayout(0, 2));
            btnPanel.add(submitBtn);
            btnPanel.add(homeBtn);

            panel.setLayout(new GridLayout(0, 1));
            panel.add(inputPanel);
            panel.add(btnPanel);
        }
    }

    // Displays view account page for the GUI
    private class ViewAccount extends AbstractAction {
        ViewAccount() {
            super("View Account");
        }

        // EFFECTS: performs the action of ViewAccount()
        @Override
        public void actionPerformed(ActionEvent e) {
            menuFrame.dispose();

            JFrame viewAccountFrame = new JFrame();
            JPanel viewAccountPanel = new JPanel();

            viewAccountPanel.setLayout(new GridLayout(0, 2));

            JLabel viewLabel = new JLabel("Account to View: ");
            JTextField viewIn = new JTextField();
            JButton submitBtn = new JButton("Submit");

            submitBtn.addActionListener(event -> {
                StringBuilder accountString = new StringBuilder();

                for (String detail : passwords.getDetail(viewIn.getText())) {
                    accountString.append(detail).append("\n");
                }

                JOptionPane.showMessageDialog(null, accountString.toString());
                toMainMenu(viewAccountFrame);
            });

            JButton homeBtn = new JButton("Main Menu");
            homeBtn.addActionListener(event -> toMainMenu(viewAccountFrame));

            viewAccountPanel.add(viewLabel);
            viewAccountPanel.add(viewIn);
            viewAccountPanel.add(submitBtn);
            viewAccountPanel.add(homeBtn);

            pageSetup(viewAccountFrame, viewAccountPanel, "View Account");
        }
    }

    // Displays remove account page for the GUI
    private class RemoveAccount extends AbstractAction {
        JFrame removeAccountFrame;

        RemoveAccount() {
            super("Remove Account");
        }

        // EFFECTS: performs the action of RemoveAccount()
        @Override
        public void actionPerformed(ActionEvent e) {
            menuFrame.dispose();

            removeAccountFrame = new JFrame();
            JPanel removeAccountPanel = new JPanel();

            removeAccountPanel.setLayout(new GridLayout(0, 2));

            JLabel removeLabel = new JLabel("Account to Remove: ");
            JTextField removeIn = new JTextField();
            JButton submitBtn = new JButton("Submit");

            submitBtn.addActionListener(event -> {
                int prevSize = passwords.getAllDetails().size();
                passwords.removeDetail(removeIn.getText());

                if (prevSize == passwords.getAllDetails().size()) {
                    JOptionPane.showMessageDialog(null, "Failed to remove password!");
                    toMainMenu(removeAccountFrame);
                } else {
                    JOptionPane.showMessageDialog(null, "Removed " + removeIn.getText() + "!");
                    toMainMenu(removeAccountFrame);
                }
            });

            removeAccountSetter(removeAccountPanel, removeLabel, removeIn, submitBtn);
            pageSetup(removeAccountFrame, removeAccountPanel, "Remove Account");
        }

        // Adds the required GUI elements to the panel of RemoveAccount()
        private void removeAccountSetter(JPanel panel, JLabel removeLabel, JTextField removeIn, JButton submitBtn) {

            JButton homeBtn = new JButton("Main Menu");
            homeBtn.addActionListener(event -> toMainMenu(removeAccountFrame));

            panel.add(removeLabel);
            panel.add(removeIn);
            panel.add(submitBtn);
            panel.add(homeBtn);
        }
    }

    // Displays view all accounts page for the GUI
    private void viewAllAccounts() {
        if (passwords.getAllDetails().size() == 0) {
            JOptionPane.showMessageDialog(null,"No passwords stored!");
        } else {
            StringBuilder accountString = new StringBuilder();
            for (List<String> accounts : passwords.getAllDetails().values()) {
                accountString.append(accounts.get(0).substring(9)).append(": \n");
                for (String account : accounts) {
                    accountString.append(account.toString()).append("\n");
                }
                accountString.append("\n");
            }
            JOptionPane.showMessageDialog(null, accountString.toString());
        }
    }

    // Displays verify password page for the GUI
    private class VerifyPassword extends AbstractAction {
        VerifyPassword() {
            super("Verify Password");
        }

        // EFFECTS: performs the action of VerifyPassword()
        @Override
        public void actionPerformed(ActionEvent e) {
            menuFrame.dispose();

            JFrame verifyPasswordFrame = new JFrame();
            JPanel verifyPasswordPanel = new JPanel();
            JLabel verifyLabel = new JLabel("Account to verify: ");
            JTextField verifyIn = new JTextField();
            JButton submitBtn = new JButton("Submit");
            verifyPasswordPanel.setLayout(new GridLayout(0, 2));


            verifyPasswordPanel.add(verifyLabel);
            verifyPasswordPanel.add(verifyIn);
            verifyPasswordPanel.add(submitBtn);

            submitBtn.addActionListener(event -> {
                JOptionPane.showMessageDialog(null,
                        "Password is " + passwords.verifyDetailEntry(verifyIn.getText()).toString());
                toMainMenu(verifyPasswordFrame);
            });
            
            JButton homeBtn = new JButton("Main Menu");
            homeBtn.addActionListener(event -> toMainMenu(verifyPasswordFrame));

            verifyPasswordPanel.add(homeBtn);

            pageSetup(verifyPasswordFrame, verifyPasswordPanel, "Verify Account");
        }
    }

    // EFFECTS: saves passwords to file
    private void saveAccounts() {
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
    private void loadAccounts() {
        try {
            passwords = jsonReader.read();
            System.out.println("Loaded passwords from " + JSON_STORE);
        } catch (IOException e) {
            System.out.println("Unable to read from file: " + JSON_STORE);
        }
    }

    // Opens main menu and closes current frame
    private void toMainMenu(JFrame frame) {
        menuFrame.setVisible(true);
        frame.dispose();
    }

    // EFFECTS: structures the setup for frames
    private void pageSetup(JFrame frame, JPanel panel, String title) {
        panel.setBorder(new EmptyBorder(25, 25, 25, 25));
        frame.add(panel, BorderLayout.CENTER);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setTitle(title);
        frame.setVisible(true);
    }
}
