package application;

import java.util.*;
import java.io.*;
import java.io.IOException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
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

    static String activeUser = "";

    public void userLogin(ActionEvent event) throws IOException {
        checkLogin();
    }

    // When the 'Sign up here' Hyperlink is pressed, it transitions to the 'Sign Up.fxml' page
    public void goToReg(ActionEvent event) throws IOException {
        Main m = new Main();
        m.changeScene("Sign Up.fxml");
    }

    // Method 'checkLogin()' checks the login details of the user
    @SuppressWarnings("unused")
    private void checkLogin() throws IOException {
        Main mainApp = new Main();

        // IF the user is an organizer, the program transitions to the 'Organizer Home.fxml' page
        if (email.getText().toString().equals("eventbooking@gmail.com") && password.getText().toString().equals("123456")) {
            Main.setOrganizerMode(true);
            mainApp.changeScene("Organizer Home.fxml");
        }
        // (...)

        // ELSE IF any of the entry fields are empty, an error message is displayed
        else if (email.getText().isEmpty() || password.getText().isEmpty()) {
            if (email.getText().isEmpty() && password.getText().isEmpty()) {
                invalidEntry.setText("*Please enter email and password.*");
            } else if (email.getText().isEmpty()) {
                invalidEntry.setText("*Please enter email.*");
            } else if (password.getText().isEmpty()) {
                invalidEntry.setText("*Please enter password.*");
            }

        }
        // ELSE checks the user's details against the values in the 'Registration details.txt' file
        else {
            // Retrieves the user inputted details from the text fields and stores them in variables
            String inputEmail = email.getText();
            String inputPassword = password.getText();

            // Stores the file path in the String variable 'filepath'
            String userDataFile = "./Registration details.txt";

            // Creates FileReader and BufferedReader to read the file
            BufferedReader fileReader = new BufferedReader(new FileReader(userDataFile));
            // Creates LinkedLists to store email and passwords
            LinkedList<String> storedEmails = new LinkedList<String>();
            LinkedList<String> storedPasswords = new LinkedList<String>();
            String currentLine = fileReader.readLine(); // Reads the first line of the file
            while (currentLine != null) { // WHILE loop to iterate through the lines of the file
                String[] userRecord = currentLine.split(";"); // Assigns a semi-colon as the data delimiter
                // Adds data values to their respective lists
                String storedEmail = userRecord[2];
                String storedPassword = userRecord[3];
                storedEmails.add(storedEmail);
                storedPasswords.add(storedPassword);
                currentLine = fileReader.readLine(); // Reads the next line
            }
            fileReader.close(); // Closes BufferedReader

            // Iterates through LinkedLists to authenticate the email and password
            int userIndex = 0;
            for (int i = 0; i < storedEmails.size(); i++) {
                if (storedEmails.get(i).equals(inputEmail) && storedPasswords.get(i).equals(inputPassword)) {
                	userIndex = i;
                    activeUser = inputEmail;
                    mainApp.changeScene("View Events.fxml");
                } else {
                    invalidEntry.setText("*Invalid email or password*");
                }
            }

        }
    }

    static void setCurrentUser(String currentUser) {
        Login.activeUser = currentUser;
    }

    static String getCurrentUser() {
        return activeUser;
    }

}
