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
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
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
  private TextField homeTeamName, guestTeamName, pathToAds;

  @FXML
  private ImageView homeLogo, guestLogo;

  @FXML
  private Spinner<Integer> numberOfPeriods;

  @FXML
  private Spinner<Duration> periodLength, pauseLength, ttoLength, ttoWarningTime, penaltyLength;

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

    penaltyLength.setValueFactory(new DurationSpinnerValueFactory());
    penaltyLength.getValueFactory().valueProperty().bindBidirectional(settings.penaltyLength());

    ttoLength.setValueFactory(new DurationSpinnerValueFactory(Duration.ZERO, Duration.ofSeconds(180), Duration.ofSeconds(1)));
    ttoLength.getValueFactory().valueProperty().bindBidirectional(settings.timePerTeamTimeOut());

    ttoWarningTime.setValueFactory(new DurationSpinnerValueFactory(Duration.ZERO, Duration.ofSeconds(180), Duration.ofSeconds(1)));
    ttoWarningTime.getValueFactory().valueProperty().bindBidirectional(settings.timeOutWarningTime());

    pathToAds.textProperty().bindBidirectional(settings.pathToAdImages());
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
    var stage = (Stage) root.getScene().getWindow();
    var clientLoader = new FXMLLoader(MainApplication.class.getResource("/de/tvneheim/scoreboardfx/fxml/scoreboard-client.fxml"));
    var clientScene = new Scene(clientLoader.load());
    clientScene.getStylesheets().add(MainApplication.class.getResource("/de/tvneheim/scoreboardfx/style/client.css").toExternalForm());
    stage.setTitle("Scoreboard Client");
    stage.setScene(clientScene);
    stage.show();
  }

  @FXML
  public void pickAdFolder(ActionEvent event) {
    var dirChooser = new DirectoryChooser();
    dirChooser.setTitle("Ordner auswählen");

    var dir = dirChooser.showDialog(root.getScene().getWindow());
    pathToAds.textProperty().setValue(dir.getAbsolutePath());
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
