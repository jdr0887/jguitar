package com.kiluet.jguitar.desktop;

import java.util.List;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.RowConstraints;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.text.Text;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.kiluet.jguitar.model.Beat;
import com.kiluet.jguitar.model.Note;

public class MeasurePane extends BorderPane {

    private final Logger logger = LoggerFactory.getLogger(MeasurePane.class);

    private Integer stringCount;

    private Integer measureIndex;

    private Integer measureTotal;

    private List<Beat> beats;

    private JGuitarController jguitarController;

    public MeasurePane(JGuitarController jguitarController, Integer stringCount, Integer measureIndex,
            Integer measureTotal, List<Beat> beats) {
        super();
        this.jguitarController = jguitarController;
        this.stringCount = stringCount;
        this.measureIndex = measureIndex;
        this.measureTotal = measureTotal;
        this.beats = beats;
        init();
    }

    private void init() {
        setStyle("-fx-padding: 10 0 10 0;");

        Text measureIndexText = new Text(measureIndex.toString());
        measureIndexText.setStroke(Color.web("red"));
        measureIndexText.setStyle("-fx-font-size: 7; -fx-padding: 0;");
        setTop(measureIndexText);
        setAlignment(measureIndexText, Pos.CENTER_LEFT);

        GridPane gridPane = new GridPane();
        // gridPane.setStyle("-fx-border-color: red;");

        RowConstraints rowConstraints = new RowConstraints();
        rowConstraints.setMinHeight(20);
        rowConstraints.setVgrow(Priority.ALWAYS);

        int columnOffset = 0;
        for (int i = 0; i < beats.size(); i++) {

            Beat beat = beats.get(i);
            switch (beat.getType()) {
                case WHOLE:
                    jguitarController.getWholeDurationButton().setSelected(true);
                    break;
                case HALF:
                    jguitarController.getHalfDurationButton().setSelected(true);
                    break;
                case QUARTER:
                    jguitarController.getQuarterDurationButton().setSelected(true);
                    break;
                case EIGHTH:
                    jguitarController.getEighthDurationButton().setSelected(true);
                    break;
                case SIXTEENTH:
                    jguitarController.getSixteenthDurationButton().setSelected(true);
                    break;
                case THIRTY_SECOND:
                    jguitarController.getThritySecondDurationButton().setSelected(true);
                    break;
                case SIXTY_FOURTH:
                    jguitarController.getSixtyFouthDurationButton().setSelected(true);
                    break;
                default:
                    jguitarController.getWholeDurationButton().setSelected(true);
                    break;
            }

            for (int j = 1; j <= stringCount; j++) {

                StackPane stackPane = new StackPane();
                // stackPane.setStyle("-fx-border-color: red;");

                Line line = new Line(0, j * 10, 40, j * 10);
                line.setStroke(Color.web("lightgray"));

                TextField noteTextField = new TextField();
                noteTextField
                        .setStyle("-fx-background-color: transparent; -fx-background-insets: 0; -fx-background-radius: 0; -fx-padding: 0; -fx-pref-column-count: 2; -fx-max-height: 20; -fx-max-width: 20; -fx-font-size: 10;");
                stackPane.getChildren().addAll(line, noteTextField);

                for (Note note : beat.getNotes()) {

                    if (note.getString() == j) {

                        noteTextField = (TextField) stackPane.getChildren().get(1);
                        noteTextField.setText(note.getValue().toString());
                        columnOffset++;
                    }

                }

                gridPane.add(stackPane, i + 1, j - 1);
                gridPane.getRowConstraints().add(j - 1, rowConstraints);

            }
        }

        if (measureIndex == 1) {

            Line line = new Line(0, 0, 0, (stringCount - 1) * 20 - 3);
            line.setStrokeWidth(4);
            gridPane.add(line, 0, 0, 1, stringCount);

            line = new Line(0, 0, 0, (stringCount - 1) * 20);
            line.setStrokeWidth(1);
            gridPane.add(line, 1, 0, 1, stringCount);
            GridPane.setMargin(line, new Insets(0, 0, 0, 1));

        } else {

            Line line = new Line(0, 0, 0, (stringCount - 1) * 20);
            line.setStrokeWidth(1);
            gridPane.add(line, 0, 0, 1, stringCount);

        }

        if (measureIndex == measureTotal) {

            Line line = new Line(0, 0, 0, (stringCount - 1) * 20);
            line.setStrokeWidth(1);
            gridPane.add(line, columnOffset + 1, 0, 1, stringCount);
            GridPane.setMargin(line, new Insets(0, 1, 0, 0));

            line = new Line(0, 0, 0, (stringCount - 1) * 20 - 3);
            line.setStrokeWidth(4);
            gridPane.add(line, columnOffset + 2, 0, 1, stringCount);

        }

        setCenter(gridPane);

    }
}
