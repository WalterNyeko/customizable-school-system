package school.ui.reports;

import java.awt.Dimension;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import org.json.JSONArray;
import org.json.JSONObject;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.embed.swing.JFXPanel;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.TitledPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.web.HTMLEditor;
import javafx.util.Duration;
import school.ui.finances.CashBookController;
import school.ui.tests.AfricasTalkingGateway;

public class NotificationsAlerts extends JPanel {

	/////////// Email Stuffs////////////////

	private String subject;
	private String receiver;
	private String carboncopy;
	private String bodyofmessage;

	private static final String SMTP_HOST_NAME = "smtp.gmail.com";
	private static final int SMTP_HOST_PORT = 465;
	private static final String SMTP_AUTH_USER = "nyekowalter69@gmail.com";
	private static final String SMTP_AUTH_PWD = "CATRINAH";

	private JFXPanel fxpanel;
	protected ObservableList<Object> classEmail;
	private Connection conn;
	private PreparedStatement pst;
	protected ObservableList<Object> classSMS;
	protected ObservableList<Object> classNotice;
	protected HTMLEditor htmlEditor;
	protected TextField receiverField;
	protected TextField fieldCcEmail;
	protected TextField subjectField;
	protected TextField fieldSms;
	protected HTMLEditor htmlEditorSMS;
	private StringBuffer finalSMSBody;
	public ObservableList<Object> usersNotice;
	public Label userName;
	public ComboBox comboNoticeUsers;
	protected HTMLEditor htmlEditorNotice;
	protected TextArea areaNotice;

	public static void main(String[] args) {

		new NotificationsAlerts();
	}

