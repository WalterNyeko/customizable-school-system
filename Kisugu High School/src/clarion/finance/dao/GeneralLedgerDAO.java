package clarion.finance.dao;

import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

import clarion.finance.core.Accounts;
import clarion.finance.core.GeneralLedger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class GeneralLedgerDAO {

	public void newTransactionDr(GeneralLedger ledger, Accounts accounts) throws SQLException {
		PreparedStatement myStmt = null;
		try {
			myStmt = AccountConnect.getConnection().prepareStatement("insert into general_ledger("
					// 1
					+ "date, "
					// 2
					+ "is_dr,"
					// 3
					+ "is_cr,"
					// 4
					+ "account_name,"
					// 5
					+ "voucher_number,"
					// 6
					+ "details,"
					// 7
					+ "amount_dr,"
					// 8
					+ "balance" + "" + "" + "" + "" + "" + ") values("
					// 1
					+ "?,"
					// 2
					+ "?,"
					// 3
					+ "?,"
					// 4
					+ "?,"
					// 5
					+ "?,"
					// 6
					+ "?,"
					// 7
					+ "?,"
					// 8
					+ "?" + ")");

			Date utilDate = ledger.getDate();
			java.sql.Date sqlDate = null;

			if (utilDate != null) {
				sqlDate = new java.sql.Date(utilDate.getTime());
			}

			myStmt.setDate(1, sqlDate);
			myStmt.setBoolean(2, ledger.isDr());

			myStmt.setBoolean(3, ledger.isCr());
			myStmt.setString(4, ledger.getAccountName());

			myStmt.setString(5, ledger.getVoucherNumber());
			myStmt.setString(6, ledger.getDetails());

			myStmt.setBigDecimal(7, ledger.getAmountDr());

			myStmt.setBigDecimal(8, ledger.getBalance());

			myStmt.executeUpdate();

			AccountConnect.closeStmtAndConn();

			myStmt = AccountConnect.getConnection().prepareStatement("insert into accounts("
					// 1
					+ "date," + ""
					// 2
					+ "account_name,"
					// 3
					+ "voucher_number,"
					// 4
					+ "details,"
					// 5
					+ "folio,"
					// 6
					+ "amount_dr,"
					// 7
					+ "is_dr,"
					// 8
					+ "is_cr,"
					// 9
					+ "balance" + "" + ""
					//
					+ "" + ") values("
					// 1
					+ "?,"
					// 2
					+ "?,"
					// 3
					+ "?,"
					// 4
					+ "?,"
					// 5
					+ "?,"
					// 6
					+ "?,"
					// 7
					+ "?,"
					// 8
					+ "?,"
					// 9
					+ "?" + "" + ")");

			Date utilDate1 = accounts.getDate();
			java.sql.Date sqlDate1 = null;

			if (utilDate1 != null) {
				sqlDate1 = new java.sql.Date(utilDate1.getTime());
			}

			myStmt.setDate(1, sqlDate1);
			myStmt.setString(2, accounts.getAccountName());

			myStmt.setString(3, accounts.getVoucherNumber());
			myStmt.setString(4, accounts.getDetails());

			myStmt.setString(5, accounts.getFolio());
			myStmt.setBigDecimal(6, accounts.getAmountDr());

			myStmt.setBoolean(7, accounts.isDr());
			myStmt.setBoolean(8, accounts.isCr());

			myStmt.setBigDecimal(9, accounts.getBalance());

			myStmt.executeUpdate();

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			AccountConnect.closeStmtAndConn();
		}
	}

	public void newTransactionCr(GeneralLedger ledger, Accounts accounts) throws SQLException {
		PreparedStatement myStmt = null;
		try {
			myStmt = AccountConnect.getConnection().prepareStatement("insert into general_ledger("
					// 1
					+ "date, "
					// 2
					+ "is_dr,"
					// 3
					+ "is_cr,"
					// 4
					+ "account_name,"
					// 5
					+ "voucher_number,"
					// 6
					+ "details,"
					// 7
					+ "amount_cr,"
					// 8
					+ "balance" + "" + "" + "" + ") values("
					// 1
					+ "?,"
					// 2
					+ "?,"
					// 3
					+ "?,"
					// 4
					+ "?,"
					// 5
					+ "?,"
					// 6
					+ "?,"
					// 7
					+ "?,"
					// 8
					+ "?" + "" + ")");

			Date utilDate = ledger.getDate();
			java.sql.Date sqlDate = null;

			if (utilDate != null) {
				sqlDate = new java.sql.Date(utilDate.getTime());
			}

			myStmt.setDate(1, sqlDate);
			myStmt.setBoolean(2, ledger.isDr());

			myStmt.setBoolean(3, ledger.isCr());
			myStmt.setString(4, ledger.getAccountName());

			myStmt.setString(5, ledger.getVoucherNumber());
			myStmt.setString(6, ledger.getDetails());

			myStmt.setBigDecimal(7, ledger.getAmountCr());
			myStmt.setBigDecimal(8, ledger.getBalance());

			myStmt.executeUpdate();

			AccountConnect.closeStmtAndConn();

			myStmt = AccountConnect.getConnection().prepareStatement("insert into accounts("
					// 1
					+ "date," + ""
					// 2
					+ "account_name,"
					// 3
					+ "voucher_number,"
					// 4
					+ "details,"
					// 5
					+ "folio,"
					// 6
					+ "amount_cr,"
					// 7
					+ "is_dr,"
					// 8
					+ "is_cr,"
					// 9
					+ "balance" + ""
					//
					+ "" + ") values("
					// 1
					+ "?,"
					// 2
					+ "?,"
					// 3
					+ "?,"
					// 4
					+ "?,"
					// 5
					+ "?,"
					// 6
					+ "?,"
					// 7
					+ "?,"
					// 8
					+ "?,"
					// 9
					+ "?" + "" + ")");

			Date utilDate1 = accounts.getDate();
			java.sql.Date sqlDate1 = null;

			if (utilDate1 != null) {
				sqlDate1 = new java.sql.Date(utilDate1.getTime());
			}

			myStmt.setDate(1, sqlDate1);
			myStmt.setString(2, accounts.getAccountName());

			myStmt.setString(3, accounts.getVoucherNumber());
			myStmt.setString(4, accounts.getDetails());

			myStmt.setString(5, accounts.getFolio());
			myStmt.setBigDecimal(6, accounts.getAmountCr());

			myStmt.setBoolean(7, accounts.isDr());
			myStmt.setBoolean(8, accounts.isCr());

			myStmt.setBigDecimal(9, accounts.getBalance());

			myStmt.executeUpdate();

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			AccountConnect.closeStmtAndConn();
		}
	}

//	public ObservableList<Accounts> getAllAccountsToTable() throws SQLException {
//		ObservableList<Accounts> accounts = FXCollections.observableArrayList();
//		PreparedStatement myStmt = null;
//		ResultSet myRs = null;
//		try {
//			myStmt = AccountConnect.getConnection().prepareStatement("select * from general_le");
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		return accounts;
//	}

	// populate general ledger table

	public ObservableList<GeneralLedger> populateGeneralLegderTable() throws SQLException {
		ObservableList<GeneralLedger> ledgers = FXCollections.observableArrayList();
		PreparedStatement myStmt = null;
		ResultSet myRs = null;

		try {
			myStmt = AccountConnect.getConnection().prepareStatement("select * from general_ledger");

			myRs = myStmt.executeQuery();

			while (myRs.next()) {
				BigDecimal id = myRs.getBigDecimal("id");

				Date date = myRs.getDate("date");
				boolean isDr = myRs.getBoolean("is_dr");
				boolean isCr = myRs.getBoolean("is_cr");

				String accountName = myRs.getString("account_name");
				String voucherNumber = myRs.getString("voucher_number");
				String details = myRs.getString("details");

				BigDecimal amountDr = myRs.getBigDecimal("amount_dr");
				BigDecimal amountCr = myRs.getBigDecimal("amount_cr");
				BigDecimal balance = myRs.getBigDecimal("balance");

				GeneralLedger ledger = new GeneralLedger();

				String amountDrString = ledger.getAmountDrString(amountDr);
				if (amountDrString.equals("0")) {
					amountDrString = "";
				}
				String amountCrString = ledger.getAmountCrString(amountCr);
				if (amountCrString.equals("0")) {
					amountCrString = "";
				}

				String balanceString = ledger.getBalanceString(balance);
				// if (balanceString.equals("0")) {
				// balanceString = "Completed";
				// }

				GeneralLedger ledger2 = new GeneralLedger(id, date, isDr, isCr, accountName, voucherNumber, details,
						amountDrString, amountCrString, balanceString);

				ledgers.add(ledger2);

			}

			return ledgers;

		} catch (Exception e) {
			e.printStackTrace();
		}
		return ledgers;
	}
	
	public ObservableList<GeneralLedger> populateExpenditureTable() throws SQLException {
		ObservableList<GeneralLedger> ledgers = FXCollections.observableArrayList();
		PreparedStatement myStmt = null;
		ResultSet myRs = null;

		try {
			myStmt = AccountConnect.getConnection().prepareStatement("select * from budget_expense_income_records where account_type='Expense'");

			myRs = myStmt.executeQuery();

			while (myRs.next()) {
				BigDecimal id = myRs.getBigDecimal("id");

				
				BigDecimal amountDr1 = myRs.getBigDecimal("FY2017/2018");
				BigDecimal amountCr1 = myRs.getBigDecimal("2018");
				BigDecimal amountCr2 = myRs.getBigDecimal("FY2018/2019");
				BigDecimal amountDr2 = myRs.getBigDecimal("2019");

				GeneralLedger ledger = new GeneralLedger();



				GeneralLedger ledger2 = new GeneralLedger(id,amountDr1,amountCr1,amountCr2,amountDr2);

				ledgers.add(ledger2);

			}

			return ledgers;

		} catch (Exception e) {
			e.printStackTrace();
		}
		return ledgers;
	}

	/*
	 * Return general_ledger with a single account to pick what to enter under
	 * 'Accounts'
	 */

	public static void main(String[] args) throws SQLException {
		System.out.println(new GeneralLedgerDAO().populateGeneralLegderTable());
	}

}
