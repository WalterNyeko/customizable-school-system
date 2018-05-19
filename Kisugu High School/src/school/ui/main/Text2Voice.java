package school.ui.main;

import java.beans.PropertyVetoException;
import java.util.Locale;

import javax.sound.midi.Synthesizer;
import javax.speech.AudioException;
import javax.speech.Central;
import javax.speech.EngineException;
import javax.speech.EngineStateError;
import javax.speech.synthesis.SynthesizerModeDesc;

import com.sun.speech.freetts.Voice;

/**
 *
 * @author Manindar
 */
public class Text2Voice {

	SynthesizerModeDesc desc;
	javax.speech.synthesis.Synthesizer synthesizer;
	javax.speech.synthesis.Voice voice;

	public Text2Voice(String text) {
		// TODO Auto-generated constructor stub
		
		// su.init("kevin16");
				 
				// su.init("mbrola_us1");
				// su.init("mbrola_us2");
				// su.init("mbrola_us3");
				// high quality
				try {
					try {
						this.init("kevin");
					} catch (EngineStateError e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (PropertyVetoException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					this.doSpeak(text);
					this.terminate();
				} catch (EngineException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (AudioException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IllegalArgumentException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
		
	}

	public void init(String voiceName) throws EngineException, AudioException, EngineStateError, PropertyVetoException {
		if (desc == null) {
			System.setProperty("freetts.voices", "com.sun.speech.freetts.en.us.cmu_us_kal.KevinVoiceDirectory");
			desc = new SynthesizerModeDesc(Locale.US);
			Central.registerEngineCentral("com.sun.speech.freetts.jsapi.FreeTTSEngineCentral");
			synthesizer = Central.createSynthesizer(desc);
			synthesizer.allocate();
			synthesizer.resume();
			SynthesizerModeDesc smd = (SynthesizerModeDesc) synthesizer.getEngineModeDesc();
			javax.speech.synthesis.Voice[] voices = smd.getVoices();
			for (javax.speech.synthesis.Voice voice1 : voices) {
				if (voice1.getName().equals(voiceName)) {
					voice = voice1;
					break;
				}
			}
			synthesizer.getSynthesizerProperties().setVoice(voice);
		}
	}

	public void terminate() throws EngineException, EngineStateError {
		synthesizer.deallocate();
	}

	public void doSpeak(String speakText)
			throws EngineException, AudioException, IllegalArgumentException, InterruptedException {
		synthesizer.speakPlainText(speakText, null);
		synthesizer.waitEngineState(Synthesizer.class.getModifiers());
	}

//	public static void main(String[] args) throws Exception {
//		Text2Voice su = new Text2Voice();
//		
//	}
}