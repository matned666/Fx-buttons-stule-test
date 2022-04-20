import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.effect.DropShadow;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class Main extends Application {

    private VBox root;
    private Button dragButton;
    private Button additionalButton;
    private Button exitButton;

    private double x;
    private double y;

    private int clickCount = 0;

    @Override
    public void start(Stage primaryStage) {
        initButtons(primaryStage);
        root = new VBox(dragButton, additionalButton, exitButton);
        Scene scene = new Scene(root);
        root.setBackground(new Background(new BackgroundFill(Color.TRANSPARENT, null, null)));
        root.setStyle("-fx-border-color: #66666680;-fx-border-width:5px;-fx-border-radius:5px");
        primaryStage.setScene(scene);
        primaryStage.initStyle(StageStyle.TRANSPARENT);
        scene.setFill(Color.TRANSPARENT);
        primaryStage.show();
        primaryStage.setX(primaryStage.getX()* 1.5);
        primaryStage.setAlwaysOnTop(true);
    }

    private void initButtons(Stage primaryStage) {
        dragButton = getButton("Drag this button");
        additionalButton = getButton("Click counter");
        exitButton = getButton("EXIT");
        initDragListener(primaryStage, dragButton);
        exitButton.setOnAction(event -> {
            Platform.exit();
            System.exit(0);
        });
        additionalButton.setOnAction(event -> {
            clickCount++;
            additionalButton.setText("Clicked " + clickCount + " times");
        });
    }

    private void initDragListener(Stage primaryStage, Button button) {
        button.setCursor(Cursor.MOVE);

        button.setOnMousePressed(event -> {
            x = event.getScreenX();
            y = event.getScreenY();
        });

        button.setOnMouseDragged(event -> {
            x -= event.getScreenX();
            y -= event.getScreenY();
            primaryStage.setX(primaryStage.getX() - x);
            primaryStage.setY(primaryStage.getY() - y);
            x = event.getScreenX();
            y = event.getScreenY();
        });
    }

    private Button getButton(String name) {
        Button button = new Button(name);
        button.setStyle("-fx-border-color: #999999;-fx-background-color: #99999933;-fx-text-fill:#ffffff;-fx-font-size:50px;-fx-border-radius:4px");
        button.setPrefWidth(800);
        button.setPrefHeight(100);
        DropShadow dropShadow = new DropShadow();
        dropShadow.setHeight(10);

        ColorAdjust colorAdjust = new ColorAdjust();

        button.hoverProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue) {
                colorAdjust.setBrightness(0.5);
            } else {
                colorAdjust.setBrightness(0);
            }
        });

        button.setOnMousePressed(event -> {
            colorAdjust.setBrightness(0.8);
        });

        button.setOnMouseReleased(event -> {
            if (button.isHover()) {
                colorAdjust.setBrightness(0.5);
            } else {
                colorAdjust.setBrightness(0);
            }
        });

        colorAdjust.setInput(dropShadow);
        button.setEffect(colorAdjust);
        return button;
    }



}

