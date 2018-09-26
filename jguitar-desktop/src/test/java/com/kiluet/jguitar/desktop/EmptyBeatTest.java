package com.kiluet.jguitar.desktop;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

public class EmptyBeatTest extends Application {

    @Override
    public void start(Stage primaryStage) {
        BorderPane pane = new BorderPane();
        pane.setPadding(new Insets(20, 20, 20, 20));

        HBox hbox = new HBox();
        int stringCount = 6;
        hbox.getChildren().add(createMeasurePane(stringCount));
        pane.setCenter(hbox);

        Scene scene = new Scene(pane);
        primaryStage.setScene(scene);
        primaryStage.setWidth(100);
        primaryStage.setHeight(200);
        primaryStage.show();
    }

    public GridPane createMeasurePane(int stringCount) {

        GridPane gridPane = new GridPane();

        for (int i = 0; i < stringCount; i++) {

            StackPane stackPane = new StackPane();
            // -fx-border-color: red;
            stackPane.setMinHeight(18);
            stackPane.setMaxHeight(18);
            stackPane.setStyle("-fx-padding: 0;");

            Line line = new Line(0, i + 1 * 10, 40, i + 1 * 10);
            line.setStroke(Color.LIGHTGRAY);

            stackPane.getChildren().addAll(line);

            gridPane.add(stackPane, 0, i + 1);

        }

        GridPane circleGrid = new GridPane();
        circleGrid.setAlignment(Pos.CENTER);
        Rectangle rectangle = new Rectangle(13, 5);
        circleGrid.add(rectangle, 0, 0);
        GridPane.setMargin(rectangle, new Insets(0, 0, 12, 0));
        gridPane.add(circleGrid, 0, 0, 2, 7);

        return gridPane;
    }

    public static void main(String[] args) {
        EmptyBeatTest.launch(EmptyBeatTest.class, args);
    }
}
