package clarion.finance.dao;

import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.Observable;

import clarion.finance.core.Accounts;
import clarion.finance.core.SubCategory;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class AccountDAO {

	/*
	 * Return category table to determine the Accounts "catid" This helps to
	 * direct the insertion into 'subcategory' table under the column 'scatname'
	 * whose value for one of the cells is 'Accounts'
	 * 
	 */

	private BigDecimal determineAccountsCatID() throws SQLException {
		BigDecimal categoryID = null;
		PreparedStatement myStmt = null;
		ResultSet myRs = null;

		try {
			myStmt = AccountConnect.getConnection()
					.prepareStatement("select catid from category where catname='Accounts'");
			myRs = myStmt.executeQuery();

			while (myRs.next()) {
				// BigDecimal id = myRs.getBigDecimal("id");
				BigDecimal catid = myRs.getBigDecimal("catid");
				// String catname = myRs.getString("catname");

				categoryID = catid;

			}

			return categoryID;

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			AccountConnect.closeAll();
		}
		return categoryID;
	}

	public void newAccountUnderAccountsCategory(SubCategory subCategory) throws SQLException {
		PreparedStatement myStmt = null;
		try {
			myStmt = AccountConnect.getConnection()
					.prepareStatement("insert into subcategory(catid, scatname) values(?,?)");

			myStmt.setBigDecimal(1, this.determineAccountsCatID());
			myStmt.setString(2, subCategory.getSubCatgeoryName());

			myStmt.executeUpdate();

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			AccountConnect.closeStmtAndConn();
		}

	}

	public void newAccount(Accounts accounts) throws SQLException {
		PreparedStatement myStmt = null;

		try {
			myStmt = AccountConnect.getConnection()
					.prepareStatement("insert into account_created_details("
							// 1
							+ "account_name,"
							// 2
							+ "details" + "" + ") values("
							// 1
							+ "?,"
							// 2
							+ "?" + ")");

			Date utilDate = accounts.getDate();
			java.sql.Date sqlDate = null;

			if (utilDate != null) {
				sqlDate = new java.sql.Date(utilDate.getTime());
			}

			myStmt.setString(1, accounts.getAccountName());
			myStmt.setString(2, accounts.getDetails());

			myStmt.executeUpdate();

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			AccountConnect.closeStmtAndConn();
		}

	}

	public ObservableList<String> getAllAccountNames() throws SQLException {
		ObservableList<String> list = FXCollections.observableArrayList();
		PreparedStatement myStmt = null;
		ResultSet myRs = null;

		try {
			myStmt = AccountConnect.getConnection()
					.prepareStatement("select account_name from account_created_details");
			myRs = myStmt.executeQuery();
			while (myRs.next()) {
				String accountName = myRs.getString("account_name");
				list.add(accountName);
			}

			return list;

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			AccountConnect.closeAll();
		}
		return list;
	}

	public ObservableList<String> getAllAccountDetails() throws SQLException {
		ObservableList<String> list = FXCollections.observableArrayList();
		PreparedStatement myStmt = null;
		ResultSet myRs = null;

		try {
			myStmt = AccountConnect.getConnection().prepareStatement("select details from account_created_details");
			myRs = myStmt.executeQuery();
			while (myRs.next()) {
				String accountName = myRs.getString("details");
				list.add(accountName);
			}

			return list;

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			AccountConnect.closeAll();
		}
		return list;
	}

	public ObservableList<Accounts> getAllAccountsToTable() throws SQLException {
		ObservableList<Accounts> accounts = FXCollections.observableArrayList();
		PreparedStatement myStmt = null;
		ResultSet myRs = null;

		try {
			myStmt = AccountConnect.getConnection().prepareStatement("select * from accounts");
			myRs = myStmt.executeQuery();

			while (myRs.next()) {

				BigDecimal id = myRs.getBigDecimal("id");
				Date date = myRs.getDate("date");
				String accountName = myRs.getString("account_name");

				String voucherNumber = myRs.getString("voucher_number");
				String details = myRs.getString("details");
				String folio = myRs.getString("folio");

				BigDecimal amountDr = myRs.getBigDecimal("amount_dr");
				BigDecimal amountCr = myRs.getBigDecimal("amount_cr");

				boolean isDr = myRs.getBoolean("is_dr");
				boolean isCr = myRs.getBoolean("is_cr");

				BigDecimal balance = myRs.getBigDecimal("balance");

				Accounts accounts2 = new Accounts();

				String amountDrStr = accounts2.getAmountDrString(amountDr);

				String amountDrString = null;

				if (amountDrStr.equals("0")) {
					amountDrStr = "";
				}

				String amountCrStr = accounts2.getAmountCrString(amountCr);
				if (amountCrStr.equals("0")) {
					amountCrStr = "";
				}

				String balanceString = accounts2.getBalanceString(balance);

				Accounts account = null;

				Accounts accounts3 = new Accounts(id, date, accountName, voucherNumber, details, folio, amountDr,
						amountCr);

				account = new Accounts(id, date, accountName, voucherNumber, details, folio, amountDrStr, amountCrStr,
						isDr, isCr, balanceString);

				accounts.add(account);

			}

			return accounts;

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			AccountConnect.closeAll();
		}
		return accounts;
	}
	
	
	
	public ObservableList<String> getAccountNamesToAccountsTreeNode() throws SQLException {
		ObservableList<String> list = FXCollections.observableArrayList();
		PreparedStatement myStmt = null;
		ResultSet myRs = null;

		try {
			myStmt = AccountConnect.getConnection().prepareStatement("select account_name from accounts");
			myRs = myStmt.executeQuery();

			while (myRs.next()) {
				String accountName = myRs.getString("account_name");
				list.add(accountName);
			}
			return list;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			AccountConnect.closeAll();
		}

		return list;

	}

	public static void main(String[] args) throws SQLException {
		System.out.println(new AccountDAO().determineAccountsCatID());
	}

}
