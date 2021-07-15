package com.jun.plugin.demo;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;

import javax.sound.midi.InvalidMidiDataException;
import javax.sound.midi.MetaEventListener;
import javax.sound.midi.MetaMessage;
import javax.sound.midi.MidiChannel;
import javax.sound.midi.MidiSystem;
import javax.sound.midi.MidiUnavailableException;
import javax.sound.midi.Sequence;
import javax.sound.midi.Sequencer;
import javax.sound.midi.Synthesizer;

public class MidiDemo {
	private static String midiFile = "sound\\trippygaia1.mid";
	//private static String midiURI = "http://hostname/midifile";
	private Sequence sequence = null;

	public MidiDemo() {
		this.loadAndPlay();
	}

	public void loadAndPlay() {
		try {
			// ���ļ��ж�ȡ
			sequence = MidiSystem.getSequence(new File(midiFile));
			// ��URL�ж�ȡ
			// sequence = MidiSystem.getSequence
			//                (new URL("http://hostname/midifile"));

			// Ϊmidi���д���һ��sequencer����
			Sequencer sequencer = MidiSystem.getSequencer();
			sequencer.open();
			sequencer.setSequence(sequence);

			// ����midi���ų����ʱ��
			double durationInSecs = sequencer.getMicrosecondLength() / 1000000.0;
			System.out.println("the duration of this audio is "
					+ durationInSecs + "secs.");

			// ����Midi Sequencer�в��ŵĵ�ǰλ��
			double seconds = sequencer.getMicrosecondPosition() / 1000000.0;
			System.out.println("the Position of this audio is " + seconds
					+ "secs.");

			// ����Midi�������ŵ�����
			if (sequencer instanceof Synthesizer) {
				Synthesizer synthesizer = (Synthesizer) sequence;
				MidiChannel[] channels = synthesizer.getChannels();

				// gain ��ֵ��0��1֮��(loudest)
				double gain = 0.9D;
				for (int i = 0; i < channels.length; i++) {
					channels[i].controlChange(7, (int) (gain * 127.0));
				}
			}

			// ��ʼ����
			sequencer.start();

			// ���㵱ǰ��Midi Sequencer����λ��
			Thread.currentThread().sleep(5000);
			seconds = sequencer.getMicrosecondPosition() / 1000000.0;
			System.out.println("the Position of this audio is " + seconds
					+ "secs.");

			// ���ý����Ϣ�ļ�����
			sequencer.addMetaEventListener(new MetaEventListener() {
				public void meta(MetaMessage event) {
					if (event.getType() == 47) {
						System.out.println("Sequencer is done playing.");
					}
				}
			});
		} catch (MalformedURLException e) {
		} catch (IOException e) {
		} catch (MidiUnavailableException e) {
		} catch (InvalidMidiDataException e) {
		} catch (InterruptedException e) {
		}
	}

	public static void main(String[] args) {
		MidiDemo midi = new MidiDemo();

	}

}