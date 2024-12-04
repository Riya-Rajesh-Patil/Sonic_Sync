package application;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.util.LinkedList;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

public class EventBookingManagement implements Initializable {

    @FXML
    private Button homeButton, backButton, deleteButton;

    @FXML
    private TableView<BookingHistoryItem> table;
    
    @FXML
    private TableColumn<BookingHistoryItem, String> status, firstName, lastName, event, date, time, seats, vip, idNumber;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        status.setCellValueFactory(new PropertyValueFactory<BookingHistoryItem, String>("status"));
        firstName.setCellValueFactory(new PropertyValueFactory<BookingHistoryItem, String>("firstName"));
        lastName.setCellValueFactory(new PropertyValueFactory<BookingHistoryItem, String>("lastName"));
        event.setCellValueFactory(new PropertyValueFactory<BookingHistoryItem, String>("event"));
        date.setCellValueFactory(new PropertyValueFactory<BookingHistoryItem, String>("date"));
        time.setCellValueFactory(new PropertyValueFactory<BookingHistoryItem, String>("time"));
        seats.setCellValueFactory(new PropertyValueFactory<BookingHistoryItem, String>("seats"));
        vip.setCellValueFactory(new PropertyValueFactory<BookingHistoryItem, String>("vip"));
        idNumber.setCellValueFactory(new PropertyValueFactory<BookingHistoryItem, String>("idNumber"));

        ObservableList<BookingHistoryItem> list = FXCollections.observableArrayList();

        try {
            FileReader fr = new FileReader("bookings.txt");
            BufferedReader br = new BufferedReader(fr);
            String line = br.readLine(); 
            while (line != null) { 
                String[] data = line.split(";"); 
                list.add(new BookingHistoryItem(data[0], data[1], data[2], data[3], data[4],
                        data[5], data[6], data[7], data[8]));
                line = br.readLine(); 
            }
            fr.close(); 
        } catch (IOException e) {
            e.printStackTrace();
        }

        table.setItems(list); 
    }

    public void goBack(ActionEvent event) throws IOException {
        Main m = new Main();
        m.changeScene("Event Page.fxml");
    }

    public void goHome(ActionEvent event) throws IOException {
        Main m = new Main();
        m.changeScene("Organizer Home.fxml");
    }

    public void deleteBooking(ActionEvent event) throws IOException {
        Alert alert = new Alert(AlertType.CONFIRMATION, "Do you wish to delete this booking from view?", ButtonType.YES, ButtonType.NO);
        alert.showAndWait();
        if (alert.getResult() == ButtonType.YES) {
            table.getItems().removeAll(table.getSelectionModel().getSelectedItem());
            alert.close();
        }
    }
}
