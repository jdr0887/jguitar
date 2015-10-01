package com.kiluet.jguitar.desktop.components;

import org.apache.commons.lang3.StringUtils;

import com.kiluet.jguitar.dao.JGuitarDAOManager;
import com.kiluet.jguitar.dao.model.Note;

import javafx.scene.control.TextField;

public class NoteTextField extends TextField {

    private static final JGuitarDAOManager daoMgr = JGuitarDAOManager.getInstance();

    public NoteTextField(Note note) {
        super();
        setId(String.format("NoteTextField_%d_%d_%d_%d", note.getBeat().getMeasure().getTrack().getId(),
                note.getBeat().getMeasure().getNumber(), note.getBeat().getNumber(), note.getString()));
        setMaxWidth(18);

        textProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue.startsWith("0") && newValue.length() == 2) {
                newValue = newValue.replaceFirst("0", "");
            }
            if (newValue.length() > 2) {
                newValue = oldValue;
            }
            try {
                if (StringUtils.isNotEmpty(newValue)) {
                    note.setValue(Integer.valueOf(newValue));
                } else {
                    note.setValue(null);
                }
                daoMgr.getDaoBean().getNoteDAO().save(note);
            } catch (Exception e1) {
                e1.printStackTrace();
            }
        });

        colorBlack();
    }

    public void colorRed() {
        setStyle(
                "-fx-text-fill: red; -fx-alignment: center; -fx-background-color: transparent; -fx-background-insets: 0; -fx-background-radius: 0; -fx-padding: 0; -fx-pref-column-count: 1; -fx-font-size: 12;");
    }

    public void colorBlack() {
        setStyle(
                "-fx-text-fill: black; -fx-alignment: center; -fx-background-color: transparent; -fx-background-insets: 0; -fx-background-radius: 0; -fx-padding: 0; -fx-pref-column-count: 1; -fx-font-size: 12;");
    }

    @Override
    public void replaceText(int start, int end, String text) {
        if (text.matches("[0-9]*")) {
            super.replaceText(start, end, text);
        }
    }

    @Override
    public void replaceSelection(String text) {
        if (text.matches("[0-9]*")) {
            super.replaceSelection(text);
        }
    }

}
