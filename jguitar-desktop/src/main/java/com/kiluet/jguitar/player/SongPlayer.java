package com.kiluet.jguitar.player;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.kiluet.jguitar.dao.model.Beat;
import com.kiluet.jguitar.dao.model.InstrumentString;
import com.kiluet.jguitar.dao.model.Measure;
import com.kiluet.jguitar.dao.model.Track;
import com.kiluet.jguitar.desktop.components.MeasurePane;
import com.kiluet.jguitar.desktop.components.NoteTextField;
import com.kiluet.jguitar.desktop.components.SongPane;
import com.kiluet.jguitar.desktop.components.TrackPane;

public class SongPlayer {

    private static final Logger logger = LoggerFactory.getLogger(SongPlayer.class);

    private SongPane songPane;

    private Beat previousBeat;

    private Beat currentBeat;

    private Timer timer = null;

    private Iterator<Beat> beatIter;

    private SongPlayerState songPlayerState = SongPlayerState.PAUSED;

    public SongPlayer(SongPane songPane) {
        super();
        this.songPane = songPane;
        init();
    }

    private void init() {
        LinkedList<Beat> beatList = new LinkedList<Beat>();
        List<Track> tracks = songPane.getSong().getTracks();
        for (Track track : tracks) {
            List<Measure> measures = track.getMeasures();
            for (Measure measure : measures) {
                List<Beat> beats = measure.getBeats();
                for (Beat beat : beats) {
                    beatList.add(beat);
                }
            }
        }
        this.beatIter = beatList.iterator();
    }

    public void play() {
        logger.info("ENTERING play()");
        this.songPlayerState = SongPlayerState.PLAYING;
        this.timer = new Timer();

        this.timer.scheduleAtFixedRate(new TimerTask() {

            @Override
            public void run() {
                logger.info("ENTERING run()");
                try {

                    if (previousBeat != null) {

                        TrackPane trackPane = (TrackPane) songPane
                                .lookup(String.format("#TrackPane_%d", previousBeat.getMeasure().getTrack().getId()));
                        MeasurePane measurePane = (MeasurePane) trackPane
                                .lookup(String.format("#MeasurePane_%d", previousBeat.getMeasure().getId()));
                        List<InstrumentString> instrumentStrings = previousBeat.getMeasure().getTrack().getInstrument()
                                .getStrings();
                        for (InstrumentString instrumentString : instrumentStrings) {
                            NoteTextField noteTextField = (NoteTextField) measurePane.lookup(
                                    String.format("#NoteTextField_%d_%d_%d", previousBeat.getMeasure().getNumber(),
                                            previousBeat.getNumber(), instrumentString.getString()));
                            if (noteTextField != null && StringUtils.isNotEmpty(noteTextField.getText())) {
                                noteTextField.colorBlack();
                            }
                        }

                    }

                    if (currentBeat == null) {
                        currentBeat = beatIter.next();
                    }

                    TrackPane trackPane = (TrackPane) songPane
                            .lookup(String.format("#TrackPane_%d", currentBeat.getMeasure().getTrack().getId()));
                    MeasurePane measurePane = (MeasurePane) trackPane
                            .lookup(String.format("#MeasurePane_%d", currentBeat.getMeasure().getId()));
                    List<InstrumentString> instrumentStrings = currentBeat.getMeasure().getTrack().getInstrument()
                            .getStrings();
                    for (InstrumentString instrumentString : instrumentStrings) {
                        NoteTextField noteTextField = (NoteTextField) measurePane
                                .lookup(String.format("#NoteTextField_%d_%d_%d", currentBeat.getMeasure().getNumber(),
                                        currentBeat.getNumber(), instrumentString.getString()));
                        if (noteTextField != null && StringUtils.isNotEmpty(noteTextField.getText())) {
                            noteTextField.colorRed();
                        }
                    }

                    if (!beatIter.hasNext()) {
                        timer.cancel();
                        return;
                    }

                    previousBeat = currentBeat;
                    currentBeat = beatIter.next();

                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        }, 0, 1000);

    }

    public void pause() {
        logger.info("ENTERING pause()");
        this.timer.cancel();
        this.songPlayerState = SongPlayerState.PAUSED;
    }

    public boolean isPlaying() {
        return this.songPlayerState == SongPlayerState.PLAYING;
    }

    public boolean isPaused() {
        return this.songPlayerState == SongPlayerState.PAUSED;
    }

}
