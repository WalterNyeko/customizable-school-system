package clarion.finance.core;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.Date;

public class Accounts {

	private BigDecimal id;

	private Date date;
	private String accountName;

	private String voucherNumber;

	private String details;

	private String folio;
	private BigDecimal amountDr;
	private BigDecimal amountCr;

	private String amountDrString;
	private String amountCrString;

	private String balanceString;
	private BigDecimal balance;

	private DecimalFormat df;

	// private GeneralLedger generalLedger;

	private boolean isDr;
	private boolean isCr;

	// default empty constructor to help format BigDecimal values
	public Accounts() {

	}

	// constructor with all the native data types for all the fields considered
	// native
	public Accounts(BigDecimal id, Date date, String accountName, String voucherNumber, String details, String folio,
			BigDecimal amountDr, BigDecimal amountCr) {
		super();
		this.id = id;
		this.date = date;
		this.accountName = accountName;
		this.voucherNumber = voucherNumber;
		this.details = details;
		this.folio = folio;
		this.amountDr = amountDr;
		this.amountCr = amountCr;
	}

	public Accounts(Date date, String accountName, String voucherNumber, String details, String folio,
			BigDecimal amount, boolean isDr, boolean isCr) {
		super();
		this.date = date;
		this.accountName = accountName;
		this.voucherNumber = voucherNumber;
		this.details = details;
		this.folio = folio;

		this.isDr = isDr;
		this.isCr = isCr;

		if (isDr) {
			this.amountDr = amount;
		} else if (isCr) {
			this.amountCr = amount;
		}
	}

	public Accounts(Date date, String accountName, String voucherNumber, String details, String folio,
			BigDecimal amount, boolean isDr, boolean isCr, BigDecimal balance) {
		super();
		this.date = date;
		this.accountName = accountName;
		this.voucherNumber = voucherNumber;
		this.details = details;
		this.folio = folio;

		this.isDr = isDr;
		this.isCr = isCr;

		if (isDr) {
			this.amountDr = amount;
		} else if (isCr) {
			this.amountCr = amount;
		}

		this.balance = balance;
	}

	public Accounts(String accountName, String details) {
		super();
		this.accountName = accountName;
		this.details = details;
	}

	// constructor with all the native data types for all the fields considered
	// native
	// with exclusion of id
	public Accounts(Date date, String accountName, String voucherNumber, String details, String folio,
			BigDecimal amountDr, BigDecimal amountCr) {
		super();
		this.date = date;
		this.accountName = accountName;
		this.voucherNumber = voucherNumber;
		this.details = details;
		this.folio = folio;
		this.amountDr = amountDr;
		this.amountCr = amountCr;
	}

	// constructor with id exclusion, and replacement of all the BigDecimal
	// fields
	// with String values for formatting
	public Accounts(Date date, String accountName, String voucherNumber, String details, String folio,
			String amountDrString, String amountCrString) {
		super();
		this.date = date;
		this.accountName = accountName;
		this.voucherNumber = voucherNumber;
		this.details = details;
		this.folio = folio;
		this.amountDrString = amountDrString;
		this.amountCrString = amountCrString;
	}

	public Accounts(BigDecimal id, Date date, String accountName, String voucherNumber, String details, String folio,
			BigDecimal amountDr, BigDecimal amountCr, boolean isDr, boolean isCr) {
		super();
		this.id = id;
		this.date = date;
		this.accountName = accountName;
		this.voucherNumber = voucherNumber;
		this.details = details;
		this.folio = folio;
		this.amountDr = amountDr;
		this.amountCr = amountCr;
		this.isDr = isDr;
		this.isCr = isCr;
	}

	public Accounts(BigDecimal id, Date date, String accountName, String voucherNumber, String details, String folio,
			BigDecimal amountDr, BigDecimal amountCr, boolean isDr, boolean isCr, BigDecimal balance) {
		super();
		this.id = id;
		this.date = date;
		this.accountName = accountName;
		this.voucherNumber = voucherNumber;
		this.details = details;
		this.folio = folio;
		this.amountDr = amountDr;
		this.amountCr = amountCr;
		this.isDr = isDr;
		this.isCr = isCr;
		this.balance = balance;
	}

