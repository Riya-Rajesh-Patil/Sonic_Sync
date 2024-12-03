package application;

import java.util.*;
import java.io.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.regex.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class SignUp {

    @FXML
    private TextField firstNameField;
    @FXML
    private TextField lastNameField;
    @FXML
    private TextField regEmailField;
    @FXML
    private PasswordField regPasswordField;
    @FXML
    private PasswordField confirmPasswordField;
    @FXML
    private Button signupButton;
    @FXML
    private Label errorMessage;
    @FXML
    private Hyperlink loginLink;

    public void goToLogin(ActionEvent event) throws IOException {
        Main m = new Main();
        m.changeScene("Login.fxml");
    }

    public void userRegister(ActionEvent event) throws IOException {
        validateRegistration();
    }

    public static boolean isValidFirstName(String firstName) {
        String firstNameRegex = "[A-Z][a-z]*";
        if (firstName == null) {
            return false;
        }
        return firstName.matches(firstNameRegex);
    }

    public static boolean isValidLastName(String lastName) {
        String lastNameRegex = "[A-Z][a-z]*";
        if (lastName == null) {
            return false;
        }
        return lastName.matches(lastNameRegex);
    }

    public static boolean isValidEmail(String email) {
        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\." +
                            "[a-zA-Z0-9_+&*-]+)*@" +
                            "(?:[a-zA-Z0-9-]+\\.)+[a-z" +
                            "A-Z]{2,7}$";
        Pattern pat = Pattern.compile(emailRegex);
        if (email == null) {
            return false;
        }
        return pat.matcher(email).matches();
    }

    public static boolean isValidPassword(String password) {
        String passwordRegex = "^(?=.*[0-9])" + 
                               "(?=.*[a-z])(?=.*[A-Z])" + 
                               "(?=.*[@#$%^&+=])" + 
                               "(?=\\S+$).{8,20}$";

        Pattern p = Pattern.compile(passwordRegex);
        if (password == null) {
            return false;
        }
        return p.matcher(password).matches();
    }

    private void validateRegistration() throws IOException {
        Main m = new Main();
        String email = regEmailField.getText();
        String password = regPasswordField.getText();
        String confirmPassword = confirmPasswordField.getText();
        String firstName = firstNameField.getText();
        String lastName = lastNameField.getText();
        if (isValidFirstName(firstName) && 
            isValidLastName(lastName) && 
            isValidEmail(email) && 
            isValidPassword(password) && 
            confirmPassword.equals(password)) {
            String filepath = "Registration details.txt";
            FileWriter fw = new FileWriter(filepath, true);
            fw.write(firstName + ";" + lastName + ";" + email + ";" + password + "\n");
            fw.close();
            m.changeScene("Login.fxml");
        } else if (!isValidFirstName(firstName)) {
            errorMessage.setText("*Invalid first name.*");
        } else if (!isValidLastName(lastName)) {
            errorMessage.setText("*Invalid last name.*");
        } else if (!isValidEmail(email)) {
            errorMessage.setText("*Invalid email.*");
        } else if (!isValidPassword(password)) {
            errorMessage.setText("*Invalid password.*");
        } else if (!confirmPassword.equals(password)) {
            errorMessage.setText("*Make sure password is the same.*");
        } else {
            errorMessage.setText("*Invalid Entry.*");
        }
    }
}
