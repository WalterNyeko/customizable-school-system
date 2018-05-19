package school.ui.finances;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import javax.swing.BorderFactory;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.border.Border;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;

import school.ui.jdbc.JavaDatabaseSelectStatements;

public class FinanceStockAtHandStores extends JDialog {

	JTable tableSuspension;
	protected DefaultTableModel modelDismissedStudents;

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		new FinanceStockAtHandStores();
	}

	public FinanceStockAtHandStores() {
		// TODO Auto-generated constructor stub

		setTitle("Stock Records");
		setSize(900, 450);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setLayout(new BorderLayout());
		// setLocation(200,100);
		setLocationRelativeTo(null);

		JPanel panelforrecord = new JPanel();
		panelforrecord.setLayout(new BorderLayout());

		String[][] dataS1 = new String[][] {

				{ null, null, null, null, null, null }, { null, null, null, null, null, null },
				{ null, null, null, null, null, null }, { null, null, null, null, null, null },
				{ null, null, null, null, null, null },

				{ null, null, null, null, null, null }, { null, null, null, null, null, null },
				{ null, null, null, null, null, null }, { null, null, null, null, null, null },
				{ null, null, null, null, null, null },

				{ null, null, null, null, null, null }, { null, null, null, null, null, null },
				{ null, null, null, null, null, null }, { null, null, null, null, null, null },
				{ null, null, null, null, null, null },

				{ null, null, null, null, null, null }, { null, null, null, null, null, null },
				{ null, null, null, null, null, null }, { null, null, null, null, null, null },
				{ null, null, null, null, null, null },

		};

		String[] headingS1 = new String[] { "Date", "Item Name", "Quantity Received", "Quantity Issued", "Stock At Hand",
				"Balance(Value)" };
		
		
		
		DefaultTableModel defaultTableModel=new DefaultTableModel();
		defaultTableModel.setDataVector(dataS1, headingS1);

		tableSuspension = new JTable(defaultTableModel) {
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
		JScrollPane scrollerS1 = new JScrollPane(tableSuspension);
		tableSuspension.setRowHeight(25);
		tableSuspension.setAutoCreateRowSorter(true);
		tableSuspension.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
		scrollerS1.setPreferredSize(new Dimension(850, 400));
		Border bodaS1 = BorderFactory.createRaisedBevelBorder();
		scrollerS1.setBorder(bodaS1);

		panelforrecord.add(scrollerS1, BorderLayout.CENTER);

	

		add(panelforrecord, BorderLayout.CENTER);
		setVisible(true);
	}

}
