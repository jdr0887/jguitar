package com.kiluet.jguitar;

import java.util.LinkedList;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.kiluet.jguitar.dao.JGuitarDAOException;
import com.kiluet.jguitar.dao.model.Beat;
import com.kiluet.jguitar.dao.model.DurationType;
import com.kiluet.jguitar.dao.model.Instrument;
import com.kiluet.jguitar.dao.model.KeyType;
import com.kiluet.jguitar.dao.model.Measure;
import com.kiluet.jguitar.dao.model.Scale;
import com.kiluet.jguitar.dao.model.ScaleType;
import com.kiluet.jguitar.dao.model.Track;

public class HeptatonicScalesPersistRunnable extends AbstractPersistRunnable {

    private static final Logger logger = LoggerFactory.getLogger(HeptatonicScalesPersistRunnable.class);

    private static final List<KeyType> keyTypeList = new LinkedList<KeyType>();

    @Override
    public String getName() {
        return "Heptatonic Scales";
    }

    public HeptatonicScalesPersistRunnable() {
        super();
        keyTypeList.add(KeyType.E);
        keyTypeList.add(KeyType.F);
        keyTypeList.add(KeyType.G);
        keyTypeList.add(KeyType.A);
        keyTypeList.add(KeyType.B);
        keyTypeList.add(KeyType.C);
        keyTypeList.add(KeyType.D);
    }

    @Override
    public void run() {
        logger.info("ENTERING run()");
        try {
            writePositionI();
            writePositionII();
            writePositionIII();
            writePositionIV();
            writePositionV();
            writePositionVI();
            writePositionVII();
        } catch (JGuitarDAOException e) {
            e.printStackTrace();
        }
    }

    private void writePositionI() throws JGuitarDAOException {

        int offset = 0;

        boolean wrap = false;

        for (KeyType key : keyTypeList) {

            String name = "1st Position";
            Scale scale = new Scale();
            scale.setName(name);
            scale.setType(ScaleType.HEPTATONIC);
            scale.setKey(key);

            List<Scale> persistedScales = daoMgr.getDaoBean().getScaleDAO().findByExample(scale);
            if (CollectionUtils.isNotEmpty(persistedScales)) {
                return;
            }
            logger.info("Creating...{}", scale.toString());

            Instrument instrument = daoMgr.getDaoBean().getInstrumentDAO().findByProgram(27);
            Track track = createTrack(1, instrument);
            scale.setTrack(track);
            scale.setId(daoMgr.getDaoBean().getScaleDAO().save(scale));

            if (0 + offset > 12) {
                wrap = true;
            }

            Measure measure = createMeasure(track, 1);
            Beat beat = createBeat(measure, DurationType.EIGHTH, 1);
            createNote(beat, 6, 0 + offset, wrap);
            beat = createBeat(measure, DurationType.EIGHTH, 2);
            createNote(beat, 6, 2 + offset, wrap);
            beat = createBeat(measure, DurationType.EIGHTH, 3);
            createNote(beat, 6, 4 + offset, wrap);
            beat = createBeat(measure, DurationType.EIGHTH, 4);
            createNote(beat, 5, 0 + offset, wrap);
            beat = createBeat(measure, DurationType.EIGHTH, 5);
            createNote(beat, 5, 2 + offset, wrap);
            beat = createBeat(measure, DurationType.EIGHTH, 6);
            createNote(beat, 5, 4 + offset, wrap);
            beat = createBeat(measure, DurationType.EIGHTH, 7);
            createNote(beat, 4, 1 + offset, wrap);
            beat = createBeat(measure, DurationType.EIGHTH, 8);
            createNote(beat, 4, 2 + offset, wrap);

            measure = createMeasure(track, 2);
            beat = createBeat(measure, DurationType.EIGHTH, 1);
            createNote(beat, 4, 4 + offset, wrap);
            beat = createBeat(measure, DurationType.EIGHTH, 2);
            createNote(beat, 3, 1 + offset, wrap);
            beat = createBeat(measure, DurationType.EIGHTH, 3);
            createNote(beat, 3, 2 + offset, wrap);
            beat = createBeat(measure, DurationType.EIGHTH, 4);
            createNote(beat, 3, 4 + offset, wrap);
            beat = createBeat(measure, DurationType.EIGHTH, 5);
            createNote(beat, 2, 2 + offset, wrap);
            beat = createBeat(measure, DurationType.EIGHTH, 6);
            createNote(beat, 2, 4 + offset, wrap);
            beat = createBeat(measure, DurationType.EIGHTH, 7);
            createNote(beat, 1, 0 + offset, wrap);
            beat = createBeat(measure, DurationType.EIGHTH, 8);
            createNote(beat, 1, 2 + offset, wrap);

            measure = createMeasure(track, 3);
            beat = createBeat(measure, DurationType.WHOLE, 1);
            createNote(beat, 1, 4 + offset, wrap);

            measure = createMeasure(track, 4);
            beat = createBeat(measure, DurationType.EIGHTH, 1);
            createNote(beat, 1, 4 + offset, wrap);
            beat = createBeat(measure, DurationType.EIGHTH, 2);
            createNote(beat, 1, 2 + offset, wrap);
            beat = createBeat(measure, DurationType.EIGHTH, 3);
            createNote(beat, 1, 0 + offset, wrap);
            beat = createBeat(measure, DurationType.EIGHTH, 4);
            createNote(beat, 2, 4 + offset, wrap);
            beat = createBeat(measure, DurationType.EIGHTH, 5);
            createNote(beat, 2, 2 + offset, wrap);
            beat = createBeat(measure, DurationType.EIGHTH, 6);
            createNote(beat, 3, 4 + offset, wrap);
            beat = createBeat(measure, DurationType.EIGHTH, 7);
            createNote(beat, 3, 2 + offset, wrap);
            beat = createBeat(measure, DurationType.EIGHTH, 8);
            createNote(beat, 3, 1 + offset, wrap);

            measure = createMeasure(track, 5);
            beat = createBeat(measure, DurationType.EIGHTH, 1);
            createNote(beat, 4, 4 + offset, wrap);
            beat = createBeat(measure, DurationType.EIGHTH, 2);
            createNote(beat, 4, 2 + offset, wrap);
            beat = createBeat(measure, DurationType.EIGHTH, 3);
            createNote(beat, 4, 1 + offset, wrap);
            beat = createBeat(measure, DurationType.EIGHTH, 4);
            createNote(beat, 5, 4 + offset, wrap);
            beat = createBeat(measure, DurationType.EIGHTH, 5);
            createNote(beat, 5, 2 + offset, wrap);
            beat = createBeat(measure, DurationType.EIGHTH, 6);
            createNote(beat, 5, 0 + offset, wrap);
            beat = createBeat(measure, DurationType.EIGHTH, 7);
            createNote(beat, 6, 4 + offset, wrap);
            beat = createBeat(measure, DurationType.EIGHTH, 8);
            createNote(beat, 6, 2 + offset, wrap);

            measure = createMeasure(track, 6);
            beat = createBeat(measure, DurationType.WHOLE, 1);
            createNote(beat, 6, 0 + offset, wrap);

            if (key.equals(KeyType.E) || key.equals(KeyType.B)) {
                offset += 1;
                continue;
            }

            offset += 2;

        }
    }

