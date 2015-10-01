package com.kiluet.jguitar;

import java.util.List;

import javax.sound.midi.MidiSystem;
import javax.sound.midi.MidiUnavailableException;
import javax.sound.midi.Synthesizer;

import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.kiluet.jguitar.dao.JGuitarDAOException;
import com.kiluet.jguitar.dao.model.Instrument;
import com.kiluet.jguitar.dao.model.InstrumentString;

public class InstrumentsPersistRunnable extends AbstractPersistRunnable {

    private static final Logger logger = LoggerFactory.getLogger(InstrumentsPersistRunnable.class);

    public InstrumentsPersistRunnable() {
        super();
    }

    @Override
    public String getName() {
        return "Instruments";
    }

    @Override
    public void run() {
        logger.info("ENTERING run()");
        try {
            createInstruments();
        } catch (JGuitarDAOException e) {
            e.printStackTrace();
        }
    }

    private void createInstruments() throws JGuitarDAOException {

        try {
            Synthesizer synthesizer = MidiSystem.getSynthesizer();
            synthesizer.open();
            javax.sound.midi.Instrument[] instruments = synthesizer.getAvailableInstruments();
            for (javax.sound.midi.Instrument instrument : instruments) {
                if (instrument.getPatch().getBank() == 0) {
                    if (instrument.getName().toLowerCase().contains("guitar")) {
                        List<Instrument> foundInstruments = daoMgr.getDaoBean().getInstrumentDAO()
                                .findByName(instrument.getName());
                        if (CollectionUtils.isNotEmpty(foundInstruments)) {
                            continue;
                        }
                        Instrument jguitarInstrument = new Instrument(instrument.getName(),
                                instrument.getPatch().getProgram());
                        jguitarInstrument.getStrings().add(new InstrumentString(jguitarInstrument, 6, 40));
                        jguitarInstrument.getStrings().add(new InstrumentString(jguitarInstrument, 5, 45));
                        jguitarInstrument.getStrings().add(new InstrumentString(jguitarInstrument, 4, 50));
                        jguitarInstrument.getStrings().add(new InstrumentString(jguitarInstrument, 3, 55));
                        jguitarInstrument.getStrings().add(new InstrumentString(jguitarInstrument, 2, 59));
                        jguitarInstrument.getStrings().add(new InstrumentString(jguitarInstrument, 1, 64));
                        daoMgr.getDaoBean().getInstrumentDAO().save(jguitarInstrument);
                    }

                    if (instrument.getName().toLowerCase().contains("bass")) {
                        List<Instrument> foundInstruments = daoMgr.getDaoBean().getInstrumentDAO()
                                .findByName(instrument.getName());
                        if (CollectionUtils.isNotEmpty(foundInstruments)) {
                            continue;
                        }
                        Instrument jguitarInstrument = new Instrument(instrument.getName(),
                                instrument.getPatch().getProgram());
                        jguitarInstrument.getStrings().add(new InstrumentString(jguitarInstrument, 4, 28));
                        jguitarInstrument.getStrings().add(new InstrumentString(jguitarInstrument, 3, 33));
                        jguitarInstrument.getStrings().add(new InstrumentString(jguitarInstrument, 2, 38));
                        jguitarInstrument.getStrings().add(new InstrumentString(jguitarInstrument, 1, 43));
                        daoMgr.getDaoBean().getInstrumentDAO().save(jguitarInstrument);
                    }

                    if (instrument.getName().equalsIgnoreCase("cello")) {
                        List<Instrument> foundInstruments = daoMgr.getDaoBean().getInstrumentDAO()
                                .findByName(instrument.getName());
                        if (CollectionUtils.isNotEmpty(foundInstruments)) {
                            continue;
                        }
                        Instrument jguitarInstrument = new Instrument(instrument.getName(),
                                instrument.getPatch().getProgram());
                        jguitarInstrument.getStrings().add(new InstrumentString(jguitarInstrument, 4, 36));
                        jguitarInstrument.getStrings().add(new InstrumentString(jguitarInstrument, 3, 43));
                        jguitarInstrument.getStrings().add(new InstrumentString(jguitarInstrument, 2, 50));
                        jguitarInstrument.getStrings().add(new InstrumentString(jguitarInstrument, 1, 57));
                        daoMgr.getDaoBean().getInstrumentDAO().save(jguitarInstrument);
                    }

                    if (instrument.getName().equalsIgnoreCase("violin")) {
                        List<Instrument> foundInstruments = daoMgr.getDaoBean().getInstrumentDAO()
                                .findByName(instrument.getName());
                        if (CollectionUtils.isNotEmpty(foundInstruments)) {
                            continue;
                        }
                        Instrument jguitarInstrument = new Instrument(instrument.getName(),
                                instrument.getPatch().getProgram());
                        jguitarInstrument.getStrings().add(new InstrumentString(jguitarInstrument, 4, 55));
                        jguitarInstrument.getStrings().add(new InstrumentString(jguitarInstrument, 3, 62));
                        jguitarInstrument.getStrings().add(new InstrumentString(jguitarInstrument, 2, 69));
                        jguitarInstrument.getStrings().add(new InstrumentString(jguitarInstrument, 1, 76));
                        daoMgr.getDaoBean().getInstrumentDAO().save(jguitarInstrument);
                    }

                    if (instrument.getName().equalsIgnoreCase("viola")) {
                        List<Instrument> foundInstruments = daoMgr.getDaoBean().getInstrumentDAO()
                                .findByName(instrument.getName());
                        if (CollectionUtils.isNotEmpty(foundInstruments)) {
                            continue;
                        }
                        Instrument jguitarInstrument = new Instrument(instrument.getName(),
                                instrument.getPatch().getProgram());
                        jguitarInstrument.getStrings().add(new InstrumentString(jguitarInstrument, 4, 48));
                        jguitarInstrument.getStrings().add(new InstrumentString(jguitarInstrument, 3, 55));
                        jguitarInstrument.getStrings().add(new InstrumentString(jguitarInstrument, 2, 62));
                        jguitarInstrument.getStrings().add(new InstrumentString(jguitarInstrument, 1, 69));
                        daoMgr.getDaoBean().getInstrumentDAO().save(jguitarInstrument);
                    }

                }
            }
            synthesizer.close();

        } catch (MidiUnavailableException e) {
            e.printStackTrace();
        }

    }

}
