package org.spes.bean;

import java.sql.Timestamp;

/**
 * StaffFormula entity. @author MyEclipse Persistence Tools
 */

public class StaffFormula implements java.io.Serializable {

	// Fields

	private Integer formulaId;
	private String itemName;
	private String caclulator;
	private Timestamp useTime;
	private Integer itemId;
	private String backup1;
	private String backup2;
	private String backup3;
	private String backup4;
	private String backup5;

	// Constructors

	/** default constructor */
	public StaffFormula() {
	}

	/** full constructor */
	public StaffFormula(String itemName, String caclulator, Timestamp useTime,
			Integer itemId, String backup1, String backup2, String backup3,
			String backup4, String backup5) {
		this.itemName = itemName;
		this.caclulator = caclulator;
		this.useTime = useTime;
		this.itemId = itemId;
		this.backup1 = backup1;
		this.backup2 = backup2;
		this.backup3 = backup3;
		this.backup4 = backup4;
		this.backup5 = backup5;
	}

	// Property accessors

	public Integer getFormulaId() {
		return this.formulaId;
	}

	public void setFormulaId(Integer formulaId) {
		this.formulaId = formulaId;
	}

	public String getItemName() {
		return this.itemName;
	}

	public void setItemName(String itemName) {
		this.itemName = itemName;
	}

	public String getCaclulator() {
		return this.caclulator;
	}

	public void setCaclulator(String caclulator) {
		this.caclulator = caclulator;
	}

	public Timestamp getUseTime() {
		return this.useTime;
	}

	public void setUseTime(Timestamp useTime) {
		this.useTime = useTime;
	}

	public Integer getItemId() {
		return this.itemId;
	}

	public void setItemId(Integer itemId) {
		this.itemId = itemId;
	}

	public String getBackup1() {
		return this.backup1;
	}

	public void setBackup1(String backup1) {
		this.backup1 = backup1;
	}

	public String getBackup2() {
		return this.backup2;
	}

	public void setBackup2(String backup2) {
		this.backup2 = backup2;
	}

	public String getBackup3() {
		return this.backup3;
	}

	public void setBackup3(String backup3) {
		this.backup3 = backup3;
	}

	public String getBackup4() {
		return this.backup4;
	}

	public void setBackup4(String backup4) {
		this.backup4 = backup4;
	}

	public String getBackup5() {
		return this.backup5;
	}

	public void setBackup5(String backup5) {
		this.backup5 = backup5;
	}

}