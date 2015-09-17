package com.kiluet.jguitar.desktop.components;

import javafx.scene.Group;
import javafx.scene.shape.Line;

public class QuarterNoteSymbol extends Group {

    public QuarterNoteSymbol() {
        super();
        init();
    }

    private void init() {
        Line line = new Line(0, 0, 0, 15);
        line.setStrokeWidth(1);
        getChildren().add(line);
    }
}
