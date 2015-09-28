package com.kiluet.jguitar.desktop;

import javafx.application.Application;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.stage.Stage;

public class MeasureRepeatCloseTest extends Application {

    @Override
    public void start(Stage primaryStage) {
        BorderPane pane = new BorderPane();
        pane.setPadding(new Insets(20, 20, 20, 20));

        HBox hbox = new HBox();
        int stringCount = 6;
        hbox.getChildren().add(createStartMeasurePane(stringCount));
        // hbox.getChildren().add(createMeasurePane(stringCount));
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

        return gridPane;
    }

    public GridPane createStartMeasurePane(int stringCount) {
        GridPane gridPane = new GridPane();

        for (int i = 0; i < stringCount; i++) {

            Line line = new Line(0, i + 1 * 10, 15, i + 1 * 10);
            line.setStroke(Color.LIGHTGRAY);
            gridPane.add(line, 0, i + 1);
            GridPane.setMargin(line, new Insets(9, 0, 9, 0));

        }

        Line a = new Line(0, 0, 0, (stringCount - 1) * 19);
        a.setStrokeWidth(1);
        gridPane.add(a, 1, 0, 1, 7);

        for (int i = 0; i < stringCount; i++) {
            Line line = new Line(0, i + 1 * 10, 1, i + 1 * 10);
            line.setStroke(Color.LIGHTGRAY);
            gridPane.add(line, 2, i + 1);
        }

        Line b = new Line(0, 0, 0, (stringCount - 1) * 19 - 3);
        b.setStrokeWidth(4);
        gridPane.add(b, 3, 0, 1, 7);
        GridPane.setMargin(b, new Insets(0, 0, 0, 0));
        GridPane.setHalignment(b, HPos.RIGHT);

        GridPane circleGrid = new GridPane();
        circleGrid.setAlignment(Pos.CENTER);
        // circleGrid.setStyle("-fx-border-color: red;");

        Circle firstCircle = new Circle(0, 0, 3);
        circleGrid.add(firstCircle, 0, 0);
        GridPane.setVgrow(firstCircle, Priority.ALWAYS);
        GridPane.setValignment(firstCircle, VPos.CENTER);
        GridPane.setMargin(firstCircle, new Insets(8, 0, 0, 0));

        Circle secondCircle = new Circle(0, 0, 3);
        circleGrid.add(secondCircle, 0, 1);
        GridPane.setVgrow(secondCircle, Priority.ALWAYS);
        GridPane.setValignment(secondCircle, VPos.CENTER);
        GridPane.setMargin(secondCircle, new Insets(0, 0, 8, 0));

        gridPane.add(circleGrid, 0, 0, 3, 7);
        // GridPane.setMargin(circleGrid, new Insets(0, 0, 0, 3));

        return gridPane;
    }

    public static void main(String[] args) {
        MeasureRepeatCloseTest.launch(MeasureRepeatCloseTest.class, args);
    }
}
