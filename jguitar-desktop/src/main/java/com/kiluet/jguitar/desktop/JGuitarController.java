package com.kiluet.jguitar.desktop;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

import javax.sound.midi.InvalidMidiDataException;
import javax.sound.midi.MidiEvent;
import javax.sound.midi.MidiSystem;
import javax.sound.midi.MidiUnavailableException;
import javax.sound.midi.Sequence;
import javax.sound.midi.Sequencer;
import javax.sound.midi.ShortMessage;
import javax.sound.midi.Synthesizer;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang.math.NumberUtils;
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
import com.kiluet.jguitar.desktop.components.ScalePane;
import com.kiluet.jguitar.desktop.components.SongPane;
import com.kiluet.jguitar.desktop.components.TrackPane;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
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

public class JGuitarController extends BorderPane implements Initializable {

   private static final Logger logger = LoggerFactory.getLogger(JGuitarController.class);

   private static final JGuitarDAOManager daoMgr = JGuitarDAOManager.getInstance();

   private final Map<String, List<Beat>> beatMap = new LinkedHashMap<String, List<Beat>>();

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
   private ToggleButton wholeDurationButton, halfDurationButton, quarterDurationButton, eighthDurationButton, sixteenthDurationButton,
         thirtySecondDurationButton, sixtyFourthDurationButton;

   @FXML
   private Button playButton;

   @FXML
   private ComboBox<String> speedComboBox;

   private List<Beat> previousBeats;

   private Note selectedNote;

   private Main app;

   private Sequencer sequencer = null;

   private Synthesizer synthesizer = null;

   public JGuitarController() {
      super();

      try {
         this.sequencer = MidiSystem.getSequencer();
         this.sequencer.open();

         this.synthesizer = MidiSystem.getSynthesizer();
         sequencer.getTransmitter().setReceiver(synthesizer.getReceiver());
         synthesizer.open();
      } catch (MidiUnavailableException e) {
         e.printStackTrace();
      }

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

         scalesTableView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (oldValue != newValue) {
               getNotationBox().getChildren().clear();
               scalePane = new ScalePane(JGuitarController.this, newValue);
               getNotationBox().getChildren().add(scalePane);
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
      songsTableView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
         if (oldValue != newValue) {
            getNotationBox().getChildren().clear();
            songPane = new SongPane(JGuitarController.this);
            getNotationBox().getChildren().add(songPane);
         }
      });

