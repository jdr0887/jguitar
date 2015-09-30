package com.kiluet.jguitar.desktop.components;

import java.util.Collections;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.kiluet.jguitar.dao.JGuitarDAOManager;
import com.kiluet.jguitar.dao.model.Measure;
import com.kiluet.jguitar.dao.model.Track;
import com.kiluet.jguitar.desktop.JGuitarController;

import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.scene.layout.GridPane;

public class TrackPane extends GridPane {

    private static final Logger logger = LoggerFactory.getLogger(TrackPane.class);

    private static final JGuitarDAOManager daoMgr = JGuitarDAOManager.getInstance();

    private JGuitarController jguitarController;

    private Track track;

    public TrackPane(JGuitarController jguitarController, Track track) {
        super();
        this.jguitarController = jguitarController;
        this.track = track;
        setId(String.format("TrackPane_%d", track.getId()));
        init();
    }

    public void init() {

        // setStyle("-fx-border-color: red; -fx-padding: 14 0 14 0;");
        setStyle("-fx-padding: 14 0 14 0;");

        Measure previousMeasure = null;

        Collections.sort(track.getMeasures(), (m1, m2) -> m1.getNumber().compareTo(m2.getNumber()));

        for (Measure measure : track.getMeasures()) {

            logger.info(measure.toString());

            if (measure.getNumber() == 1 && measure.getTrack().getNumber() == 1) {
                TempoSymbol tempoSymbol = new TempoSymbol(measure.getTempo());
                add(tempoSymbol, 0, 0, track.getMeasures().size(), 1);
                GridPane.setHalignment(tempoSymbol, HPos.LEFT);
                GridPane.setMargin(tempoSymbol, new Insets(0, 0, 5, 0));
            } else if (previousMeasure != null && !previousMeasure.getTempo().equals(measure.getTempo())) {
                TempoSymbol tempoSymbol = new TempoSymbol(measure.getTempo());
                add(tempoSymbol, 0, 0, track.getMeasures().size(), 1);
                GridPane.setHalignment(tempoSymbol, HPos.LEFT);
                GridPane.setMargin(tempoSymbol, new Insets(0, 0, 5, 0));
            }

            MeasureHeaderPane measureHeaderPane = new MeasureHeaderPane(measure);
            add(measureHeaderPane, measure.getNumber(), 1);

            MeasurePane measurePane = new MeasurePane(jguitarController, measure);
            add(measurePane, measure.getNumber(), 2);

            // MeasureFooterPane measureFooterPane = new MeasureFooterPane(jguitarController, measure);
            // add(measureFooterPane, measure.getNumber(), 3);

            previousMeasure = measure;

        }

    }

    public Track getTrack() {
        return track;
    }

    public void setTrack(Track track) {
        this.track = track;
    }

}