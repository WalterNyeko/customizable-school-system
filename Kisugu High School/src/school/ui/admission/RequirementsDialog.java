package school.ui.admission;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Vector;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.border.Border;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;

import school.ui.finances.CashBookController;

public class RequirementsDialog extends JDialog {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public JTable tableforrequirements;
	JScrollPane scrollerforrequirements;
	JDialog frame;
	protected DefaultTableModel modelTableAvailableRequirements;
	public JLabel labelClassNumber;
	public JLabel labelTerm;
	private String id;
	private String term;
	private String classes;
	private String name;
	private String standardquantity;
	private String brought;
	public JLabel labelPaymentCode;
	static final int ZERO = 0;
	static final int ONE = 1;

	public RequirementsDialog() {

		CustomizedTable();
	}

	protected void CustomizedTable() {

		final DefaultTableModel model = new DefaultTableModel() {

			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			public Class<?> getColumnClass(int index) {
				switch (index) {
				case 0:
					return String.class;
				case 1:
					return Integer.class;
				case 2:
					return Integer.class;

				}
				return super.getColumnClass(index);
			}

			public String getColumnName(int index) {
				switch (index) {
				case 0:
					return "Requirement Name";
				case 1:
					return "Expected Quantity";
				case 2:
					return "Quantity Brought";

				}
				return super.getColumnName(index);
			}

			public Object getValueAt(int row, int col) {

				return super.getValueAt(row, col);

			}

			public void setValueAt(Object value, int row, int col) {

				super.setValueAt(value, row, col);
			}

			public boolean isCellEditable(int row, int col) {
				return true;
			}

			public int getColumnCount() {
				return 3;
			}

		};

		tableforrequirements = new JTable(model);
		JTableHeader headerCol = tableforrequirements.getTableHeader();
		headerCol.setPreferredSize(new Dimension(50, 30));

		scrollerforrequirements = new JScrollPane(tableforrequirements);
		tableforrequirements.setRowHeight(25);
		scrollerforrequirements.setPreferredSize(new Dimension(870, 410));
		Border bodaforrequirements = BorderFactory.createRaisedBevelBorder();
		scrollerforrequirements.setBorder(bodaforrequirements);
		tableforrequirements.setFont(new Font("Times New Roman", Font.BOLD, 16));
		tableforrequirements.setForeground(Color.white);

		DefaultTableCellRenderer renderer = new DefaultTableCellRenderer();
		renderer.setHorizontalAlignment(SwingConstants.CENTER);
		tableforrequirements.getColumnModel().getColumn(0).setCellRenderer(renderer);
		tableforrequirements.getColumnModel().getColumn(1).setCellRenderer(renderer);
		tableforrequirements.getColumnModel().getColumn(2).setCellRenderer(renderer);

		tableforrequirements.addMouseListener(new MouseAdapter() {

			@Override
			public void mouseClicked(MouseEvent e) {

				if (e.getClickCount() == 2) {
					model.addRow(new Vector<Object>());
				}

			}
		});
		model.addRow(new Vector<Object>());
		model.addRow(new Vector<Object>());
		model.addRow(new Vector<Object>());
		model.addRow(new Vector<Object>());

		frame = new JDialog(this, RequirementsDialog.class.getSimpleName());
		frame.setSize(870, 500);
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.setLocation(455, 200);
		frame.setResizable(false);

		JPanel panelHolder = new JPanel();
		frame.getContentPane().add(panelHolder);
		panelHolder.add(scrollerforrequirements);
		
		labelPaymentCode=new JLabel();
		panelHolder.add(labelPaymentCode);
		
		
		labelClassNumber=new JLabel();
		panelHolder.add(labelClassNumber);
		

		labelTerm=new JLabel();
		panelHolder.add(labelTerm);

		JButton btnSaveRequirements = new JButton("Save Requirements");
		btnSaveRequirements.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				
				
				
				SaveStudentRequirement("INSERT INTO requirements_student(id_number,student_class,term,"
				+ "requirement_name,standard_quantity,quantity_brought,payment_code) values(?,?,?,?,?,?,?)");
				
			}
		});
		panelHolder.add(btnSaveRequirements);

		JButton btnExitRequirements = new JButton("Exit Requirements");
		btnExitRequirements.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				setVisible(false);
			}
		});
		panelHolder.add(btnExitRequirements);

		frame.setTitle("Student's Requirements");

		frame.setVisible(true);

	}

	
	

	public JDialog getFrame() {
		return frame;
	}

	public void setFrame(JDialog frame) {
		this.frame = frame;
	}

	public JScrollPane getScrollerforrequirements() {
		return scrollerforrequirements;
	}

	public void setScrollerforrequirements(JScrollPane scrollerforrequirements) {
		this.scrollerforrequirements = scrollerforrequirements;
	}

	public void SaveStudentRequirement(String querries) {

		try {

			java.sql.Connection conn = null;
			java.sql.PreparedStatement pst = null;
			int rows=tableforrequirements.getRowCount();
			conn = CashBookController.getConnection();
			pst = conn.prepareStatement(querries);
			for (int row = 0; row < rows; row++) {
				 id = labelClassNumber.getText();
				 term = labelTerm.getText();
				 classes = (String) tableforrequirements.getValueAt(row, 0);
				 name = (String) tableforrequirements.getValueAt(row, 1);
				 standardquantity = (String) tableforrequirements.getValueAt(row, 2);
				 brought = (String) tableforrequirements.getValueAt(row, 3);
				 String paymentcode=labelPaymentCode.getText();

				pst.setString(1, id);
				pst.setString(2, classes);
				pst.setString(3, term);
				pst.setString(4, name);
				pst.setString(5, standardquantity);
				pst.setString(6, brought);
				pst.setString(7, paymentcode);
				
				
				pst.addBatch();
			}
			pst.executeBatch();

			JOptionPane.showMessageDialog(null, "Requirement Saved Successfully");

		} catch (Exception ex) {
			ex.printStackTrace();
			JOptionPane.showMessageDialog(null, "Request Not Saved Successfully " + ex.getMessage());

		}
	}
	
	
	
	
	public static void main(String[] args) {

		new RequirementsDialog();
	}

}
