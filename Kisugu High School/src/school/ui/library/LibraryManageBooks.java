package school.ui.library;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;

import school.controller.library.GeneralOverViewOfBooksController;
import school.model.library.GeneralOverViewOfBooks;
import school.ui.finances.CashBookController;

public class LibraryManageBooks extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JButton btnAddBook;
	private JButton btnEditBook;
	private JButton btnBack;
	private JButton btnPrintIssuedBooks;
	@SuppressWarnings("unused")
	private JButton btnPrintAllBooks;

	private JTable tableAllBooks;
	public JTable tableIssuedBooks;

	private JScrollPane scrollPaneAllBooks;
	private JScrollPane scrollPaneIssuedBooks;

	private JLabel labelTitleAllBooks;
	private JLabel labelTitleIssuedBooks;
	private JLabel labelFake1;
	private JLabel labelFake2;
	@SuppressWarnings("unused")
	private AddNewBook addNewBook;

	private GeneralOverViewOfBooksController generalOverViewOfBooksController;
	private GeneralOverViewOfBooksTableModel generalOverViewOfBooksTableModel;
	private List<GeneralOverViewOfBooks> books;

	public LibraryManageBooks() {
		bush();
	}

	private void bush() {

		btnBack = new JButton("Back");
		add(btnBack);

		btnAddBook = new JButton("Add New Book");
		btnAddBook.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				// create dialog
				AddNewBook addNewBook = new AddNewBook(LibraryManageBooks.this, generalOverViewOfBooksController);
				addNewBook.setVisible(true);
			}
		});
		add(btnAddBook);

		btnEditBook = new JButton("Edit book");
		btnEditBook.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				// get the selected book row
				int row = tableAllBooks.getSelectedRow();

				// make sure a row is selected
				if (row < 0) {
					JOptionPane.showMessageDialog(LibraryManageBooks.this, "You must select a book row", "Error",
							JOptionPane.ERROR_MESSAGE);
					return;
				}

				// get the current book row
				GeneralOverViewOfBooks tempBooks = (GeneralOverViewOfBooks) tableAllBooks.getValueAt(row,
						GeneralOverViewOfBooksTableModel.OBJECT_COLUMN);

				// create a dialog
				AddNewBook addNewBook = new AddNewBook(LibraryManageBooks.this, generalOverViewOfBooksController,
						tempBooks, true);
				addNewBook.setVisible(true);

			}
		});
		add(btnEditBook);

		btnPrintAllBooks = new JButton("Print All Books");

		labelFake1 = new JLabel();
		labelFake1.setPreferredSize(new Dimension(200, 25));
		;
		add(labelFake1);

		labelTitleAllBooks = new JLabel("General Overview Of Books");
		labelTitleAllBooks.setFont(new Font("Times New Roman", Font.BOLD, 18));
		add(labelTitleAllBooks);

		try {
			generalOverViewOfBooksController = new GeneralOverViewOfBooksController();
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		books = null;
		try {
			books = generalOverViewOfBooksController.getAllBookBatches();
			generalOverViewOfBooksTableModel = new GeneralOverViewOfBooksTableModel(books);
		} catch (SQLException e) {
			e.printStackTrace();
		}

		tableAllBooks = new JTable();
		tableAllBooks.setModel(generalOverViewOfBooksTableModel);

		JTableHeader headerColBooks = tableAllBooks.getTableHeader();
		headerColBooks.setPreferredSize(new Dimension(1150, 30));
		// headerColBooks.setBackground(Color.black);

		// scrollPaneAllBooks.setPreferredSize(new Dimension(1150, 220));
		scrollPaneAllBooks = new JScrollPane(tableAllBooks);

		add(scrollPaneAllBooks);

		labelFake2 = new JLabel();
		labelFake2.setPreferredSize(new Dimension(450, 25));
		;
		add(labelFake2);

		labelTitleIssuedBooks = new JLabel("General Overview Issued Books");
		labelTitleIssuedBooks.setFont(new Font("Times New Roman", Font.BOLD, 18));
		add(labelTitleIssuedBooks);

		///////////////////

		String[][] dataIssuedBooks = new String[][] { { null, null, null, null, null, null },
				{ null, null, null, null, null, null }, { null, null, null, null, null, null },
				{ null, null, null, null, null, null }, { null, null, null, null, null, null },
				{ null, null, null, null, null, null }, { null, null, null, null, null, null },
				{ null, null, null, null, null, null }, { null, null, null, null, null, null },
				{ null, null, null, null, null, null }, { null, null, null, null, null, null },
				{ null, null, null, null, null, null }, { null, null, null, null, null, null },
				{ null, null, null, null, null, null },

		};

		String[] headingIssuedBooks = new String[] { "Book Title", "Subject", "Author", "Publisher", "Quantity Issued",
				"Quantity Remaining" };

		DefaultTableModel model = new DefaultTableModel();
		model.setDataVector(dataIssuedBooks, headingIssuedBooks);

		tableIssuedBooks = new JTable(model);
		JTableHeader headerColIssuedBooks = tableIssuedBooks.getTableHeader();
		headerColIssuedBooks.setPreferredSize(new Dimension(1150, 30));
		// headerColIssuedBooks.setBackground(Color.black);

		scrollPaneIssuedBooks = new JScrollPane(tableIssuedBooks);

		add(scrollPaneIssuedBooks);

		
		btnPrintIssuedBooks = new JButton("Print Issued Books");
		add(btnPrintIssuedBooks);

	}

	public void refreshAllBooks() {
		try {
			generalOverViewOfBooksController = new GeneralOverViewOfBooksController();
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		books = null;
		try {
			books = generalOverViewOfBooksController.getAllBookBatches();
			generalOverViewOfBooksTableModel = new GeneralOverViewOfBooksTableModel(books);
		} catch (SQLException e) {
			e.printStackTrace();
		}

		tableAllBooks.setModel(generalOverViewOfBooksTableModel);
	}

	public GeneralOverViewOfBooksTableModel getGeneralOverViewOfBooksTableModel() {
		return generalOverViewOfBooksTableModel;
	}

	public JTable getTableAllBooks() {
		return tableAllBooks;
	}

	public JScrollPane getScrollPaneAllBooks() {
		return scrollPaneAllBooks;
	}

	public JScrollPane getScrollPaneIssuedBooks() {
		return scrollPaneIssuedBooks;
	}

	public void displayData(JTable table, String query) {

		Connection conn = null;
		PreparedStatement pst = null;
		ResultSet rs = null;

		try {

			conn = CashBookController.getConnection();
			pst = conn.prepareStatement(query);
			rs = pst.executeQuery();
			while (table.getRowCount() > 0) {
				((DefaultTableModel) table.getModel()).removeRow(0);

			}
			int columns = rs.getMetaData().getColumnCount();
			while (rs.next()) {
				Object[] row = new Object[columns];
				for (int i = 1; i <= columns; i++) {
					row[i - 1] = rs.getObject(i);
				}
				((DefaultTableModel) table.getModel()).insertRow(rs.getRow() - 1, row);
			}
			rs.close();
			pst.close();
			conn.close();

		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}

	}

}
