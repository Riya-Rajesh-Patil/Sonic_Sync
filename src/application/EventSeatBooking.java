package application;

import java.io.IOException;
import javafx.animation.RotateTransition;
import javafx.animation.ScaleTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.text.Text;
import javafx.util.Duration;

public class EventSeatBooking {

    @FXML
    private Button backButton, confirmationButton;
    @FXML
    private Button A1, A2, A3, A4, A5, A6, A7, A8,A9,A10,A11,A12, B1, B2, B3, B4, B5, B6, B7, B8,B9,B10,B11,B12, C1, C2, C3, C4, C5, C6, C7, C8,C9,C10,C11,C12;

    private Button[] seats = new Button[36];
    public static boolean[] eventBookings = new boolean[36];
    public static boolean[] booked;

    private int maxSeats = (EventBooking.adultTickets + EventBooking.childTickets + EventBooking.seniorTickets);
    private int numberOfSeats = 0;
    public static String userSeats = "";

    private boolean rotatedPane = false;
    public static boolean seatsSelected = false;

    @FXML
    private Text eventName, totalTickets, totalPrice;

    @FXML
    private Label selectedSeats;

    @FXML
    void initialize() throws IOException {
        selectedSeats.setText("");
        booked = new boolean[36];
        initialiseArray();
        setUpSeats();
        eventName.setText(Main.getSelectedEventTitle());
        totalTickets.setText("" + maxSeats);
        totalPrice.setText("$" + String.format("%.2f", EventBooking.total));
    }

    private void setUpSeats() {
        for (int i = 0; i < seats.length; i++) {
            if (eventBookings[i] == false) { // IF the seat is available...
                seats[i].setStyle("-fx-background-color:  #edf0f4"); // GREY colour
                int finalI1 = i;

                seats[i].setOnAction(event -> { // Using a lambda expression as an action listener
                    if (booked[finalI1] == false) { // IF the seat is selected...
                        if (numberOfSeats < maxSeats) {
                            numberOfSeats++;
                            seats[finalI1].setStyle("-fx-background-color:  #23b33b"); // GREEN colour
                            setBookedSeats(seats[finalI1], true);
                        } else { // Outputs error message
                            Alert alert = new Alert(AlertType.WARNING, "Error: Maximum seat limit reached!", ButtonType.OK);
                            alert.showAndWait();
                            if (alert.getResult() == ButtonType.OK) {
                                return;
                            }
                        }
                    } else if (booked[finalI1] == true) { // IF the user unselects a seat...
                        numberOfSeats--; // Subtracts 1 from the number of selected seats
                        seats[finalI1].setStyle("-fx-background-color:  #edf0f4"); // GREY colour
                        setBookedSeats(seats[finalI1], false);
                    }
                    popSeat(seats[finalI1]);
                });
            } else if (eventBookings[i] == true) { // IF the seat is unavailable...
                seats[i].setStyle("-fx-background-color:  #e40606"); // RED colour
                int finalI = i;
                seats[i].setOnAction(event -> rotateButton(seats[finalI]));
            }
        }
    }

    private void popSeat(Button btn) {
        ScaleTransition st = new ScaleTransition(Duration.millis(200), btn);
        st.setToX(1.2);
        st.setToY(1.2);
        st.setRate(1.5);
        st.setCycleCount(1);
        st.play();

        st.setOnFinished(event -> {
            ScaleTransition st2 = new ScaleTransition(Duration.millis(200), btn);
            st2.setToX(1);
            st2.setToY(1);
            st2.setRate(1.5);
            st2.setCycleCount(1);
            st2.play();
        });
    }

    private void setBookedSeats(Button btn, boolean selected) {
        int seat = 0;
        if (btn.getId().startsWith("A")) {
            seat = (Integer.parseInt(btn.getId().substring(1))) - 1;
        } else if (btn.getId().startsWith("B")) {
            seat = (Integer.parseInt(btn.getId().substring(1))) + 11;
        } else if (btn.getId().startsWith("C")) {
            seat = (Integer.parseInt(btn.getId().substring(1))) + 23;
        }
        booked[seat] = selected;
        String btnId = btn.getId();
        String s = selectedSeats.getText();
        if (selected == true) {
            if (s.isEmpty()) {
                selectedSeats.setText(btnId);
            } else {
                selectedSeats.setText(s + ", " + btnId);
            }
        } else {
            if (s.startsWith(btnId) && s.length() == 2) {
                selectedSeats.setText(s.replaceAll(btn.getId(), ""));
            } else if (s.startsWith(btnId) && s.length() > 2) {
                selectedSeats.setText(s.replaceAll(btn.getId() + ", ", ""));
            } else {
                selectedSeats.setText(s.replaceAll(", " + btnId, ""));
            }
        }
    }

    public void rotateButton(Button btn) {
        if (rotatedPane == false) {
            rotatedPane = true;
            RotateTransition rt = new RotateTransition(Duration.millis(60), btn);
            rt.setByAngle(45);
            rt.setCycleCount(2);
            rt.setAutoReverse(true);
            rt.play();

            rt.setOnFinished(event -> {
                RotateTransition rt2 = new RotateTransition(Duration.millis(60), btn);
                rt2.setByAngle(-45);
                rt2.setCycleCount(2);
                rt2.setAutoReverse(true);
                rt2.play();
                rt2.setOnFinished(event1 -> rotatedPane = false);
            });
        }
    }

    private void initialiseArray() {
        seats[0] = A1;
        seats[1] = A2;
        seats[2] = A3;
        seats[3] = A4;
        seats[4] = A5;
        seats[5] = A6;
        seats[6] = A7;
        seats[7] = A8;
        seats[8] = A9;
        seats[9] = A10;
        seats[10] = A11;
        seats[11] = A12;

        seats[12] = B1;
        seats[13] = B2;
        seats[14] = B3;
        seats[15] = B4;
        seats[16] = B5;
        seats[17] = B6;
        seats[18] = B7;
        seats[19] = B8;
        seats[20] = A9;
        seats[21] = A10;
        seats[22] = A11;
        seats[23] = A12;

        seats[24] = C1;
        seats[25] = C2;
        seats[26] = C3;
        seats[27] = C4;
        seats[28] = C5;
        seats[29] = C6;
        seats[30] = C7;
        seats[31] = C8;
        seats[32] = A9;
        seats[33] = A10;
        seats[34] = A11;
        seats[35] = A12;
    }

    public void goBack(ActionEvent event) throws IOException {
        Main m = new Main();
        m.changeScene("Event Booking.fxml");
    }

    public void goToConfirmation(ActionEvent event) throws IOException {
        if (numberOfSeats == maxSeats) {
            seatsSelected = true;
            userSeats = selectedSeats.getText();
            Main m = new Main();
            m.changeScene("Confirmation Page.fxml");
        } else {
            Alert alert = new Alert(AlertType.WARNING, "Error: Select all seats!", ButtonType.OK);
            alert.showAndWait();
            if (alert.getResult() == ButtonType.OK) {
                return;
            }
        }
    }
}
