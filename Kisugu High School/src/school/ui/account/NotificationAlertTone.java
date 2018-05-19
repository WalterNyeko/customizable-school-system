package school.ui.account;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.SourceDataLine;

public class NotificationAlertTone {

	public static float SAMPLE_RATE = 8000f;

	public static void tone(int hz, int msecs) throws LineUnavailableException {
		tone(hz, msecs, 0.8);
	}

	public static void tone(int hz, int msecs, double vol) throws LineUnavailableException {
		byte[] buf = new byte[1];
		AudioFormat af = new AudioFormat(SAMPLE_RATE, 8, 1, true, false);
		// sampleRate
		// sampleSizeInBits
		// channels
		// signed
		// bigEndian
		SourceDataLine sdl = AudioSystem.getSourceDataLine(af);
		sdl.open(af);
		sdl.start();
		for (int i = 0; i < msecs * 8; i++) {
			double angle = i / (SAMPLE_RATE / hz) * 4.0 * Math.PI;
			buf[0] = (byte) (Math.sin(angle) * 127.0 * vol);
			sdl.write(buf, 0, 1);
		}
		sdl.drain();
		sdl.stop();
		sdl.close();
	}

	public static void main(String[] args) throws Exception {
		// SoundAlert.tone(1000,100);
		// Thread.sleep(1000);
		NotificationAlertTone.tone(900, 200, 0.6);
		// Thread.sleep(100);
		// SoundAlert.tone(500,100);
		// Thread.sleep(1000);
		// SoundAlert.tone(400,500);
		// Thread.sleep(1000);
		// SoundAlert.tone(400,500, 0.2);

	}
}