    private void writePositionII() throws JGuitarDAOException {

        int offset = 0;
        boolean wrap = false;

        for (KeyType key : keyTypeList) {

            String name = "2nd Position";
            Scale scale = new Scale();
            scale.setName(name);
            scale.setType(ScaleType.HEPTATONIC);
            scale.setKey(key);

            List<Scale> persistedScales = daoMgr.getDaoBean().getScaleDAO().findByExample(scale);
            if (CollectionUtils.isNotEmpty(persistedScales)) {
                return;
            }
            logger.info("Creating...{}", scale.toString());

            Instrument instrument = daoMgr.getDaoBean().getInstrumentDAO().findByProgram(27);
            Track track = createTrack(1, instrument);
            scale.setTrack(track);
            scale.setId(daoMgr.getDaoBean().getScaleDAO().save(scale));

            if (2 + offset > 12) {
                wrap = true;
            }

            Measure measure = createMeasure(track, 1);
            Beat beat = createBeat(measure, DurationType.EIGHTH, 1);
            createNote(beat, 6, 2 + offset, wrap);
            beat = createBeat(measure, DurationType.EIGHTH, 2);
            createNote(beat, 6, 4 + offset, wrap);
            beat = createBeat(measure, DurationType.EIGHTH, 3);
            createNote(beat, 6, 5 + offset, wrap);
            beat = createBeat(measure, DurationType.EIGHTH, 4);
            createNote(beat, 5, 2 + offset, wrap);
            beat = createBeat(measure, DurationType.EIGHTH, 5);
            createNote(beat, 5, 4 + offset, wrap);
            beat = createBeat(measure, DurationType.EIGHTH, 6);
            createNote(beat, 5, 6 + offset, wrap);
            beat = createBeat(measure, DurationType.EIGHTH, 7);
            createNote(beat, 4, 2 + offset, wrap);
            beat = createBeat(measure, DurationType.EIGHTH, 8);
            createNote(beat, 4, 4 + offset, wrap);

            measure = createMeasure(track, 2);
            beat = createBeat(measure, DurationType.EIGHTH, 1);
            createNote(beat, 4, 6 + offset, wrap);
            beat = createBeat(measure, DurationType.EIGHTH, 2);
            createNote(beat, 3, 2 + offset, wrap);
            beat = createBeat(measure, DurationType.EIGHTH, 3);
            createNote(beat, 3, 4 + offset, wrap);
            beat = createBeat(measure, DurationType.EIGHTH, 4);
            createNote(beat, 2, 2 + offset, wrap);
            beat = createBeat(measure, DurationType.EIGHTH, 5);
            createNote(beat, 2, 4 + offset, wrap);
            beat = createBeat(measure, DurationType.EIGHTH, 6);
            createNote(beat, 2, 5 + offset, wrap);
            beat = createBeat(measure, DurationType.EIGHTH, 7);
            createNote(beat, 1, 2 + offset, wrap);
            beat = createBeat(measure, DurationType.EIGHTH, 8);
            createNote(beat, 1, 4 + offset, wrap);

            measure = createMeasure(track, 3);
            beat = createBeat(measure, DurationType.WHOLE, 1);
            createNote(beat, 1, 5 + offset, wrap);

            measure = createMeasure(track, 4);
            beat = createBeat(measure, DurationType.EIGHTH, 1);
            createNote(beat, 1, 5 + offset, wrap);
            beat = createBeat(measure, DurationType.EIGHTH, 2);
            createNote(beat, 1, 4 + offset, wrap);
            beat = createBeat(measure, DurationType.EIGHTH, 3);
            createNote(beat, 1, 2 + offset, wrap);
            beat = createBeat(measure, DurationType.EIGHTH, 4);
            createNote(beat, 2, 5 + offset, wrap);
            beat = createBeat(measure, DurationType.EIGHTH, 5);
            createNote(beat, 2, 4 + offset, wrap);
            beat = createBeat(measure, DurationType.EIGHTH, 6);
            createNote(beat, 2, 2 + offset, wrap);
            beat = createBeat(measure, DurationType.EIGHTH, 7);
            createNote(beat, 3, 4 + offset, wrap);
            beat = createBeat(measure, DurationType.EIGHTH, 8);
            createNote(beat, 3, 2 + offset, wrap);

            measure = createMeasure(track, 5);
            beat = createBeat(measure, DurationType.EIGHTH, 1);
            createNote(beat, 4, 6 + offset, wrap);
            beat = createBeat(measure, DurationType.EIGHTH, 2);
            createNote(beat, 4, 4 + offset, wrap);
            beat = createBeat(measure, DurationType.EIGHTH, 3);
            createNote(beat, 4, 2 + offset, wrap);
            beat = createBeat(measure, DurationType.EIGHTH, 4);
            createNote(beat, 5, 6 + offset, wrap);
            beat = createBeat(measure, DurationType.EIGHTH, 5);
            createNote(beat, 5, 4 + offset, wrap);
            beat = createBeat(measure, DurationType.EIGHTH, 6);
            createNote(beat, 5, 2 + offset, wrap);
            beat = createBeat(measure, DurationType.EIGHTH, 7);
            createNote(beat, 6, 5 + offset, wrap);
            beat = createBeat(measure, DurationType.EIGHTH, 8);
            createNote(beat, 6, 4 + offset, wrap);

            measure = createMeasure(track, 6);
            beat = createBeat(measure, DurationType.WHOLE, 1);
            createNote(beat, 6, 2 + offset, wrap);

            if (key.equals(KeyType.E) || key.equals(KeyType.B)) {
                offset += 1;
                continue;
            }

            offset += 2;

        }
    }

