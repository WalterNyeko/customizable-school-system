package school.ui.account;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Image;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Optional;

import javax.swing.AbstractButton;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JToolBar;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.TitledPane;
import javafx.scene.input.KeyCombination;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import school.ui.finances.CashBookController;
//import school.ui.main.LoginHelperClass;

public class SystemUsersPanel extends JPanel {

	public JTable tableUsers;
	private JScrollPane scrollPaneUsers;
	private JButton buttonLogout, buttonPrint;
	private JLabel labelUsers, printLabel, filterLabel, sortALabel, sortDLabel, refreshLabel, undoLabel, labelSave,
			labelSaveAll, labelCopy, labelCut, labelSettings, labelColor, labelAdd, labelExport, labelView, labelLogo,
			labelEdit, labelDelete, labelLeft, labelRight, labelCenter, labelBold, labelItalic, labelUnderline,
			labelFont, labelProfile;
	private JToolBar toolUsers;
	private JComboBox comboNew;
	protected TextField fieldItemReceived;
	private MouseAdapter listenForMouseClick;
	private Connection con;
	private boolean hasData;

	public SystemUsersPanel() {

		SystemUsers();
	}

	private void SystemUsers() {

		setLayout(new FlowLayout(FlowLayout.CENTER));
		setBorder(new LineBorder(Color.blue, 3));
		setPreferredSize(new Dimension(1160, 480));
		setBackground(Color.decode("#5f9ea0"));

		// buttonPrint=new JButton("Print");
		// add(buttonPrint);

		toolUsers = new JToolBar();
		toolUsers.setPreferredSize(new Dimension(870, 125));
		add(toolUsers);

		JPanel upper = new JPanel();
		upper.setLayout(new FlowLayout(FlowLayout.LEFT, 5, 15));
		upper.setPreferredSize(new Dimension(550, 125));

		toolUsers.add(upper);

		JPanel lower = new JPanel();
		lower.setPreferredSize(new Dimension(300, 125));
		lower.setLayout(new FlowLayout(FlowLayout.RIGHT));
		toolUsers.add(lower);

		printLabel = new JLabel("");
		toolUsers.addSeparator();
		printLabel.setToolTipText("Print Users");
		upper.add(printLabel);

		Image img1 = new ImageIcon(this.getClass().getResource("printer.png")).getImage();
		printLabel.setIcon(new ImageIcon(img1));

		undoLabel = new JLabel("");
		toolUsers.addSeparator();
		undoLabel.setToolTipText("Undo Action");
		upper.add(undoLabel);

		Image img2 = new ImageIcon(this.getClass().getResource("undo.png")).getImage();
		undoLabel.setIcon(new ImageIcon(img2));

		sortALabel = new JLabel("");
		toolUsers.addSeparator();
		sortALabel.setToolTipText("Sort Users Ascending");
		upper.add(sortALabel);

		Image img3 = new ImageIcon(this.getClass().getResource("sortd1.png")).getImage();
		sortALabel.setIcon(new ImageIcon(img3));

		sortDLabel = new JLabel("");
		toolUsers.addSeparator();
		sortDLabel.setToolTipText("Sort Users Descending");
		upper.add(sortDLabel);

		Image img4 = new ImageIcon(this.getClass().getResource("sortd.png")).getImage();
		sortDLabel.setIcon(new ImageIcon(img4));

		filterLabel = new JLabel("");
		toolUsers.addSeparator();
		filterLabel.setToolTipText("Filter Users");
		upper.add(filterLabel);

		Image img5 = new ImageIcon(this.getClass().getResource("filter.png")).getImage();
		filterLabel.setIcon(new ImageIcon(img5));

		refreshLabel = new JLabel("");
		toolUsers.addSeparator();
		refreshLabel.setToolTipText("Refresh Users");
		upper.add(refreshLabel);
		refreshLabel.addMouseListener(new MouseListener() {
			
			@Override
			public void mouseReleased(MouseEvent arg0) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mousePressed(MouseEvent arg0) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mouseExited(MouseEvent arg0) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mouseEntered(MouseEvent arg0) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mouseClicked(MouseEvent arg0) {
				// TODO Auto-generated method stub
				displayData(tableUsers, "select username, CONCAT(first_name,' ',last_name),'','','','','','',id from application_users");
			}
		});

		Image img6 = new ImageIcon(this.getClass().getResource("refresh.png")).getImage();
		refreshLabel.setIcon(new ImageIcon(img6));

		labelSave = new JLabel("");
		toolUsers.addSeparator();
		labelSave.setToolTipText("Save User");
		upper.add(labelSave);

		Image img7 = new ImageIcon(this.getClass().getResource("save.png")).getImage();
		labelSave.setIcon(new ImageIcon(img7));

		labelSaveAll = new JLabel("");
		toolUsers.addSeparator();
		labelSaveAll.setToolTipText("SaveAll User");
		upper.add(labelSaveAll);

		Image img8 = new ImageIcon(this.getClass().getResource("saveall.png")).getImage();
		labelSaveAll.setIcon(new ImageIcon(img8));

		labelCopy = new JLabel("");
		toolUsers.addSeparator();
		labelCopy.setToolTipText("Copy User");
		upper.add(labelCopy);

		Image img9 = new ImageIcon(this.getClass().getResource("copy.png")).getImage();
		labelCopy.setIcon(new ImageIcon(img9));

		labelCut = new JLabel("");
		toolUsers.addSeparator();
		labelCut.setToolTipText("Cut User");
		upper.add(labelCut);

		Image img10 = new ImageIcon(this.getClass().getResource("cut.png")).getImage();
		labelCut.setIcon(new ImageIcon(img10));

		labelAdd = new JLabel("");
		toolUsers.addSeparator();
		labelAdd.setToolTipText("Add New User");
		labelAdd.addMouseListener(new MouseListener() {

			@Override
			public void mouseReleased(MouseEvent arg0) {
				// TODO Auto-generated method stub

			}

			@Override
			public void mousePressed(MouseEvent arg0) {
				// TODO Auto-generated method stub

			}

			@Override
			public void mouseExited(MouseEvent arg0) {
				// TODO Auto-generated method stub

			}

			@Override
			public void mouseEntered(MouseEvent arg0) {
				// TODO Auto-generated method stub

			}

			@Override
			public void mouseClicked(MouseEvent arg0) {
				// TODO Auto-generated method stub

				// CreateUsers createUsers=new CreateUsers();
				Platform.runLater(new Runnable() {

					private Stage stage;

					@Override
					public void run() {
						// TODO Auto-generated method stub

						TitledPane paneReceived = new TitledPane();

						GridPane GpaneReceived = new GridPane();
						GpaneReceived.setVgap(10);
						GpaneReceived.setHgap(10);
						GpaneReceived.setPadding(new Insets(10, 10, 10, 10));
						GpaneReceived.add(new Label("Date of Creation"), 0, 0);
						DatePicker dateReceived = new DatePicker();
						dateReceived.getValue();
						GpaneReceived.add(dateReceived, 1, 0);

						GpaneReceived.add(new Label("First Name"), 2, 0);

						TextField fieldQtyReceived = new TextField();
						fieldQtyReceived.setPromptText("E.g Robert");
						GpaneReceived.add(fieldQtyReceived, 3, 0);

						GpaneReceived.add(new Label("Last Name"), 0, 1);

						fieldItemReceived = new TextField();
						fieldItemReceived.setPromptText("E.g Obina");
						GpaneReceived.add(fieldItemReceived, 1, 1);

						Label labelOtherNames = new Label("Other Names");
						// labelOtherNames.setPrefWidth(200);
						GpaneReceived.add(labelOtherNames, 2, 1);
						TextField fieldStockCardNumberReceived = new TextField();
						fieldStockCardNumberReceived.setPromptText("E.g P'Adong");
						GpaneReceived.add(fieldStockCardNumberReceived, 3, 1);

						GpaneReceived.add(new Label("User Name"), 0, 2);
						TextField fieldDeliveryNoteReceived = new TextField();
						fieldDeliveryNoteReceived.setPromptText("E.g Walter Nyeko");
						GpaneReceived.add(fieldDeliveryNoteReceived, 1, 2);

						GpaneReceived.add(new Label("Password"), 2, 2);
						PasswordField fieldReceiverReceived = new PasswordField();
						fieldReceiverReceived.setPromptText("E.g robbie!2!%25");
						GpaneReceived.add(fieldReceiverReceived, 3, 2);

						GpaneReceived.add(new Label("Re-type Password"), 0, 3);
						PasswordField fieldRemarksReceived = new PasswordField();
						fieldRemarksReceived.setPromptText("Repeat the password");
						GpaneReceived.add(fieldRemarksReceived, 1, 3);

						Button btnEnterReceived = new Button("Create Account");
						btnEnterReceived.setOnAction(new EventHandler<ActionEvent>() {

							@Override
							public void handle(ActionEvent arg0) {
								// TODO Auto-generated method stub
								
								if(!fieldRemarksReceived.getText().equals(fieldReceiverReceived.getText())) {
									JOptionPane.showMessageDialog(null, "Password Missmatch");
								}else {
									AddUpdateDeleteUsers(
											"insert into application_users(date,first_name,last_name,other_name,username,password) values('"
													+ dateReceived.getValue() + "','" + fieldQtyReceived.getText() + "','"
													+ fieldItemReceived.getText() + "','"
													+ fieldStockCardNumberReceived.getText() + "','"+fieldDeliveryNoteReceived.getText()+"','"+fieldReceiverReceived.getText()+"')");
									stage.hide();
									

								}

								
							}
						});
						btnEnterReceived.setPrefWidth(150);
						GpaneReceived.add(btnEnterReceived, 2, 3);

						Button btnClearReceived = new Button("Cancel");
						btnClearReceived.setPrefWidth(200);
						GpaneReceived.add(btnClearReceived, 3, 3);
						paneReceived.setText("Create Application Users Accounts");
						paneReceived.setPadding(new Insets(10, 10, 10, 10));
						paneReceived.setContent(GpaneReceived);

						btnClearReceived.setOnAction(event -> {
							Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
							alert.initStyle(StageStyle.TRANSPARENT);
							alert.initModality(Modality.WINDOW_MODAL);
							alert.setContentText("Are you sure to exit?");
							Optional<ButtonType> result = alert.showAndWait();
							if (result.get() == ButtonType.OK) {
								Platform.exit();
							}
						});

						VBox box = new VBox();
						box.getChildren().add(paneReceived);
						final Scene scene = new Scene(box);
						scene.setFill(null);
						stage = new Stage();
						stage.setScene(scene);
						stage.setTitle("Add Application Users");
						stage.setFullScreenExitHint("");
						stage.setFullScreenExitKeyCombination(KeyCombination.NO_MATCH);
						stage.setFullScreen(false);
						stage.show();
					}
				});
			}
		});
		upper.add(labelAdd);

		Image img11 = new ImageIcon(this.getClass().getResource("new.png")).getImage();
		labelAdd.setIcon(new ImageIcon(img11));

		labelSettings = new JLabel("");
		toolUsers.addSeparator();
		labelSettings.setToolTipText("Settings ");
		upper.add(labelSettings);

		Image img12 = new ImageIcon(this.getClass().getResource("settings.png")).getImage();
		labelSettings.setIcon(new ImageIcon(img12));

		labelExport = new JLabel("");
		toolUsers.addSeparator();
		labelExport.setToolTipText("Exports");
		upper.add(labelExport);

		Image img14 = new ImageIcon(this.getClass().getResource("export.png")).getImage();
		labelExport.setIcon(new ImageIcon(img14));

		labelView = new JLabel("");
		toolUsers.addSeparator();
		labelView.setToolTipText("View User");
		upper.add(labelView);

		Image img15 = new ImageIcon(this.getClass().getResource("view.png")).getImage();
		labelView.setIcon(new ImageIcon(img15));

		labelEdit = new JLabel("");
		toolUsers.addSeparator();
		labelEdit.setToolTipText("Edit User");
//		labelEdit.addMouseListener(new MouseListener() {
//
//			@Override
//			public void mouseReleased(MouseEvent arg0) {
//				// TODO Auto-generated method stub
//
//			}
//
//			@Override
//			public void mousePressed(MouseEvent arg0) {
//				// TODO Auto-generated method stub
//
//			}
//
//			@Override
//			public void mouseExited(MouseEvent arg0) {
//				// TODO Auto-generated method stub
//
//			}
//
//			@Override
//			public void mouseEntered(MouseEvent arg0) {
//				// TODO Auto-generated method stub
//
//			}
//
//			@Override
//			public void mouseClicked(MouseEvent arg0) {
//				// TODO Auto-generated method stub
//
//				// CreateUsers createUsers=new CreateUsers();
//				Platform.runLater(new Runnable() {
//
//					private Stage stage;
//					private TextField fieldHost;
//					private TextField fieldUser;
//					private PasswordField fieldPassword;
//
//					@Override
//					public void run() {
//						// TODO Auto-generated method stub
//
//						TitledPane paneReceived = new TitledPane();
//
//						GridPane GpaneReceived = new GridPane();
//						GpaneReceived.setVgap(10);
//						GpaneReceived.setHgap(10);
//						GpaneReceived.setPadding(new Insets(10, 10, 10, 10));
//						GpaneReceived.add(new Label("Date of Creation"), 0, 0);
//						DatePicker dateReceived = new DatePicker();
//						dateReceived.getValue();
//						GpaneReceived.add(dateReceived, 1, 0);
//
//						GpaneReceived.add(new Label("Database Host Name:"), 2, 0);
//
//						fieldHost = new TextField();
//						fieldHost.setPromptText("E.g mysql:jdbc://192.168.43.29");
//						GpaneReceived.add(fieldHost, 3, 0);
//
//						GpaneReceived.add(new Label("Host's Port Number:"), 0, 1);
//
//						fieldItemReceived = new TextField();
//						fieldItemReceived.setPromptText("Default is 3306");
//						fieldItemReceived.setEditable(false);
//						GpaneReceived.add(fieldItemReceived, 1, 1);
//
//						Label labelOtherNames = new Label("Database User Name:");
//						GpaneReceived.add(labelOtherNames, 2, 1);
//						fieldUser = new TextField();
//						fieldUser.setPromptText("E.g P'Adong");
//						GpaneReceived.add(fieldUser, 3, 1);
//
//						GpaneReceived.add(new Label("Database Password:"), 0, 2);
//
//						fieldPassword = new PasswordField();
//						fieldPassword.setPromptText("E.g robbie!2!%25");
//						GpaneReceived.add(fieldPassword, 1, 2);
//
//
//						Button btnEnterReceived = new Button("Save Changes");
//						btnEnterReceived.setOnAction(new EventHandler<ActionEvent>() {
//
//
//							@Override
//							public void handle(ActionEvent arg0) {
//								// TODO Auto-generated method stub
////								if (con == null) {
////									try {
//////										new LoginHelperClass().getConnection();
////									} catch (ClassNotFoundException e) {
////										// TODO Auto-generated catch block
////										e.printStackTrace();
////									} catch (SQLException e) {
////										// TODO Auto-generated catch block
////										e.printStackTrace();
////									}
////								}
//
//								
//									/////////////////////Checking Table dbUsers
//									Statement statement = null;
//									ResultSet resultOfExistenceOfTable = null;
//									
//									try {
//										statement = con.createStatement();
//										resultOfExistenceOfTable = statement
//												.executeQuery("SELECT name from sqlite_master WHERE type='table' AND name='dbUsers'");
//									} catch (SQLException e) {
//										// TODO Auto-generated catch block
//										e.printStackTrace();
//									}
//									
//									
//									try {
//										while (resultOfExistenceOfTable.next()) {
//											
//											
//												PreparedStatement state;
//												try {
//													state = con
//															.prepareStatement("update dbUsers set dbhost=?,dbusername=?,dbpassword=?");
//													state.setString(1, fieldHost.getText());
//													state.setString(2, fieldUser.getText());
//													state.setString(3, fieldPassword.getText());
//													state.execute();
//												} catch (SQLException e) {
//													// TODO Auto-generated catch block
//													e.printStackTrace();
//												}
//												
//											
//
//										}
//									} catch (SQLException e) {
//										// TODO Auto-generated catch block
//										e.printStackTrace();
//									}
//
//									
//
//									
//									
//									stage.hide();
//									
//
//
//								
//							}
//						});
//						btnEnterReceived.setPrefWidth(150);
//						GpaneReceived.add(btnEnterReceived, 2, 3);
//
//						Button btnClearReceived = new Button("Cancel");
//						btnClearReceived.setPrefWidth(200);
//						GpaneReceived.add(btnClearReceived, 3, 3);
//						paneReceived.setText("Create Application Users Accounts");
//						paneReceived.setPadding(new Insets(10, 10, 10, 10));
//						paneReceived.setContent(GpaneReceived);
//
//						btnClearReceived.setOnAction(event -> {
//							Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
//							alert.initStyle(StageStyle.TRANSPARENT);
//							alert.initModality(Modality.WINDOW_MODAL);
//							Optional<ButtonType> result = alert.showAndWait();
//							if (result.get() == ButtonType.OK) {
//								Platform.exit();
//							}
//						});
//
//						VBox box = new VBox();
//						box.getChildren().add(paneReceived);
//						final Scene scene = new Scene(box);
//						scene.setFill(null);
//						stage = new Stage();
//						stage.setScene(scene);
//						stage.setTitle("Edit Database Users Credentials");
//						stage.setFullScreenExitHint("");
//						stage.setFullScreenExitKeyCombination(KeyCombination.NO_MATCH);
//						stage.setFullScreen(false);
//						stage.show();
//					}
//				});
//			}
//		});
		upper.add(labelEdit);

		Image img16 = new ImageIcon(this.getClass().getResource("edit.png")).getImage();
		labelEdit.setIcon(new ImageIcon(img16));

		labelDelete = new JLabel("");
		toolUsers.addSeparator();
		labelDelete.setToolTipText("Delete User");
		upper.add(labelDelete);

		listenForMouseClick = new MouseAdapter() {

			@Override
			public void mouseClicked(MouseEvent e) {

				int row = tableUsers.getSelectedRow();
				String username = tableUsers.getValueAt(row, 0).toString();

				AddUpdateDelete("delete from application_users where username='" + username + "'");
				displayData(tableUsers, "select username, CONCAT(first_name,' ',last_name),'','','','','','',id from application_users");

			}
		};

		Image img17 = new ImageIcon(this.getClass().getResource("delete.png")).getImage();
		labelDelete.setIcon(new ImageIcon(img17));
		labelDelete.addMouseListener(new MouseListener() {
			
			@Override
			public void mouseReleased(MouseEvent arg0) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mousePressed(MouseEvent arg0) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mouseExited(MouseEvent arg0) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mouseEntered(MouseEvent arg0) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mouseClicked(MouseEvent arg0) {
				// TODO Auto-generated method stub
				
				int row=tableUsers.getSelectedRow();
				
				String id=tableUsers.getValueAt(row, 8).toString();
				
				DeleteUser("delete from application_users where id='"+id+"'");
			}
		});

		//
		// labelLeft = new JLabel("");
		// toolUsers.addSeparator();
		// labelLeft.setToolTipText("Left Align");
		// upper.add(labelLeft);
		//
		// Image img18 = new
		// ImageIcon(this.getClass().getResource("left.png")).getImage();
		// labelLeft.setIcon(new ImageIcon(img18));
		//
		//
		// labelCenter = new JLabel("");
		// toolUsers.addSeparator();
		// labelCenter.setToolTipText("Center Align");
		// upper.add(labelCenter);
		//
		// Image img19 = new
		// ImageIcon(this.getClass().getResource("center.png")).getImage();
		// labelCenter.setIcon(new ImageIcon(img19));
		//
		// labelRight = new JLabel("");
		// toolUsers.addSeparator();
		// labelRight.setToolTipText("Right Align");
		// upper.add(labelRight);
		//
		// Image img20 = new
		// ImageIcon(this.getClass().getResource("right.png")).getImage();
		// labelRight.setIcon(new ImageIcon(img20));
		//
		// labelBold = new JLabel("");
		// toolUsers.addSeparator();
		// labelBold.setToolTipText("Bold");
		// upper.add(labelBold);
		//
		// Image img21 = new
		// ImageIcon(this.getClass().getResource("bold.png")).getImage();
		// labelBold.setIcon(new ImageIcon(img21));
		//
		// labelItalic = new JLabel("");
		// toolUsers.addSeparator();
		// labelItalic.setToolTipText("Italicize");
		// upper.add(labelItalic);
		//
		// Image img22 = new
		// ImageIcon(this.getClass().getResource("italic.png")).getImage();
		// labelItalic.setIcon(new ImageIcon(img22));
		//
		//
		// labelUnderline = new JLabel("");
		// toolUsers.addSeparator();
		// labelUnderline.setToolTipText("Underline");
		// upper.add(labelUnderline);
		//
		// Image img23 = new
		// ImageIcon(this.getClass().getResource("underline.png")).getImage();
		// labelUnderline.setIcon(new ImageIcon(img23));
		//
		// labelFont = new JLabel("");
		// toolUsers.addSeparator();
		// labelFont.setToolTipText("Fonts");
		// upper.add(labelFont);
		//
		// Image img24 = new
		// ImageIcon(this.getClass().getResource("font.png")).getImage();
		// labelFont.setIcon(new ImageIcon(img24));
		//
		//
		// labelProfile = new JLabel("");
		// toolUsers.addSeparator();
		// labelProfile.setToolTipText("Change Profile Picture");
		// upper.add(labelProfile);
		//
		// Image img25 = new
		// ImageIcon(this.getClass().getResource("profile.png")).getImage();
		// labelProfile.setIcon(new ImageIcon(img25));
		//
		// labelLogo = new JLabel("");
		// toolUsers.addSeparator();
		// labelLogo.setPreferredSize(new Dimension(150, 125));
		// labelLogo.setToolTipText("School Logo Picture");
		// lower.add(labelLogo);
		//
		// Image img26 = new
		// ImageIcon(this.getClass().getResource("logo1.png")).getImage();
		// labelLogo.setIcon(new ImageIcon(img26));
		//

		labelUsers = new JLabel("User Picture");
		labelUsers.setPreferredSize(new Dimension(120, 125));
		labelUsers.setBorder(new LineBorder(Color.RED, 2));
		add(labelUsers);

		String[][] dataS1 = new String[][] {

				{ null, null, null, null, null, null, null, null, null },
				{ null, null, null, null, null, null, null, null, null },
				{ null, null, null, null, null, null, null, null, null },
				{ null, null, null, null, null, null, null, null, null },
				{ null, null, null, null, null, null, null, null, null },
				{ null, null, null, null, null, null, null, null, null },
				{ null, null, null, null, null, null, null, null, null },
				{ null, null, null, null, null, null, null, null, null },
				{ null, null, null, null, null, null, null, null, null },
				{ null, null, null, null, null, null, null, null, null },
				{ null, null, null, null, null, null, null, null, null },
				{ null, null, null, null, null, null, null, null, null },
				{ null, null, null, null, null, null, null, null, null },
				{ null, null, null, null, null, null, null, null, null },
				{ null, null, null, null, null, null, null, null, null },
				{ null, null, null, null, null, null, null, null, null },
				{ null, null, null, null, null, null, null, null, null },
				{ null, null, null, null, null, null, null, null, null },
				{ null, null, null, null, null, null, null, null, null },
				{ null, null, null, null, null, null, null, null, null },
				{ null, null, null, null, null, null, null, null, null },
				{ null, null, null, null, null, null, null, null, null },
				{ null, null, null, null, null, null, null, null, null },
				{ null, null, null, null, null, null, null, null, null },
				{ null, null, null, null, null, null, null, null, null },
				{ null, null, null, null, null, null, null, null, null },
				{ null, null, null, null, null, null, null, null, null }

		};

		String[] headingS1 = new String[] { "User Name", "Full Name", "Staff Category", "Sex", "Active",
				"Authorization", "Contact", "Email", "ID Number" };

		DefaultTableModel defaultTableModel = new DefaultTableModel();
		defaultTableModel.setDataVector(dataS1, headingS1);

		tableUsers = new JTable(defaultTableModel);
		tableUsers.setShowGrid(false);
		JTableHeader headerUNSAItems = tableUsers.getTableHeader();
		// headerUNSAItems.setBackground(Color.BLACK);
		headerUNSAItems.setForeground(Color.white);
		headerUNSAItems.setPreferredSize(new Dimension(1170, 30));
		JScrollPane scrollPaneUsers = new JScrollPane(tableUsers);
		tableUsers.setRowHeight(30);
		scrollPaneUsers.setPreferredSize(new Dimension(1000, 320));
		Border bodausers = BorderFactory.createRaisedBevelBorder();
		scrollPaneUsers.setBorder(bodausers);

		add(scrollPaneUsers);

		tableUsers.setShowGrid(false);
		tableUsers.getColumnModel().getColumn(0).setPreferredWidth(235);
		tableUsers.getColumnModel().getColumn(1).setPreferredWidth(85);
		tableUsers.getColumnModel().getColumn(2).setPreferredWidth(100);
		tableUsers.getColumnModel().getColumn(3).setPreferredWidth(50);
		tableUsers.getColumnModel().getColumn(4).setPreferredWidth(50);
		tableUsers.getColumnModel().getColumn(5).setPreferredWidth(85);
		tableUsers.getColumnModel().getColumn(6).setPreferredWidth(85);
		tableUsers.getColumnModel().getColumn(7).setPreferredWidth(230);
		tableUsers.getColumnModel().getColumn(8).setPreferredWidth(80);

		setVisible(true);
		displayData(tableUsers, "select username, CONCAT(first_name,' ',last_name),'','','','','','',id from application_users");
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
	
	public void AddUpdateDeleteUsers(String querries) {

		try {

			java.sql.Connection conn = null;
			java.sql.PreparedStatement pst = null;
			conn = CashBookController.getConnection();
			pst = conn.prepareStatement(querries);

			pst.executeUpdate();

//			JOptionPane.showMessageDialog(null, "Request Executed Successfully");

		} catch (Exception ex) {
			ex.printStackTrace();
			JOptionPane.showMessageDialog(null, "Request Not Executed Successfully " + ex.getMessage());

		}
	}
	
	
	public void DeleteUser(String querries) {

		try {

			java.sql.Connection conn = null;
			java.sql.PreparedStatement pst = null;
			conn = CashBookController.getConnection();
			pst = conn.prepareStatement(querries);
			
			String[] options = { "Delete User", "Cancel" };
			int ans = JOptionPane.showOptionDialog(null, "Are You Sure You Want to Delete the selected User???",
					"Confirmation of Delete Request", JOptionPane.YES_NO_CANCEL_OPTION,
					JOptionPane.QUESTION_MESSAGE, null, options, options[1]);
			if (ans == 0) {

				pst.executeUpdate();
				JOptionPane.showMessageDialog(null, "User Deleted Successfully");
			} else {

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

			e.printStackTrace();
		}

	}
	
	

		

}
