package org.spes.bean;

/**
 * StaffItem entity. @author MyEclipse Persistence Tools
 */

public class StaffItem implements java.io.Serializable {

	// Fields

	private Integer itemId;
	private String itemName;
	private Integer parentId;
	private Integer itemGrade;
	private Double itemWeight;
	private String itemType;
	private Boolean enabled;
	private Integer centerId;
	private Integer windowId;
	private String backup1;
	private String backup2;
	private String backup3;
	private String backup4;
	private String backup5;

	// Constructors

	/** default constructor */
	public StaffItem() {
	}

	/** full constructor */
	public StaffItem(String itemName, Integer parentId, Integer itemGrade,
			Double itemWeight, String itemType, Boolean enabled,
			Integer centerId, Integer windowId,String backup1, String backup2, String backup3,
			String backup4, String backup5) {
		this.itemName = itemName;
		this.parentId = parentId;
		this.itemGrade = itemGrade;
		this.itemWeight = itemWeight;
		this.itemType = itemType;
		this.enabled = enabled;
		this.centerId = centerId;
		this.windowId = windowId;
		this.backup1 = backup1;
		this.backup2 = backup2;
		this.backup3 = backup3;
		this.backup4 = backup4;
		this.backup5 = backup5;
	}

	// Property accessors

	public Integer getItemId() {
		return this.itemId;
	}

	public void setItemId(Integer itemId) {
		this.itemId = itemId;
	}

	public String getItemName() {
		return this.itemName;
	}

	public void setItemName(String itemName) {
		this.itemName = itemName;
	}

	public Integer getParentId() {
		return this.parentId;
	}

	public void setParentId(Integer parentId) {
		this.parentId = parentId;
	}

	public Integer getItemGrade() {
		return this.itemGrade;
	}

	public void setItemGrade(Integer itemGrade) {
		this.itemGrade = itemGrade;
	}

	public Double getItemWeight() {
		return this.itemWeight;
	}

	public void setItemWeight(Double itemWeight) {
		this.itemWeight = itemWeight;
	}

	public String getItemType() {
		return this.itemType;
	}

	public void setItemType(String itemType) {
		this.itemType = itemType;
	}

	public Boolean getEnabled() {
		return this.enabled;
	}

	public void setEnabled(Boolean enabled) {
		this.enabled = enabled;
	}

	public Integer getCenterId() {
		return this.centerId;
	}

	public void setCenterId(Integer centerId) {
		this.centerId = centerId;
	}

	public Integer getWindowId() {
		return this.windowId;
	}

	public void setWindowId(Integer windowId) {
		this.windowId = windowId;
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

	public String getBackup1() {
		return backup1;
	}

	public void setBackup1(String backup1) {
		this.backup1 = backup1;
	}
	

}