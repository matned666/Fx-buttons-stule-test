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

    private int clickCount1 = 0;
    private int clickCount2 = 0;

    @Override
    public void start(Stage primaryStage) {
        initButtons(primaryStage);
        root = new VBox(dragButton, additionalButton, exitButton);
        root.setBackground(new Background(new BackgroundFill(Color.TRANSPARENT, null, null)));
        root.setStyle("-fx-border-color: #666666aa;-fx-border-width:50px;-fx-border-radius:5px");
        initDragListener(primaryStage);

        Scene scene = new Scene(root);
        primaryStage.setScene(scene);
        primaryStage.initStyle(StageStyle.TRANSPARENT);
        scene.setFill(Color.TRANSPARENT);
        primaryStage.show();
        primaryStage.setX(primaryStage.getX()* 1.5);
        primaryStage.setAlwaysOnTop(true);
    }

    private void initButtons(Stage primaryStage) {
        dragButton = getButton("Click counter 1");
        additionalButton = getButton("Click counter 2");
        exitButton = getButton("EXIT");
        exitButton.setOnAction(event -> {
            Platform.exit();
            System.exit(0);
        });
        dragButton.setOnAction(event -> {
            clickCount1++;
            dragButton.setText("Clicked " + clickCount1 + " times");
        });
        additionalButton.setOnAction(event -> {
            clickCount2++;
            additionalButton.setText("Clicked " + clickCount2 + " times");
        });

    }

    private void initDragListener(Stage primaryStage) {
        root.setCursor(Cursor.MOVE);

        root.setOnMousePressed(event -> {
            x = event.getScreenX();
            y = event.getScreenY();
        });

        root.setOnMouseDragged(event -> {
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
        button.setCursor(Cursor.HAND);

        button.setStyle("-fx-border-color: #999999;-fx-background-color: #99999933;-fx-text-fill:#ffffff;-fx-font-size:50px;-fx-border-radius:4px;-fx-background-radius:4px");
        button.setPrefWidth(800);
        button.setPrefHeight(100);
        DropShadow dropShadow = new DropShadow();
        dropShadow.setHeight(5);
        dropShadow.setOffsetX(-2);
        dropShadow.setOffsetY(-1);
        dropShadow.setSpread(0.3);

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

