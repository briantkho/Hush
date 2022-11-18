package ui;

import model.PasswordManager;
import persistence.JsonReader;
import persistence.JsonWriter;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;
import java.util.Scanner;

public class HushAppGui extends JFrame {
    private static final int WIDTH = 550;
    private static final int HEIGHT = 550;

    private PasswordManager passwords = new PasswordManager();
    private static final String JSON_STORE = "./data/passwords.json";
    private JsonWriter jsonWriter;
    private JsonReader jsonReader;

    private JFrame frame;
    private JFrame menuFrame;
    private JButton menuActionButton;
    private JPanel panel;

    private JFrame addAccountMenuFrame;
    private JFrame addAccountNoGenFrame;
    private JFrame addAccountGenFrame;
    private JFrame viewAccountFrame;
    private JFrame viewAllAccountsFrame;
    private JFrame removeAccountFrame;
    private JFrame verifyPasswordFrame;

    public HushAppGui() {
        super("Hush - Password Manager");

        jsonWriter = new JsonWriter(JSON_STORE);
        jsonReader = new JsonReader(JSON_STORE);

        frame = new JFrame();

        this.menuActionButton = new JButton(new MainMenu());

        panel = new JPanel();
        panel.add(menuActionButton);

        frame.add(panel, BorderLayout.CENTER);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setTitle("Hush - Password Manager");
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setSize(WIDTH, HEIGHT);
        frame.setVisible(true);
        init();
    }

    private void init() {
        PasswordManager passwords = new PasswordManager();
        Scanner input = new Scanner(System.in);
        input.useDelimiter("\n");
    }

    private class MainMenu extends AbstractAction {
        MainMenu() {
            super("Main Menu");
        }

        // EFFECTS: Performs the action of displaying the menu
        @Override
        public void actionPerformed(ActionEvent evt) {
            displayMenuSetup(frame);
            displayMenuBody();
        }

        private void displayMenuSetup(JFrame frame) {
            disposeFrame(frame);
            menuFrame = new JFrame();
        }

