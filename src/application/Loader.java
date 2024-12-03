package application;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.scene.control.ProgressBar;
import javafx.util.Duration;

public class Loader {

    @FXML
    private ProgressBar loadingBar;

    public void initialize() {
        // Simulate the loading process with a Timeline
        Timeline timeline = new Timeline(
            new KeyFrame(Duration.ZERO, e -> loadingBar.setProgress(0)),
            new KeyFrame(Duration.seconds(2), e -> loadingBar.setProgress(0.3)),
            new KeyFrame(Duration.seconds(4), e -> loadingBar.setProgress(0.6)),
            new KeyFrame(Duration.seconds(6), e -> loadingBar.setProgress(1.0))
        );

        timeline.setOnFinished(event -> Main.switchToLogin()); // Switch to login screen after loading
        timeline.play();
    }
}
