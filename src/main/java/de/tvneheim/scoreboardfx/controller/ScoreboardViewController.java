package de.tvneheim.scoreboardfx.controller;

import de.tvneheim.scoreboardfx.events.GameState;
import de.tvneheim.scoreboardfx.utils.LayoutUtils;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.web.WebView;
import lombok.extern.java.Log;

import java.io.File;
import java.net.URL;
import java.util.Arrays;
import java.util.ResourceBundle;

@Log
public class ScoreboardViewController implements Initializable {

  @FXML
  private AnchorPane rootPane, scoreHomeContainer, scoreGuestContainer;

  @FXML
  private VBox centerPane;

  @FXML
  private ImageView adDisplay, homeLogo, guestLogo;

  @FXML
  private Label time, scoreHome, scoreGuest, nameHome, nameGuest;


  @Override
  public void initialize(URL url, ResourceBundle resourceBundle) {
    bindModel();
    initBackground();
    initAds();
    initLogos();
  }

  private void bindModel() {
    time.textProperty().bind(GameState.getStopWatch().getTime());

    GameState.getGame().addListener((observable, oldValue, game) -> {
      scoreHome.setText(String.valueOf(game.home().score()));
      scoreGuest.setText(String.valueOf(game.guest().score()));
      nameHome.setText(game.home().name());
      nameGuest.setText(game.guest().name());
    });

    rootPane.widthProperty().addListener(observable -> {
      nameHome.setPrefWidth(rootPane.getWidth() * 0.3d);
      nameGuest.setPrefWidth(rootPane.getWidth() * 0.3d);
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

  private void initAds() {
    centerPane.widthProperty().addListener(observable -> adDisplay.setFitWidth(.66 * centerPane.getWidth()));

    var directory = new File(GameState.getSettings().pathToAdImages());
    var files = Arrays.stream(directory.listFiles()).toList();
    log.info("Found following ads: " + files);

    var image = new Image(files.stream().findFirst().orElseThrow().toURI().toString());
    adDisplay.setImage(image);
  }

  private void initLogos() {
    var directory = new File(GameState.getSettings().pathToAdLogos());
    var files = Arrays.stream(directory.listFiles()).toList();
    log.info("Found following logos: " + files);

    // home
    scoreHomeContainer.widthProperty().addListener(observable -> {
      homeLogo.setFitWidth(scoreHomeContainer.getWidth());
      Platform.runLater(() -> LayoutUtils.centerImage(homeLogo));
    });
    scoreHomeContainer.heightProperty().addListener(observable -> {
      homeLogo.setFitHeight(scoreHomeContainer.getHeight());
      Platform.runLater(() -> LayoutUtils.centerImage(homeLogo));
    });
    var homeTeamLogo = new Image(files.get(1).toURI().toString());
    homeLogo.setImage(homeTeamLogo);

    // guest
    scoreGuestContainer.widthProperty().addListener(observable -> {
      guestLogo.setFitWidth(scoreGuestContainer.getWidth());
      Platform.runLater(() -> LayoutUtils.centerImage(guestLogo));
    });
    scoreGuestContainer.heightProperty().addListener(observable -> {
      guestLogo.setFitHeight(scoreGuestContainer.getHeight());
      Platform.runLater(() -> LayoutUtils.centerImage(guestLogo));
    });

    var guestTeamLogo = new Image(files.getFirst().toURI().toString());
    guestLogo.setImage(guestTeamLogo);
  }
}
