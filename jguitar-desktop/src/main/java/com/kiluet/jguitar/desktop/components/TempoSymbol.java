package com.kiluet.jguitar.desktop.components;

import javafx.geometry.VPos;
import javafx.scene.Group;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.shape.Ellipse;
import javafx.scene.shape.Line;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.transform.Rotate;

public class TempoSymbol extends GridPane {

    private Integer tempo;

    public TempoSymbol(Integer tempo) {
        super();
        this.tempo = tempo;
        init();
    }

    private void init() {

        Group group = new Group();
        Line line = new Line(1.35, 9, 1.35, -10);
        line.setStrokeWidth(.75);
        group.getChildren().add(line);
        Ellipse ellipse = new Ellipse(6, 6, 2.75, 5);
        ellipse.getTransforms().add(new Rotate(65));
        group.getChildren().add(ellipse);

        add(group, 0, 0);
        Text text = new Text(String.format(" = %d", this.tempo));
        // Text text = new Text(" = ");
        text.setFont(Font.font(java.awt.Font.SANS_SERIF, FontWeight.THIN, 10));
        add(text, 1, 0);
        GridPane.setValignment(text, VPos.BOTTOM);

        // TextField textField = new TextField();
        // textField.setText(tempo.toString());
        // textField.setMaxWidth(30);
        // textField.setStyle(
        // "-fx-text-fill: black; -fx-alignment: center; -fx-background-color: transparent; -fx-background-insets: 0;
        // -fx-background-radius: 0; -fx-padding: 0; -fx-pref-column-count: 3; -fx-font-size: 10;");
        // add(textField, 2, 0);
        // GridPane.setValignment(textField, VPos.BOTTOM);

    }

}
