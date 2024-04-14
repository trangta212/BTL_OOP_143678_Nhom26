package application.controller;

import application.data.Account;
import application.data.Accounts;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.control.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.File;
import java.io.IOException;
import java.util.Map;
public class LoginController {
    @FXML
    private Button btnCreateAcc;

    @FXML
    private Button btnLogin;

    @FXML
    private CheckBox checkBoxAgreeTerms;

    @FXML
    private TextField tfEmailSignUp;

    @FXML
    private TextField tfEmailLogin;

    @FXML
    private TextField tfPasswordCfSignUp;

    @FXML
    private TextField tfPasswordLogin;

    @FXML
    private TextField tfPasswordSignUp;

    @FXML
    private TextField tfUsernameSignUp;

    @FXML
    private Tab tabLogin;

    @FXML
    private Tab tabSignUp;

    @FXML
    private Label lblErrorMessageLogin;

    @FXML
    private Label lblErrorMessageSignUp;

    private void clearAllFieldsLogin() {
        tfEmailLogin.clear();
        tfPasswordLogin.clear();
        tfEmailLogin.setStyle("-fx-border-color: black");
        tfPasswordLogin.setStyle("-fx-border-color: black");
        lblErrorMessageLogin.setVisible(false);
    }

    private void clearAllFieldsSignUp() {
        tfUsernameSignUp.clear();
        tfEmailSignUp.clear();
        tfPasswordSignUp.clear();
        tfPasswordCfSignUp.clear();
        checkBoxAgreeTerms.setSelected(false);
        lblErrorMessageSignUp.setVisible(false);
        tfUsernameSignUp.setStyle("-fx-border-color: black");
        tfEmailSignUp.setStyle("-fx-border-color: black");
        tfPasswordSignUp.setStyle("-fx-border-color: black");
        tfPasswordCfSignUp.setStyle("-fx-border-color: black");
    }

    private void highlightRedEmptyFieldsSignUp() {
        if (tfUsernameSignUp.getText().isEmpty()) {
            tfUsernameSignUp.setStyle("-fx-border-color: red");
        } else {
            tfUsernameSignUp.setStyle("-fx-border-color: black");
        }
        if (tfEmailSignUp.getText().isEmpty()) {
            tfEmailSignUp.setStyle("-fx-border-color: red");
        } else {
            tfEmailSignUp.setStyle("-fx-border-color: black");
        }
        if (tfPasswordSignUp.getText().isEmpty()) {
            tfPasswordSignUp.setStyle("-fx-border-color: red");
        } else {
            tfPasswordSignUp.setStyle("-fx-border-color: black");
        }
        if (tfPasswordCfSignUp.getText().isEmpty()) {
            tfPasswordCfSignUp.setStyle("-fx-border-color: red");
        } else {
            tfPasswordCfSignUp.setStyle("-fx-border-color: black");
        }
    }

//    @FXML
//    void changeTab(ActionEvent event) {
////        if (tabLogin.isSelected()) {
////            clearAllFieldsLogin();
////        } else {
////            clearAllFieldsSignUp();
////        }
//    }

    @FXML
    void createAccount(ActionEvent event) throws IOException {
        if (tfUsernameSignUp.getText().isEmpty() || tfEmailSignUp.getText().isEmpty()
                || tfPasswordSignUp.getText().isEmpty() || tfPasswordCfSignUp.getText().isEmpty()) {
            lblErrorMessageSignUp.setText("Please fill in all fields.");
            lblErrorMessageSignUp.setVisible(true);
            highlightRedEmptyFieldsSignUp();
            return;
        }
        if (!tfPasswordSignUp.getText().equals(tfPasswordCfSignUp.getText())) {
            lblErrorMessageSignUp.setText("Passwords do not match. Please try again.");
            lblErrorMessageSignUp.setVisible(true);
            highlightRedEmptyFieldsSignUp();
            return;
        }
        if (!checkBoxAgreeTerms.isSelected()) {
            lblErrorMessageSignUp.setText("Please agree to the terms and conditions.");
            lblErrorMessageSignUp.setVisible(true);
            highlightRedEmptyFieldsSignUp();
            return;
        }
        Accounts accounts = readAccountsFromJson();
        for (Account account : accounts.getAccounts()) {
            if (account.getEmail().equals(tfEmailSignUp.getText())) {
                lblErrorMessageSignUp.setText("Email is already registered. Please sign in or try another email.");
                lblErrorMessageSignUp.setVisible(true);
                highlightRedEmptyFieldsSignUp();
                return;
            }
        }
        Account newAccount = new Account(tfUsernameSignUp.getText(), tfEmailSignUp.getText(), tfPasswordSignUp.getText());
        accounts.getAccounts().add(newAccount);
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.writeValue(new File("src/database/users.json"), accounts);
        System.out.println("Account created successfully");
        clearAllFieldsSignUp();
    }

    @FXML
    void login(ActionEvent event) throws IOException {
        Accounts accounts = readAccountsFromJson();
        String errorMessage = "";
        if (tfEmailLogin.getText().isEmpty() || tfPasswordLogin.getText().isEmpty()) {
            errorMessage = "Please fill in all fields.";
            lblErrorMessageLogin.setText(errorMessage);
            lblErrorMessageLogin.setVisible(true);
            tfEmailLogin.setStyle("-fx-border-color: red");
            tfPasswordLogin.setStyle("-fx-border-color: red");
            return;
        }
        for (Account account : accounts.getAccounts()) {
            if (account.getEmail().equals(tfEmailLogin.getText()) && account.getPassword().equals(tfPasswordLogin.getText())) {
                System.out.println("Login successful");
                return;
            }
            if (account.getEmail().equals(tfEmailLogin.getText()) && !account.getPassword().equals(tfPasswordLogin.getText())) {
                errorMessage = "Password is incorrect. Please try again.";
                break;
            }
        }
        if (errorMessage.isEmpty()) {
            errorMessage = "Email is not registered. Please sign up.";
        }
        tfPasswordLogin.clear();
        lblErrorMessageLogin.setText(errorMessage);
        lblErrorMessageLogin.setVisible(true);
        tfEmailLogin.setStyle("-fx-border-color: red");
        tfPasswordLogin.setStyle("-fx-border-color: red");
        System.out.println("Login failed");
    }

    public Accounts readAccountsFromJson() {
        ObjectMapper objectMapper = new ObjectMapper();
        final String JSON_FILE_PATH = "src/database/users.json";
        try {
            //            for (Account account : accounts.getAccounts()) {
////                System.out.println("Username: " + account.getUsername());
////                System.out.println("Email: " + account.getEmail());
////                System.out.println("Password: " + account.getPassword());
////                System.out.println("-------------------");
//            }
            return objectMapper.readValue(new File(JSON_FILE_PATH), Accounts.class);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}
