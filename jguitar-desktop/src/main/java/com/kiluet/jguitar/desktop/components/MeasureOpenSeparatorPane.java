package com.kiluet.jguitar.desktop.components;

import java.util.List;

import com.kiluet.jguitar.dao.model.InstrumentString;
import com.kiluet.jguitar.dao.model.Measure;
import com.kiluet.jguitar.util.MIDINumber2NoteConverter;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

public class MeasureOpenSeparatorPane extends GridPane {

    private Measure measure;

    public MeasureOpenSeparatorPane(Measure measure) {
        super();
        this.measure = measure;
        setId(String.format("MeasureOpenSeparatorPane_%d", measure.getId()));
        init();
    }

    public void init() {

        List<InstrumentString> instrumentStrings = measure.getTrack().getInstrument().getStrings();

        if (measure.getNumber() == 1) {

            for (InstrumentString is : instrumentStrings) {
                Text note = new Text(MIDINumber2NoteConverter.getNote(is.getPitch()));
                note.setStyle("-fx-font-size: 10;");
                add(note, 0, is.getString());
                GridPane.setMargin(note, new Insets(0, 5, 0, 0));
            }

            Line a = new Line(0, 0, 0, (instrumentStrings.size() - 1) * 18 - 3);
            a.setStrokeWidth(4);
            add(a, 1, 0, 1, 7);

            for (int i = 0; i < instrumentStrings.size(); i++) {
                Line line = new Line(0, i + 1 * 10, 1, i + 1 * 10);
                line.setStroke(Color.LIGHTGRAY);
                add(line, 2, i + 1);
            }

            Line b = new Line(0, 0, 0, (instrumentStrings.size() - 1) * 18);
            b.setStrokeWidth(1);
            add(b, 3, 0, 1, 7);

            for (int i = 0; i < instrumentStrings.size(); i++) {
                StackPane stackPane = new StackPane();
                // -fx-border-color: red;
                stackPane.setMinHeight(18);
                stackPane.setMaxHeight(18);
                stackPane.setStyle("-fx-padding: 0;");

                Line line = new Line(0, i + 1 * 10, 36, i + 1 * 10);
                line.setStroke(Color.LIGHTGRAY);

                stackPane.getChildren().addAll(line);

                add(stackPane, 4, i + 1);
            }

            GridPane circleGrid = new GridPane();
            circleGrid.setAlignment(Pos.CENTER);
            // circleGrid.setStyle("-fx-border-color: red;");

            Text numeratorText = new Text(measure.getMeterType().getNumerator().toString());
            numeratorText.setFont(Font.font(java.awt.Font.SANS_SERIF, FontWeight.THIN, 24));
            circleGrid.add(numeratorText, 0, 0);
            GridPane.setVgrow(numeratorText, Priority.ALWAYS);
            GridPane.setValignment(numeratorText, VPos.BOTTOM);
            GridPane.setMargin(numeratorText, new Insets(0, 0, 3, 0));

            Text denominatorText = new Text(measure.getMeterType().getDenominator().toString());
            denominatorText.setFont(Font.font(java.awt.Font.SANS_SERIF, FontWeight.THIN, 24));
            circleGrid.add(denominatorText, 0, 1);
            GridPane.setVgrow(denominatorText, Priority.ALWAYS);
            GridPane.setValignment(denominatorText, VPos.TOP);
            GridPane.setMargin(numeratorText, new Insets(3, 0, 0, 0));

            add(circleGrid, 4, 0, 2, 7);
            GridPane.setMargin(circleGrid, new Insets(0, 0, 0, 3));

        } else {

            if (measure.getOpenRepeat()) {

                Line a = new Line(0, 0, 0, (instrumentStrings.size() - 1) * 18 - 3);
                a.setStrokeWidth(4);
                add(a, 0, 0, 1, instrumentStrings.size() + 1);

                for (int i = 0; i < instrumentStrings.size(); i++) {
                    Line line = new Line(0, i + 1 * 10, 1, i + 1 * 10);
                    line.setStroke(Color.LIGHTGRAY);
                    add(line, 1, i + 1);
                }

                Line b = new Line(0, 0, 0, (instrumentStrings.size() - 1) * 18);
                b.setStrokeWidth(1);
                add(b, 2, 0, 1, instrumentStrings.size() + 1);

                for (int i = 0; i < instrumentStrings.size(); i++) {

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

                GridPane circleGrid = new GridPane();
                circleGrid.setAlignment(Pos.CENTER);
                // circleGrid.setStyle("-fx-border-color: red;");

                Circle firstCircle = new Circle(0, 0, 2.4);
                circleGrid.add(firstCircle, 0, 0);
                GridPane.setVgrow(firstCircle, Priority.ALWAYS);
                GridPane.setValignment(firstCircle, VPos.CENTER);
                GridPane.setMargin(firstCircle, new Insets(4, 0, 0, 5));

                Circle secondCircle = new Circle(0, 0, 2.4);
                circleGrid.add(secondCircle, 0, 1);
                GridPane.setVgrow(secondCircle, Priority.ALWAYS);
                GridPane.setValignment(secondCircle, VPos.CENTER);
                GridPane.setMargin(secondCircle, new Insets(0, 0, 4, 5));

                add(circleGrid, 2, 0, 3, instrumentStrings.size() + 1);

            } else {

                Line b = new Line(0, 0, 0, (instrumentStrings.size() - 1) * 18);
                b.setStrokeWidth(1);
                add(b, 0, 0, 1, instrumentStrings.size() + 1);
                GridPane.setMargin(b, new Insets(9, 0, 0, 0));

            }

        }

    }

}