	public NotificationsAlerts() {

		Platform.runLater(new Runnable() {
			@Override
			public void run() {
				// --- GridPane container
				TitledPane gridTitlePane = new TitledPane();
				GridPane grid = new GridPane();
				grid.setVgap(4);
				grid.setAlignment(Pos.CENTER);
				grid.setPadding(new Insets(5, 5, 5, 5));

				grid.add(new Label("To: "), 0, 0);

				receiverField = new TextField("nyekowalter69@gmail.com");
				receiverField.setEditable(false);
				grid.add(receiverField, 1, 0);

				grid.add(new Label("Cc: "), 0, 1);

				fieldCcEmail = new TextField();
				// fieldCcEmail.setEditable(false);
				grid.add(fieldCcEmail, 1, 1);
				grid.add(new Label("Subject: "), 0, 2);

				subjectField = new TextField();
				grid.add(subjectField, 1, 2);

				grid.add(new Label("Body: "), 0, 3);
				grid.add(new Label("Choose Class: "), 0, 4);

				Button sendEmail = new Button("Send Email");
				grid.add(sendEmail, 2, 4);
				sendEmail.setOnAction(new EventHandler<ActionEvent>() {

					@Override
					public void handle(ActionEvent arg0) {
						// TODO Auto-generated method stub
						if (!validateFields()) {
							return;
						}

						receiver = receiverField.getText();

						String BodyOfEmail = htmlEditor.getHtmlText();
						stripHTMLTags(BodyOfEmail);
						bodyofmessage = finalSMSBody.toString().trim();
						subject = subjectField.getText();
						carboncopy = fieldCcEmail.getText();

						try {
							test();

							JOptionPane.showMessageDialog(null, "The Selected Recepients Received The Email",
									"E-mail sent successfully!", JOptionPane.INFORMATION_MESSAGE);

						} catch (Exception ex) {
							// JOptionPane.showMessageDialog(this, "The System Experienced An Error While
							// Sending The Message: ");
							ex.printStackTrace();
						}
					}
				});

				// add your init here

				htmlEditor = new HTMLEditor();
				htmlEditor.setPrefHeight(245);
				htmlEditor.setPrefWidth(900);

				grid.add(htmlEditor, 1, 3);
				gridTitlePane.setText("Send Email Alerts ");
				gridTitlePane.setContent(grid);

				classEmail = FXCollections.observableArrayList();

				ComboBox combo = new ComboBox(classEmail);
				combo.setPrefWidth(250);
				combo.setPromptText("Choose Class");
				grid.add(combo, 1, 4);

				displayInComboBoxEmail(combo, "select class_name from student_classes");
				combo.setOnAction(new EventHandler<Event>() {

					@Override
					public void handle(Event arg0) {
						// TODO Auto-generated method stub
						displayParentsEmails(fieldCcEmail,
								"select GROUP_CONCAT(parent_email) from parents_info where student_class='"
										+ combo.getSelectionModel().getSelectedItem() + "'");
					}
				});

				TitledPane gridTitlePaneSMS = new TitledPane();
				GridPane gridSMS = new GridPane();
				gridSMS.setVgap(4);
				gridSMS.setAlignment(Pos.CENTER);
				gridSMS.setPadding(new Insets(5, 5, 5, 5));
				gridSMS.add(new Label("To: "), 0, 0);

				fieldSms = new TextField();

				gridSMS.add(fieldSms, 1, 0);
				gridSMS.add(new Label("Body: "), 0, 1);
				gridSMS.add(new Label("Choose Class: "), 0, 2);

				Button sendSMS = new Button("Send SMS");
				gridSMS.add(sendSMS, 2, 2);

				htmlEditorSMS = new HTMLEditor();
				htmlEditorSMS.setPrefHeight(245);
				htmlEditorSMS.setPrefWidth(900);
				gridSMS.add(htmlEditorSMS, 1, 1);
				gridTitlePaneSMS.setText("Send SMS Alerts ");
				gridTitlePaneSMS.setContent(gridSMS);

				classSMS = FXCollections.observableArrayList();
				ComboBox comboSMS = new ComboBox(classSMS);
				comboSMS.setPrefWidth(250);
				comboSMS.setPromptText("Choose Class");
				gridSMS.add(comboSMS, 1, 2);
				displayInComboBoxSMS(comboSMS, "select class_name from student_classes");
				comboSMS.setOnAction(new EventHandler<Event>() {

					@Override
					public void handle(Event arg0) {
						// TODO Auto-generated method stub
						displayFathersContacts(fieldSms,
								"select GROUP_CONCAT(fathers_phone) from parents_info where student_class='"
										+ comboSMS.getSelectionModel().getSelectedItem()
										+ "' and fathers_phone is not null");
						displayMothersContacts(fieldSms,
								"select GROUP_CONCAT(mothers_phone) from parents_info where student_class='"
										+ comboSMS.getSelectionModel().getSelectedItem()
										+ "' and mothers_phone is not null");
						displayGuardiansContacts(fieldSms,
								"select GROUP_CONCAT(guardians_phone) from parents_info where student_class='"
										+ comboSMS.getSelectionModel().getSelectedItem()
										+ "' and guardianss_phone is not null");
						displaySponsorsContacts(fieldSms,
								"select GROUP_CONCAT(sponsors_phone) from parents_info where student_class='"
										+ comboSMS.getSelectionModel().getSelectedItem()
										+ "' and sponsors_phone is not null");
					}
				});

				TitledPane gridTitlePaneNotice = new TitledPane();
				GridPane gridNotice = new GridPane();
				gridNotice.setVgap(4);
				gridNotice.setAlignment(Pos.CENTER);
				gridNotice.setPadding(new Insets(5, 5, 5, 5));

				usersNotice = FXCollections.observableArrayList();
				comboNoticeUsers = new ComboBox(usersNotice);
				comboNoticeUsers.setPromptText("Choose User");
				displayInComboBoxNoticeUsers(comboNoticeUsers, "select username from application_users");

				Button sendMEssage = new Button("Send Now");
				sendMEssage.setDefaultButton(true);
				htmlEditorNotice = new HTMLEditor();
				htmlEditorNotice.setPrefHeight(245);
				htmlEditorNotice.setPrefWidth(600);

				gridNotice.add(comboNoticeUsers, 0, 0);
				gridNotice.add(htmlEditorNotice, 0, 1);
				gridNotice.add(sendMEssage, 0, 2);

				gridTitlePaneNotice.setText("Send Instant Messages To System Users");

				HBox hBoxNotice = new HBox(5);
				hBoxNotice.setAlignment(Pos.CENTER);

				HBox hBoxNoticeRight = new HBox(5);
				hBoxNoticeRight.setAlignment(Pos.CENTER);

				hBoxNotice.getChildren().addAll(gridNotice, hBoxNoticeRight);

				gridTitlePaneNotice.setContent(hBoxNotice);

				areaNotice = new TextArea();
				areaNotice.setWrapText(true);
				areaNotice.setPrefWidth(550);
				// areaNotice.setPromptText("Choose Class");
				hBoxNoticeRight.getChildren().add(areaNotice);
				areaNotice.layout();
				ScrollPane scrollPane=new ScrollPane(areaNotice);
				scrollPane.setVvalue(1.0d);

				VBox hbox = new VBox(10);
				hbox.setPadding(new Insets(20, 10, 10, 20));
				gridTitlePane.setExpanded(false);
				gridTitlePaneSMS.setExpanded(false);
				gridTitlePaneNotice.setExpanded(false);

				gridTitlePane.setPrefWidth(800);
				gridTitlePaneSMS.setPrefWidth(800);
				gridTitlePaneNotice.setPrefWidth(800);
				areaNotice.setEditable(false);
				areaNotice.setScrollTop(1);

				hbox.setPrefSize(1170, 490);
				userName = new Label();
				hbox.getChildren().setAll(gridTitlePane, gridTitlePaneSMS, gridTitlePaneNotice, userName);

				Scene scene = new Scene(hbox);
				fxpanel = new JFXPanel();
				fxpanel.setScene(scene);

				areaNotice.setFont(new Font("Times New Roman", 16));

				String styleSheet = getClass().getResource("attendance.css").toExternalForm();
				scene.getStylesheets().add(styleSheet);

				sendSMS.setOnAction(new EventHandler<ActionEvent>() {

					@Override
					public void handle(ActionEvent arg0) {
						// TODO Auto-generated method stub
						// Specify your login credentials

						String username = "SoftEarthTechnologies";
						String apiKey = "95612f4a153df664312747c3c50cb3d14c015e8144d9bf2cbdb6f640b54e37b4";///// SoftEarthTechnologies

						// String username = "NyekoWalter";
						// String apiKey =
						// "f9457aeb09835b258b7ec8a300e401fa0f3b34ced7ee436b5e4d353e874f3664";/////NyekoWalter

						// Specify the numbers that you want to send to in a comma-separated list
						// Please ensure you include the country code (+254 for Kenya in this case)
						String recipients = fieldSms.getText();
						// And of course we want our recipients to know what we really do
						String htmlTextBody = htmlEditorSMS.getHtmlText();
						stripHTMLTags(htmlTextBody);
						String message = finalSMSBody.toString().trim();
//						String from = "Layibi";
						// Create a new instance of our awesome gateway class
						AfricasTalkingGateway gateway = new AfricasTalkingGateway(username, apiKey);
						/*************************************************************************************
						 * NOTE: If connecting to the sandbox: 1. Use "sandbox" as the username 2. Use
						 * the apiKey generated from your sandbox application
						 * https://account.africastalking.com/apps/sandbox/settings/key 3. Add the
						 * "sandbox" flag to the constructor AfricasTalkingGateway gateway = new
						 * AfricasTalkingGateway(username, apiKey, "sandbox");
						 **************************************************************************************/
						// Thats it, hit send and we'll take care of the rest. Any errors will
						// be captured in the Exception class below
						try {
							JSONArray results = gateway.sendMessage(recipients, message);
							for (int i = 0; i < results.length(); ++i) {
								JSONObject result = results.getJSONObject(i);
								System.out.print(result.getString("status") + ","); // status is either "Success" or
																					// "error message"
								System.out.print(result.getString("number") + ",");
								System.out.print(result.getString("messageId") + ",");
								System.out.println(result.getString("cost"));
							}
							JOptionPane.showMessageDialog(null, "Message(s) successfully sent to the selected recipient(s)");
						} catch (Exception e) {
							System.out.println("Encountered an error while sending " + e.getMessage());
						}
					}
				});

				sendMEssage.setOnAction(new EventHandler<ActionEvent>() {

					@Override
					public void handle(ActionEvent arg0) {
						// TODO Auto-generated method stub

						submitMessage();

					}
				});
				fxpanel.setPreferredSize(new Dimension(1160, 480));
				add(fxpanel);

				gridTitlePaneNotice.expandedProperty().addListener(new ChangeListener<Boolean>() {

					private Timeline timeline;

					@Override
					public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue,
							Boolean newValue) {
						// TODO Auto-generated method stub
						if (newValue) {
							timeline = new Timeline(new KeyFrame(Duration.seconds(0.5), evt -> {
								
								checkAvailabilityOfMessagesBeforeDisplayingTheMessage();

								readMessages();

							}), new KeyFrame(Duration.seconds(5), evt -> {
							
								checkAvailabilityOfMessagesBeforeDisplayingTheMessage();
								
								readMessages();

							}));
							timeline.setCycleCount(Animation.INDEFINITE);
							timeline.play();

						} else if (oldValue) {
							timeline.stop();
							System.out.println("Timer Stopped Running");
						}
					}
				});

			}
		});

	}

	public void displayMessages() {
		String sql = "select * from chat_messenger where (message_sender='" + userName.getText()
				+ "' and message_receiver='" + comboNoticeUsers.getSelectionModel().getSelectedItem()
				+ "') or (message_receiver='" + userName.getText() + "' and message_sender='"
				+ comboNoticeUsers.getSelectionModel().getSelectedItem() + "') order by id DESC";

		java.sql.Connection conn = null;
		ResultSet rs = null;
		java.sql.PreparedStatement pst = null;

		try {

			conn = CashBookController.getConnection();
			pst = conn.prepareStatement(sql);
			rs = pst.executeQuery();
			areaNotice.setText(null);

			while (rs.next()) {

				areaNotice.appendText(
						"  " + "<" + rs.getString(6) + ">" + " " + rs.getString(3) + ": " + rs.getString(2) + "\n");

			}

		} catch (Exception e1) {

		} finally {
			if (conn != null) {
				try {
					conn.close();
				} catch (Exception e2) {

				}
			}
		}
	}
	
	
	public void checkAvailabilityOfMessagesBeforeDisplayingTheMessage() {
		String sql = "select * from chat_messenger where (message_sender='" + userName.getText()
				+ "' and message_receiver='" + comboNoticeUsers.getSelectionModel().getSelectedItem()
				+ "') or (message_receiver='" + userName.getText() + "' and message_sender='"
				+ comboNoticeUsers.getSelectionModel().getSelectedItem() + "') and read_by is null order by id DESC";

		java.sql.Connection conn = null;
		ResultSet rs = null;
		java.sql.PreparedStatement pst = null;

		try {

			conn = CashBookController.getConnection();
			pst = conn.prepareStatement(sql);
			rs = pst.executeQuery();
			areaNotice.setText(null);

			if (rs.next()) {

				displayMessages();
			}else {
			
				System.out.println("No Message Yet");
			}

		} catch (Exception e1) {

		} finally {
			if (conn != null) {
				try {
					conn.close();
				} catch (Exception e2) {

				}
			}
		}
	}

	public void readMessages() {

		java.sql.Connection conn = null;
		java.sql.PreparedStatement pst = null;

		try {

			conn = CashBookController.getConnection();
			pst = conn.prepareStatement("update chat_messenger set read_by='" + userName.getText()
					+ "' where message_receiver='" + userName.getText() + "' and message_sender='"
					+ comboNoticeUsers.getSelectionModel().getSelectedItem() + "'");
			pst.executeUpdate();

		} catch (Exception e1) {

		} finally {
			if (conn != null) {
				try {
					conn.close();
				} catch (Exception e2) {

				}
			}
		}
	}

	public void submitMessage() {

		String htmlTextBody = htmlEditorNotice.getHtmlText();
		stripHTMLTags(htmlTextBody);
		String messageBefore = finalSMSBody.toString().trim();
		String msgAfter = messageBefore.replaceAll("'", "''");
		String sql = "insert into chat_messenger(message_body,message_sender,message_receiver) values ('" + msgAfter
				+ "','" + userName.getText() + "','" + comboNoticeUsers.getSelectionModel().getSelectedItem() + "')";

		java.sql.Connection conn = null;

		java.sql.PreparedStatement pst = null;

		try {

			conn = CashBookController.getConnection();
			pst = conn.prepareStatement(sql);

			pst.executeUpdate();

		} catch (Exception e1) {
			e1.printStackTrace();

		} finally {
			if (conn != null) {
				try {
					conn.close();
				} catch (Exception e2) {

				}
			}
		}

		htmlEditorNotice.setHtmlText("");
	}

	public void displayInComboBoxEmail(ComboBox combo, String query) {
		try {
			conn = CashBookController.getConnection();
			pst = conn.prepareStatement(query);

			ResultSet rs = pst.executeQuery();

			while (rs.next()) {
				classEmail.add(rs.getString(1));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			if (conn != null) {

				try {
					conn.close();
					pst.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}
		}

	}

	public void displayInComboBoxSMS(ComboBox combo, String query) {
		try {
			conn = CashBookController.getConnection();
			pst = conn.prepareStatement(query);

			ResultSet rs = pst.executeQuery();

			while (rs.next()) {
				classSMS.add(rs.getString(1));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			if (conn != null) {

				try {
					conn.close();
					pst.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}
		}

	}

	public void displayInComboBoxNotice(ComboBox combo, String query) {
		try {
			conn = CashBookController.getConnection();
			pst = conn.prepareStatement(query);

			ResultSet rs = pst.executeQuery();

			while (rs.next()) {
				classNotice.add(rs.getString(1));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			if (conn != null) {

				try {
					conn.close();
					pst.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}
		}

	}

	public void displayInComboBoxNoticeUsers(ComboBox combo, String query) {
		try {
			conn = CashBookController.getConnection();
			pst = conn.prepareStatement(query);

			ResultSet rs = pst.executeQuery();

			usersNotice.clear();
			usersNotice.add("Choose User");
			while (rs.next()) {
				usersNotice.add(rs.getString(1));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			if (conn != null) {

				try {
					conn.close();
					pst.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}
		}

	}

	public void displayParentsEmails(TextField field, String query) {
		try {
			conn = CashBookController.getConnection();
			pst = conn.prepareStatement(query);

			ResultSet rs = pst.executeQuery();

			while (rs.next()) {

				field.setText(rs.getString(1));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			if (conn != null) {

				try {
					conn.close();
					pst.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}
		}

	}

	public void displayFathersContacts(TextField field, String query) {
		try {
			conn = CashBookController.getConnection();
			pst = conn.prepareStatement(query);

			ResultSet rs = pst.executeQuery();

			while (rs.next()) {

				field.setText(rs.getString(1));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			if (conn != null) {

				try {
					conn.close();
					pst.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}
		}

	}

	public void displayMothersContacts(TextField field, String query) {
		try {
			conn = CashBookController.getConnection();
			pst = conn.prepareStatement(query);

			ResultSet rs = pst.executeQuery();

			while (rs.next()) {

				field.setText(field.getText() + "," + rs.getString(1));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			if (conn != null) {

				try {
					conn.close();
					pst.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}
		}

	}

	public void displayGuardiansContacts(TextField field, String query) {
		try {
			conn = CashBookController.getConnection();
			pst = conn.prepareStatement(query);

			ResultSet rs = pst.executeQuery();

			while (rs.next()) {

				field.setText(field.getText() + "," + rs.getString(1));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			if (conn != null) {

				try {
					conn.close();
					pst.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}
		}

	}

	public void displaySponsorsContacts(TextField field, String query) {
		try {
			conn = CashBookController.getConnection();
			pst = conn.prepareStatement(query);

			ResultSet rs = pst.executeQuery();

			while (rs.next()) {

				field.setText(field.getText() + "," + rs.getString(1));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			if (conn != null) {

				try {
					conn.close();
					pst.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}
		}

	}

	private boolean validateFields() {
		if (fieldCcEmail.getText().equals("")) {
			JOptionPane.showMessageDialog(this, "Please Enter The Email Address(s)!", "Email(s) Missing",
					JOptionPane.ERROR_MESSAGE);
			fieldCcEmail.requestFocus();
			return false;
		}

		if (subjectField.getText().equals("")) {
			JOptionPane.showMessageDialog(this, "Please Write The E-Mail Subject!", "Subject Missing",
					JOptionPane.ERROR_MESSAGE);
			subjectField.requestFocus();
			return false;
		}

		if (htmlEditor.getHtmlText().isEmpty()) {
			JOptionPane.showMessageDialog(this, "Please Write The Message To The Recipient!", "Message Missing",
					JOptionPane.ERROR_MESSAGE);
			htmlEditor.requestFocus();
			return false;
		}

		return true;
	}

	public void test() throws Exception {
		Properties props = new Properties();

		props.put("mail.transport.protocol", "smtps");
		props.put("mail.smtps.host", SMTP_HOST_NAME);
		props.put("mail.smtps.auth", "true");

		Session mailSession = Session.getDefaultInstance(props);
		mailSession.setDebug(true);
		Transport transport = mailSession.getTransport();

		MimeMessage message = new MimeMessage(mailSession);
		message.setSubject(subject);
		message.setContent(bodyofmessage, "text/plain");

		message.addRecipient(Message.RecipientType.TO, new InternetAddress(receiver));
		message.addRecipients(Message.RecipientType.CC, InternetAddress.parse(carboncopy));

		transport.connect(SMTP_HOST_NAME, SMTP_HOST_PORT, SMTP_AUTH_USER, SMTP_AUTH_PWD);

		transport.sendMessage(message, message.getRecipients(Message.RecipientType.TO));

		transport.sendMessage(message, message.getRecipients(Message.RecipientType.CC));
		transport.close();
	}

	private void stripHTMLTags(String htmlText) {

		Pattern pattern = Pattern.compile("<[^>]*>");
		Matcher matcher = pattern.matcher(htmlText);
		finalSMSBody = new StringBuffer(htmlText.length());
		while (matcher.find()) {
			matcher.appendReplacement(finalSMSBody, " ");
		}
		matcher.appendTail(finalSMSBody);
		System.out.println(finalSMSBody.toString().trim());

	}

}