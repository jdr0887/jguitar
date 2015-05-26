package com.kiluet.jguitar.desktop;

import java.util.ArrayList;
import java.util.List;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.TreeItem;

import com.kiluet.jguitar.dao.JGuitarDAOException;
import com.kiluet.jguitar.dao.JGuitarDAOManager;
import com.kiluet.jguitar.dao.model.Song;
import com.kiluet.jguitar.dao.model.Track;

public class SongTreeTableViewChangeListener implements ChangeListener<TreeItem<String>> {

    private final JGuitarDAOManager daoMgr = JGuitarDAOManager.getInstance();

    public JGuitarController controller;

    public SongTreeTableViewChangeListener(JGuitarController controller) {
        super();
        this.controller = controller;
    }

    @Override
    public void changed(ObservableValue<? extends TreeItem<String>> observable, TreeItem<String> oldValue,
            TreeItem<String> newValue) {
        if (newValue != null && newValue.isLeaf()) {
            List<Track> tracks = new ArrayList<Track>();
            try {
                List<Song> songs = daoMgr.getDaoBean().getSongDAO().findByName(newValue.getValue());
                if (songs != null && !songs.isEmpty()) {
                    tracks.addAll(daoMgr.getDaoBean().getTrackDAO().findBySongId(songs.get(0).getId()));
                }
            } catch (JGuitarDAOException e) {
                e.printStackTrace();
            }

            if (!tracks.isEmpty()) {
                controller.getNotationBox().getChildren().clear();
                controller.getNotationBox().getChildren().add(controller.drawSongPane(tracks));
            }
        }

    }

}
