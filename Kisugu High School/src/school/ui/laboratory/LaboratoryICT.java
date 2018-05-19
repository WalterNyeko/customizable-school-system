package school.ui.laboratory;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Date;

import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;

import school.ui.finances.CashBookController;

public class LaboratoryICT extends JPanel {

	private JButton btnAddItem;
	private JButton btnAddEditItem;
	private JButton btnNewEvents;
	private JButton btnEditEvents;
	private JButton btnItems;
	private JButton btnEvents;

	public JTable tableItems;
	public JTable tableEvents;

	private JScrollPane scrollPaneItems;
	private JScrollPane scrollPaneEvents;

	private JButton btnBack;
	private JButton btnPrint;

	LaboratoryICTRecordEvent laboratoryICTRecordEvent;
	private JPanel panelTopHolder;
	protected LaboratorySwitcherPanel laboratorySwitcherPanel;
	protected LaboratoryICT laboratoryICT;
	protected LaboratorySciencePractical laboratorySciencePractical;

	public LaboratoryICT() {
		buildGUI();
	}

	private void buildGUI() {

		panelTopHolder = new JPanel();
		panelTopHolder.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		panelTopHolder.setBorder(new LineBorder(Color.blue, 3));
		panelTopHolder.setPreferredSize(new Dimension(1160, 480));
		add(panelTopHolder);
		// TODO Auto-generated method stub
		btnAddItem = new JButton("Add New Item");
		btnAddItem.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				LaboratoryICTAddNewItem laboratoryICTAddNewItem = new LaboratoryICTAddNewItem();
				// laboratoryICTAddNewItem.setVisible(true);
			}
		});
		panelTopHolder.add(btnAddItem);

		btnAddEditItem = new JButton("Edit Selected Item");
		btnAddEditItem.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub

				int row = tableItems.getSelectedRow();

				if (row < 0) {
					JOptionPane.showMessageDialog(LaboratoryICT.this, "You must select an item from Items Table",
							"Error", JOptionPane.ERROR_MESSAGE);
					return;
				} else {
					LaboratoryICTAddNewItem laboratoryICTAddNewItem = new LaboratoryICTAddNewItem();
					int rows=tableItems.getSelectedRow();
					String name=tableItems.getValueAt(rows, 0).toString();
					String type=tableItems.getValueAt(rows, 1).toString();
					Date date=(Date) tableItems.getValueAt(rows, 3);
					String desc=tableItems.getValueAt(rows, 2).toString();
					String receiver=tableItems.getValueAt(rows, 5).toString();
					String donor=tableItems.getValueAt(rows, 4).toString();
					laboratoryICTAddNewItem.fieldItemName.setText(name);
					laboratoryICTAddNewItem.fieldType.setText(type);
					laboratoryICTAddNewItem.areaDescription.setText(desc);
					laboratoryICTAddNewItem.fieldRecievedBy.setText(receiver);
					laboratoryICTAddNewItem.fieldDonor.setText(donor);
					laboratoryICTAddNewItem.dateChooserDateIn.setDate(date);
					laboratoryICTAddNewItem.setTitle("Edit Selected Item");
					laboratoryICTAddNewItem.getBtnAdd().setPreferredSize(new Dimension(120, 30));
					laboratoryICTAddNewItem.getBtnAdd().setText("Save Changes");

				}

			}
		});
		panelTopHolder.add(btnAddEditItem);

		btnNewEvents = new JButton("Record Event");
		btnNewEvents.addActionListener(new ActionListener() {

			@SuppressWarnings("unchecked")
			@Override
			public void actionPerformed(ActionEvent arg0) {
				laboratoryICTRecordEvent = new LaboratoryICTRecordEvent();
				laboratoryICTRecordEvent.setTitle("Record Event");

				laboratoryICTRecordEvent.fieldItemName.removeAllItems();
				laboratoryICTRecordEvent.fieldType.removeAllItems();
				laboratoryICTRecordEvent.fieldClass.removeAllItems();
				laboratoryICTRecordEvent.displayInComboBox(laboratoryICTRecordEvent.fieldItemName,
						"select item_name from laboratory_ict_items");
				laboratoryICTRecordEvent.displayInComboBox(laboratoryICTRecordEvent.fieldType,
						"select item_type from laboratory_ict_items");
				laboratoryICTRecordEvent.displayInComboBox(laboratoryICTRecordEvent.fieldClass,
						"select class_name from student_classes");

			}
		});
		panelTopHolder.add(btnNewEvents);

		btnEditEvents = new JButton("Edit Selected Events");
		btnEditEvents.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				int row = tableEvents.getSelectedRow();
				if (row < 0) {
					JOptionPane.showMessageDialog(LaboratoryICT.this, "You must select an event from Events Table",
							"Error", JOptionPane.ERROR_MESSAGE);
					return;
				} else {
					laboratoryICTRecordEvent = new LaboratoryICTRecordEvent();
					int rows=tableEvents.getSelectedRow();
					String name=tableEvents.getValueAt(rows, 0).toString();
					String type=tableEvents.getValueAt(rows, 1).toString();
					Date date=(Date) tableEvents.getValueAt(rows, 2);
					String desc=tableEvents.getValueAt(rows, 3).toString();
					String person=tableEvents.getValueAt(rows, 4).toString();
					String code=tableEvents.getValueAt(rows, 5).toString();
					laboratoryICTRecordEvent.fieldItemName.setSelectedItem(name);
					laboratoryICTRecordEvent.fieldType.setSelectedItem(type);
					laboratoryICTRecordEvent.fieldIDNo.setText(code);
					laboratoryICTRecordEvent.fieldResponsiblePerson.setText(person);
					laboratoryICTRecordEvent.areaDescription.setText(desc);
					laboratoryICTRecordEvent.chooser.setDate(date);
					laboratoryICTRecordEvent.setTitle("Edit Selected Event");
					laboratoryICTRecordEvent.getBtnAddEvent().setPreferredSize(new Dimension(120, 30));
					laboratoryICTRecordEvent.getBtnAddEvent().setText("Save Changes");
					
					

				}
			}
		});
		panelTopHolder.add(btnEditEvents);

		btnItems = new JButton("Show Items");

		panelTopHolder.add(btnItems);

		btnEvents = new JButton("Show Events");
		panelTopHolder.add(btnEvents);

		String[] headItems = { "Item Name", "Type", "Description", "Date In", "Donor", "Recieved By" };

		String[] headEvents = { "Item Name", "Item Type", "Event Date", "Event Description", "Responsible Person",
				"Student Code" };

		Object[][] dataItems = {

				{ null, null, null, null, null, null }, { null, null, null, null, null, null },
				{ null, null, null, null, null, null }, { null, null, null, null, null, null },
				{ null, null, null, null, null, null }, { null, null, null, null, null, null },
				{ null, null, null, null, null, null }, { null, null, null, null, null, null },
				{ null, null, null, null, null, null }, { null, null, null, null, null, null },
				{ null, null, null, null, null, null }, { null, null, null, null, null, null },
				{ null, null, null, null, null, null }, { null, null, null, null, null, null },
				{ null, null, null, null, null, null }, { null, null, null, null, null, null }

		};

		Object[][] dataEvents = {

				{ null, null, null, null, null, null}, { null, null, null, null, null, null},
				{ null, null, null, null, null, null}, { null, null, null, null, null, null},
				{ null, null, null, null, null, null}, { null, null, null, null, null, null},
				{ null, null, null, null, null, null}, { null, null, null, null, null, null},
				{ null, null, null, null, null, null}, { null, null, null, null, null, null},
				{ null, null, null, null, null, null}, { null, null, null, null, null, null},
				{ null, null, null, null, null, null}, { null, null, null, null, null, null},
				{ null, null, null, null, null, null}, { null, null, null, null, null, null},
				{ null, null, null, null, null, null}, { null, null, null, null, null, null}

		};

		DefaultTableModel defaultTableModelItems = new DefaultTableModel();
		defaultTableModelItems.setDataVector(dataItems, headItems);

		tableItems = new JTable(defaultTableModelItems);
		tableItems.setShowGrid(false);
		JTableHeader headerUNSA = tableItems.getTableHeader();
		headerUNSA.setForeground(Color.white);
		headerUNSA.setPreferredSize(new Dimension(1140, 30));
		tableItems.setRowHeight(30);
		scrollPaneItems = new JScrollPane(tableItems);
		panelTopHolder.add(scrollPaneItems);

		DefaultTableModel defaultTableModelEvents = new DefaultTableModel();
		defaultTableModelEvents.setDataVector(dataEvents, headEvents);

		tableEvents = new JTable(defaultTableModelEvents);
		tableEvents.setShowGrid(false);
		JTableHeader headerUNSAEvents = tableEvents.getTableHeader();
		headerUNSAEvents.setForeground(Color.white);
		headerUNSAEvents.setPreferredSize(new Dimension(1140, 30));
		tableEvents.setRowHeight(30);
		scrollPaneEvents = new JScrollPane(tableEvents);
		scrollPaneEvents.setVisible(false);
		panelTopHolder.add(scrollPaneEvents);

		btnBack = new JButton("Back To Science Lab");
		btnBack.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub

				laboratorySwitcherPanel = new LaboratorySwitcherPanel();
				laboratoryICT = new LaboratoryICT();
				laboratorySciencePractical = new LaboratorySciencePractical();

				laboratoryICT.setVisible(false);
				laboratorySciencePractical.setVisible(true);
				laboratorySwitcherPanel.setVisible(false);
			}
		});
		panelTopHolder.add(btnBack);

		btnPrint = new JButton("Export To Excel");
		panelTopHolder.add(btnPrint);

		btnItems.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				scrollPaneItems.setVisible(true);
				scrollPaneEvents.setVisible(false);
			}
		});

		btnEvents.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				scrollPaneEvents.setVisible(true);
				scrollPaneItems.setVisible(false);
				displayData(tableEvents,
						"select item_name,item_type,event_date,item_description,responsible_person,student_code,student_class from laboratory_ict_events");
			}
		});

		panelTopHolder.setBackground(Color.decode("#5f9ea0"));

		displayData(tableItems, "select item_name,item_type,item_description,"
				+ "item_date_in,item_donor,item_receiver from laboratory_ict_items");
		displayData(tableEvents,
				"select item_name,item_type,event_date,item_description,responsible_person,student_code from laboratory_ict_events");

	}

	public JTable getTableItems() {
		return tableItems;
	}

	public JTable getTableEvents() {
		return tableEvents;
	}

	public JScrollPane getScrollPaneItems() {
		return scrollPaneItems;
	}

	public JScrollPane getScrollPaneEvents() {
		return scrollPaneEvents;
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
