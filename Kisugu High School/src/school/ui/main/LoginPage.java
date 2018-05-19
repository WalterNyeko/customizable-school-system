package school.ui.main;

import java.awt.HeadlessException;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.InterfaceAddress;
import java.net.NetworkInterface;
import java.net.Socket;
import java.net.SocketException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Enumeration;

import javax.swing.ImageIcon;
import javax.swing.JOptionPane;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Control;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.control.RadioButton;
import javafx.scene.control.Separator;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Font;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;
import school.ui.finances.CashBookController;

/**
 * Sample application that shows how the sized of controls can be managed.
 * Sample is for demonstration purposes only, most controls are inactive.
 */
public class LoginPage extends Application {

	////////////////// Progress Bar Stuffs//////////////////
	final Float[] values = new Float[] { -2f };
	final Label[] labels = new Label[values.length];
	final ProgressBar[] pbs = new ProgressBar[values.length];
	final ProgressIndicator[] pins = new ProgressIndicator[values.length];
	final HBox hbs[] = new HBox[values.length];

	// Define buttons here for access by multiple methods
	private Circle internet;
	private Circle c;
	private Circle wLAN;

	Enumeration<NetworkInterface> interfaces;
	private NetworkInterface networkInterface;
	private Separator separator;
	private FileInputStream input;
	protected Scene sceneProgressBar;
	protected Task<MainPage> createMainScene;
	private Label connectedIP;
	private TextField tfName;
	private PasswordField pfPwd;
	private CheckBox rememberme;
	private LoginHelperClass helperClass;

	private String currentUser;
	private Connection conn;
	private RadioButton radioButton1;
	private RadioButton radioButton2;
	private RadioButton radioButton3;
	private String dbhost;
	private String dbuser;
	private String dbPass;

	public String getCurrentUser() {
		return this.currentUser();
	}

	private String currentUser() {

		String sql = "insert into login_tracking(username,password,login_date,login_time) values(?,?,now(),now())";
		try {

			helperClass = new LoginHelperClass();

			conn = CashBookController.getConnection();
			PreparedStatement pst = conn.prepareStatement(sql);
			pst.setString(1, tfName.getText());
			pst.setString(2, pfPwd.getText());

			pst.execute();

		} catch (Exception e) {
			e.printStackTrace();
		}

		return currentUser;
	}

	/**
	 * @param args
	 *            the command line arguments
	 */
	public static void main(String[] args) {
		Application.launch(LoginPage.class, args);

	}

	// @Override
	public void start(Stage primaryStage) {

		HBox box = new HBox(sizingSample());

//		Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(5), evt -> {
//			if ((testInet(("afri-whiteevents.com")) == true) || testInet(("google.com")) == true) {
//			    checks Internet connection and returns true
//				internet.setFill(Color.GREEN);
//				internet.setVisible(false);
//
//			} else {
//
//				internet.setFill(Color.RED);
//			}
//
//			try {
//				interfaces = NetworkInterface.getNetworkInterfaces();
//			} catch (SocketException e) {
//				e.printStackTrace();
//			}
//			while (interfaces.hasMoreElements()) {
//
//				networkInterface = interfaces.nextElement();
//
//				try {
//					if (networkInterface.isUp() == true) {
//						for (InterfaceAddress i : networkInterface.getInterfaceAddresses()) {
//
//							if (i.getAddress().getHostAddress().contains("wlan")) {
//
//								wLAN.setFill(Color.GREEN);
//								wLAN.setVisible(false);
//
//							} else if (i.getAddress().getHostAddress().contains("eth")) {
//
//								c.setFill(Color.GREEN);
//								c.setVisible(false);
//
//							} else {
//								// c.setFill(Color.RED);
//								// wLAN.setFill(Color.RED);
//							}
//
//						}
//
//					} else {
//
//					}
//				} catch (HeadlessException e) {
//					e.printStackTrace();
//				} catch (SocketException e) {
//					e.printStackTrace();
//				}
//
//			}
//
//		}), new KeyFrame(Duration.seconds(2), evt -> {
//			wLAN.setVisible(true);
//			c.setVisible(true);
//			internet.setVisible(true);
//		}));
//		timeline.setCycleCount(Animation.INDEFINITE);
//		timeline.play();

		Scene scene = new Scene(box, 550, 240);
		primaryStage.initStyle(StageStyle.UNDECORATED);
		primaryStage.setTitle("SoftEarth Technologies- Login Page");
		primaryStage.setScene(scene);

		helperClass = new LoginHelperClass();
		ResultSet result;
		ResultSet resultRem;
		try {

			result = helperClass.displayDataBaseLoginCredentials();
			while (result.next()) {
				System.out.println("Yes " + result.getString(1));
			}

			resultRem = helperClass.displayRememberedUsers();
			if (resultRem.next()) {

				System.out.println("Yes " + resultRem.getString(1));

				radioButton2.setSelected(true);
				tfName.setText(resultRem.getString(1));
				pfPwd.setText(resultRem.getString(2));
			}

		} catch (Exception e) {
		}

		primaryStage.show();

	}