    private void writePositionIII() throws JGuitarDAOException {

        int offset = 0;
        boolean wrap = false;

        for (KeyType key : keyTypeList) {

            String name = "3rd Position";
            Scale scale = new Scale();
            scale.setName(name);
            scale.setType(ScaleType.HEPTATONIC);
            scale.setKey(key);

            List<Scale> persistedScales = daoMgr.getDaoBean().getScaleDAO().findByExample(scale);
            if (CollectionUtils.isNotEmpty(persistedScales)) {
                return;
            }
            logger.info("Creating...{}", scale.toString());

            Instrument instrument = daoMgr.getDaoBean().getInstrumentDAO().findByProgram(27);
            Track track = createTrack(1, instrument);
            scale.setTrack(track);
            scale.setId(daoMgr.getDaoBean().getScaleDAO().save(scale));

            if (4 + offset > 12) {
                wrap = true;
            }

            Measure measure = createMeasure(track, 1);
            Beat beat = createBeat(measure, DurationType.EIGHTH, 1);
            createNote(beat, 6, 4 + offset, wrap);
            beat = createBeat(measure, DurationType.EIGHTH, 2);
            createNote(beat, 6, 5 + offset, wrap);
            beat = createBeat(measure, DurationType.EIGHTH, 3);
            createNote(beat, 6, 7 + offset, wrap);
            beat = createBeat(measure, DurationType.EIGHTH, 4);
            createNote(beat, 5, 4 + offset, wrap);
            beat = createBeat(measure, DurationType.EIGHTH, 5);
            createNote(beat, 5, 6 + offset, wrap);
            beat = createBeat(measure, DurationType.EIGHTH, 6);
            createNote(beat, 5, 7 + offset, wrap);
            beat = createBeat(measure, DurationType.EIGHTH, 7);
            createNote(beat, 4, 4 + offset, wrap);
            beat = createBeat(measure, DurationType.EIGHTH, 8);
            createNote(beat, 4, 6 + offset, wrap);

            measure = createMeasure(track, 2);
            beat = createBeat(measure, DurationType.EIGHTH, 1);
            createNote(beat, 4, 7 + offset, wrap);
            beat = createBeat(measure, DurationType.EIGHTH, 2);
            createNote(beat, 3, 4 + offset, wrap);
            beat = createBeat(measure, DurationType.EIGHTH, 3);
            createNote(beat, 3, 6 + offset, wrap);
            beat = createBeat(measure, DurationType.EIGHTH, 4);
            createNote(beat, 2, 4 + offset, wrap);
            beat = createBeat(measure, DurationType.EIGHTH, 5);
            createNote(beat, 2, 5 + offset, wrap);
            beat = createBeat(measure, DurationType.EIGHTH, 6);
            createNote(beat, 2, 7 + offset, wrap);
            beat = createBeat(measure, DurationType.EIGHTH, 7);
            createNote(beat, 1, 4 + offset, wrap);
            beat = createBeat(measure, DurationType.EIGHTH, 8);
            createNote(beat, 1, 5 + offset, wrap);

            measure = createMeasure(track, 3);
            beat = createBeat(measure, DurationType.WHOLE, 1);
            createNote(beat, 1, 7 + offset, wrap);

            measure = createMeasure(track, 4);
            beat = createBeat(measure, DurationType.EIGHTH, 1);
            createNote(beat, 1, 7 + offset, wrap);
            beat = createBeat(measure, DurationType.EIGHTH, 2);
            createNote(beat, 1, 5 + offset, wrap);
            beat = createBeat(measure, DurationType.EIGHTH, 3);
            createNote(beat, 1, 4 + offset, wrap);
            beat = createBeat(measure, DurationType.EIGHTH, 4);
            createNote(beat, 2, 7 + offset, wrap);
            beat = createBeat(measure, DurationType.EIGHTH, 5);
            createNote(beat, 2, 5 + offset, wrap);
            beat = createBeat(measure, DurationType.EIGHTH, 6);
            createNote(beat, 2, 4 + offset, wrap);
            beat = createBeat(measure, DurationType.EIGHTH, 7);
            createNote(beat, 3, 6 + offset, wrap);
            beat = createBeat(measure, DurationType.EIGHTH, 8);
            createNote(beat, 3, 4 + offset, wrap);

            measure = createMeasure(track, 5);
            beat = createBeat(measure, DurationType.EIGHTH, 1);
            createNote(beat, 4, 7 + offset, wrap);
            beat = createBeat(measure, DurationType.EIGHTH, 2);
            createNote(beat, 4, 6 + offset, wrap);
            beat = createBeat(measure, DurationType.EIGHTH, 3);
            createNote(beat, 4, 4 + offset, wrap);
            beat = createBeat(measure, DurationType.EIGHTH, 4);
            createNote(beat, 5, 7 + offset, wrap);
            beat = createBeat(measure, DurationType.EIGHTH, 5);
            createNote(beat, 5, 6 + offset, wrap);
            beat = createBeat(measure, DurationType.EIGHTH, 6);
            createNote(beat, 5, 4 + offset, wrap);
            beat = createBeat(measure, DurationType.EIGHTH, 7);
            createNote(beat, 6, 7 + offset, wrap);
            beat = createBeat(measure, DurationType.EIGHTH, 8);
            createNote(beat, 6, 5 + offset, wrap);

            measure = createMeasure(track, 6);
            beat = createBeat(measure, DurationType.WHOLE, 1);
            createNote(beat, 6, 4 + offset, wrap);

            if (key.equals(KeyType.E) || key.equals(KeyType.B)) {
                offset += 1;
                continue;
            }

            offset += 2;

        }
    }

