package com.kiluet.jguitar.dao;

public class JGuitarDAOBean {

    private BeatDAO beatDAO;

    private MeasureDAO measureDAO;

    private NoteDAO noteDAO;

    private ScaleDAO scaleDAO;

    private SongDAO songDAO;

    private TrackDAO trackDAO;

    private InstrumentDAO instrumentDAO;

    public JGuitarDAOBean() {
        super();
    }

    public InstrumentDAO getInstrumentDAO() {
        return instrumentDAO;
    }

    public void setInstrumentDAO(InstrumentDAO instrumentDAO) {
        this.instrumentDAO = instrumentDAO;
    }

    public ScaleDAO getScaleDAO() {
        return scaleDAO;
    }

    public void setScaleDAO(ScaleDAO scaleDAO) {
        this.scaleDAO = scaleDAO;
    }

    public SongDAO getSongDAO() {
        return songDAO;
    }

    public void setSongDAO(SongDAO songDAO) {
        this.songDAO = songDAO;
    }

    public BeatDAO getBeatDAO() {
        return beatDAO;
    }

    public void setBeatDAO(BeatDAO beatDAO) {
        this.beatDAO = beatDAO;
    }

    public MeasureDAO getMeasureDAO() {
        return measureDAO;
    }

    public void setMeasureDAO(MeasureDAO measureDAO) {
        this.measureDAO = measureDAO;
    }

    public NoteDAO getNoteDAO() {
        return noteDAO;
    }

    public void setNoteDAO(NoteDAO noteDAO) {
        this.noteDAO = noteDAO;
    }

    public TrackDAO getTrackDAO() {
        return trackDAO;
    }

    public void setTrackDAO(TrackDAO trackDAO) {
        this.trackDAO = trackDAO;
    }

}
