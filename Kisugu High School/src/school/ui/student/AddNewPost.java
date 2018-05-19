package school.ui.student;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;

public class AddNewPost extends JFrame {

	private JLabel labelNewPost;
	private JTextField fieldPost;
	private JButton btnSave;

	public AddNewPost() {
		// TODO Auto-generated constructor stub
		builGUI();
	}

	private void builGUI() {
		// setTitle("Add New Post");
		setLocationRelativeTo(null);
		setSize(450, 100);
		setResizable(false);
		setLayout(new FlowLayout(FlowLayout.LEFT));

		labelNewPost = new JLabel("Post Name");
		labelNewPost.setPreferredSize(new Dimension(80, 30));
		add(labelNewPost);

		fieldPost = new JTextField();
		fieldPost.setPreferredSize(new Dimension(200, 30));
		add(fieldPost);

		btnSave = new JButton("Add Post");
		btnSave.setPreferredSize(new Dimension(100, 30));
		add(btnSave);
		btnSave.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				PrefectPostController controller = null;
				try {
					controller = new PrefectPostController();
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				String post = fieldPost.getText();
				PrefectPost prefectPost = new PrefectPost(post);
				try {
					controller.addPrefectPost(prefectPost);
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

				AddNewPost.this.setVisible(false);
			}
		});

		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setVisible(true);
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		new AddNewPost();
	}

}
