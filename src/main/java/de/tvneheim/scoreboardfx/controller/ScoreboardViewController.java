package de.tvneheim.scoreboardfx.controller;

import atlantafx.base.util.Animations;
import de.tvneheim.scoreboardfx.view.ImageViewPane;
import de.tvneheim.scoreboardfx.viewmodel.GameState;
import de.tvneheim.scoreboardfx.viewmodel.SuspensionSlots;
import de.tvneheim.scoreboardfx.utils.LayoutUtils;
import de.tvneheim.scoreboardfx.view.SuspensionLabel;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.shape.Rectangle;
import javafx.scene.web.WebView;
import lombok.extern.slf4j.Slf4j;

import java.awt.*;
import java.io.File;
import java.net.URL;
import java.util.Arrays;
import java.util.ResourceBundle;
import java.util.concurrent.atomic.AtomicInteger;

import static de.tvneheim.scoreboardfx.viewmodel.GameState.getStopWatch;
import static de.tvneheim.scoreboardfx.utils.FXUtils.convertToFxDuration;
import static de.tvneheim.scoreboardfx.utils.FormatterUtils.bindFormattedTime;

@Slf4j
public class ScoreboardViewController implements Initializable {

  @FXML
  private GridPane grid;

  @FXML
  private AnchorPane rootPane;

  @FXML
  private StackPane adDisplayContainer, homeLogoContainer, guestLogoContainer;

  @FXML
  private VBox penaltiesHome, penaltiesGuest;

  @FXML
  private HBox pauseContainer, ttoContainer;

  @FXML
  private ImageView adDisplay, homeLogo, guestLogo;

  @FXML
  private Label time, pauseTime, scoreHome, scoreGuest, nameHome, nameGuest, period, ttoTime;

  @Override
  public void initialize(URL url, ResourceBundle resourceBundle) {
    bindModel();
    initBackground();
    initAnimations();

    Platform.runLater(() -> {
      initAdLoop();
      initLogos();
    });
  }

  private void initAdLoop() {

    var directory = new File(GameState.getSettings().pathToAdImages().get());
    var files = Arrays.stream(directory.listFiles()).toList();
    log.info("Found following ads: {}", files);
    var file = files.getFirst();
    var img = new Image(file.toURI().toString());

    LayoutUtils.bindExactSize(
        adDisplayContainer,
        grid.widthProperty().divide(grid.getColumnConstraints().size()).multiply(GridPane.getColumnSpan(adDisplayContainer)),
        grid.heightProperty().divide(grid.getRowConstraints().size()).multiply(GridPane.getRowSpan(adDisplayContainer))
    );

    adDisplay.setImage(img);
    adDisplay.setSmooth(true);
    adDisplay.setPreserveRatio(true);
    adDisplay.fitHeightProperty().bind(adDisplayContainer.heightProperty());

    Rectangle clip = new Rectangle();
    clip.widthProperty().bind(adDisplayContainer.widthProperty());
    clip.heightProperty().bind(adDisplayContainer.heightProperty());
    adDisplayContainer.setClip(clip);

    AtomicInteger index = new AtomicInteger();
    var timeline = new Timeline(new KeyFrame(convertToFxDuration(GameState.getSettings().showTimeOfAds().get()), event -> {
      index.getAndIncrement();
      var currentFile = files.get(index.get() % files.size());
      var currentImg = new Image(currentFile.toURI().toString());
      adDisplay.setImage(currentImg);
    }));


    timeline.setCycleCount(Timeline.INDEFINITE);
    timeline.setAutoReverse(false);
    timeline.playFromStart();
  }

