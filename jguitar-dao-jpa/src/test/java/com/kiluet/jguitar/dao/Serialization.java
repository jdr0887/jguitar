package com.kiluet.jguitar.dao;

import org.junit.Test;

import com.kiluet.jguitar.dao.model.Beat;
import com.kiluet.jguitar.dao.model.Instrument;
import com.kiluet.jguitar.dao.model.InstrumentString;
import com.kiluet.jguitar.dao.model.Measure;
import com.kiluet.jguitar.dao.model.Note;
import com.kiluet.jguitar.dao.model.Song;
import com.kiluet.jguitar.dao.model.Track;

public class Serialization {

    private final JGuitarDAOManager daoMgr = JGuitarDAOManager.getInstance();

    @Test
    public void scratch() {
        try {
            Instrument instrument = new Instrument("Electric Guitar", 27);
            instrument.getStrings().add(new InstrumentString(instrument, 6, 40));
            instrument.getStrings().add(new InstrumentString(instrument, 5, 45));
            instrument.getStrings().add(new InstrumentString(instrument, 4, 50));
            instrument.getStrings().add(new InstrumentString(instrument, 3, 55));
            instrument.getStrings().add(new InstrumentString(instrument, 2, 59));
            instrument.getStrings().add(new InstrumentString(instrument, 1, 64));
            daoMgr.getDaoBean().getInstrumentDAO().save(instrument);
        } catch (JGuitarDAOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void checkingMeasures() {
        try {
            Song song = daoMgr.getDaoBean().getSongDAO().findByTitle("Template").get(0);
            System.out.println(song.toString());
            for (Track track : song.getTracks()) {
                System.out.println(track.toString());
                for (Measure measure : track.getMeasures()) {
                    System.out.println(measure.toString());
                    for (Beat beat : measure.getBeats()) {
                        System.out.println(beat.toString());
                        for (Note note : beat.getNotes()) {
                            System.out.println(note.toString());
                        }
                    }
                }
            }
        } catch (JGuitarDAOException e) {
            e.printStackTrace();
        }
    }

}
