package com.kiluet.jguitar.desktop;

import java.util.List;

import org.apache.commons.collections.CollectionUtils;

import com.kiluet.jguitar.dao.JGuitarDAOException;
import com.kiluet.jguitar.dao.JGuitarDAOManager;
import com.kiluet.jguitar.dao.model.KeyType;
import com.kiluet.jguitar.dao.model.Scale;
import com.kiluet.jguitar.dao.model.ScaleType;
import com.kiluet.jguitar.desktop.components.ScalePane;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.TreeItem;

public class ScalesTreeViewChangeListener implements ChangeListener<TreeItem<String>> {

    private final JGuitarDAOManager daoMgr = JGuitarDAOManager.getInstance();

    public JGuitarController controller;

    public ScalesTreeViewChangeListener(JGuitarController controller) {
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

            Scale scale = new Scale();
            try {
                scale.setName(name);
                scale.setKey(KeyType.valueOf(key));
                scale.setType(ScaleType.valueOf(type.toUpperCase()));
                List<Scale> scales = daoMgr.getDaoBean().getScaleDAO().findByExample(scale);
                if (CollectionUtils.isEmpty(scales)) {
                    Alert alert = new Alert(AlertType.ERROR);
                    alert.setTitle("Scale not found");
                    alert.setHeaderText("Scale not found");
                    alert.setContentText("Scale not found");
                    alert.showAndWait();
                } else {
                    scale = scales.get(0);
                    controller.getNotationBox().getChildren().clear();
                    controller.getNotationBox().getChildren().add(new ScalePane(this.controller, scale));
                }
            } catch (JGuitarDAOException e) {
                e.printStackTrace();
            }
        }

    }

}
