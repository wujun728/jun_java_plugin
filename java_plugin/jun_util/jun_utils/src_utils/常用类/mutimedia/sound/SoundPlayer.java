package book.mutimedia.sound;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

import javax.sound.midi.InvalidMidiDataException;
import javax.sound.midi.MidiSystem;
import javax.sound.midi.MidiUnavailableException;
import javax.sound.midi.Receiver;
import javax.sound.midi.Sequence;
import javax.sound.midi.Sequencer;
import javax.sound.midi.Synthesizer;
import javax.sound.midi.Track;
import javax.sound.midi.Transmitter;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.FloatControl;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.SwingUtilities;
import javax.swing.Timer;
import javax.swing.UIManager;
import javax.swing.border.TitledBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

/**
 * 使用javax的sound包实现音频的播放器
 * 该程序能够控制音频的播放与停止，显示和控制音频播放的进度，调节声音大小和声道.
 * 支持.wav/.au/.aiff/.mid声音文件的播放。
 * 在处理MIDI文件和其他声音文件时区别，这是因为对于其他音频文件，时间以微秒计算，
 * 而MIDI文件以MIDI的“ticks”计算
 */
public class SoundPlayer extends JComponent {
	
	// 区分播放的是否为MIDI文件
    boolean isMIDI; 
    // MIDI文件的内容
    Sequence sequence; 
    // 播放MIDI内容的Sequencer。
    Sequencer sequencer; 
    
    // 其他音频文件的声音剪辑对象
    Clip clip;
    
    // 标示音频是否在播放 
    boolean playing = false;

    // 标示音频的长度，一般音频文件用毫秒计算。
    // MIDI文件用“ticks”数计算
    int audioLength;   
    // 记录当前播放的位置
    int audioPosition = 0; 

    // 使用到的Swing组件
    JButton play; // 播放暂停按钮
    JSlider progress; // 进度条
    JLabel time;	// 播放时间
    Timer timer;

    // 创建一个SoundPlayer组件并在JFrame中显示
    public static void main(String[] args) 
        throws IOException,
               UnsupportedAudioFileException,
               LineUnavailableException,
               MidiUnavailableException,
               InvalidMidiDataException
    {
        SoundPlayer player;
        // 待播放的文件
        String fileName = "C:/temp/faruxue.mid";
//        String fileName = "C:/temp/test.wav";
        File file = new File(fileName); 
        // 判断是否为MIDI文件
        boolean ismidi;
        try {
        	// 通过MidiSystem类的getMidiFileFormat从文件中读取MID内容。
        	// 如果读取成功，则表明为MIDI文件
            MidiSystem.getMidiFileFormat(file);
            ismidi = true;
        } catch(InvalidMidiDataException e) {
        	// 读取失败，表示不是一个MIDI文件
            ismidi = false;
        }

        // 创建一个SoundPlayer对象
        player = new SoundPlayer(file, ismidi);


        // 放入JFrame
        JFrame f = new JFrame("音频播放器");
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.getContentPane().add(player, "Center");
        f.pack();
		try {
			//设置界面的外观，为系统外观
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
			SwingUtilities.updateComponentTreeUI(f);
		} catch (Exception e) {
			e.printStackTrace();
		}
        f.setVisible(true);
    }

