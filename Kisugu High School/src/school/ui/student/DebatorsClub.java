package school.ui.student;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.AdjustmentEvent;
import java.awt.event.AdjustmentListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Collections;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.SwingUtilities;
import javax.swing.border.LineBorder;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;

import school.ui.finances.CashBookController;
import school.ui.multiline.MultiLineTableCellRenderer;

public class DebatorsClub extends JPanel {

	public JTable tableDebate;
	private JLabel labelImage;
	private JButton btnBack, btnPrint;
	private int FIXED_NUM = 1;
	private String[][] dataDebate;
	private String[] headingDebate;
	private JTable fixedTable;
	private float sum;
	private float max;
	private float min;
	private AbstractTableModel model;
	private DefaultTableModel defaulttable;
	protected AbstractTableModel AbstractModel;
	private JTextField fieldTrial;
	private String[][] datafixed;
	protected DefaultTableModel tableModelDebators;
	private JButton btnAddNewClubMember;

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		new DebatorsClub();
	}

	public DebatorsClub() {
		// TODO Auto-generated constructor stub

		// setSize(900, 500);

		JPopupMenu popupMenuClubs = new JPopupMenu();
		JMenuItem editClubInfo = new JMenuItem("Edit Member Inforamtion");

		editClubInfo.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				EditClubMemberInfo editClubMemberInfo = new EditClubMemberInfo();
				editClubMemberInfo.setTitle("Edit Debator's Club Information");
				

				int row = tableDebate.getSelectedRow();
				String id = tableDebate.getValueAt(row, 0).toString();
				String studentSQL = "select CONCAT(first_name, ' ', middle_name, ' ', last_name) "
						+ "as Name,class_joining,gender from " + "tableadmissionstudentdetails where id_number='" + id
						+ "'";
				try {

					java.sql.Connection conn =  CashBookController.getConnection();

					PreparedStatement pst = conn.prepareStatement(studentSQL);
					ResultSet rs = pst.executeQuery();

					while (rs.next()) {
						editClubMemberInfo.fieldIDNo.setText(id);
						editClubMemberInfo.fieldStudentName.setText(rs.getString(1));
						editClubMemberInfo.fieldClass.setText(rs.getString(2));
						editClubMemberInfo.fieldGender.setText(rs.getString(3));
					}

				} catch (Exception ex) {
					ex.printStackTrace();
				}

			}
		});

		JMenuItem deleteClubInfo = new JMenuItem("Delete Selected Member");
		deleteClubInfo.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				String[] options = { "Delete Student", "Cancel" };
				int row = tableDebate.getSelectedRow();
				String id = tableDebate.getValueAt(row, 0).toString();
				int ans = JOptionPane.showOptionDialog(null, "Are You Sure You Want to Delete the selected Student???",
						"Confirmation of Delete Request", JOptionPane.YES_NO_CANCEL_OPTION,
						JOptionPane.QUESTION_MESSAGE, null, options, options[1]);
				if (ans == 0) {

					AddUpdateDelete("delete from debators where id_number='" + id + "'");

				} else {

				}

			}
		});
		JMenuItem addClubInfo = new JMenuItem("Add Club Member");

		addClubInfo.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				AddNewClubMember addNewClubMember = new AddNewClubMember();
				addNewClubMember.setTitle("Add Debator's Club Information");
			}
		});

		popupMenuClubs.add(addClubInfo);
		popupMenuClubs.add(editClubInfo);
		popupMenuClubs.add(deleteClubInfo);

		JPopupMenu popupMenuClubsData = new JPopupMenu();

		JMenuItem sumClubInfo = new JMenuItem("View Sum");
		sumClubInfo.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub

				int row = fixedTable.getSelectedRow();
				int col = fixedTable.getSelectedColumn();
				fixedTable.setValueAt((getSum()), row, col);

			}
		});

		JMenuItem averageClubInfo = new JMenuItem("View Average");
		averageClubInfo.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {

				int row = fixedTable.getSelectedRow();
				int col = fixedTable.getSelectedColumn();
				fixedTable.setValueAt((getAverage()), row, col);

			}
		});

		JMenuItem rangeClubInfo = new JMenuItem("View Range");
		rangeClubInfo.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				int row = fixedTable.getSelectedRow();
				int col = fixedTable.getSelectedColumn();
				fixedTable.setValueAt((getRange()), row, col);

			}
		});

		JMenuItem maxClubInfo = new JMenuItem("View Max");
		maxClubInfo.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				int row = fixedTable.getSelectedRow();
				int col = fixedTable.getSelectedColumn();
				fixedTable.setValueAt((getMax()), row, col);

			}
		});

		JMenuItem minClubInfo = new JMenuItem("View Min");
		minClubInfo.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				int row = fixedTable.getSelectedRow();
				int col = fixedTable.getSelectedColumn();
				fixedTable.setValueAt((getMin()), row, col);

			}
		});

		JMenuItem countClubInfo = new JMenuItem("View Count");
		countClubInfo.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				int row = fixedTable.getSelectedRow();
				int col = fixedTable.getSelectedColumn();
				fixedTable.setValueAt((getCount()), row, col);

			}
		});

		popupMenuClubsData.add(sumClubInfo);
		popupMenuClubsData.add(averageClubInfo);
		popupMenuClubsData.add(rangeClubInfo);

		popupMenuClubsData.add(maxClubInfo);
		popupMenuClubsData.add(minClubInfo);
		popupMenuClubsData.add(countClubInfo);

		btnBack = new JButton("Back");
		add(btnBack);

		btnPrint = new JButton("Print");
		add(btnPrint);

		btnAddNewClubMember = new JButton("Add New Club Member");
		btnAddNewClubMember.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				AddNewClubMember add = new AddNewClubMember();
				add.setTitle("Add Debator's Club Information");

			}
		});
		add(btnAddNewClubMember);

		JLabel labelFake = new JLabel();
		labelFake.setPreferredSize(new Dimension(780, 10));
		add(labelFake);

		labelImage = new JLabel("Debators Picture");
		labelImage.setPreferredSize(new Dimension(120, 100));
		labelImage.setBorder(new LineBorder(Color.red, 3));
		add(labelImage);

		dataDebate = new String[][] {

				{ null, null, null, null, null }, { null, null, null, null, null }, { null, null, null, null, null },
				{ null, null, null, null, null },

				{ null, null, null, null, null }, { null, null, null, null, null }, { null, null, null, null, null },
				{ null, null, null, null, null },

				{ null, null, null, null, null }, { null, null, null, null, null }, { null, null, null, null, null },
				{ null, null, null, null, null },

				{ null, null, null, null, null }, { null, null, null, null, null }, { null, null, null, null, null },
				{ null, null, null, null, null },

		};

		datafixed = new String[][] {

				{ null, null, null, null, null }

		};

		headingDebate = new String[] { "ID Number", "Student Name", "Class", "Sex", "Position" };

		////// my abstract table model for the upper table///////
		model = new AbstractTableModel() {
			public int getColumnCount() {
				return headingDebate.length;
			}

			public int getRowCount() {
				return dataDebate.length - FIXED_NUM;
			}

			public String getColumnName(int col) {
				return (String) headingDebate[col];
			}

			public Object getValueAt(int row, int col) {
				return dataDebate[row][col];
			}

			public void setValueAt(Object obj, int row, int col) {
				dataDebate[row][col] = (String) obj;
			}

			public boolean isCellEditable(int row, int col) {
				return true;
			}
		};

		/////// ends here////////

		/////////// my abstract table model for the fixed table model/////////

		AbstractTableModel fixedModel = new AbstractTableModel() {
			public int getColumnCount() {
				return headingDebate.length;
			}

			public int getRowCount() {
				return FIXED_NUM;
			}

			public Object getValueAt(int row, int col) {
				return dataDebate[row + (dataDebate.length - FIXED_NUM)][col];
			}

			public void setValueAt(Object object, int row, int col) {
				dataDebate[FIXED_NUM][col] = (String) object;
			}

			public boolean isCellEditable(int row, int col) {
				return true;
			}
		};

		///// ends here/////////////////////////////

		//// initializing the JTables for both the first abstract model and the
		//// second one////////////
		tableDebate = new JTable(model);
		tableDebate.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
		tableDebate.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		tableDebate.setShowGrid(false);
		JTableHeader headerReq = tableDebate.getTableHeader();
		headerReq.setBackground(Color.BLACK);
		headerReq.setPreferredSize(new Dimension(1170, 30));
		tableDebate.setRowHeight(25);

		tableDebate.addMouseListener(new MouseListener() {

			@Override
			public void mouseReleased(MouseEvent arg0) {
				// TODO Auto-generated method stub

			}

			@Override
			public void mousePressed(MouseEvent arg0) {
				// TODO Auto-generated method stub

			}

			@Override
			public void mouseExited(MouseEvent arg0) {
				// TODO Auto-generated method stub

			}

			@Override
			public void mouseEntered(MouseEvent arg0) {
				// TODO Auto-generated method stub

			}

			@Override
			public void mouseClicked(MouseEvent arg0) {
				

				int selectedRow = tableDebate.getSelectedRow();

				String studentID = (String) tableDebate.getValueAt(selectedRow, 0);
				String studentName = (String) tableDebate.getValueAt(selectedRow, 1);
				try {

					java.sql.Connection conn = CashBookController.getConnection();
					PreparedStatement pst = conn.prepareStatement(
							"select photo from tableadmissionstudentdetails where id_number='" + studentID + "'");
					ResultSet rs = pst.executeQuery();

					if (rs.next()) {

						byte[] img = rs.getBytes("photo");
						ImageIcon image = new ImageIcon(img);
						Image im = image.getImage();
						Image im2 = im.getScaledInstance(labelImage.getWidth(), labelImage.getHeight(),
								Image.SCALE_SMOOTH);
						ImageIcon newImage = new ImageIcon(im2);
						labelImage.setIcon(newImage);

					} else {
						JOptionPane.showMessageDialog(null, "No Image Found For " + studentName + " In The Database",
								"Lacking " + studentName + "'s Photo In Database", JOptionPane.INFORMATION_MESSAGE);
					}

				} catch (Exception ex) {
					ex.printStackTrace();
				}

			}
		});

		fixedTable = new JTable(datafixed, headingDebate);
		fixedTable.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
		fixedTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		fixedTable.setRowHeight(25);
		fixedTable.setForeground(Color.white);
		MultiLineTableCellRenderer renderer = new MultiLineTableCellRenderer();

		fixedTable.setDefaultRenderer(Object.class, renderer);

		///// ends here///////////////////////////////////

		//////// setting the column widths of both tables////////

		tableDebate.getColumnModel().getColumn(0).setPreferredWidth(125);
		tableDebate.getColumnModel().getColumn(2).setPreferredWidth(125);
		tableDebate.getColumnModel().getColumn(3).setPreferredWidth(125);
		tableDebate.getColumnModel().getColumn(4).setPreferredWidth(310);
		tableDebate.getColumnModel().getColumn(1).setPreferredWidth(450);

		fixedTable.getColumnModel().getColumn(0).setPreferredWidth(125);
		fixedTable.getColumnModel().getColumn(2).setPreferredWidth(125);
		fixedTable.getColumnModel().getColumn(3).setPreferredWidth(125);
		fixedTable.getColumnModel().getColumn(4).setPreferredWidth(310);
		fixedTable.getColumnModel().getColumn(1).setPreferredWidth(450);

		///// ends here/////////////////////////////////////

		///// don not allow horizontal scrolling around the upper data table////
		JScrollPane scroll = new JScrollPane(tableDebate);
		scroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

		///// ends
		///// here///////////////////////////////////////////////////////////

		////// allow horizontal scrolling around the lower fixed Table
		////// always//////

		JScrollPane fixedScroll = new JScrollPane(fixedTable) {
			public void setColumnHeaderView(Component view) {
			}
		};

		fixedScroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		JScrollBar bar = fixedScroll.getVerticalScrollBar();
		JScrollBar dummyBar = new JScrollBar() {
			public void paint(Graphics g) {
			}
		};
		dummyBar.setPreferredSize(bar.getPreferredSize());
		fixedScroll.setVerticalScrollBar(dummyBar);

		final JScrollBar bar1 = scroll.getHorizontalScrollBar();
		JScrollBar bar2 = fixedScroll.getHorizontalScrollBar();
		bar2.addAdjustmentListener(new AdjustmentListener() {
			public void adjustmentValueChanged(AdjustmentEvent e) {
				bar1.setValue(e.getValue());
			}
		});

		/////////////// ends
		/////////////// here/////////////////////////////////////////////////////

		////////// add and set the sizes of both tables////////
		scroll.setPreferredSize(new Dimension(1155, 330));
		fixedScroll.setPreferredSize(new Dimension(1155, 28)); // Hmm...
		add(scroll, BorderLayout.CENTER);
		add(fixedScroll, BorderLayout.SOUTH);

		////// ends here////////////////////////////////////////////////////

		tableDebate.addMouseListener(new MouseListener() {

			@Override
			public void mouseReleased(MouseEvent arg0) {
				// TODO Auto-generated method stub

			}

			@Override
			public void mousePressed(MouseEvent arg0) {
				// TODO Auto-generated method stub

			}

			@Override
			public void mouseExited(MouseEvent arg0) {
				// TODO Auto-generated method stub

			}

			@Override
			public void mouseEntered(MouseEvent arg0) {
				// TODO Auto-generated method stub

			}

			@Override
			public void mouseClicked(MouseEvent e) {
				// TODO Auto-generated method stub
				if (SwingUtilities.isRightMouseButton(e)) {
					popupMenuClubs.show(tableDebate, e.getX(), e.getY());
				}
			}
		});

		fixedTable.addMouseListener(new MouseListener() {

			@Override
			public void mouseReleased(MouseEvent arg0) {
				// TODO Auto-generated method stub

			}

			@Override
			public void mousePressed(MouseEvent arg0) {
				// TODO Auto-generated method stub

			}

			@Override
			public void mouseExited(MouseEvent arg0) {
				// TODO Auto-generated method stub

			}

			@Override
			public void mouseEntered(MouseEvent arg0) {
				// TODO Auto-generated method stub

			}

			@Override
			public void mouseClicked(MouseEvent e) {
				// TODO Auto-generated method stub
				if (SwingUtilities.isRightMouseButton(e)) {
					popupMenuClubsData.show(fixedTable, e.getX(), e.getY());
				}
			}
		});

		setVisible(true);
	}

	public String getSum() {

		int rowsCount = tableDebate.getRowCount();
		sum = 0;
		for (int i = 0; i < rowsCount; i++) {
			sum = sum + Float.parseFloat(tableDebate.getValueAt(i, 4).toString());
		}

		return "SUM = " + sum;

	}

	public String getAverage() {
		float Sum = sum;
		int rowsCount = tableDebate.getRowCount();
		float average = Sum / rowsCount;
		return "AVERAGE = " + average;

	}

	public String getMax() {

		ArrayList<Float> list = new ArrayList<Float>();
		for (int i = 0; i < tableDebate.getRowCount(); i++) {

			list.add(Float.parseFloat(tableDebate.getValueAt(i, 4).toString()));
		}

		float max = Collections.max(list);

		return "MAX = " + max;

	}

	public String getMin() {

		ArrayList<Float> list = new ArrayList<Float>();
		for (int i = 0; i < tableDebate.getRowCount(); i++) {

			list.add(Float.parseFloat(tableDebate.getValueAt(i, 4).toString()));
		}

		float min = Collections.min(list);
		return "MIN = " + min;
	}

	public String getCount() {

		int rowsCount = tableDebate.getRowCount();

		return "COUNT = " + rowsCount;

	}

	public String getRange() {

		float maximum = max;
		float minimum = min;
		float range = maximum - minimum;
		return "RANGE = " + range;

	}

	public void AddUpdateDelete(String querries) {

		

		try {

			java.sql.Connection conn = null;
			java.sql.PreparedStatement pst = null;
			conn = CashBookController.getConnection();
			pst = conn.prepareStatement(querries);

			pst.executeUpdate();

			JOptionPane.showMessageDialog(null, "Request Executed Successfully");

		} catch (Exception ex) {
			ex.printStackTrace();
			JOptionPane.showMessageDialog(null, "Request Not Executed Successfully " + ex.getMessage());

		}
	}

}