    private void writePositionIV() throws JGuitarDAOException {

        int offset = 0;
        boolean wrap = false;

        for (KeyType key : keyTypeList) {

            String name = "4th Position";
            Scale scale = new Scale();
            scale.setName(name);
            scale.setType(ScaleType.HEPTATONIC);
            scale.setKey(key);

            List<Scale> persistedScales = daoMgr.getDaoBean().getScaleDAO().findByExample(scale);
            if (CollectionUtils.isNotEmpty(persistedScales)) {
                return;
            }
            logger.info("Creating...{}", scale.toString());

            Instrument instrument = daoMgr.getDaoBean().getInstrumentDAO().findByProgram(27);
            Track track = createTrack(1, instrument);
            scale.setTrack(track);
            scale.setId(daoMgr.getDaoBean().getScaleDAO().save(scale));

            if (5 + offset > 12) {
                wrap = true;
            }

            Measure measure = createMeasure(track, 1);
            Beat beat = createBeat(measure, DurationType.EIGHTH, 1);
            createNote(beat, 6, 5 + offset, wrap);
            beat = createBeat(measure, DurationType.EIGHTH, 2);
            createNote(beat, 6, 7 + offset, wrap);
            beat = createBeat(measure, DurationType.EIGHTH, 3);
            createNote(beat, 6, 9 + offset, wrap);
            beat = createBeat(measure, DurationType.EIGHTH, 4);
            createNote(beat, 5, 6 + offset, wrap);
            beat = createBeat(measure, DurationType.EIGHTH, 5);
            createNote(beat, 5, 7 + offset, wrap);
            beat = createBeat(measure, DurationType.EIGHTH, 6);
            createNote(beat, 5, 9 + offset, wrap);
            beat = createBeat(measure, DurationType.EIGHTH, 7);
            createNote(beat, 4, 6 + offset, wrap);
            beat = createBeat(measure, DurationType.EIGHTH, 8);
            createNote(beat, 4, 7 + offset, wrap);

            measure = createMeasure(track, 2);
            beat = createBeat(measure, DurationType.EIGHTH, 1);
            createNote(beat, 4, 9 + offset, wrap);
            beat = createBeat(measure, DurationType.EIGHTH, 2);
            createNote(beat, 3, 6 + offset, wrap);
            beat = createBeat(measure, DurationType.EIGHTH, 3);
            createNote(beat, 3, 8 + offset, wrap);
            beat = createBeat(measure, DurationType.EIGHTH, 4);
            createNote(beat, 3, 9 + offset, wrap);
            beat = createBeat(measure, DurationType.EIGHTH, 5);
            createNote(beat, 2, 7 + offset, wrap);
            beat = createBeat(measure, DurationType.EIGHTH, 6);
            createNote(beat, 2, 9 + offset, wrap);
            beat = createBeat(measure, DurationType.EIGHTH, 7);
            createNote(beat, 1, 5 + offset, wrap);
            beat = createBeat(measure, DurationType.EIGHTH, 8);
            createNote(beat, 1, 7 + offset, wrap);

            measure = createMeasure(track, 3);
            beat = createBeat(measure, DurationType.WHOLE, 1);
            createNote(beat, 1, 9 + offset, wrap);

            measure = createMeasure(track, 4);
            beat = createBeat(measure, DurationType.EIGHTH, 1);
            createNote(beat, 1, 9 + offset, wrap);
            beat = createBeat(measure, DurationType.EIGHTH, 2);
            createNote(beat, 1, 7 + offset, wrap);
            beat = createBeat(measure, DurationType.EIGHTH, 3);
            createNote(beat, 1, 5 + offset, wrap);
            beat = createBeat(measure, DurationType.EIGHTH, 4);
            createNote(beat, 2, 9 + offset, wrap);
            beat = createBeat(measure, DurationType.EIGHTH, 5);
            createNote(beat, 2, 7 + offset, wrap);
            beat = createBeat(measure, DurationType.EIGHTH, 6);
            createNote(beat, 3, 9 + offset, wrap);
            beat = createBeat(measure, DurationType.EIGHTH, 7);
            createNote(beat, 3, 8 + offset, wrap);
            beat = createBeat(measure, DurationType.EIGHTH, 8);
            createNote(beat, 3, 6 + offset, wrap);

            measure = createMeasure(track, 5);
            beat = createBeat(measure, DurationType.EIGHTH, 1);
            createNote(beat, 4, 9 + offset, wrap);
            beat = createBeat(measure, DurationType.EIGHTH, 2);
            createNote(beat, 4, 7 + offset, wrap);
            beat = createBeat(measure, DurationType.EIGHTH, 3);
            createNote(beat, 4, 6 + offset, wrap);
            beat = createBeat(measure, DurationType.EIGHTH, 4);
            createNote(beat, 5, 9 + offset, wrap);
            beat = createBeat(measure, DurationType.EIGHTH, 5);
            createNote(beat, 5, 7 + offset, wrap);
            beat = createBeat(measure, DurationType.EIGHTH, 6);
            createNote(beat, 5, 6 + offset, wrap);
            beat = createBeat(measure, DurationType.EIGHTH, 7);
            createNote(beat, 6, 9 + offset, wrap);
            beat = createBeat(measure, DurationType.EIGHTH, 8);
            createNote(beat, 6, 7 + offset, wrap);

            measure = createMeasure(track, 6);
            beat = createBeat(measure, DurationType.WHOLE, 1);
            createNote(beat, 6, 5 + offset, wrap);

            if (key.equals(KeyType.E) || key.equals(KeyType.B)) {
                offset += 1;
                continue;
            }

            offset += 2;

        }
    }