      songsTableView.getSelectionModel().selectFirst();

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
            ((ToggleButton) previousToggle).requestFocus();
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
            ((ToggleButton) previousToggle).requestFocus();
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
            ((ToggleButton) previousToggle).requestFocus();
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
            ((ToggleButton) previousToggle).requestFocus();
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
            ((ToggleButton) previousToggle).requestFocus();
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
            ((ToggleButton) previousToggle).requestFocus();
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
            ((ToggleButton) previousToggle).requestFocus();
         }
      });
   }

   private Boolean durationToggle(DurationType durationType) {

      // DurationType durationType = (DurationType) beatDurationTypeGroup.getSelectedToggle().getUserData();
      if (selectedNote != null) {

         Beat selectedBeat = selectedNote.getBeat();

         Measure selectedMeasure = selectedBeat.getMeasure();

         Track selectedTrack = selectedMeasure.getTrack();

         List<Beat> proximalBeats = new ArrayList<Beat>();

         if (selectedNote.getBeat().getDuration().getCode() < durationType.getCode()) {
            // attempting to expand beats

            selectedMeasure.getBeats().forEach(beat -> {
               if (!beat.equals(selectedBeat) && beat.getNumber() > selectedBeat.getNumber()
                     && beat.getDuration().getCode() < selectedBeat.getDuration().getCode()) {
                  proximalBeats.add(beat);
               }
            });

            if (CollectionUtils.isEmpty(proximalBeats)) {
               expandBeats(proximalBeats, durationType);
               return true;
            } else {
               return false;
            }
         }

         if (selectedNote.getBeat().getDuration().getCode() > durationType.getCode()) {
            // attempting to collapse beats

            for (Beat beat : selectedMeasure.getBeats()) {
               if (!beat.equals(selectedBeat) && beat.getNumber() > selectedBeat.getNumber()
                     && beat.getDuration().equals(selectedBeat.getDuration())) {
                  proximalBeats.add(beat);
               }
            }

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
                  newMeasure = new Measure(track, measure.getMeterType(), measure.getTempo(), measure.getNumber() + 1);
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
      Measure measure = null;
      tracks: for (Track track : this.song.getTracks()) {
         for (Measure m : track.getMeasures()) {
            if (m.getNumber().equals(getSelectedNote().getBeat().getMeasure().getNumber())) {
               measure = m;
               break tracks;
            }
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

   private void collapseBeats(List<Beat> proximalBeats, DurationType durationType) {
      logger.info("ENTERING collapseBeats(DurationType)");

      Beat selectedBeat = selectedNote.getBeat();
      Measure selectedMeasure = selectedBeat.getMeasure();
      AtomicInteger beatNumber = new AtomicInteger(0);
      for (Beat beat : selectedMeasure.getBeats()) {
         if (beat.getNumber() > beatNumber.get()) {
            beatNumber.set(beat.getNumber());
         }
      }

      for (Beat beat : selectedMeasure.getBeats()) {
         if (beat.getNumber() >= beatNumber.get()) {
            try {
               beat.setNumber(beat.getNumber() - proximalBeats.size());
               daoMgr.getDaoBean().getBeatDAO().save(beat);
            } catch (Exception e) {
               e.printStackTrace();
            }
         }
      }

      try {
         Iterator<Beat> beatIter = selectedMeasure.getBeats().iterator();
         while (beatIter.hasNext()) {
            Beat beat = beatIter.next();
            if (proximalBeats.contains(beat)) {
               beatIter.remove();
               daoMgr.getDaoBean().getBeatDAO().delete(beat);
            }
         }
         selectedBeat.setDuration(durationType);
         daoMgr.getDaoBean().getBeatDAO().save(selectedBeat);
      } catch (JGuitarDAOException e) {
         e.printStackTrace();
      }
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

                     Node trackPaneNode = this.songPane.lookup(String.format("#TrackPane_%d", a.getTrack().getId()));
                     Node measurePaneNode = trackPaneNode.lookup(String.format("#MeasurePane_%d", a.getId()));

                     Node measureOpenSeparatorNode = measurePaneNode.lookup(String.format("#MeasureOpenSeparatorPane_%d", a.getId()));
                     if (measureOpenSeparatorNode != null && measureOpenSeparatorNode instanceof MeasureOpenSeparatorPane) {
                        MeasureOpenSeparatorPane measureSeparatorPane = (MeasureOpenSeparatorPane) measureOpenSeparatorNode;
                        measureSeparatorPane.getChildren().clear();
                        measureSeparatorPane.init();
                        measureSeparatorPane.requestLayout();
                     }

                     Node measureCloseSeparatorNode = measurePaneNode.lookup(String.format("#MeasureCloseSeparatorPane_%d", a.getId()));
                     if (measureCloseSeparatorNode != null && measureCloseSeparatorNode instanceof MeasureCloseSeparatorPane) {
                        MeasureCloseSeparatorPane measureSeparatorPane = (MeasureCloseSeparatorPane) measureCloseSeparatorNode;
                        measureSeparatorPane.getChildren().clear();
                        measureSeparatorPane.init();
                        measureSeparatorPane.requestLayout();
                     }

                     Node measureHeaderNode = trackPaneNode.lookup(String.format("#MeasureHeaderPane_%d", a.getId()));
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

               Node measureSeparatorNode = measurePaneNode.lookup(String.format("#MeasureCloseSeparatorPane_%d", a.getId()));
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
         ChoiceDialog<String> instrumentDialog = new ChoiceDialog<String>(instruments.get(0).getName(), instrumentValueList);
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
                  Measure newMeasure = new Measure(newTrack, measure.getMeterType(), measure.getTempo(), measure.getNumber());
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

      try {

         Sequence sequence = new Sequence(Sequence.PPQ, 1);
         javax.sound.midi.Track midiTrack = sequence.createTrack();
         sequencer.setTempoInBPM(120);
         sequencer.setTempoFactor(Float.valueOf(speedComboBox.getSelectionModel().getSelectedItem()) / 100F);

         if (getNotationBox().getChildren().get(0) instanceof SongPane) {

            for (Track track : this.song.getTracks()) {

               Integer tick = 0;

               List<InstrumentString> instrumentStrings = track.getInstrument().getStrings();

               ShortMessage shortMessage = new ShortMessage(ShortMessage.PROGRAM_CHANGE, 0, track.getInstrument().getProgram(), 0);
               midiTrack.add(new MidiEvent(shortMessage, 0));

               for (Measure measure : track.getMeasures()) {

                  for (Beat beat : measure.getBeats()) {

                     tick++;
                     logger.info("tick: {}", tick);

                     List<Note> notes = beat.getNotes();

                     List<Note> filteredNotes = notes.parallelStream().filter(a -> a.getValue() != null).collect(Collectors.toList());

                     for (Note note : filteredNotes) {
                        logger.info(note.toString());

                        InstrumentString foundInstrumentString = instrumentStrings.parallelStream()
                              .filter(a -> a.getString() == note.getString()).findFirst().get();
                        logger.info(foundInstrumentString.toString());

                        Integer key = foundInstrumentString.getPitch() + note.getValue();

                        ShortMessage on = new ShortMessage(ShortMessage.NOTE_ON, 0, key, 100);
                        midiTrack.add(new MidiEvent(on, tick));

                        ShortMessage off = new ShortMessage(ShortMessage.NOTE_OFF, 0, key, 100);
                        midiTrack.add(new MidiEvent(off, tick + beat.getDuration().getLength()));

                     }
                  }
               }
            }
         } else {

            Integer tick = 0;

            List<InstrumentString> instrumentStrings = this.scale.getTrack().getInstrument().getStrings();

            ShortMessage shortMessage = new ShortMessage(ShortMessage.PROGRAM_CHANGE, 0, this.scale.getTrack().getInstrument().getProgram(),
                  0);
            midiTrack.add(new MidiEvent(shortMessage, 0));

            for (Measure measure : this.scale.getTrack().getMeasures()) {
               for (Beat beat : measure.getBeats()) {

                  tick++;
                  logger.info("tick: {}", tick);

                  List<Note> notes = beat.getNotes();

                  List<Note> filteredNotes = notes.parallelStream().filter(a -> a.getValue() != null).collect(Collectors.toList());

                  for (Note note : filteredNotes) {
                     logger.info(note.toString());

                     InstrumentString foundInstrumentString = instrumentStrings.parallelStream()
                           .filter(a -> a.getString() == note.getString()).findFirst().get();
                     logger.info(foundInstrumentString.toString());

                     Integer key = foundInstrumentString.getPitch() + note.getValue();

                     ShortMessage on = new ShortMessage(ShortMessage.NOTE_ON, 0, key, 100);
                     midiTrack.add(new MidiEvent(on, tick));

                     ShortMessage off = new ShortMessage(ShortMessage.NOTE_OFF, 0, key, 100);
                     midiTrack.add(new MidiEvent(off, tick + beat.getDuration().getLength()));

                  }
               }
            }
         }

         sequencer.addMetaEventListener(a -> {
            Platform.runLater(() -> {
               if (a.getType() == 47) {
                  playButton.setText(">");
               }
            });
         });

         sequencer.setSequence(sequence);
         sequencer.start();
      } catch (InvalidMidiDataException e1) {
         e1.printStackTrace();
      }

   }

   @FXML
   private void pause(final ActionEvent event) {
      logger.info("ENTERING pause()");
      sequencer.stop();
      playButton.setText(">");
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
