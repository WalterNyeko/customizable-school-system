package school.ui.laboratory;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableModel;

import school.ui.finances.CashBookController;

public class LaboratorySwitcherPanel extends JPanel {

	private JButton buttonLabICT;
	private JButton buttonLabSciencePractical;
	private LaboratoryICT laboratoryICT;

	public LaboratorySwitcherPanel() {
		switchLabs();
	}

	private void switchLabs() {

		setSize(new Dimension(1150, 20));
		setBorder(new LineBorder(Color.blue, 3));
		setPreferredSize(new Dimension(1160, 480));
		setBackground(Color.decode("#5f9ea0"));

		buttonLabICT = new JButton("ICT Laboratory");
		buttonLabICT.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub


			}
		});
		add(buttonLabICT);

		buttonLabSciencePractical = new JButton("Science Practical Lab");
		add(buttonLabSciencePractical);
		
		 laboratoryICT=new LaboratoryICT();
		
		

	}

	public JButton getButtonLabICT() {
		return buttonLabICT;
	}

	public JButton getButtonLabSciencePractical() {
		return buttonLabSciencePractical;
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

			e.printStackTrace();
		}

	}

}
