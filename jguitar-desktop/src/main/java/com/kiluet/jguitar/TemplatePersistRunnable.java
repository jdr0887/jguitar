package com.kiluet.jguitar;

import org.apache.commons.collections.CollectionUtils;

import com.kiluet.jguitar.dao.model.Beat;
import com.kiluet.jguitar.dao.model.DurationType;
import com.kiluet.jguitar.dao.model.Instrument;
import com.kiluet.jguitar.dao.model.Measure;
import com.kiluet.jguitar.dao.model.Song;
import com.kiluet.jguitar.dao.model.Track;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class TemplatePersistRunnable extends AbstractPersistRunnable {

    @Override
    public String getName() {
        return "Template";
    }

    @Override
    public void run() {

        try {

            if (CollectionUtils.isEmpty(daoMgr.getDaoBean().getSongDAO().findByTitle("Template"))) {

                Song song = new Song();
                song.setTitle("Template");
                song.setComments("Template");
                song.setId(daoMgr.getDaoBean().getSongDAO().save(song));

                Instrument instrument = daoMgr.getDaoBean().getInstrumentDAO().findByProgram(27);
                Track track = createTrack(song, 1, instrument);

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

                instrument = daoMgr.getDaoBean().getInstrumentDAO().findByProgram(34);
                track = createTrack(song, 2, instrument);

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

                daoMgr.getDaoBean().getSongDAO().save(song);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public static void main(String[] args) {
        TemplatePersistRunnable runnable = new TemplatePersistRunnable();
        runnable.run();
    }
}
