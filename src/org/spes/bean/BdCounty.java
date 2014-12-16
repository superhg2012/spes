package org.spes.bean;

/**
 * BdCounty entity. @author MyEclipse Persistence Tools
 */

public class BdCounty implements java.io.Serializable {

	// Fields

	private Integer id;
	private String name;
	private String code;
	private String shrtMm;
	private String enabled;

	// Constructors

	/** default constructor */
	public BdCounty() {
	}

	/** full constructor */
	public BdCounty(String name, String code, String shrtMm, String enabled) {
		this.name = name;
		this.code = code;
		this.shrtMm = shrtMm;
		this.enabled = enabled;
	}

	// Property accessors

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
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

	public String getShrtMm() {
		return this.shrtMm;
	}

	public void setShrtMm(String shrtMm) {
		this.shrtMm = shrtMm;
	}

	public String getEnabled() {
		return this.enabled;
	}

	public void setEnabled(String enabled) {
		this.enabled = enabled;
	}

}