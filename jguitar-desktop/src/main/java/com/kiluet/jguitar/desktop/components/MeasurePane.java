package com.kiluet.jguitar.desktop.components;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.kiluet.jguitar.dao.model.Beat;
import com.kiluet.jguitar.dao.model.InstrumentString;
import com.kiluet.jguitar.dao.model.Measure;
import com.kiluet.jguitar.dao.model.Note;
import com.kiluet.jguitar.desktop.JGuitarController;
import com.kiluet.jguitar.util.MIDINumber2NoteConverter;

import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.text.Text;

public class MeasurePane extends GridPane {

    private static final Logger logger = LoggerFactory.getLogger(MeasurePane.class);

    private Measure measure;

    private JGuitarController jguitarController;

    public MeasurePane(JGuitarController jguitarController, Measure measure) {
        super();
        this.jguitarController = jguitarController;
        this.measure = measure;
        init();
    }

    public void init() {
        setId(String.format("MeasurePane_%d", measure.getId()));

        // setStyle("-fx-border-color: red; -fx-padding: 10 0 10 0;");
        setStyle("-fx-padding: 15 0 15 0;");
        // setAlignment(Pos.BASELINE_CENTER);

        // ColumnConstraints columnConstraints = new ColumnConstraints();
        // columnConstraints.setFillWidth(true);
        // columnConstraints.setHgrow(Priority.ALWAYS);
        // getColumnConstraints().add(columnConstraints);

        Text measureIndexText = new Text(measure.getNumber().toString());
        measureIndexText.setStyle("-fx-font-size: 8; -fx-fill: red; -fx-padding: 0;");
        add(measureIndexText, 0, 0, 2, 1);
        GridPane.setHalignment(measureIndexText, HPos.LEFT);

        if (measure.getNumber() == 1) {
            GridPane.setMargin(measureIndexText, new Insets(0, 0, 0, 15));
        }

        int columnOffset = 1;

        List<InstrumentString> instrumentStrings = measure.getTrack().getInstrument().getStrings();

        if (measure.getNumber() == 1) {
            for (InstrumentString is : instrumentStrings) {
                Text note = new Text(MIDINumber2NoteConverter.getNote(is.getPitch()));
                note.setStyle("-fx-font-size: 10;");
                add(note, 0, is.getString());
                GridPane.setMargin(note, new Insets(0, 5, 0, 0));
            }
        }

        for (Beat beat : measure.getBeats()) {
            for (InstrumentString instrumentString : instrumentStrings) {

                StackPane stackPane = new StackPane();
                stackPane.setId(String.format("StackPane_%d_%d_%d", measure.getNumber(), beat.getNumber(),
                        instrumentString.getString()));

                // -fx-border-color: red;
                stackPane.setMinHeight(20);
                stackPane.setMaxHeight(20);
                stackPane.setStyle("-fx-padding: 0;");

                Line line = new Line(0, instrumentString.getString() * 10, 40, instrumentString.getString() * 10);
                line.setStroke(Color.web("lightgray"));

                NoteTextField noteTextField = new NoteTextField();
                noteTextField.setId(String.format("NoteTextField_%d_%d_%d", measure.getNumber(), beat.getNumber(),
                        instrumentString.getString()));

                stackPane.getChildren().addAll(line, noteTextField);

                for (Note note : beat.getNotes()) {

                    if (note.getString() == instrumentString.getString()) {
                        noteTextField = (NoteTextField) stackPane.getChildren().get(1);
                        noteTextField.setText(note.getValue().toString());
                        columnOffset++;
                    }

                }

                add(stackPane, beat.getNumber(), instrumentString.getString());

            }

            switch (beat.getDuration()) {
                case WHOLE:
                    jguitarController.getWholeDurationButton().setSelected(true);
                    break;
                case HALF:
                    jguitarController.getHalfDurationButton().setSelected(true);
                    break;
                case QUARTER:
                    jguitarController.getQuarterDurationButton().setSelected(true);
                    QuarterNoteSymbol quarterNoteSymbol = new QuarterNoteSymbol();
                    add(quarterNoteSymbol, beat.getNumber(), instrumentStrings.size() + 1);
                    GridPane.setMargin(quarterNoteSymbol, new Insets(10, 0, 0, 0));
                    GridPane.setHalignment(quarterNoteSymbol, HPos.CENTER);
                    break;
                case EIGHTH:
                    jguitarController.getEighthDurationButton().setSelected(true);
                    EighthNoteSymbol eighthNoteSymbol = new EighthNoteSymbol();
                    add(eighthNoteSymbol, beat.getNumber(), instrumentStrings.size() + 1);
                    GridPane.setMargin(eighthNoteSymbol, new Insets(10, 0, 0, 0));
                    GridPane.setHalignment(eighthNoteSymbol, HPos.CENTER);
                    break;
                case SIXTEENTH:
                    jguitarController.getSixteenthDurationButton().setSelected(true);
                    SixteenthNoteSymbol sixteenthNoteSymbol = new SixteenthNoteSymbol();
                    add(sixteenthNoteSymbol, beat.getNumber(), instrumentStrings.size() + 1);
                    GridPane.setMargin(sixteenthNoteSymbol, new Insets(10, 0, 0, 0));
                    GridPane.setHalignment(sixteenthNoteSymbol, HPos.CENTER);
                    break;
                case THIRTY_SECOND:
                    jguitarController.getThritySecondDurationButton().setSelected(true);
                    ThirtySecondNoteSymbol thirtySecondNoteSymbol = new ThirtySecondNoteSymbol();
                    add(thirtySecondNoteSymbol, beat.getNumber(), instrumentStrings.size() + 1);
                    GridPane.setMargin(thirtySecondNoteSymbol, new Insets(10, 0, 0, 0));
                    GridPane.setHalignment(thirtySecondNoteSymbol, HPos.CENTER);
                    break;
                case SIXTY_FOURTH:
                    jguitarController.getSixtyFouthDurationButton().setSelected(true);
                    SixtyFourthNoteSymbol sixtyFourthNoteSymbol = new SixtyFourthNoteSymbol();
                    add(sixtyFourthNoteSymbol, beat.getNumber(), instrumentStrings.size() + 1);
                    GridPane.setMargin(sixtyFourthNoteSymbol, new Insets(10, 0, 0, 0));
                    GridPane.setHalignment(sixtyFourthNoteSymbol, HPos.CENTER);
                    break;
                default:
                    jguitarController.getWholeDurationButton().setSelected(true);
                    break;
            }

        }

        if (measure.getNumber() == 1) {

            Line line = new Line(0, 0, 0, (instrumentStrings.size() - 1) * 20 - 3);
            line.setStrokeWidth(4);
            add(line, 1, 1, 1, instrumentStrings.size());

            line = new Line(0, 0, 0, (instrumentStrings.size() - 1) * 20);
            line.setStrokeWidth(1);
            add(line, 1, 1, 1, instrumentStrings.size());
            GridPane.setMargin(line, new Insets(0, 0, 0, 5));

        } else {

            Line line = new Line(0, 0, 0, (instrumentStrings.size() - 1) * 20);
            line.setStrokeWidth(1);
            add(line, 1, 1, 1, instrumentStrings.size());

        }

        if (measure.getNumber() == measure.getTrack().getMeasures().size()) {

            Line line = new Line(0, 0, 0, (instrumentStrings.size() - 1) * 20);
            line.setStrokeWidth(1);
            add(line, columnOffset + 1, 1, 1, instrumentStrings.size());
            GridPane.setMargin(line, new Insets(0, 1, 0, 0));

            line = new Line(0, 0, 0, (instrumentStrings.size() - 1) * 20 - 3);
            line.setStrokeWidth(4);
            add(line, columnOffset + 2, 1, 1, instrumentStrings.size());

        }
    }

    public Measure getMeasure() {
        return measure;
    }

    public void setMeasure(Measure measure) {
        this.measure = measure;
    }

}
