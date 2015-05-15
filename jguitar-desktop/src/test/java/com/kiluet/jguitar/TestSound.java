package com.kiluet.jguitar;

import java.util.Arrays;

import javax.sound.midi.Instrument;
import javax.sound.midi.MidiChannel;
import javax.sound.midi.MidiDevice;
import javax.sound.midi.MidiSystem;
import javax.sound.midi.MidiUnavailableException;
import javax.sound.midi.Synthesizer;

import org.junit.Test;

import com.kiluet.jguitar.dao.model.InstrumentString;

public class TestSound {

    @Test
    public void showInstruments() {
        try {
            Synthesizer synthesizer = MidiSystem.getSynthesizer();
            synthesizer.open();
            Instrument[] instruments = synthesizer.getAvailableInstruments();
            for (Instrument instrument : instruments) {
                System.out.println(instrument.toString());
            }

            synthesizer.close();

        } catch (MidiUnavailableException e) {
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
            for (Instrument i : instruments) {
                System.out.println(i.toString());
            }

            MidiChannel[] channels = synthesizer.getChannels();

            try {

                int idx = 27;
                System.out.println(instruments[idx].toString());
                channels[0].programChange(0, idx);

                for (Integer note : Arrays.asList(40, 45, 50, 55, 59, 64)) {
                    channels[0].noteOn(note, 200);
                    Thread.sleep(2000);
                    channels[0].noteOff(note);
                }

                idx = 34;
                System.out.println(instruments[idx].toString());
                channels[0].programChange(0, idx);

                for (Integer note : Arrays.asList(28, 33, 38, 42)) {
                    channels[0].noteOn(note, 200);
                    Thread.sleep(2000);
                    channels[0].noteOff(note);
                }

                idx = 42;
                System.out.println(instruments[idx].toString());
                channels[0].programChange(0, idx);

                for (Integer note : Arrays.asList(36, 43, 50, 57)) {
                    channels[0].noteOn(note, 200);
                    Thread.sleep(2000);
                    channels[0].noteOff(note);
                }

                idx = 40;
                System.out.println(instruments[idx].toString());
                channels[0].programChange(0, idx);

                for (Integer note : Arrays.asList(55, 62, 69, 76)) {
                    channels[0].noteOn(note, 200);
                    Thread.sleep(2000);
                    channels[0].noteOff(note);
                }

            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            synthesizer.close();

        } catch (MidiUnavailableException e) {
            e.printStackTrace();
        }

    }

}
