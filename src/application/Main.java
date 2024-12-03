package application;

import java.io.*;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.File;
import java.io.FileReader;
import java.util.HashSet;
import java.util.LinkedList;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

@SuppressWarnings("unused")
public class Main extends Application {

    static Parent root;
    static Stage primaryStage;

    private static Stage stg;
    static Boolean organizerMode = false;
    static String selectedEventTitle = "";

    // Creates LinkedLists of type String for eventTitles, startDates, endDates, time1, time2, and time3
    public static LinkedList<String> eventTitles = new LinkedList<String>();
    public static LinkedList<String> eventStartDates = new LinkedList<String>();
    public static LinkedList<String> eventEndDates = new LinkedList<String>();
    public static LinkedList<String> eventTime1 = new LinkedList<String>();
    public static LinkedList<String> eventTime2 = new LinkedList<String>();
    public static LinkedList<String> eventTime3 = new LinkedList<String>();

    static HashSet<BookingHistoryItem> bookings = new HashSet<BookingHistoryItem>();

    @Override
    public void start(Stage primaryStage) {
        this.primaryStage = primaryStage;

        try {
            // Load the loader screen
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/application/Loader.fxml"));
            AnchorPane root = loader.load();
            Scene scene = new Scene(root);

            // Set and display the loader screen
            primaryStage.setScene(scene);
            primaryStage.setTitle("EventHive - Loading...");
            primaryStage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
//    @Override
//    public void start(Stage primaryStage) throws IOException {
//        stg = primaryStage;
//        primaryStage.setResizable(false);
//        Parent root = FXMLLoader.load(getClass().getResource("Login.fxml"));
//        Scene scene = new Scene(root);
//        primaryStage.setTitle("Event Booking System");
//        primaryStage.setScene(scene);
//        primaryStage.sizeToScene();
//        primaryStage.show();
//    }
    public static void switchToLogin() {
        try {
            FXMLLoader loader = new FXMLLoader(Main.class.getResource("/application/Login.fxml"));
            BorderPane root = loader.load(); // Updated to match the root layout type in FXML
            Scene scene = new Scene(root);
            primaryStage.setScene(scene);
            primaryStage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    // Method 'changeScene()' allows any FXML window to be opened, integrated within the application window
    public void changeScene(String fxml) throws IOException {
        Parent pane = FXMLLoader.load(getClass().getResource(fxml)); // Defines the variable 'pane' of the FXML window wanted
        stg.getScene().setRoot(pane); // Sets the stage as the new pane
        stg.sizeToScene(); // Resizes the stage
    }

    public static void main(String[] args) {
        launch(args);
    }

    static HashSet<BookingHistoryItem> getBookingList() {
        return bookings;
    }

    static void resetBookingList() {
        bookings.clear();
    }

    static boolean isOrganizer() {
        return organizerMode;
    }

    static void setOrganizerMode(boolean organizerMode) {
        Main.organizerMode = organizerMode;
    }

    // Method readEventData() reads 'events.txt' file and adds respective data to each LinkedList
    public static void readEventData() throws IOException {
        // Creates FileReader and BufferedReader to read the file
        FileReader fr = new FileReader("events.txt");
        BufferedReader br = new BufferedReader(fr);
        String line = br.readLine(); // Reads the first line of the file
        while (line != null) { // WHILE loop to iterate through the lines of the file
            String[] data = line.split(";"); // Assigns a semi-colon as the data delimiter
            // Adds data values to their respective lists
            eventTitles.add(data[0]);
            eventStartDates.add(data[3]);
            eventEndDates.add(data[4]);
            eventTime1.add(data[5]);
            eventTime2.add(data[6]);
            eventTime3.add(data[7]);
            line = br.readLine(); // Reads the next line
        }
    }

    static void setSelectedEventTitle(String selectedEventTitle) {
        Main.selectedEventTitle = selectedEventTitle;
    }

    static String getSelectedEventTitle() {
        return selectedEventTitle;
    }

    static Parent getRoot() {
        return root;
    }

    static void setRoot(Parent root) {
        Main.root = root;
    }

    static Stage getStage() {
        return primaryStage;
    }

    static void setStage(Stage stage) {
        Main.primaryStage = stage;
    }
}
