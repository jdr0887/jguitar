package com.kiluet.jguitar.desktop;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.ToolBar;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeTableColumn;
import javafx.scene.control.TreeTableView;
import javafx.scene.control.cell.TextFieldTreeTableCell;
import javafx.scene.input.InputEvent;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.TilePane;
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

import org.apache.commons.lang3.StringUtils;
import org.controlsfx.dialog.ExceptionDialog;

import com.kiluet.jguitar.dao.JGuitarDAOException;
import com.kiluet.jguitar.dao.JGuitarDAOManager;
import com.kiluet.jguitar.dao.model.KeyType;
import com.kiluet.jguitar.dao.model.Measure;
import com.kiluet.jguitar.dao.model.Scale;
import com.kiluet.jguitar.dao.model.ScaleType;
import com.kiluet.jguitar.dao.model.Song;
import com.kiluet.jguitar.dao.model.Track;

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
    private VBox notationBox;

    @FXML
    private TreeTableView<String> songsTreeTableView;

    @FXML
    private TreeTableView<String> scalesTreeTableView;

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
        JGuitarDAOManager daoMgr = JGuitarDAOManager.getInstance();
        TreeItem<String> scalesTreeTableDummyRoot = new TreeItem<String>();
        try {
            TreeItem<String> heptatonicScalesRoot = new TreeItem<String>(StringUtils.capitalize(ScaleType.HEPTATONIC
                    .toString().toLowerCase()));

            for (KeyType keyType : KeyType.values()) {

                TreeItem<String> heptatonicScaleKeyTreeItem = new TreeItem<String>(keyType.toString());
                List<Scale> scales = daoMgr.getDaoBean().getScaleDAO().findByKeyAndType(keyType, ScaleType.HEPTATONIC);
                scales.forEach(p -> heptatonicScaleKeyTreeItem.getChildren().add(new TreeItem<String>(p.getName())));
                heptatonicScalesRoot.getChildren().add(heptatonicScaleKeyTreeItem);
            }

            TreeItem<String> pentatonicScalesRoot = new TreeItem<String>(StringUtils.capitalize(ScaleType.PENTATONIC
                    .toString().toLowerCase()));

            for (KeyType keyType : KeyType.values()) {

                TreeItem<String> pentatonicScaleKeyTreeItem = new TreeItem<String>(keyType.toString());
                List<Scale> scales = daoMgr.getDaoBean().getScaleDAO().findByKeyAndType(keyType, ScaleType.PENTATONIC);
                scales.forEach(p -> pentatonicScaleKeyTreeItem.getChildren().add(new TreeItem<String>(p.getName())));
                pentatonicScalesRoot.getChildren().add(pentatonicScaleKeyTreeItem);

            }

            scalesTreeTableDummyRoot.getChildren().addAll(heptatonicScalesRoot, pentatonicScalesRoot);
        } catch (JGuitarDAOException e) {
            e.printStackTrace();
        }

        TreeTableColumn<String, String> scalesNameTreeTableColumn = new TreeTableColumn<>("Name");
        scalesNameTreeTableColumn.setPrefWidth(150);
        scalesNameTreeTableColumn
                .setCellValueFactory((TreeTableColumn.CellDataFeatures<String, String> param) -> new ReadOnlyStringWrapper(
                        param.getValue().getValue()));

        TreeTableColumn<String, String> scalesIterationsTreeTableColumn = new TreeTableColumn<>("X");
        scalesIterationsTreeTableColumn.setPrefWidth(30);
        scalesIterationsTreeTableColumn.setCellFactory(TextFieldTreeTableCell.forTreeTableColumn());

        TreeTableColumn<String, String> scalesProgressTreeTableColumn = new TreeTableColumn<>("Progress");
        scalesProgressTreeTableColumn.setPrefWidth(120);

        scalesTreeTableView.getColumns().addAll(scalesNameTreeTableColumn, scalesIterationsTreeTableColumn,
                scalesProgressTreeTableColumn);
        scalesTreeTableView.setShowRoot(false);
        scalesTreeTableView.setRoot(scalesTreeTableDummyRoot);
        scalesTreeTableView.getSelectionModel().selectedItemProperty()
                .addListener(new ScalesTreeTableViewChangeListener(this));
        scalesTreeTableView.getSelectionModel().selectFirst();

        TreeItem<String> songsTreeTableDummyRoot = new TreeItem<String>();
        try {
            List<Song> potentialTemplateSong = daoMgr.getDaoBean().getSongDAO().findByName("Template");
            Song template = potentialTemplateSong.get(0);
            TreeItem<String> templateRoot = new TreeItem<String>(template.getName());
            songsTreeTableDummyRoot.getChildren().add(templateRoot);
        } catch (JGuitarDAOException e1) {
            e1.printStackTrace();
        }

        TreeTableColumn<String, String> songsNameTreeTableColumn = new TreeTableColumn<>("Name");
        songsNameTreeTableColumn.setPrefWidth(150);
        songsNameTreeTableColumn
                .setCellValueFactory((TreeTableColumn.CellDataFeatures<String, String> param) -> new ReadOnlyStringWrapper(
                        param.getValue().getValue()));

        TreeTableColumn<String, String> songsIterationsTreeTableColumn = new TreeTableColumn<>("X");
        songsIterationsTreeTableColumn.setPrefWidth(30);

        TreeTableColumn<String, String> songsProgressTreeTableColumn = new TreeTableColumn<>("Progress");
        songsProgressTreeTableColumn.setPrefWidth(120);

        songsTreeTableView.getColumns().addAll(songsNameTreeTableColumn, songsIterationsTreeTableColumn,
                songsProgressTreeTableColumn);
        songsTreeTableView.setShowRoot(false);
        songsTreeTableView.setRoot(songsTreeTableDummyRoot);
        songsTreeTableView.getSelectionModel().selectedItemProperty()
                .addListener(new SongTreeTableViewChangeListener(this));
        songsTreeTableView.getSelectionModel().selectFirst();

    }

    public TilePane drawSongPane(List<Track> tracks) {
        JGuitarDAOManager daoMgr = JGuitarDAOManager.getInstance();
        TilePane tilePane = new TilePane(Orientation.VERTICAL);

        for (int i = 0; i < tracks.size(); i++) {
            Track track = tracks.get(i);

            HBox trackBox = new HBox();
            trackBox.setStyle("-fx-padding: 0;");
            // trackBox.setStyle("-fx-border-color: black;");

            List<Measure> measures = new ArrayList<Measure>();
            try {
                measures.addAll(daoMgr.getDaoBean().getMeasureDAO().findByTrackId(track.getId()));
            } catch (JGuitarDAOException e) {
                e.printStackTrace();
            }

            for (int j = 0; j < measures.size(); j++) {
                Measure measure = measures.get(j);
                MeasurePane measurePane = new MeasurePane(this, j + 1, track.getMeasures().size(), measure, track
                        .getInstrument().getStrings());
                trackBox.getChildren().add(measurePane);
            }

            tilePane.getChildren().add(trackBox);

        }
        return tilePane;
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

        GridPane gridPane = (GridPane) notationBox.getChildren().get(0);
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
            // Song song = (Song) m.unmarshal(fis);
            // songTreeView.getItems().add(song);
            // songTreeView.getSelectionModel().selectLast();
            // drawSongPane(songTreeView.getSelectionModel().getSelectedItem());
            // songBox.getChildren().clear();
            // songBox.getChildren().add(drawSongPane(song));
        } catch (FileNotFoundException | JAXBException e) {
            ExceptionDialog dialog = new ExceptionDialog(e);
            dialog.show();
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

    public VBox getNotationBox() {
        return notationBox;
    }

    public void setNotationBox(VBox notationBox) {
        this.notationBox = notationBox;
    }

    public TreeTableView<String> getPlaylistTreeTableView() {
        return scalesTreeTableView;
    }

    public void setPlaylistTreeTableView(TreeTableView<String> playlistTreeTableView) {
        this.scalesTreeTableView = playlistTreeTableView;
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
