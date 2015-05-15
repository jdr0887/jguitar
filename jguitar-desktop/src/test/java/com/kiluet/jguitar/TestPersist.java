package com.kiluet.jguitar;

import static org.junit.Assert.assertTrue;

import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.junit.Test;

import com.kiluet.jguitar.dao.JGuitarDAOException;
import com.kiluet.jguitar.dao.JGuitarDAOManager;
import com.kiluet.jguitar.dao.model.KeyType;
import com.kiluet.jguitar.dao.model.Scale;
import com.kiluet.jguitar.dao.model.ScaleType;
import com.kiluet.jguitar.desktop.scales.PersistInstrumentsRunnable;
import com.kiluet.jguitar.desktop.scales.PersistPentatonicScalesRunnable;

public class TestPersist {

    @Test
    public void testPersistPentatonicRunnable() {

        try {
            ExecutorService es = Executors.newSingleThreadExecutor();
            es.submit(new PersistInstrumentsRunnable()).get();
            es.submit(new PersistPentatonicScalesRunnable()).get();
            es.shutdown();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        JGuitarDAOManager daoMgr = JGuitarDAOManager.getInstance();
        try {
            List<Scale> scales = daoMgr.getDaoBean().getScaleDAO().findByKeyAndType(KeyType.A, ScaleType.PENTATONIC);
            assertTrue(scales != null);
        } catch (JGuitarDAOException e) {
            e.printStackTrace();
        }

    }

}
