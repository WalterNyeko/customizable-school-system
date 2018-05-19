package school.ui.account;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.sql.SQLException;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;
import javax.swing.filechooser.FileNameExtensionFilter;

import school.ui.finances.CashBookController;

@SuppressWarnings("serial")
public class SystemSettings extends JDialog {

	private JTabbedPane tabEvents;
	private JPanel panelEvents, panelAdminConfig, panelNotifications;
	private JScrollPane scrollPaneEvents, scrollPaneAdmin, scrollPaneNotification, scrollPaneNamings;
	JPanel panelNamings;
	private JLabel nameofSchool, address, email, telephone;
	private JTextField fieldSchool, fieldAddress, fieldEmail, fieldTel;
	private JButton btnSaveChangesNotification, btnSaveChangesSchoolInfo;
	private JLabel schoolLogo;
	private JButton btnUploadLogo;
	protected String ss;
	private JLabel labelSchoolClasses;
	private JTextField fieldNameOfClass;
	private JButton btnAddClass;
	private JPanel panelAddClass;
	private JPanel panelAddSubjects;
	private JLabel labelSchoolSubjectses;
	private JTextField fieldNameOfSubjects;
	private JButton btnAddSubjects;
	private JPanel panelAddSponsors;
	private JLabel labelSchoolSponsorses;
	private JTextField fieldNameOfSponsors;
	private JButton btnAddSponsors;
	private JPanel panelAddNationality;
	private JLabel labelSchoolNationalityes;
	private JTextField fieldNameOfNationality;
	private JButton btnAddNationality;
	private JPanel panelAddTerm;
	private JLabel labelSchoolTermes;
	private JTextField fieldNameOfTerm;
	private JButton btnAddTerm;
	private JPanel panelAddReligion;
	private JLabel labelSchoolReligiones;
	private JTextField fieldNameOfReligion;
	private JButton btnAddReligion;
	private JPanel panelAddSex;
	private JLabel labelSchoolSexes;
	private JTextField fieldNameOfSex;
	private JButton btnAddSex;
	private JPanel panelAddSubjectsA;
	private JTextField fieldNameOfSubjectsA;
	private JButton btnAddSubjectsA;

	public static void main(String[] args) {
		new SystemSettings();
	}

