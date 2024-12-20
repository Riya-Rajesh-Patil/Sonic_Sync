package application;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.VPos;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;

public class ViewComedies implements Initializable {

    ArrayList<File> fileList = new ArrayList<File>();
    HBox hb = new HBox();

    @FXML
    private Button backButton, homeButton;
    @FXML
    private ScrollPane scrollPane;
    @FXML
    private GridPane grid;
    @FXML
    private ImageView pic;
    @FXML
    private Image image;
    @FXML
    private String id;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        try {

            String path = "./Images/comedyImages";
            System.out.println(path);
            File folder = new File(path);
            for (File file : folder.listFiles()) {
                if (!file.toString().contains("DS_Store"))
                    fileList.add(file);
            }

            scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);

            grid.setPadding(new Insets(7, 7, 7, 7));
            grid.setHgap(10);
            grid.setVgap(10);

            int rows = (fileList.size() / 4) + 1;
            int columns = 4;
            int imageIndex = 0;

            for (int i = 0; i < rows; i++) {
                for (int j = 0; j < columns; j++) {
                    if (imageIndex < fileList.size()) {
                        addImage(imageIndex, j, i);
                        imageIndex++;
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void addImage(int index, int colIndex, int rowIndex) {

        String idToCut = fileList.get(index).getName();
        String id = idToCut.substring(0, (idToCut.length() - 4));

        image = new Image(fileList.get(index).toURI().toString());
        pic = new ImageView();
        pic.setFitWidth(160);
        pic.setFitHeight(220);
        pic.setImage(image);
        pic.setId(id);
        hb.getChildren().add(pic);
        GridPane.setConstraints(pic, colIndex, rowIndex, 1, 1, HPos.CENTER, VPos.CENTER);
        grid.getChildren().addAll(pic);

        pic.setOnMouseClicked(e -> {
            Main.setSelectedEventTitle(id);
			Main m = new Main();
			m.changeScene("Event Page.fxml");
        });
    }

    @FXML
    public String getId() {
        return id;
    }

    @FXML
    public void userLogout(ActionEvent event) throws IOException {
        Main m = new Main();
        Main.setOrganizerMode(false);
        m.changeScene("Login.fxml");
    }
    
    public void goBack(ActionEvent event) throws IOException {
        Main m = new Main();
        m.changeScene("Dashboard.fxml");
    }

    @FXML
    public void goHome(ActionEvent event) throws IOException {
        Main m = new Main();
        if (Main.isOrganizer()) {
            m.changeScene("Organizer Home.fxml");
        } else {
            m.changeScene("View Events.fxml");
        }
    }
}
