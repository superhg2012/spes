package org.spes.bean;

/**
 * Evaluatesheet entity. @author MyEclipse Persistence Tools
 */

public class Evaluatesheet implements java.io.Serializable {

	// Fields

	private Integer sheetId;
	private String sheetName;
	private String sheetState;
	private String userName;
	private String createTime;
	private String lastUpdate;
	private String sheetType;
	private String checkType;
	private String backup3;
	private String backup4;
	private String backup5;

	// Constructors

	/** default constructor */
	public Evaluatesheet() {
	}

	/** full constructor */
	public Evaluatesheet(String sheetName, String sheetState, String userName,
			String createTime, String lastUpdate, String sheetType,
			String checkType, String backup3, String backup4, String backup5) {
		this.sheetName = sheetName;
		this.sheetState = sheetState;
		this.userName = userName;
		this.createTime = createTime;
		this.lastUpdate = lastUpdate;
		this.sheetType = sheetType;
		this.checkType = checkType;
		this.backup3 = backup3;
		this.backup4 = backup4;
		this.backup5 = backup5;
	}

	// Property accessors

	public Integer getSheetId() {
		return this.sheetId;
	}

	public void setSheetId(Integer sheetId) {
		this.sheetId = sheetId;
	}

	public String getSheetName() {
		return this.sheetName;
	}

	public void setSheetName(String sheetName) {
		this.sheetName = sheetName;
	}

	public String getSheetState() {
		return this.sheetState;
	}

	public void setSheetState(String sheetState) {
		this.sheetState = sheetState;
	}

	public String getUserName() {
		return this.userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getCreateTime() {
		return this.createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

	public String getLastUpdate() {
		return this.lastUpdate;
	}

	public void setLastUpdate(String lastUpdate) {
		this.lastUpdate = lastUpdate;
	}


	public String getSheetType() {
		return sheetType;
	}

	public void setSheetType(String sheetType) {
		this.sheetType = sheetType;
	}

	public String getCheckType() {
		return this.checkType;
	}

	public void setCheckType(String checkType) {
		this.checkType = checkType;
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