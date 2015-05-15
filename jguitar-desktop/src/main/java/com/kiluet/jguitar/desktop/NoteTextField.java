package com.kiluet.jguitar.desktop;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.TextField;

public class NoteTextField extends TextField {

    public NoteTextField() {
        super();
        this.textProperty().addListener(new ChangeListener<String>() {

            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                if (newValue.startsWith("0") && newValue.length() == 2) {
                    setText(newValue.replaceFirst("0", ""));
                }
                if (newValue.length() > 2) {
                    setText(oldValue);
                }
            }

        });
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