    // 构造方法
	public SoundPlayer(File f, boolean isMidi) throws IOException,
			UnsupportedAudioFileException, LineUnavailableException,
			MidiUnavailableException, InvalidMidiDataException {
		if (isMidi) {
			// 如果是MIDI文件，需要用sequencer播放
			isMIDI = true;
			// 首先获取sequencer并打开它
			sequencer = MidiSystem.getSequencer();
			sequencer.open();

			// 创建一个合成器
			Synthesizer synth = MidiSystem.getSynthesizer();
			synth.open();

			// 将sequencer与合成器连接起来
			Transmitter transmitter = sequencer.getTransmitter();
			Receiver receiver = synth.getReceiver();
			transmitter.setReceiver(receiver);

			// 从MIDI文件读取sequence
			sequence = MidiSystem.getSequence(f);
			// 获取MIDI文件的ticks长度	
			audioLength = (int) sequence.getTickLength();
			// 将sequence对象设置在sequencer内
			sequencer.setSequence(sequence);
		} else { 
			// 如果是一个普通音频文件
			isMIDI = false;
			// 获取音频输入流
			AudioInputStream ain = AudioSystem.getAudioInputStream(f);
			try {
				// 根据音频的格式打开文件
				DataLine.Info info = new DataLine.Info(Clip.class, ain
						.getFormat());
				clip = (Clip) AudioSystem.getLine(info);
				clip.open(ain);
			} finally { 
				ain.close();
			}
			// 获取音频剪辑的长度，将微秒转换成毫秒
			audioLength = (int) (clip.getMicrosecondLength() / 1000);
		}

		// 初始化GUI
		play = new JButton("play");
		// 创建一个进度条，参数为进度条的最小值、最大值、初始值
		progress = new JSlider(0, audioLength, 0); 
		time = new JLabel("0");// 显示当前播放的时间

		// 为按钮添加时间处理器，控制播放和暂停
		play.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (playing){
					suspend();
				}else {
					play();
				}
			}
		});

		// 为进度条改变事件处理器。
		progress.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				// 如果改变了进度条的值，则将时间标签修改
				int value = progress.getValue();
				if (isMIDI){
					time.setText(value + "");
				} else {
					time.setText(value / 1000 + "." + (value % 1000) / 100);
				}
				// 如果播放位置的目标值和当前值不同，则跳到目标值位置
				if (value != audioPosition) {
					skip(value);
				}
			}
		});

		// 控制进度条的值的变化，每一秒进度条动10次
		timer = new javax.swing.Timer(100, new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				tick();
			}
		});

		// 将上述的3个组件放在一个面板中，从左到右的显示
		JPanel panel1 = new JPanel();
		panel1.setLayout(new FlowLayout());
		panel1.add(play);
		panel1.add(progress);
		panel1.add(time);

		setLayout(new BorderLayout());
		this.add(panel1, BorderLayout.NORTH);

		// 增加控制组件
		if (isMIDI){
			addMidiControls();
		} else {
			addSampledControls();
		}
	}
    /** 
     * 播放音频
     */
    public void play() {
		if (isMIDI){
			sequencer.start();
		} else {
			clip.start();
		}
		timer.start();
		play.setText("suspend");
		playing = true;
	}
    /** 
     * 暂停音频
     */
    public void suspend() {
		timer.stop();
		// 停止音频
		if (isMIDI){
			sequencer.stop();
		} else {
			clip.stop();
		}
		play.setText("play");
		playing = false;
	}
    /** 
     * 停止播放，并将播放进度调整为0 
     */
    public void reset() {
		suspend();
		if (isMIDI){
			sequencer.setTickPosition(0);
		} else {
			clip.setMicrosecondPosition(0);
		}
		audioPosition = 0;
		progress.setValue(0);
	}

    /** 
     * 跳转到指定位置，当拖动进度条时调用该方法
     */
    public void skip(int position) {
		if (position < 0 || position > audioLength){
			return;
		}
		audioPosition = position;
		if (isMIDI){
			sequencer.setTickPosition(position);
		} else {
			clip.setMicrosecondPosition(position * 1000);
		}
		// 更新进度条的值
		progress.setValue(position); 
	}

    /** 
     * 返回音频长度
     */
	public int getLength() {
		return audioLength;
	}

    /**
     * Timer每1秒调用10次该方法，调整进度条的值。
     * 当音频结束时，将进度条的值归0
     */
	void tick() {
    	// 获取当前音频播放的位置，并将进度条指向该位置
        if (isMIDI && sequencer.isRunning()) {
            audioPosition = (int)sequencer.getTickPosition();
            progress.setValue(audioPosition);
        }  else if (!isMIDI && clip.isActive()) {
            audioPosition = (int)(clip.getMicrosecondPosition()/1000);
            progress.setValue(audioPosition);
        }  else {
        	reset();
        }
    }
    /**
     * 一般音频文件的控制器
     */
    void addSampledControls() {
        try {
        	// FloatControl提供一系列的浮点值，
        	// FloatControl.Type.MASTER_GAIN表示获取控制音频声音大小的FloatControl，单位是dB
			FloatControl gainControl = (FloatControl) clip
					.getControl(FloatControl.Type.MASTER_GAIN);
			if (gainControl != null){
				// 创建一个控制声音大小的进度条
				this.add(createSlider(gainControl), BorderLayout.CENTER);
			}
		} catch(IllegalArgumentException e) {
        }
        try {
        	// FloatControl.Type.PAN表示获取控制音频声道的FloatControl。
			FloatControl panControl = (FloatControl) clip
					.getControl(FloatControl.Type.PAN);
			if (panControl != null) {
				// 创建一个控制声道的进度条
				this.add(createSlider(panControl), BorderLayout.SOUTH);
			}
		}
        catch(IllegalArgumentException e) { 
        }
    }
    /**
     * 根据FloatControl创建一个进度条
     */
    JSlider createSlider(final FloatControl c) {
        if (c == null){
			return null;
        }
        // 创建一个进度条
		final JSlider slider = new JSlider(0, 1000);
		// 获取FloatControl的最小值，最大值，和当前值
		final float min = c.getMinimum();
		final float max = c.getMaximum();
		final float width = max - min;
		float fval = c.getValue();
		// 设置进度条的当前值
		slider.setValue((int) ((fval - min) / width * 1000));

		// 在进度条上设置3个刻度，最左边表示FloatControl最小值的标签
		// 中间表示FloatControl中间值的标签，最右边表示FloatControl最大值的标签
		java.util.Hashtable labels = new java.util.Hashtable(3);
		labels.put(new Integer(0), new JLabel(c.getMinLabel()));
		labels.put(new Integer(500), new JLabel(c.getMidLabel()));
		labels.put(new Integer(1000), new JLabel(c.getMaxLabel()));
		// 将这些刻度标记在进度条上并显示
		slider.setLabelTable(labels);
		slider.setPaintLabels(true);

		// 为进度条设置一个有标题的边框
		// 边框的标题为FloatControl的类型 和 FloatControl的单位，如dB
        slider.setBorder(new TitledBorder(c.getType().toString() + " " +
                                     c.getUnits()));

        // 为进度条添加事件处理器，当拖动进度条时触发该类事件
        slider.addChangeListener(new ChangeListener() {
                public void stateChanged(ChangeEvent e) {
                	// 获取进度条的值
                    int i = slider.getValue();
                    // 反射成 FloatControl的值
                    float f = min + (i*width/1000.0f);
                    // 设置到FloatControl中
                    c.setValue(f);
                }
            });
        return slider;
    }

    /**
     * 对于MIDI音频，创建进度条控制音乐的速度tempo
     * 创建复选框选择是独奏solo还是使用弱音器mute
     */
    void addMidiControls() {
        // 创建进度条控制速度
		final JSlider tempoSlider = new JSlider(50, 200);
		// 进度条的值设为当前音频的速度
		tempoSlider.setValue((int) (sequencer.getTempoFactor() * 100));
		// 创建一个有标题的边框
		tempoSlider.setBorder(new TitledBorder("Tempo Adjustment (%)"));
		java.util.Hashtable labels = new java.util.Hashtable();
		// 为进度条设置3个刻度，标签分别为50%，100%，200%
		labels.put(new Integer(50), new JLabel("50%"));
		labels.put(new Integer(100), new JLabel("100%"));
		labels.put(new Integer(200), new JLabel("200%"));
		// 将刻度添加到进度条上并显示
		tempoSlider.setLabelTable(labels);
		tempoSlider.setPaintLabels(true);
		// 添加事件处理器
		tempoSlider.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				sequencer.setTempoFactor(tempoSlider.getValue() / 100.0f);
			}
		});

		this.add(tempoSlider, BorderLayout.CENTER);

		//　获取音频的轨道track
		Track[] tracks = sequence.getTracks();
		// 创建一个大的面板，放置这些选择框
		JPanel trackPanel = new JPanel();
		trackPanel.setLayout(new GridLayout(tracks.length, 1));
		// 为每一个轨道track创建选择框，是solo还是mute
		for (int i = 0; i < tracks.length; i++) {
			final int tracknum = i;
			// 每个轨道两个选择矿
			final JCheckBox solo = new JCheckBox("solo");
			final JCheckBox mute = new JCheckBox("mute");
			// 添加事件处理器
			solo.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					// 为sequencer设置轨道的solo值
					sequencer.setTrackSolo(tracknum, solo.isSelected());
				}
			});
			mute.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					// 为sequencer设置轨道的mute值
					sequencer.setTrackMute(tracknum, mute.isSelected());
				}
			});

			// 将每个track的复选框都放在一个面板内
			JPanel panel = new JPanel();
			panel.add(new JLabel("Track " + tracknum));
			panel.add(solo);
			panel.add(mute);
			
			trackPanel.add(panel);
		}
		// 添加到窗体中
		this.add(trackPanel, BorderLayout.SOUTH);
	}
}