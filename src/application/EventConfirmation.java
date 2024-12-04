package application;

import java.awt.Label;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javax.imageio.ImageIO;

import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;

public class EventConfirmation implements Initializable {

    @FXML
    Text firstname, surname, email, datetime, screen, seats;

    @FXML
    Text selectedEventTitle, adult, child, senior, isVip, total;

    @FXML
    ImageView selectedEventPoster;

    @FXML
    Button homeButton, emailButton;

    String selectedEvent = "", date = "", time = "";
    File imgFile = null;

    public static String name = "", finalDate = "", finalTime = "", vipConf = "";
    public static int min = 0;
    public static int max = 9999;
    public static int id = (int) Math.floor(Math.random() * (max - min + 1) + min);
    public static String bookingId = "EVENT" + Integer.toString(id);

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        selectedEventTitle.setText(Main.getSelectedEventTitle());
        email.setText(Login.getCurrentUser());
        seats.setText(EventSeatBooking.userSeats);

        date = EventBooking.date;
        time = EventBooking.time;
        datetime.setText(date + " @ " + time);
        finalDate = date;
        finalTime = time;

        adult.setText(EventBooking.adultTickets + "");
        child.setText(EventBooking.childTickets + "");
        senior.setText(EventBooking.seniorTickets + "");

        if (EventBooking.isVip) {
            isVip.setText("Yes");
            vipConf = "Yes";
        } else {
            isVip.setText("No");
            vipConf = "No";
        }
        total.setText("$" + String.format("%.2f", EventBooking.total));
        String email = Login.getCurrentUser();
        selectedEvent = Main.getSelectedEventTitle();

        try {
            FileReader fr = new FileReader("Registration details.txt");
            BufferedReader br = new BufferedReader(fr);
            String line = br.readLine();
            while (line != null) {
                String[] data = line.split(";");
                if (data[2].equals(email)) {
                    firstname.setText(data[0]);
                    surname.setText(data[1]);
                    name = data[0] + " " + data[1];
                }
                line = br.readLine();
            }
            fr.close();
            String path = "./Images/eventImages/";
            imgFile = new File(path + selectedEvent + ".png");
            Image img = SwingFXUtils.toFXImage(ImageIO.read(imgFile), null);
            selectedEventPoster.setImage(img);
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            FileWriter fw = new FileWriter("bookings.txt", true);
            fw.write("booked" + ";" + firstname.getText() + ";" + surname.getText() + ";" + selectedEventTitle.getText() +
                     ";" + date + ";" + time + ";" + seats.getText() + ";" + isVip.getText() + ";" + bookingId + "\n");
            fw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void emailConfirmation(ActionEvent event) throws IOException {
      
        Alert alert = new Alert(AlertType.CONFIRMATION, "Would you like a confirmation to be emailed to you?",
                ButtonType.YES, ButtonType.NO);
        alert.showAndWait();

        if (alert.getResult() == ButtonType.YES) {
            EventSendEmail.sendEmail(Login.getCurrentUser(), "confirmation");
            alert.close();
        }

        else {
            alert.close();
            return;
        }
    }

    public void goHome(ActionEvent event) throws IOException {
        Main m = new Main();
        m.changeScene("View Events.fxml");
    }
}
