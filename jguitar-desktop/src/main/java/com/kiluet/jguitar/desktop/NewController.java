package com.kiluet.jguitar.desktop;

import org.apache.commons.lang3.StringUtils;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class NewController {

    private Main app;

    private Stage dialogStage;

    @FXML
    private TextField name;

    public NewController() {
        super();
    }

    @FXML
    private void doOK(final ActionEvent event) {
        if (isInputValid()) {
            // app.createNew(this.name.getText());
            dialogStage.close();
        }
    }

    @FXML
    private void doCancel(final ActionEvent event) {
        dialogStage.close();
    }

    public Main getApp() {
        return app;
    }

    public void setApp(Main app) {
        this.app = app;
    }

    private boolean isInputValid() {
        String errorMessage = "";

        if (StringUtils.isEmpty(name.getText())) {
            errorMessage += "No valid name!\n";
        }

        if (StringUtils.isEmpty(errorMessage)) {
            return true;
        } else {
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Invalid Fields");
            alert.setContentText("Please correct invalid fields");
            alert.showAndWait();
            return false;
        }
    }

    public Stage getDialogStage() {
        return dialogStage;
    }

    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }

    public TextField getName() {
        return name;
    }

    public void setName(TextField name) {
        this.name = name;
    }

}
