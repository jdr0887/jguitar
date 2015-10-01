package com.kiluet.jguitar;

import com.kiluet.jguitar.dao.JGuitarDAOException;
import com.kiluet.jguitar.dao.JGuitarDAOManager;
import com.kiluet.jguitar.dao.model.Beat;
import com.kiluet.jguitar.dao.model.DurationType;
import com.kiluet.jguitar.dao.model.Instrument;
import com.kiluet.jguitar.dao.model.Measure;
import com.kiluet.jguitar.dao.model.MeterType;
import com.kiluet.jguitar.dao.model.Note;
import com.kiluet.jguitar.dao.model.Song;
import com.kiluet.jguitar.dao.model.Track;

public abstract class AbstractPersistRunnable implements Runnable {

    protected static final JGuitarDAOManager daoMgr = JGuitarDAOManager.getInstance();

    public AbstractPersistRunnable() {
        super();
    }

    public abstract String getName();

    protected Track createTrack(Song song, Integer number, Instrument instrument) throws JGuitarDAOException {
        Track track = new Track(song, instrument, number);
        track.setId(daoMgr.getDaoBean().getTrackDAO().save(track));
        song.getTracks().add(track);
        return track;
    }

    protected Track createTrack(Integer number, Instrument instrument) throws JGuitarDAOException {
        Track track = new Track();
        track.setNumber(number);
        track.setInstrument(instrument);
        track.setId(daoMgr.getDaoBean().getTrackDAO().save(track));
        return track;
    }

    protected Measure createMeasure(Track track, Integer number) throws JGuitarDAOException {
        Measure measure = new Measure(track, MeterType.COMMON, 120, number);
        measure.setId(daoMgr.getDaoBean().getMeasureDAO().save(measure));
        track.getMeasures().add(measure);
        return measure;
    }

    protected Beat createBeat(Measure measure, DurationType durationType, Integer number) throws JGuitarDAOException {
        Beat beat = new Beat(measure, durationType, number);
        beat.setId(daoMgr.getDaoBean().getBeatDAO().save(beat));
        measure.getBeats().add(beat);
        return beat;
    }

    protected void createNote(Beat beat, Integer string, Integer value) throws JGuitarDAOException {
        createNote(beat, string, value, false);
    }

    protected void createNote(Beat beat, Integer string, Integer value, boolean wrap) throws JGuitarDAOException {
        if (wrap) {
            value -= 12;
        }
        Note note = new Note(beat, string, value, 200);
        note.setId(daoMgr.getDaoBean().getNoteDAO().save(note));
        beat.getNotes().add(note);
    }

}
