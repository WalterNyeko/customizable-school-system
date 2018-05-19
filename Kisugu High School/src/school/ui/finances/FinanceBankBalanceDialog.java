package school.ui.finances;

import java.awt.Color;
import java.awt.Font;
import java.text.DecimalFormat;

import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;


public class FinanceBankBalanceDialog extends JDialog {

	public JLabel labelCashMM;
	public JLabel labelCashMMValue;
	public JLabel labelCashDD;
	public JLabel labelCashDDValue;
	public JLabel labelCashExpenses;
	public JLabel labelCashExpensesValue;
	public JPanel panelMain;
	public JLabel labelCashActualRevenue;
	public JLabel labelCashActualRevenueValue;

	public FinanceBankBalanceDialog() {
		
		setTitle("Sales Of The Day");
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setLocationRelativeTo(null);
		setSize(400, 150);
		
		java.awt.Dimension dim=new java.awt.Dimension(180, 25);
		
		labelCashMM=new JLabel("");
		labelCashMM.setPreferredSize(dim);
		labelCashMMValue=new JLabel("456");
		labelCashMMValue.setPreferredSize(dim);
		labelCashMMValue.setFont(new Font("Times New Roman", Font.BOLD, 18));
		labelCashMMValue.setForeground(Color.BLUE);
		labelCashDD=new JLabel("Balance in Other Banks:");
		labelCashDD.setPreferredSize(dim);
		labelCashDDValue=new JLabel("123");
		labelCashDDValue.setPreferredSize(dim);
		labelCashDDValue.setFont(new Font("Times New Roman", Font.BOLD, 18));
		labelCashDDValue.setForeground(Color.BLUE);
		labelCashExpenses=new JLabel("Total Bank Balnce:");
		labelCashExpenses.setPreferredSize(dim);
		labelCashExpensesValue=new JLabel("");
		labelCashExpensesValue.setFont(new Font("Times New Roman", Font.BOLD, 18));
		labelCashExpensesValue.setForeground(Color.BLUE);
		labelCashExpensesValue.setPreferredSize(dim);
		
		
		
		
		panelMain=new JPanel();
		add(panelMain);
		panelMain.add(labelCashMM);
		panelMain.add(labelCashMMValue);
		panelMain.add(labelCashDD);
		panelMain.add(labelCashDDValue);
		panelMain.add(labelCashExpenses);
		panelMain.add(labelCashExpensesValue);
		
		
		
		
		
		
		setVisible(true);
	}
	
	public void showDailyOfTheDay(JLabel label, String query) {
		
		java.sql.Connection con=null;
		java.sql.PreparedStatement stm=null;
		java.sql.ResultSet rs=null;
		
		try {
			con = CashBookController.getConnection();
			stm = con.prepareStatement(query);
			rs=stm.executeQuery();
			while(rs.next()) {
				DecimalFormat decimalformat = new DecimalFormat("#,###.00");

				label.setText("UGX "+decimalformat.format(rs.getDouble(1)));
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, "Request is not executed successfully");
		}
	}
	
	
public void showDailyOfTheDayActual(JLabel label, String query,String query1) {
		
		java.sql.Connection con=null;
		java.sql.PreparedStatement stm=null;
		java.sql.ResultSet rs=null;
		java.sql.PreparedStatement stm1=null;
		java.sql.ResultSet rs1=null;
		

		
		try {
			con = CashBookController.getConnection();
			stm = con.prepareStatement(query);
			rs=stm.executeQuery();
			stm1 = con.prepareStatement(query1);
			rs1=stm1.executeQuery();
			if(rs.next() ) {
				
				if(rs1.next()) {
					DecimalFormat decimalformat = new DecimalFormat("#,###.00");
					
					String result1=""+rs.getDouble(1);
					String result2=""+rs1.getDouble(1);
					
					Double res1=Double.parseDouble(result1);
					Double res2=Double.parseDouble(result2);
					
					double actual=res1-res2;

					label.setText("UGX "+decimalformat.format(actual));
				}
				
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, "Request is not executed successfully");
		}
	}
}