    private void writePositionV() throws JGuitarDAOException {

        int offset = 0;
        boolean wrap = false;

        for (KeyType key : keyTypeList) {

            String name = "5th Position";
            Scale scale = new Scale();
            scale.setName(name);
            scale.setType(ScaleType.HEPTATONIC);
            scale.setKey(key);

            List<Scale> persistedScales = daoMgr.getDaoBean().getScaleDAO().findByExample(scale);
            if (CollectionUtils.isNotEmpty(persistedScales)) {
                return;
            }
            logger.info("Creating...{}", scale.toString());

            Instrument instrument = daoMgr.getDaoBean().getInstrumentDAO().findByProgram(27);
            Track track = createTrack(1, instrument);
            scale.setTrack(track);
            scale.setId(daoMgr.getDaoBean().getScaleDAO().save(scale));

            if (7 + offset > 12) {
                wrap = true;
            }

            Measure measure = createMeasure(track, 1);
            Beat beat = createBeat(measure, DurationType.EIGHTH, 1);
            createNote(beat, 6, 7 + offset, wrap);
            beat = createBeat(measure, DurationType.EIGHTH, 2);
            createNote(beat, 6, 9 + offset, wrap);
            beat = createBeat(measure, DurationType.EIGHTH, 3);
            createNote(beat, 6, 11 + offset, wrap);
            beat = createBeat(measure, DurationType.EIGHTH, 4);
            createNote(beat, 5, 7 + offset, wrap);
            beat = createBeat(measure, DurationType.EIGHTH, 5);
            createNote(beat, 5, 9 + offset, wrap);
            beat = createBeat(measure, DurationType.EIGHTH, 6);
            createNote(beat, 5, 11 + offset, wrap);
            beat = createBeat(measure, DurationType.EIGHTH, 7);
            createNote(beat, 4, 7 + offset, wrap);
            beat = createBeat(measure, DurationType.EIGHTH, 8);
            createNote(beat, 4, 9 + offset, wrap);

            measure = createMeasure(track, 2);
            beat = createBeat(measure, DurationType.EIGHTH, 1);
            createNote(beat, 4, 11 + offset, wrap);
            beat = createBeat(measure, DurationType.EIGHTH, 2);
            createNote(beat, 3, 8 + offset, wrap);
            beat = createBeat(measure, DurationType.EIGHTH, 3);
            createNote(beat, 3, 9 + offset, wrap);
            beat = createBeat(measure, DurationType.EIGHTH, 4);
            createNote(beat, 3, 11 + offset, wrap);
            beat = createBeat(measure, DurationType.EIGHTH, 5);
            createNote(beat, 2, 9 + offset, wrap);
            beat = createBeat(measure, DurationType.EIGHTH, 6);
            createNote(beat, 2, 10 + offset, wrap);
            beat = createBeat(measure, DurationType.EIGHTH, 7);
            createNote(beat, 1, 7 + offset, wrap);
            beat = createBeat(measure, DurationType.EIGHTH, 8);
            createNote(beat, 1, 9 + offset, wrap);

            measure = createMeasure(track, 3);
            beat = createBeat(measure, DurationType.WHOLE, 1);
            createNote(beat, 1, 11 + offset, wrap);

            measure = createMeasure(track, 4);
            beat = createBeat(measure, DurationType.EIGHTH, 1);
            createNote(beat, 1, 11 + offset, wrap);
            beat = createBeat(measure, DurationType.EIGHTH, 2);
            createNote(beat, 1, 9 + offset, wrap);
            beat = createBeat(measure, DurationType.EIGHTH, 3);
            createNote(beat, 1, 7 + offset, wrap);
            beat = createBeat(measure, DurationType.EIGHTH, 4);
            createNote(beat, 2, 10 + offset, wrap);
            beat = createBeat(measure, DurationType.EIGHTH, 5);
            createNote(beat, 2, 9 + offset, wrap);
            beat = createBeat(measure, DurationType.EIGHTH, 6);
            createNote(beat, 3, 11 + offset, wrap);
            beat = createBeat(measure, DurationType.EIGHTH, 7);
            createNote(beat, 3, 9 + offset, wrap);
            beat = createBeat(measure, DurationType.EIGHTH, 8);
            createNote(beat, 3, 8 + offset, wrap);

            measure = createMeasure(track, 5);
            beat = createBeat(measure, DurationType.EIGHTH, 1);
            createNote(beat, 4, 11 + offset, wrap);
            beat = createBeat(measure, DurationType.EIGHTH, 2);
            createNote(beat, 4, 9 + offset, wrap);
            beat = createBeat(measure, DurationType.EIGHTH, 3);
            createNote(beat, 4, 7 + offset, wrap);
            beat = createBeat(measure, DurationType.EIGHTH, 4);
            createNote(beat, 5, 11 + offset, wrap);
            beat = createBeat(measure, DurationType.EIGHTH, 5);
            createNote(beat, 5, 9 + offset, wrap);
            beat = createBeat(measure, DurationType.EIGHTH, 6);
            createNote(beat, 5, 7 + offset, wrap);
            beat = createBeat(measure, DurationType.EIGHTH, 7);
            createNote(beat, 6, 11 + offset, wrap);
            beat = createBeat(measure, DurationType.EIGHTH, 8);
            createNote(beat, 6, 9 + offset, wrap);

            measure = createMeasure(track, 6);
            beat = createBeat(measure, DurationType.WHOLE, 1);
            createNote(beat, 6, 7 + offset, wrap);

            if (key.equals(KeyType.E) || key.equals(KeyType.B)) {
                offset += 1;
                continue;
            }

            offset += 2;

        }
    }

