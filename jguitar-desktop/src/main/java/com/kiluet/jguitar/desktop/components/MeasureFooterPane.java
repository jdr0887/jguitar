package com.kiluet.jguitar.desktop.components;

import com.kiluet.jguitar.dao.model.Measure;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;

public class MeasureFooterPane extends GridPane {

    private Measure measure;

    public MeasureFooterPane(Measure measure) {
        super();
        this.measure = measure;
        init();
    }

    private void init() {

        int stringCount = measure.getTrack().getInstrument().getStrings().size();

        for (int i = 0; i < stringCount; i++) {

            StackPane stackPane = new StackPane();
            // -fx-border-color: red;
            stackPane.setMinHeight(18);
            stackPane.setMaxHeight(18);
            stackPane.setStyle("-fx-padding: 0;");

            Line line = new Line(0, i + 1 * 10, 15, i + 1 * 10);
            line.setStroke(Color.LIGHTGRAY);

            stackPane.getChildren().addAll(line);

            add(stackPane, 3, i + 1);

        }

        Line line = new Line(0, 0, 0, (stringCount - 1) * 18);
        line.setStrokeWidth(1);
        add(line, 1, 1, 1, stringCount);
        GridPane.setMargin(line, new Insets(0, 1, 0, 0));

        line = new Line(0, 0, 0, (stringCount - 1) * 18 - 3);
        line.setStrokeWidth(4);
        add(line, 2, 1, 1, stringCount);

    }

}
