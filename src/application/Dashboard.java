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

    @FXML
    private void goConcerts(MouseEvent event) {
        try {
        	Main m = new Main();
            m.changeScene("View Events.fxml");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void goMovies(MouseEvent event) {
        try {
        	Main m = new Main();
            m.changeScene("View Movies.fxml");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void goComedies(MouseEvent event) {
        try {
        	Main m = new Main();
            m.changeScene("View Comedies.fxml");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void userLogout(ActionEvent event) {
        try {
        	Main m = new Main();
            m.changeScene("Login.fxml");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

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
