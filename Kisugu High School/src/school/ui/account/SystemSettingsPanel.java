package school.ui.account;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.sql.ResultSet;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;
import javax.swing.table.JTableHeader;
import school.ui.finances.CashBookController;

@SuppressWarnings("serial")
public class SystemSettingsPanel extends JPanel {
	private JButton btnAdminSettings;
	private JLabel labelMaize, labelBeans, labelSugar, labelSalt, labelCookingOil, labelFloor, labelLabEvents,
			labelLibEvents, labelDormEvents, labelRights, labelSuspension, labelDismiss;
	private JTextField fieldMaize, fieldBeans, fieldSugar, fieldSalt, fieldCookingOil, fieldFloor, fieldLabEvents,
			fieldLibEvents, fieldDormEvents, fieldRights, fieldSuspension, fieldDismiss;
	private JPanel panelFoodStuffsAlert, panelEventsAlerts;

	private JTable tableSettings;
	SystemSettings systemSettings;
	private JButton btnSave;

	public SystemSettingsPanel() {
		

		setSize(1020, 470);
		
		setBorder(new LineBorder(Color.blue, 3));
		setPreferredSize(new Dimension(1160, 480));
		setBackground(Color.decode("#5f9ea0"));

		Dimension dimLabels = new Dimension(100, 30);
		Dimension dimLabelsEvents = new Dimension(130, 30);
		Dimension dimFields = new Dimension(150, 30);
		Dimension dimFieldsEvents = new Dimension(285, 30);

		panelFoodStuffsAlert = new JPanel();
		panelFoodStuffsAlert.setPreferredSize(new Dimension(550, 220));
		add(panelFoodStuffsAlert);
		panelFoodStuffsAlert.setBorder(new LineBorder(Color.white, 3));

		panelEventsAlerts = new JPanel();
		panelEventsAlerts.setPreferredSize(new Dimension(450, 220));
		add(panelEventsAlerts);
		panelEventsAlerts.setBorder(new LineBorder(Color.white, 3));

		JLabel labelheading = new JLabel("Stores Thresholds");
		labelheading.setFont(new Font("Times New Roman", Font.BOLD, 18));

		JPanel panelHodingLabel = new JPanel();
		panelHodingLabel.setPreferredSize(new Dimension(500, 30));
		panelFoodStuffsAlert.add(panelHodingLabel);
		panelHodingLabel.add(labelheading);

		labelMaize = new JLabel("Maize(kg):");
		labelMaize.setPreferredSize(dimLabels);
		panelFoodStuffsAlert.add(labelMaize);

		fieldMaize = new JTextField("60");
		fieldMaize.setEditable(false);
		fieldMaize.addFocusListener(new FocusListener() {

			@Override
			public void focusLost(FocusEvent arg0) {
				// TODO Auto-generated method stub
				fieldMaize.setEditable(false);
			}

			@Override
			public void focusGained(FocusEvent arg0) {
				// TODO Auto-generated method stub
				fieldMaize.setEditable(true);
			}
		});
		fieldMaize.setOpaque(true);
		fieldMaize.setBorder(null);
		fieldMaize.setAlignmentX(JTextField.CENTER_ALIGNMENT);
		fieldMaize.setPreferredSize(dimFields);
		panelFoodStuffsAlert.add(fieldMaize);

		Timer timer = new Timer();
		TimerTask tt = new TimerTask() {

			@Override
			public void run() {
				// TODO Auto-generated method stub

				String sql = "select maize from foodstuffs";
				java.sql.Connection conn = null;

				java.sql.PreparedStatement pst = null;

				ResultSet rs = null;

				try {

					conn = CashBookController.getConnection();
					pst = conn.prepareStatement(sql);

					rs = pst.executeQuery();

					while (rs.next()) {
						fieldMaize.setText(rs.getString(1));

					}

				} catch (Exception e1) {
					e1.printStackTrace();

				} finally {
					if (conn != null) {
						try {
							pst.close();
							rs.close();
							conn.close();
						} catch (Exception e2) {

						}
					}
				}

			}

		};

		//timer.schedule(tt, 2000, 2000);
		

		labelBeans = new JLabel("Beans(kg):");
		labelBeans.setPreferredSize(dimLabels);
		panelFoodStuffsAlert.add(labelBeans);

		fieldBeans = new JTextField("40");
		fieldBeans.setEditable(false);
		fieldBeans.setOpaque(true);
		fieldBeans.setBorder(null);
		fieldBeans.setPreferredSize(dimFields);
		panelFoodStuffsAlert.add(fieldBeans);

		labelSugar = new JLabel("Sugar(kg):");
		labelSugar.setPreferredSize(dimLabels);
		panelFoodStuffsAlert.add(labelSugar);

		fieldSugar = new JTextField("80");
		fieldSugar.setEditable(false);
		fieldSugar.setOpaque(true);
		fieldSugar.setBorder(null);
		fieldSugar.setPreferredSize(dimFields);
		panelFoodStuffsAlert.add(fieldSugar);

		labelSalt = new JLabel("Salt(kg):");
		labelSalt.setPreferredSize(dimLabels);
		panelFoodStuffsAlert.add(labelSalt);

		fieldSalt = new JTextField("25");
		fieldSalt.setEditable(false);
		fieldSalt.setOpaque(true);
		fieldSalt.setBorder(null);
		fieldSalt.setPreferredSize(dimFields);
		panelFoodStuffsAlert.add(fieldSalt);

		labelCookingOil = new JLabel("CookingOil(Ltrs):");
		labelCookingOil.setPreferredSize(dimLabels);
		panelFoodStuffsAlert.add(labelCookingOil);

		fieldCookingOil = new JTextField("60");
		fieldCookingOil.setEditable(false);
		fieldCookingOil.setOpaque(true);
		fieldCookingOil.setBorder(null);
		fieldCookingOil.setPreferredSize(dimFields);
		panelFoodStuffsAlert.add(fieldCookingOil);

		labelFloor = new JLabel("Floor(kg):");
		labelFloor.setPreferredSize(dimLabels);
		panelFoodStuffsAlert.add(labelFloor);

		fieldFloor = new JTextField("55");
		fieldFloor.setEditable(false);
		fieldFloor.setOpaque(true);
		fieldFloor.setBorder(null);
		fieldFloor.setPreferredSize(dimFields);
		panelFoodStuffsAlert.add(fieldFloor);
		
		
		btnSave = new JButton("Save Changes");
		btnSave.setFont(new Font("Times New Roman", Font.BOLD, 16));
		panelFoodStuffsAlert.add(btnSave);
		

		labelLabEvents = new JLabel("Laboratory Events:");
		labelLabEvents.setPreferredSize(dimLabelsEvents);
		panelEventsAlerts.add(labelLabEvents);

		fieldLabEvents = new JTextField("S4 Students Stole Thermometer ");
		fieldLabEvents.setEditable(false);
		fieldLabEvents.setOpaque(true);
		fieldLabEvents.setBorder(null);
		fieldLabEvents.setPreferredSize(dimFieldsEvents);
		panelEventsAlerts.add(fieldLabEvents);

		labelLibEvents = new JLabel("Library Events:");
		labelLibEvents.setPreferredSize(dimLabelsEvents);
		panelEventsAlerts.add(labelLibEvents);

		fieldLibEvents = new JTextField("S4 Students Stole Books ");
		fieldLibEvents.setEditable(false);
		fieldLibEvents.setOpaque(true);
		fieldLibEvents.setBorder(null);
		fieldLibEvents.setPreferredSize(dimFieldsEvents);
		panelEventsAlerts.add(fieldLibEvents);

		labelDormEvents = new JLabel("Dormitory Events:");
		labelDormEvents.setPreferredSize(dimLabelsEvents);
		panelEventsAlerts.add(labelDormEvents);

		fieldDormEvents = new JTextField("S4 Students Stole Bed ");
		fieldDormEvents.setEditable(false);
		fieldDormEvents.setOpaque(true);
		fieldDormEvents.setBorder(null);
		fieldDormEvents.setPreferredSize(dimFieldsEvents);
		panelEventsAlerts.add(fieldDormEvents);

		labelRights = new JLabel("Rights Request Events:");
		labelRights.setPreferredSize(dimLabelsEvents);
		panelEventsAlerts.add(labelRights);

		fieldRights = new JTextField("User Requested For Rights");
		fieldRights.setEditable(false);
		fieldRights.setOpaque(true);
		fieldRights.setBorder(null);
		fieldRights.setPreferredSize(dimFieldsEvents);
		panelEventsAlerts.add(fieldRights);

		labelSuspension = new JLabel("Suspension Events:");
		labelSuspension.setPreferredSize(dimLabelsEvents);
		panelEventsAlerts.add(labelSuspension);

		fieldSuspension = new JTextField("Student Suspended");
		fieldSuspension.setEditable(false);
		fieldSuspension.setOpaque(true);
		fieldSuspension.setBorder(null);
		fieldSuspension.setPreferredSize(dimFieldsEvents);
		panelEventsAlerts.add(fieldSuspension);

		labelDismiss = new JLabel("Dismissal Events:");
		labelDismiss.setPreferredSize(dimLabelsEvents);
		panelEventsAlerts.add(labelDismiss);

		fieldDismiss = new JTextField("Student Dismissed");
		fieldDismiss.setEditable(false);
		fieldDismiss.setOpaque(true);
		fieldDismiss.setBorder(null);
		fieldDismiss.setPreferredSize(dimFieldsEvents);
		panelEventsAlerts.add(fieldDismiss);

		btnAdminSettings = new JButton("Application Settings");
		btnAdminSettings.setFont(new Font("Times New Roman", Font.BOLD, 16));
		btnAdminSettings.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				systemSettings = new SystemSettings();

			}
		});

		add(btnAdminSettings);

		String[][] dataS2 = new String[][] {

				{ null, null, null, null, null, null, null, null }, { null, null, null, null, null, null, null, null },
				{ null, null, null, null, null, null, null, null }, { null, null, null, null, null, null, null, null },
				{ null, null, null, null, null, null, null, null }, { null, null, null, null, null, null, null, null },
				{ null, null, null, null, null, null, null, null }, { null, null, null, null, null, null, null, null },
				{ null, null, null, null, null, null, null, null }, { null, null, null, null, null, null, null, null },
				{ null, null, null, null, null, null, null, null }, { null, null, null, null, null, null, null, null },
				{ null, null, null, null, null, null, null, null }, { null, null, null, null, null, null, null, null },
				{ null, null, null, null, null, null, null, null }, { null, null, null, null, null, null, null, null },
				{ null, null, null, null, null, null, null, null }, { null, null, null, null, null, null, null, null },
				{ null, null, null, null, null, null, null, null }, { null, null, null, null, null, null, null, null },
				{ null, null, null, null, null, null, null, null }, { null, null, null, null, null, null, null, null },
				{ null, null, null, null, null, null, null, null }, { null, null, null, null, null, null, null, null },
				{ null, null, null, null, null, null, null, null }, { null, null, null, null, null, null, null, null },
				{ null, null, null, null, null, null, null, null }, { null, null, null, null, null, null, null, null },
				{ null, null, null, null, null, null, null, null }

		};

		String[] headingS2 = new String[] { "Record Time", "Event Name", "Value", "Location", "Reported By",
				"Student ID Number", "Student Name", "Class" };

		tableSettings = new JTable(dataS2, headingS2);
		tableSettings.setShowGrid(false);
		JTableHeader headerUNSAItems = tableSettings.getTableHeader();
