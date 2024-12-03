package application;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.text.ParseException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.LinkedList;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.imageio.ImageIO;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;

public class EventManagement {

    private File selectedImage;

    @FXML
    private Button backButton, homeButton, uploadImageButton, viewEventsButton, addEventButton;
    @FXML
    private Text newEventTitle, newEventDescription, newEventTime1, newEventTime2, newEventTime3, newEventAge, newEventRating;
    @FXML
    private Label newEventStartDate, newEventEndDate;
    @FXML
    private TextArea eventDescription;
    @FXML
    private DatePicker eventStartDate, eventEndDate;
    @FXML
    private TextField eventTitle, eventTrailer, eventRating;
    @FXML
    private ComboBox<String> eventTime1, eventTime2, eventTime3, eventAge;
    @FXML
    private ImageView uploadedEventPoster;

    LinkedList<String> titles = Main.eventTitles;
    LinkedList<String> startDates = Main.eventStartDates;
    LinkedList<String> endDates = Main.eventEndDates;
    LinkedList<String> time1 = Main.eventTime1;
    LinkedList<String> time2 = Main.eventTime2;
    LinkedList<String> time3 = Main.eventTime3;

    @FXML
    void initialize() throws IOException {
        ObservableList<String> obsList1 = FXCollections.observableArrayList("13:00", "14:00", "15:00", "16:00", "17:00",
                "18:00", "19:00", "20:00", "21:00", "22:00", "23:00", "00:00", "01:00", "02:00", "03:00");
        ObservableList<String> obsList2 = FXCollections.observableArrayList("U", "PG", "12A", "15", "18", "R");
        eventAge.setItems(obsList2);
        eventAge.setValue("12A");
        newEventAge.setText("12A");
        eventTime1.setItems(obsList1);
        eventTime2.setItems(obsList1);
        eventTime3.setItems(obsList1);
        eventTime1.setValue("21:00");
        newEventTime1.setText("21:00");
        eventStartDate.setValue(LocalDate.now());
        LocalDate startDate = eventStartDate.getValue();
        String startDateFormatted = startDate.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
        newEventStartDate.setText(startDateFormatted);
    }

    public void goBack(ActionEvent event) throws IOException {
        Main m = new Main();
        m.changeScene("Organizer Home.fxml");
    }

    public void goHome(ActionEvent event) throws IOException {
        Main m = new Main();
        m.changeScene("Organizer Home.fxml");
    }

    public void goToViewEvents(ActionEvent event) throws IOException {
        Main m = new Main();
        m.changeScene("View Events.fxml");
    }

    @FXML
    public void updateDateTimeAge(ActionEvent e) {
        try {
            switch (((Node) e.getSource()).getId()) {
                case "eventStartDate":
                    LocalDate startDate = eventStartDate.getValue();
                    String startDateFormatted = startDate.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
                    newEventStartDate.setText(startDateFormatted);
                    break;
                case "eventEndDate":
                    LocalDate endDate = eventEndDate.getValue();
                    String endDateFormatted = endDate.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
                    newEventEndDate.setText(endDateFormatted);
                    break;
                case "eventTime1":
                    newEventTime1.setText(eventTime1.getValue().toString());
                    break;
                case "eventTime2":
                    newEventTime2.setText(eventTime2.getValue().toString());
                    break;
                case "eventTime3":
                    newEventTime3.setText(eventTime3.getValue().toString());
                    break;
                case "eventAge":
                    newEventAge.setText(eventAge.getValue().toString());
                    break;
            }
        } catch (NullPointerException ex) {
            ex.getMessage();
        }
    }

    @FXML
    public void updateEventText(KeyEvent e) {
        switch (((Node) e.getSource()).getId()) {
            case "eventTitle":
                if (eventTitle.getText().length() > 20) {
                    eventTitle.setEditable(false);
                }
                break;
            case "eventDescription":
                if (eventDescription.getText().length() > 220) {
                    eventDescription.setEditable(false);
                }
                break;
            case "eventRating":
                if (eventRating.getText().length() > 3) {
                    eventRating.setEditable(false);
                }
                break;
        }

        if (e.getCode().equals(KeyCode.BACK_SPACE)) {
            eventTitle.setEditable(true);
            eventDescription.setEditable(true);
            eventRating.setEditable(true);
        }

        switch (((Node) e.getSource()).getId()) {
            case "eventTitle":
                newEventTitle.setText(eventTitle.getText());
                break;
            case "eventDescription":
                newEventDescription.setText(eventDescription.getText());
                break;
            case "eventRating":
                newEventRating.setText(eventRating.getText() + "/10");
                break;
        }
    }

