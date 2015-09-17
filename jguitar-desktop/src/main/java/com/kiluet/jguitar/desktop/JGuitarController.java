package com.kiluet.jguitar.desktop;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.net.URL;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.kiluet.jguitar.dao.JGuitarDAOException;
import com.kiluet.jguitar.dao.JGuitarDAOManager;
import com.kiluet.jguitar.dao.model.KeyType;
import com.kiluet.jguitar.dao.model.Scale;
import com.kiluet.jguitar.dao.model.ScaleType;
import com.kiluet.jguitar.dao.model.Song;
import com.kiluet.jguitar.dao.model.Track;
import com.kiluet.jguitar.desktop.components.ScalePane;
import com.kiluet.jguitar.desktop.components.SongPane;
import com.kiluet.jguitar.player.SongPlayer;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.ToolBar;
import javafx.scene.input.InputEvent;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;

public class JGuitarController extends BorderPane implements Initializable {

    private static final Logger logger = LoggerFactory.getLogger(JGuitarController.class);

    private SongPlayer player;

    private Song song;

    @FXML
    private Label dateLabel;

    @FXML
    private Menu scalesMenu;

    @FXML
    private Label statusLabel;

    @FXML
    private ToolBar controlsToolBar;

    @FXML
    private VBox notationBox;

    @FXML
    private ListView<Track> trackListView;

    @FXML
    private ToggleGroup beatDurationTypeGroup, modeToggleGroup, voiceToggleGroup;

    @FXML
    private ToggleButton wholeDurationButton, halfDurationButton, quarterDurationButton, eighthDurationButton,
            sixteenthDurationButton, thritySecondDurationButton, sixtyFouthDurationButton;

    @FXML
    private Button deleteMeasureButton, previousMeasureButton, slowerVelocityButton, playSongButton,
            fasterVelocityButton, nextMeasureButton, addMeasureButton;

    private Main app;

    public JGuitarController() {
        super();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        dateLabel.setText(DateFormatUtils.ISO_DATE_FORMAT.format(new Date()));

        JGuitarDAOManager daoMgr = JGuitarDAOManager.getInstance();

        Map<String, Menu> menuMap = new HashMap<>();
        for (KeyType keyType : KeyType.values()) {
            menuMap.put(keyType.name(), new Menu(keyType.name()));
        }
        for (KeyType keyType : KeyType.values()) {
            try {
                List<Scale> scales = daoMgr.getDaoBean().getScaleDAO().findByKeyAndType(keyType, ScaleType.HEPTATONIC);
                scales.forEach(p -> menuMap.get(keyType.name()).getItems().add(new MenuItem(p.getName()) {
                    {
                        setOnAction(e -> {
                            try {
                                Scale scale = daoMgr.getDaoBean().getScaleDAO().findById(p.getId());
                                getNotationBox().getChildren().clear();
                                getNotationBox().getChildren().add(new ScalePane(JGuitarController.this, scale));
                            } catch (Exception e1) {
                                e1.printStackTrace();
                            }
                        });
                    }
                }));
            } catch (JGuitarDAOException e) {
                e.printStackTrace();
            }
        }
        Menu heptatonicMenu = new Menu(StringUtils.capitalize(ScaleType.HEPTATONIC.name().toLowerCase()));
        menuMap.values().forEach(p -> heptatonicMenu.getItems().add(p));
        scalesMenu.getItems().add(heptatonicMenu);

        for (KeyType keyType : KeyType.values()) {
            menuMap.put(keyType.name(), new Menu(keyType.name()));
        }
        for (KeyType keyType : KeyType.values()) {
            try {
                List<Scale> scales = daoMgr.getDaoBean().getScaleDAO().findByKeyAndType(keyType, ScaleType.PENTATONIC);
                scales.forEach(p -> menuMap.get(keyType.name()).getItems().add(new MenuItem(p.getName()) {
                    {
                        setOnAction(e -> {
                            try {
                                Scale scale = daoMgr.getDaoBean().getScaleDAO().findById(p.getId());
                                getNotationBox().getChildren().clear();
                                getNotationBox().getChildren().add(new ScalePane(JGuitarController.this, scale));
                            } catch (Exception e1) {
                                e1.printStackTrace();
                            }
                        });
                    }
                }));
            } catch (JGuitarDAOException e) {
                e.printStackTrace();
            }
        }
        Menu pentatonicMenu = new Menu(StringUtils.capitalize(ScaleType.PENTATONIC.name().toLowerCase()));
        menuMap.values().forEach(p -> pentatonicMenu.getItems().add(p));
        scalesMenu.getItems().add(pentatonicMenu);

        try {
            this.song = daoMgr.getDaoBean().getSongDAO().findByName("Template").get(0);
            getNotationBox().getChildren().clear();
            SongPane songPane = new SongPane(JGuitarController.this, this.song);
            getNotationBox().getChildren().add(songPane);
            this.player = new SongPlayer(songPane);
        } catch (JGuitarDAOException e) {
            e.printStackTrace();
        }

    }

