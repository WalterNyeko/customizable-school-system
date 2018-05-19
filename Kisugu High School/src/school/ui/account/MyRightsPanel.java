package school.ui.account;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.table.DefaultTableModel;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.embed.swing.JFXPanel;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.HBox;
import school.ui.finances.CashBookController;

public class MyRightsPanel extends JPanel {

	private JTabbedPane tabEvents;
	private JPanel panelEvents, panelAdminConfig, panelNotifications, subjects, departments, allowedtabs, allowedroles;
	private JScrollPane scrollPaneEvents, scrollPaneAdmin, scrollPaneNotification, scrollPaneNamings, scrollPaneConfig;
	JPanel panelNamings;

	public JButton btnSave;
	private JFXPanel jfxPanel;
	private ObservableList<Object> data;
	private Connection conn;
	private PreparedStatement pst;
	public ComboBox combo;
	private JCheckBox cboxMyAccount;
	private JCheckBox cboxLab;
	private JCheckBox cboxAssets;
	private JCheckBox cboxDormitory;
	private JComponent cboxHome;
	private JCheckBox cboxNotice;
	private JCheckBox cboxAttendance;
	private JCheckBox checkboxAllroles;
	private JCheckBox cboxMyAccount1;
	private JCheckBox cboxAddUsers;
	private JCheckBox cboxAssets1;
	private JCheckBox cboxDormitory1;
	private JCheckBox cboxHome1;
	private JCheckBox cboxNotice1;
	private JCheckBox cboxAttendance1;
	private JCheckBox checkboxAllroles1;
	private JCheckBox cboxMyAccount11;
	private JComponent cboxAddUsers1;
	private JCheckBox cboxAssets11;
	private JCheckBox cboxDormitory11;
	private JCheckBox cboxHome11;
	private JCheckBox cboxNotice11;
	private JCheckBox cboxAttendance11;
	private JCheckBox checkboxAllroles11;
	private JCheckBox cboxMyAccountAdminRoles;
	private JCheckBox cboxLabAdminRoles;
	private JCheckBox cboxAssetsAdminRoles;
	private JCheckBox cboxDormitoryAdminRoles;
	private JCheckBox cboxHomeAdminRoles;
	private JCheckBox cboxNoticeAdminRoles;
	private JCheckBox cboxAttendanceAdminRoles;
	private JCheckBox cboxDispensary;
	private JCheckBox ckboxAll;
	private JCheckBox cboxAdmission;
	private JCheckBox cboxStudents;
	private JCheckBox cboxStaffs;
	private JCheckBox cboxAcademics;
	private JCheckBox cboxLibrary;
	private JCheckBox cboxFinance;
	private JCheckBox cboxTimeTable;
	private JCheckBox cboxAdmin;
	private int Account;
	private int Lab;
	private int Dispensary;
	public JTable table;
	private JButton btnSaveNewActivity;
	public JTable tableSystemRoles;
	public JTable tableAdminRoles;
	private JButton btnSaveNewAdminRoles;
	private Connection con;
	private ResultSet rs;

