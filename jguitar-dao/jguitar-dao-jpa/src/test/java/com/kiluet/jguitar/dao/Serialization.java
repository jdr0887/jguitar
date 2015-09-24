package com.kiluet.jguitar.dao;

import org.junit.Test;

import com.kiluet.jguitar.dao.JGuitarDAOException;
import com.kiluet.jguitar.dao.JGuitarDAOManager;
import com.kiluet.jguitar.dao.model.Instrument;
import com.kiluet.jguitar.dao.model.InstrumentString;

public class Serialization {

    private final JGuitarDAOManager daoMgr = JGuitarDAOManager.getInstance();

    @Test
    public void scratch() {
        try {
            Instrument instrument = new Instrument("Electric Guitar", 27);
            instrument.getStrings().add(new InstrumentString(6, 40, instrument));
            instrument.getStrings().add(new InstrumentString(5, 45, instrument));
            instrument.getStrings().add(new InstrumentString(4, 50, instrument));
            instrument.getStrings().add(new InstrumentString(3, 55, instrument));
            instrument.getStrings().add(new InstrumentString(2, 59, instrument));
            instrument.getStrings().add(new InstrumentString(1, 64, instrument));
            daoMgr.getDaoBean().getInstrumentDAO().save(instrument);
        } catch (JGuitarDAOException e) {
            e.printStackTrace();
        }

    }

}
