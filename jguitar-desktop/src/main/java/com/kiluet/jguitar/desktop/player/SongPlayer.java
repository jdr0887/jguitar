package com.kiluet.jguitar.desktop.player;

import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.kiluet.jguitar.dao.model.Beat;
import com.kiluet.jguitar.dao.model.InstrumentString;
import com.kiluet.jguitar.dao.model.Measure;
import com.kiluet.jguitar.dao.model.Song;
import com.kiluet.jguitar.dao.model.Track;
import com.kiluet.jguitar.desktop.JGuitarController;
import com.kiluet.jguitar.desktop.components.MeasurePane;
import com.kiluet.jguitar.desktop.components.NoteTextField;
import com.kiluet.jguitar.desktop.components.TrackPane;

public class SongPlayer {

    private static final Logger logger = LoggerFactory.getLogger(SongPlayer.class);

    private final Map<String, List<Beat>> beatMap = new LinkedHashMap<String, List<Beat>>();

    private JGuitarController jGuitarController;

    private ScheduledExecutorService es;

    private List<Beat> previousBeats;

    private List<Beat> currentBeats;

    private Iterator<List<Beat>> beatIter;

    private Integer measureIndex;

    private SongPlayerState songPlayerState = SongPlayerState.PAUSED;

    public SongPlayer(JGuitarController jGuitarController, Song song) {
        super();
        this.jGuitarController = jGuitarController;
        for (Track track : song.getTracks()) {
            for (Measure measure : track.getMeasures()) {
                for (Beat beat : measure.getBeats()) {
                    String key = String.format("%d_%d", measure.getNumber(), beat.getNumber());
                    if (beatMap.get(key) == null) {
                        beatMap.put(key, new LinkedList<Beat>());
                    }
                    beatMap.get(key).add(beat);
                }
            }
        }
        this.beatIter = beatMap.values().iterator();
        currentBeats = beatIter.next();
    }

    public void play() {
        logger.info("ENTERING play()");
        this.es = Executors.newSingleThreadScheduledExecutor();

        if (!beatIter.hasNext()) {
            this.beatIter = beatMap.values().iterator();
        }

        logger.info("measureIndex = {}", measureIndex);
        if (measureIndex != null) {
            for (int i = 0; i < measureIndex; i++) {
                previousBeats = currentBeats;
                currentBeats = beatIter.next();
            }
        }

        this.songPlayerState = SongPlayerState.PLAYING;
        es.scheduleAtFixedRate(new Runnable() {

            @Override
            public void run() {
                logger.info("ENTERING run()");
                try {

                    if (CollectionUtils.isNotEmpty(previousBeats)) {

                        for (Beat beat : previousBeats) {
                            TrackPane trackPane = (TrackPane) jGuitarController.getNotationBox().getChildren().get(0)
                                    .lookup(String.format("#TrackPane_%d", beat.getMeasure().getTrack().getId()));
                            MeasurePane measurePane = (MeasurePane) trackPane
                                    .lookup(String.format("#MeasurePane_%d", beat.getMeasure().getId()));
                            List<InstrumentString> instrumentStrings = beat.getMeasure().getTrack().getInstrument()
                                    .getStrings();
                            for (InstrumentString instrumentString : instrumentStrings) {
                                NoteTextField noteTextField = (NoteTextField) measurePane.lookup(String.format(
                                        "#NoteTextField_%d_%d_%d_%d", beat.getMeasure().getTrack().getId(),
                                        beat.getMeasure().getNumber(), beat.getNumber(), instrumentString.getString()));
                                if (noteTextField != null && StringUtils.isNotEmpty(noteTextField.getText())) {
                                    noteTextField.colorBlack();
                                }
                            }
                        }

                    }

                    for (Beat beat : currentBeats) {

                        TrackPane trackPane = (TrackPane) jGuitarController.getNotationBox().getChildren().get(0)
                                .lookup(String.format("#TrackPane_%d", beat.getMeasure().getTrack().getId()));
                        MeasurePane measurePane = (MeasurePane) trackPane
                                .lookup(String.format("#MeasurePane_%d", beat.getMeasure().getId()));
                        List<InstrumentString> instrumentStrings = beat.getMeasure().getTrack().getInstrument()
                                .getStrings();
                        for (InstrumentString instrumentString : instrumentStrings) {
                            NoteTextField noteTextField = (NoteTextField) measurePane.lookup(String.format(
                                    "#NoteTextField_%d_%d_%d_%d", beat.getMeasure().getTrack().getId(),
                                    beat.getMeasure().getNumber(), beat.getNumber(), instrumentString.getString()));
                            if (noteTextField != null && StringUtils.isNotEmpty(noteTextField.getText())) {
                                noteTextField.colorRed();
                            }
                        }

                    }

                    if (!beatIter.hasNext()) {
                        pause();
                        return;
                    }

                    previousBeats = currentBeats;
                    currentBeats = beatIter.next();

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

    public Integer getMeasureIndex() {
        return measureIndex;
    }

    public void setMeasureIndex(Integer measureIndex) {
        this.measureIndex = measureIndex;
    }

}
