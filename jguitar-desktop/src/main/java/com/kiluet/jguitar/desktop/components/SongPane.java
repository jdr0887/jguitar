package com.kiluet.jguitar.desktop.components;

import com.kiluet.jguitar.dao.model.Track;
import com.kiluet.jguitar.desktop.JGuitarController;

import javafx.geometry.HPos;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;

public class SongPane extends GridPane {

    private JGuitarController jguitarController;

    public SongPane(JGuitarController jguitarController) {
        super();
        this.jguitarController = jguitarController;
        init();
    }

    public void init() {
        // setStyle("-fx-border-color: red;");

        ColumnConstraints columnConstraints = new ColumnConstraints();
        columnConstraints.setFillWidth(true);
        columnConstraints.setHgrow(Priority.ALWAYS);
        getColumnConstraints().add(columnConstraints);

        int row = 0;
        for (Track track : jguitarController.getSong().getTracks()) {
            TrackPane trackPane = new TrackPane(jguitarController, track);
            add(trackPane, 0, row);
            GridPane.setHalignment(trackPane, HPos.LEFT);
            row++;
        }
    }

}
