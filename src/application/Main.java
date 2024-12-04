package application;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashSet;
import java.util.LinkedList;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    private static Stage primaryStage;
    private static boolean organizerMode = false;
    private static String selectedEventTitle = "";

    public static final LinkedList<String> eventTitles = new LinkedList<>();
    public static final LinkedList<String> eventStartDates = new LinkedList<>();
    public static final LinkedList<String> eventEndDates = new LinkedList<>();
    public static final LinkedList<String> eventTime1 = new LinkedList<>();
    public static final LinkedList<String> eventTime2 = new LinkedList<>();
    public static final LinkedList<String> eventTime3 = new LinkedList<>();

    private static final HashSet<BookingHistoryItem> bookings = new HashSet<>();

    @Override
    public void start(Stage stage) {
        try {
            primaryStage = stage;

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/application/Loader.fxml"));
            Parent root = loader.load();

            Scene scene = new Scene(root);
            primaryStage.setScene(scene);
            primaryStage.setTitle("EventHive - Loading...");
            primaryStage.show();

        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Failed to load the loader screen.");
        }
    }

    public static void switchToLogin() {
        try {
            System.out.println("Switching to Login.fxml...");
            FXMLLoader loader = new FXMLLoader(Main.class.getResource("/application/Login.fxml"));
            Parent root = loader.load();

            Scene scene = new Scene(root);
            primaryStage.setScene(scene);
            primaryStage.setTitle("EventHive - Login");
            primaryStage.show();
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Failed to switch to the Login screen.");
        }
    }

    public void changeScene(String fxml) {
        try {
            System.out.println("Changing scene to: " + fxml);
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/application/" + fxml));
            Parent pane = loader.load();

            primaryStage.getScene().setRoot(pane);
            primaryStage.sizeToScene();
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Failed to change scene: " + fxml);
        }
    }

    public static void readEventData() throws IOException {
        try (BufferedReader br = new BufferedReader(new FileReader("events.txt"))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] data = line.split(";");
                eventTitles.add(data[0]);
                eventStartDates.add(data[3]);
                eventEndDates.add(data[4]);
                eventTime1.add(data[5]);
                eventTime2.add(data[6]);
                eventTime3.add(data[7]);
            }
            System.out.println("Event data loaded successfully.");
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Error reading event data.");
        }
    }

    public static void main(String[] args) {
        launch(args);
    }

    public static boolean isOrganizer() {
        return organizerMode;
    }

    public static void setOrganizerMode(boolean mode) {
        organizerMode = mode;
    }

    public static String getSelectedEventTitle() {
        return selectedEventTitle;
    }

    public static void setSelectedEventTitle(String title) {
        selectedEventTitle = title;
    }

    public static HashSet<BookingHistoryItem> getBookingList() {
        return bookings;
    }

    public static void resetBookingList() {
        bookings.clear();
    }

	public static Stage getStage() {
		return primaryStage;
	}
	public static Stage setStage() {
		return Main.primaryStage = setStage();
	}
}
