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

    // Method 'exportBookings()' creates a saveable 'bookings_exported.txt' file
    public void exportBookings(ActionEvent event) throws IOException {

        // Organizer chooses a folder in which they want to save the newly exported file
        DirectoryChooser folderChooser = new DirectoryChooser();
        folderChooser.setTitle("Select folder:");
        File defaultDirectory = new File(".");
        folderChooser.setInitialDirectory(defaultDirectory);
        File selectedDirectory = folderChooser.showDialog(null);

        // IF the organizer clicks on cancel
        if (selectedDirectory == null) {
            return;
        }

        // Clearing the export file, in case it exists from before
        PrintWriter pw = new PrintWriter(new FileOutputStream(
                new File(selectedDirectory.toPath() + "/bookings_exported.txt")));
        pw.close();

        // Creating the PrintWriter using the append option now
        pw = new PrintWriter(new FileOutputStream(
                new File(selectedDirectory.toPath() + "/bookings_exported.txt"), 
                true));

        // Creates FileReader and BufferedReader to read the file
        FileReader fr = new FileReader("bookings.txt");
        BufferedReader br = new BufferedReader(fr);
        String line = br.readLine(); // Reads the first line of the file
        while (line != null) { // WHILE loop to iterate through the lines of the file
            pw.append(line + "\n"); // Appends (adds) line to the 'bookings_exported.txt' file
            line = br.readLine(); // Reads the next line
        }

        pw.close(); // Closes PrintWriter

        // Creates message of the completed task
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