  private void bindModel() {
    period.textProperty().bind(getStopWatch().getPeriodTimer().description());
    time.textProperty().bind(bindFormattedTime(getStopWatch().getPeriodTimer().currentTime()));

    pauseTime.textProperty().bind(bindFormattedTime(getStopWatch().getPauseTimer().current()));
    pauseContainer.visibleProperty().bind(getStopWatch().getPauseTimer().running());

    ttoTime.textProperty().bind(bindFormattedTime(getStopWatch().getTimeOutTimer().current()));
    ttoContainer.visibleProperty().bind(getStopWatch().getTimeOutTimer().running());

    GameState.getGame().addListener((observable, oldValue, game) -> {
      scoreHome.setText(String.valueOf(game.home().score()));
      scoreGuest.setText(String.valueOf(game.guest().score()));
      nameHome.setText(game.home().name());
      nameGuest.setText(game.guest().name());
    });

    updateSuspensions(penaltiesHome, getStopWatch().getSuspensionsHome());
    getStopWatch().getSuspensionsHome().addListener(change -> updateSuspensions(penaltiesHome, getStopWatch().getSuspensionsHome()));

    updateSuspensions(penaltiesGuest, getStopWatch().getSuspensionsGuest());
    getStopWatch().getSuspensionsGuest().addListener(change -> updateSuspensions(penaltiesGuest, getStopWatch().getSuspensionsGuest()));

    rootPane.widthProperty().addListener(observable -> {
      nameHome.setPrefWidth(rootPane.getWidth() * 0.3d);
      nameGuest.setPrefWidth(rootPane.getWidth() * 0.3d);
    });
  }

  private void updateSuspensions(Pane root, SuspensionSlots suspensions) {
    root.getChildren().clear();

    // fill currentTime penalties
    suspensions.forEach(suspension -> {
      if (suspension == null) {
        var emptyLabel = new SuspensionLabel();
        root.getChildren().add(emptyLabel);
      } else {
        var penaltyLabel = new SuspensionLabel(suspension);
        root.getChildren().add(penaltyLabel);
      }
    });
  }

  private void initBackground() {
    WebView webView = new WebView();
    URL resource = this.getClass().getResource("/de/tvneheim/scoreboardfx/static/background.html");
    assert resource != null;
    webView.getEngine().load(resource.toString());

    AnchorPane.setBottomAnchor(webView, 0d);
    AnchorPane.setTopAnchor(webView, 0d);
    AnchorPane.setRightAnchor(webView, 0d);
    AnchorPane.setLeftAnchor(webView, 0d);
    rootPane.getChildren().addFirst(webView);
  }

  private void initLogos() {
    var directory = new File(GameState.getSettings().pathToLogos().get());
    var files = Arrays.stream(directory.listFiles()).toList();
    log.info("Found following logos: {}", files);

    // home
    LayoutUtils.bindExactSize(
        homeLogoContainer,
        grid.widthProperty().divide(grid.getColumnConstraints().size()).multiply(GridPane.getColumnSpan(homeLogoContainer)),
        grid.heightProperty().divide(grid.getRowConstraints().size()).multiply(GridPane.getRowSpan(homeLogoContainer))
    );
    homeLogo.fitHeightProperty().bind(homeLogoContainer.heightProperty());

    var homeTeamLogo = new Image(files.get(2).toURI().toString());
    homeLogo.setImage(homeTeamLogo);

    // guest
    LayoutUtils.bindExactSize(
        guestLogoContainer,
        grid.widthProperty().divide(grid.getColumnConstraints().size()).multiply(GridPane.getColumnSpan(guestLogoContainer)),
        grid.heightProperty().divide(grid.getRowConstraints().size()).multiply(GridPane.getRowSpan(guestLogoContainer))
    );
    guestLogo.fitHeightProperty().bind(guestLogoContainer.heightProperty());

    var guestTeamLogo = new Image(files.get(1).toURI().toString());
    guestLogo.setImage(guestTeamLogo);
  }

  private void initAnimations() {
    scoreHome.textProperty().addListener(observable -> {
      var animations = Animations.pulse(scoreHome);
      animations.playFromStart();
    });
    scoreGuest.textProperty().addListener(observable -> {
      var animations = Animations.pulse(scoreGuest);
      animations.playFromStart();
    });
  }
}
