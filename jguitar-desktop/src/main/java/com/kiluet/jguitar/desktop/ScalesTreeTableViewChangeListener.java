package com.kiluet.jguitar.desktop;

import java.util.ArrayList;
import java.util.List;

import com.kiluet.jguitar.dao.JGuitarDAOException;
import com.kiluet.jguitar.dao.JGuitarDAOManager;
import com.kiluet.jguitar.dao.model.KeyType;
import com.kiluet.jguitar.dao.model.Scale;
import com.kiluet.jguitar.dao.model.ScaleType;
import com.kiluet.jguitar.dao.model.Track;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.TreeItem;

public class ScalesTreeTableViewChangeListener implements ChangeListener<TreeItem<String>> {

    private final JGuitarDAOManager daoMgr = JGuitarDAOManager.getInstance();

    public JGuitarController controller;

    public ScalesTreeTableViewChangeListener(JGuitarController controller) {
        super();
        this.controller = controller;
    }

    @Override
    public void changed(ObservableValue<? extends TreeItem<String>> observable, TreeItem<String> oldValue,
            TreeItem<String> newValue) {
        if (newValue != null && newValue.isLeaf()) {
            String name = newValue.getValue();
            String key = newValue.getParent().getValue();
            String type = newValue.getParent().getParent().getValue();

            List<Track> tracks = new ArrayList<Track>();
            try {
                Scale scale = new Scale();
                scale.setName(name);
                scale.setKey(KeyType.valueOf(key));
                scale.setType(ScaleType.valueOf(type.toUpperCase()));
                List<Scale> scales = daoMgr.getDaoBean().getScaleDAO().findByExample(scale);
                if (scales != null && !scales.isEmpty()) {
                    tracks.add(scales.get(0).getTrack());
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
