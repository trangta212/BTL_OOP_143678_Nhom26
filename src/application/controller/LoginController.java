package application.controller;

import application.data.Account;
import application.data.Accounts;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;

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
    private TextField tfEmail;

    @FXML
    private TextField tfEmailLogin;

    @FXML
    private TextField tfPasswordCfSignUp;

    @FXML
    private TextField tfPasswordLogin;

    @FXML
    private TextField tfPasswordSignUp;

    @FXML
    private TextField tfUsername;

    @FXML
    void createAccount(ActionEvent event) throws IOException {
    }

    @FXML
    void login(ActionEvent event) throws IOException {
        Accounts accounts = readAccountsFromJson();
        for (Account account : accounts.getAccounts()) {
            if (account.getEmail().equals(tfEmailLogin.getText()) && account.getPassword().equals(tfPasswordLogin.getText())) {
                System.out.println("Login successful");
                return;
            }
        }
    }

    public Accounts readAccountsFromJson() {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            Accounts accounts = objectMapper.readValue(new File("src/database/users.json"), Accounts.class);
            for (Account account : accounts.getAccounts()) {
                System.out.println("Username: " + account.getUsername());
                System.out.println("Email: " + account.getEmail());
                System.out.println("Password: " + account.getPassword());
                System.out.println("-------------------");
            }
            return accounts;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}
