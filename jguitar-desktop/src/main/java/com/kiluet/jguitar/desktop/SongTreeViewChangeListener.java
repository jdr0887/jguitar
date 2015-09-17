package com.kiluet.jguitar.desktop;

import java.util.List;

import org.apache.commons.collections.CollectionUtils;

import com.kiluet.jguitar.dao.JGuitarDAOException;
import com.kiluet.jguitar.dao.JGuitarDAOManager;
import com.kiluet.jguitar.dao.model.Song;
import com.kiluet.jguitar.desktop.components.SongPane;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.TreeItem;

public class SongTreeViewChangeListener implements ChangeListener<TreeItem<String>> {

    private final JGuitarDAOManager daoMgr = JGuitarDAOManager.getInstance();

    public JGuitarController controller;

    public SongTreeViewChangeListener(JGuitarController controller) {
        super();
        this.controller = controller;
    }

    @Override
    public void changed(ObservableValue<? extends TreeItem<String>> observable, TreeItem<String> oldValue,
            TreeItem<String> newValue) {
        if (newValue != null && newValue.isLeaf()) {
            Song song = null;
            try {
                List<Song> songs = daoMgr.getDaoBean().getSongDAO().findByName(newValue.getValue());
                if (CollectionUtils.isEmpty(songs)) {
                    Alert alert = new Alert(AlertType.ERROR);
                    alert.setTitle("Song not found");
                    alert.setHeaderText("Song not found");
                    alert.setContentText("Song not found");
                    alert.showAndWait();
                } else {
                    song = songs.get(0);
                    controller.getNotationBox().getChildren().clear();
                    SongPane songPane = new SongPane(controller, song);
                    controller.getNotationBox().getChildren().add(songPane);
                }
            } catch (JGuitarDAOException e) {
                e.printStackTrace();
            }

        }

    }

}
