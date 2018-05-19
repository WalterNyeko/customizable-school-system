package school.ui.assets;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DecimalFormat;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;

import com.toedter.calendar.JDateChooser;
import com.toedter.calendar.JYearChooser;

import school.ui.finances.CashBookController;

@SuppressWarnings("serial")
public class AssetsPanel extends JPanel {

	private JPanel panelTopHolder;

	private JLabel labelDate;
	private JLabel labelDescription;
	private JLabel labelLocation;
	private JLabel labelStatus;
	private JLabel labelCostOrValuation;
	private JLabel labelDepreciation;
	private JLabel labelAccumulatedDepreciation;

	private JDateChooser dateChooserDate;
	private JTextField fieldDescription;
	private JTextField fieldLocation;
	private JTextField fieldStatus;
	private JTextField fieldCostOrValuation;
	private JTextField fieldDepreciation;
	private JTextField fieldAccumulatedDepreciation;

	private JButton btnAddition;
	private JButton btnWriteOff;

	private JTable tableAssets;
	private JScrollPane scrollPaneAssets;

	private JButton btnExport;
	private JButton btnPrint;

	public JComboBox<String> fieldAsset;

	private JLabel labelAsset;

	private JTextField fieldClass;

	private JLabel labelClass;

	private JButton btnEnter;

	// private JPanel panelTopHolderAssetsName;

	private Connection conn;

	private JPanel panelTopHolderAssetsName;

	protected JYearChooser year;

	private JYearChooser yearOfNow;

	public AssetsPanel() {
		// TODO Auto-generated constructor stub
		setUpAssetPanel();
	}

