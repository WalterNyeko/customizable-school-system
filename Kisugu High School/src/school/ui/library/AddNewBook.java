package school.ui.library;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.Date;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import com.toedter.calendar.JDateChooser;

import school.controller.library.GeneralOverViewOfBooksController;
import school.model.library.GeneralOverViewOfBooks;

public class AddNewBook extends JDialog {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JLabel labelDate;
	private JLabel labelBookTitle;
	private JLabel labelSubject;
	private JLabel labelAuthor;
	private JLabel labelPublisher;
	private JLabel labelShelfName;
	private JLabel labelQty;
	private JLabel labelDonor;

	private JDateChooser dateChooser;
	private JTextField fieldBookTitle;
	private JTextField fieldSubject;
	private JTextField fieldAuthor;
	private JTextField fieldPublisher;
	private JTextField fieldShelfName;
	private JTextField fieldQty;
	private JTextField fieldDonor;

	private JButton btnSave;
	private JButton btnCancel;

	private JPanel holderPanel;

	private GeneralOverViewOfBooksController controller;

	private LibraryManageBooks libraryManageBooks;

	private GeneralOverViewOfBooks previousBooks = null;
	private boolean updateMode = false;

	public AddNewBook(LibraryManageBooks theLibraryManageBooks, GeneralOverViewOfBooksController theController,
			GeneralOverViewOfBooks theBookPrevious, boolean theUpdateMode) {
		this();
		controller = theController;
		libraryManageBooks = theLibraryManageBooks;

		previousBooks = theBookPrevious;

		updateMode = theUpdateMode;

		if (updateMode) {
			setTitle("Edit Book Details");

			populateGui(previousBooks);
		}
	}

	private void populateGui(GeneralOverViewOfBooks theBook) {
		dateChooser.setDate(theBook.getDateIn());
		fieldBookTitle.setText(theBook.getBookTitle());
		fieldSubject.setText(theBook.getSubject());
		fieldAuthor.setText(theBook.getAuthor());
		fieldPublisher.setText(theBook.getPublisher());
		fieldShelfName.setText(theBook.getShelfName());
		Integer qtyInt = new Integer(theBook.getQuantity());
		fieldQty.setText(qtyInt.toString());
		fieldDonor.setText(theBook.getDonor());
	}

	public AddNewBook(LibraryManageBooks theLibraryManageBooks, GeneralOverViewOfBooksController theController) {
		this(theLibraryManageBooks, theController, null, false);
	}

	public AddNewBook() {
		buildGUI();
	}

