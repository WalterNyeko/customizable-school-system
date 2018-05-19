package school.ui.reports;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Font;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.embed.swing.JFXPanel;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListView;
import javafx.scene.layout.VBox;
import school.ui.finances.CashBookController;

public class MessagesAlertsNumbers extends JPanel {

	private JTree financeTree;

	private JScrollPane scrollPaneFinance;
	public ListView<String> lvListDisplayedFinance;
	private ObservableList<String> itemsDisplayed;

	private Connection conn;

	private PreparedStatement pst;

	private String composant;

	public MessagesAlertsNumbers() {
		buildGUI();
	}

	private void buildGUI() {

		lvListDisplayedFinance = new ListView();
//		itemsDisplayed = FXCollections.observableArrayList("");
		lvListDisplayedFinance.setMaxWidth(170);
		lvListDisplayedFinance.setPrefWidth(152);
		lvListDisplayedFinance.setPrefHeight(540);

		VBox vb = new VBox(2);
		vb.getChildren().add(lvListDisplayedFinance);

		Scene scene = new Scene(vb);

		JFXPanel panel = new JFXPanel();
		panel.setScene(scene);

		Platform.setImplicitExit(false);
		add(panel);

		

	}

	public void displayNumberOfUnreadMessagesPerUser(String query) {
		try {
			conn = CashBookController.getConnection();
			pst = conn.prepareStatement(query);

			ResultSet rs = pst.executeQuery();

			lvListDisplayedFinance.getItems().clear();
			while (rs.next()) {
				
				 composant = rs.getString(1) + " (" + rs.getString(2) + ")";

				lvListDisplayedFinance.getItems().add(composant);
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

	public ListView<String> getLvListDisplayedFinance() {
		return lvListDisplayedFinance;
	}

	public void setLvListDisplayedFinance(ListView<String> lvListDisplayedFinance) {
		this.lvListDisplayedFinance = lvListDisplayedFinance;
	}

	public JTree getFinanceTree() {
		return financeTree;
	}

	public JScrollPane getScrollPaneFinance() {
		return scrollPaneFinance;
	}

	private DefaultMutableTreeNode addNode(String nodeName, DefaultMutableTreeNode parentNodeName) {
		DefaultMutableTreeNode newNode = new DefaultMutableTreeNode(nodeName);
		parentNodeName.add(newNode);
		return newNode;
	}

}
