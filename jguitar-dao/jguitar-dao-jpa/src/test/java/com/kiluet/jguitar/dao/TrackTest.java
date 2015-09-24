package com.kiluet.jguitar.dao;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.junit.Test;

import com.kiluet.jguitar.dao.JGuitarDAOException;
import com.kiluet.jguitar.dao.JGuitarDAOManager;
import com.kiluet.jguitar.dao.model.Beat;
import com.kiluet.jguitar.dao.model.Measure;
import com.kiluet.jguitar.dao.model.Song;
import com.kiluet.jguitar.dao.model.Track;

public class TrackTest {

    @Test
    public void trackBeatTest() {

        JGuitarDAOManager daoMgr = JGuitarDAOManager.getInstance();
        try {
            Song song = daoMgr.getDaoBean().getSongDAO().findByName("Template").get(0);

            Map<String, List<Beat>> beatMap = new HashMap<String, List<Beat>>();

            for (Track track : song.getTracks()) {
                for (Measure measure : track.getMeasures()) {
                    for (Beat beat : measure.getBeats()) {
                        String key = String.format("%d_%d", measure.getNumber(), beat.getNumber());
                        // System.out.println(key);
                        if (beatMap.get(key) == null) {
                            beatMap.put(key, new LinkedList<Beat>());
                        }
                        beatMap.get(key).add(beat);
                    }
                }
            }

            for (String key : beatMap.keySet()) {
                for (Beat beat : beatMap.get(key)) {
                    System.out.println(beat.getMeasure().getTrack().getId());
                }
            }

        } catch (JGuitarDAOException e) {
            e.printStackTrace();
        }

    }

    @Test
    public void trackBeatTest2() {

        JGuitarDAOManager daoMgr = JGuitarDAOManager.getInstance();
        try {
            Song song = daoMgr.getDaoBean().getSongDAO().findByName("Template").get(0);

            for (Measure measure : song.getTracks().get(0).getMeasures()) {
                List<Beat> beats = daoMgr.getDaoBean().getBeatDAO().findBySongIdAndMeasureNumber(song.getId(),
                        measure.getNumber());
                for (Beat beat : beats) {
                    System.out.println(beat.getMeasure().getTrack().getId());
                }
            }

        } catch (JGuitarDAOException e) {
            e.printStackTrace();
        }

    }

}
