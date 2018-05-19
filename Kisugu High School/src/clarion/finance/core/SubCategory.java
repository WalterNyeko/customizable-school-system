package clarion.finance.core;

import java.math.BigDecimal;

public class SubCategory {

	private BigDecimal id;
	private BigDecimal categoryId;
	private String subCatgeoryName;

	public SubCategory(BigDecimal id, BigDecimal categoryId, String subCatgeoryName) {
		super();
		this.id = id;
		this.categoryId = categoryId;
		this.subCatgeoryName = subCatgeoryName;
	}
	
	
	public SubCategory(String subCatgeoryName) {
		super();
		this.subCatgeoryName = subCatgeoryName;
	}

	public BigDecimal getId() {
		return id;
	}

	public void setId(BigDecimal id) {
		this.id = id;
	}

	public BigDecimal getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(BigDecimal categoryId) {
		this.categoryId = categoryId;
	}

	public String getSubCatgeoryName() {
		return subCatgeoryName;
	}

	public void setSubCatgeoryName(String subCatgeoryName) {
		this.subCatgeoryName = subCatgeoryName;
	}

	@Override
	public String toString() {
		return "SubCategory [id=" + id + ", categoryId=" + categoryId + ", subCatgeoryName=" + subCatgeoryName + "]";
	}

}