	private BorderPane sizingSample() {

		BorderPane border = new BorderPane();
		border.setPadding(new Insets(10, 0, 20, 10));

		VBox vb = new VBox(20);
		Label labelLogin = new Label("ST MARY'S HIGH SCHOOL-KISUGU, KAMPALA (U)");

		separator = new Separator(Orientation.HORIZONTAL);

		labelLogin.setFont(new Font("Times New Roman", 18));
		labelLogin.setStyle("-fx-text-color: blue");

		GridPane grid = new GridPane();
		grid.setAlignment(Pos.CENTER); // Override default
		grid.setHgap(10);
		grid.setVgap(12);

		Button btnMoveDown = new Button("Forgot Password?");

		btnMoveDown.setStyle("-fx-font-size: 8pt;");

		// Comment out the following statements to see the default button sizes
		btnMoveDown.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
		btnMoveDown.setMinWidth(Control.USE_PREF_SIZE);

		vb.setAlignment(Pos.TOP_CENTER);

		vb.getChildren().addAll(grid, createButtonRow());
		ColumnConstraints column1 = new ColumnConstraints();
		column1.setHalignment(HPos.RIGHT); // Override default
		grid.getColumnConstraints().add(column1);

		ColumnConstraints column2 = new ColumnConstraints();
		column2.setHalignment(HPos.LEFT); // Override default
		grid.getColumnConstraints().add(column2);

		HBox hbButtons = new HBox();
		hbButtons.setSpacing(10.0);
		hbButtons.setAlignment(Pos.CENTER); // Aligns HBox and controls in HBox

		Button btnSubmit = new Button("Login");
		btnSubmit.setDefaultButton(true);
		Button btnClear = new Button("Forgot Password?");
		Button btnExit2 = new Button("Cancel");
		btnSubmit.setStyle("-fx-font-size: 8pt;");
		btnClear.setStyle("-fx-font-size: 8pt;");
		btnExit2.setStyle("-fx-font-size: 8pt;");
		btnSubmit.setPrefWidth(120);
		btnClear.setPrefWidth(120);
		btnExit2.setPrefWidth(120);

		Label lblName = new Label("Username:");
		tfName = new TextField();
		Label lblPwd = new Label("Password:");
		pfPwd = new PasswordField();

		hbButtons.getChildren().addAll(btnSubmit, btnClear, btnExit2);

		btnSubmit.setOnAction(new EventHandler<ActionEvent>() {

			private ProgressBar pb;

			@Override
			public void handle(ActionEvent arg0) {

				if (rememberme.isSelected()) {

					try {
						helperClass.updateRememberedUser(tfName.getText(), pfPwd.getText());

						Group root = new Group();
						sceneProgressBar = new Scene(root, 250, 55, Color.BLACK);
						Stage stage = new Stage();

						stage.initStyle(StageStyle.UNDECORATED);
						stage.setScene(sceneProgressBar);
						stage.setTitle("Progress Controls");

						for (int i = 0; i < values.length; i++) {

							final Label label = labels[i] = new Label();

							pb = pbs[i] = new ProgressBar();
							pb.setProgress(values[i]);

							final ProgressIndicator pin = pins[i] = new ProgressIndicator();
							pin.setProgress(values[i]);
							final HBox hb = hbs[i] = new HBox();
							hb.setSpacing(5);
							hb.setAlignment(Pos.CENTER);
							hb.getChildren().addAll(label, pb, pin);
						}

						VBox vb = new VBox();
						vb.setSpacing(5);
						vb.getChildren().addAll(hbs);
						sceneProgressBar.setRoot(vb);
						stage.setResizable(false);
						stage.setOpacity(1);

						Rectangle2D primScreenBounds = Screen.getPrimary().getVisualBounds();
						stage.setX((primScreenBounds.getWidth() + 500 - stage.getWidth()));
						stage.setY((primScreenBounds.getHeight() - stage.getHeight()));

						stage.show();

						createMainScene = new Task<MainPage>() {
							@Override
							public MainPage call() {

								checkLoginCredentials();
								{
								}
								return null;
							}
						};
						pb.progressProperty().bind(createMainScene.progressProperty());
						stage.setScene(sceneProgressBar);

						createMainScene.setOnSucceeded(e -> {

							stage.close();

						});

						new Thread(createMainScene).start();

					} catch (ClassNotFoundException e1) {
						e1.printStackTrace();
					} catch (SQLException e1) {
						e1.printStackTrace();
					}

				} else {

					try {
						helperClass.updateRememberedUser("", "");
					} catch (ClassNotFoundException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					} catch (SQLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					Group root = new Group();
					sceneProgressBar = new Scene(root, 250, 55, Color.BLACK);
					Stage stage = new Stage();

					stage.initStyle(StageStyle.UNDECORATED);
					stage.setScene(sceneProgressBar);
					stage.setTitle("Progress Controls");

					for (int i = 0; i < values.length; i++) {
						final Label label = labels[i] = new Label();

						pb = pbs[i] = new ProgressBar();
						pb.setProgress(values[i]);

						final ProgressIndicator pin = pins[i] = new ProgressIndicator();
						pin.setProgress(values[i]);
						final HBox hb = hbs[i] = new HBox();
						hb.setSpacing(5);
						hb.setAlignment(Pos.CENTER);
						hb.getChildren().addAll(label, pb, pin);
					}

					VBox vb = new VBox();
					vb.setSpacing(5);
					vb.getChildren().addAll(hbs);
					sceneProgressBar.setRoot(vb);
					stage.setResizable(false);
					stage.setOpacity(1);

					Rectangle2D primScreenBounds = Screen.getPrimary().getVisualBounds();
					stage.setX((primScreenBounds.getWidth() + 500 - stage.getWidth()));
					stage.setY((primScreenBounds.getHeight() - stage.getHeight()));

					stage.show();

					createMainScene = new Task<MainPage>() {
						@Override
						public MainPage call() {

							checkLoginCredentials();
							{
							}
							return null;
						}
					};
					pb.progressProperty().bind(createMainScene.progressProperty());
					stage.setScene(sceneProgressBar);

					createMainScene.setOnSucceeded(e -> {

						stage.close();
						stage.hide();

					});

					new Thread(createMainScene).start();

				}

			}
		});

		btnExit2.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {

				System.exit(0);
			}
		});