    @FXML
    private void showAbout(final ActionEvent event) {
        // app.showAbout();
    }

    @FXML
    private void doNew(final ActionEvent event) {
        // app.doNew();
    }

    @FXML
    private void doUndo(final ActionEvent event) {
    }

    @FXML
    private void doPlaySong(final ActionEvent event) {

        if (playSongButton.getText().equals(">")) {
            playSongButton.setText("||");
            if (player.isPaused()) {
                // start playing
                player.play();
            } 
        } else {
            playSongButton.setText(">");
            if (player.isPlaying()) {
                // stop playing
                player.pause();
            } 
        }

    }

    @FXML
    private void doRedo(final ActionEvent event) {
    }

    @FXML
    private void doSelectionMode(final ActionEvent event) {
    }

    @FXML
    private void doScoreEditionMode(final ActionEvent event) {
    }

    @FXML
    private void doSharpFlatMode(final ActionEvent event) {
    }

    @FXML
    private void doSelectVoice1(final ActionEvent event) {
    }

    @FXML
    private void doSelectVoice2(final ActionEvent event) {
    }

    @FXML
    private void doSave(final ActionEvent event) {
    }

    @FXML
    private void doSaveAs(final ActionEvent event) {
    }

    @FXML
    private void doOpen(final ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open jGuitar Song");
        fileChooser.getExtensionFilters().add(new ExtensionFilter("JGuitar XML", "*.xml"));
        File selectedFile = fileChooser.showOpenDialog(this.app.getStage());
        try {
            JAXBContext context = JAXBContext.newInstance(Song.class);
            Unmarshaller m = context.createUnmarshaller();
            FileInputStream fis = new FileInputStream(selectedFile);
            // Song song = (Song) m.unmarshal(fis);
            // songTreeView.getItems().add(song);
            // songTreeView.getSelectionModel().selectLast();
            // drawSongPane(songTreeView.getSelectionModel().getSelectedItem());
            // songBox.getChildren().clear();
            // songBox.getChildren().add(drawSongPane(song));
        } catch (FileNotFoundException | JAXBException e) {
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("ERROR: JGuitarDAOException");
            alert.setContentText(e.getMessage());
            alert.showAndWait();
        }
    }

    @FXML
    private void doOpenURL(final ActionEvent event) {
    }

    @FXML
    private void doExport(final ActionEvent event) {
        // app.doExport();
    }

    @FXML
    private void doImport(final ActionEvent event) {
        // app.doImport();
    }

    @FXML
    private void doExit(final ActionEvent event) {
        System.exit(0);
    }

    @FXML
    private void handleKeyInput(final InputEvent event) {
        if (event instanceof KeyEvent) {
            final KeyEvent keyEvent = (KeyEvent) event;
            if (keyEvent.isControlDown() && keyEvent.getCode() == KeyCode.A) {
                // app.showAbout();
            }
        }
    }

    public Main getApp() {
        return app;
    }

