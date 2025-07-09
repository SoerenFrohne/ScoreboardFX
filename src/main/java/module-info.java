module de.tvneheim.scoreboardfx {
  requires javafx.controls;
  requires javafx.fxml;
  requires javafx.web;

  requires java.logging;
  requires org.controlsfx.controls;
  requires com.dlsc.formsfx;
  requires org.kordamp.ikonli.core;
  requires org.kordamp.ikonli.javafx;
  requires org.kordamp.ikonli.fontawesome6;
  requires static lombok;
  requires javafx.media;
  requires atlantafx.base;
  requires com.fasterxml.jackson.databind;

  opens de.tvneheim.scoreboardfx to javafx.fxml;
  opens de.tvneheim.scoreboardfx.controller to javafx.fxml;
  opens de.tvneheim.scoreboardfx.view to javafx.fxml;

  exports de.tvneheim.scoreboardfx;
  exports de.tvneheim.scoreboardfx.controller;
  exports de.tvneheim.scoreboardfx.model;
  exports de.tvneheim.scoreboardfx.view;
  exports de.tvneheim.scoreboardfx.infrastructure.settings;
  exports de.tvneheim.scoreboardfx.infrastructure.sound;

  opens de.tvneheim.scoreboardfx.model to javafx.fxml;
  exports de.tvneheim.scoreboardfx.game;
  opens de.tvneheim.scoreboardfx.game to javafx.fxml;
  exports de.tvneheim.scoreboardfx.game.events;
  opens de.tvneheim.scoreboardfx.game.events to javafx.fxml;
}