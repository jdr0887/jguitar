package com.kiluet.jguitar.desktop.components;

import java.util.List;

import com.kiluet.jguitar.dao.model.Beat;
import com.kiluet.jguitar.dao.model.InstrumentString;
import com.kiluet.jguitar.dao.model.Measure;
import com.kiluet.jguitar.desktop.JGuitarController;

import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.scene.layout.GridPane;

public class MeasureFooterPane extends GridPane {

    private Measure measure;

    private JGuitarController jguitarController;

    public MeasureFooterPane(JGuitarController jguitarController, Measure measure) {
        super();
        this.measure = measure;
        this.jguitarController = jguitarController;
        init();
    }

    public void init() {

        List<InstrumentString> instrumentStrings = measure.getTrack().getInstrument().getStrings();

        for (Beat beat : measure.getBeats()) {

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
                    add(quarterNoteSymbol, beat.getNumber(), 0);
                    GridPane.setMargin(quarterNoteSymbol, new Insets(10, 0, 0, 0));
                    if (measure.getNumber() == 1) {
                        GridPane.setMargin(quarterNoteSymbol, new Insets(10, 0, 0, 40));
                    } 
                    GridPane.setHalignment(quarterNoteSymbol, HPos.CENTER);
                    break;
                case EIGHTH:
                    jguitarController.getEighthDurationButton().setSelected(true);
                    EighthNoteSymbol eighthNoteSymbol = new EighthNoteSymbol();
                    add(eighthNoteSymbol, beat.getNumber(), 0);
                    GridPane.setMargin(eighthNoteSymbol, new Insets(10, 0, 0, 0));
                    if (measure.getNumber() == 1) {
                        GridPane.setMargin(eighthNoteSymbol, new Insets(10, 0, 0, 40));
                    } 
                    GridPane.setHalignment(eighthNoteSymbol, HPos.CENTER);
                    break;
                case SIXTEENTH:
                    jguitarController.getSixteenthDurationButton().setSelected(true);
                    SixteenthNoteSymbol sixteenthNoteSymbol = new SixteenthNoteSymbol();
                    add(sixteenthNoteSymbol, beat.getNumber(), 0);
                    if (measure.getNumber() == 1) {
                        GridPane.setMargin(sixteenthNoteSymbol, new Insets(10, 0, 0, 40));
                    } else {
                        GridPane.setMargin(sixteenthNoteSymbol, new Insets(10, 0, 0, 0));
                    }
                    GridPane.setHalignment(sixteenthNoteSymbol, HPos.CENTER);
                    break;
                case THIRTY_SECOND:
                    jguitarController.getThritySecondDurationButton().setSelected(true);
                    ThirtySecondNoteSymbol thirtySecondNoteSymbol = new ThirtySecondNoteSymbol();
                    add(thirtySecondNoteSymbol, beat.getNumber(), 0);
                    if (measure.getNumber() == 1) {
                        GridPane.setMargin(thirtySecondNoteSymbol, new Insets(10, 0, 0, 40));
                    } else {
                        GridPane.setMargin(thirtySecondNoteSymbol, new Insets(10, 0, 0, 0));
                    }
                    GridPane.setHalignment(thirtySecondNoteSymbol, HPos.CENTER);
                    break;
                case SIXTY_FOURTH:
                    jguitarController.getSixtyFouthDurationButton().setSelected(true);
                    SixtyFourthNoteSymbol sixtyFourthNoteSymbol = new SixtyFourthNoteSymbol();
                    add(sixtyFourthNoteSymbol, beat.getNumber(), 0);
                    if (measure.getNumber() == 1) {
                        GridPane.setMargin(sixtyFourthNoteSymbol, new Insets(10, 0, 0, 40));
                    } else {
                        GridPane.setMargin(sixtyFourthNoteSymbol, new Insets(10, 0, 0, 0));
                    }
                    GridPane.setHalignment(sixtyFourthNoteSymbol, HPos.CENTER);
                    break;
                default:
                    jguitarController.getWholeDurationButton().setSelected(true);
                    break;
            }

        }

    }

}
