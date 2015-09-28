package com.kiluet.jguitar.desktop.components;

import java.util.List;

import com.kiluet.jguitar.dao.model.InstrumentString;
import com.kiluet.jguitar.dao.model.Measure;

import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;

public class MeasureCloseSeparatorPane extends GridPane {

    private Measure measure;

    public MeasureCloseSeparatorPane(Measure measure) {
        super();
        this.measure = measure;
        setId(String.format("MeasureCloseSeparatorPane_%d", measure.getId()));
        init();
    }

    public void init() {

        List<InstrumentString> instrumentStrings = measure.getTrack().getInstrument().getStrings();

        if (measure.getNumber().equals(measure.getTrack().getMeasures().size())) {
            int stringCount = instrumentStrings.size();

            for (int i = 0; i < stringCount; i++) {

                StackPane stackPane = new StackPane();
                // -fx-border-color: red;
                stackPane.setMinHeight(18);
                stackPane.setMaxHeight(18);
                stackPane.setStyle("-fx-padding: 0;");

                Line line = new Line(0, i + 1 * 10, 10, i + 1 * 10);
                line.setStroke(Color.LIGHTGRAY);

                stackPane.getChildren().addAll(line);

                add(stackPane, 0, i + 1);

            }

            Line a = new Line(0, 0, 0, (stringCount - 1) * 18);
            a.setStrokeWidth(1);
            add(a, 1, 0, 1, 7);

            for (int i = 0; i < stringCount; i++) {
                Line line = new Line(0, i + 1 * 10, 1, i + 1 * 10);
                line.setStroke(Color.LIGHTGRAY);
                add(line, 2, i + 1);
            }

            Line b = new Line(0, 0, 0, (stringCount - 1) * 18 - 3);
            b.setStrokeWidth(4);
            add(b, 3, 0, 1, 7);

        } else {

            if (measure.getCloseRepeat() != null) {

                int stringCount = instrumentStrings.size();

                for (int i = 0; i < stringCount; i++) {

                    StackPane stackPane = new StackPane();
                    // -fx-border-color: red;
                    stackPane.setMinHeight(18);
                    stackPane.setMaxHeight(18);
                    stackPane.setStyle("-fx-padding: 0;");

                    Line line = new Line(0, i + 1 * 10, 15, i + 1 * 10);
                    line.setStroke(Color.LIGHTGRAY);

                    stackPane.getChildren().addAll(line);

                    add(stackPane, 0, i + 1);

                }

                Line a = new Line(0, 0, 0, (stringCount - 1) * 18);
                a.setStrokeWidth(1);
                add(a, 1, 0, 1, 7);

                for (int i = 0; i < stringCount; i++) {
                    Line line = new Line(0, i + 1 * 10, 1, i + 1 * 10);
                    line.setStroke(Color.LIGHTGRAY);
                    add(line, 2, i + 1);
                }

                Line b = new Line(0, 0, 0, (stringCount - 1) * 18 - 2);
                b.setStrokeWidth(3);
                add(b, 3, 0, 1, stringCount + 1);
                GridPane.setMargin(b, new Insets(0, 0, 0, 0));
                GridPane.setHalignment(b, HPos.RIGHT);

                GridPane circleGrid = new GridPane();
                circleGrid.setAlignment(Pos.CENTER);
                // circleGrid.setStyle("-fx-border-color: red;");

                Circle firstCircle = new Circle(0, 0, 2.4);
                circleGrid.add(firstCircle, 0, 0);
                GridPane.setVgrow(firstCircle, Priority.ALWAYS);
                GridPane.setValignment(firstCircle, VPos.CENTER);
                GridPane.setMargin(firstCircle, new Insets(4, 0, 0, 0));

                Circle secondCircle = new Circle(0, 0, 2.4);
                circleGrid.add(secondCircle, 0, 1);
                GridPane.setVgrow(secondCircle, Priority.ALWAYS);
                GridPane.setValignment(secondCircle, VPos.CENTER);
                GridPane.setMargin(secondCircle, new Insets(0, 0, 4, 0));

                add(circleGrid, 0, 0, 3, stringCount + 1);

            }

        }

    }

}
