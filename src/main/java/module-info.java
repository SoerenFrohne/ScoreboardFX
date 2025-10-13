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
  requires com.fasterxml.jackson.datatype.jsr310;
  requires java.sql;
  requires org.slf4j;
  requires ch.qos.logback.classic;
  requires ch.qos.logback.core;
  requires java.desktop;

  opens de.tvneheim.scoreboardfx to javafx.fxml;
  opens de.tvneheim.scoreboardfx.controller to javafx.fxml;
  opens de.tvneheim.scoreboardfx.view to javafx.fxml;

  exports de.tvneheim.scoreboardfx;
  exports de.tvneheim.scoreboardfx.controller;
  exports de.tvneheim.scoreboardfx.model;
  exports de.tvneheim.scoreboardfx.view;
  exports de.tvneheim.scoreboardfx.infrastructure.sound;

  opens de.tvneheim.scoreboardfx.model to javafx.fxml;
  exports de.tvneheim.scoreboardfx.viewmodel;
  opens de.tvneheim.scoreboardfx.viewmodel to javafx.fxml;
  exports de.tvneheim.scoreboardfx.viewmodel.events;
  opens de.tvneheim.scoreboardfx.viewmodel.events to javafx.fxml;
}