package com.kiluet.jguitar.desktop;

import javafx.scene.control.ListCell;

import com.kiluet.jguitar.model.Song;

public class SongCell extends ListCell<Song> {

    @Override
    protected void updateItem(Song item, boolean empty) {
        super.updateItem(item, empty);
        if (item != null) {
            setText(item.getName());
        }
    }

}
