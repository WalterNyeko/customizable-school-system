package school.ui.staff;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.border.Border;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;

import school.ui.finances.CashBookController;
import school.ui.jdbc.JavaDatabaseSelectStatements;

public class StaffsResponsibilities extends JFrame {

	JTable tableSuspension;
	protected DefaultTableModel modelDismissedStudents;

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		new StaffsResponsibilities();
	}

	public StaffsResponsibilities() {
		// TODO Auto-generated constructor stub
		super("");

		setSize(900, 450);
		setResizable(false);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		// setLocation(200,100);
		setLocationRelativeTo(null);

		JPanel panelforrecord = new JPanel();
		
		DefaultTableModel model=new DefaultTableModel();
		

		String[][] dataS1 = new String[][] {

				{ null, null, null}, { null, null, null},
				{ null, null, null}, { null, null, null},
				{ null, null, null},

				{ null, null, null}, { null, null, null},
				{ null, null, null}, { null, null, null},
				{ null, null, null},

				{ null, null, null}, { null, null, null},
				{ null, null, null}, { null, null, null},
				{ null, null, null},

				{ null, null, null}, { null, null, null},
				{ null, null, null}, { null, null, null},
				{ null, null, null},

		};
		String[] headingS1 = new String[] { "ID Number", "Staff Name", "Responsibilities" };

		model.setDataVector(dataS1, headingS1);

		
		tableSuspension = new JTable() {
			public Component prepareRenderer(TableCellRenderer renderer, int row, int column) {
				Component returnComp = super.prepareRenderer(renderer, row, column);
				Color alternateColor = new Color(202, 202, 206);
				Color whiteColor = Color.decode("#7f8c8d");
				if (!returnComp.getBackground().equals(getSelectionBackground())) {
					Color bg = (row % 2 == 0 ? alternateColor : whiteColor);
					returnComp.setBackground(bg);
					bg = null;
				}
				return returnComp;
			}
		};
		
		tableSuspension.setModel(model);
		JScrollPane scrollerS1 = new JScrollPane(tableSuspension);
		tableSuspension.setRowHeight(25);
		tableSuspension.setAutoCreateRowSorter(true);
		tableSuspension.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
		scrollerS1.setPreferredSize(new Dimension(850, 400));
		Border bodaS1 = BorderFactory.createRaisedBevelBorder();
		scrollerS1.setBorder(bodaS1);

		panelforrecord.add(scrollerS1);

		this.addWindowListener(new WindowListener() {

			@Override
			public void windowOpened(WindowEvent arg0) {
				// TODO Auto-generated method stub

			}

			@Override
			public void windowIconified(WindowEvent arg0) {
				// TODO Auto-generated method stub

			}

			@Override
			public void windowDeiconified(WindowEvent arg0) {
				// TODO Auto-generated method stub

			}

			@Override
			public void windowDeactivated(WindowEvent arg0) {
				// TODO Auto-generated method stub

			}

			@Override
			public void windowClosing(WindowEvent arg0) {
				// TODO Auto-generated method stub

			}

			@Override
			public void windowClosed(WindowEvent arg0) {
				// TODO Auto-generated method stub

			}

			@Override
			public void windowActivated(WindowEvent arg0) {
				// TODO Auto-generated method stub


//				displayData(tableSuspension, "select staff_id,staff_name,responsibility from non_teaching_staff");
			
			}
		});

		add(panelforrecord);

		setVisible(true);
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
