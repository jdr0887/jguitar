package com.kiluet.jguitar.desktop;

import java.util.List;

import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.RowConstraints;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.text.Text;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.kiluet.jguitar.dao.model.Beat;
import com.kiluet.jguitar.dao.model.InstrumentString;
import com.kiluet.jguitar.dao.model.Measure;
import com.kiluet.jguitar.dao.model.Note;
import com.kiluet.jguitar.util.MIDINumber2NoteConverter;

public class MeasurePane extends GridPane {

    private final Logger logger = LoggerFactory.getLogger(MeasurePane.class);

    private Integer measureIndex;

    private Integer measureTotal;

    private Measure measure;

    private JGuitarController jguitarController;

    private List<InstrumentString> instrumentStrings;

    public MeasurePane(JGuitarController jguitarController, Integer measureIndex, Integer measureTotal,
            Measure measure, List<InstrumentString> instrumentStrings) {
        super();
        this.jguitarController = jguitarController;
        this.measureIndex = measureIndex;
        this.measureTotal = measureTotal;
        this.measure = measure;
        this.instrumentStrings = instrumentStrings;
        init();
    }

    private void init() {
        setStyle("-fx-padding: 0;");
        setAlignment(Pos.BASELINE_CENTER);
        setMaxHeight(180);
        setMinHeight(180);

        Text measureIndexText = new Text(measureIndex.toString());
        measureIndexText.setStyle("-fx-font-size: 8; -fx-fill: red; -fx-padding: 0;");
        add(measureIndexText, 0, 0, 2, 1);
        setHalignment(measureIndexText, HPos.LEFT);

        if (measureIndex == 1) {
            setMargin(measureIndexText, new Insets(0, 0, 0, 15));
        }

        int columnOffset = 1;

        if (measureIndex == 1) {
            for (InstrumentString is : instrumentStrings) {
                Text note = new Text(MIDINumber2NoteConverter.getNote(is.getPitch()));
                note.setStyle("-fx-font-size: 9;");
                add(note, 0, is.getString());
                GridPane.setMargin(note, new Insets(0, 5, 0, 0));
            }
        }

        for (int i = 0; i < measure.getBeats().size(); i++) {

            Beat beat = measure.getBeats().get(i);

            for (int j = 0; j < instrumentStrings.size(); j++) {

                StackPane stackPane = new StackPane();
                // -fx-border-color: red;
                stackPane.setStyle("-fx-padding: 0;");

                Line line = new Line(0, j + 1 * 10, 40, j + 1 * 10);
                line.setStroke(Color.web("lightgray"));

                NoteTextField noteTextField = new NoteTextField();
                noteTextField
                        .setStyle("-fx-alignment: center; -fx-background-color: transparent; -fx-background-insets: 0; -fx-background-radius: 0; -fx-padding: 0; -fx-pref-column-count: 1; -fx-max-height: 20; -fx-max-width: 20; -fx-font-size: 10;");

                stackPane.getChildren().addAll(line, noteTextField);

                for (Note note : beat.getNotes()) {

                    if (note.getString() == j + 1) {
                        noteTextField = (NoteTextField) stackPane.getChildren().get(1);
                        noteTextField.setText(note.getValue().toString());
                        columnOffset++;
                    }

                }

                add(stackPane, i + 1, j + 1);
                RowConstraints rowConstraints = new RowConstraints();
                rowConstraints.setMinHeight(20);
                rowConstraints.setMaxHeight(20);
                rowConstraints.setVgrow(Priority.ALWAYS);
                getRowConstraints().add(rowConstraints);

            }

            switch (beat.getDuration()) {
                case WHOLE:
                    jguitarController.getWholeDurationButton().setSelected(true);
                    break;
                case HALF:
                    jguitarController.getHalfDurationButton().setSelected(true);
                    Line line = new Line(0, 0, 0, 15);
                    line.setStrokeWidth(1);
                    add(line, beat.getNumber(), instrumentStrings.size() + 1);
                    setHalignment(line, HPos.CENTER);
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

        }

        if (measureIndex == 1) {

            Line line = new Line(0, 0, 0, (instrumentStrings.size() - 1) * 20 - 3);
            line.setStrokeWidth(4);
            add(line, 1, 1, 1, instrumentStrings.size());

            line = new Line(0, 0, 0, (instrumentStrings.size() - 1) * 20);
            line.setStrokeWidth(1);
            add(line, 1, 1, 1, instrumentStrings.size());
            setMargin(line, new Insets(0, 0, 0, 5));

        } else {

            Line line = new Line(0, 0, 0, (instrumentStrings.size() - 1) * 20);
            line.setStrokeWidth(1);
            add(line, 1, 1, 1, instrumentStrings.size());

        }

        if (measureIndex == measureTotal) {

            Line line = new Line(0, 0, 0, (instrumentStrings.size() - 1) * 20);
            line.setStrokeWidth(1);
            add(line, columnOffset + 1, 1, 1, instrumentStrings.size());
            setMargin(line, new Insets(0, 1, 0, 0));

            line = new Line(0, 0, 0, (instrumentStrings.size() - 1) * 20 - 3);
            line.setStrokeWidth(4);
            add(line, columnOffset + 2, 1, 1, instrumentStrings.size());

        }

    }
}
