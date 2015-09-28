package com.kiluet.jguitar.desktop.components;

import com.kiluet.jguitar.dao.model.Measure;

import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

public class MeasureHeaderPane extends GridPane {

    private Measure measure;

    public MeasureHeaderPane(Measure measure) {
        super();
        this.measure = measure;
        setId(String.format("MeasureHeaderPane_%d", measure.getId()));
        init();
    }

    public void init() {
        // setStyle(" -fx-border-color: red;");

        Text measureIndexText = new Text(measure.getNumber().toString());
        measureIndexText.setFill(Color.RED);
        measureIndexText.setFont(Font.font(java.awt.Font.SANS_SERIF, FontWeight.THIN, 9));
        add(measureIndexText, 0, 0);
        GridPane.setHalignment(measureIndexText, HPos.LEFT);
        GridPane.setMargin(measureIndexText, new Insets(0, 0, 0, 6));
        GridPane.setHgrow(measureIndexText, Priority.ALWAYS);

        if (measure.getCloseRepeat() != null) {
            Text closeRepeatText = new Text(String.format("x%d", measure.getCloseRepeat()));
            closeRepeatText.setFont(Font.font(java.awt.Font.SANS_SERIF, FontWeight.THIN, 9));
            add(closeRepeatText, 1, 0);
            GridPane.setHalignment(closeRepeatText, HPos.RIGHT);
            GridPane.setMargin(closeRepeatText, new Insets(0, 6, 0, 0));
        }

    }

}
