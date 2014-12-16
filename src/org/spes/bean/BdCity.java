package org.spes.bean;

/**
 * BdCity entity. @author MyEclipse Persistence Tools
 */

public class BdCity implements java.io.Serializable {

	// Fields

	private Integer rowId;
	private String name;
	private String code;
	private String shrtNm;
	private Boolean enabled;

	// Constructors

	/** default constructor */
	public BdCity() {
	}

	/** minimal constructor */
	public BdCity(String name, Boolean enabled) {
		this.name = name;
		this.enabled = enabled;
	}

	/** full constructor */
	public BdCity(String name, String code, String shrtNm, Boolean enabled) {
		this.name = name;
		this.code = code;
		this.shrtNm = shrtNm;
		this.enabled = enabled;
	}

	// Property accessors

	public Integer getRowId() {
		return this.rowId;
	}

	public void setRowId(Integer rowId) {
		this.rowId = rowId;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCode() {
		return this.code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getShrtNm() {
		return this.shrtNm;
	}

	public void setShrtNm(String shrtNm) {
		this.shrtNm = shrtNm;
	}

	public Boolean getEnabled() {
		return this.enabled;
	}

	public void setEnabled(Boolean enabled) {
		this.enabled = enabled;
	}

}