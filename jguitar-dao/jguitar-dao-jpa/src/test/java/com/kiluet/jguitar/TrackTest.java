package com.kiluet.jguitar;

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

            Map<Integer, List<Beat>> trackBeatMap = new HashMap<Integer, List<Beat>>();

            for (Track track : song.getTracks()) {
                if (trackBeatMap.get(track.getNumber()) == null) {
                    trackBeatMap.put(track.getNumber(), new LinkedList<Beat>());
                }
            }

            for (Track track : song.getTracks()) {
                for (Measure measure : track.getMeasures()) {
                    for (Beat beat : measure.getBeats()) {
                        trackBeatMap.get(track.getNumber()).add(beat);
                    }
                }
            }

            for (Integer key : trackBeatMap.keySet()) {
                System.out.println(key);
                for (Beat beat : trackBeatMap.get(key)) {
                    System.out.println(beat);
                }
            }

        } catch (JGuitarDAOException e) {
            e.printStackTrace();
        }

    }

}