//		headerUNSAItems.setBackground(Color.BLACK);
		headerUNSAItems.setForeground(Color.white);
		headerUNSAItems.setPreferredSize(new Dimension(1170, 30));
		JScrollPane scrollerSettings = new JScrollPane(tableSettings);
		tableSettings.setRowHeight(25);
		scrollerSettings.setPreferredSize(new Dimension(1005, 205));
		Border bodaS2 = BorderFactory.createRaisedBevelBorder();
		scrollerSettings.setBorder(bodaS2);
		tableSettings.setShowGrid(false);
		tableSettings.getColumnModel().getColumn(0).setPreferredWidth(100);
		tableSettings.getColumnModel().getColumn(1).setPreferredWidth(100);
		tableSettings.getColumnModel().getColumn(2).setPreferredWidth(85);
		tableSettings.getColumnModel().getColumn(3).setPreferredWidth(100);
		tableSettings.getColumnModel().getColumn(4).setPreferredWidth(225);
		tableSettings.getColumnModel().getColumn(5).setPreferredWidth(85);
		tableSettings.getColumnModel().getColumn(6).setPreferredWidth(225);
		tableSettings.getColumnModel().getColumn(7).setPreferredWidth(80);

		add(scrollerSettings);

		setVisible(true);

	}

}
