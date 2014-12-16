package org.spes.bean;

import java.sql.Timestamp;

/**
 * CenterParam entity. @author MyEclipse Persistence Tools
 */

public class CenterParam implements java.io.Serializable {

	// Fields

	private Integer id;
	private String itemName;
	private Double itemValue;
	private Timestamp evaluateDate;
	private Integer itemId;
	private Integer centerId;
	private String backup1;
	private String backup2;
	private String backup3;
	private String backup4;
	private String backup5;

	// Constructors

	/** default constructor */
	public CenterParam() {
	}

	/** full constructor */
	public CenterParam(String itemName, Double itemValue,
			Timestamp evaluateDate, Integer itemId, Integer centerId,
			String backup1, String backup2, String backup3, String backup4,
			String backup5) {
		this.itemName = itemName;
		this.itemValue = itemValue;
		this.evaluateDate = evaluateDate;
		this.itemId = itemId;
		this.centerId = centerId;
		this.backup1 = backup1;
		this.backup2 = backup2;
		this.backup3 = backup3;
		this.backup4 = backup4;
		this.backup5 = backup5;
	}

	// Property accessors

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getItemName() {
		return this.itemName;
	}

	public void setItemName(String itemName) {
		this.itemName = itemName;
	}

	public Double getItemValue() {
		return this.itemValue;
	}

	public void setItemValue(Double itemValue) {
		this.itemValue = itemValue;
	}

	public Timestamp getEvaluateDate() {
		return this.evaluateDate;
	}

	public void setEvaluateDate(Timestamp evaluateDate) {
		this.evaluateDate = evaluateDate;
	}

	public Integer getItemId() {
		return this.itemId;
	}

	public void setItemId(Integer itemId) {
		this.itemId = itemId;
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
			CenterParam cp = (CenterParam) obj;
			return this.itemName.equals(cp.itemName)
					&& this.itemValue.equals(cp.itemValue)
					&& this.centerId.equals(cp.centerId)
					&& this.itemId.equals(cp.centerId);
		}
		return false;
	}

	public int hashCode(){
		return 17;
	}
	
	public String toString() {
		return "[id=" + id + ",itemName=" + itemName + ",itemValue="
				+ itemValue + ",itemId=" + itemId + ",centerId=" + centerId
				+ ",evaluateDate=" + evaluateDate + "]";
	}
}