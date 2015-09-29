package com.kiluet.jguitar.desktop;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.kiluet.jguitar.HeptatonicScalesPersistRunnable;
import com.kiluet.jguitar.InstrumentsPersistRunnable;
import com.kiluet.jguitar.PentatonicScalesPersistRunnable;
import com.kiluet.jguitar.TemplatePersistRunnable;

import javafx.animation.FadeTransition;
import javafx.concurrent.Task;
import javafx.concurrent.Worker;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.ProgressBar;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;

public class SplashController extends Pane implements Initializable {

    private static final Logger logger = LoggerFactory.getLogger(SplashController.class);

    @FXML
    private VBox splashBox;

    @FXML
    private ProgressBar progressBar;

    @FXML
    private ImageView imageView;

    private Main app;

    public SplashController() {
        super();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        setEffect(new DropShadow());
        Task<Void> task = new Task<Void>() {
            @Override
            protected Void call() throws Exception {
                ExecutorService es = Executors.newSingleThreadExecutor();
                es.submit(new InstrumentsPersistRunnable());
                es.submit(new TemplatePersistRunnable());
                es.submit(new HeptatonicScalesPersistRunnable());
                es.submit(new PentatonicScalesPersistRunnable());
                es.shutdown();
                es.awaitTermination(10, TimeUnit.SECONDS);
                updateProgress(1, 1);
                
                return null;
            }
        };
        
        progressBar.progressProperty().bind(task.progressProperty());

        task.stateProperty().addListener((observableValue, oldState, newState) -> {
            if (newState == Worker.State.SUCCEEDED) {
                progressBar.progressProperty().unbind();
                progressBar.setProgress(1);
                app.getStage().toFront();
                FadeTransition fadeSplash = new FadeTransition(Duration.seconds(1), splashBox);
                fadeSplash.setFromValue(1.0);
                fadeSplash.setToValue(0.0);
                fadeSplash.setOnFinished(actionEvent -> {
                    app.getStage().hide();
                    showJGuitar();
                });
                fadeSplash.play();
            }
        });

        new Thread(task).start();
    }

    private void showJGuitar() {
        Stage mainStage = new Stage(StageStyle.DECORATED);
        mainStage.setTitle("JGuitar");
        mainStage.setOnCloseRequest(e -> System.exit(0));
        app.setStage(mainStage);

        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Main.class.getResource("JGuitar.fxml"));
            BorderPane rootPane = loader.load();

            JGuitarController controller = loader.getController();
            controller.setApp(app);

            Scene scene = new Scene(rootPane);
            app.getStage().setScene(scene);
            app.getStage().sizeToScene();

            app.getStage().show();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Main getApp() {
        return app;
    }

    public void setApp(Main app) {
        this.app = app;
    }

}
