package com.kiluet.jguitar.desktop;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.atomic.AtomicInteger;

import javax.sound.midi.MidiChannel;
import javax.sound.midi.MidiSystem;
import javax.sound.midi.MidiUnavailableException;
import javax.sound.midi.Synthesizer;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang.math.NumberUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.kiluet.jguitar.dao.JGuitarDAOException;
import com.kiluet.jguitar.dao.JGuitarDAOManager;
import com.kiluet.jguitar.dao.model.Beat;
import com.kiluet.jguitar.dao.model.DurationType;
import com.kiluet.jguitar.dao.model.Instrument;
import com.kiluet.jguitar.dao.model.InstrumentString;
import com.kiluet.jguitar.dao.model.KeyType;
import com.kiluet.jguitar.dao.model.Measure;
import com.kiluet.jguitar.dao.model.Note;
import com.kiluet.jguitar.dao.model.Scale;
import com.kiluet.jguitar.dao.model.ScaleType;
import com.kiluet.jguitar.dao.model.Song;
import com.kiluet.jguitar.dao.model.Track;
import com.kiluet.jguitar.desktop.components.MeasureCloseSeparatorPane;
import com.kiluet.jguitar.desktop.components.MeasureHeaderPane;
import com.kiluet.jguitar.desktop.components.MeasureOpenSeparatorPane;
import com.kiluet.jguitar.desktop.components.MeasurePane;
import com.kiluet.jguitar.desktop.components.NoteTextField;
import com.kiluet.jguitar.desktop.components.ScalePane;
import com.kiluet.jguitar.desktop.components.SongPane;
import com.kiluet.jguitar.desktop.components.TrackPane;

import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.ScheduledService;
import javafx.concurrent.Task;
import javafx.concurrent.WorkerStateEvent;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceDialog;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TableView;
import javafx.scene.control.TextInputDialog;
import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.ToolBar;
import javafx.scene.input.InputEvent;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.util.Duration;

public class JGuitarController extends BorderPane implements Initializable {

    private static final Logger logger = LoggerFactory.getLogger(JGuitarController.class);

    private static final JGuitarDAOManager daoMgr = JGuitarDAOManager.getInstance();

    private final Map<String, List<Beat>> beatMap = new LinkedHashMap<String, List<Beat>>();

    private ScheduledExecutorService es;

    private Song song;

    private SongPane songPane;

    private Scale scale;

    private ScalePane scalePane;

    @FXML
    private Label statusLabel;

    @FXML
    private ToolBar controlsToolBar;

    @FXML
    private VBox notationBox;

    @FXML
    private TableView<Song> songsTableView;

    @FXML
    private TabPane playlistTabPane;

    @FXML
    private TableView<Scale> scalesTableView;

    @FXML
    private ToggleGroup beatDurationTypeGroup, modeToggleGroup, voiceToggleGroup;

    @FXML
    private ToggleButton wholeDurationButton, halfDurationButton, quarterDurationButton, eighthDurationButton,
            sixteenthDurationButton, thirtySecondDurationButton, sixtyFourthDurationButton;

    @FXML
    private Button playButton;

    @FXML
    private ComboBox<String> speedComboBox;

    private List<Beat> previousBeats;

    private List<Beat> currentBeats;

    private Iterator<List<Beat>> beatIter;

    private Note selectedNote;

    private Synthesizer synthesizer;

    private Main app;

    public JGuitarController() {
        super();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        try {
            ObservableList<Scale> scalesList = FXCollections.observableArrayList();
            for (KeyType keyType : KeyType.values()) {
                List<Scale> scales = daoMgr.getDaoBean().getScaleDAO().findByKeyAndType(keyType, ScaleType.HEPTATONIC);
                scalesList.addAll(scales);
                scales = daoMgr.getDaoBean().getScaleDAO().findByKeyAndType(keyType, ScaleType.PENTATONIC);
                scalesList.addAll(scales);
            }
            scalesTableView.setItems(scalesList);
            scalesTableView.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Scale>() {

                @Override
                public void changed(ObservableValue<? extends Scale> observable, Scale oldValue, Scale newValue) {
                    if (oldValue != newValue) {
                        getNotationBox().getChildren().clear();
                        scalePane = new ScalePane(JGuitarController.this, newValue);
                        getNotationBox().getChildren().add(scalePane);
                    }
                }

            });

        } catch (JGuitarDAOException e) {
            e.printStackTrace();
        }