        private void displayMenuBody() {
            JButton addAccountButton = new JButton(new AddAccountMenu());
            JButton removeAccountButton = new JButton(new RemoveAccount());
            JButton viewAccountButton = new JButton(new ViewAccount());
            JButton viewAllAccountButton = new JButton(new ViewAllAccounts());
            JButton verifyPasswordButton = new JButton(new VerifyPassword());
            JButton saveAccountsButton = new JButton("Save Accounts");
            JButton loadAccountsButton = new JButton("Load Accounts");

            JPanel menuPanel = new JPanel();
            menuPanel.setLayout(new GridLayout(0, 1));

            menuSetter(menuPanel, addAccountButton, removeAccountButton, viewAccountButton, viewAllAccountButton,
                    verifyPasswordButton, saveAccountsButton, loadAccountsButton);

            menuFrame.add(menuPanel, BorderLayout.CENTER);
            menuFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            menuFrame.setTitle("Main Menu");
            menuFrame.pack();
            menuFrame.setLocationRelativeTo(null);
            menuFrame.setSize(WIDTH, HEIGHT);
            menuFrame.setVisible(true);
        }

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
        }
    }

    private class AddAccountMenu extends AbstractAction {
        AddAccountMenu() {
            super("Add Account");
        }

        @Override
        public void actionPerformed(ActionEvent e) {
//            menuFrame.dispose();

            disposeFrame(menuFrame);

            addAccountMenuFrame = new JFrame();
            JPanel addAccountMenuPanel = new JPanel();

            JLabel genPasswordLabel = new JLabel("Generate a Password: ");
            JButton genPasswordTrue = new JButton(new AddAccountGen());
            JButton genPasswordFalse = new JButton(new AddAccountNoGen());

            JPanel accountMenuBody = new JPanel();
            accountMenuBody.setLayout(new GridLayout(0, 3));
            accountMenuBody.add(genPasswordLabel);
            accountMenuBody.add(genPasswordTrue);
            accountMenuBody.add(genPasswordFalse);

            addAccountMenuPanel.add(accountMenuBody);

            addAccountMenuFrame.setTitle("Add Account");
            pageSetup(addAccountMenuFrame, addAccountMenuPanel);
        }
    }

    private class AddAccountGen extends AbstractAction {
        private Boolean specSelect;
        private Boolean numSelect;

        AddAccountGen() {
            super("Yes");
            specSelect = false;
            numSelect = false;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            disposeFrame(addAccountMenuFrame);

            addAccountGenFrame = new JFrame();
            JPanel addAccountPanel = new JPanel();

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

            addAccountGenPanelSetter(specSelect, numSelect, addAccountPanel, passwordLengthLabel, passwordLengthInput,
                    passwordSpecialCharLabel, passwordSpecialCharTrue, passwordSpecialCharFalse, passwordNumberLabel,
                    passwordNumberTrue, passwordNumberFalse, emailLabel, emailField, websiteLabel, websiteField
            );

            addAccountGenFrame.setTitle("Add Account");
            pageSetup(addAccountGenFrame, addAccountPanel);
        }
    }

    private void addAccountGenPanelSetter(Boolean specSelect, Boolean numSelect,  JPanel panel, JLabel pwLenLabel,
                                          JTextField pwLen, JLabel pwSpecCharLabel, JRadioButton pwSpecCharTrue,
                                          JRadioButton pwSpecCharFalse, JLabel pwNumLabel, JRadioButton pwNumTrue,
                                          JRadioButton pwNumFalse, JLabel emLabel, JTextField emIn, JLabel siteLabel,
                                          JTextField siteIn
    ) {
        JPanel inputPanel = new JPanel();
        JPanel radioPanel = new JPanel();

        addAccountFieldAdder(inputPanel, radioPanel, panel, pwLenLabel, pwLen, pwSpecCharLabel, pwSpecCharTrue,
                pwSpecCharFalse, pwNumLabel, pwNumTrue, pwNumFalse, emLabel, emIn, siteLabel, siteIn);

        ButtonGroup specCharGroup = new ButtonGroup();
        handleRadioBtns(specCharGroup, pwSpecCharTrue, pwSpecCharFalse);

        ButtonGroup numGroup = new ButtonGroup();
        handleRadioBtns(numGroup, pwNumTrue, pwNumFalse);

        JButton submitBtn = new JButton("Submit");
        submitBtn.addActionListener(e -> {
            try {
                handleAddAccountGen(specSelect, numSelect, pwLen, emIn, siteIn, pwSpecCharTrue, pwSpecCharFalse,
                        pwNumTrue, pwNumFalse);
            } catch (Exception ex) {
                throw new RuntimeException(ex);
            }
        });
        panel.add(submitBtn);

        JButton homeBtn = new JButton(new MainMenu());
        panel.add(homeBtn);
    }

    private void addAccountFieldAdder(JPanel inputPanel, JPanel radioPanel, JPanel mainPanel, JLabel pwLenLabel,
                                      JTextField pwLen, JLabel pwSpecCharLabel, JRadioButton pwSpecCharTrue,
                                      JRadioButton pwSpecCharFalse, JLabel pwNumLabel, JRadioButton pwNumTrue,
                                      JRadioButton pwNumFalse, JLabel emLabel, JTextField emIn, JLabel siteLabel,
                                      JTextField siteIn) {

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

        mainPanel.add(inputPanel);
        mainPanel.add(radioPanel);
    }

    private ButtonGroup handleRadioBtns(ButtonGroup group, JRadioButton inOne, JRadioButton inTwo) {
        group.add(inOne);
        group.add(inTwo);

        return group;
    }

    private void handleAddAccountGen(Boolean specSelect, Boolean numSelect, JTextField pwLen, JTextField emIn,
                                  JTextField siteIn, JRadioButton pwSpecCharTrue, JRadioButton pwSpecCharFalse,
                                  JRadioButton pwNumTrue, JRadioButton pwNumFalse) throws Exception {

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

        passwords.addDetailEntry(passwordLength, specSelect, numSelect, emIn.getText(), siteIn.getText());
        new MainMenu();
    }

    private class AddAccountNoGen extends AbstractAction {
        AddAccountNoGen() {
            super("No");
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            addAccountMenuFrame.dispose();

            addAccountNoGenFrame = new JFrame();

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

            JButton homeBtn = new JButton(new MainMenu());

            addAccountNoGenSetter(addAccountPanel, emailLabel, websiteLabel, passwordLabel, emailField, websiteField,
                    passwordField, submitBtn, homeBtn);

            addAccountNoGenFrame.setTitle("Add Account");
            pageSetup(addAccountNoGenFrame, addAccountPanel);
        }
    }

    private void addAccountNoGenSetter(JPanel panel, JLabel emLabel, JLabel siteLabel, JLabel pwLabel, JTextField emIn,
                                       JTextField siteIn, JTextField pwIn, JButton submitBtn, JButton homeBtn) {
        JPanel inputPanel = new JPanel();
        JPanel btnPanel = new JPanel();

        inputPanel.setLayout(new GridLayout(3, 2));
        inputPanel.add(emLabel);
        inputPanel.add(emIn);
        inputPanel.add(siteLabel);
        inputPanel.add(siteIn);
        inputPanel.add(pwLabel);
        inputPanel.add(pwIn);

        btnPanel.setLayout(new GridLayout(1, 2));
        btnPanel.add(submitBtn);
        btnPanel.add(homeBtn);

        panel.setLayout(new GridLayout(3, 2));
        panel.add(inputPanel);
        panel.add(btnPanel);
    }

    private class ViewAccount extends AbstractAction {
        ViewAccount() {
            super("View Account");
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            menuFrame.dispose();

            viewAccountFrame = new JFrame();
            JPanel viewAccountPanel = new JPanel();

            viewAccountPanel.setLayout(new GridLayout(0, 1));

            JLabel viewLabel = new JLabel("Account to View: ");
            JTextField viewIn = new JTextField();
            JButton submitBtn = new JButton("Submit");

            JPanel resultsPanel = new JPanel();
            JLabel resultsLabel = new JLabel("Details will display here");
            resultsPanel.add(resultsLabel);

            submitBtn.addActionListener(event -> {
                resultsLabel.setText(passwords.getDetail(viewIn.getText()).toString());
            });

            JButton homeBtn = new JButton(new MainMenu());

            viewAccountPanel.add(viewLabel);
            viewAccountPanel.add(viewIn);
            viewAccountPanel.add(resultsPanel);
            viewAccountPanel.add(submitBtn);
            viewAccountPanel.add(homeBtn);

            pageSetup(viewAccountFrame, viewAccountPanel);
        }
    }

    private class RemoveAccount extends AbstractAction {
        RemoveAccount() {
            super("Remove Account");
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            menuFrame.dispose();

            removeAccountFrame = new JFrame();
            JPanel removeAccountPanel = new JPanel();

            removeAccountPanel.setLayout(new GridLayout(0, 1));

            JLabel removeLabel = new JLabel("Account to Remove: ");
            JTextField removeIn = new JTextField();
            JButton submitBtn = new JButton("Submit");
            JPanel resultsPanel = new JPanel();
            JLabel resultsLabel = new JLabel();

            submitBtn.addActionListener(event -> {
                int prevSize = passwords.getAllDetails().size();
                passwords.removeDetail(removeIn.getText());

                if (prevSize == passwords.getAllDetails().size()) {
                    resultsLabel.setText("Failed to remove password");
                } else {
                    resultsLabel.setText("Removed " + removeIn.getText() + " account!");
                }
            });

            removeAccountSetter(removeAccountPanel, removeLabel, removeIn, submitBtn, resultsPanel);
            pageSetup(removeAccountFrame, removeAccountPanel);
        }

        private void removeAccountSetter(JPanel panel, JLabel removeLabel, JTextField removeIn, JButton submitBtn,
                                         JPanel resultsPanel) {
            JButton homeBtn = new JButton(new MainMenu());
            panel.add(removeLabel);
            panel.add(removeIn);
            panel.add(submitBtn);
            panel.add(resultsPanel);
            panel.add(homeBtn);
        }
    }

    private class ViewAllAccounts extends AbstractAction {
        ViewAllAccounts() {
            super("View All Accounts");
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            menuFrame.dispose();

            viewAllAccountsFrame = new JFrame();
            JPanel viewAllAccountsPanel = new JPanel();
            viewAllAccountsPanel.setLayout(new GridLayout(0, 1));

            if (passwords.getAllDetails().size() == 0) {
                JLabel accountDetails = new JLabel("No passwords stored!");
                viewAllAccountsPanel.add(accountDetails);
            } else {
                for (List<String> accounts : passwords.getAllDetails().values()) {
                    for (String account : accounts) {
                        JLabel accountDetails = new JLabel(account);
                        viewAllAccountsPanel.add(accountDetails);
                    }
                }
            }

            JButton homeBtn = new JButton(new MainMenu());
            viewAllAccountsPanel.add(homeBtn);

            pageSetup(viewAllAccountsFrame, viewAllAccountsPanel);
        }
    }

    private class VerifyPassword extends AbstractAction {
        VerifyPassword() {
            super("Verify Password Strength");
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            menuFrame.dispose();

            verifyPasswordFrame = new JFrame();
            JPanel verifyPasswordPanel = new JPanel();
            JLabel verifyLabel = new JLabel("Account to verify: ");
            JTextField verifyIn = new JTextField();
            JButton submitBtn = new JButton("Submit");
            JLabel verifyResults = new JLabel("Password strength is: ");

            verifyPasswordPanel.setLayout(new GridLayout(0, 1));


            verifyPasswordPanel.add(verifyLabel);
            verifyPasswordPanel.add(verifyIn);
            verifyPasswordPanel.add(verifyResults);
            verifyPasswordPanel.add(submitBtn);

            submitBtn.addActionListener(event -> {
                verifyResults.setText("Password strength is: "
                        + passwords.verifyDetailEntry(verifyIn.getText()).toString());
            });


            JButton homeBtn = new JButton(new MainMenu());
            verifyPasswordPanel.add(homeBtn);

            pageSetup(verifyPasswordFrame, verifyPasswordPanel);
        }
    }

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

    private void loadAccounts() {
        try {
            passwords = jsonReader.read();
            System.out.println("Loaded passwords from " + JSON_STORE);
        } catch (IOException e) {
            System.out.println("Unable to read from file: " + JSON_STORE);
        }
    }

    private void disposeFrame(JFrame frame) {
        frame.dispose();
    }

    // EFFECTS: structures the setup for button classes
    private void pageSetup(JFrame frame, JPanel panel) {
        frame.add(panel, BorderLayout.CENTER);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setSize(WIDTH, HEIGHT);
        frame.setVisible(true);
    }
}
