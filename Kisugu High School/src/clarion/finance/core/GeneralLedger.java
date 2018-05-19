package clarion.finance.core;

import java.math.BigDecimal;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.Date;

public class GeneralLedger {

	private BigDecimal id;

	private Date date;
	private boolean isDr;
	private boolean isCr;

	private String voucherNumber;
	private String details;

	private String accountName;

	private BigDecimal amountDr;
	private BigDecimal amountCr;

	private String amountDrString;
	private String amountCrString;

	private DecimalFormat df;

	private BigDecimal amountGeneral;

	private String balanceString;
	private BigDecimal balance;
	private BigDecimal amountDr1;
	private BigDecimal amountCr1;
	private BigDecimal amountCr2;
	private BigDecimal amountDr2;

	// constructor with all the native fields
	
	public GeneralLedger(BigDecimal id,BigDecimal amountDr1, BigDecimal amountCr1,BigDecimal amountCr2, BigDecimal amountDr2) {
		super();
		this.id = id;

		this.amountDr1 = amountDr1;
		this.amountCr1 = amountCr1;
		this.amountDr2 = amountDr2;
		this.amountCr2 = amountCr2;
	}

	public GeneralLedger(BigDecimal id, Date date, boolean isDr, boolean isCr, String accountName, String voucherNumber,
			String details, BigDecimal amountDr, BigDecimal amountCr) {
		super();
		this.id = id;

		this.date = date;
		this.isDr = isDr;
		this.isCr = isCr;

		this.accountName = accountName;
		this.voucherNumber = voucherNumber;
		this.details = details;

		this.amountDr = amountDr;
		this.amountCr = amountCr;
	}

	public GeneralLedger(Date date, boolean isDr, boolean isCr, String accountName, String voucherNumber,
			String details, BigDecimal amount) {
		super();

		this.date = date;
		this.isDr = isDr;
		this.isCr = isCr;

		this.accountName = accountName;
		this.voucherNumber = voucherNumber;
		this.details = details;

		if (isDr) {
			this.amountDr = amount;
		} else if (isCr) {
			this.amountCr = amount;
		}

	}

	public GeneralLedger(Date date, boolean isDr, boolean isCr, String accountName, String voucherNumber,
			String details, BigDecimal amount, BigDecimal balance) {
		super();

		this.date = date;
		this.isDr = isDr;
		this.isCr = isCr;

		this.accountName = accountName;
		this.voucherNumber = voucherNumber;
		this.details = details;

		if (isDr) {
			this.amountDr = amount;
		} else if (isCr) {
			this.amountCr = amount;
		}

		this.balance = balance;

	}

	

	public GeneralLedger(Date date, boolean isDr, boolean isCr, String accountName, String voucherNumber,
			String details, BigDecimal amountDr, BigDecimal amountCr, BigDecimal balance) {
		super();
		this.date = date;
		this.isDr = isDr;
		this.isCr = isCr;
		this.accountName = accountName;
		this.voucherNumber = voucherNumber;
		this.details = details;
		this.amountDr = amountDr;
		this.amountCr = amountCr;
		this.balance = balance;
	}

	public GeneralLedger(Date date, boolean isDr, boolean isCr, String accountName, String voucherNumber,
			String details, String amountDrString, String amountCrString) {
		super();
		this.date = date;
		this.isDr = isDr;
		this.isCr = isCr;
		this.accountName = accountName;
		this.voucherNumber = voucherNumber;
		this.details = details;
		this.amountDrString = amountDrString;
		this.amountCrString = amountCrString;
	}

	// String with id
	public GeneralLedger(BigDecimal id, Date date, boolean isDr, boolean isCr, String accountName, String voucherNumber,
			String details, String amountDrString, String amountCrString, String balanceString) {
		super();
		this.id = id;
		this.date = date;
		this.isDr = isDr;
		this.isCr = isCr;
		this.accountName = accountName;
		this.voucherNumber = voucherNumber;
		this.details = details;
		this.amountDrString = amountDrString;
		this.amountCrString = amountCrString;
		this.balanceString = balanceString;
	}

	public GeneralLedger(Date date, boolean isDr, boolean isCr, String accountName, String voucherNumber,
			String details, String amountDrString, String amountCrString, String balanceString) {
		super();
		this.date = date;
		this.isDr = isDr;
		this.isCr = isCr;
		this.accountName = accountName;
		this.voucherNumber = voucherNumber;
		this.details = details;
		this.amountDrString = amountDrString;
		this.amountCrString = amountCrString;
		this.balanceString = balanceString;
	}

	public GeneralLedger() {
		// TODO Auto-generated constructor stub
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

	public boolean isDr() {
		return isDr;
	}

	public boolean isCr() {
		return isCr;
	}

	public String getVoucherNumber() {
		return voucherNumber;
	}

	public String getDetails() {
		return details;
	}

	public BigDecimal getAmountDr() {
		return amountDr;
	}

	public BigDecimal getAmountCr() {
		return amountCr;
	}

	public DecimalFormat getDf() {
		return df;
	}

	public void setDf(DecimalFormat df) {
		this.df = df;
	}

	public String getAmountDrString() {
		return amountDrString;
	}

	public String getAmountDrString(BigDecimal bigDecimal) {
		this.amountDr = bigDecimal;
		setDf(new DecimalFormat("#,###.####"));
		return getDf().format(bigDecimal);
	}

	public String getAmountCrString() {
		return amountCrString;
	}

	public String getAmountCrString(BigDecimal bigDecimal) {
		this.amountCr = bigDecimal;
		setDf(new DecimalFormat("#,###.####"));
		return getDf().format(bigDecimal);
	}

	public void setId(BigDecimal id) {
		this.id = id;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public void setDr(boolean isDr) {
		this.isDr = isDr;
	}

	public void setCr(boolean isCr) {
		this.isCr = isCr;
	}

	public void setVoucherNumber(String voucherNumber) {
		this.voucherNumber = voucherNumber;
	}

	public void setDetails(String details) {
		this.details = details;
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

	public String getAccountName() {
		return accountName;
	}

	public void setAccountName(String accountName) {
		this.accountName = accountName;
	}

	public BigDecimal getAmountGeneral() {
		return amountGeneral;
	}

	public void setAmountGeneral(BigDecimal amountGeneral) {
		this.amountGeneral = amountGeneral;
	}

	@Override
	public String toString() {
		return "GeneralLedger [id=" + id + ", date=" + date + ", isDr=" + isDr + ", isCr=" + isCr + ", voucherNumber="
				+ voucherNumber + ", details=" + details + ", accountName=" + accountName + ", amountDrString="
				+ amountDrString + ", amountCrString=" + amountCrString + ", df=" + df + ", balanceString="
				+ balanceString + "]";
	}

}