        ObservableList<Song> songsList = FXCollections.observableArrayList();
        try {
            this.song = JGuitarDAOManager.getInstance().getDaoBean().getSongDAO().findByTitle("Template").get(0);
            songsList.add(this.song);
        } catch (JGuitarDAOException e) {
            e.printStackTrace();
        }
        songsTableView.setItems(songsList);
        songsTableView.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Song>() {

            @Override
            public void changed(ObservableValue<? extends Song> observable, Song oldValue, Song newValue) {
                if (oldValue != newValue) {
                    getNotationBox().getChildren().clear();
                    songPane = new SongPane(JGuitarController.this);
                    getNotationBox().getChildren().add(songPane);
                }
            }

        });
        songsTableView.getSelectionModel().selectFirst();

        try {
            this.synthesizer = MidiSystem.getSynthesizer();
            this.synthesizer.open();
        } catch (MidiUnavailableException e) {
            e.printStackTrace();
        }

        wholeDurationButton.setUserData(DurationType.WHOLE);
        halfDurationButton.setUserData(DurationType.HALF);
        quarterDurationButton.setUserData(DurationType.QUARTER);
        eighthDurationButton.setUserData(DurationType.EIGHTH);
        sixteenthDurationButton.setUserData(DurationType.SIXTEENTH);
        thirtySecondDurationButton.setUserData(DurationType.THIRTY_SECOND);
        sixtyFourthDurationButton.setUserData(DurationType.SIXTY_FOURTH);

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
    private void wholeNoteDurationAction(final ActionEvent event) {
        Platform.runLater(() -> {
            if (durationToggle(DurationType.WHOLE)) {
                wholeDurationButton.setSelected(true);
            } else {
                Beat selectedBeat = selectedNote.getBeat();
                Toggle previousToggle = null;
                for (Toggle toggle : beatDurationTypeGroup.getToggles()) {
                    if (toggle.getUserData().equals(selectedBeat.getDuration())) {
                        previousToggle = toggle;
                        break;
                    }
                }
                previousToggle.setSelected(true);
            }
        });
    }

    @FXML
    private void halfNoteDurationAction(final ActionEvent event) {
        Platform.runLater(() -> {
            if (durationToggle(DurationType.HALF)) {
                halfDurationButton.setSelected(true);
            } else {
                Beat selectedBeat = selectedNote.getBeat();
                Toggle previousToggle = null;
                for (Toggle toggle : beatDurationTypeGroup.getToggles()) {
                    if (toggle.getUserData().equals(selectedBeat.getDuration())) {
                        previousToggle = toggle;
                        break;
                    }
                }
                previousToggle.setSelected(true);
            }
        });
    }

    @FXML
    private void quarterNoteDurationAction(final ActionEvent event) {
        Platform.runLater(() -> {
            if (durationToggle(DurationType.QUARTER)) {
                quarterDurationButton.setSelected(true);
            } else {
                Beat selectedBeat = selectedNote.getBeat();
                Toggle previousToggle = null;
                for (Toggle toggle : beatDurationTypeGroup.getToggles()) {
                    if (toggle.getUserData().equals(selectedBeat.getDuration())) {
                        previousToggle = toggle;
                        break;
                    }
                }
                previousToggle.setSelected(true);
            }
        });
    }

    @FXML
    private void eighthNoteDurationAction(final ActionEvent event) {
        Platform.runLater(() -> {
            if (durationToggle(DurationType.EIGHTH)) {
                eighthDurationButton.setSelected(true);
            } else {
                Beat selectedBeat = selectedNote.getBeat();
                Toggle previousToggle = null;
                for (Toggle toggle : beatDurationTypeGroup.getToggles()) {
                    if (toggle.getUserData().equals(selectedBeat.getDuration())) {
                        previousToggle = toggle;
                        break;
                    }
                }
                previousToggle.setSelected(true);
            }
        });
    }

    @FXML
    private void sixteenthNoteDurationAction(final ActionEvent event) {
        Platform.runLater(() -> {
            if (durationToggle(DurationType.SIXTEENTH)) {
                sixteenthDurationButton.setSelected(true);
            } else {
                Beat selectedBeat = selectedNote.getBeat();
                Toggle previousToggle = null;
                for (Toggle toggle : beatDurationTypeGroup.getToggles()) {
                    if (toggle.getUserData().equals(selectedBeat.getDuration())) {
                        previousToggle = toggle;
                        break;
                    }
                }
                previousToggle.setSelected(true);
            }
        });
    }

    @FXML
    private void thirtySecondNoteDurationAction(final ActionEvent event) {
        Platform.runLater(() -> {
            if (durationToggle(DurationType.THIRTY_SECOND)) {
                thirtySecondDurationButton.setSelected(true);
            } else {
                Beat selectedBeat = selectedNote.getBeat();
                Toggle previousToggle = null;
                for (Toggle toggle : beatDurationTypeGroup.getToggles()) {
                    if (toggle.getUserData().equals(selectedBeat.getDuration())) {
                        previousToggle = toggle;
                        break;
                    }
                }
                previousToggle.setSelected(true);
            }
        });
    }

    @FXML
    private void sixtyFourthNoteDurationAction(final ActionEvent event) {
        Platform.runLater(() -> {
            if (durationToggle(DurationType.SIXTY_FOURTH)) {
                sixtyFourthDurationButton.setSelected(true);
            } else {
                Beat selectedBeat = selectedNote.getBeat();
                Toggle previousToggle = null;
                for (Toggle toggle : beatDurationTypeGroup.getToggles()) {
                    if (toggle.getUserData().equals(selectedBeat.getDuration())) {
                        previousToggle = toggle;
                        break;
                    }
                }
                previousToggle.setSelected(true);
            }
        });
    }

    private Boolean durationToggle(DurationType durationType) {

        // DurationType durationType = (DurationType) beatDurationTypeGroup.getSelectedToggle().getUserData();
        if (selectedNote != null) {

            Beat selectedBeat = selectedNote.getBeat();

            Measure selectedMeasure = selectedBeat.getMeasure();

            List<Beat> proximalBeats = new ArrayList<Beat>();
            selectedMeasure.getBeats().forEach(beat -> {
                if (!beat.equals(selectedBeat)
                        && beat.getNumber() < selectedBeat.getNumber() + selectedBeat.getDuration().getCode() / 2) {
                    proximalBeats.add(beat);
                }
            });

            if (CollectionUtils.isNotEmpty(proximalBeats)) {
                Iterator<Beat> beatIter = selectedMeasure.getBeats().iterator();
                while (beatIter.hasNext()) {
                    Beat beat = beatIter.next();
                    if (proximalBeats.contains(beat)) {
                        try {
                            beatIter.remove();
                            daoMgr.getDaoBean().getBeatDAO().delete(beat);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }
            }

            if (selectedNote.getBeat().getDuration().getCode() < durationType.getCode()) {
                if (CollectionUtils.isEmpty(proximalBeats)) {
                    expandBeats(proximalBeats, durationType);
                    return true;
                } else {
                    return false;
                }
            }

            if (selectedNote.getBeat().getDuration().getCode() > durationType.getCode()) {
                if (CollectionUtils.isNotEmpty(proximalBeats)) {
                    collapseBeats(proximalBeats, durationType);
                    return true;
                } else {
                    return false;
                }
            }

        }
        return false;
    }

    @FXML
    private void addMeasure(final ActionEvent event) {
        TextInputDialog input = new TextInputDialog();
        input.setGraphic(null);
        input.setHeaderText(null);
        input.setTitle("Measure Index");
        input.setContentText("New measure after:");
        Optional<String> value = input.showAndWait();

        if (!value.isPresent()) {
            return;
        }

        String v = value.get();
        if (!NumberUtils.isNumber(v)) {
            return;
        }
        Integer measureIndex = Integer.valueOf(v);

        for (Track track : song.getTracks()) {
            Measure newMeasure = null;
            for (Measure measure : track.getMeasures()) {
                try {
                    if (measure.getNumber() < measureIndex) {
                        continue;
                    }
                    if (measure.getNumber() == measureIndex) {
                        newMeasure = new Measure(track, measure.getMeterType(), measure.getTempo(),
                                measure.getNumber() + 1);
                        newMeasure.setId(daoMgr.getDaoBean().getMeasureDAO().save(newMeasure));

                        Beat beat = new Beat(newMeasure, DurationType.WHOLE, 1);
                        beat.setId(daoMgr.getDaoBean().getBeatDAO().save(beat));
                        newMeasure.getBeats().add(beat);

                        for (InstrumentString is : track.getInstrument().getStrings()) {
                            Note note = new Note(beat, is.getString(), null, 200);
                            note.setId(daoMgr.getDaoBean().getNoteDAO().save(note));
                            beat.getNotes().add(note);
                        }
                    }
                    if (measure.getNumber() > measureIndex) {
                        measure.incrementNumber();
                        daoMgr.getDaoBean().getMeasureDAO().save(measure);
                    }
                } catch (JGuitarDAOException e) {
                    e.printStackTrace();
                }
            }
            try {
                track.getMeasures().add(newMeasure);
                daoMgr.getDaoBean().getTrackDAO().save(track);
            } catch (JGuitarDAOException e) {
                e.printStackTrace();
            }

            Node trackNode = this.songPane.lookup(String.format("#TrackPane_%d", track.getId()));
            if (trackNode != null) {
                TrackPane trackPane = (TrackPane) trackNode;
                trackPane.getChildren().clear();
                trackPane.init();
                trackPane.requestLayout();
            }

            for (Measure measure : track.getMeasures()) {
                if (measure.getNumber() < measureIndex) {
                    continue;
                }
                if (measure.getNumber() >= measureIndex) {
                    Node measurePaneNode = trackNode.lookup(String.format("#MeasurePane_%d", measure.getId()));
                    if (measurePaneNode != null) {
                        MeasurePane measurePane = (MeasurePane) measurePaneNode;
                        measurePane.getChildren().clear();
                        measurePane.init();
                        measurePane.requestLayout();
                    }
                }
            }

        }
    }

    @FXML
    private void removeMeasure(final ActionEvent event) {
        TextInputDialog input = new TextInputDialog();
        input.setGraphic(null);
        input.setHeaderText(null);
        input.setTitle("Measure Index");
        input.setContentText("Measure to remove:");
        Optional<String> value = input.showAndWait();

        if (!value.isPresent()) {
            return;
        }

        String v = value.get();
        if (!NumberUtils.isNumber(v)) {
            return;
        }

        Integer measureIndex = Integer.valueOf(v);
        for (Track track : song.getTracks()) {
            Iterator<Measure> measureIter = track.getMeasures().iterator();
            while (measureIter.hasNext()) {
                Measure measure = measureIter.next();
                try {
                    if (measure.getNumber() < measureIndex) {
                        continue;
                    }
                    if (measure.getNumber() == measureIndex) {
                        for (Beat beat : measure.getBeats()) {
                            daoMgr.getDaoBean().getNoteDAO().delete(beat.getNotes());
                        }
                        daoMgr.getDaoBean().getBeatDAO().delete(measure.getBeats());
                        daoMgr.getDaoBean().getMeasureDAO().delete(measure);
                        measureIter.remove();
                    }
                    if (measure.getNumber() > measureIndex) {
                        try {
                            measure.decrementNumber();
                            daoMgr.getDaoBean().getMeasureDAO().save(measure);
                        } catch (JGuitarDAOException e) {
                            e.printStackTrace();
                        }
                    }
                } catch (JGuitarDAOException e) {
                    e.printStackTrace();
                }
            }

            Node trackNode = this.songPane.lookup(String.format("#TrackPane_%d", track.getId()));
            if (trackNode != null) {
                TrackPane trackPane = (TrackPane) trackNode;
                trackPane.getChildren().clear();
                trackPane.init();
                trackPane.requestLayout();
            }

            for (Measure measure : track.getMeasures()) {
                if (measure.getNumber() < measureIndex) {
                    continue;
                }
                if (measure.getNumber() >= measureIndex) {
                    Node measurePaneNode = trackNode.lookup(String.format("#MeasurePane_%d", measure.getId()));
                    if (measurePaneNode != null) {
                        MeasurePane measurePane = (MeasurePane) measurePaneNode;
                        measurePane.getChildren().clear();
                        measurePane.init();
                        measurePane.requestLayout();
                    }
                }
            }
        }
    }

    @FXML
    private void toggleOpenRepeat(final ActionEvent event) {
        for (Track track : this.song.getTracks()) {
            Measure measure = null;
            for (Measure m : track.getMeasures()) {
                if (m.getNumber().equals(getSelectedNote().getBeat().getMeasure().getNumber())) {
                    measure = m;
                    break;
                }
            }

            try {
                if (measure.getOpenRepeat()) {
                    measure.setOpenRepeat(Boolean.FALSE);
                } else {
                    measure.setOpenRepeat(Boolean.TRUE);
                }
                daoMgr.getDaoBean().getMeasureDAO().save(measure);
            } catch (JGuitarDAOException e) {
                e.printStackTrace();
            }

            refreshMeasure(measure);
        }

    }

    private void collapseBeats(List<Beat> proximalBeats, DurationType durationType) {
        logger.info("ENTERING collapseBeats(DurationType)");

        Beat selectedBeat = selectedNote.getBeat();
        Measure selectedMeasure = selectedBeat.getMeasure();

        try {
            selectedBeat.setDuration(durationType);
            daoMgr.getDaoBean().getBeatDAO().save(selectedBeat);
        } catch (JGuitarDAOException e) {
            e.printStackTrace();
        }

        List<Beat> newBeats = new ArrayList<Beat>();
        for (int i = 0; i < (durationType.getCode() / 2) - 1; i++) {
            try {
                Beat newBeat = new Beat(selectedMeasure, durationType, selectedBeat.getNumber() + i + 1);
                newBeat.setId(daoMgr.getDaoBean().getBeatDAO().save(newBeat));
                selectedMeasure.getTrack().getInstrument().getStrings().forEach(string -> {
                    try {
                        Note newNote = new Note(newBeat, string.getString(), null, 200);
                        newNote.setId(daoMgr.getDaoBean().getNoteDAO().save(newNote));
                        newBeat.getNotes().add(newNote);
                    } catch (JGuitarDAOException e) {
                        e.printStackTrace();
                    }
                });
                newBeats.add(newBeat);
            } catch (JGuitarDAOException e) {
                e.printStackTrace();
            }
        }
        // newBeats.forEach(a -> logger.info(a.toString()));

        List<Beat> beatsToDecrement = new ArrayList<Beat>();
        selectedMeasure.getBeats().forEach(a -> {
            if (a.getNumber() > selectedBeat.getNumber() + durationType.getCode() - 1) {
                beatsToDecrement.add(a);
            }
        });

        beatsToDecrement.forEach(a -> {
            for (int i = 0; i < newBeats.size() + proximalBeats.size(); i++) {
                try {
                    a.decrementNumber();
                    daoMgr.getDaoBean().getBeatDAO().save(a);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        selectedMeasure.getBeats().addAll(newBeats);

        selectedMeasure.getBeats().sort((a, b) -> a.getNumber().compareTo(b.getNumber()));
        selectedMeasure.getBeats().forEach(a -> logger.info(a.toString()));

        refreshMeasure(selectedMeasure);

    }

    private void expandBeats(List<Beat> proximalBeats, DurationType durationType) {
        logger.info("ENTERING expandBeats(DurationType)");
        Beat selectedBeat = selectedNote.getBeat();
        Measure selectedMeasure = selectedBeat.getMeasure();

        try {
            selectedBeat.setDuration(durationType);
            daoMgr.getDaoBean().getBeatDAO().save(selectedBeat);
        } catch (JGuitarDAOException e) {
            e.printStackTrace();
        }

        List<Beat> newBeats = new ArrayList<Beat>();
        for (int i = 0; i < (durationType.getCode() / 2) - 1; i++) {
            try {
                Beat newBeat = new Beat(selectedMeasure, durationType, selectedBeat.getNumber() + i + 1);
                newBeat.setId(daoMgr.getDaoBean().getBeatDAO().save(newBeat));
                selectedMeasure.getTrack().getInstrument().getStrings().forEach(string -> {
                    try {
                        Note newNote = new Note(newBeat, string.getString(), null, 200);
                        newNote.setId(daoMgr.getDaoBean().getNoteDAO().save(newNote));
                        newBeat.getNotes().add(newNote);
                    } catch (JGuitarDAOException e) {
                        e.printStackTrace();
                    }
                });
                newBeats.add(newBeat);
            } catch (JGuitarDAOException e) {
                e.printStackTrace();
            }
        }

        // newBeats.forEach(a -> logger.info(a.toString()));

        List<Beat> beatsToIncrement = new ArrayList<Beat>();
        selectedMeasure.getBeats().forEach(a -> {
            if (a.getNumber() > selectedBeat.getNumber()) {
                beatsToIncrement.add(a);
            }
        });

        beatsToIncrement.forEach(a -> {
            for (int i = 0; i < newBeats.size() - proximalBeats.size(); i++) {
                try {
                    a.incrementNumber();
                    daoMgr.getDaoBean().getBeatDAO().save(a);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        selectedMeasure.getBeats().addAll(newBeats);

        selectedMeasure.getBeats().sort((a, b) -> a.getNumber().compareTo(b.getNumber()));
        selectedMeasure.getBeats().forEach(a -> logger.info(a.toString()));

        refreshMeasure(selectedMeasure);
    }

    public void refreshMeasure(Measure measure) {
        Node trackPaneNode = this.songPane.lookup(String.format("#TrackPane_%d", measure.getTrack().getId()));
        Node measurePaneNode = trackPaneNode.lookup(String.format("#MeasurePane_%d", measure.getId()));
        if (measurePaneNode != null && measurePaneNode instanceof MeasurePane) {
            MeasurePane measurePane = (MeasurePane) measurePaneNode;
            measurePane.getChildren().clear();
            measurePane.init();
            measurePane.requestLayout();
        }
    }

    @FXML
    private void toggleCloseRepeat(final ActionEvent event) {

        TextInputDialog input = new TextInputDialog();
        input.setGraphic(null);
        input.setHeaderText(null);
        input.setTitle("Remove Repeat");
        input.setContentText("Repetitions");

        List<Measure> measuresAccrossTracks = new ArrayList<Measure>();
        for (Track track : this.song.getTracks()) {
            for (Measure m : track.getMeasures()) {
                if (m.getNumber().equals(getSelectedNote().getBeat().getMeasure().getNumber())) {
                    measuresAccrossTracks.add(m);
                    break;
                }
            }
        }

        if (CollectionUtils.isNotEmpty(measuresAccrossTracks)) {

            if (measuresAccrossTracks.get(0).getCloseRepeat() == null) {
                Optional<String> value = input.showAndWait();
                value.ifPresent(b -> {

                    if (NumberUtils.isNumber(b)) {

                        measuresAccrossTracks.forEach(a -> {
                            try {
                                a.setCloseRepeat(Integer.valueOf(b));
                                daoMgr.getDaoBean().getMeasureDAO().save(a);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }

                            Node trackPaneNode = this.songPane
                                    .lookup(String.format("#TrackPane_%d", a.getTrack().getId()));
                            Node measurePaneNode = trackPaneNode.lookup(String.format("#MeasurePane_%d", a.getId()));

                            Node measureOpenSeparatorNode = measurePaneNode
                                    .lookup(String.format("#MeasureOpenSeparatorPane_%d", a.getId()));
                            if (measureOpenSeparatorNode != null
                                    && measureOpenSeparatorNode instanceof MeasureOpenSeparatorPane) {
                                MeasureOpenSeparatorPane measureSeparatorPane = (MeasureOpenSeparatorPane) measureOpenSeparatorNode;
                                measureSeparatorPane.getChildren().clear();
                                measureSeparatorPane.init();
                                measureSeparatorPane.requestLayout();
                            }

                            Node measureCloseSeparatorNode = measurePaneNode
                                    .lookup(String.format("#MeasureCloseSeparatorPane_%d", a.getId()));
                            if (measureCloseSeparatorNode != null
                                    && measureCloseSeparatorNode instanceof MeasureCloseSeparatorPane) {
                                MeasureCloseSeparatorPane measureSeparatorPane = (MeasureCloseSeparatorPane) measureCloseSeparatorNode;
                                measureSeparatorPane.getChildren().clear();
                                measureSeparatorPane.init();
                                measureSeparatorPane.requestLayout();
                            }

                            Node measureHeaderNode = trackPaneNode
                                    .lookup(String.format("#MeasureHeaderPane_%d", a.getId()));
                            if (measureHeaderNode != null && measureHeaderNode instanceof MeasureHeaderPane) {
                                MeasureHeaderPane measureHeaderPane = (MeasureHeaderPane) measureHeaderNode;
                                measureHeaderPane.getChildren().clear();
                                measureHeaderPane.init();
                                measureHeaderPane.requestLayout();
                            }

                        });

                    }
                });

            } else {

                measuresAccrossTracks.forEach(a -> {
                    try {
                        a.setCloseRepeat(null);
                        daoMgr.getDaoBean().getMeasureDAO().save(a);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                    Node trackPaneNode = this.songPane.lookup(String.format("#TrackPane_%d", a.getTrack().getId()));
                    Node measurePaneNode = trackPaneNode.lookup(String.format("#MeasurePane_%d", a.getId()));

                    Node measureHeaderNode = trackPaneNode.lookup(String.format("#MeasureHeaderPane_%d", a.getId()));
                    if (measureHeaderNode != null && measureHeaderNode instanceof MeasureHeaderPane) {
                        MeasureHeaderPane measureHeaderPane = (MeasureHeaderPane) measureHeaderNode;
                        measureHeaderPane.getChildren().clear();
                        measureHeaderPane.init();
                        measureHeaderPane.requestLayout();
                    }

                    Node measureSeparatorNode = measurePaneNode
                            .lookup(String.format("#MeasureCloseSeparatorPane_%d", a.getId()));
                    if (measureSeparatorNode != null && measureSeparatorNode instanceof MeasureCloseSeparatorPane) {
                        MeasureCloseSeparatorPane measureSeparatorPane = (MeasureCloseSeparatorPane) measureSeparatorNode;
                        measureSeparatorPane.getChildren().clear();
                        measureSeparatorPane.init();
                        measureSeparatorPane.requestLayout();
                    }

                });
            }

        }

    }

    @FXML
    private void songsTableViewOnMouseReleased(final MouseEvent event) {
        getNotationBox().getChildren().clear();
        this.song = this.songsTableView.getSelectionModel().getSelectedItem();
        this.songPane = new SongPane(JGuitarController.this);
        getNotationBox().getChildren().add(songPane);
    }

    @FXML
    private void scalesTableViewOnMouseReleased(final MouseEvent event) {
        getNotationBox().getChildren().clear();
        this.scale = this.scalesTableView.getSelectionModel().getSelectedItem();
        this.scalePane = new ScalePane(JGuitarController.this, scale);
        getNotationBox().getChildren().add(scalePane);
    }

    @FXML
    private void addTrack(final ActionEvent event) {
        try {
            List<Instrument> instruments = daoMgr.getDaoBean().getInstrumentDAO().findAll();
            List<String> instrumentValueList = new ArrayList<String>();
            instruments.forEach(a -> instrumentValueList.add(a.getName()));
            ChoiceDialog<String> instrumentDialog = new ChoiceDialog<String>(instruments.get(0).getName(),
                    instrumentValueList);
            instrumentDialog.setTitle("Choose an instrument");
            instrumentDialog.setContentText("Choose an instrument");
            Optional<String> result = instrumentDialog.showAndWait();
            Instrument selectedInstrument = null;
            if (result.isPresent()) {
                String instrumentValue = result.get();
                for (Instrument instrument : instruments) {
                    if (instrument.getName().equals(instrumentValue)) {
                        selectedInstrument = instrument;
                        break;
                    }
                }
                AtomicInteger max = new AtomicInteger(0);
                this.song.getTracks().forEach(a -> {
                    if (a.getNumber() > max.get()) {
                        max.set(a.getNumber());
                    }
                });

                Track newTrack = new Track(this.song, selectedInstrument, max.incrementAndGet());
                newTrack.setId(daoMgr.getDaoBean().getTrackDAO().save(newTrack));
                logger.info(newTrack.toString());
                if (CollectionUtils.isNotEmpty(this.song.getTracks())) {
                    for (Measure measure : this.song.getTracks().get(0).getMeasures()) {
                        Measure newMeasure = new Measure(newTrack, measure.getMeterType(), measure.getTempo(),
                                measure.getNumber());
                        newMeasure.setId(daoMgr.getDaoBean().getMeasureDAO().save(newMeasure));
                        logger.info(newMeasure.toString());

                        for (Beat beat : measure.getBeats()) {
                            Beat newBeat = new Beat(newMeasure, beat.getDuration(), beat.getNumber());
                            newBeat.setId(daoMgr.getDaoBean().getBeatDAO().save(newBeat));
                            logger.info(newBeat.toString());

                            for (InstrumentString is : selectedInstrument.getStrings()) {
                                Note note = new Note(newBeat, is.getString(), null, 200);
                                note.setId(daoMgr.getDaoBean().getNoteDAO().save(note));
                                logger.info(note.toString());
                                newBeat.getNotes().add(note);
                            }
                            newMeasure.getBeats().add(newBeat);
                        }
                        newTrack.getMeasures().add(newMeasure);
                    }
                    this.song.getTracks().add(newTrack);
                }

            }
        } catch (JGuitarDAOException e) {
            e.printStackTrace();
        }
        this.songPane.getChildren().clear();
        this.songPane.init();
        this.songPane.requestLayout();

    }

    @FXML
    private void removeTrack(final ActionEvent event) {
        TextInputDialog input = new TextInputDialog();
        input.setGraphic(null);
        input.setHeaderText(null);
        input.setTitle("Track Index");
        input.setContentText("Track to remove:");
        Optional<String> value = input.showAndWait();

        if (!value.isPresent()) {
            return;
        }

        String v = value.get();
        if (!NumberUtils.isNumber(v)) {
            return;
        }

        Integer trackIndex = Integer.valueOf(v);

        for (Track track : song.getTracks()) {
            if (track.getNumber() != trackIndex) {
                continue;
            }
            try {
                Iterator<Measure> measureIter = track.getMeasures().iterator();
                while (measureIter.hasNext()) {
                    Measure measure = measureIter.next();
                    for (Beat beat : measure.getBeats()) {
                        daoMgr.getDaoBean().getNoteDAO().delete(beat.getNotes());
                    }
                    daoMgr.getDaoBean().getBeatDAO().delete(measure.getBeats());
                    daoMgr.getDaoBean().getMeasureDAO().delete(measure);
                    measureIter.remove();
                }
                daoMgr.getDaoBean().getMeasureDAO().delete(track.getMeasures());
                daoMgr.getDaoBean().getTrackDAO().delete(track);
            } catch (JGuitarDAOException e) {
                e.printStackTrace();
            }

            this.songPane.getChildren().clear();
            this.songPane.init();
            this.songPane.requestLayout();

        }

    }

    @FXML
    private void doUndo(final ActionEvent event) {
    }

    @FXML
    private void moveToNext(final ActionEvent event) {
        Tab selectedTab = playlistTabPane.getSelectionModel().getSelectedItem();
        if (selectedTab.getText().equals("Songs")) {
            this.songsTableView.getSelectionModel().selectNext();
        } else {
            this.scalesTableView.getSelectionModel().selectNext();
        }
    }

    @FXML
    private void moveToPrevious(final ActionEvent event) {
        Tab selectedTab = playlistTabPane.getSelectionModel().getSelectedItem();
        if (selectedTab.getText().equals("Songs")) {
            this.songsTableView.getSelectionModel().selectPrevious();
        } else {
            this.scalesTableView.getSelectionModel().selectPrevious();
        }
    }

    @FXML
    private void play(final ActionEvent event) {
        logger.info("ENTERING play()");
        playButton.setText("||");
        beatMap.clear();
        if (getNotationBox().getChildren().get(0) instanceof SongPane) {
            for (Track track : this.song.getTracks()) {
                for (Measure measure : track.getMeasures()) {
                    for (Beat beat : measure.getBeats()) {
                        String key = String.format("%d_%d", measure.getNumber(), beat.getNumber());
                        if (beatMap.get(key) == null) {
                            beatMap.put(key, new LinkedList<Beat>());
                        }
                        beatMap.get(key).add(beat);
                    }
                }
            }
        } else {
            for (Measure measure : this.scale.getTrack().getMeasures()) {
                for (Beat beat : measure.getBeats()) {
                    String key = String.format("%d_%d", measure.getNumber(), beat.getNumber());
                    if (beatMap.get(key) == null) {
                        beatMap.put(key, new LinkedList<Beat>());
                    }
                    beatMap.get(key).add(beat);
                }
            }
        }
        this.beatIter = beatMap.values().iterator();

        this.es = Executors.newSingleThreadScheduledExecutor();
        Integer selectedMeasureIndex = selectedNote.getBeat().getMeasure().getNumber();
        logger.info("selectedMeasureIndex = {}", selectedMeasureIndex);
        if (selectedMeasureIndex != null) {
            for (int i = 0; i < selectedMeasureIndex - 1; i++) {
                previousBeats = currentBeats;
                currentBeats = beatIter.next();
            }
        }

        if (currentBeats == null && beatIter.hasNext()) {
            currentBeats = beatIter.next();
        }

        Integer tempo = currentBeats.get(0).getMeasure().getTempo();

        Double tempoInMillis = Double.valueOf(((60D / tempo.doubleValue()) * 1000));
        logger.info(tempoInMillis.toString());

        Double speed = Double.valueOf(getSpeedComboBox().getSelectionModel().getSelectedItem());
        tempoInMillis += (tempoInMillis - Double.valueOf(tempoInMillis * speed / 100D));
        logger.info(tempoInMillis.toString());

        ScheduledService<List<Beat>> svc = new ScheduledService<List<Beat>>() {

            @Override
            protected Task<List<Beat>> createTask() {

                return new Task<List<Beat>>() {

                    @Override
                    protected List<Beat> call() {

                        try {

                            MidiChannel[] channels = synthesizer.getChannels();

                            if (CollectionUtils.isNotEmpty(previousBeats)) {

                                for (Beat beat : previousBeats) {
                                    logger.info(beat.getMeasure().getTrack().getInstrument().toString());
                                    channels[beat.getMeasure().getTrack().getNumber() - 1].allNotesOff();
                                    TrackPane trackPane = (TrackPane) getNotationBox().getChildren().get(0).lookup(
                                            String.format("#TrackPane_%d", beat.getMeasure().getTrack().getId()));
                                    MeasurePane measurePane = (MeasurePane) trackPane
                                            .lookup(String.format("#MeasurePane_%d", beat.getMeasure().getId()));
                                    List<InstrumentString> instrumentStrings = beat.getMeasure().getTrack()
                                            .getInstrument().getStrings();
                                    for (InstrumentString instrumentString : instrumentStrings) {
                                        NoteTextField noteTextField = (NoteTextField) measurePane.lookup(String.format(
                                                "#NoteTextField_%d_%d_%d_%d", beat.getMeasure().getTrack().getId(),
                                                beat.getMeasure().getNumber(), beat.getNumber(),
                                                instrumentString.getString()));
                                        if (noteTextField != null && StringUtils.isNotEmpty(noteTextField.getText())) {
                                            noteTextField.colorBlack();
                                        }
                                    }
                                }

                            }

                            for (Beat beat : currentBeats) {
                                logger.info(beat.getMeasure().getTrack().getInstrument().toString());
                                channels[beat.getMeasure().getTrack().getNumber() - 1].programChange(0,
                                        beat.getMeasure().getTrack().getInstrument().getProgram());

                                TrackPane trackPane = (TrackPane) getNotationBox().getChildren().get(0)
                                        .lookup(String.format("#TrackPane_%d", beat.getMeasure().getTrack().getId()));
                                MeasurePane measurePane = (MeasurePane) trackPane
                                        .lookup(String.format("#MeasurePane_%d", beat.getMeasure().getId()));

                                List<InstrumentString> instrumentStrings = beat.getMeasure().getTrack().getInstrument()
                                        .getStrings();
                                for (InstrumentString instrumentString : instrumentStrings) {
                                    NoteTextField noteTextField = (NoteTextField) measurePane
                                            .lookup(String.format("#NoteTextField_%d_%d_%d_%d",
                                                    beat.getMeasure().getTrack().getId(), beat.getMeasure().getNumber(),
                                                    beat.getNumber(), instrumentString.getString()));
                                    if (noteTextField != null && StringUtils.isNotEmpty(noteTextField.getText())) {
                                        noteTextField.colorRed();
                                        logger.info(instrumentString.getPitch()
                                                + Integer.valueOf(noteTextField.getText()).toString());
                                        channels[beat.getMeasure().getTrack().getNumber() - 1].noteOn(
                                                instrumentString.getPitch() + Integer.valueOf(noteTextField.getText()),
                                                200);
                                    }

                                }

                            }

                            previousBeats = currentBeats;
                            if (beatIter.hasNext()) {
                                currentBeats = beatIter.next();
                            }

                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                        return currentBeats;

                    }

                };

            }

        };
        svc.setOnScheduled(new EventHandler<WorkerStateEvent>() {

            @Override
            public void handle(WorkerStateEvent event) {
                if (currentBeats == previousBeats) {
                    svc.cancel();
                    playButton.setText(">");
                    if (CollectionUtils.isNotEmpty(currentBeats)) {

                        for (Beat beat : currentBeats) {
                            TrackPane trackPane = (TrackPane) getNotationBox().getChildren().get(0)
                                    .lookup(String.format("#TrackPane_%d", beat.getMeasure().getTrack().getId()));
                            MeasurePane measurePane = (MeasurePane) trackPane
                                    .lookup(String.format("#MeasurePane_%d", beat.getMeasure().getId()));
                            List<InstrumentString> instrumentStrings = beat.getMeasure().getTrack().getInstrument()
                                    .getStrings();
                            for (InstrumentString instrumentString : instrumentStrings) {
                                NoteTextField noteTextField = (NoteTextField) measurePane.lookup(String.format(
                                        "#NoteTextField_%d_%d_%d_%d", beat.getMeasure().getTrack().getId(),
                                        beat.getMeasure().getNumber(), beat.getNumber(), instrumentString.getString()));
                                if (noteTextField != null && StringUtils.isNotEmpty(noteTextField.getText())) {
                                    noteTextField.colorBlack();
                                }
                            }
                        }

                    }
                }

            }

        });
        svc.setDelay(Duration.ZERO);
        svc.setPeriod(Duration.millis(tempoInMillis));
        svc.start();
    }

    @FXML
    private void pause(final ActionEvent event) {
        logger.info("ENTERING pause()");
        playButton.setText(">");
        this.es.shutdownNow();
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

    public Scale getScale() {
        return scale;
    }

    public void setScale(Scale scale) {
        this.scale = scale;
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

    public ToggleButton getThirtySecondDurationButton() {
        return thirtySecondDurationButton;
    }

    public void setThirtySecondDurationButton(ToggleButton thirtySecondDurationButton) {
        this.thirtySecondDurationButton = thirtySecondDurationButton;
    }

    public ToggleButton getSixtyFourthDurationButton() {
        return sixtyFourthDurationButton;
    }

    public void setSixtyFourthDurationButton(ToggleButton sixtyFourthDurationButton) {
        this.sixtyFourthDurationButton = sixtyFourthDurationButton;
    }

    public ComboBox<String> getSpeedComboBox() {
        return speedComboBox;
    }

    public void setSpeedComboBox(ComboBox<String> speedComboBox) {
        this.speedComboBox = speedComboBox;
    }

    public Note getSelectedNote() {
        return selectedNote;
    }

    public void setSelectedNote(Note selectedNote) {
        this.selectedNote = selectedNote;
    }

    public SongPane getSongPane() {
        return songPane;
    }

    public void setSongPane(SongPane songPane) {
        this.songPane = songPane;
    }

    public ScalePane getScalePane() {
        return scalePane;
    }

    public void setScalePane(ScalePane scalePane) {
        this.scalePane = scalePane;
    }

}
