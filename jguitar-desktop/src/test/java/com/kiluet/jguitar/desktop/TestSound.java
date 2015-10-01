package com.kiluet.jguitar.desktop;

import java.io.File;
import java.io.IOException;

import javax.sound.midi.Instrument;
import javax.sound.midi.InvalidMidiDataException;
import javax.sound.midi.MidiChannel;
import javax.sound.midi.MidiDevice;
import javax.sound.midi.MidiSystem;
import javax.sound.midi.MidiUnavailableException;
import javax.sound.midi.Soundbank;
import javax.sound.midi.Synthesizer;

import org.junit.Test;

public class TestSound {

    @Test
    public void showInstruments() {
        try {
            Synthesizer synthesizer = MidiSystem.getSynthesizer();
            synthesizer.open();
            // Soundbank soundbank = synthesizer.getDefaultSoundbank();
            // System.out.println(soundbank.getName());
            // System.out.println(soundbank.getDescription());
            Instrument[] instruments = synthesizer.getAvailableInstruments();
            for (Instrument instrument : instruments) {
                if (instrument.getPatch().getBank() == 0) {
                    System.out.println(instrument.toString());
                }

                // System.out.println("instrument.getName(): " + instrument.getName());
                // System.out.println("instrument.getClass().getName(): " + instrument.getClass().getName());
                // System.out.println("instrument.getPatch().getBank(): " + instrument.getPatch().getBank());
                // System.out.println("instrument.getPatch().getProgram(): " + instrument.getPatch().getProgram());
            }
            synthesizer.close();

        } catch (MidiUnavailableException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void showDeluxeInstruments() {
        try {
            Soundbank soundbank = MidiSystem.getSoundbank(new File("/home/jdr0887/Downloads", "soundbank-deluxe.gm"));
            // Soundbank soundbank = MidiSystem
            // .getSoundbank(new File("/home/jdr0887/Downloads/fluid-soundfont", "FluidR3 GM2-2.SF2"));
            Instrument[] instruments = soundbank.getInstruments();
            for (Instrument instrument : instruments) {
                System.out.println(instrument.toString());
            }
        } catch (InvalidMidiDataException | IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void showMidiDeviceInfo() {
        MidiDevice.Info[] midiDeviceInfoArray = MidiSystem.getMidiDeviceInfo();
        for (MidiDevice.Info midiDeviceInfo : midiDeviceInfoArray) {
            System.out.println("Next device:");
            System.out.println("  Name: " + midiDeviceInfo.getName());
            System.out.println("  Description: " + midiDeviceInfo.getDescription());
            System.out.println("  Vendor: " + midiDeviceInfo.getVendor());
            System.out.println("  toString(): " + midiDeviceInfo.toString());
            System.out.println("");
        }

    }

    @Test
    public void testTurnOnOffSomeNotes() {
        try {
            Synthesizer synthesizer = MidiSystem.getSynthesizer();
            synthesizer.open();
            Instrument[] instruments = synthesizer.getAvailableInstruments();

            MidiChannel[] channels = synthesizer.getChannels();

            try {

                int idx = 27;
                // System.out.println(instruments[idx].toString());
                // channels[0].programChange(0, idx);
                //
                // for (Integer note : Arrays.asList(40, 45, 50, 55, 59, 64)) {
                // channels[0].noteOn(note, 200);
                // Thread.sleep(2000);
                // channels[0].noteOff(note);
                // }

                idx = 34;
                // System.out.println(instruments[idx].toString());
                // channels[0].programChange(0, idx);
                //
                // for (Integer note : Arrays.asList(28, 33, 38, 42)) {
                // channels[0].noteOn(note, 200);
                // Thread.sleep(2000);
                // channels[0].noteOff(note);
                // }

                idx = 42;
                System.out.println(instruments[idx].toString());
                channels[0].programChange(0, idx);

                // for (Integer note : Arrays.asList(36, 43, 50, 57)) {
                // channels[0].noteOn(note, 200);
                // Thread.sleep(2000);
                // channels[0].noteOff(note);
                // }

                idx = 40;
                // System.out.println(instruments[idx].toString());
                // channels[0].programChange(0, idx);
                //
                // for (Integer note : Arrays.asList(55, 62, 69, 76)) {
                // channels[0].noteOn(note, 200);
                // Thread.sleep(2000);
                // channels[0].noteOff(note);
                // }

                channels[0].noteOn(62, 200);
                Thread.sleep(3000);
                channels[0].noteOff(62);

            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            synthesizer.close();

        } catch (MidiUnavailableException e) {
            e.printStackTrace();
        }

    }

}
