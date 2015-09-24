package com.kiluet.jguitar;

import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.kiluet.jguitar.dao.model.Beat;
import com.kiluet.jguitar.dao.model.DurationType;
import com.kiluet.jguitar.dao.model.Instrument;
import com.kiluet.jguitar.dao.model.Measure;
import com.kiluet.jguitar.dao.model.Song;
import com.kiluet.jguitar.dao.model.Track;

public class TemplatePersistRunnable extends AbstractPersistRunnable {

    private static final Logger logger = LoggerFactory.getLogger(TemplatePersistRunnable.class);

    public TemplatePersistRunnable() {
        super();
    }

    @Override
    public String getName() {
        return "Template";
    }

    @Override
    public void run() {
        logger.info("ENTERING run()");
        writeTemplate();

        // try {
        // JAXBContext context = JAXBContext.newInstance(Song.class);
        // Marshaller m = context.createMarshaller();
        // m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
        // FileWriter fw = new FileWriter(new File("/tmp", "template.xml"));
        // m.marshal(template, fw);
        // } catch (IOException e1) {
        // e1.printStackTrace();
        // } catch (PropertyException e1) {
        // e1.printStackTrace();
        // } catch (JAXBException e1) {
        // e1.printStackTrace();
        // }
    }

    private Song writeTemplate() {

        Song song = null;

        try {

            List<Song> persistedSongs = daoMgr.getDaoBean().getSongDAO().findByName("Template");
            if (CollectionUtils.isNotEmpty(persistedSongs)) {
                return persistedSongs.get(0);
            }

            song = new Song();
            song.setName("Template");
            song.setComments("Template");
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
        return song;
    }

}
