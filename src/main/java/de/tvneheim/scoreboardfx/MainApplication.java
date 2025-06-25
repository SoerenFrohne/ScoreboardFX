package de.tvneheim.scoreboardfx;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;

public class MainApplication extends Application {

    @Override
    public void start(Stage clientStage) throws IOException {

        var primaryScreen = Screen.getPrimary();

        var clientLoader = new FXMLLoader(MainApplication.class.getResource("/de/tvneheim/scoreboardfx/fxml/scoreboard-client.fxml"));
        var clientScene = new Scene(clientLoader.load());
        clientStage.setTitle("Scoreboard Client");
        clientStage.setScene(clientScene);
        clientStage.show();

        var viewLoader = new FXMLLoader(MainApplication.class.getResource("/de/tvneheim/scoreboardfx/fxml/scoreboard-view.fxml"));
        var viewScene = new Scene(viewLoader.load());
        viewScene.getStylesheets().add(MainApplication.class.getResource("/de/tvneheim/scoreboardfx/style/view.css").toExternalForm());
        var viewStage = new Stage();
        viewStage.setTitle("Scoreboard Client");
        viewStage.setScene(viewScene);
        viewStage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}