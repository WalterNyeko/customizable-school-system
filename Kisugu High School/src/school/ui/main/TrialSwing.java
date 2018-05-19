package school.ui.main;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JTextArea;

import com.sun.speech.freetts.Voice;
import com.sun.speech.freetts.VoiceManager;

import javafx.scene.Scene;

public class TrialSwing extends JDialog {

	private Scene scene;
	private JPanel jfxpanel;
	private static final String VOICENAME="kevin16k";

	public TrialSwing() {
		// TODO Auto-generated constructor stub
		setTitle("User Manual");
		setSize(650, 800);
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		setLocationRelativeTo(null);

		

		jfxpanel = new JPanel();

		
		JTextArea area=new JTextArea();
		area.setPreferredSize(new Dimension(600, 700));
		
		JButton btn=new JButton("Speak");
		
		jfxpanel.add(area);
		jfxpanel.add(btn);
		
		btn.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				Voice voice;
				VoiceManager vm =VoiceManager.getInstance();
				voice=vm.getVoice(VOICENAME);
				voice.allocate();
				
				try {
					
					voice.speak(area.getText());
					
					
				} catch (Exception e) {
					// TODO: handle exception
				}
			}
		});

		this.add(jfxpanel);
		this.setVisible(true);
	}

}
