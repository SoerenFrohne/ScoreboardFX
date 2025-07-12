package de.tvneheim.scoreboardfx;

import atlantafx.base.theme.CupertinoDark;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Screen;
import javafx.stage.Stage;
import lombok.extern.java.Log;

import java.io.File;
import java.io.IOException;

@Log
public class MainApplication extends Application {

    @Override
    public void start(Stage clientStage) throws IOException {

        log.info("Path: " + new File(".").getAbsolutePath());
        Application.setUserAgentStylesheet(new CupertinoDark().getUserAgentStylesheet());

        var primaryScreen = Screen.getPrimary();

        var clientLoader = new FXMLLoader(MainApplication.class.getResource("/de/tvneheim/scoreboardfx/fxml/scoreboard-client.fxml"));
        var clientScene = new Scene(clientLoader.load());
        clientScene.getStylesheets().add(MainApplication.class.getResource("/de/tvneheim/scoreboardfx/style/client.css").toExternalForm());
        clientStage.setTitle("Scoreboard Client");
        clientStage.setScene(clientScene);
        clientStage.show();

        var viewLoader = new FXMLLoader(MainApplication.class.getResource("/de/tvneheim/scoreboardfx/fxml/scoreboard-view.fxml"));
        var viewScene = new Scene(viewLoader.load());
        viewScene.getStylesheets().add(MainApplication.class.getResource("/de/tvneheim/scoreboardfx/style/view.css").toExternalForm());
        var viewStage = new Stage();
        viewStage.setTitle("Scoreboard View");
        viewStage.setScene(viewScene);
        viewStage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}