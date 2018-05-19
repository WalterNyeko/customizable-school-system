package school.ui.student;
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

public class SuspensionRecords extends JDialog{

	JTable tableSuspension;
	protected DefaultTableModel modelSuspendedStudents;
	

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		new SuspensionRecords();
	}
	
	public SuspensionRecords() {
		// TODO Auto-generated constructor stub
		setTitle("Suspension Record");
		setSize(900, 450);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setLayout(new BorderLayout());
		//setLocation(200,100);
		setLocationRelativeTo(null);
		
		JPanel panelforrecord=new JPanel();
		panelforrecord.setLayout(new BorderLayout());

		
		String[][] dataS1=new String[][]
				{
				
					{null,null,null,null,null,null,null,null},
					{null,null,null,null,null,null,null,null},
					{"White","White",null,null,null,null,null,null},
					{"Color","Color",null,null,null,null,null,null},
					{null,null,null,null,null,null,null,null},

					{null,null,null,null,null,null,null,null},
					{null,null,null,null,null,null,null,null},
					{null,null,null,null,null,null,null,null},
					{null,null,null,null,null,null,null,null},
					{null,null,null,null,null,null,null,null},

					{null,null,null,null,null,null,null,null},
					{null,null,null,null,null,null,null,null},
					{null,null,null,null,null,null,null,null},
					{null,null,null,null,null,null,null,null},
					{null,null,null,null,null,null,null,null},

					{null,null,null,null,null,null,null,null},
					{null,null,null,null,null,null,null,null},
					{null,null,null,null,null,null,null,null},
					{null,null,null,null,null,null,null,null},
					{null,null,null,null,null,null,null,null},
					
					
				};
				
				String[] headingS1=new String[]{"Student Code","Student Name","Class","Sex","Reason","Duration","Date of Suspension","Date of Return"};
				
				tableSuspension=new JTable(dataS1,headingS1){
				    public Component prepareRenderer(TableCellRenderer renderer, int row, int column){
				        Component returnComp = super.prepareRenderer(renderer, row, column);
				        Color alternateColor = new Color(100,150,240);
				        Color whiteColor = Color.decode("#7f8c8d");
				        if (!returnComp.getBackground().equals(getSelectionBackground())){
				            Color bg = (row % 2 == 0 ? alternateColor : whiteColor);
				            returnComp .setBackground(bg);
				            bg = null;
				        }
				        return returnComp;
				}
				};
				JScrollPane scrollerS1= new JScrollPane(tableSuspension);
				tableSuspension.setRowHeight(25);
				scrollerS1.setPreferredSize(new Dimension(850,400));
				Border bodaS1=BorderFactory.createRaisedBevelBorder();
				scrollerS1.setBorder(bodaS1);
				tableSuspension.setForeground(Color.WHITE);
				
				panelforrecord.add(scrollerS1, BorderLayout.CENTER);		
		
			
				

				modelSuspendedStudents = new JavaDatabaseSelectStatements().DisplaySuspendedStudents();
				
				tableSuspension.setModel(modelSuspendedStudents);
		
				add(panelforrecord, BorderLayout.CENTER);
				
		setVisible(true);
	}

}
