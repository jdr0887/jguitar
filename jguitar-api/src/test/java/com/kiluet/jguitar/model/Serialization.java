package com.kiluet.jguitar.model;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.PropertyException;

import org.junit.Test;

public class Serialization {

    @Test
    public void writeDefaultSong() {

        Song song = new Song();
        song.setArtist("jGuitar");
        song.setComments("Default song");
        song.setName("Default song");

        Track track = new Track();
        track.setInstrument(InstrumentType.GUITAR);

        Measure measure = new Measure();
        Beat beat = new Beat(DurationType.WHOLE);
        beat.getNotes().add(new Note(6, 0));
        measure.getBeats().add(beat);
        track.getMeasures().add(measure);

        measure = new Measure();
        beat = new Beat(DurationType.WHOLE);
        beat.getNotes().add(new Note(5, 0));
        measure.getBeats().add(beat);
        track.getMeasures().add(measure);

        measure = new Measure();
        beat = new Beat(DurationType.WHOLE);
        beat.getNotes().add(new Note(4, 0));
        measure.getBeats().add(beat);
        track.getMeasures().add(measure);

        measure = new Measure();
        beat = new Beat(DurationType.WHOLE);
        beat.getNotes().add(new Note(3, 0));
        measure.getBeats().add(beat);
        track.getMeasures().add(measure);

        measure = new Measure();
        beat = new Beat(DurationType.WHOLE);
        beat.getNotes().add(new Note(2, 0));
        measure.getBeats().add(beat);
        track.getMeasures().add(measure);

        measure = new Measure();
        beat = new Beat(DurationType.WHOLE);
        beat.getNotes().add(new Note(1, 0));
        measure.getBeats().add(beat);
        track.getMeasures().add(measure);

        measure = new Measure();
        beat = new Beat(DurationType.WHOLE);
        beat.getNotes().add(new Note(1, 0));
        beat.getNotes().add(new Note(2, 1));
        beat.getNotes().add(new Note(3, 0));
        beat.getNotes().add(new Note(4, 2));
        beat.getNotes().add(new Note(5, 3));
        beat.getNotes().add(new Note(6, 0));
        measure.getBeats().add(beat);
        track.getMeasures().add(measure);

        song.getTracks().add(track);

        track = new Track();
        track.setInstrument(InstrumentType.BASS);

        measure = new Measure();
        beat = new Beat(DurationType.WHOLE);
        beat.getNotes().add(new Note(4, 0));
        measure.getBeats().add(beat);
        track.getMeasures().add(measure);

        measure = new Measure();
        beat = new Beat(DurationType.WHOLE);
        beat.getNotes().add(new Note(3, 0));
        measure.getBeats().add(beat);
        track.getMeasures().add(measure);

        measure = new Measure();
        beat = new Beat(DurationType.WHOLE);
        beat.getNotes().add(new Note(2, 0));
        measure.getBeats().add(beat);
        track.getMeasures().add(measure);

        measure = new Measure();
        beat = new Beat(DurationType.WHOLE);
        beat.getNotes().add(new Note(1, 0));
        measure.getBeats().add(beat);
        track.getMeasures().add(measure);

        measure = new Measure();
        beat = new Beat(DurationType.WHOLE);
        beat.getNotes().add(new Note(2, 0));
        measure.getBeats().add(beat);
        track.getMeasures().add(measure);

        measure = new Measure();
        beat = new Beat(DurationType.WHOLE);
        beat.getNotes().add(new Note(3, 0));
        measure.getBeats().add(beat);
        track.getMeasures().add(measure);

        measure = new Measure();
        beat = new Beat(DurationType.WHOLE);
        beat.getNotes().add(new Note(4, 0));
        measure.getBeats().add(beat);
        track.getMeasures().add(measure);

        song.getTracks().add(track);

        try {
            JAXBContext context = JAXBContext.newInstance(Song.class);
            Marshaller m = context.createMarshaller();
            m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
            FileWriter fw = new FileWriter(new File("/tmp/defaultSong.xml"));
            m.marshal(song, fw);
        } catch (IOException e1) {
            e1.printStackTrace();
        } catch (PropertyException e1) {
            e1.printStackTrace();
        } catch (JAXBException e1) {
            e1.printStackTrace();
        }
    }

    @Test
    public void serializeGMajoScale() {

        Song song = new Song();
        song.setArtist("jGuitar");
        song.setName("G Major Scale");

        Track track = new Track();
        track.setInstrument(InstrumentType.GUITAR);

        Measure measure = new Measure();

        Beat beat = new Beat(DurationType.EIGHTH);
        beat.getNotes().add(new Note(6, 3));
        measure.getBeats().add(beat);

        beat = new Beat(DurationType.EIGHTH);
        beat.getNotes().add(new Note(6, 5));
        measure.getBeats().add(beat);

        beat = new Beat(DurationType.EIGHTH);
        beat.getNotes().add(new Note(6, 7));
        measure.getBeats().add(beat);

        beat = new Beat(DurationType.EIGHTH);
        beat.getNotes().add(new Note(5, 3));
        measure.getBeats().add(beat);

        beat = new Beat(DurationType.EIGHTH);
        beat.getNotes().add(new Note(5, 5));
        measure.getBeats().add(beat);

        beat = new Beat(DurationType.EIGHTH);
        beat.getNotes().add(new Note(5, 7));
        measure.getBeats().add(beat);

        beat = new Beat(DurationType.EIGHTH);
        beat.getNotes().add(new Note(4, 4));
        measure.getBeats().add(beat);

        beat = new Beat(DurationType.EIGHTH);
        beat.getNotes().add(new Note(4, 5));
        measure.getBeats().add(beat);

        track.getMeasures().add(measure);

        measure = new Measure();

        beat = new Beat(DurationType.EIGHTH);
        beat.getNotes().add(new Note(4, 7));
        measure.getBeats().add(beat);

        beat = new Beat(DurationType.EIGHTH);
        beat.getNotes().add(new Note(3, 4));
        measure.getBeats().add(beat);

        beat = new Beat(DurationType.EIGHTH);
        beat.getNotes().add(new Note(3, 5));
        measure.getBeats().add(beat);

        beat = new Beat(DurationType.EIGHTH);
        beat.getNotes().add(new Note(3, 7));
        measure.getBeats().add(beat);

        beat = new Beat(DurationType.EIGHTH);
        beat.getNotes().add(new Note(2, 5));
        measure.getBeats().add(beat);

        beat = new Beat(DurationType.EIGHTH);
        beat.getNotes().add(new Note(2, 7));
        measure.getBeats().add(beat);

        beat = new Beat(DurationType.EIGHTH);
        beat.getNotes().add(new Note(1, 3));
        measure.getBeats().add(beat);

        beat = new Beat(DurationType.EIGHTH);
        beat.getNotes().add(new Note(1, 5));
        measure.getBeats().add(beat);

        track.getMeasures().add(measure);

        song.getTracks().add(track);

        try {
            JAXBContext context = JAXBContext.newInstance(Song.class);
            Marshaller m = context.createMarshaller();
            m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
            FileWriter fw = new FileWriter(new File("/tmp/gMajorScale.xml"));
            m.marshal(song, fw);
        } catch (IOException e1) {
            e1.printStackTrace();
        } catch (PropertyException e1) {
            e1.printStackTrace();
        } catch (JAXBException e1) {
            e1.printStackTrace();
        }
    }

}
