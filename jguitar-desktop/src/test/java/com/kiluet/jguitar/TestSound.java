package com.kiluet.jguitar;

import javax.sound.midi.Instrument;
import javax.sound.midi.MidiChannel;
import javax.sound.midi.MidiDevice;
import javax.sound.midi.MidiSystem;
import javax.sound.midi.MidiUnavailableException;
import javax.sound.midi.Synthesizer;

import org.junit.Test;

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
            int idx = 34;
            System.out.println(instruments[idx].toString());

            MidiChannel[] channels = synthesizer.getChannels();

            try {
                
                channels[0].programChange(0, idx);
                channels[0].noteOn(22, 80);
                Thread.sleep(2000);
                channels[0].noteOff(64);

                channels[0].noteOn(60, 80);
                channels[0].noteOn(64, 80);
                channels[0].noteOn(67, 80);
                Thread.sleep(2000);
                channels[0].allNotesOff();

            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            synthesizer.close();

        } catch (MidiUnavailableException e) {
            e.printStackTrace();
        }

    }

}
