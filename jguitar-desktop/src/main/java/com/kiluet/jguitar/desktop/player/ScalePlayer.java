package com.kiluet.jguitar.desktop.player;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.kiluet.jguitar.dao.model.Beat;
import com.kiluet.jguitar.dao.model.InstrumentString;
import com.kiluet.jguitar.dao.model.Measure;
import com.kiluet.jguitar.dao.model.Scale;
import com.kiluet.jguitar.desktop.JGuitarController;
import com.kiluet.jguitar.desktop.components.MeasurePane;
import com.kiluet.jguitar.desktop.components.NoteTextField;
import com.kiluet.jguitar.desktop.components.TrackPane;

public class ScalePlayer {

    private static final Logger logger = LoggerFactory.getLogger(ScalePlayer.class);

    private final List<Beat> beatList = new LinkedList<Beat>();

    private JGuitarController jGuitarController;

    private ScheduledExecutorService es;

    private Beat previousBeat;

    private Beat currentBeat;

    private Iterator<Beat> beatIter;

    private SongPlayerState songPlayerState = SongPlayerState.PAUSED;

    public ScalePlayer(JGuitarController jGuitarController, Scale scale) {
        super();
        this.jGuitarController = jGuitarController;
        for (Measure measure : scale.getTrack().getMeasures()) {
            for (Beat beat : measure.getBeats()) {
                this.beatList.add(beat);
            }
        }
        this.beatIter = beatList.iterator();
        currentBeat = beatIter.next();
    }

    public void play() {
        logger.info("ENTERING play()");
        this.es = Executors.newSingleThreadScheduledExecutor();

        if (!beatIter.hasNext()) {
            this.beatIter = this.beatList.iterator();
        }

        this.songPlayerState = SongPlayerState.PLAYING;
        es.scheduleAtFixedRate(new Runnable() {

            @Override
            public void run() {
                logger.info("ENTERING run()");
                try {

                    if (previousBeat != null) {

                        TrackPane trackPane = (TrackPane) jGuitarController.getNotationBox().getChildren().get(0)
                                .lookup(String.format("#TrackPane_%d", previousBeat.getMeasure().getTrack().getId()));
                        MeasurePane measurePane = (MeasurePane) trackPane
                                .lookup(String.format("#MeasurePane_%d", previousBeat.getMeasure().getId()));
                        List<InstrumentString> instrumentStrings = previousBeat.getMeasure().getTrack().getInstrument()
                                .getStrings();
                        for (InstrumentString instrumentString : instrumentStrings) {
                            NoteTextField noteTextField = (NoteTextField) measurePane.lookup(String.format(
                                    "#NoteTextField_%d_%d_%d_%d", previousBeat.getMeasure().getTrack().getId(),
                                    previousBeat.getMeasure().getNumber(), previousBeat.getNumber(),
                                    instrumentString.getString()));
                            if (noteTextField != null && StringUtils.isNotEmpty(noteTextField.getText())) {
                                noteTextField.colorBlack();
                            }
                        }

                    }

                    TrackPane trackPane = (TrackPane) jGuitarController.getNotationBox().getChildren().get(0)
                            .lookup(String.format("#TrackPane_%d", currentBeat.getMeasure().getTrack().getId()));
                    MeasurePane measurePane = (MeasurePane) trackPane
                            .lookup(String.format("#MeasurePane_%d", currentBeat.getMeasure().getId()));
                    List<InstrumentString> instrumentStrings = currentBeat.getMeasure().getTrack().getInstrument()
                            .getStrings();
                    for (InstrumentString instrumentString : instrumentStrings) {
                        NoteTextField noteTextField = (NoteTextField) measurePane.lookup(
                                String.format("#NoteTextField_%d_%d_%d_%d", currentBeat.getMeasure().getTrack().getId(),
                                        currentBeat.getMeasure().getNumber(), currentBeat.getNumber(),
                                        instrumentString.getString()));
                        if (noteTextField != null && StringUtils.isNotEmpty(noteTextField.getText())) {
                            noteTextField.colorRed();
                        }
                    }

                    if (!beatIter.hasNext()) {
                        pause();
                        return;
                    }

                    previousBeat = currentBeat;
                    currentBeat = beatIter.next();

                } catch (Exception e) {
                    e.printStackTrace();
                }

            }

        }, 0, 1, TimeUnit.SECONDS);
    }

    public void pause() {
        logger.info("ENTERING pause()");
        this.songPlayerState = SongPlayerState.PAUSED;
        this.es.shutdownNow();
    }

    public boolean isPlaying() {
        return this.songPlayerState == SongPlayerState.PLAYING;
    }

    public boolean isPaused() {
        return this.songPlayerState == SongPlayerState.PAUSED;
    }

}
