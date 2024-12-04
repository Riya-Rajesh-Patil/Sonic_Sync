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

    // To store the currently logged-in user's email
    private static String currentUser = "";

    /**
     * Handles the login button click
     * @throws IOException 
     */
    public void userLogin(ActionEvent event) throws IOException {
        //switchToScene("View Events.fxml");  //debugging purpose only, remove it 
		System.out.println("Attempting login...");
        checkLogin();
    }

    /**
     * Handles the registration hyperlink click, switches to the Sign-Up page
     */
    public void goToReg(ActionEvent event) {
        try {
            System.out.println("Navigating to the Sign-Up page...");
            System.out.println("Loading FXML: /application/SignUp Page.fxml");
            System.out.println("Resolved URL: " + getClass().getResource("/application/SignUp Page.fxml"));

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/application/SignUp Page.fxml"));
            Parent pane = loader.load();

            // Use the primary stage from Main
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

    /**
     * Checks the login details and transitions to the appropriate page
     */
    private void checkLogin() throws IOException {
        if (email.getText().isEmpty() || password.getText().isEmpty()) {
            handleEmptyFields();
            return;
        }

        String loginEmail = email.getText();
        String loginPswd = password.getText();

        // Check for predefined organizer login
        if ("pcrcinemas@gmail.com".equals(loginEmail) && "123456".equals(loginPswd)) {
            Main.setOrganizerMode(true);
            switchToScene("Organizer Home Page.fxml");
            return;
        }

        // Validate against the Registration details file
        if (validateCredentials(loginEmail, loginPswd)) {
            currentUser = loginEmail;
            switchToScene("View Events.fxml");
        } else {
            invalidEntry.setText("*Invalid email or password*");
        }
    }

    /**
     * Handles empty email or password fields
     */
    private void handleEmptyFields() {
        if (email.getText().isEmpty() && password.getText().isEmpty()) {
            invalidEntry.setText("*Please enter email and password.*");
        } else if (email.getText().isEmpty()) {
            invalidEntry.setText("*Please enter email.*");
        } else if (password.getText().isEmpty()) {
            invalidEntry.setText("*Please enter password.*");
        }
    }

    /**
     * Validates the email and password against the registration details file
     * @return true if the credentials are valid, false otherwise
     */
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

    /**
     * Switches to a new scene
     */
    private void switchToScene(String fxmlFile) {
        try {
            System.out.println("Switching to: " + fxmlFile);
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/application/" + fxmlFile));
            Parent pane = loader.load();

            // Use the primary stage from Main
            Main.getStage().getScene().setRoot(pane);
            Main.getStage().sizeToScene();
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Failed to load: " + fxmlFile);
        }
    }

    // Getter and Setter for the current user
    public static String getCurrentUser() {
        return currentUser;
    }

    public static void setCurrentUser(String currentUser) {
        Login.currentUser = currentUser;
    }
}
