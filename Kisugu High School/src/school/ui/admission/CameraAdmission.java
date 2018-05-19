package school.ui.admission;


import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;

import com.github.sarxos.webcam.Webcam;

import school.ui.main.MainPage;

public class CameraAdmission extends JFrame {

	private Image image;

	/**
	 * 
	 */
	private static final long serialVersionUID = 3799318832516336691L;
	private JLabel labelImage;
	private JLabel labelImageHolder;
	private JButton btnCapture;

	private Webcam webcam;
	private Boolean isRunning = false;
	
	
	private MainPage mainPage;

	public CameraAdmission() {
		webcam = Webcam.getDefault();

		webcam.setViewSize(new Dimension(320, 240));
		webcam.open();

		setTitle("Capture image");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setSize(450, 600);
		setLocationRelativeTo(null);

		labelImage = new JLabel("");
		labelImage.setPreferredSize(new Dimension(330, 300));
		add(labelImage, BorderLayout.CENTER);

//		labelImageHolder = new JLabel("");
//		labelImageHolder.setBorder(new LineBorder(Color.black, 3));
//		labelImageHolder.setPreferredSize(new Dimension(100, 200));
//		add(labelImageHolder, BorderLayout.EAST);

		btnCapture = new JButton("Capture");
		btnCapture.setPreferredSize(new Dimension(150, 30));
		btnCapture.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				if (!isRunning) {
					isRunning = true;
					new VideoFeedTaker().start();
				} else {
					isRunning = false;
					image = webcam.getImage();
					mainPage.getLabelPictureUploaded().setIcon(new ImageIcon(image));
					System.exit(0);
				}

			}
		});
		add(btnCapture, BorderLayout.SOUTH);

		setVisible(true);

	}

	public static void main(String[] args) {
		@SuppressWarnings("unused")
		CameraAdmission chop = new CameraAdmission();
	}

	class VideoFeedTaker extends Thread {
		@Override
		public void run() {
			// TODO Auto-generated method stub
			while (isRunning) {

				try {
					Image image = webcam.getImage();
					labelImage.setIcon(new ImageIcon(image));

					Thread.sleep(10);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}

}
