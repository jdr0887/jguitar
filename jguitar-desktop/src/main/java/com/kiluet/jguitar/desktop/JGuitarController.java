package com.kiluet.jguitar.desktop;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.ToolBar;
import javafx.scene.input.InputEvent;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;

import javax.sound.midi.MidiChannel;
import javax.sound.midi.MidiSystem;
import javax.sound.midi.Sequence;
import javax.sound.midi.Sequencer;
import javax.sound.midi.Soundbank;
import javax.sound.midi.Synthesizer;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

import org.controlsfx.dialog.Dialogs;

import com.kiluet.jguitar.model.Measure;
import com.kiluet.jguitar.model.Song;
import com.kiluet.jguitar.model.Track;

public class JGuitarController extends BorderPane implements Initializable {

    private Sequencer sequencer;

    private Sequence sequence;

    private Synthesizer synthesizer;

    private Track track;

    @FXML
    private BorderPane leftPane;

    @FXML
    private BorderPane rightPane;

    @FXML
    private ToolBar controlsToolBar;

    @FXML
    private VBox songBox;

    @FXML
    private ListView<Song> songListView;

    @FXML
    private ListView trackListView;

    @FXML
    private ToggleGroup beatDurationTypeGroup;

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
        Song song = deserializeDefaultSong();
        songListView.getItems().add(song);
        songListView.setCellFactory(p -> new SongCell());
        songListView.getSelectionModel().selectFirst();
        songBox.getChildren().add(drawSongPane(song));
    }

    private GridPane drawSongPane(Song song) {
        GridPane gridPane = new GridPane();
        gridPane.setVgap(10);

        for (int i = 0; i < song.getTracks().size(); i++) {
            Track track = song.getTracks().get(i);

            HBox trackBox = new HBox();
            // trackBox.setStyle("-fx-border-color: black;");

            Integer stringCount = track.getInstrument().getStringCount();

            for (int j = 0; j < track.getMeasures().size(); j++) {
                Measure measure = track.getMeasures().get(j);
                MeasurePane measurePane = new MeasurePane(this, stringCount, j + 1, track.getMeasures().size(),
                        measure.getBeats());
                trackBox.getChildren().add(measurePane);
            }

            gridPane.add(trackBox, 0, i);
            GridPane.setHgrow(trackBox, Priority.ALWAYS);

        }
        return gridPane;
    }

    private Song deserializeDefaultSong() {
        Song song = null;
        try {
            JAXBContext context = JAXBContext.newInstance(Song.class);
            Unmarshaller m = context.createUnmarshaller();
            InputStream is = this.getClass().getResourceAsStream("defaultSong.xml");
            song = (Song) m.unmarshal(is);
        } catch (JAXBException e) {
            e.printStackTrace();
        }
        return song;
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

        playSongButton.setText("||");

        GridPane gridPane = (GridPane) songBox.getChildren().get(0);
        javax.sound.midi.Track track;
        try {
            if (synthesizer == null) {
                if ((synthesizer = MidiSystem.getSynthesizer()) == null) {
                    System.out.println("getSynthesizer() failed!");
                    return;
                }
            }
            synthesizer.open();
            sequencer = MidiSystem.getSequencer();
            sequence = new Sequence(Sequence.PPQ, 10);
        } catch (Exception ex) {
            ex.printStackTrace();
            return;
        }

        Soundbank sb = synthesizer.getDefaultSoundbank();
        if (sb != null) {
            synthesizer.loadInstrument(synthesizer.getDefaultSoundbank().getInstruments()[26]);
        }

        for (Node trackNode : gridPane.getChildren()) {
            HBox trackBox = (HBox) trackNode;
            for (Node measureNode : trackBox.getChildren()) {
                MeasurePane measurePane = (MeasurePane) measureNode;
                GridPane measureGridPane = (GridPane) measurePane.getCenter();
                System.out.println(measureGridPane.getRowConstraints().size());
                // StackPane stackPane = measureGridPane.getChildren().get(0);
                MidiChannel[] channels = synthesizer.getChannels();
                try {
                    channels[26].noteOn(60, 80);
                    Thread.sleep(100);
                    channels[26].noteOn(60, 80);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

        }

        if (synthesizer != null) {
            synthesizer.close();
        }
        if (sequencer != null) {
            sequencer.close();
        }
        sequencer = null;
        synthesizer = null;

        playSongButton.setText(">");

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
            Song song = (Song) m.unmarshal(fis);
            songListView.getItems().add(song);
            songListView.getSelectionModel().selectLast();
            drawSongPane(songListView.getSelectionModel().getSelectedItem());
            songBox.getChildren().clear();
            songBox.getChildren().add(drawSongPane(song));
        } catch (FileNotFoundException | JAXBException e) {
            Dialogs.create().showException(e);
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

    public Sequencer getSequencer() {
        return sequencer;
    }

    public void setSequencer(Sequencer sequencer) {
        this.sequencer = sequencer;
    }

    public Sequence getSequence() {
        return sequence;
    }

    public void setSequence(Sequence sequence) {
        this.sequence = sequence;
    }

    public Synthesizer getSynthesizer() {
        return synthesizer;
    }

    public void setSynthesizer(Synthesizer synthesizer) {
        this.synthesizer = synthesizer;
    }

    public Track getTrack() {
        return track;
    }

    public void setTrack(Track track) {
        this.track = track;
    }

    public BorderPane getLeftPane() {
        return leftPane;
    }

    public void setLeftPane(BorderPane leftPane) {
        this.leftPane = leftPane;
    }

    public BorderPane getRightPane() {
        return rightPane;
    }

    public void setRightPane(BorderPane rightPane) {
        this.rightPane = rightPane;
    }

    public ToolBar getControlsToolBar() {
        return controlsToolBar;
    }

    public void setControlsToolBar(ToolBar controlsToolBar) {
        this.controlsToolBar = controlsToolBar;
    }

    public VBox getSongBox() {
        return songBox;
    }

    public void setSongBox(VBox songBox) {
        this.songBox = songBox;
    }

    public ListView<Song> getSongListView() {
        return songListView;
    }

    public void setSongListView(ListView<Song> songListView) {
        this.songListView = songListView;
    }

    public ListView getTrackListView() {
        return trackListView;
    }

    public void setTrackListView(ListView trackListView) {
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