	public SystemSettings() {

		setTitle("Application Settings");
		setSize(780, 350);
		setLocation(350, 220);

		tabEvents = new JTabbedPane();
		add(tabEvents);

		Dimension dimPanel = new Dimension(480, 320);

		panelEvents = new JPanel();
		panelEvents.setPreferredSize(dimPanel);
		scrollPaneEvents = new JScrollPane(panelEvents);
		scrollPaneEvents.setPreferredSize(dimPanel);
		tabEvents.addTab("Laboratory Thresholds", scrollPaneEvents);

		panelAdminConfig = new JPanel();
		panelAdminConfig.setPreferredSize(dimPanel);
		scrollPaneAdmin = new JScrollPane(panelAdminConfig);
		scrollPaneAdmin.setPreferredSize(dimPanel);
		tabEvents.addTab("Admin Settings", scrollPaneAdmin);

		panelAddClass = new JPanel();
		panelAddClass.setBorder(new TitledBorder("Add Student Class"));

		labelSchoolClasses = new JLabel("Name of Class:");
		panelAddClass.add(labelSchoolClasses);

		fieldNameOfClass = new JTextField();
		fieldNameOfClass.setPreferredSize(new Dimension(110, 25));
		panelAddClass.add(fieldNameOfClass);

		btnAddClass = new JButton("Add Class");
		btnAddClass.setPreferredSize(new Dimension(110, 25));
		btnAddClass.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				AddClass("insert into student_classes(class_name) values('" + fieldNameOfClass.getText() + "') ");
				AddClassTable("create table `" + fieldNameOfClass.getText() + "`(id int(50) primary key auto_increment,payment_code varchar(50),class_number varchar(100)"
						+ ",student_class varchar(100),student_name varchar(200),date_of_birth date,address varchar(100),sponsor varchar(100)"
						+ ",dormitory varchar(100),term varchar(100),year varchar(100),fathers_contact varchar(100)"
						+ ",mothers_contact varchar(100),guardians_contact varchar(100),fathers_name varchar(200),mothers_name varchar(200),guardians_name varchar(200),discipline_status varchar(200))");
				AddClass("CREATE TRIGGER `"+fieldNameOfClass.getText()+"` AFTER INSERT ON `" + fieldNameOfClass.getText()
						+ "` FOR EACH ROW BEGIN INSERT INTO all_students_and_parents SET payment_code=NEW.payment_code,class_number=NEW.class_number,"
						+ "student_class=NEW.student_class,student_name=NEW.student_name,date_of_birth=NEW.date_of_birth,address=NEW.address,"
						+ "sponsor=NEW.sponsor,dormitory=NEW.dormitory,term=NEW.term,year=NEW.year,"
						+ "fathers_contact=NEW.fathers_contact,mothers_contact=NEW.mothers_contact,guardians_contact=NEW.guardians_contact,"
						+ "fathers_name=NEW.fathers_name,mothers_name=NEW.mothers_name,guardians_name=NEW.guardians_name;"
						+ "END ;");
				
			}
		});
		panelAddClass.add(btnAddClass);

		panelAdminConfig.add(panelAddClass);

		panelAddSubjects = new JPanel();
		panelAddSubjects.setBorder(new TitledBorder("Add O-Level Subjects"));

		labelSchoolSubjectses = new JLabel("Name of Subjects:");
		panelAddSubjects.add(labelSchoolSubjectses);

		fieldNameOfSubjects = new JTextField();
		fieldNameOfSubjects.setPreferredSize(new Dimension(110, 25));
		panelAddSubjects.add(fieldNameOfSubjects);

		btnAddSubjects = new JButton("Add O-Level Subject");
		btnAddSubjects.setPreferredSize(new Dimension(110, 25));
		btnAddSubjects.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				AddSubject(
						"insert into student_subjects(subject_name) values('" + fieldNameOfSubjects.getText() + "') ");
				AddSubject(
						"alter table subjects_offered add column `" + fieldNameOfSubjects.getText() + "` int(2) default 0");
				
				AddSubject("ALTER TABLE application_users add COLUMN `"+fieldNameOfSubjects.getText()+"` int(2) DEFAULT 0");
				
				

			}
		});
		panelAddSubjects.add(btnAddSubjects);

		panelAdminConfig.add(panelAddSubjects);

		panelAddSubjectsA = new JPanel();
		panelAddSubjectsA.setBorder(new TitledBorder("Add A-Level Subjects"));

		labelSchoolSubjectses = new JLabel("Name of Subjects:");
		panelAddSubjectsA.add(labelSchoolSubjectses);

		fieldNameOfSubjectsA = new JTextField();
		fieldNameOfSubjectsA.setPreferredSize(new Dimension(110, 25));
		panelAddSubjectsA.add(fieldNameOfSubjectsA);

		btnAddSubjectsA = new JButton("Add A-Level Subject");
		btnAddSubjectsA.setPreferredSize(new Dimension(110, 25));
		btnAddSubjectsA.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				AddSubject("insert into student_subjectsA(subject_name) values('" + fieldNameOfSubjectsA.getText()
						+ "') ");
				AddSubject("alter table subjects_offeredA add column `" + fieldNameOfSubjectsA.getText()
						+ "` int(2) default 0");
				
				AddSubject("ALTER TABLE application_users add COLUMN `"+fieldNameOfSubjectsA.getText()+"` int(2) DEFAULT 0");

			}
		});
		panelAddSubjectsA.add(btnAddSubjectsA);

		panelAdminConfig.add(panelAddSubjectsA);

		panelAddReligion = new JPanel();
		panelAddReligion.setBorder(new TitledBorder("Add Student Religion"));

		labelSchoolReligiones = new JLabel("Name of Religion:");
		panelAddReligion.add(labelSchoolReligiones);

		fieldNameOfReligion = new JTextField();
		fieldNameOfReligion.setPreferredSize(new Dimension(110, 25));
		panelAddReligion.add(fieldNameOfReligion);

		btnAddReligion = new JButton("Add Religion");
		btnAddReligion.setPreferredSize(new Dimension(110, 25));
		btnAddReligion.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				AddClass(
						"insert into student_religion(religion_name) values('" + fieldNameOfReligion.getText() + "') ");
			}
		});
		panelAddReligion.add(btnAddReligion);

		panelAdminConfig.add(panelAddReligion);

		panelAddTerm = new JPanel();
		panelAddTerm.setBorder(new TitledBorder("Add Student Term"));

		labelSchoolTermes = new JLabel("Name of Term:");
		panelAddTerm.add(labelSchoolTermes);

		fieldNameOfTerm = new JTextField();
		fieldNameOfTerm.setPreferredSize(new Dimension(110, 25));
		panelAddTerm.add(fieldNameOfTerm);

		btnAddTerm = new JButton("Add Term");
		btnAddTerm.setPreferredSize(new Dimension(110, 25));
		btnAddTerm.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				AddClass("insert into student_terms(term_name) values('" + fieldNameOfTerm.getText() + "') ");
			}
		});
		panelAddTerm.add(btnAddTerm);

		panelAdminConfig.add(panelAddTerm);

		panelAddNationality = new JPanel();
		panelAddNationality.setBorder(new TitledBorder("Add Student Nationality"));

		labelSchoolNationalityes = new JLabel("Name of Nationality:");
		panelAddNationality.add(labelSchoolNationalityes);

		fieldNameOfNationality = new JTextField();
		fieldNameOfNationality.setPreferredSize(new Dimension(110, 25));
		panelAddNationality.add(fieldNameOfNationality);

		btnAddNationality = new JButton("Add Nationality");
		btnAddNationality.setPreferredSize(new Dimension(110, 25));
		btnAddNationality.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				AddClass("insert into student_nationality(nationality_name) values('" + fieldNameOfNationality.getText()
						+ "') ");
			}
		});
		panelAddNationality.add(btnAddNationality);

		panelAdminConfig.add(panelAddNationality);

		panelAddSponsors = new JPanel();
		panelAddSponsors.setBorder(new TitledBorder("Add Student Sponsors"));

		labelSchoolSponsorses = new JLabel("Name of Sponsors:");
		panelAddSponsors.add(labelSchoolSponsorses);

		fieldNameOfSponsors = new JTextField();
		fieldNameOfSponsors.setPreferredSize(new Dimension(110, 25));
		panelAddSponsors.add(fieldNameOfSponsors);

		btnAddSponsors = new JButton("Add Sponsors");
		btnAddSponsors.setPreferredSize(new Dimension(110, 25));
		btnAddSponsors.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				AddClass(
						"insert into student_sponsors(sponsors_name) values('" + fieldNameOfSponsors.getText() + "') ");
			}
		});
		panelAddSponsors.add(btnAddSponsors);

		panelAdminConfig.add(panelAddSponsors);

		// Admin Config data has to go here

		panelNotifications = new JPanel();
		panelNotifications.setPreferredSize(dimPanel);
		scrollPaneNotification = new JScrollPane(panelNotifications);
		scrollPaneNotification.setPreferredSize(dimPanel);
		tabEvents.addTab("Events Notifcations", scrollPaneNotification);

		panelAddSex = new JPanel();
		panelAddSex.setBorder(new TitledBorder("Add Student Sex"));

		labelSchoolSexes = new JLabel("Name of Sex:");
		panelAddSex.add(labelSchoolSexes);

		fieldNameOfSex = new JTextField();
		fieldNameOfSex.setPreferredSize(new Dimension(110, 25));
		panelAddSex.add(fieldNameOfSex);

		btnAddSex = new JButton("Add Sex");
		btnAddSex.setPreferredSize(new Dimension(110, 25));
		btnAddSex.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				AddClass("insert into student_sex(sex_name) values('" + fieldNameOfSex.getText() + "') ");
			}
		});
		panelAddSex.add(btnAddSex);

		panelAdminConfig.add(panelAddSex);

		labelSchoolClasses.setPreferredSize(new Dimension(110, 25));
		labelSchoolNationalityes.setPreferredSize(new Dimension(110, 25));
		labelSchoolReligiones.setPreferredSize(new Dimension(110, 25));
		labelSchoolSponsorses.setPreferredSize(new Dimension(110, 25));
		labelSchoolSubjectses.setPreferredSize(new Dimension(110, 25));
		labelSchoolTermes.setPreferredSize(new Dimension(110, 25));
		labelSchoolSexes.setPreferredSize(new Dimension(110, 25));

		// dimension

		Dimension dimFields = new Dimension(600, 30);
		Dimension dimLabels = new Dimension(100, 30);

		JLabel labelEmails = new JLabel("Emails");
		labelEmails.setPreferredSize(dimLabels);
		panelNotifications.add(labelEmails);

		JTextArea emailPane = new JTextArea();
		JScrollPane paneEmail = new JScrollPane(emailPane);
		emailPane.setPreferredSize(new Dimension(600, 80));
		paneEmail.setPreferredSize(new Dimension(600, 80));
		panelNotifications.add(paneEmail);

		JLabel labelSMS = new JLabel("SMS");
		labelSMS.setPreferredSize(dimLabels);
		panelNotifications.add(labelSMS);

		JTextArea SMSPane = new JTextArea();
		JScrollPane paneSMS = new JScrollPane(SMSPane);
		SMSPane.setPreferredSize(new Dimension(600, 80));
		paneSMS.setPreferredSize(new Dimension(600, 80));
		panelNotifications.add(paneSMS);

		btnSaveChangesNotification = new JButton("Save Changes");
		btnSaveChangesNotification.setFont(new Font("Times New Roman", Font.BOLD, 16));
		panelNotifications.add(btnSaveChangesNotification);

		panelNamings = new JPanel();
		panelNamings.setPreferredSize(dimPanel);
		scrollPaneNamings = new JScrollPane(panelNamings);
		scrollPaneNamings.setPreferredSize(dimPanel);
		tabEvents.addTab("School Name & Address", scrollPaneNamings);

		nameofSchool = new JLabel("Name Of School");
		nameofSchool.setPreferredSize(dimLabels);
		panelNamings.add(nameofSchool);

		fieldSchool = new JTextField("ST. MARY'S HIGH SCHOOL-KISUGU");
		panelNamings.add(fieldSchool);
		fieldSchool.setPreferredSize(dimFields);

		address = new JLabel("Address of School");
		address.setPreferredSize(dimLabels);
		panelNamings.add(address);

		fieldAddress = new JTextField("P.O.BOX 123 Gulu(Ug)");
		panelNamings.add(fieldAddress);
		fieldAddress.setPreferredSize(dimFields);

		email = new JLabel("Email Address");
		email.setPreferredSize(dimLabels);
		panelNamings.add(email);

		fieldEmail = new JTextField("E-mail: layibicollege@gmail.com");
		panelNamings.add(fieldEmail);
		fieldEmail.setPreferredSize(dimFields);

		telephone = new JLabel("Telephone");
		telephone.setPreferredSize(dimLabels);
		panelNamings.add(telephone);

		fieldTel = new JTextField("Tel: 031265746352/0758910850");
		panelNamings.add(fieldTel);
		fieldTel.setPreferredSize(dimFields);

		schoolLogo = new JLabel("");
		schoolLogo.setPreferredSize(new Dimension(130, 110));
		schoolLogo.setBorder(new LineBorder(Color.white, 3));
		panelNamings.add(schoolLogo);

		btnUploadLogo = new JButton("Upload Logo");
		btnUploadLogo.setFont(new Font("Times New Roman", Font.BOLD, 16));
		btnUploadLogo.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				JFileChooser fc = new JFileChooser();
				fc.setCurrentDirectory(new File(System.getProperty("user.home")));
				FileNameExtensionFilter filter = new FileNameExtensionFilter("*.Images", "jpg", "gif", "png");
				fc.addChoosableFileFilter(filter);
				int result = fc.showSaveDialog(null);
				if (result == JFileChooser.APPROVE_OPTION) {
					File selectedShit = fc.getSelectedFile();
					String path = selectedShit.getAbsolutePath();
					schoolLogo.setIcon(ResizeImage(path));
					ss = path;
				}

				else if (result == JFileChooser.CANCEL_OPTION) {
					JOptionPane.showMessageDialog(null, "No Photo Was Selected", "Please select a photo",
							JOptionPane.INFORMATION_MESSAGE);
				}

			}
		});
		panelNamings.add(btnUploadLogo);

		btnSaveChangesSchoolInfo = new JButton("Save Changes");
		btnSaveChangesSchoolInfo.setFont(new Font("Times New Roman", Font.BOLD, 16));
		btnSaveChangesSchoolInfo.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				String sql = "insert into school_details(school_name,school_address,school_email,school_phone,school_logo,id) values(?,?,?,?,?,?) on duplicate key update "
						+ "school_name=values(school_name)," + "school_address=values(school_address),"
						+ "school_email=values(school_email)," + "school_phone=values(school_phone),"
						+ "school_logo=values(school_logo)";
				java.sql.Connection conn = null;
				java.sql.PreparedStatement pst = null;

				try {
					conn = CashBookController.getConnection();
					pst = conn.prepareStatement(sql);

					InputStream schoolLogo = new FileInputStream(new File(ss));

					pst.setString(1, fieldSchool.getText());
					pst.setString(2, fieldAddress.getText());
					pst.setString(3, fieldEmail.getText());
					pst.setString(4, fieldTel.getText());
					pst.setBlob(5, schoolLogo);
					pst.setString(6, "1");

					pst.executeUpdate();
					JOptionPane.showMessageDialog(null, "Successfully Changed School's Details");
				} catch (Exception e) {
					JOptionPane.showMessageDialog(null, "Not Successfully Changed School's Details");

					e.printStackTrace();
				}

			}
		});

		panelNamings.add(btnSaveChangesSchoolInfo);

		setVisible(true);
	}

	public JTextField getFieldSchool() {
		return fieldSchool;
	}

	public JTextField getFieldAddress() {
		return fieldAddress;
	}

	public JTextField getFieldEmail() {
		return fieldEmail;
	}

	public JTextField getFieldTel() {
		return fieldTel;
	}

	public ImageIcon ResizeImage(String ImagePath) {

		ImageIcon MyImg = new ImageIcon(ImagePath);
		Image img = MyImg.getImage();
		Image newimg = img.getScaledInstance(schoolLogo.getWidth(), schoolLogo.getHeight(), Image.SCALE_SMOOTH);
		ImageIcon image = new ImageIcon(newimg);

		return image;

	}

	public void AddClass(String querries) {

		java.sql.Connection conn = null;
		java.sql.PreparedStatement pst = null;

		try {

			conn = CashBookController.getConnection();
			pst = conn.prepareStatement(querries);

			pst.executeUpdate();

			JOptionPane.showMessageDialog(null, "Added Successfully");

		} catch (Exception ex) {
			ex.printStackTrace();
			JOptionPane.showMessageDialog(null, "Request Not Executed Successfully " + ex.getMessage());

		} finally {
			if (conn != null) {
				try {
					conn.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}

	public void AddClassTable(String querries) {

		java.sql.Connection conn = null;
		java.sql.PreparedStatement pst = null;

		try {

			conn = CashBookController.getConnection();
			pst = conn.prepareStatement(querries);

			pst.executeUpdate();

			JOptionPane.showMessageDialog(null, "Class Added Successfully");

		} catch (Exception ex) {
			ex.printStackTrace();
			JOptionPane.showMessageDialog(null, "Request Not Executed Successfully " + ex.getMessage());

		} finally {
			if (conn != null) {
				try {
					conn.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}

	public void AddSubject(String querries) {

		java.sql.Connection conn = null;
		java.sql.PreparedStatement pst = null;

		try {

			conn = CashBookController.getConnection();
			pst = conn.prepareStatement(querries);

			pst.executeUpdate();

			JOptionPane.showMessageDialog(null, "Subject Added Successfully");

		} catch (Exception ex) {
			ex.printStackTrace();
			JOptionPane.showMessageDialog(null, "Request Not Executed Successfully " + ex.getMessage());

		} finally {
			if (conn != null) {
				try {
					conn.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}

}
