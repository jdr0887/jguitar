package com.kiluet.jguitar.desktop.components;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.kiluet.jguitar.dao.model.Beat;
import com.kiluet.jguitar.dao.model.InstrumentString;
import com.kiluet.jguitar.dao.model.Measure;
import com.kiluet.jguitar.dao.model.Track;
import com.kiluet.jguitar.desktop.JGuitarController;

import javafx.event.EventHandler;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
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

        // ColumnConstraints columnConstraints = new ColumnConstraints();
        // columnConstraints.setFillWidth(true);
        // columnConstraints.setHgrow(Priority.ALWAYS);
        // getColumnConstraints().add(columnConstraints);

        for (int column = 0; column < track.getMeasures().size(); column++) {
            Measure measure = track.getMeasures().get(column);
            MeasurePane measurePane = new MeasurePane(jguitarController, measure);
            add(measurePane, column, 0);
        }

        List<InstrumentString> instrumentStrings = track.getInstrument().getStrings();

        // navigation
        for (Measure measure : track.getMeasures()) {

            for (Beat beat : measure.getBeats()) {

                for (InstrumentString instrumentString : instrumentStrings) {

                    String currentNoteTextFieldId = String.format("#NoteTextField_%d_%d_%d", measure.getNumber(),
                            beat.getNumber(), instrumentString.getString());

                    NoteTextField noteTextField = (NoteTextField) lookup(currentNoteTextFieldId);
                    noteTextField.setOnKeyReleased(new EventHandler<KeyEvent>() {

                        @Override
                        public void handle(KeyEvent event) {
                            if (event.getCode().isArrowKey()) {
                                KeyCode code = event.getCode();
                                NoteTextField noteTextField = null;
                                switch (code) {
                                    case RIGHT:
                                        String rightOfNoteTextFieldId = null;
                                        if (beat.getNumber() == measure.getBeats().size()) {
                                            rightOfNoteTextFieldId = String.format("#NoteTextField_%d_%d_%d",
                                                    measure.getNumber() + 1, 1, instrumentString.getString());
                                        } else {
                                            rightOfNoteTextFieldId = String.format("#NoteTextField_%d_%d_%d",
                                                    measure.getNumber(), beat.getNumber() + 1,
                                                    instrumentString.getString());
                                        }
                                        logger.info(rightOfNoteTextFieldId);
                                        noteTextField = (NoteTextField) lookup(rightOfNoteTextFieldId);
                                        break;
                                    case LEFT:
                                        String leftOfNoteTextFieldId = null;
                                        if (beat.getNumber() - 1 == 0) {
                                            leftOfNoteTextFieldId = String.format("#NoteTextField_%d_%d_%d",
                                                    measure.getNumber() - 1, measure.getBeats().size(),
                                                    instrumentString.getString());
                                        } else {
                                            leftOfNoteTextFieldId = String.format("#NoteTextField_%d_%d_%d",
                                                    measure.getNumber(), beat.getNumber() - 1,
                                                    instrumentString.getString());
                                        }
                                        logger.info(leftOfNoteTextFieldId);
                                        noteTextField = (NoteTextField) lookup(leftOfNoteTextFieldId);
                                        break;
                                    case UP:
                                        String upOfNoteTextFieldId = String.format("#NoteTextField_%d_%d_%d",
                                                measure.getNumber(), beat.getNumber(),
                                                instrumentString.getString() - 1);
                                        logger.info(upOfNoteTextFieldId);
                                        noteTextField = (NoteTextField) lookup(upOfNoteTextFieldId);
                                        break;
                                    case DOWN:
                                        String downOfNoteTextFieldId = String.format("#NoteTextField_%d_%d_%d",
                                                measure.getNumber(), beat.getNumber(),
                                                instrumentString.getString() + 1);
                                        logger.info(downOfNoteTextFieldId);
                                        noteTextField = (NoteTextField) lookup(downOfNoteTextFieldId);
                                        break;
                                }
                                if (noteTextField != null) {
                                    noteTextField.requestFocus();
                                }

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