package application;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.input.MouseEvent;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

public class Dashboard {

    @FXML
    private Button logoutBtn;

    @FXML
    private Button homeBtn;

    @FXML
    private ScrollPane scrollPane;

    @FXML
    private ImageView goConcerts;

    @FXML
    private ImageView goMovies;

    @FXML
    private ImageView goComedies;

    // Method to handle clicking on the "Go to Concerts" image
    @FXML
    private void goConcerts(MouseEvent event) {
        try {
        	Main m = new Main();
            m.changeScene("View Events.fxml");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Method to handle clicking on the "Go to Movies" image
    @FXML
    private void goMovies(MouseEvent event) {
        try {
        	Main m = new Main();
            m.changeScene("View Movies.fxml");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Method to handle clicking on the "Go to Stand-Up Comedies" image
    @FXML
    private void goComedies(MouseEvent event) {
        try {
        	Main m = new Main();
            m.changeScene("View Comedies.fxml");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Method to handle the "Log Out" button
    @FXML
    private void userLogout(ActionEvent event) {
        try {
            // Clear user session and navigate to login page
        	Main m = new Main();
            m.changeScene("Login.fxml");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Method to handle the "Go Home" button
    @FXML
    private void goHome(ActionEvent event) {
        try {
        	Main m = new Main();
            m.changeScene("Dashboard.fxml");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
