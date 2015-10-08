package com.kiluet.jguitar.desktop.components;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.kiluet.jguitar.dao.JGuitarDAOException;
import com.kiluet.jguitar.dao.JGuitarDAOManager;
import com.kiluet.jguitar.dao.model.Beat;
import com.kiluet.jguitar.dao.model.InstrumentString;
import com.kiluet.jguitar.dao.model.Measure;
import com.kiluet.jguitar.dao.model.Note;
import com.kiluet.jguitar.desktop.JGuitarController;

import javafx.application.Platform;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;

public class MeasurePane extends GridPane {

    private static final Logger logger = LoggerFactory.getLogger(MeasurePane.class);

    private static final JGuitarDAOManager daoMgr = JGuitarDAOManager.getInstance();

    private Measure measure;

    private JGuitarController jguitarController;

    public MeasurePane(JGuitarController jguitarController, Measure measure) {
        super();
        this.jguitarController = jguitarController;
        this.measure = measure;
        setId(String.format("MeasurePane_%d", measure.getId()));
        init();
    }

    public void init() {

        List<InstrumentString> instrumentStrings = measure.getTrack().getInstrument().getStrings();

        add(new MeasureOpenSeparatorPane(measure), 0, 1, 1, instrumentStrings.size() + 1);

        for (Beat beat : measure.getBeats()) {
            for (InstrumentString instrumentString : instrumentStrings) {

                StackPane stackPane = new StackPane();
                stackPane.setId(String.format("StackPane_%d_%d_%d_%d", measure.getTrack().getId(), measure.getNumber(),
                        beat.getNumber(), instrumentString.getString()));

                // -fx-border-color: red;
                stackPane.setMinHeight(18);
                stackPane.setMaxHeight(18);
                stackPane.setStyle("-fx-padding: 0;");

                Line line = new Line(0, instrumentString.getString() * 10, 40, instrumentString.getString() * 10);
                line.setStroke(Color.LIGHTGRAY);

                Note note = null;
                for (Note n : beat.getNotes()) {
                    if (n.getString().equals(instrumentString.getString())) {
                        note = n;
                        break;
                    }
                }

                if (note == null) {
                    try {
                        Note newNote = new Note(beat, instrumentString.getString(), null, 200);
                        newNote.setId(daoMgr.getDaoBean().getNoteDAO().save(newNote));
                        beat.getNotes().add(newNote);
                        note = newNote;
                    } catch (JGuitarDAOException e1) {
                        e1.printStackTrace();
                    }
                }

                final Note finalNote = note;
                NoteTextField noteTextField = new NoteTextField(note);
                noteTextField.setId(String.format("NoteTextField_%d_%d_%d_%d", measure.getTrack().getId(),
                        measure.getNumber(), beat.getNumber(), instrumentString.getString()));
                noteTextField.focusedProperty().addListener(e -> {
                    jguitarController.setSelectedNote(finalNote);
                    switch (beat.getDuration()) {
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
                            jguitarController.getThirtySecondDurationButton().setSelected(true);
                            break;
                        case SIXTY_FOURTH:
                            jguitarController.getSixtyFourthDurationButton().setSelected(true);
                            break;
                    }
                });

                String noteTextFieldLookupFormat = "#NoteTextField_%d_%d_%d_%d";

                noteTextField.setOnKeyReleased(e -> {

                    if (e.getCode().isArrowKey()) {
                        NoteTextField proximalNoteTextField = null;
                        switch (e.getCode()) {
                            case RIGHT:
                                String rightOfNoteTextFieldId = null;
                                if (beat.getNumber() == measure.getBeats().size()) {
                                    rightOfNoteTextFieldId = String.format(noteTextFieldLookupFormat,
                                            measure.getTrack().getId(), measure.getNumber() + 1, 1,
                                            instrumentString.getString());
                                } else {
                                    rightOfNoteTextFieldId = String.format(noteTextFieldLookupFormat,
                                            measure.getTrack().getId(), measure.getNumber(), beat.getNumber() + 1,
                                            instrumentString.getString());
                                }
                                logger.info(rightOfNoteTextFieldId);
                                proximalNoteTextField = (NoteTextField) this.getParent().lookup(rightOfNoteTextFieldId);
                                break;
                            case LEFT:
                                String leftOfNoteTextFieldId = null;
                                if (measure.getNumber() > 1 && beat.getNumber() == 1) {
                                    Measure pMeasure = measure.getTrack().getMeasures().get(measure.getNumber() - 2);
                                    leftOfNoteTextFieldId = String.format(noteTextFieldLookupFormat,
                                            measure.getTrack().getId(), pMeasure.getNumber(),
                                            pMeasure.getBeats().size(), instrumentString.getString());
                                } else {
                                    leftOfNoteTextFieldId = String.format(noteTextFieldLookupFormat,
                                            measure.getTrack().getId(), measure.getNumber(), beat.getNumber() - 1,
                                            instrumentString.getString());
                                }
                                logger.info(leftOfNoteTextFieldId);
                                proximalNoteTextField = (NoteTextField) this.getParent().lookup(leftOfNoteTextFieldId);
                                break;
                            case UP:
                                String upOfNoteTextFieldId = String.format(noteTextFieldLookupFormat,
                                        measure.getTrack().getId(), measure.getNumber(), beat.getNumber(),
                                        instrumentString.getString() - 1);
                                logger.info(upOfNoteTextFieldId);
                                proximalNoteTextField = (NoteTextField) this.getParent().lookup(upOfNoteTextFieldId);
                                break;
                            case DOWN:
                                String downOfNoteTextFieldId = String.format(noteTextFieldLookupFormat,
                                        measure.getTrack().getId(), measure.getNumber(), beat.getNumber(),
                                        instrumentString.getString() + 1);
                                logger.info(downOfNoteTextFieldId);
                                proximalNoteTextField = (NoteTextField) this.getParent().lookup(downOfNoteTextFieldId);
                                break;
                        }
                        if (proximalNoteTextField != null) {
                            proximalNoteTextField.requestFocus();
                        }

                    }

                });

                stackPane.getChildren().addAll(line, noteTextField);

                if (note.getString() == instrumentString.getString()) {
                    noteTextField = (NoteTextField) stackPane.getChildren().get(1);
                    if (note.getValue() != null) {
                        noteTextField.setText(note.getValue().toString());
                        if (beat.getNumber() == 1 && measure.getNumber() == 1
                                && beat.getMeasure().getTrack().getNumber() == 1) {
                            Platform.runLater(() -> stackPane.getChildren().get(1).requestFocus());
                        }
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
                    jguitarController.getThirtySecondDurationButton().setSelected(true);
                    ThirtySecondNoteSymbol thirtySecondNoteSymbol = new ThirtySecondNoteSymbol();
                    add(thirtySecondNoteSymbol, beat.getNumber(), instrumentStrings.size() + 1);
                    GridPane.setMargin(thirtySecondNoteSymbol, new Insets(10, 0, 0, 0));
                    GridPane.setHalignment(thirtySecondNoteSymbol, HPos.CENTER);
                    break;
                case SIXTY_FOURTH:
                    jguitarController.getSixtyFourthDurationButton().setSelected(true);
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

        add(new MeasureCloseSeparatorPane(measure), measure.getBeats().size() + 1, 1, 1, instrumentStrings.size() + 1);

    }

    public Measure getMeasure() {
        return measure;
    }

    public void setMeasure(Measure measure) {
        this.measure = measure;
    }

}
