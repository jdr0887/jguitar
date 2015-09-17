package com.kiluet.jguitar.desktop.components;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.TextField;

public class NoteTextField extends TextField {

    public NoteTextField() {
        super();
        setMaxWidth(18);
        colorBlack();
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
