module de.tvneheim.scoreboardfx {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;

    requires java.logging;
    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires org.kordamp.ikonli.javafx;
    requires static lombok;
    requires javafx.media;

    opens de.tvneheim.scoreboardfx to javafx.fxml;
    opens de.tvneheim.scoreboardfx.controller to javafx.fxml;
    opens de.tvneheim.scoreboardfx.view to javafx.fxml;

    exports de.tvneheim.scoreboardfx;
    exports de.tvneheim.scoreboardfx.controller;
    exports de.tvneheim.scoreboardfx.model;
    exports de.tvneheim.scoreboardfx.view;
    opens de.tvneheim.scoreboardfx.model to javafx.fxml;
}