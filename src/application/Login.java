package application;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.LinkedList;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class Login {

    @FXML
    private Button loginBtn;

    @FXML
    private Label invalidEntry;

    @FXML
    private TextField email;

    @FXML
    private PasswordField password;

    @FXML
    private Hyperlink regLink;

    private static String currentUser = "";

    public void userLogin(ActionEvent event) throws IOException { 
		System.out.println("Attempting login...");
        checkLogin();
    }

    public void goToReg(ActionEvent event) {
        try {
            System.out.println("Navigating to the Sign-Up page...");
            System.out.println("Loading FXML: /application/SignUp Page.fxml");
            System.out.println("Resolved URL: " + getClass().getResource("/application/SignUp Page.fxml"));

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/application/SignUp Page.fxml"));
            Parent pane = loader.load();

            if (Main.getStage() == null) {
                System.out.println("Main's primary stage is null.");
                return;
            }

            Main.getStage().getScene().setRoot(pane);
            Main.getStage().sizeToScene();
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Failed to load the Sign-Up page.");
        }
    }

    private void checkLogin() throws IOException {
        if (email.getText().isEmpty() || password.getText().isEmpty()) {
            handleEmptyFields();
            return;
        }

        String loginEmail = email.getText();
        String loginPswd = password.getText();

        if ("admin".equals(loginEmail) && "admin".equals(loginPswd)) {
            Main.setOrganizerMode(true);
            switchToScene("Organizer Home Page.fxml");
            return;
        }

        if (validateCredentials(loginEmail, loginPswd)) {
            currentUser = loginEmail;
            switchToScene("Dashboard.fxml");
        } else {
            invalidEntry.setText("*Invalid email or password*");
        }
    }

    private void handleEmptyFields() {
        if (email.getText().isEmpty() && password.getText().isEmpty()) {
            invalidEntry.setText("*Please enter email and password.*");
        } else if (email.getText().isEmpty()) {
            invalidEntry.setText("*Please enter email.*");
        } else if (password.getText().isEmpty()) {
            invalidEntry.setText("*Please enter password.*");
        }
    }

    private boolean validateCredentials(String loginEmail, String loginPswd) {
        String filepath = "./Registration details.txt";
        try (BufferedReader br = new BufferedReader(new FileReader(filepath))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] data = line.split(";");
                if (data.length > 3 && loginEmail.equals(data[2]) && loginPswd.equals(data[3])) {
                    return true;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Error reading the registration details file.");
        }
        return false;
    }

    private void switchToScene(String fxmlFile) {
        try {
            System.out.println("Switching to: " + fxmlFile);
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/application/" + fxmlFile));
            Parent pane = loader.load();
            Main.getStage().getScene().setRoot(pane);
            Main.getStage().sizeToScene();
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Failed to load: " + fxmlFile);
        }
    }

    public static String getCurrentUser() {
        return currentUser;
    }

    public static void setCurrentUser(String currentUser) {
        Login.currentUser = currentUser;
    }
}
