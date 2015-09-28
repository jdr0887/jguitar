package com.kiluet.jguitar.desktop.components;

import javafx.scene.Group;
import javafx.scene.shape.Line;

public class ThirtySecondNoteSymbol extends Group {

    public ThirtySecondNoteSymbol() {
        super();
        init();
    }

    public void init() {
        Line line = new Line(0, 0, 0, 15);
        line.setStrokeWidth(.5);
        getChildren().add(line);
        line = new Line(0, 6, 3, 6);
        line.setStrokeWidth(1.5);
        getChildren().add(line);
        line = new Line(0, 9, 3, 9);
        line.setStrokeWidth(1.5);
        getChildren().add(line);
        line = new Line(0, 12, 3, 12);
        line.setStrokeWidth(1.5);
        getChildren().add(line);
        line = new Line(0, 15, 3, 15);
        line.setStrokeWidth(1.5);
        getChildren().add(line);
    }
}
