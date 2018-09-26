package com.kiluet.jguitar.desktop;

import static org.junit.Assert.assertTrue;

import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.junit.Test;

import com.kiluet.jguitar.InstrumentsPersistRunnable;
import com.kiluet.jguitar.dao.JGuitarDAOException;
import com.kiluet.jguitar.dao.JGuitarDAOManager;
import com.kiluet.jguitar.dao.model.KeyType;
import com.kiluet.jguitar.dao.model.Scale;
import com.kiluet.jguitar.dao.model.ScaleType;
import com.kiluet.jguitar.dao.model.Song;
import com.kiluet.jguitar.scales.pentatonic.PentatonicScalesPersistFirstPositionRunnable;

public class TestPersist {

    @Test
    public void testPersistPentatonicRunnable() {

        try {
            ExecutorService es = Executors.newSingleThreadExecutor();
            es.submit(new InstrumentsPersistRunnable()).get();
            es.submit(new PentatonicScalesPersistFirstPositionRunnable()).get();
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

    @Test
    public void testGetTemplate() throws Exception {

        JGuitarDAOManager daoMgr = JGuitarDAOManager.getInstance();
        List<Song> songs = daoMgr.getDaoBean().getSongDAO().findByTitle("Template");
        assertTrue(songs != null);

    }

}