		grid.add(lblName, 0, 0);
		grid.add(tfName, 1, 0);
		grid.add(lblPwd, 0, 1);
		grid.add(pfPwd, 1, 1);
		grid.add(hbButtons, 0, 2, 2, 1);

		VBox boxForSeparatorAndheading = new VBox();
		boxForSeparatorAndheading.setPadding(new Insets(10, 10, 10, 10));
		boxForSeparatorAndheading.setAlignment(Pos.TOP_CENTER);
		boxForSeparatorAndheading.getChildren().addAll(labelLogin, separator);

		border.setTop(boxForSeparatorAndheading);
		border.setCenter(vb);
		border.setRight(createButtonColumn());
		// border.setBottom(createButtonRow()); // Uses a tile pane for sizing
		return border;
	}

	private VBox createButtonColumn() {

		Image img = new Image(getClass().getResourceAsStream("logo1.png"));
		ImageView imageView = new ImageView(img);

		GridPane panelRight = new GridPane();
		panelRight.setVgap(15);
		panelRight.setHgap(10);
		panelRight.setPadding(new Insets(0, 5, 5, 5));

		HBox hbox = new HBox();
		hbox.getChildren().add(imageView);
		hbox.setAlignment(Pos.CENTER);

		rememberme = new CheckBox("Remember Me");
		rememberme.setSelected(true);

		connectedIP = new Label("SoftEarth Technologies");

		panelRight.add(hbox, 0, 0);
		panelRight.add(rememberme, 0, 1);
		panelRight.add(connectedIP, 0, 2);

		VBox vbButtons = new VBox(panelRight);
		
		return vbButtons;
	}

	private GridPane createButtonRow() {

		radioButton1 = new RadioButton("Ethernet (Cabled LAN)");
		radioButton2 = new RadioButton("Wireless (W-LAN)");
		radioButton3 = new RadioButton("Internet Access");

		ToggleGroup radioGroup = new ToggleGroup();

		radioButton1.setToggleGroup(radioGroup);
		radioButton2.setToggleGroup(radioGroup);
		radioButton3.setToggleGroup(radioGroup);

		Group g = new Group();

		DropShadow ds1 = new DropShadow();
		ds1.setOffsetY(4.0);

		c = new Circle();
		c.setEffect(ds1);
		c.setRadius(4.0);
		c.setFill(Color.RED);
		c.setCache(true);

		wLAN = new Circle();
		wLAN.setEffect(ds1);
		wLAN.setRadius(4.0);
		wLAN.setFill(Color.RED);
		wLAN.setCache(true);

		internet = new Circle();
		internet.setEffect(ds1);
		internet.setRadius(4.0);
		internet.setFill(Color.RED);
		internet.setCache(true);

		g.getChildren().addAll(c, wLAN, internet);

		GridPane gridPane = new GridPane();
		gridPane.setVgap(10);
		gridPane.setHgap(10);

		gridPane.add(radioButton1, 0, 0);
		gridPane.add(radioButton2, 1, 0);
		gridPane.add(radioButton3, 2, 0);
		gridPane.add(c, 0, 1);
		gridPane.add(wLAN, 1, 1);
		gridPane.add(internet, 2, 1);

		return gridPane;
	}

	public static boolean testInet(String site) {
		Socket sock = new Socket();
		InetSocketAddress addr = new InetSocketAddress(site, 80);
		try {
			sock.connect(addr, 3000);
			return true;
		} catch (IOException e) {
			return false;
		} finally {
			try {
				sock.close();
			} catch (IOException e) {
			}
		}
	}

	public void checkLoginCredentials() {

		String sql = "SELECT * FROM application_users WHERE username=? and password=?";
		try {

			helperClass = new LoginHelperClass();
			
			if (radioButton1.isSelected()) {
				
				ResultSet result;
				try {
					
					result = helperClass.displayDataBaseLoginCredentials();
					while (result.next()) {
						 dbhost=result.getString(1);
						 dbuser=result.getString(2);
						 dbPass=result.getString(3);
						

					}

				} catch (Exception e) {
					e.printStackTrace();
				}
			} else if (radioButton2.isSelected()) {
				ResultSet resultWLAN;
				try {

					resultWLAN = helperClass.displayDataBaseLoginCredentials();
					while (resultWLAN.next()) {
						 dbhost=resultWLAN.getString(1);
						 dbuser=resultWLAN.getString(2);
						 dbPass=resultWLAN.getString(3);
					}

				} catch (Exception e) {
					e.printStackTrace();
				}
			} else if (radioButton3.isSelected()) {
				ResultSet resultNet;
				try {

					resultNet = helperClass.displayDataBaseLoginCredentialsForInternet();
					while (resultNet.next()) {
						 dbhost=resultNet.getString(1);
						 dbuser=resultNet.getString(2);
						 dbPass=resultNet.getString(3);

					}

				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			
			

			conn = DriverManager.getConnection(dbhost, dbuser, dbPass);
			PreparedStatement pst = conn.prepareStatement(sql);
			pst.setString(2, pfPwd.getText());
			pst.setString(1, tfName.getText());

			ResultSet rs = pst.executeQuery();
			if (rs.next()) {

				MainPage object = new MainPage();
				getCurrentUser();

				object.current_user.setText(tfName.getText());
				
				Platform.runLater(new Runnable() {
					
					@Override
					public void run() {

						Platform.isAccessibilityActive();
//						Platform.setImplicitExit(true);
						Platform.isImplicitExit();
					}
				});

				
			} else {

				JOptionPane.showMessageDialog(null, "Username or Password is Not Correct");
				new LoginPage();
			}
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null,
					"Please make sure your server is running and try again later " + e.getMessage());
			e.printStackTrace();
		}
	}

}
