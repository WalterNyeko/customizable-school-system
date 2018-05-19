package school.model.finance;

import java.math.BigDecimal;
import java.time.Year;

public class FinanceNormalTermFees {

	private String theClass;
	private BigDecimal fees;
	private String term;
	private Year year;

	public FinanceNormalTermFees(String theClass, BigDecimal fees, String term, Year year) {
		super();
		this.theClass = theClass;
		this.fees = fees;
		this.term = term;
		this.year = year;
	}

	public String getTheClass() {
		return theClass;
	}

	public void setTheClass(String theClass) {
		this.theClass = theClass;
	}

	public BigDecimal getFees() {
		return fees;
	}

	public void setFees(BigDecimal fees) {
		this.fees = fees;
	}

	public String getTerm() {
		return term;
	}

	public void setTerm(String term) {
		this.term = term;
	}

	public Year getYear() {
		return year;
	}

	public void setYear(Year year) {
		this.year = year;
	}

}
