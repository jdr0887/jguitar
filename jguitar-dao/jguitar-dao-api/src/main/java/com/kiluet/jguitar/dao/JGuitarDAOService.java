package com.kiluet.jguitar.dao;

public interface JGuitarDAOService {

    public InstrumentDAO getInstrumentDAO();

    public void setInstrumentDAO(InstrumentDAO instrumentDAO);

    public ScaleDAO getScaleDAO();

    public void setScaleDAO(ScaleDAO scaleDAO);

    public SongDAO getSongDAO();

    public void setSongDAO(SongDAO songDAO);

    public BeatDAO getBeatDAO();

    public void setBeatDAO(BeatDAO beatDAO);

    public MeasureDAO getMeasureDAO();

    public void setMeasureDAO(MeasureDAO measureDAO);

    public NoteDAO getNoteDAO();

    public void setNoteDAO(NoteDAO noteDAO);

    public TrackDAO getTrackDAO();

    public void setTrackDAO(TrackDAO trackDAO);

}