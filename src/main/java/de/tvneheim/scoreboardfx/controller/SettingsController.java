package de.tvneheim.scoreboardfx.controller;

import de.tvneheim.scoreboardfx.MainApplication;
import de.tvneheim.scoreboardfx.utils.DurationSpinnerValueFactory;
import de.tvneheim.scoreboardfx.viewmodel.GameState;
import de.tvneheim.scoreboardfx.viewmodel.Settings;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.stage.FileChooser;
import javafx.stage.Screen;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.time.Duration;
import java.util.Optional;
import java.util.ResourceBundle;

public class SettingsController implements Initializable {

  @FXML
  private StackPane root;

  @FXML
  private TextField homeTeamName, guestTeamName;

  @FXML
  private ImageView homeLogo, guestLogo;

  @FXML
  private Spinner<Integer> numberOfPeriods;

  @FXML
  private Spinner<Duration> periodLength, pauseLength, ttoLength, ttoWarningTime;

  private final Settings settings = GameState.getSettings();

  @Override
  public void initialize(URL location, ResourceBundle resources) {
    homeTeamName.textProperty().bindBidirectional(settings.homeTeamName());
    homeLogo.imageProperty().bind(settings.homeTeamLogo());

    guestTeamName.textProperty().bindBidirectional(settings.guestTeamName());
    guestLogo.imageProperty().bind(settings.guestTeamLogo());

    numberOfPeriods.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 4));
    numberOfPeriods.getValueFactory().valueProperty().bindBidirectional(settings.numberOfPeriods().asObject());

    periodLength.setValueFactory(new DurationSpinnerValueFactory());
    periodLength.getValueFactory().valueProperty().bindBidirectional(settings.lengthPerPeriod());

    pauseLength.setValueFactory(new DurationSpinnerValueFactory());
    pauseLength.getValueFactory().valueProperty().bindBidirectional(settings.pauseBetweenPeriods());

    ttoLength.setValueFactory(new DurationSpinnerValueFactory());
    ttoLength.getValueFactory().valueProperty().bindBidirectional(settings.timePerTeamTimeOut());

    ttoWarningTime.setValueFactory(new DurationSpinnerValueFactory());
    ttoWarningTime.getValueFactory().valueProperty().bindBidirectional(settings.timeOutWarningTime());
  }

  @FXML
  public void pickHomeLogo(ActionEvent event) {
    pickImage().ifPresent(image -> settings.homeTeamLogo().setValue(image));
  }

  @FXML
  public void pickGuestLogo(ActionEvent event) {
    pickImage().ifPresent(image -> settings.guestTeamLogo().setValue(image));
  }

  @FXML
  public void startGame(ActionEvent event) throws IOException {


  }

  private Optional<Image> pickImage() {
    var fileChooser = new FileChooser();
    fileChooser.setTitle("Bild auswählen");

    // Standardverzeichnis
    var defaultDir = new File("D:\\TV Neheim 1884\\Scoreboard\\Logos");
    if (defaultDir.exists()) {
      fileChooser.setInitialDirectory(defaultDir);
    }

    // Nur Bilddateien anzeigen
    fileChooser.getExtensionFilters().addAll(
        new FileChooser.ExtensionFilter("Bilddateien", "*.png", "*.jpg", "*.jpeg", "*.gif")
    );

    var file = fileChooser.showOpenDialog(root.getScene().getWindow());
    var img = new Image(file.toURI().toString(), 1024, 1024, true, true);
    return Optional.of(img);
  }
}
