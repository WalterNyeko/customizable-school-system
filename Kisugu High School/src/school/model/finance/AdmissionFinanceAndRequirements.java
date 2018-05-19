package school.model.finance;

import java.math.BigDecimal;
import java.time.Year;

public class AdmissionFinanceAndRequirements {

	private BigDecimal admissionFees;
	private String classForTheFees;
	private String term;
	private Year year;

	public AdmissionFinanceAndRequirements(BigDecimal admissionFees, String classForTheFees, String term, Year year) {
		super();
		this.admissionFees = admissionFees;
		this.classForTheFees = classForTheFees;
		this.term = term;
		this.year = year;
	}

	public BigDecimal getAdmissionFees() {
		return admissionFees;
	}

	public void setAdmissionFees(BigDecimal admissionFees) {
		this.admissionFees = admissionFees;
	}

	public String getClassForTheFees() {
		return classForTheFees;
	}

	public void setClassForTheFees(String classForTheFees) {
		this.classForTheFees = classForTheFees;
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