    @FXML
    public void uploadImage(ActionEvent event) throws IOException {
        try {
            FileChooser fc = new FileChooser();
            selectedImage = fc.showOpenDialog(null);
            if (selectedImage == null) {
                return;
            } else if (ImageIO.read(selectedImage) == null) {
                Alert alert = new Alert(AlertType.WARNING, "Please upload an image in JPG or PNG format!",
                        ButtonType.OK);
                alert.showAndWait();
                if (alert.getResult() == ButtonType.OK) {
                    return;
                }
            } else {
                Image img = SwingFXUtils.toFXImage(ImageIO.read(selectedImage), null);
                uploadedEventPoster.setImage(img);
            }
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Login.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    public void storeEventInfo(ActionEvent event) throws ParseException {
        try {
            validateEventInput();

            FileWriter fw = new FileWriter("events.txt", true);
            fw.write(newEventTitle.getText() + ";" + newEventDescription.getText() + ";" + eventTrailer.getText() + ";" +
                     newEventStartDate.getText() + ";" + newEventEndDate.getText() + ";" + newEventTime1.getText() + ";" +
                     newEventTime2.getText() + ";" + newEventTime3.getText() + ";" + newEventAge.getText() + ";" +
                     newEventRating.getText() + "\n");
            fw.close();

            String folderPath = "./Images/eventImages";
            File uploads = new File(folderPath);
            File file = new File(uploads, eventTitle.getText() + ".png");
            InputStream input = Files.newInputStream(selectedImage.toPath());
            Files.copy(input, file.toPath(), StandardCopyOption.REPLACE_EXISTING);

            Alert alert = new Alert(AlertType.INFORMATION, "The event " + eventTitle.getText() + " has been added!",
                    ButtonType.OK);
            alert.showAndWait();

            if (alert.getResult() == ButtonType.OK) {
                eventDescription.setText("");
                eventTrailer.setText("");
                eventTitle.setText("");
                eventStartDate.setPromptText("dd/MM/yyyy");
                eventEndDate.setPromptText("dd/MM/yyyy");
                eventTime1.setPromptText("HH:mm");
                eventTime2.setPromptText("HH:mm");
                eventTime3.setPromptText("HH:mm");
                eventAge.setPromptText("");
                eventRating.setText("");
                alert.close();
            }
        } catch (Exception e) {
            Alert alert = new Alert(AlertType.WARNING, "Error: " + e.getMessage(), ButtonType.OK);
            alert.showAndWait();
        }
    }

    void validateEventInput() throws InvalidEventInputException, ParseException {
        try {
            if (eventTitle.getText().equals("") || eventDescription.getText().equals("") ||
                eventTrailer.getText().equals("") || eventStartDate.getValue().equals("dd/MM/yyyy") ||
                eventEndDate.getValue().equals("dd/MM/yyyy") || eventAge.getValue().equals("") ||
                eventRating.getText().equals("")) {
                throw new InvalidEventInputException("Please complete all fields!");
            } else if (selectedImage == null) {
                throw new InvalidEventInputException("Please add the event poster!");
            } else if (eventStartDate.getValue().compareTo(LocalDate.now()) < 0) {
                throw new InvalidEventInputException("Start date cannot be before today!");
            } else if (eventStartDate.getValue().compareTo(eventEndDate.getValue()) >= 0) {
                throw new InvalidEventInputException("End date must be after the start date!");
            }

            for (int i = 0; i < titles.size(); i++) {
                if (titles.get(i).equals(eventTitle.getText())) {
                    throw new InvalidEventInputException("The title " + eventTitle.getText() +
                            " belongs to another scheduled event!");
                }
            }

            for (int i = 0; i < titles.size(); i++) {
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
                LocalDate startDateEvents = LocalDate.parse(startDates.get(i), formatter);
                LocalDate endDateEvents = LocalDate.parse(endDates.get(i), formatter);

                if (!(eventStartDate.getValue().compareTo(endDateEvents) > 0 ||
                      eventEndDate.getValue().compareTo(startDateEvents) < 0)) {
                    if (time1.get(i).equals(eventTime1.getValue()) || time2.get(i).equals(eventTime2.getValue()) ||
                        time3.get(i).equals(eventTime3.getValue())) {
                        throw new InvalidEventInputException("The screening time(s) of your event: " + eventTitle.getText() +
                                " overlap(s) with another event!");
                    }
                }
            }
        } catch (NullPointerException e) {
            throw new InvalidEventInputException("Please complete all fields!");
        }
    }
}

class InvalidEventInputException extends Exception {
    private static final long serialVersionUID = 1L;

    InvalidEventInputException(String s) {
        super(s);
    }
}
