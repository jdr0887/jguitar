package com.kiluet.jguitar.dao.jpa;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.kiluet.jguitar.dao.BeatDAO;
import com.kiluet.jguitar.dao.InstrumentDAO;
import com.kiluet.jguitar.dao.JGuitarDAOService;
import com.kiluet.jguitar.dao.MeasureDAO;
import com.kiluet.jguitar.dao.NoteDAO;
import com.kiluet.jguitar.dao.ScaleDAO;
import com.kiluet.jguitar.dao.SongDAO;
import com.kiluet.jguitar.dao.TrackDAO;

@Component
public class JGuitarDAOServiceImpl implements JGuitarDAOService {

    @Autowired
    private BeatDAO beatDAO;

    @Autowired
    private MeasureDAO measureDAO;

    @Autowired
    private NoteDAO noteDAO;

    @Autowired
    private ScaleDAO scaleDAO;

    @Autowired
    private SongDAO songDAO;

    @Autowired
    private TrackDAO trackDAO;

    @Autowired
    private InstrumentDAO instrumentDAO;

    public JGuitarDAOServiceImpl() {
        super();
    }

    @Override
    public InstrumentDAO getInstrumentDAO() {
        return instrumentDAO;
    }

    @Override
    public void setInstrumentDAO(InstrumentDAO instrumentDAO) {
        this.instrumentDAO = instrumentDAO;
    }

    @Override
    public ScaleDAO getScaleDAO() {
        return scaleDAO;
    }

    @Override
    public void setScaleDAO(ScaleDAO scaleDAO) {
        this.scaleDAO = scaleDAO;
    }

    @Override
    public SongDAO getSongDAO() {
        return songDAO;
    }

    @Override
    public void setSongDAO(SongDAO songDAO) {
        this.songDAO = songDAO;
    }

    @Override
    public BeatDAO getBeatDAO() {
        return beatDAO;
    }

    @Override
    public void setBeatDAO(BeatDAO beatDAO) {
        this.beatDAO = beatDAO;
    }

    @Override
    public MeasureDAO getMeasureDAO() {
        return measureDAO;
    }

    @Override
    public void setMeasureDAO(MeasureDAO measureDAO) {
        this.measureDAO = measureDAO;
    }

    @Override
    public NoteDAO getNoteDAO() {
        return noteDAO;
    }

    @Override
    public void setNoteDAO(NoteDAO noteDAO) {
        this.noteDAO = noteDAO;
    }

    @Override
    public TrackDAO getTrackDAO() {
        return trackDAO;
    }

    @Override
    public void setTrackDAO(TrackDAO trackDAO) {
        this.trackDAO = trackDAO;
    }

}