	public MyRightsPanel() {

		setBorder(new LineBorder(Color.blue, 3));
		setPreferredSize(new Dimension(1160, 480));
		setBackground(Color.decode("#5f9ea0"));

		jfxPanel = new JFXPanel();

		data = FXCollections.observableArrayList();
		combo = new ComboBox(data);
		combo.setPromptText("Choose Users To View/Edit Their Rights");

		HBox box = new HBox(10);
		box.getChildren().addAll(combo);
		Scene scene = new Scene(box);
		jfxPanel.setScene(scene);

		JPanel panelHodingLabel = new JPanel();
		panelHodingLabel.setPreferredSize(new Dimension(870, 40));
		add(panelHodingLabel);
		panelHodingLabel.add(jfxPanel);

		subjects = new JPanel();
		subjects.setPreferredSize(new Dimension(325, 380));

		/********************************************************
		 * 
		 * 
		 * 
		 * Allowed Subjects in a JTable
		 * 
		 * 
		 * 
		 *******************************************************/

		MyTableModel model = new MyTableModel();
		model.addRow(new Object[] { "English", false });
		model.addRow(new Object[] { "Mathematics", false });
		model.addRow(new Object[] { "History", false });
		model.addRow(new Object[] { "Agriculture", false });
		table = new JTable(model);
		table.getColumnModel().getColumn(0).setPreferredWidth(180);
		JScrollPane scrollSubjects = new JScrollPane(table);
		scrollSubjects.setPreferredSize(new Dimension(300, 350));
		subjects.setBorder(new TitledBorder("Allowed Subjects"));
		add(subjects);
		subjects.add(scrollSubjects);

		/********************************************************
		 * 
		 * 
		 * 
		 * Allowed System Roles
		 * 
		 * 
		 * 
		 *******************************************************/

		departments = new JPanel();
		departments.setPreferredSize(new Dimension(325, 380));

		MyTableModel modelSystemRoles = new MyTableModel();
		modelSystemRoles.addRow(new Object[] { "English", false });
		modelSystemRoles.addRow(new Object[] { "Mathematics", false });
		modelSystemRoles.addRow(new Object[] { "History", false });
		modelSystemRoles.addRow(new Object[] { "Agriculture", false });
		tableSystemRoles = new JTable(modelSystemRoles);
		tableSystemRoles.getColumnModel().getColumn(0).setPreferredWidth(180);
		JScrollPane scrollDepts = new JScrollPane(tableSystemRoles);
		scrollDepts.setPreferredSize(new Dimension(300, 350));
		departments.setBorder(new TitledBorder("Allowed System Roles"));
		departments.add(scrollDepts);
		add(departments);

		/********************************************************
		 * 
		 * 
		 * 
		 * Allowed Administrative Roles u0069/750
		 * 
		 * 
		 *******************************************************/

		panelAdminConfig = new JPanel();
		panelAdminConfig.setPreferredSize(new Dimension(325, 380));

		MyTableModel modelAdminRoles = new MyTableModel();
		modelAdminRoles.addRow(new Object[] { "English", false });
		modelAdminRoles.addRow(new Object[] { "Mathematics", false });
		modelAdminRoles.addRow(new Object[] { "History", false });
		modelAdminRoles.addRow(new Object[] { "Agriculture", false });
		tableAdminRoles = new JTable(modelAdminRoles);
		tableAdminRoles.getColumnModel().getColumn(0).setPreferredWidth(180);

		JScrollPane scrollAdmin = new JScrollPane(tableAdminRoles);
		scrollAdmin.setPreferredSize(new Dimension(300, 350));
		panelAdminConfig.setBorder(new TitledBorder("Allowed Admin Roles"));
		panelAdminConfig.add(scrollAdmin);
		add(panelAdminConfig);

		// ckboxAll = new JCheckBox("Check All");
		// ckboxAll.setFont(new Font("Times New Roman", Font.BOLD, 15));
		// ckboxAll.setPreferredSize(new Dimension(240, 20));
		// departments.add(ckboxAll);
		//
		// cboxAdmission = new JCheckBox("Admission");
		// cboxAdmission.setFont(new Font("Times New Roman", Font.BOLD, 15));
		// cboxAdmission.setPreferredSize(new Dimension(240, 20));
		// departments.add(cboxAdmission);
		//
		// cboxStudents = new JCheckBox("Students");
		// cboxStudents.setFont(new Font("Times New Roman", Font.BOLD, 15));
		// cboxStudents.setPreferredSize(new Dimension(240, 20));
		// departments.add(cboxStudents);
		//
		// cboxStaffs = new JCheckBox("Staffs");
		// cboxStaffs.setFont(new Font("Times New Roman", Font.BOLD, 15));
		// cboxStaffs.setPreferredSize(new Dimension(240, 20));
		// departments.add(cboxStaffs);
		//
		// cboxAcademics = new JCheckBox("Academics");
		// cboxAcademics.setFont(new Font("Times New Roman", Font.BOLD, 15));
		// cboxAcademics.setPreferredSize(new Dimension(240, 20));
		// departments.add(cboxAcademics);
		//
		// cboxLibrary = new JCheckBox("Library");
		// cboxLibrary.setFont(new Font("Times New Roman", Font.BOLD, 15));
		// cboxLibrary.setPreferredSize(new Dimension(240, 20));
		// departments.add(cboxLibrary);
		//
		// cboxFinance = new JCheckBox("Finance");
		// cboxFinance.setFont(new Font("Times New Roman", Font.BOLD, 15));
		// cboxFinance.setPreferredSize(new Dimension(240, 20));
		// departments.add(cboxFinance);
		//
		// cboxTimeTable = new JCheckBox("Time Table");
		// cboxTimeTable.setFont(new Font("Times New Roman", Font.BOLD, 15));
		// cboxTimeTable.setPreferredSize(new Dimension(240, 20));
		// departments.add(cboxTimeTable);
		//
		// cboxAdmin = new JCheckBox("Administration");
		// cboxAdmin.setFont(new Font("Times New Roman", Font.BOLD, 15));
		// cboxAdmin.setPreferredSize(new Dimension(240, 20));
		// departments.add(cboxAdmin);
		//
		// allowedtabs = new JPanel();
		// allowedtabs.setPreferredSize(new Dimension(300, 240));
		// allowedtabs.setBorder(new TitledBorder("Allowed Tabs"));
		// JScrollPane scrolltabs = new JScrollPane(allowedtabs);
		// scrolltabs.setPreferredSize(new Dimension(300, 190));
		// add(scrolltabs);
		// // check boxes under allowed tabs
		//
		// JCheckBox checkboxAlltabs = new JCheckBox("Check All");
		// checkboxAlltabs.setFont(new Font("Times New Roman", Font.BOLD, 15));
		// checkboxAlltabs.setPreferredSize(new Dimension(240, 20));
		// allowedtabs.add(checkboxAlltabs);
		//
		// cboxMyAccount = new JCheckBox("My Account");
		// cboxMyAccount.setFont(new Font("Times New Roman", Font.BOLD, 15));
		// cboxMyAccount.setPreferredSize(new Dimension(240, 20));
		// Account = 0;
		// cboxMyAccount.addActionListener(new ActionListener() {
		//
		// @Override
		// public void actionPerformed(ActionEvent arg0) {
		// // TODO Auto-generated method stub
		// if (cboxMyAccount.isSelected()) {
		// Account = 1;
		// } else {
		// Account = 0;
		// }
		// }
		// });
		// allowedtabs.add(cboxMyAccount);
		//
		// cboxLab = new JCheckBox("Laboratory");
		// cboxLab.setFont(new Font("Times New Roman", Font.BOLD, 15));
		// cboxLab.setPreferredSize(new Dimension(240, 20));
		//
		// Lab = 0;
		// cboxLab.addActionListener(new ActionListener() {
		//
		// @Override
		// public void actionPerformed(ActionEvent arg0) {
		// // TODO Auto-generated method stub
		// if (cboxLab.isSelected()) {
		// Lab = 1;
		// } else {
		// Lab = 0;
		// }
		// }
		// });
		// allowedtabs.add(cboxLab);
		//
		// cboxDispensary = new JCheckBox("Dispensary");
		// cboxDispensary.setFont(new Font("Times New Roman", Font.BOLD, 15));
		// cboxDispensary.setPreferredSize(new Dimension(240, 20));
		//
		// Dispensary=0;
		// cboxDispensary.addActionListener(new ActionListener() {
		//
		// @Override
		// public void actionPerformed(ActionEvent arg0) {
		// // TODO Auto-generated method stub
		// if (cboxDispensary.isSelected()) {
		// Dispensary = 1;
		// } else {
		// Dispensary = 0;
		// }
		// }
		// });
		// allowedtabs.add(cboxDispensary);
		//
		// cboxAssets = new JCheckBox("Assets");
		// cboxAssets.setFont(new Font("Times New Roman", Font.BOLD, 15));
		// cboxAssets.setPreferredSize(new Dimension(240, 20));
		// allowedtabs.add(cboxAssets);
		//
		// cboxDormitory = new JCheckBox("Dormitory");
		// cboxDormitory.setFont(new Font("Times New Roman", Font.BOLD, 15));
		// cboxDormitory.setPreferredSize(new Dimension(240, 20));
		// allowedtabs.add(cboxDormitory);
		//
		// cboxHome = new JCheckBox("Home");
		// cboxHome.setFont(new Font("Times New Roman", Font.BOLD, 15));
		// cboxHome.setPreferredSize(new Dimension(240, 20));
		// allowedtabs.add(cboxHome);
		//
		// cboxNotice = new JCheckBox("Notice");
		// cboxNotice.setFont(new Font("Times New Roman", Font.BOLD, 15));
		// cboxNotice.setPreferredSize(new Dimension(240, 20));
		// allowedtabs.add(cboxNotice);
		//
		// cboxAttendance = new JCheckBox("Attendance");
		// cboxAttendance.setFont(new Font("Times New Roman", Font.BOLD, 15));
		// cboxAttendance.setPreferredSize(new Dimension(240, 20));
		// allowedtabs.add(cboxAttendance);
		//
		// allowedroles = new JPanel();
		// allowedroles.setPreferredSize(new Dimension(300, 240));
		// allowedroles.setBorder(new TitledBorder("Allowed Roles"));
		// JScrollPane scrollroles = new JScrollPane(allowedroles);
		// scrollroles.setPreferredSize(new Dimension(300, 190));
		// add(scrollroles);
		//
		// checkboxAllroles = new JCheckBox("Check All");
		// checkboxAllroles.setFont(new Font("Times New Roman", Font.BOLD, 15));
		// checkboxAllroles.setPreferredSize(new Dimension(240, 20));
		// allowedroles.add(checkboxAllroles);
		//
		// cboxMyAccount1 = new JCheckBox("Grant Permissions(Rights)");
		// cboxMyAccount1.setFont(new Font("Times New Roman", Font.BOLD, 15));
		// cboxMyAccount1.setPreferredSize(new Dimension(240, 20));
		// allowedroles.add(cboxMyAccount1);
		//
		// cboxAddUsers = new JCheckBox("Add New User");
		// cboxAddUsers.setFont(new Font("Times New Roman", Font.BOLD, 15));
		// cboxAddUsers.setPreferredSize(new Dimension(240, 20));
		// allowedroles.add(cboxAddUsers);
		//
		// cboxAssets1 = new JCheckBox("Delete User");
		// cboxAssets1.setFont(new Font("Times New Roman", Font.BOLD, 15));
		// cboxAssets1.setPreferredSize(new Dimension(240, 20));
		// allowedroles.add(cboxAssets1);
		//
		// cboxDormitory1 = new JCheckBox("Edit Users Details");
		// cboxDormitory1.setFont(new Font("Times New Roman", Font.BOLD, 15));
		// cboxDormitory1.setPreferredSize(new Dimension(240, 20));
		// allowedroles.add(cboxDormitory1);
		//
		// cboxHome1 = new JCheckBox("View All Users");
		// cboxHome1.setFont(new Font("Times New Roman", Font.BOLD, 15));
		// cboxHome1.setPreferredSize(new Dimension(240, 20));
		// allowedroles.add(cboxHome1);
		//
		//
		// cboxAttendance1 = new JCheckBox("Create Group Chat");
		// cboxAttendance1.setFont(new Font("Times New Roman", Font.BOLD, 15));
		// cboxAttendance1.setPreferredSize(new Dimension(240, 20));
		// allowedroles.add(cboxAttendance1);
		//
		// panelNotifications = new JPanel();
		// panelNotifications.setPreferredSize(new Dimension(300, 240));
		// scrollPaneNotification = new JScrollPane(panelNotifications);
		// scrollPaneNotification.setPreferredSize(new Dimension(300, 190));
		// panelNotifications.setBorder(new TitledBorder("Allowed Users Actions"));
		// add(scrollPaneNotification);
		//
		// checkboxAllroles1 = new JCheckBox("Check All");
		// checkboxAllroles1.setFont(new Font("Times New Roman", Font.BOLD, 15));
		// checkboxAllroles1.setPreferredSize(new Dimension(240, 20));
		// panelNotifications.add(checkboxAllroles1);
		//
		// cboxMyAccount11 = new JCheckBox("Delete Information");
		// cboxMyAccount11.setFont(new Font("Times New Roman", Font.BOLD, 15));
		// cboxMyAccount11.setPreferredSize(new Dimension(240, 20));
		// panelNotifications.add(cboxMyAccount11);
		//
		// cboxAddUsers1 = new JCheckBox("Edit Information");
		// cboxAddUsers1.setFont(new Font("Times New Roman", Font.BOLD, 15));
		// cboxAddUsers1.setPreferredSize(new Dimension(240, 20));
		// panelNotifications.add(cboxAddUsers1);
		//
		// cboxAssets11 = new JCheckBox("Delete My Account");
		// cboxAssets11.setFont(new Font("Times New Roman", Font.BOLD, 15));
		// cboxAssets11.setPreferredSize(new Dimension(240, 20));
		// panelNotifications.add(cboxAssets11);
		//
		// cboxDormitory11 = new JCheckBox("Edit My Details");
		// cboxDormitory11.setFont(new Font("Times New Roman", Font.BOLD, 15));
		// cboxDormitory11.setPreferredSize(new Dimension(240, 20));
		// panelNotifications.add(cboxDormitory11);
		//
		// cboxHome11 = new JCheckBox("Add New Information");
		// cboxHome11.setFont(new Font("Times New Roman", Font.BOLD, 15));
		// cboxHome11.setPreferredSize(new Dimension(240, 20));
		// panelNotifications.add(cboxHome11);
		//
		// cboxNotice11 = new JCheckBox("Chat With Anybody");
		// cboxNotice11.setFont(new Font("Times New Roman", Font.BOLD, 15));
		// cboxNotice11.setPreferredSize(new Dimension(240, 20));
		// panelNotifications.add(cboxNotice11);
		//
		// cboxAttendance11 = new JCheckBox("Change Application Settings");
		// cboxAttendance11.setFont(new Font("Times New Roman", Font.BOLD, 15));
		// cboxAttendance11.setPreferredSize(new Dimension(240, 20));
		// panelNotifications.add(cboxAttendance11);
		//
		// panelAdminConfig = new JPanel();
		// panelAdminConfig.setPreferredSize(new Dimension(300, 240));
		// scrollPaneConfig = new JScrollPane(panelAdminConfig);
		// scrollPaneConfig.setPreferredSize(new Dimension(300, 190));
		// panelAdminConfig.setBorder(new TitledBorder("Admin Roles"));
		// add(scrollPaneConfig);

		// admin roles
		// checkboxAllroles11 = new JCheckBox("Check All");
		// checkboxAllroles11.setFont(new Font("Times New Roman", Font.BOLD, 15));
		// checkboxAllroles11.setPreferredSize(new Dimension(240, 20));
		// panelAdminConfig.add(checkboxAllroles11);
		//
		// cboxMyAccountAdminRoles = new JCheckBox("Dismiss Students");
		// cboxMyAccountAdminRoles.setFont(new Font("Times New Roman", Font.BOLD, 15));
		// cboxMyAccountAdminRoles.setPreferredSize(new Dimension(240, 20));
		// panelAdminConfig.add(cboxMyAccountAdminRoles);
		//
		// cboxLabAdminRoles = new JCheckBox("Suspend Students");
		// cboxLabAdminRoles.setFont(new Font("Times New Roman", Font.BOLD, 15));
		// cboxLabAdminRoles.setPreferredSize(new Dimension(240, 20));
		// panelAdminConfig.add(cboxLabAdminRoles);
		//
		// cboxAssetsAdminRoles = new JCheckBox("Delete Student Info Permanently");
		// cboxAssetsAdminRoles.setFont(new Font("Times New Roman", Font.BOLD, 15));
		// cboxAssetsAdminRoles.setPreferredSize(new Dimension(240, 20));
		// panelAdminConfig.add(cboxAssetsAdminRoles);
		//
		// cboxDormitoryAdminRoles = new JCheckBox("Delete Staff Info Permanently");
		// cboxDormitoryAdminRoles.setFont(new Font("Times New Roman", Font.BOLD, 15));
		// cboxDormitoryAdminRoles.setPreferredSize(new Dimension(240, 20));
		// panelAdminConfig.add(cboxDormitoryAdminRoles);
		//
		// cboxHomeAdminRoles = new JCheckBox("Give Responsibilities");
		// cboxHomeAdminRoles.setFont(new Font("Times New Roman", Font.BOLD, 15));
		// cboxHomeAdminRoles.setPreferredSize(new Dimension(240, 20));
		// panelAdminConfig.add(cboxHomeAdminRoles);
		//
		// cboxNoticeAdminRoles = new JCheckBox("Edit Responsibilities");
		// cboxNoticeAdminRoles.setFont(new Font("Times New Roman", Font.BOLD, 15));
		// cboxNoticeAdminRoles.setPreferredSize(new Dimension(240, 20));
		// panelAdminConfig.add(cboxNoticeAdminRoles);
		//
		// cboxAttendanceAdminRoles = new JCheckBox("Block Someone/Everyone");
		// cboxAttendanceAdminRoles.setFont(new Font("Times New Roman", Font.BOLD, 15));
		// cboxAttendanceAdminRoles.setPreferredSize(new Dimension(240, 20));
		// panelAdminConfig.add(cboxAttendanceAdminRoles);

		btnSave = new JButton("Save Changes");
//		btnSave.setEnabled(false);
		btnSave.setFont(new Font("Times New Roman", Font.BOLD, 16));
		add(btnSave);
		btnSave.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub

				updateSubjectsStudents();
				updateAdminRoles();
				updateSystemRoles();

			}
		});

		btnSaveNewActivity = new JButton("Add System Roles");
		btnSaveNewActivity.setEnabled(false);
		btnSaveNewActivity.setFont(new Font("Times New Roman", Font.BOLD, 16));
		add(btnSaveNewActivity);
		btnSaveNewActivity.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub

				JDialog dialog = new JDialog();
				dialog.setTitle("Add System Roles");
				dialog.setLocationRelativeTo(null);
				dialog.setSize(400, 100);

				JPanel panelMain = new JPanel();
				dialog.add(panelMain);

				JLabel labeAcitivityName = new JLabel("System Roles Name");
				panelMain.add(labeAcitivityName);

				JTextField fieldActivityName = new JTextField();
				fieldActivityName.setPreferredSize(new Dimension(150, 25));
				panelMain.add(fieldActivityName);

				JButton btnAtivityName = new JButton("Save System Roles");
				panelMain.add(btnAtivityName);
				btnAtivityName.addActionListener(new ActionListener() {

					@Override
					public void actionPerformed(ActionEvent arg0) {
						// TODO Auto-generated method stub

						AddUpdateDelete("insert into rights_activities_system(right_name) values('"
								+ fieldActivityName.getText() + "')");
						AddUpdateDelete("alter table application_users add column `" + fieldActivityName.getText()
								+ "` int(2) DEFAULT 0");
						displayData(tableSystemRoles, "select right_name from rights_activities_system");
					}
				});

				dialog.setVisible(true);

			}
		});

		btnSaveNewAdminRoles = new JButton("Add Admin Roles");
		btnSaveNewAdminRoles.setEnabled(false);
		btnSaveNewAdminRoles.setFont(new Font("Times New Roman", Font.BOLD, 16));
		add(btnSaveNewAdminRoles);
		btnSaveNewAdminRoles.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub

				JDialog dialog = new JDialog();
				dialog.setTitle("Add Admin Roles");
				dialog.setLocationRelativeTo(null);
				dialog.setSize(400, 100);

				JPanel panelMain = new JPanel();
				dialog.add(panelMain);

				JLabel labeAcitivityName = new JLabel("Admin Roles Name");
				panelMain.add(labeAcitivityName);

				JTextField fieldAdminRolesName = new JTextField();
				fieldAdminRolesName.setPreferredSize(new Dimension(150, 25));
				panelMain.add(fieldAdminRolesName);

				JButton btnAtivityName = new JButton("Save Admin Roles");
				panelMain.add(btnAtivityName);
				btnAtivityName.addActionListener(new ActionListener() {

					@Override
					public void actionPerformed(ActionEvent arg0) {
						// TODO Auto-generated method stub

						AddUpdateDelete("alter table application_users add column `" + fieldAdminRolesName.getText()
								+ "` int(2) DEFAULT 0");
						AddUpdateDelete("insert into rights_activities_admin(right_name) values('"
								+ fieldAdminRolesName.getText() + "')");
						displayData(tableAdminRoles, "select right_name from rights_activities_admin");
					}
				});

				dialog.setVisible(true);

			}
		});

		setLayout(new FlowLayout(FlowLayout.CENTER));

		displayInComboBox(combo, "select username from application_users");
		combo.setOnAction(new EventHandler<Event>() {

			@Override
			public void handle(Event arg0) {
				// TODO Auto-generated method stub

				callUsersDateFromApplicationSystemUsers();
				callUsersDateFromApplicationUsers();
				callUsersDateFromApplicationSubjects();
			}
		});
		setVisible(true);

		displayData(table,
				"select subject_name from student_subjects UNION select subject_name from student_subjectsa");
		displayData(tableSystemRoles, "select right_name from rights_activities_system");
		displayData(tableAdminRoles, "select right_name from rights_activities_admin");
	}
	
	

	public JButton getBtnSave() {
		return btnSave;
	}



	public void setBtnSave(JButton btnSave) {
		this.btnSave = btnSave;
	}



	public void displayInComboBox(ComboBox combo, String query) {

		try {
			conn = CashBookController.getConnection();
			pst = conn.prepareStatement(query);

			ResultSet rs = pst.executeQuery();

			data.clear();
			while (rs.next()) {

				data.add(rs.getString(1));

			}
		} catch (SQLException e) {

			e.printStackTrace();
		} finally {
			if (conn != null) {

				try {
					conn.close();
					pst.close();
				} catch (SQLException e) {

					e.printStackTrace();
				}

			}
		}

	}

	public void tickCheckBoxes() {

		try {
			conn = CashBookController.getConnection();
			pst = conn.prepareStatement("select * from application_users where username='"
					+ combo.getSelectionModel().getSelectedItem() + "'");

			ResultSet rs = pst.executeQuery();

			while (rs.next()) {

				if (rs.getInt("laboratory_tab") == 1) {
					cboxLab.setSelected(true);
				} else {
					cboxLab.setSelected(false);
				}
				if (rs.getInt("dispensary_tab") == 1) {
					cboxDispensary.setSelected(true);
				} else {
					cboxDispensary.setSelected(false);
				}

			}
			if (rs.getInt("attendance_tab") == 1) {
				cboxAttendance.setSelected(true);
			} else {

				cboxAttendance.setSelected(false);
			}
			if (rs.getInt("notice_tab") == 1) {
				cboxNotice.setSelected(true);
			} else {
				cboxNotice.setSelected(false);
			}

			if (rs.getInt("dormitory_tab") == 1) {
				cboxDormitory.setSelected(true);
			} else {

				cboxDormitory.setSelected(false);
			}

			if (rs.getInt("assets_register_tab") == 1) {
				cboxAssets.setSelected(true);
			} else {

				cboxAssets.setSelected(false);
			}

			if (rs.getInt("admission_dept") == 1) {
				cboxAdmission.setSelected(true);
			} else {

				cboxAdmission.setSelected(false);
			}

			if (rs.getInt("academics_dept") == 1) {
				cboxAcademics.setSelected(true);
			} else {
				cboxAcademics.setSelected(false);
			}

			if (rs.getInt("students_dept") == 1) {
				cboxStudents.setSelected(true);
			} else {

				cboxStudents.setSelected(false);
			}

			if (rs.getInt("staffs_dept") == 1) {
				cboxStaffs.setSelected(true);
			} else {

				cboxStaffs.setSelected(false);

			}

			if (rs.getInt("library_dept") == 1) {
				cboxLibrary.setSelected(true);
			} else {

				cboxLibrary.setSelected(false);

			}

			if (rs.getInt("time_table_dept") == 1) {
				cboxTimeTable.setSelected(true);
			} else {

				cboxTimeTable.setSelected(false);
			}

			if (rs.getInt("finance_dept") == 1) {
				cboxFinance.setSelected(true);
			} else {

				cboxFinance.setSelected(false);
			}

			if (rs.getInt("admin_dept") == 1) {
				cboxAdmin.setSelected(true);
			} else {

				cboxAdmin.setSelected(false);
			}

			if (rs.getInt("add_users_role") == 1) {
				cboxAddUsers.setSelected(true);
			} else {
				cboxAddUsers.setSelected(false);
			}

			if (rs.getInt("permit_users_role") == 1) {
				cboxMyAccount1.setSelected(true);
			} else {

				cboxMyAccount1.setSelected(false);
			}

			if (rs.getInt("delete_users_role") == 1) {
				cboxAssets1.setSelected(true);
			} else {

				cboxAssets1.setSelected(false);
			}

			if (rs.getInt("edit_users_role") == 1) {
				cboxDormitory1.setSelected(true);
			} else {

				cboxDormitory1.setSelected(false);
			}

			if (rs.getInt("suspend_students_admin") == 1) {
				cboxLabAdminRoles.setSelected(true);
			} else {

				cboxLabAdminRoles.setSelected(false);

			}

			if (rs.getInt("dismiss_students_admin") == 1) {
				cboxMyAccountAdminRoles.setSelected(true);
			} else {

				cboxMyAccountAdminRoles.setSelected(false);
			}

			if (rs.getInt("give_responsibilities_admin") == 1) {
				cboxHomeAdminRoles.setSelected(true);
			} else {
				cboxHomeAdminRoles.setSelected(false);
			}

			if (rs.getInt("edit_responsibilities_admin") == 1) {
				cboxNoticeAdminRoles.setSelected(true);
			} else {

				cboxNoticeAdminRoles.setSelected(false);
			}

			//

		} catch (SQLException e) {

			e.printStackTrace();
		} finally {
			if (conn != null) {

				try {
					conn.close();
					pst.close();
				} catch (SQLException e) {

					e.printStackTrace();
				}

			}
		}

	}

	public void AddUpdateDelete(String querries) {

		try {

			java.sql.Connection conn = null;
			java.sql.PreparedStatement pst = null;
			conn = CashBookController.getConnection();
			pst = conn.prepareStatement(querries);

			pst.executeUpdate();

			JOptionPane.showMessageDialog(null, "Request Executed Successfully");

		} catch (Exception ex) {
			ex.printStackTrace();
			JOptionPane.showMessageDialog(null, "Request Not Executed Successfully " + ex.getMessage());

		}
	}

	public class MyTableModel extends DefaultTableModel {

		public MyTableModel() {
			super(new String[] { "Name of Right", "Allowed" }, 0);
		}

		@Override
		public Class<?> getColumnClass(int columnIndex) {
			Class clazz = String.class;
			switch (columnIndex) {
			case 0:
				clazz = String.class;
				break;
			case 1:
				clazz = Boolean.class;
				break;
			}
			return clazz;
		}

		@Override
		public boolean isCellEditable(int row, int column) {
			return true;
		}

		@Override
		public void setValueAt(Object aValue, int row, int column) {
			if (aValue instanceof Boolean && column == 1) {
				System.out.println(aValue);
				Vector rowData = (Vector) getDataVector().get(row);
				rowData.set(1, (boolean) aValue);
				fireTableCellUpdated(row, column);

			}
		}

	}

	public void updateSubjectsStudents() {

		try {

			java.sql.Connection conn = null;

			java.sql.PreparedStatement pst = null;

			conn = CashBookController.getConnection();

			for (int j = 0; j < table.getModel().getRowCount(); j++) {

				String headingSubject = table.getValueAt(j, 0).toString();
				String sqlStatement = "update application_users set `" + headingSubject + "`=? where username='"
						+ combo.getSelectionModel().getSelectedItem() + "'";
				Boolean valueSelected = (Boolean) table.getValueAt(j, 1);
				pst = conn.prepareStatement(sqlStatement);
				if (Boolean.TRUE.equals(valueSelected)) {
					pst.setObject(1, valueSelected);
					pst.executeUpdate();
				} else {
					pst.setObject(1, valueSelected);
					pst.executeUpdate();
				}
			}

		} catch (Exception ex) {
			ex.printStackTrace();
			JOptionPane.showMessageDialog(null, "Request Not Executed Successfully " + ex.getMessage());

		}
	}
	
	
	public void updateSystemRoles() {

		try {

			java.sql.Connection conn = null;

			java.sql.PreparedStatement pst = null;

			conn = CashBookController.getConnection();

			for (int j = 0; j < tableSystemRoles.getModel().getRowCount(); j++) {

				String headingSubject = tableSystemRoles.getValueAt(j, 0).toString();
				String sqlStatement = "update application_users set `" + headingSubject + "`=? where username='"
						+ combo.getSelectionModel().getSelectedItem() + "'";
				Boolean valueSelected = (Boolean) tableSystemRoles.getValueAt(j, 1);
				pst = conn.prepareStatement(sqlStatement);
				if (Boolean.TRUE.equals(valueSelected)) {
					pst.setObject(1, valueSelected);
					pst.executeUpdate();
				} else {
					pst.setObject(1, valueSelected);
					pst.executeUpdate();
				}
			}

		} catch (Exception ex) {
			ex.printStackTrace();
			JOptionPane.showMessageDialog(null, "Request Not Executed Successfully " + ex.getMessage());

		}
	}

	
	public void updateAdminRoles() {

		try {

			java.sql.Connection conn = null;

			java.sql.PreparedStatement pst = null;

			conn = CashBookController.getConnection();

			for (int j = 0; j < tableAdminRoles.getModel().getRowCount(); j++) {

				String headingSubject = tableAdminRoles.getValueAt(j, 0).toString();
				String sqlStatement = "update application_users set `" + headingSubject + "`=? where username='"
						+ combo.getSelectionModel().getSelectedItem() + "'";
				Boolean valueSelected = (Boolean) tableAdminRoles.getValueAt(j, 1);
				pst = conn.prepareStatement(sqlStatement);
				if (Boolean.TRUE.equals(valueSelected)) {
					pst.setObject(1, valueSelected);
					pst.executeUpdate();
				} else {
					pst.setObject(1, valueSelected);
					pst.executeUpdate();
				}
			}

		} catch (Exception ex) {
			ex.printStackTrace();
			JOptionPane.showMessageDialog(null, "Request Not Executed Successfully " + ex.getMessage());

		}
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

	
	protected void callUsersDateFromApplicationUsers() {
		// TODO Auto-generated method stub

		String sql = "select * from application_users where username='" + combo.getSelectionModel().getSelectedItem()
				+ "'";
		try {

			con = CashBookController.getConnection();
			pst = con.prepareStatement(sql);
			rs = pst.executeQuery();
			if (rs.next()) {

				for (int row = 0; row < tableAdminRoles.getModel().getRowCount(); row++) {

					String subjectName = tableAdminRoles.getValueAt(row, 0).toString();

					if (rs.getInt(subjectName) == 1) {
						tableAdminRoles.setValueAt(Boolean.TRUE, row, 1);

					} else {
						tableAdminRoles.setValueAt(Boolean.FALSE, row, 1);
					}

				}

			}

		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		} finally {
			if (conn != null) {
				try {
					pst.close();
					rs.close();
					conn.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}

	}

	protected void callUsersDateFromApplicationSystemUsers() {
		// TODO Auto-generated method stub

		String sql = "select * from application_users where username='" + combo.getSelectionModel().getSelectedItem()
				+ "'";
		try {

			con = CashBookController.getConnection();
			pst = con.prepareStatement(sql);
			rs = pst.executeQuery();
			if (rs.next()) {

				for (int row = 0; row < tableSystemRoles.getModel().getRowCount(); row++) {

					String subjectName = tableSystemRoles.getValueAt(row, 0).toString();

					if (rs.getInt(subjectName) == 1) {
						tableSystemRoles.setValueAt(Boolean.TRUE, row, 1);

					} else {
						tableSystemRoles.setValueAt(Boolean.FALSE, row, 1);
					}

				}

			}

		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		} finally {
			if (conn != null) {
				try {
					pst.close();
					rs.close();
					conn.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}

	}
	protected void callUsersDateFromApplicationSubjects() {
		// TODO Auto-generated method stub

		String sql = "select * from application_users where username='" + combo.getSelectionModel().getSelectedItem()
				+ "'";
		try {

			con = CashBookController.getConnection();
			pst = con.prepareStatement(sql);
			rs = pst.executeQuery();
			if (rs.next()) {

				for (int row = 0; row < table.getModel().getRowCount(); row++) {

					String subjectName = table.getValueAt(row, 0).toString();

					if (rs.getInt(subjectName) == 1) {
						table.setValueAt(Boolean.TRUE, row, 1);

					} else {
						table.setValueAt(Boolean.FALSE, row, 1);
					}

				}

			}

		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		} finally {
			if (conn != null) {
				try {
					pst.close();
					rs.close();
					conn.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}

	}

}
