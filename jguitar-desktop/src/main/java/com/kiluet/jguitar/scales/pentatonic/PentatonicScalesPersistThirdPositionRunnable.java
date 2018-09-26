package com.kiluet.jguitar.scales.pentatonic;

import java.util.List;

import org.apache.commons.collections.CollectionUtils;

import com.kiluet.jguitar.dao.JGuitarDAOException;
import com.kiluet.jguitar.dao.model.Beat;
import com.kiluet.jguitar.dao.model.DurationType;
import com.kiluet.jguitar.dao.model.Instrument;
import com.kiluet.jguitar.dao.model.KeyType;
import com.kiluet.jguitar.dao.model.Measure;
import com.kiluet.jguitar.dao.model.Scale;
import com.kiluet.jguitar.dao.model.ScaleType;
import com.kiluet.jguitar.dao.model.Track;

import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@NoArgsConstructor
@Slf4j
public class PentatonicScalesPersistThirdPositionRunnable extends AbstractPentatonicPersistRunnable {

    @Override
    public String getName() {
        return "Pentatonic Scales";
    }

    @Override
    public void run() {
        try {
            int offset = 0;
            boolean wrap = false;

            for (KeyType key : keyTypeList) {

                String name = "3rd Position";
                Scale scale = new Scale();
                scale.setName(name);
                scale.setType(ScaleType.PENTATONIC);
                scale.setKey(key);

                List<Scale> persistedScales = daoMgr.getDaoBean().getScaleDAO().findByExample(scale);
                if (CollectionUtils.isNotEmpty(persistedScales)) {
                    continue;
                }
                log.info(scale.toString());

                Instrument instrument = daoMgr.getDaoBean().getInstrumentDAO().findByProgram(27);
                Track track = createTrack(1, instrument);
                scale.setTrack(track);
                scale.setId(daoMgr.getDaoBean().getScaleDAO().save(scale));

                if (4 + offset > 12) {
                    wrap = true;
                }

                Measure measure = createMeasure(track, 1);
                Beat beat = createBeat(measure, DurationType.EIGHTH, 1);
                createNote(beat, 6, 5 + offset, wrap);
                beat = createBeat(measure, DurationType.EIGHTH, 2);
                createNote(beat, 6, 7 + offset, wrap);
                beat = createBeat(measure, DurationType.EIGHTH, 3);
                createNote(beat, 5, 5 + offset, wrap);
                beat = createBeat(measure, DurationType.EIGHTH, 4);
                createNote(beat, 5, 7 + offset, wrap);
                beat = createBeat(measure, DurationType.EIGHTH, 5);
                createNote(beat, 4, 5 + offset, wrap);
                beat = createBeat(measure, DurationType.EIGHTH, 6);
                createNote(beat, 4, 7 + offset, wrap);
                beat = createBeat(measure, DurationType.EIGHTH, 7);
                createNote(beat, 3, 4 + offset, wrap);
                beat = createBeat(measure, DurationType.EIGHTH, 8);
                createNote(beat, 3, 7 + offset, wrap);

                measure = createMeasure(track, 2);
                beat = createBeat(measure, DurationType.EIGHTH, 1);
                createNote(beat, 2, 5 + offset, wrap);
                beat = createBeat(measure, DurationType.EIGHTH, 2);
                createNote(beat, 2, 8 + offset, wrap);
                beat = createBeat(measure, DurationType.EIGHTH, 3);
                createNote(beat, 1, 5 + offset, wrap);
                beat = createBeat(measure, DurationType.WHOLE, 4);
                createNote(beat, 1, 7 + offset, wrap);

                measure = createMeasure(track, 3);
                beat = createBeat(measure, DurationType.EIGHTH, 1);
                createNote(beat, 1, 7 + offset, wrap);
                beat = createBeat(measure, DurationType.EIGHTH, 2);
                createNote(beat, 1, 5 + offset, wrap);
                beat = createBeat(measure, DurationType.EIGHTH, 3);
                createNote(beat, 2, 8 + offset, wrap);
                beat = createBeat(measure, DurationType.EIGHTH, 4);
                createNote(beat, 2, 5 + offset, wrap);
                beat = createBeat(measure, DurationType.EIGHTH, 5);
                createNote(beat, 3, 7 + offset, wrap);
                beat = createBeat(measure, DurationType.EIGHTH, 6);
                createNote(beat, 3, 4 + offset, wrap);
                beat = createBeat(measure, DurationType.EIGHTH, 7);
                createNote(beat, 4, 7 + offset, wrap);
                beat = createBeat(measure, DurationType.EIGHTH, 8);
                createNote(beat, 4, 5 + offset, wrap);

                measure = createMeasure(track, 4);
                beat = createBeat(measure, DurationType.EIGHTH, 1);
                createNote(beat, 5, 7 + offset, wrap);
                beat = createBeat(measure, DurationType.EIGHTH, 2);
                createNote(beat, 5, 5 + offset, wrap);
                beat = createBeat(measure, DurationType.EIGHTH, 3);
                createNote(beat, 6, 7 + offset, wrap);
                beat = createBeat(measure, DurationType.WHOLE, 4);
                createNote(beat, 6, 5 + offset, wrap);

                if (key.equals(KeyType.E) || key.equals(KeyType.B)) {
                    offset += 1;
                    continue;
                }

                offset += 2;

            }
        } catch (JGuitarDAOException e) {
            e.printStackTrace();
        }
    }

}
