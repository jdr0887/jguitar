package com.kiluet.jguitar;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import javax.sound.midi.MidiSystem;
import javax.sound.midi.Synthesizer;

import org.apache.commons.collections.CollectionUtils;

import com.kiluet.jguitar.dao.model.Instrument;
import com.kiluet.jguitar.dao.model.InstrumentString;

import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@NoArgsConstructor
public class InstrumentsPersistRunnable extends AbstractPersistRunnable {

    @Override
    public String getName() {
        return "Instruments";
    }

    @Override
    public void run() {
        try (Synthesizer synthesizer = MidiSystem.getSynthesizer()) {

            List<javax.sound.midi.Instrument> instruments = Arrays.asList(synthesizer.getAvailableInstruments()).stream()
                    .filter(a -> a.getName().toLowerCase().contains("guitar") && a.getPatch().getBank() == 0).collect(Collectors.toList());

            if (CollectionUtils.isNotEmpty(instruments)) {
                for (javax.sound.midi.Instrument instrument : instruments) {

                    if (CollectionUtils.isEmpty(daoMgr.getDaoBean().getInstrumentDAO().findByName(instrument.getName()))) {
                        Instrument jguitarInstrument = new Instrument(instrument.getName(), instrument.getPatch().getProgram());
                        List<InstrumentString> instrumentStrings = new ArrayList<>();
                        instrumentStrings.add(new InstrumentString(jguitarInstrument, 6, 40));
                        instrumentStrings.add(new InstrumentString(jguitarInstrument, 5, 45));
                        instrumentStrings.add(new InstrumentString(jguitarInstrument, 4, 50));
                        instrumentStrings.add(new InstrumentString(jguitarInstrument, 3, 55));
                        instrumentStrings.add(new InstrumentString(jguitarInstrument, 2, 59));
                        instrumentStrings.add(new InstrumentString(jguitarInstrument, 1, 64));
                        jguitarInstrument.setStrings(instrumentStrings);
                        daoMgr.getDaoBean().getInstrumentDAO().save(jguitarInstrument);
                    }

                }
            }

            instruments = Arrays.asList(synthesizer.getAvailableInstruments()).stream()
                    .filter(a -> a.getName().toLowerCase().contains("bass") && a.getPatch().getBank() == 0).collect(Collectors.toList());

            if (CollectionUtils.isNotEmpty(instruments)) {
                for (javax.sound.midi.Instrument instrument : instruments) {
                    if (CollectionUtils.isEmpty(daoMgr.getDaoBean().getInstrumentDAO().findByName(instrument.getName()))) {
                        Instrument jguitarInstrument = new Instrument(instrument.getName(), instrument.getPatch().getProgram());
                        List<InstrumentString> instrumentStrings = new ArrayList<>();
                        instrumentStrings.add(new InstrumentString(jguitarInstrument, 4, 28));
                        instrumentStrings.add(new InstrumentString(jguitarInstrument, 3, 33));
                        instrumentStrings.add(new InstrumentString(jguitarInstrument, 2, 38));
                        instrumentStrings.add(new InstrumentString(jguitarInstrument, 1, 43));
                        jguitarInstrument.setStrings(instrumentStrings);
                        daoMgr.getDaoBean().getInstrumentDAO().save(jguitarInstrument);
                    }
                }
            }

            instruments = Arrays.asList(synthesizer.getAvailableInstruments()).stream()
                    .filter(a -> a.getName().toLowerCase().contains("cello") && a.getPatch().getBank() == 0).collect(Collectors.toList());

            if (CollectionUtils.isNotEmpty(instruments)) {
                for (javax.sound.midi.Instrument instrument : instruments) {
                    if (CollectionUtils.isEmpty(daoMgr.getDaoBean().getInstrumentDAO().findByName(instrument.getName()))) {
                        Instrument jguitarInstrument = new Instrument(instrument.getName(), instrument.getPatch().getProgram());
                        List<InstrumentString> instrumentStrings = new ArrayList<>();
                        instrumentStrings.add(new InstrumentString(jguitarInstrument, 4, 36));
                        instrumentStrings.add(new InstrumentString(jguitarInstrument, 3, 43));
                        instrumentStrings.add(new InstrumentString(jguitarInstrument, 2, 50));
                        instrumentStrings.add(new InstrumentString(jguitarInstrument, 1, 57));
                        jguitarInstrument.setStrings(instrumentStrings);
                        daoMgr.getDaoBean().getInstrumentDAO().save(jguitarInstrument);
                    }
                }
            }

            instruments = Arrays.asList(synthesizer.getAvailableInstruments()).stream()
                    .filter(a -> a.getName().toLowerCase().contains("violin") && a.getPatch().getBank() == 0).collect(Collectors.toList());

            if (CollectionUtils.isNotEmpty(instruments)) {
                for (javax.sound.midi.Instrument instrument : instruments) {
                    if (CollectionUtils.isEmpty(daoMgr.getDaoBean().getInstrumentDAO().findByName(instrument.getName()))) {
                        Instrument jguitarInstrument = new Instrument(instrument.getName(), instrument.getPatch().getProgram());
                        List<InstrumentString> instrumentStrings = new ArrayList<>();
                        instrumentStrings.add(new InstrumentString(jguitarInstrument, 4, 55));
                        instrumentStrings.add(new InstrumentString(jguitarInstrument, 3, 62));
                        instrumentStrings.add(new InstrumentString(jguitarInstrument, 2, 69));
                        instrumentStrings.add(new InstrumentString(jguitarInstrument, 1, 76));
                        jguitarInstrument.setStrings(instrumentStrings);
                        daoMgr.getDaoBean().getInstrumentDAO().save(jguitarInstrument);
                    }
                }
            }

            instruments = Arrays.asList(synthesizer.getAvailableInstruments()).stream()
                    .filter(a -> a.getName().toLowerCase().contains("viola") && a.getPatch().getBank() == 0).collect(Collectors.toList());

            if (CollectionUtils.isNotEmpty(instruments)) {
                for (javax.sound.midi.Instrument instrument : instruments) {
                    if (CollectionUtils.isEmpty(daoMgr.getDaoBean().getInstrumentDAO().findByName(instrument.getName()))) {
                        Instrument jguitarInstrument = new Instrument(instrument.getName(), instrument.getPatch().getProgram());
                        List<InstrumentString> instrumentStrings = new ArrayList<>();
                        instrumentStrings.add(new InstrumentString(jguitarInstrument, 4, 48));
                        instrumentStrings.add(new InstrumentString(jguitarInstrument, 3, 55));
                        instrumentStrings.add(new InstrumentString(jguitarInstrument, 2, 62));
                        instrumentStrings.add(new InstrumentString(jguitarInstrument, 1, 69));
                        jguitarInstrument.setStrings(instrumentStrings);
                        daoMgr.getDaoBean().getInstrumentDAO().save(jguitarInstrument);
                    }
                }
            }

        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
    }

    public static void main(String[] args) {
        InstrumentsPersistRunnable runnable = new InstrumentsPersistRunnable();
        runnable.run();
    }

}
