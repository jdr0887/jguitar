package com.kiluet.jguitar.desktop;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.VPos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.shape.Ellipse;
import javafx.scene.shape.Line;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.transform.Rotate;
import javafx.stage.Stage;

public class TempoTest extends Application {

    @Override
    public void start(Stage primaryStage) {
        BorderPane pane = new BorderPane();
        pane.setPadding(new Insets(20, 20, 20, 20));

        for (String name : Font.getFontNames()) {
            System.out.println(name);
        }
        
        GridPane gridPane = new GridPane();
        Group group = new Group();
        Line line = new Line(1.35, 9, 1.35, -10);
        line.setStrokeWidth(.75);
        group.getChildren().add(line);
        Ellipse ellipse = new Ellipse(6, 6, 2.75, 5);
        ellipse.getTransforms().add(new Rotate(65));
        group.getChildren().add(ellipse);

        gridPane.add(group, 0, 0);
        Label label = new Label(String.format(" = %d", 120));
        label.setFont(Font.font(java.awt.Font.DIALOG_INPUT, FontWeight.THIN, 45));
        label.setStyle("-fx-font-size: 11;");

        gridPane.add(label, 1, 0);
        GridPane.setValignment(label, VPos.BOTTOM);

        pane.setCenter(gridPane);

        Scene scene = new Scene(pane);
        primaryStage.setScene(scene);
        primaryStage.setWidth(100);
        primaryStage.setHeight(100);
        primaryStage.show();
    }

    public static void main(String[] args) {
        TempoTest.launch(TempoTest.class, args);
    }
}
