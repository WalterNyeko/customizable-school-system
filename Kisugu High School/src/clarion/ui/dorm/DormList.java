package clarion.ui.dorm;

import java.awt.Dimension;
import java.sql.SQLException;
import java.util.List;

import javax.swing.DefaultListModel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import clarion.controller.dorm.DormController;
import clarion.model.dorm.Dorm;

public class DormList extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private JList<Object> list;

	private DormController controller;

	private JScrollPane scrollPaneDorm;

	private DormPanel dormPanel;

	private DormTableModel dormTableModel;

	private JTable tableDorm;

	private JScrollPane scrollPaneDormTable;

	private DefaultListModel<Object> model;

	public DormList(DormPanel theDormPanel, DormController theController) {
		this();
		dormPanel = theDormPanel;
		controller = theController;

	}

	public DormList(DormPanel theDormPanel) {
		this(theDormPanel, null);

	}

	public DormList() {

		setPreferredSize(new Dimension(160, 540));

		
		List<Object> theLists = null;

		model = new DefaultListModel<>();

		try {
			controller = new DormController();
			theLists = controller.fillDormTree();
		} catch (SQLException e) {
			e.printStackTrace();
		}

		theLists.toArray();

		model.addElement(theLists);

		list = new JList<>();
		list.setModel(model);

		tableDorm = new JTable();
		scrollPaneDormTable = new JScrollPane();
		scrollPaneDormTable.setViewportView(tableDorm);

		list.addListSelectionListener(new ListSelectionListener() {

			List<Dorm> dorms = null;

			@Override
			public void valueChanged(ListSelectionEvent e) {
				if (!e.getValueIsAdjusting()) {
					String ming = list.getSelectedValue().toString();
					// System.out.println(list.getSelectedValue().toString());

					try {
						dorms = controller.getAllDormStudents(list.getSelectedValue().toString());
					} catch (SQLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}

					dormTableModel = new DormTableModel(dorms);

					// dormPanel = new DormPanel();
					//
					// dormPanel.getTableDorm().setModel(dormTableModel);
					tableDorm.setModel(dormTableModel);

				}
			}
		});

		// scrollPaneDorm = new JScrollPane(list);
		// add(scrollPaneDorm);
		scrollPaneDormTable.setPreferredSize(new Dimension(160, 200));
		add(scrollPaneDormTable);

	}

	public void updateList() {
		List<Object> theLists = null;

		try {
			theLists = controller.fillDormTree();
		} catch (SQLException e) {
			e.printStackTrace();
		}

		theLists.toArray();

		model.addElement(theLists);

		list.setModel(model);

	}

	public JScrollPane getScrollPaneDorm() {
		return scrollPaneDorm;
	}

	public JList<Object> getList() {
		return list;
	}

	public JTable getTableDorm() {
		return tableDorm;
	}

	public JScrollPane getScrollPaneDormTable() {
		return scrollPaneDormTable;
	}

}
