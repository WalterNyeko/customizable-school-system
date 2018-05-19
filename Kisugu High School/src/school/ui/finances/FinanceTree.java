package school.ui.finances;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Font;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.embed.swing.JFXPanel;
import javafx.scene.Scene;
import javafx.scene.control.ListView;
import javafx.scene.layout.VBox;

public class FinanceTree extends JPanel {

	private JTree financeTree;
	private DefaultMutableTreeNode parentFinance;
	private DefaultMutableTreeNode treeModelFinanceCashBook;
	private DefaultMutableTreeNode treeModelFinanceAccounts;

	private DefaultMutableTreeNode treeModelFinanceLedgerBook;
	private DefaultMutableTreeNode treeModelFinanceGeneralLedger;
	private DefaultMutableTreeNode treeModelFinanceStudentLedgerCard;
	private DefaultMutableTreeNode treeModelFinanceStoreLedger;
	
	private DefaultMutableTreeNode treeModelFinanceTrialBalance;
	private DefaultMutableTreeNode treeModelFinanceReport;
	private DefaultMutableTreeNode treeModelFinanceStatement;
	private DefaultMutableTreeNode treeModelFinanceBalanceSheet;
	
	private DefaultMutableTreeNode treeModelFinanceStockCard;
	private DefaultMutableTreeNode treeModelFinanceNotes;
	
	private JScrollPane scrollPaneFinance;
	private ListView<String> lvListDisplayedFinance;
	private ObservableList<String> itemsDisplayed;

	public FinanceTree() {
		buildGUI();
	}

	private void buildGUI() {

		
		
		lvListDisplayedFinance = new ListView<String>();
		itemsDisplayed = FXCollections.observableArrayList("Cash Book","Accounts","General Ledger","Student Ledger Card",
				"Store Ledger","Trial Balance","Stock Card","Income Statement","Balance Sheet","Notes");
		lvListDisplayedFinance.setItems(itemsDisplayed);
//		lvListDisplayedFinance.setMaxHeight(Control.USE_PREF_SIZE);
		lvListDisplayedFinance.setMaxWidth(170);
		lvListDisplayedFinance.setPrefWidth(152);
		lvListDisplayedFinance.setPrefHeight(540);
		
		VBox vb=new VBox(2);
		vb.getChildren().add(lvListDisplayedFinance);
		
		Scene scene=new Scene(vb);
		
		JFXPanel panel=new JFXPanel();
		panel.setScene(scene);
		
		Platform.setImplicitExit(false);
		add(panel);

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
