package com.kiluet.jguitar.desktop.components;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.kiluet.jguitar.dao.model.Beat;
import com.kiluet.jguitar.dao.model.InstrumentString;
import com.kiluet.jguitar.dao.model.Measure;
import com.kiluet.jguitar.dao.model.Track;
import com.kiluet.jguitar.desktop.JGuitarController;

import javafx.geometry.HPos;
import javafx.scene.layout.GridPane;

public class TrackPane extends GridPane {

    private static final Logger logger = LoggerFactory.getLogger(TrackPane.class);

    private JGuitarController jguitarController;

    private Track track;

    public TrackPane(JGuitarController jguitarController, Track track) {
        super();
        this.jguitarController = jguitarController;
        this.track = track;
        init();
    }

    public void init() {
        setId(String.format("TrackPane_%d", track.getId()));

        // setStyle("-fx-border-color: red;");

        Measure previousMeasure = null;
        for (Measure measure : track.getMeasures()) {

            if (measure.getNumber() == 1 && track.getNumber() == 1) {
                TempoSymbol tempoSymbol = new TempoSymbol(measure.getTempo());
                add(tempoSymbol, 1, 0);
                GridPane.setHalignment(tempoSymbol, HPos.LEFT);
            } else if (previousMeasure != null && !previousMeasure.getTempo().equals(measure.getTempo())) {
                TempoSymbol tempoSymbol = new TempoSymbol(measure.getTempo());
                add(tempoSymbol, 1, 0);
                GridPane.setHalignment(tempoSymbol, HPos.LEFT);
            }

            logger.info(measure.toString());
            MeasurePane measurePane = new MeasurePane(jguitarController, measure);
            add(measurePane, measure.getNumber(), 1);
            previousMeasure = measure;
        }

        List<InstrumentString> instrumentStrings = track.getInstrument().getStrings();

        String noteTextFieldLookupFormat = "#NoteTextField_%d_%d_%d_%d";
        // navigation
        for (Measure measure : track.getMeasures()) {

            for (Beat beat : measure.getBeats()) {

                for (InstrumentString instrumentString : instrumentStrings) {

                    String currentNoteTextFieldId = String.format(noteTextFieldLookupFormat, track.getId(),
                            measure.getNumber(), beat.getNumber(), instrumentString.getString());

                    NoteTextField noteTextField = (NoteTextField) lookup(currentNoteTextFieldId);

                    noteTextField.setOnKeyReleased(e -> {

                        if (e.getCode().isArrowKey()) {
                            NoteTextField proximalNoteTextField = null;
                            switch (e.getCode()) {
                                case RIGHT:
                                    String rightOfNoteTextFieldId = null;
                                    if (beat.getNumber() == measure.getBeats().size()) {
                                        rightOfNoteTextFieldId = String.format(noteTextFieldLookupFormat, track.getId(),
                                                measure.getNumber() + 1, 1, instrumentString.getString());
                                    } else {
                                        rightOfNoteTextFieldId = String.format(noteTextFieldLookupFormat, track.getId(),
                                                measure.getNumber(), beat.getNumber() + 1,
                                                instrumentString.getString());
                                    }
                                    logger.debug(rightOfNoteTextFieldId);
                                    proximalNoteTextField = (NoteTextField) lookup(rightOfNoteTextFieldId);
                                    break;
                                case LEFT:
                                    String leftOfNoteTextFieldId = null;
                                    if (beat.getNumber() - 1 == 0) {
                                        leftOfNoteTextFieldId = String.format(noteTextFieldLookupFormat, track.getId(),
                                                measure.getNumber() - 1, measure.getBeats().size(),
                                                instrumentString.getString());
                                    } else {
                                        leftOfNoteTextFieldId = String.format(noteTextFieldLookupFormat, track.getId(),
                                                measure.getNumber(), beat.getNumber() - 1,
                                                instrumentString.getString());
                                    }
                                    logger.debug(leftOfNoteTextFieldId);
                                    proximalNoteTextField = (NoteTextField) lookup(leftOfNoteTextFieldId);
                                    break;
                                case UP:
                                    String upOfNoteTextFieldId = String.format(noteTextFieldLookupFormat, track.getId(),
                                            measure.getNumber(), beat.getNumber(), instrumentString.getString() - 1);
                                    logger.debug(upOfNoteTextFieldId);
                                    proximalNoteTextField = (NoteTextField) lookup(upOfNoteTextFieldId);
                                    break;
                                case DOWN:
                                    String downOfNoteTextFieldId = String.format(noteTextFieldLookupFormat,
                                            track.getId(), measure.getNumber(), beat.getNumber(),
                                            instrumentString.getString() + 1);
                                    logger.debug(downOfNoteTextFieldId);
                                    proximalNoteTextField = (NoteTextField) lookup(downOfNoteTextFieldId);
                                    break;
                            }
                            if (proximalNoteTextField != null) {
                                proximalNoteTextField.requestFocus();
                            }

                        }

                    });

                }
            }

        }

    }

    public Track getTrack() {
        return track;
    }

    public void setTrack(Track track) {
        this.track = track;
    }

}