    public void setApp(Main app) {
        this.app = app;
    }

    public SongPlayer getPlayer() {
        return player;
    }

    public void setPlayer(SongPlayer player) {
        this.player = player;
    }

    public Song getSong() {
        return song;
    }

    public void setSong(Song song) {
        this.song = song;
    }

    public ToolBar getControlsToolBar() {
        return controlsToolBar;
    }

    public void setControlsToolBar(ToolBar controlsToolBar) {
        this.controlsToolBar = controlsToolBar;
    }

    public VBox getNotationBox() {
        return notationBox;
    }

    public void setNotationBox(VBox notationBox) {
        this.notationBox = notationBox;
    }

    public ListView<Track> getTrackListView() {
        return trackListView;
    }

    public void setTrackListView(ListView<Track> trackListView) {
        this.trackListView = trackListView;
    }

    public ToggleButton getWholeDurationButton() {
        return wholeDurationButton;
    }

    public void setWholeDurationButton(ToggleButton wholeDurationButton) {
        this.wholeDurationButton = wholeDurationButton;
    }

    public ToggleButton getHalfDurationButton() {
        return halfDurationButton;
    }

    public void setHalfDurationButton(ToggleButton halfDurationButton) {
        this.halfDurationButton = halfDurationButton;
    }

    public ToggleButton getQuarterDurationButton() {
        return quarterDurationButton;
    }

    public void setQuarterDurationButton(ToggleButton quarterDurationButton) {
        this.quarterDurationButton = quarterDurationButton;
    }

    public ToggleButton getEighthDurationButton() {
        return eighthDurationButton;
    }

    public void setEighthDurationButton(ToggleButton eighthDurationButton) {
        this.eighthDurationButton = eighthDurationButton;
    }

    public ToggleButton getSixteenthDurationButton() {
        return sixteenthDurationButton;
    }

    public void setSixteenthDurationButton(ToggleButton sixteenthDurationButton) {
        this.sixteenthDurationButton = sixteenthDurationButton;
    }

    public ToggleButton getThritySecondDurationButton() {
        return thritySecondDurationButton;
    }

    public void setThritySecondDurationButton(ToggleButton thritySecondDurationButton) {
        this.thritySecondDurationButton = thritySecondDurationButton;
    }

    public ToggleButton getSixtyFouthDurationButton() {
        return sixtyFouthDurationButton;
    }

    public void setSixtyFouthDurationButton(ToggleButton sixtyFouthDurationButton) {
        this.sixtyFouthDurationButton = sixtyFouthDurationButton;
    }

    public Button getDeleteMeasureButton() {
        return deleteMeasureButton;
    }

    public void setDeleteMeasureButton(Button deleteMeasureButton) {
        this.deleteMeasureButton = deleteMeasureButton;
    }

    public Button getPreviousMeasureButton() {
        return previousMeasureButton;
    }

    public void setPreviousMeasureButton(Button previousMeasureButton) {
        this.previousMeasureButton = previousMeasureButton;
    }

    public Button getSlowerVelocityButton() {
        return slowerVelocityButton;
    }

    public void setSlowerVelocityButton(Button slowerVelocityButton) {
        this.slowerVelocityButton = slowerVelocityButton;
    }

    public Button getPlaySongButton() {
        return playSongButton;
    }

    public void setPlaySongButton(Button playSongButton) {
        this.playSongButton = playSongButton;
    }

    public Button getFasterVelocityButton() {
        return fasterVelocityButton;
    }

    public void setFasterVelocityButton(Button fasterVelocityButton) {
        this.fasterVelocityButton = fasterVelocityButton;
    }

    public Button getNextMeasureButton() {
        return nextMeasureButton;
    }

    public void setNextMeasureButton(Button nextMeasureButton) {
        this.nextMeasureButton = nextMeasureButton;
    }

    public Button getAddMeasureButton() {
        return addMeasureButton;
    }

    public void setAddMeasureButton(Button addMeasureButton) {
        this.addMeasureButton = addMeasureButton;
    }

}
