package application;

import java.awt.Desktop;
import java.io.*;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;

import javax.imageio.ImageIO;

import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;

public class EventPage implements Initializable {

    String selectedEvent = "";
    File imgFile = null;
    Desktop desktop = Desktop.getDesktop();

    @FXML
    private Button backButton, bookButton, deleteEventButton;
    @FXML
    private ImageView selectedEventPoster;
    @FXML
    private Text title;
    @FXML
    private Text description;
    @FXML
    private Text startDate;
    @FXML
    private Text endDate;
    @FXML
    private Text time;
    @FXML
    private Text age;
    @FXML
    private Text rating;

    Main m1 = new Main();
    ViewEvents vf = new ViewEvents();
    String id = vf.getId();

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        if (Main.isOrganizer()) {
            bookButton.setText("SEE BOOKINGS");
        }
        selectedEvent = Main.getSelectedEventTitle();
        try {
            FileReader fr = new FileReader("./events.txt");
            BufferedReader br = new BufferedReader(fr);
            String line = br.readLine();
            while (line != null) {
                String[] data = line.split(";");
                if (data[0].equals(selectedEvent)) {
                    title.setText(data[0]);
                    description.setText(data[1]);
                    startDate.setText(data[3]);
                    time.setText(data[5]);
                }
                line = br.readLine();
            }

            String path = "./Images/eventImages/";
            imgFile = new File(path + selectedEvent + ".png");
            Image img = SwingFXUtils.toFXImage(ImageIO.read(imgFile), null);
            selectedEventPoster.setImage(img);
        } catch (IOException e) {
            e.printStackTrace();
        }

        if (!Main.isOrganizer()) {
            deleteEventButton.setVisible(false);
        }

        selectedEventPoster.setOnMouseClicked((event) -> {
            try {
                FileReader fr = new FileReader("events.txt");
                BufferedReader br = new BufferedReader(fr);
                String line = br.readLine();
                String trailer = "";
                while (line != null) {
                    String[] data = line.split(";");
                    if (data[0].equals(selectedEvent)) {
                        trailer = data[2];
                    }
                    line = br.readLine();
                }
                desktop.browse(new URI(trailer));
            } catch (IOException | URISyntaxException e) {
                e.printStackTrace();
            }
        });
    }

    @FXML
    public void deleteEvent(ActionEvent event) throws IOException {
        selectedEvent = Main.getSelectedEventTitle();

        Alert alert = new Alert(AlertType.CONFIRMATION, "Do you want to delete this event?", ButtonType.NO, ButtonType.YES);
        alert.showAndWait();

        if (alert.getResult() == ButtonType.YES) {

            for (BookingHistoryItem booking : Main.getBookingList()) {

                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
                if (booking.getEvent().equals(Main.getSelectedEventTitle()) && !booking.getStatus().equals("cancelled")
                        && LocalDate.parse(booking.getDate(), formatter).compareTo(LocalDate.now()) >= 0) {
                    Alert existingBookingAlert = new Alert(AlertType.WARNING, "You cannot delete an event with future bookings!", ButtonType.OK);
                    existingBookingAlert.showAndWait();
                    return;
                }
            }

            imgFile.delete();

            String tempFile = "temp.txt";
            File oldFile = new File("events.txt");
            File newFile = new File("temp.txt");

            String currentLine;
            String[] data;

            FileWriter fw = new FileWriter(tempFile, true);
            BufferedWriter bw = new BufferedWriter(fw);
            PrintWriter pw = new PrintWriter(bw);

            FileReader fr = new FileReader("events.txt");
            BufferedReader br = new BufferedReader(fr);

            while ((currentLine = br.readLine()) != null) {
                data = currentLine.split(";");
                if (!(data[0].equals(selectedEvent))) {
                    pw.println(currentLine);
                }
            }

            pw.flush();
            pw.close();
            fr.close();
            br.close();

            oldFile.delete();
            File dump = new File("events.txt");
            newFile.renameTo(dump);

            Main.readEventData();

            goBack(event);
        }
    }

    public void goBack(ActionEvent event) throws IOException {
        Main m = new Main();
        m.changeScene("View Events.fxml");
    }

    public void goToBookingScene(ActionEvent event) throws IOException {
        if (Main.isOrganizer()) {
            Main m = new Main();
            m.changeScene("Booking Management.fxml");
        } else {
            Main m = new Main();
            m.changeScene("Event Booking.fxml");
        }
    }
}
