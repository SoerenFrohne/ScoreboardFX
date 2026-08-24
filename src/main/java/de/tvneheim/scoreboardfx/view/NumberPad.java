package de.tvneheim.scoreboardfx.view;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;

import java.util.function.Consumer;

public class NumberPad extends VBox {

  private final TextField display = new TextField();

  private Consumer<Integer> onConfirm;

  public NumberPad() {
    initialize();
  }

  private void initialize() {
    setSpacing(10);
    setAlignment(Pos.CENTER);

    display.setEditable(false);
    display.setFocusTraversable(false);
    display.setAlignment(Pos.CENTER);
    display.getStyleClass().add("number-pad-display");

    GridPane grid = new GridPane();
    grid.setHgap(8);
    grid.setVgap(8);

    addNumberButton(grid, "1", 0, 0);
    addNumberButton(grid, "2", 1, 0);
    addNumberButton(grid, "3", 2, 0);

    addNumberButton(grid, "4", 0, 1);
    addNumberButton(grid, "5", 1, 1);
    addNumberButton(grid, "6", 2, 1);

    addNumberButton(grid, "7", 0, 2);
    addNumberButton(grid, "8", 1, 2);
    addNumberButton(grid, "9", 2, 2);

    Button deleteButton = createButton("⌫");
    deleteButton.setOnAction(e -> deleteLastDigit());

    Button zeroButton = createButton("0");
    zeroButton.setOnAction(e -> appendDigit("0"));

    Button confirmButton = createButton("✓");
    confirmButton.getStyleClass().add("number-pad-confirm-button");
    confirmButton.setOnAction(e -> confirm());

    grid.add(deleteButton, 0, 3);
    grid.add(zeroButton, 1, 3);
    grid.add(confirmButton, 2, 3);

    getChildren().addAll(display, grid);
  }

  private void addNumberButton(
      GridPane grid,
      String number,
      int column,
      int row
  ) {
    Button button = createButton(number);

    button.setOnAction(e -> appendDigit(number));

    grid.add(button, column, row);
  }

  private Button createButton(String text) {
    Button button = new Button(text);

    button.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
    button.setPrefSize(70, 60);

    button.getStyleClass().add("number-pad-button");

    GridPane.setHgrow(button, Priority.ALWAYS);
    GridPane.setVgrow(button, Priority.ALWAYS);

    return button;
  }

  private void appendDigit(String digit) {
    if (display.getText().length() >= 2) {
      return;
    }

    display.appendText(digit);
  }

  private void deleteLastDigit() {
    String text = display.getText();

    if (!text.isEmpty()) {
      display.setText(
          text.substring(0, text.length() - 1)
      );
    }
  }

  private void confirm() {
    if (display.getText().isBlank()) {
      return;
    }

    int number = Integer.parseInt(display.getText());

    if (onConfirm != null) {
      onConfirm.accept(number);
    }
  }

  public void setOnConfirm(Consumer<Integer> onConfirm) {
    this.onConfirm = onConfirm;
  }

  public void clear() {
    display.clear();
  }

  public Integer getValue() {
    if (display.getText().isBlank()) {
      return null;
    }

    return Integer.parseInt(display.getText());
  }
}