package com.kiluet.jguitar.desktop;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.kiluet.jguitar.config.Config;

public class Main extends Application {

    private final Logger logger = LoggerFactory.getLogger(Main.class);

    private final Config config = Config.getInstance();

    private Stage stage;

    public Main() {
        super();
    }

    public String getVersion() {
        String version = config.getProperties().getProperty("version");
        return StringUtils.isNotEmpty(version) ? version : "0.0.1-SNAPSHOT";
    }

    @Override
    public void start(final Stage stage) throws Exception {
        logger.debug("ENTERING start(Stage)");
        this.stage = stage;
        this.stage.setTitle("JGuitar");

        try {

            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Main.class.getResource("JGuitar.fxml"));
            BorderPane rootPane = loader.load();

            JGuitarController controller = loader.getController();
            controller.setApp(this);

            Scene scene = new Scene(rootPane);
            this.stage.setScene(scene);
            this.stage.sizeToScene();

            this.stage.show();

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public static void main(String[] args) {
        try {
            Main.launch(Main.class, args);
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(-1);
        }
    }

    public Stage getStage() {
        return stage;
    }

    public void setPrimaryStage(Stage stage) {
        this.stage = stage;
    }

}
