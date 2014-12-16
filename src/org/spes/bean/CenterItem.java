package org.spes.bean;

/**
 * CenterItem entity. @author MyEclipse Persistence Tools
 */

public class CenterItem implements java.io.Serializable {

	// Fields

	private Integer itemId;
	private String itemName;
	private Integer parentId;
	private Integer itemGrade;
	private Double itemWeight;
	private Boolean enabled;
	private String itemType;
	private Integer centerId;
	private String backup1;
	private String backup2;
	private String backup3;
	private String backup4;
	private String backup5;

	// Constructors

	/** default constructor */
	public CenterItem() {
	}

	/** full constructor */
	public CenterItem(String itemName, Integer parentId, Integer itemGrade,
			Double itemWeight, Boolean enabled, String itemType,
			Integer centerId, String backup1, String backup2, String backup3,
			String backup4, String backup5) {
		this.itemName = itemName;
		this.parentId = parentId;
		this.itemGrade = itemGrade;
		this.itemWeight = itemWeight;
		this.enabled = enabled;
		this.itemType = itemType;
		this.centerId = centerId;
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

	public Boolean getEnabled() {
		return this.enabled;
	}

	public void setEnabled(Boolean enabled) {
		this.enabled = enabled;
	}

	public String getItemType() {
		return this.itemType;
	}

	public void setItemType(String itemType) {
		this.itemType = itemType;
	}

	public Integer getCenterId() {
		return this.centerId;
	}

	public void setCenterId(Integer centerId) {
		this.centerId = centerId;
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

	public boolean equals(Object obj) {
		if (obj == null)
			return false;
		if (this == obj)
			return true;
		if (getClass() == obj.getClass()) {
			CenterItem ci = (CenterItem) obj;
			return this.itemName.equals(ci.itemName)
					&& this.itemGrade.equals(ci.itemGrade)
					&& this.itemType.equals(ci.itemType)
					&& this.itemWeight.equals(ci.itemWeight)
					&& this.centerId.equals(ci.centerId)
					&& this.enabled.equals(ci.enabled)
					&& this.parentId.equals(ci.parentId);

		}
		return false;
	}
	
	
	public int hashCode(){
		int result = 17;
		return result;
	}
	
	public String toString() {
		return "[itemId=" + itemId + ",itemName=" + itemName + ",parentId="
				+ parentId + ",itemGrade=" + itemGrade + ",itemWeight="
				+ itemWeight + ",itemType=" + itemType + ",centerId="
				+ centerId;
	}
}