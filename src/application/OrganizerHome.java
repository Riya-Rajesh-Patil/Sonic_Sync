package application;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.DirectoryChooser;

public class OrganizerHome {

    @FXML
    private Button logoutButton, manageEventsButton, exportBookingsButton;

    public void exportBookings(ActionEvent event) throws IOException {

        DirectoryChooser folderChooser = new DirectoryChooser();
        folderChooser.setTitle("Select folder:");
        File defaultDirectory = new File(".");
        folderChooser.setInitialDirectory(defaultDirectory);
        File selectedDirectory = folderChooser.showDialog(null);

        if (selectedDirectory == null) {
            return;
        }

        PrintWriter pw = new PrintWriter(new FileOutputStream(
                new File(selectedDirectory.toPath() + "/bookings_exported.txt")));
        pw.close();

        pw = new PrintWriter(new FileOutputStream(
                new File(selectedDirectory.toPath() + "/bookings_exported.txt"), 
                true));

        FileReader fr = new FileReader("bookings.txt");
        BufferedReader br = new BufferedReader(fr);
        String line = br.readLine(); 
        while (line != null) { 
            pw.append(line + "\n"); 
            line = br.readLine(); 
        }

        pw.close(); 

        Alert alert = new Alert(AlertType.INFORMATION, "The bookings.txt file has been exported!",
                ButtonType.OK);
        alert.showAndWait();
        if (alert.getResult() == ButtonType.OK) {
            return;
        }
    }

    public void organizerLogout(ActionEvent event) throws IOException {
        Main m = new Main();
        Main.setOrganizerMode(false);
        m.changeScene("Login.fxml");
    }

    public void goToEventManagement(ActionEvent event) throws IOException {
        Main m = new Main();
        m.changeScene("Event Management.fxml");
    }
}
