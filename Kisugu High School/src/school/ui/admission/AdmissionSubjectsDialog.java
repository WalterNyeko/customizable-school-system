package school.ui.admission;

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;

import school.controller.academics.subjects.AcademicsSubjectSDAO;
import school.model.academics.subjects.AcademicsSubjects;

public class AdmissionSubjectsDialog extends JDialog {

	private JTable tableSubjects;
	private JScrollPane scrollPaneSubjects;
	private JButton button;

	private AcademicsSubjectSDAO dao;

	private CheckBoxSelectionTableModel checkBoxSelectionTableModel;

	public AdmissionSubjectsDialog() {
		// TODO Auto-generated constructor stub
		setUpSubjectsDialog();
	}

	private void setUpSubjectsDialog() {
		setTitle("Choose subjects for the students");
		setSize(500, 500);
		setLayout(new FlowLayout());
		setLocationRelativeTo(null);

		try {
			dao = new AcademicsSubjectSDAO();
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		List<AcademicsSubjects> academicsSubjects = null;

		try {
			academicsSubjects = dao.getAllSubjects();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		AdmissionSubjectsTableModel admissionSubjectsTableModel = new AdmissionSubjectsTableModel(academicsSubjects);

		tableSubjects = new JTable();
		tableSubjects.setModel(admissionSubjectsTableModel);
		scrollPaneSubjects = new JScrollPane();
		scrollPaneSubjects.setViewportView(tableSubjects);
		add(scrollPaneSubjects);

		tableSubjects.getModel().addTableModelListener(new TableModelListener() {

			@Override
			public void tableChanged(TableModelEvent e) {
				// TODO Auto-generated method stub
				for(int i=0;i<tableSubjects.getModel().getRowCount();i++)
                {
                  if ((Boolean) tableSubjects.getModel().getValueAt(i,1))
                  {  
                    System.out.println(">\t"+tableSubjects.getSelectedRow());
                    break;
                  }
               }     
			}
		});

		button = new JButton("button");
		add(button);
		button.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				// tableSubjects.getModel().getValueAt
			}
		});

		setVisible(true);
	}

	public static void main(String[] args) {
		new AdmissionSubjectsDialog();
	}

}