	private void setUpAssetPanel() {
		// TODO Auto-generated method stub

		panelTopHolderAssetsName = new JPanel();
		panelTopHolderAssetsName.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		panelTopHolderAssetsName.setBorder(new LineBorder(Color.blue, 3));
		panelTopHolderAssetsName.setPreferredSize(new Dimension(1160, 480));
		add(panelTopHolderAssetsName);

		panelTopHolder = new JPanel();
		panelTopHolder.setLayout(new FlowLayout(FlowLayout.CENTER, 15, 10));
		// panelTopHolder.setBorder(new LineBorder(Color.blue, 3));
		panelTopHolder.setPreferredSize(new Dimension(1150, 150));
		panelTopHolder.setBackground(Color.decode("#5f9ea0"));
		panelTopHolderAssetsName.add(panelTopHolder);

		Dimension dimensionFields = new Dimension(200, 25);
		Dimension dimensionLabels = new Dimension(140, 25);

		labelClass = new JLabel("Class:");
		labelClass.setPreferredSize(dimensionLabels);
		panelTopHolder.add(labelClass);
		fieldClass = new JTextField();
		fieldClass.setPreferredSize(dimensionFields);
		panelTopHolder.add(fieldClass);

		labelAsset = new JLabel("Asset:");
		labelAsset.setPreferredSize(dimensionLabels);
		panelTopHolder.add(labelAsset);
		fieldAsset = new JComboBox<String>();
		fieldAsset.setPreferredSize(dimensionFields);
		panelTopHolder.add(fieldAsset);
		fieldAsset.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				displayAssetInfo();

			}
		});

		labelDate = new JLabel("Date Acquired:");
		labelDate.setPreferredSize(dimensionLabels);
		panelTopHolder.add(labelDate);
		dateChooserDate = new JDateChooser();
		dateChooserDate.setPreferredSize(dimensionFields);
		dateChooserDate.getDate();
		// dateChooserDate.setEnabled(false);
		panelTopHolder.add(dateChooserDate);

		labelDescription = new JLabel("Description");
		labelDescription.setPreferredSize(dimensionLabels);
		panelTopHolder.add(labelDescription);
		fieldDescription = new JTextField();
		fieldDescription.setPreferredSize(dimensionFields);
		panelTopHolder.add(fieldDescription);

		labelLocation = new JLabel("Location:");
		labelLocation.setPreferredSize(dimensionLabels);
		panelTopHolder.add(labelLocation);
		fieldLocation = new JTextField();
		fieldLocation.setPreferredSize(dimensionFields);
		panelTopHolder.add(fieldLocation);

		labelStatus = new JLabel("Status:");
		labelStatus.setPreferredSize(dimensionLabels);
		panelTopHolder.add(labelStatus);
		fieldStatus = new JTextField();
		fieldStatus.setPreferredSize(dimensionFields);
		panelTopHolder.add(fieldStatus);

		labelCostOrValuation = new JLabel("Cost/Valuation");
		labelCostOrValuation.setPreferredSize(dimensionLabels);
		panelTopHolder.add(labelCostOrValuation);
		fieldCostOrValuation = new JTextField();
		fieldCostOrValuation.setPreferredSize(dimensionFields);
		panelTopHolder.add(fieldCostOrValuation);

		labelDepreciation = new JLabel("Depreciation (%):");
		labelDepreciation.setPreferredSize(dimensionLabels);
		panelTopHolder.add(labelDepreciation);
		fieldDepreciation = new JTextField();
		fieldDepreciation.setPreferredSize(dimensionFields);
		fieldDepreciation.addKeyListener(new KeyListener() {

			@Override
			public void keyTyped(KeyEvent arg0) {
				// TODO Auto-generated method stub

			}

			@Override
			public void keyReleased(KeyEvent arg0) {
				// TODO Auto-generated method stub
				if (!fieldCostOrValuation.getText().isEmpty()) {

					double depreciation = Double.parseDouble(fieldDepreciation.getText());
					double cost = Double.parseDouble(fieldCostOrValuation.getText());
					double accumulated = depreciation * cost;

					DecimalFormat df = new DecimalFormat("#");
					df.setMaximumFractionDigits(12);

					fieldAccumulatedDepreciation.setText("" + df.format(accumulated));

				} else {
					JOptionPane.showMessageDialog(null, "Please first make sure the asset has acquisition date");
				}

			}

			@Override
			public void keyPressed(KeyEvent arg0) {
				// TODO Auto-generated method stub

			}
		});
		panelTopHolder.add(fieldDepreciation);

		labelAccumulatedDepreciation = new JLabel("Accumulated Depreciation:");
		labelAccumulatedDepreciation.setPreferredSize(dimensionLabels);
		panelTopHolder.add(labelAccumulatedDepreciation);
		fieldAccumulatedDepreciation = new JTextField();
		fieldAccumulatedDepreciation.setEditable(false);
		fieldAccumulatedDepreciation.setPreferredSize(dimensionFields);
		panelTopHolder.add(fieldAccumulatedDepreciation);

		btnAddition = new JButton("Addition");
		btnAddition.setPreferredSize(new Dimension(160, 25));
		btnAddition.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub

				JDialog dialogEstimates = new JDialog();
				dialogEstimates.setTitle("Record a new addition to an existing asset");
				dialogEstimates.setLocationRelativeTo(null);
				dialogEstimates.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
				dialogEstimates.setSize(600, 270);

				JPanel panelHolder = new JPanel();
				JLabel labelYearname = new JLabel("Date of Addition:");
				labelYearname.setPreferredSize(new Dimension(180, 25));
				dialogEstimates.getContentPane().add(panelHolder);
				panelHolder.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 20));
				panelHolder.add(labelYearname);

				JDateChooser dateofAddition = new JDateChooser();
				dateofAddition.getDate();
				dateofAddition.setPreferredSize(new Dimension(260, 25));
				panelHolder.add(dateofAddition);

				JLabel labelYear = new JLabel("Name of Asset:");
				labelYear.setPreferredSize(new Dimension(180, 25));
				panelHolder.add(labelYear);

				JComboBox chooseItemName = new JComboBox();
				chooseItemName.setPreferredSize(new Dimension(260, 25));
				panelHolder.add(chooseItemName);

				JLabel additionDescription = new JLabel("Addition Description:");
				additionDescription.setPreferredSize(new Dimension(180, 25));
				panelHolder.add(additionDescription);

				JTextField fiedlAdditionDescription = new JTextField();
				fiedlAdditionDescription.setPreferredSize(new Dimension(260, 25));
				panelHolder.add(fiedlAdditionDescription);

				JLabel labelYearName = new JLabel("Addition Value:");
				labelYearName.setPreferredSize(new Dimension(180, 25));
				panelHolder.add(labelYearName);

				JTextField additionValue = new JTextField();
				additionValue.setPreferredSize(new Dimension(260, 25));
				panelHolder.add(additionValue);

				JLabel labelYear1 = new JLabel("Addition Year:");
				labelYear1.setPreferredSize(new Dimension(180, 25));
				panelHolder.add(labelYear1);

				JYearChooser yearOfWriteoff = new JYearChooser();
				yearOfWriteoff.getYear();
				yearOfWriteoff.setPreferredSize(new Dimension(180, 25));
				panelHolder.add(yearOfWriteoff);

				displayInComboBox(chooseItemName,
						"select scatname from subcategory where account_type='Current Asset' or account_type='Fixed Asset'");

				JButton btnOpen = new JButton("Save Asset Value");
				btnOpen.setPreferredSize(new Dimension(150, 25));
				btnOpen.addActionListener(new ActionListener() {

					@Override
					public void actionPerformed(ActionEvent arg0) {
						// TODO Auto-generated method stub

						AddItem("insert into school_assets_addition_writeof(date,asset_name,asset_value_addition,asset_description) values("
								+ "'" + convertFromUtilToSQLDate(dateofAddition.getDate()) + "','"
								+ chooseItemName.getSelectedItem() + "','" + additionValue.getText() + "'," + "'"
								+ fiedlAdditionDescription.getText() + "')");

						AddItem("update school_fixed_assets set `" + yearOfWriteoff.getYear() + "`=`"
								+ yearOfWriteoff.getYear() + "`+'" + additionValue.getText() + "'"
								+ " where asset_name='" + chooseItemName.getSelectedItem() + "'");

						displayAssets(tableAssets,
								"select date,asset_class,asset_name,description," + "location,asset_status,"
										+ "asset_cost,depreciation,accumulated_depreciation," + "`"
										+ yearOfNow.getYear() + "` " + "from school_fixed_assets where "
										+ "asset_status is not null");
						displayInComboBox(fieldAsset,
								"select scatname from subcategory where account_type='Current Asset' or account_type='Fixed Asset'");

						dialogEstimates.dispose();

					}
				});
				panelHolder.add(btnOpen);

				displayInComboBox(fieldAsset,
						"select scatname from subcategory where account_type='Current Asset' or account_type='Fixed Asset'");

				dialogEstimates.setVisible(true);

			}
		});
		panelTopHolder.add(btnAddition);

		btnWriteOff = new JButton("Write Off");
		btnWriteOff.setPreferredSize(new Dimension(160, 25));
		btnWriteOff.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub

				JDialog dialogEstimates = new JDialog();
				dialogEstimates.setTitle("Record a new writeoff to an existing asset");
				dialogEstimates.setLocationRelativeTo(null);
				dialogEstimates.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
				dialogEstimates.setSize(600, 270);

				JPanel panelHolder = new JPanel();
				JLabel labelYearname = new JLabel("Date of WriteOff:");
				labelYearname.setPreferredSize(new Dimension(180, 25));
				dialogEstimates.getContentPane().add(panelHolder);
				panelHolder.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 20));
				panelHolder.add(labelYearname);

				JDateChooser dateofWriteOff = new JDateChooser();
				dateofWriteOff.getDate();
				dateofWriteOff.setPreferredSize(new Dimension(260, 25));
				panelHolder.add(dateofWriteOff);

				JLabel labelYear = new JLabel("Name of Asset:");
				labelYear.setPreferredSize(new Dimension(180, 25));
				panelHolder.add(labelYear);

				JComboBox chooseItemName = new JComboBox();
				chooseItemName.setPreferredSize(new Dimension(260, 25));
				panelHolder.add(chooseItemName);

				JLabel writeoffDescription = new JLabel("WriteOff Description:");
				writeoffDescription.setPreferredSize(new Dimension(180, 25));
				panelHolder.add(writeoffDescription);

				JTextField fiedlWriteOffDescription = new JTextField();
				fiedlWriteOffDescription.setPreferredSize(new Dimension(260, 25));
				panelHolder.add(fiedlWriteOffDescription);

				JLabel labelYearName = new JLabel("WriteOff Value:");
				labelYearName.setPreferredSize(new Dimension(180, 25));
				panelHolder.add(labelYearName);

				JTextField writeoffValue = new JTextField();
				writeoffValue.setPreferredSize(new Dimension(260, 25));
				panelHolder.add(writeoffValue);

				JLabel labelYear1 = new JLabel("WriteOff Year:");
				labelYear1.setPreferredSize(new Dimension(180, 25));
				panelHolder.add(labelYear1);

				JYearChooser yearOfWriteoff = new JYearChooser();
				yearOfWriteoff.getYear();
				yearOfWriteoff.setPreferredSize(new Dimension(180, 25));
				panelHolder.add(yearOfWriteoff);

				displayInComboBox(chooseItemName,
						"select scatname from subcategory where account_type='Current Asset' or account_type='Fixed Asset'");

				JButton btnOpen = new JButton("Save Asset Value");
				btnOpen.setPreferredSize(new Dimension(150, 25));
				btnOpen.addActionListener(new ActionListener() {

					@Override
					public void actionPerformed(ActionEvent arg0) {
						// TODO Auto-generated method stub

						AddItem("insert into school_assets_addition_writeof(date,asset_name,asset_value_writeof,asset_description) values("
								+ "'" + convertFromUtilToSQLDate(dateofWriteOff.getDate()) + "','"
								+ chooseItemName.getSelectedItem() + "','" + writeoffValue.getText() + "'," + "'"
								+ fiedlWriteOffDescription.getText() + "')");

						AddItem("update school_fixed_assets set `" + yearOfWriteoff.getYear() + "`=`"
								+ yearOfWriteoff.getYear() + "`-'" + writeoffValue.getText() + "'"
								+ " where asset_name='" + chooseItemName.getSelectedItem() + "'");

						displayInComboBox(fieldAsset,
								"select scatname from subcategory where account_type='Current Asset' or account_type='Fixed Asset'");

						displayAssets(tableAssets, "select date,asset_class,asset_name,description," + "location,asset_status,"
								+ "asset_cost,depreciation,accumulated_depreciation," + "`" + yearOfNow.getYear() + "` "
								+ "from school_fixed_assets where " + "asset_status is not null UNION "
								+ "SELECT date, '' as asset_class, account_name, details, '' as location, '' as status,"
								+ " sum(debit), '' as depreciation, sum(credit), "
								+ "sum(debit)-sum(credit) from student_ledger where debit is not null group by account_name");
						dialogEstimates.dispose();

					}
				});
				panelHolder.add(btnOpen);

				displayInComboBox(fieldAsset,
						"select scatname from subcategory where account_type='Current Asset' or account_type='Fixed Asset'");

				dialogEstimates.setVisible(true);

			}
		});
		panelTopHolder.add(btnWriteOff);

		btnEnter = new JButton("Update Asset Value");
		btnEnter.setPreferredSize(new Dimension(160, 25));
		btnEnter.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub

				JDialog dialog = new JDialog();
				dialog.setLocationRelativeTo(null);
				dialog.setSize(250, 100);
				dialog.setTitle("Confirmation");
				dialog.setLayout(new FlowLayout());

				JLabel jLabel = new JLabel("At The End Of:");
				dialog.add(jLabel);

				year = new JYearChooser();
				year.getYear();
				dialog.add(year);
				year.setPreferredSize(new Dimension(100, 25));

				JButton btnContinue = new JButton("Continue");
				btnContinue.addActionListener(new ActionListener() {

					@Override
					public void actionPerformed(ActionEvent arg0) {
						// TODO Auto-generated method stub

						AddItem("alter table school_fixed_assets add column IF NOT EXISTS `" + year.getYear()
								+ "` decimal(12,2) NOT NULL");

						AddItem("insert into school_fixed_assets(asset_name,date,asset_class,location,description,depreciation,asset_cost,accumulated_depreciation,asset_status) "
								+ "values('" + fieldAsset.getSelectedItem() + "'," + "'"
								+ convertFromUtilToSQLDate(dateChooserDate.getDate()) + "','" + fieldClass.getText()
								+ "','" + fieldLocation.getText() + "'," + "'" + fieldDescription.getText() + "','"
								+ fieldDepreciation.getText() + "','" + fieldCostOrValuation.getText() + "'," + "'"
								+ fieldAccumulatedDepreciation.getText() + "','" + fieldStatus.getText() + "') "
								+ "ON DUPLICATE KEY UPDATE `" + year.getYear()
								+ "`=values(asset_cost)-values(accumulated_depreciation)-accumulated_depreciation, accumulated_depreciation=accumulated_depreciation+'"+ fieldAccumulatedDepreciation.getText() + "'");
//						AddItem("update school_fixed_assets set accumulated_depreciation=accumulated_depreciation+'"
//								+ fieldAccumulatedDepreciation.getText() + "'" + " where asset_name='"
//								+ fieldAsset.getSelectedItem() + "'");

						AddItem("update school_fixed_assets set `" + year.getYear()
								+ "`=asset_cost-accumulated_depreciation,depreciation='" + fieldDepreciation.getText()
								+ "'" + " where asset_name='" + fieldAsset.getSelectedItem() + "' ");

						fieldClass.setText(null);
						fieldLocation.setText(null);
						fieldDescription.setText(null);
						fieldDepreciation.setText(null);
						fieldCostOrValuation.setText(null);
						fieldStatus.setText(null);
						dateChooserDate.setDate(null);
						fieldAccumulatedDepreciation.setText(null);

						displayAssets(tableAssets, "select date,asset_class,asset_name,description," + "location,asset_status,"
								+ "asset_cost,depreciation,accumulated_depreciation," + "`" + yearOfNow.getYear() + "` "
								+ "from school_fixed_assets where " + "asset_status is not null UNION "
								+ "SELECT date, '' as asset_class, account_name, details, '' as location, '' as status,"
								+ " sum(debit), '' as depreciation, sum(credit), "
								+ "sum(debit)-sum(credit) from student_ledger where debit is not null group by account_name");
						dialog.dispose();

					}
				});
				dialog.add(btnContinue);
				btnContinue.setPreferredSize(new Dimension(100, 25));

				JButton btnCancel = new JButton("Cancel");
				btnCancel.addActionListener(new ActionListener() {

					@Override
					public void actionPerformed(ActionEvent arg0) {
						// TODO Auto-generated method stub
						dialog.dispose();
					}
				});
				dialog.add(btnCancel);
				btnCancel.setPreferredSize(new Dimension(100, 25));

				dialog.setVisible(true);

			}
		});
		panelTopHolder.add(btnEnter);

		String[] head = { "Date Of Acquisition", "Class", "Asset Name", "Description", "Location", "Status",
				"Cost/Valuation", "Depreciation", "Accumulated Depreciation", "Net Book Value" };

		Object[][] data = {

				{ null, null, null, null, null, null, null, null, null, null, },
				{ null, null, null, null, null, null, null, null, null, null, },
				{ null, null, null, null, null, null, null, null, null, null, },
				{ null, null, null, null, null, null, null, null, null, null, },
				{ null, null, null, null, null, null, null, null, null, null, },
				{ null, null, null, null, null, null, null, null, null, null, },
				{ null, null, null, null, null, null, null, null, null, null, },
				{ null, null, null, null, null, null, null, null, null, null, },
				{ null, null, null, null, null, null, null, null, null, null, },
				{ null, null, null, null, null, null, null, null, null, null, }

		};

		DefaultTableModel model = new DefaultTableModel();

		model.setDataVector(data, head);

		tableAssets = new JTable();
		tableAssets.setModel(model);
		tableAssets.setBackground(Color.decode("#5f9ea0"));

		JTableHeader hd = tableAssets.getTableHeader();
		hd.setPreferredSize(new Dimension(10, 30));
		// hd.setBackground(Color.darkGray);
		tableAssets.setRowHeight(25);
		tableAssets.setAutoCreateRowSorter(true);
		tableAssets.setAutoscrolls(true);
		tableAssets.setAutoCreateColumnsFromModel(true);
		tableAssets.setSelectionBackground(Color.GREEN);
		tableAssets.setAutoResizeMode(100);
		tableAssets.setCellSelectionEnabled(true);
		tableAssets.setColumnSelectionAllowed(true);
		tableAssets.setDragEnabled(true);
		tableAssets.setRowMargin(3);
		tableAssets.setShowGrid(false);

		scrollPaneAssets = new JScrollPane(tableAssets);
		scrollPaneAssets.setPreferredSize(new Dimension(1150, 280));
		Border bodaTable = BorderFactory.createRaisedBevelBorder();
		scrollPaneAssets.setBorder(bodaTable);
		panelTopHolderAssetsName.add(scrollPaneAssets);

		btnExport = new JButton("Export");
		panelTopHolderAssetsName.add(btnExport);

		btnPrint = new JButton("Print");
		panelTopHolderAssetsName.add(btnPrint);
		panelTopHolderAssetsName.setBackground(Color.decode("#5f9ea0"));

		yearOfNow = new JYearChooser();
		yearOfNow.getYear();

		displayAssets(tableAssets, "select date,asset_class,asset_name,description," + "location,asset_status,"
				+ "asset_cost,depreciation,accumulated_depreciation," + "`" + yearOfNow.getYear() + "` "
				+ "from school_fixed_assets where " + "asset_status is not null UNION "
				+ "SELECT date, '' as asset_class, account_name, details, '' as location, '' as status,"
				+ " sum(debit), '' as depreciation, sum(credit), "
				+ "sum(debit)-sum(credit) from student_ledger where debit is not null group by account_name");
		displayInComboBox(fieldAsset,
				"select scatname from subcategory where account_type='Current Asset' or account_type='Fixed Asset'");

	}

	public void displayInComboBox(JComboBox<String> combo, String query) {
		Connection conn = null;
		PreparedStatement pst = null;
		ResultSet rs = null;

		try {

			conn = CashBookController.getConnection();
			pst = conn.prepareStatement(query);
			rs = pst.executeQuery();

			combo.removeAllItems();
			combo.removeAll();
			while (rs.next()) {

				combo.addItem(rs.getString(1));
			}

		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
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

	public void AddItem(String querries) {

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

	public Date convertFromUtilToSQLDate(java.util.Date dateUtil) {

		if (dateUtil != null) {
			java.sql.Date sqlDate = new java.sql.Date(dateUtil.getTime());

			return sqlDate;
		} else {
			return null;
		}
	}

	public void displayAssetInfo() {
		try {

			java.sql.Connection conn = null;
			java.sql.PreparedStatement pst = null;
			conn = CashBookController.getConnection();
			pst = conn.prepareStatement(
					"select * from school_fixed_assets where asset_name='" + fieldAsset.getSelectedItem() + "'");

			ResultSet rs = pst.executeQuery();

			while (rs.next()) {
				fieldClass.setText(rs.getString("asset_class"));
				fieldLocation.setText(rs.getString("location"));
				fieldDescription.setText(rs.getString("description"));
				fieldDepreciation.setText(rs.getString("depreciation"));
				fieldCostOrValuation.setText(rs.getString("asset_cost"));
				fieldStatus.setText(rs.getString("asset_status"));
				dateChooserDate.setDate(rs.getDate("date"));
			}
		} catch (Exception ex) {
			ex.printStackTrace();
			JOptionPane.showMessageDialog(null, "Request Not Executed Successfully " + ex.getMessage());

		}

	}

	public void displayAssets(JTable table, String query) {

		try {

			conn = CashBookController.getConnection();
			PreparedStatement pst = conn.prepareStatement(query);
			ResultSet rs = pst.executeQuery();
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

}