	public Accounts(BigDecimal id, Date date, String accountName, String voucherNumber, String details, String folio,
			String amountDrString, String amountCrString, boolean isDr, boolean isCr) {
		super();
		this.id = id;
		this.date = date;
		this.accountName = accountName;

		this.voucherNumber = voucherNumber;
		this.details = details;
		this.folio = folio;

		this.amountDrString = amountDrString;
		this.amountCrString = amountCrString;
		this.isDr = isDr;

		this.isCr = isCr;
	}

	public Accounts(BigDecimal id, Date date, String accountName, String voucherNumber, String details, String folio,
			String amountDrString, String amountCrString, boolean isDr, boolean isCr, String balanceString) {
		super();
		this.id = id;
		this.date = date;
		this.accountName = accountName;

		this.voucherNumber = voucherNumber;
		this.details = details;
		this.folio = folio;

		this.amountDrString = amountDrString;
		this.amountCrString = amountCrString;
		this.isDr = isDr;

		this.isCr = isCr;
		this.balanceString = balanceString;
	}

	public String getBalanceString() {
		return balanceString;
	}

	public String getBalanceString(BigDecimal bigDecimal) {
		balance = bigDecimal;
		setDf(new DecimalFormat("#,###.####"));
		return getDf().format(bigDecimal);
	}

	public BigDecimal getBalance() {
		return balance;
	}

	public void setBalanceString(String balanceString) {
		this.balanceString = balanceString;
	}

	public void setBalance(BigDecimal balance) {
		this.balance = balance;
	}

	public BigDecimal getId() {
		return id;
	}

	public Date getDate() {
		return date;
	}

	public String getAccountName() {
		return accountName;
	}

	public String getDetails() {
		return details;
	}

	public String getFolio() {
		return folio;
	}

	public BigDecimal getAmountDr() {
		return amountDr;
	}

	public BigDecimal getAmountCr() {
		return amountCr;
	}

	public String getAmountDrString() {
		return amountDrString;
	}

	public DecimalFormat getDf() {
		return df;
	}

	public void setDf(DecimalFormat df) {
		this.df = df;
	}

	public String getAmountDrString(BigDecimal bigDecimal) {
		amountDr = bigDecimal;
		setDf(new DecimalFormat("#,###.####"));
		return getDf().format(bigDecimal);
	}

	public String getAmountCrString(BigDecimal bigDecimal) {
		amountCr = bigDecimal;
		setDf(new DecimalFormat("#,###.####"));
		return getDf().format(bigDecimal);
	}

	public String getAmountCrString() {
		return amountCrString;
	}

	public void setId(BigDecimal id) {
		this.id = id;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public void setAccountName(String accountName) {
		this.accountName = accountName;
	}

	public void setDetails(String details) {
		this.details = details;
	}

	public void setFolio(String folio) {
		this.folio = folio;
	}

	public void setAmountDr(BigDecimal amountDr) {
		this.amountDr = amountDr;
	}

	public void setAmountCr(BigDecimal amountCr) {
		this.amountCr = amountCr;
	}

	public void setAmountDrString(String amountDrString) {
		this.amountDrString = amountDrString;
	}

	public void setAmountCrString(String amountCrString) {
		this.amountCrString = amountCrString;
	}

	public String getVoucherNumber() {
		return voucherNumber;
	}

	public void setVoucherNumber(String voucherNumber) {
		this.voucherNumber = voucherNumber;
	}

	public boolean isDr() {
		return isDr;
	}

	public boolean isCr() {
		return isCr;
	}

	public void setDr(boolean isDr) {
		this.isDr = isDr;
	}

	public void setCr(boolean isCr) {
		this.isCr = isCr;
	}

	@Override
	public String toString() {
		return "Accounts [id=" + id + ", date=" + date + ", accountName=" + accountName + ", details=" + details
				+ ", folio=" + folio + ", amountDrString=" + amountDrString + ", amountCrString=" + amountCrString
				+ "]";
	}

}
