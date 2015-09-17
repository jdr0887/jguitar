package com.kiluet.jguitar.desktop.components;

import com.kiluet.jguitar.dao.model.Scale;
import com.kiluet.jguitar.desktop.JGuitarController;

import javafx.geometry.HPos;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;

public class ScalePane extends GridPane {

    private JGuitarController jguitarController;

    private Scale scale;

    public ScalePane(JGuitarController jguitarController, Scale scale) {
        super();
        this.jguitarController = jguitarController;
        this.scale = scale;
        init();
    }

    public void init() {

        ColumnConstraints columnConstraints = new ColumnConstraints();
        columnConstraints.setFillWidth(true);
        columnConstraints.setHgrow(Priority.ALWAYS);
        getColumnConstraints().add(columnConstraints);

        TrackPane trackPane = new TrackPane(jguitarController, scale.getTrack());
        add(trackPane, 0, 0);

        GridPane.setHalignment(trackPane, HPos.LEFT);
    }

}