	private void buildGUI() {
		setSize(450, 380);
		setTitle("Add New Book Batch");
		setResizable(false);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setLocationRelativeTo(null);

		holderPanel = new JPanel();
		holderPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
		holderPanel.setPreferredSize(new Dimension(500, 380));
		add(holderPanel);

		Dimension dimLabels = new Dimension(200, 30);
		Dimension dimTextFields = new Dimension(200, 30);

		Font fontlabels = new Font("Times New Roman", Font.BOLD, 16);
		Font fontFieldText = new Font("Times New Roman", Font.BOLD, 14);

		labelDate = new JLabel("Date:");
		labelDate.setPreferredSize(dimLabels);
		labelDate.setFont(fontlabels);
		holderPanel.add(labelDate);

		dateChooser = new JDateChooser();
		dateChooser.getDate();
		dateChooser.setPreferredSize(dimTextFields);
		holderPanel.add(dateChooser);

		labelBookTitle = new JLabel("Book Title:");
		labelBookTitle.setPreferredSize(dimLabels);
		labelBookTitle.setFont(fontlabels);
		holderPanel.add(labelBookTitle);
		fieldBookTitle = new JTextField();
		fieldBookTitle.setPreferredSize(dimTextFields);
		fieldBookTitle.setFont(fontFieldText);
		holderPanel.add(fieldBookTitle);

		labelSubject = new JLabel("Subject:");
		labelSubject.setPreferredSize(dimLabels);
		labelSubject.setFont(fontlabels);
		holderPanel.add(labelSubject);
		fieldSubject = new JTextField();
		fieldSubject.setPreferredSize(dimTextFields);
		holderPanel.add(fieldSubject);

		labelAuthor = new JLabel("Author:");
		labelAuthor.setPreferredSize(dimLabels);
		labelAuthor.setFont(fontlabels);
		holderPanel.add(labelAuthor);
		fieldAuthor = new JTextField();
		fieldAuthor.setPreferredSize(dimTextFields);
		holderPanel.add(fieldAuthor);

		labelPublisher = new JLabel("Publisher:");
		labelPublisher.setPreferredSize(dimLabels);
		labelPublisher.setFont(fontlabels);
		holderPanel.add(labelPublisher);
		fieldPublisher = new JTextField();
		fieldPublisher.setPreferredSize(dimTextFields);
		holderPanel.add(fieldPublisher);

		labelShelfName = new JLabel("Shelf Name:");
		labelShelfName.setPreferredSize(dimLabels);
		labelShelfName.setFont(fontlabels);
		holderPanel.add(labelShelfName);
		fieldShelfName = new JTextField();
		fieldShelfName.setPreferredSize(dimTextFields);
		holderPanel.add(fieldShelfName);

		labelQty = new JLabel("Quantity:");
		labelQty.setPreferredSize(dimLabels);
		labelQty.setFont(fontlabels);
		holderPanel.add(labelQty);
		fieldQty = new JTextField();
		fieldQty.setPreferredSize(dimTextFields);
		holderPanel.add(fieldQty);

		labelDonor = new JLabel("Donor:");
		labelDonor.setPreferredSize(dimLabels);
		labelDonor.setFont(fontlabels);
		holderPanel.add(labelDonor);
		fieldDonor = new JTextField();
		fieldDonor.setPreferredSize(dimTextFields);
		holderPanel.add(fieldDonor);

		btnSave = new JButton("Save");
		btnSave.setFont(fontlabels);
		holderPanel.add(btnSave);
		btnSave.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				saveBookBatch();
			}
		});

		btnCancel = new JButton("Cancel");
		btnCancel.setFont(fontlabels);
		holderPanel.add(btnCancel);
		btnCancel.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				AddNewBook.this.setVisible(false);
			}
		});

		setVisible(true);
	}

	private void saveBookBatch() {
		try {
			controller = new GeneralOverViewOfBooksController();
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		// get the Book info from the gui

		Date date = dateChooser.getDate();
		String bookTitle = fieldBookTitle.getText();
		String subject = fieldSubject.getText();
		String author = fieldAuthor.getText();
		String publisher = fieldPublisher.getText();
		String shelfName = fieldShelfName.getText();

		String quantityStr = fieldQty.getText();
		Integer quantity = new Integer(quantityStr);

		String donor = fieldDonor.getText();

		GeneralOverViewOfBooks books = null;

		if (updateMode) {
			books = previousBooks;

			books.setDateIn(date);
			books.setBookTitle(bookTitle);
			books.setAuthor(author);
			books.setSubject(subject);
			books.setShelfName(shelfName);
			books.setDonor(donor);
			books.setQuantity(quantity);
			books.setPublisher(publisher);

		} else {
			books = new GeneralOverViewOfBooks(date, bookTitle, subject, author, publisher, shelfName, quantity, donor);
		}

		try {
			// save to database
			if (updateMode) {
				controller.editBookBatches(books);
			} else {
				controller.addNewBookBatch(books);
			}

			// close the dialog
			setVisible(false);

			// refresh the GUI list
			libraryManageBooks.refreshAllBooks();

			JOptionPane.showMessageDialog(null, "Saved");

			libraryManageBooks.refreshAllBooks();

		} catch (SQLException e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, "Not Saved " + e.getMessage());
		}

	}

	public JButton getBtnSave() {
		return btnSave;
	}

}
