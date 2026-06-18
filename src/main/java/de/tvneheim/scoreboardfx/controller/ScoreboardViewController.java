package de.tvneheim.scoreboardfx.controller;

import atlantafx.base.util.Animations;
import de.tvneheim.scoreboardfx.model.Side;
import de.tvneheim.scoreboardfx.utils.FileUtils;
import de.tvneheim.scoreboardfx.viewmodel.GameState;
import de.tvneheim.scoreboardfx.viewmodel.GameTimeStatus;
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

import java.net.URL;
import java.util.ResourceBundle;
import java.util.concurrent.atomic.AtomicInteger;

import static de.tvneheim.scoreboardfx.viewmodel.GameState.getSettings;
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
  private HBox pauseContainer, ttoContainer, endContainer;

  @FXML
  private ImageView adDisplay, homeLogo, guestLogo;

  @FXML
  private Label time, pauseTime, scoreHome, scoreGuest, nameHome, nameGuest, period, ttoTime, presentedLabel;

  @Override
  public void initialize(URL url, ResourceBundle resourceBundle) {
    bindModel();
    initBackground();
    initAnimations();
    initAdLoop();

    Platform.runLater(this::initLogos);
  }

  private void initAdLoop() {
    LayoutUtils.bindExactSize(
        adDisplayContainer,
        grid.widthProperty().divide(grid.getColumnConstraints().size()).multiply(GridPane.getColumnSpan(adDisplayContainer)),
        grid.heightProperty().divide(grid.getRowConstraints().size()).multiply(GridPane.getRowSpan(adDisplayContainer))
    );

    adDisplay.setSmooth(true);
    adDisplay.setPreserveRatio(true);
    adDisplay.fitHeightProperty().bind(adDisplayContainer.heightProperty());
    adDisplay.fitWidthProperty().bind(adDisplayContainer.widthProperty());

    var path = GameState.getSettings().pathToAdImages().get();
    var files = FileUtils.listFiles(path);
    log.info("Found following ads from '{}': {}", path, files);

    if (!files.isEmpty()) {
      var file = files.getFirst();
      var img = new Image(file.toURI().toString());
      adDisplay.setImage(img);



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
  }

  private void bindModel() {
    period.textProperty().bind(getStopWatch().getPeriodTimer().description());
    time.textProperty().bind(bindFormattedTime(getStopWatch().getPeriodTimer().currentTime()));

    pauseTime.textProperty().bind(bindFormattedTime(getStopWatch().getPauseTimer().current()));
    pauseContainer.managedProperty().bind(pauseContainer.visibleProperty());
    pauseContainer.visibleProperty().bind(getStopWatch().getPauseTimer().running());

    endContainer.setVisible(false);
    endContainer.managedProperty().bind(endContainer.visibleProperty());
    getStopWatch().getGameTimeStatus().addListener((observableValue, oldStatus, newStatus) -> {
      if (newStatus == GameTimeStatus.FINISHED) {
        endContainer.setVisible(true);
        pauseContainer.setVisible(false);
      }
    });

    ttoTime.textProperty().bind(bindFormattedTime(getStopWatch().getTimeOutTimer().current()));
    ttoContainer.visibleProperty().bind(getStopWatch().getTimeOutTimer().running());

    presentedLabel.visibleProperty().bind(
        getStopWatch().getPauseTimer().running()
            .or(getStopWatch().getTimeOutTimer().running())
            .or(getSettings().pathToAdImages().isEmpty())
            .not()
    );

    getSettings().pathToAdImages().addListener((observable, oldValue, newValue) -> log.info("newVal: {}", newValue));

    nameHome.textProperty().bind(getSettings().homeTeamName());
    nameGuest.textProperty().bind(getSettings().guestTeamName());

    GameState.getGame().addListener((observable, oldValue, game) -> {
      scoreHome.setText(String.valueOf(game.home().score()));
      scoreGuest.setText(String.valueOf(game.guest().score()));
    });

    updateSuspensions(penaltiesHome, getStopWatch().getSuspensionsHome(), Side.HOME);
    getStopWatch().getSuspensionsHome().addListener(change -> updateSuspensions(penaltiesHome, getStopWatch().getSuspensionsHome(), Side.HOME));

    updateSuspensions(penaltiesGuest, getStopWatch().getSuspensionsGuest(), Side.GUEST);
    getStopWatch().getSuspensionsGuest().addListener(change -> updateSuspensions(penaltiesGuest, getStopWatch().getSuspensionsGuest(), Side.GUEST));

    rootPane.widthProperty().addListener(observable -> {
      nameHome.setPrefWidth(rootPane.getWidth() * 0.3d);
      nameGuest.setPrefWidth(rootPane.getWidth() * 0.3d);
    });

    GameState.getSettings().pathToAdImages().addListener((observable, oldValue, newValue) -> initAdLoop());
  }

  private void updateSuspensions(Pane root, SuspensionSlots suspensions, Side side) {
    root.getChildren().clear();

    // fill currentTime penalties
    suspensions.forEach(suspension -> {
      if (suspension == null) {
        var emptyLabel = new SuspensionLabel(side);
        root.getChildren().add(emptyLabel);
      } else {
        var penaltyLabel = new SuspensionLabel(suspension, side);
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

    // home
    LayoutUtils.bindExactSize(
        homeLogoContainer,
        grid.widthProperty().divide(grid.getColumnConstraints().size()).multiply(GridPane.getColumnSpan(homeLogoContainer)),
        grid.heightProperty().divide(grid.getRowConstraints().size()).multiply(GridPane.getRowSpan(homeLogoContainer))
    );
    homeLogo.fitHeightProperty().bind(homeLogoContainer.heightProperty());

    //var homeTeamLogo = new Image(files.get(2).toURI().toString(), homeLogo.getFitWidth() * 1.25, homeLogo.getFitHeight() * 1.25, true, true);
    homeLogo.imageProperty().bind(GameState.getSettings().homeTeamLogo());
    homeLogo.setSmooth(true);

    // guest
    LayoutUtils.bindExactSize(
        guestLogoContainer,
        grid.widthProperty().divide(grid.getColumnConstraints().size()).multiply(GridPane.getColumnSpan(guestLogoContainer)),
        grid.heightProperty().divide(grid.getRowConstraints().size()).multiply(GridPane.getRowSpan(guestLogoContainer))
    );
    guestLogo.fitHeightProperty().bind(guestLogoContainer.heightProperty());

    guestLogo.imageProperty().bind(GameState.getSettings().guestTeamLogo());
    guestLogo.setSmooth(true);
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
