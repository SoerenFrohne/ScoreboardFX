package de.tvneheim.scoreboardfx;

import atlantafx.base.theme.CupertinoDark;
import ch.qos.logback.classic.LoggerContext;
import ch.qos.logback.classic.joran.JoranConfigurator;
import ch.qos.logback.core.joran.spi.JoranException;
import de.tvneheim.scoreboardfx.controller.ScoreboardViewController;
import de.tvneheim.scoreboardfx.infrastructure.logging.LoggingHelper;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;

@Slf4j
public class MainApplication extends Application {

  @Override
  public void start(Stage primaryStage) throws IOException {

    log.info("Path: {}", new File(".").getAbsolutePath());
    Application.setUserAgentStylesheet(new CupertinoDark().getUserAgentStylesheet());

    var primaryScreen = Screen.getPrimary();

    var settingsLoader = new FXMLLoader(MainApplication.class.getResource("/de/tvneheim/scoreboardfx/fxml/settings.fxml"));
    var settingsScene = new Scene(settingsLoader.load());
    primaryStage.setTitle("Einstellungen");
    primaryStage.setScene(settingsScene);
    primaryStage.setX(primaryScreen.getVisualBounds().getMinX() + 128);
    primaryStage.setY(primaryScreen.getVisualBounds().getMinY() + 128);
    primaryStage.show();

    var viewStage = ScoreboardViewController.show();
    if(Screen.getScreens().size() == 2) {
      var secondScreen = Screen.getScreens().get(1);
      var bounds = secondScreen.getBounds();

      viewStage.setWidth(bounds.getWidth());
      viewStage.setHeight(bounds.getHeight());
      viewStage.setX(bounds.getMinX());
      viewStage.setY(bounds.getMinY());
      viewStage.setFullScreen(true);
    }
  }

  public static void main(String[] args) throws JoranException {

    String logDir = LoggingHelper.getLogDir("ScoreboardFx").toString();
    System.setProperty("app.logdir", logDir);
    LoggerContext context = (LoggerContext) LoggerFactory.getILoggerFactory();
    JoranConfigurator configurator = new JoranConfigurator();
    configurator.setContext(context);
    context.reset();
    configurator.doConfigure(MainApplication.class.getResource("/logback.xml"));

    launch();
  }
}