    private void writePositionVI() throws JGuitarDAOException {

        int offset = 0;
        boolean wrap = false;

        for (KeyType key : keyTypeList) {

            String name = "6th Position";
            Scale scale = new Scale();
            scale.setName(name);
            scale.setType(ScaleType.HEPTATONIC);
            scale.setKey(key);

            List<Scale> persistedScales = daoMgr.getDaoBean().getScaleDAO().findByExample(scale);
            if (CollectionUtils.isNotEmpty(persistedScales)) {
                return;
            }
            logger.info("Creating...{}", scale.toString());

            Instrument instrument = daoMgr.getDaoBean().getInstrumentDAO().findByProgram(27);
            Track track = createTrack(1, instrument);
            scale.setTrack(track);
            scale.setId(daoMgr.getDaoBean().getScaleDAO().save(scale));

            if (9 + offset > 12) {
                wrap = true;
            }

            Measure measure = createMeasure(track, 1);
            Beat beat = createBeat(measure, DurationType.EIGHTH, 1);
            createNote(beat, 6, 9 + offset, wrap);
            beat = createBeat(measure, DurationType.EIGHTH, 2);
            createNote(beat, 6, 11 + offset, wrap);
            beat = createBeat(measure, DurationType.EIGHTH, 3);
            createNote(beat, 6, 12 + offset, wrap);
            beat = createBeat(measure, DurationType.EIGHTH, 4);
            createNote(beat, 5, 9 + offset, wrap);
            beat = createBeat(measure, DurationType.EIGHTH, 5);
            createNote(beat, 5, 11 + offset, wrap);
            beat = createBeat(measure, DurationType.EIGHTH, 6);
            createNote(beat, 5, 12 + offset, wrap);
            beat = createBeat(measure, DurationType.EIGHTH, 7);
            createNote(beat, 4, 9 + offset, wrap);
            beat = createBeat(measure, DurationType.EIGHTH, 8);
            createNote(beat, 4, 11 + offset, wrap);

            measure = createMeasure(track, 2);
            beat = createBeat(measure, DurationType.EIGHTH, 1);
            createNote(beat, 4, 13 + offset, wrap);
            beat = createBeat(measure, DurationType.EIGHTH, 2);
            createNote(beat, 3, 9 + offset, wrap);
            beat = createBeat(measure, DurationType.EIGHTH, 3);
            createNote(beat, 3, 11 + offset, wrap);
            beat = createBeat(measure, DurationType.EIGHTH, 4);
            createNote(beat, 2, 9 + offset, wrap);
            beat = createBeat(measure, DurationType.EIGHTH, 5);
            createNote(beat, 2, 10 + offset, wrap);
            beat = createBeat(measure, DurationType.EIGHTH, 6);
            createNote(beat, 2, 12 + offset, wrap);
            beat = createBeat(measure, DurationType.EIGHTH, 7);
            createNote(beat, 1, 9 + offset, wrap);
            beat = createBeat(measure, DurationType.EIGHTH, 8);
            createNote(beat, 1, 11 + offset, wrap);

            measure = createMeasure(track, 3);
            beat = createBeat(measure, DurationType.WHOLE, 1);
            createNote(beat, 1, 12 + offset, wrap);

            measure = createMeasure(track, 4);
            beat = createBeat(measure, DurationType.EIGHTH, 1);
            createNote(beat, 1, 12 + offset, wrap);
            beat = createBeat(measure, DurationType.EIGHTH, 2);
            createNote(beat, 1, 11 + offset, wrap);
            beat = createBeat(measure, DurationType.EIGHTH, 3);
            createNote(beat, 1, 9 + offset, wrap);
            beat = createBeat(measure, DurationType.EIGHTH, 4);
            createNote(beat, 2, 12 + offset, wrap);
            beat = createBeat(measure, DurationType.EIGHTH, 5);
            createNote(beat, 2, 10 + offset, wrap);
            beat = createBeat(measure, DurationType.EIGHTH, 6);
            createNote(beat, 2, 9 + offset, wrap);
            beat = createBeat(measure, DurationType.EIGHTH, 7);
            createNote(beat, 3, 11 + offset, wrap);
            beat = createBeat(measure, DurationType.EIGHTH, 8);
            createNote(beat, 3, 9 + offset, wrap);

            measure = createMeasure(track, 5);
            beat = createBeat(measure, DurationType.EIGHTH, 1);
            createNote(beat, 4, 13 + offset, wrap);
            beat = createBeat(measure, DurationType.EIGHTH, 2);
            createNote(beat, 4, 11 + offset, wrap);
            beat = createBeat(measure, DurationType.EIGHTH, 3);
            createNote(beat, 4, 9 + offset, wrap);
            beat = createBeat(measure, DurationType.EIGHTH, 4);
            createNote(beat, 5, 12 + offset, wrap);
            beat = createBeat(measure, DurationType.EIGHTH, 5);
            createNote(beat, 5, 11 + offset, wrap);
            beat = createBeat(measure, DurationType.EIGHTH, 6);
            createNote(beat, 5, 9 + offset, wrap);
            beat = createBeat(measure, DurationType.EIGHTH, 7);
            createNote(beat, 6, 12 + offset, wrap);
            beat = createBeat(measure, DurationType.EIGHTH, 8);
            createNote(beat, 6, 11 + offset, wrap);

            measure = createMeasure(track, 6);
            beat = createBeat(measure, DurationType.WHOLE, 1);
            createNote(beat, 6, 9 + offset, wrap);

            if (key.equals(KeyType.E) || key.equals(KeyType.B)) {
                offset += 1;
                continue;
            }

            offset += 2;

        }
    }

