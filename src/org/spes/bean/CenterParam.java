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
	private String evaluated;
	private String sheetType;
	private String sheetId;
	private String backup4;
	private String backup5;

	// Constructors

	/** default constructor */
	public CenterParam() {
	}

	/** full constructor */
	public CenterParam(String itemName, Double itemValue,
			Timestamp evaluateDate, Integer itemId, Integer centerId,
			String evaluated, String sheetType, String sheetId, String backup4,
			String backup5) {
		this.itemName = itemName;
		this.itemValue = itemValue;
		this.evaluateDate = evaluateDate;
		this.itemId = itemId;
		this.centerId = centerId;
		this.evaluated = evaluated;
		this.sheetType = sheetType;
		this.sheetId = sheetId;
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

	public String getEvaluated() {
		return evaluated;
	}

	public void setEvaluated(String evaluated) {
		this.evaluated = evaluated;
	}

	public String getSheetType() {
		return sheetType;
	}

	public void setSheetType(String sheetType) {
		this.sheetType = sheetType;
	}

	public String getSheetId() {
		return sheetId;
	}

	public void setSheetId(String sheetId) {
		this.sheetId = sheetId;
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