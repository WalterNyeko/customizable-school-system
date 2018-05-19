package school.ui.library;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;

import school.controller.library.IssueBooksController;
import school.model.library.RecordEachBookDetailModel;

public class RecordEachBookDetail extends JDialog {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JTextField fieldBookId;
	private JTextField fieldBookTitle;
	private JTextField fieldSubjectName;

	private JLabel labelBookId;
	private JLabel labelBookTitle;
	private JLabel labelSubjectName;

	private JPanel panelHoldFields;

	private JButton btnSaveBook;
	private JButton btnCancel;

	private JTable tableEachBookDetails;
	private JScrollPane scrollPaneEachBookDetails;

	public RecordEachBookDetail() {
		eachBookDetail();
	}

	private void eachBookDetail() {

		setTitle("Record Individual Book Details");
		setSize(600, 450);
		setLocationRelativeTo(null);

		setLayout(new FlowLayout(FlowLayout.LEFT));

		panelHoldFields = new JPanel();
		panelHoldFields.setPreferredSize(new Dimension(350, 150));
		add(panelHoldFields);

		Dimension dimensionLabel = new Dimension(150, 30);
		Dimension dimensionFields = new Dimension(180, 30);

		labelBookId = new JLabel("Book ID:");
		labelBookId.setPreferredSize(dimensionLabel);
		panelHoldFields.add(labelBookId);
		fieldBookId = new JTextField();
		fieldBookId.setPreferredSize(dimensionFields);
		panelHoldFields.add(fieldBookId);

		labelBookTitle = new JLabel("Book Title:");
		labelBookTitle.setPreferredSize(dimensionLabel);
		panelHoldFields.add(labelBookTitle);
		fieldBookTitle = new JTextField();
		fieldBookTitle.setPreferredSize(dimensionFields);
		panelHoldFields.add(fieldBookTitle);

		labelSubjectName = new JLabel("Subject Name:");
		labelSubjectName.setPreferredSize(dimensionLabel);
		panelHoldFields.add(labelSubjectName);
		fieldSubjectName = new JTextField();
		fieldSubjectName.setPreferredSize(dimensionFields);
		panelHoldFields.add(fieldSubjectName);

		btnSaveBook = new JButton("Save");
		panelHoldFields.add(btnSaveBook);
		btnSaveBook.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				String bookId = fieldBookId.getText();
				String bookTitle = fieldBookTitle.getText();
				String subjectName = fieldSubjectName.getText();

				IssueBooksController controller = null;
				try {
					controller = new IssueBooksController();
				} catch (SQLException e2) {
					// TODO Auto-generated catch block
					e2.printStackTrace();
				}

				RecordEachBookDetailModel detailModel = new RecordEachBookDetailModel(bookId, bookTitle, subjectName);

				try {
					controller.addEachBookDetail(detailModel);
					JOptionPane.showMessageDialog(null, "Saved successfully");
					refresh();
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}

			}
		});

		btnCancel = new JButton("Cancel");
		panelHoldFields.add(btnCancel);

		List<RecordEachBookDetailModel> detailModels = null;
		IssueBooksController controller = null;
		try {
			controller = new IssueBooksController();
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		try {
			detailModels = controller.getEachBookDetails();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		RecordEachBookDetailTableModel model = new RecordEachBookDetailTableModel(detailModels);

		// table and scroll pane
		tableEachBookDetails = new JTable();
		tableEachBookDetails.setModel(model);

		scrollPaneEachBookDetails = new JScrollPane(tableEachBookDetails);
		scrollPaneEachBookDetails.setPreferredSize(new Dimension(590, 255));
		add(scrollPaneEachBookDetails);

		// setVisible(true);
	}

	private void refresh() {
		
		List<RecordEachBookDetailModel> detailModels = null;
		IssueBooksController controller = null;
		try {
			controller = new IssueBooksController();
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		try {
			detailModels = controller.getEachBookDetails();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		RecordEachBookDetailTableModel model = new RecordEachBookDetailTableModel(detailModels);

		// table and scroll pane
		tableEachBookDetails.setModel(model);
		
	}

}
