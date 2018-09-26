package com.kiluet.jguitar.dao;

import java.util.List;

import org.junit.Test;

import com.kiluet.jguitar.dao.model.Beat;
import com.kiluet.jguitar.dao.model.DurationType;
import com.kiluet.jguitar.dao.model.Instrument;
import com.kiluet.jguitar.dao.model.Measure;
import com.kiluet.jguitar.dao.model.MeterType;
import com.kiluet.jguitar.dao.model.Note;
import com.kiluet.jguitar.dao.model.Song;
import com.kiluet.jguitar.dao.model.Track;

public class TemplatePersistTest {

    private static final JGuitarDAOManager daoMgr = JGuitarDAOManager.getInstance();

    @Test
    public void testTempate() {

        try {

            Song song = new Song();
            song.setComments("Template");
            song.setTitle("Template");
            song.setId(daoMgr.getDaoBean().getSongDAO().save(song));

            List<Instrument> instrumentList = daoMgr.getDaoBean().getInstrumentDAO().findByName("Electric Guitar");

            Track track = createTrack(song, 1, instrumentList.get(0));

            Measure measure = createMeasure(track, 1);
            Beat beat = createBeat(measure, DurationType.HALF, 1);
            createNote(beat, 6, 0);

            beat = createBeat(measure, DurationType.HALF, 2);
            createNote(beat, 5, 0);

            measure = createMeasure(track, 2);
            beat = createBeat(measure, DurationType.HALF, 1);
            createNote(beat, 4, 0);

            beat = createBeat(measure, DurationType.HALF, 2);
            createNote(beat, 3, 0);

            measure = createMeasure(track, 3);
            beat = createBeat(measure, DurationType.HALF, 1);
            createNote(beat, 2, 0);

            beat = createBeat(measure, DurationType.HALF, 2);
            createNote(beat, 1, 0);

            measure = createMeasure(track, 4);
            beat = createBeat(measure, DurationType.HALF, 1);
            createNote(beat, 1, 0);
            createNote(beat, 2, 1);
            createNote(beat, 3, 0);
            createNote(beat, 4, 2);
            createNote(beat, 5, 3);
            createNote(beat, 6, 0);

            beat = createBeat(measure, DurationType.HALF, 2);
            createNote(beat, 1, 3);
            createNote(beat, 2, 3);
            createNote(beat, 3, 0);
            createNote(beat, 4, 0);
            createNote(beat, 5, 2);
            createNote(beat, 6, 3);

            instrumentList = daoMgr.getDaoBean().getInstrumentDAO().findByName("Electric Bass");

            track = createTrack(song, 2, instrumentList.get(0));

            measure = createMeasure(track, 1);
            beat = createBeat(measure, DurationType.HALF, 1);
            createNote(beat, 4, 0);

            beat = createBeat(measure, DurationType.HALF, 2);
            createNote(beat, 3, 0);

            measure = createMeasure(track, 2);
            beat = createBeat(measure, DurationType.HALF, 1);
            createNote(beat, 2, 0);

            beat = createBeat(measure, DurationType.HALF, 2);
            createNote(beat, 1, 0);

            measure = createMeasure(track, 3);
            beat = createBeat(measure, DurationType.HALF, 1);
            createNote(beat, 1, 4);

            beat = createBeat(measure, DurationType.HALF, 2);
            createNote(beat, 1, 9);

            measure = createMeasure(track, 4);
            beat = createBeat(measure, DurationType.HALF, 1);
            createNote(beat, 3, 3);

            beat = createBeat(measure, DurationType.HALF, 2);
            createNote(beat, 4, 3);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    protected Track createTrack(Song song, Integer number, Instrument instrument) throws JGuitarDAOException {
        Track track = new Track();
        track.setNumber(number);
        track.setInstrument(instrument);
        track.setSong(song);
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
        Measure measure = new Measure();
        measure.setMeterType(MeterType.COMMON);
        measure.setTempo(120);
        measure.setNumber(number);
        measure.setTrack(track);
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
