package com.kiluet.jguitar.desktop.scales;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.PropertyException;

import com.kiluet.jguitar.dao.JGuitarDAOException;
import com.kiluet.jguitar.dao.model.Beat;
import com.kiluet.jguitar.dao.model.DurationType;
import com.kiluet.jguitar.dao.model.Instrument;
import com.kiluet.jguitar.dao.model.Measure;
import com.kiluet.jguitar.dao.model.Song;
import com.kiluet.jguitar.dao.model.Track;

public class PersistTemplateRunnable extends AbstractPersistRunnable {

    public PersistTemplateRunnable() {
        super();
    }

    @Override
    public void run() {
        try {
            writeTemplate();
        } catch (JGuitarDAOException e) {
            e.printStackTrace();
        }

    }

    private void writeTemplate() throws JGuitarDAOException {

        List<Song> persistedSongs = daoMgr.getDaoBean().getSongDAO().findByName("Template");
        if (!persistedSongs.isEmpty()) {
            return;
        }

        Song song = new Song();
        song.setComments("Template");
        song.setName("Template");
        song.setId(daoMgr.getDaoBean().getSongDAO().save(song));

        List<Instrument> instrumentList = daoMgr.getDaoBean().getInstrumentDAO().findByName("Electric Guitar");

        Track track = createTrack(song, 1, instrumentList.get(0));

        Measure measure = createMeasure(track, 1);
        Beat beat = createBeat(measure, DurationType.WHOLE, 1);
        createNote(beat, 6, 0);

        measure = createMeasure(track, 2);
        beat = createBeat(measure, DurationType.WHOLE, 1);
        createNote(beat, 5, 0);

        measure = createMeasure(track, 3);
        beat = createBeat(measure, DurationType.WHOLE, 1);
        createNote(beat, 4, 0);

        measure = createMeasure(track, 4);
        beat = createBeat(measure, DurationType.WHOLE, 1);
        createNote(beat, 3, 0);

        measure = createMeasure(track, 5);
        beat = createBeat(measure, DurationType.WHOLE, 1);
        createNote(beat, 2, 0);

        measure = createMeasure(track, 6);
        beat = createBeat(measure, DurationType.WHOLE, 1);
        createNote(beat, 1, 0);

        measure = createMeasure(track, 7);
        beat = createBeat(measure, DurationType.WHOLE, 1);
        createNote(beat, 1, 0);
        createNote(beat, 2, 1);
        createNote(beat, 3, 0);
        createNote(beat, 4, 2);
        createNote(beat, 5, 3);
        createNote(beat, 6, 0);

        instrumentList = daoMgr.getDaoBean().getInstrumentDAO().findByName("Electric Bass");

        track = createTrack(song, 2, instrumentList.get(0));

        measure = createMeasure(track, 1);
        beat = createBeat(measure, DurationType.WHOLE, 1);
        createNote(beat, 4, 0);

        measure = createMeasure(track, 2);
        beat = createBeat(measure, DurationType.WHOLE, 1);
        createNote(beat, 3, 0);

        measure = createMeasure(track, 3);
        beat = createBeat(measure, DurationType.WHOLE, 1);
        createNote(beat, 2, 0);

        measure = createMeasure(track, 4);
        beat = createBeat(measure, DurationType.WHOLE, 1);
        createNote(beat, 1, 0);

        measure = createMeasure(track, 5);
        beat = createBeat(measure, DurationType.WHOLE, 1);
        createNote(beat, 2, 0);

        measure = createMeasure(track, 6);
        beat = createBeat(measure, DurationType.WHOLE, 1);
        createNote(beat, 3, 0);

        measure = createMeasure(track, 7);
        beat = createBeat(measure, DurationType.WHOLE, 1);
        createNote(beat, 4, 0);

        persistedSongs = daoMgr.getDaoBean().getSongDAO().findByName("Template");
        Song persistedSong = persistedSongs.get(0);

        try {
            JAXBContext context = JAXBContext.newInstance(Song.class);
            Marshaller m = context.createMarshaller();
            m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
            FileWriter fw = new FileWriter(new File("/tmp", "template.xml"));
            m.marshal(persistedSong, fw);
        } catch (IOException e1) {
            e1.printStackTrace();
        } catch (PropertyException e1) {
            e1.printStackTrace();
        } catch (JAXBException e1) {
            e1.printStackTrace();
        }
    }

}