    private void writePositionVII() throws JGuitarDAOException {

        int offset = 0;
        boolean wrap = false;

        for (KeyType key : keyTypeList) {

            String name = "7th Position";
            Scale scale = new Scale();
            scale.setName(name);
            scale.setType(ScaleType.HEPTATONIC);
            scale.setKey(key);

            List<Scale> persistedScales = daoMgr.getDaoBean().getScaleDAO().findByExample(scale);
            if (CollectionUtils.isNotEmpty(persistedScales)) {
                return;
            }
            logger.info("Creating...{}", scale.toString());

            Instrument instrument = daoMgr.getDaoBean().getInstrumentDAO().findByProgram(27);
            Track track = createTrack(1, instrument);
            scale.setTrack(track);
            scale.setId(daoMgr.getDaoBean().getScaleDAO().save(scale));

            if (11 + offset > 12) {
                wrap = true;
            }

            Measure measure = createMeasure(track, 1);
            Beat beat = createBeat(measure, DurationType.EIGHTH, 1);
            createNote(beat, 6, 11 + offset, wrap);
            beat = createBeat(measure, DurationType.EIGHTH, 2);
            createNote(beat, 6, 12 + offset, wrap);
            beat = createBeat(measure, DurationType.EIGHTH, 3);
            createNote(beat, 6, 14 + offset, wrap);
            beat = createBeat(measure, DurationType.EIGHTH, 4);
            createNote(beat, 5, 11 + offset, wrap);
            beat = createBeat(measure, DurationType.EIGHTH, 5);
            createNote(beat, 5, 12 + offset, wrap);
            beat = createBeat(measure, DurationType.EIGHTH, 6);
            createNote(beat, 5, 14 + offset, wrap);
            beat = createBeat(measure, DurationType.EIGHTH, 7);
            createNote(beat, 4, 11 + offset, wrap);
            beat = createBeat(measure, DurationType.EIGHTH, 8);
            createNote(beat, 4, 13 + offset, wrap);

            measure = createMeasure(track, 2);
            beat = createBeat(measure, DurationType.EIGHTH, 1);
            createNote(beat, 4, 14 + offset, wrap);
            beat = createBeat(measure, DurationType.EIGHTH, 2);
            createNote(beat, 3, 11 + offset, wrap);
            beat = createBeat(measure, DurationType.EIGHTH, 3);
            createNote(beat, 3, 13 + offset, wrap);
            beat = createBeat(measure, DurationType.EIGHTH, 4);
            createNote(beat, 3, 14 + offset, wrap);
            beat = createBeat(measure, DurationType.EIGHTH, 5);
            createNote(beat, 2, 12 + offset, wrap);
            beat = createBeat(measure, DurationType.EIGHTH, 6);
            createNote(beat, 2, 14 + offset, wrap);
            beat = createBeat(measure, DurationType.EIGHTH, 7);
            createNote(beat, 1, 11 + offset, wrap);
            beat = createBeat(measure, DurationType.EIGHTH, 8);
            createNote(beat, 1, 12 + offset, wrap);

            measure = createMeasure(track, 3);
            beat = createBeat(measure, DurationType.WHOLE, 1);
            createNote(beat, 1, 14 + offset, wrap);

            measure = createMeasure(track, 4);
            beat = createBeat(measure, DurationType.EIGHTH, 1);
            createNote(beat, 1, 14 + offset, wrap);
            beat = createBeat(measure, DurationType.EIGHTH, 2);
            createNote(beat, 1, 12 + offset, wrap);
            beat = createBeat(measure, DurationType.EIGHTH, 3);
            createNote(beat, 1, 11 + offset, wrap);
            beat = createBeat(measure, DurationType.EIGHTH, 4);
            createNote(beat, 2, 14 + offset, wrap);
            beat = createBeat(measure, DurationType.EIGHTH, 5);
            createNote(beat, 2, 12 + offset, wrap);
            beat = createBeat(measure, DurationType.EIGHTH, 6);
            createNote(beat, 3, 14 + offset, wrap);
            beat = createBeat(measure, DurationType.EIGHTH, 7);
            createNote(beat, 3, 13 + offset, wrap);
            beat = createBeat(measure, DurationType.EIGHTH, 8);
            createNote(beat, 3, 11 + offset, wrap);

            measure = createMeasure(track, 5);
            beat = createBeat(measure, DurationType.EIGHTH, 1);
            createNote(beat, 4, 14 + offset, wrap);
            beat = createBeat(measure, DurationType.EIGHTH, 2);
            createNote(beat, 4, 13 + offset, wrap);
            beat = createBeat(measure, DurationType.EIGHTH, 3);
            createNote(beat, 4, 11 + offset, wrap);
            beat = createBeat(measure, DurationType.EIGHTH, 4);
            createNote(beat, 5, 14 + offset, wrap);
            beat = createBeat(measure, DurationType.EIGHTH, 5);
            createNote(beat, 5, 12 + offset, wrap);
            beat = createBeat(measure, DurationType.EIGHTH, 6);
            createNote(beat, 5, 11 + offset, wrap);
            beat = createBeat(measure, DurationType.EIGHTH, 7);
            createNote(beat, 6, 14 + offset, wrap);
            beat = createBeat(measure, DurationType.EIGHTH, 8);
            createNote(beat, 6, 12 + offset, wrap);

            measure = createMeasure(track, 6);
            beat = createBeat(measure, DurationType.WHOLE, 1);
            createNote(beat, 6, 11 + offset, wrap);

            if (key.equals(KeyType.E) || key.equals(KeyType.B)) {
                offset += 1;
                continue;
            }

            offset += 2;

        }
    }

}
