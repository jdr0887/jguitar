package com.kiluet.jguitar.desktop;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.kiluet.jguitar.config.Config;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class Main extends Application {

    private static final Logger logger = LoggerFactory.getLogger(Main.class);

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
    public void init() throws Exception {
        super.init();
    }

    @Override
    public void start(final Stage stage) throws Exception {
        logger.debug("ENTERING start(Stage)");

        this.stage = stage;
        this.stage.setTitle("JGuitar");

        try {

            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Main.class.getResource("Splash.fxml"));
            Pane rootPane = loader.load();

            SplashController controller = loader.getController();
            controller.setApp(this);

            Scene scene = new Scene(rootPane);
            this.stage.initStyle(StageStyle.UNDECORATED);
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

    public void setStage(Stage stage) {
        this.stage = stage;
    }

}
