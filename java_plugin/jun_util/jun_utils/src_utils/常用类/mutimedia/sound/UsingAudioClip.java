package book.mutimedia.sound;

import java.io.File;
import java.net.MalformedURLException;

/**
 * 支持.wav/.au/.aiff/.mid声音文件的播放
 */
public class UsingAudioClip {
	public static void main(String[] args) {
		// 打开wav文件
		String fileName = "C:/temp/test.wav";
		File file = new File(fileName);
		java.applet.AudioClip clip;
		try {
			// 利用URL创建一个AudioClip对象。AudioClip是一个接口。
			clip = java.applet.Applet.newAudioClip(file.toURL());
			// 播放音频文件
			clip.play();
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
	}
}
