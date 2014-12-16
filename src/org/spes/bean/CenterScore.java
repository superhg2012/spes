package org.spes.bean;

import java.sql.Timestamp;

/**
 * CenterScore entity. @author MyEclipse Persistence Tools
 */

public class CenterScore implements java.io.Serializable {

	// Fields

	private Integer id;
	private String itemName;
	private Double itemScore;
	private Timestamp evaluateDate;
	private String calculator;
	private Integer itemId;
	private Integer centerId;
	private String evaluated;
	private String sheetType;
	private String sheetId;
	private String backup4;
	private String backup5;

	// Constructors

	/** default constructor */
	public CenterScore() {
	}

	/** full constructor */
	public CenterScore(String itemName, Double itemScore,
			Timestamp evaluateDate, String calculator, Integer itemId,
			Integer centerId, String evaluated, String sheetType, String sheetId,
			String backup4, String backup5) {
		this.itemName = itemName;
		this.itemScore = itemScore;
		this.evaluateDate = evaluateDate;
		this.calculator = calculator;
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

	public Double getItemScore() {
		return this.itemScore;
	}

	public void setItemScore(Double itemScore) {
		this.itemScore = itemScore;
	}

	public Timestamp getEvaluateDate() {
		return this.evaluateDate;
	}

	public void setEvaluateDate(Timestamp evaluateDate) {
		this.evaluateDate = evaluateDate;
	}

	public String getCalculator() {
		return this.calculator;
	}

	public void setCalculator(String calculator) {
		this.calculator = calculator;
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

	public String toString() {
		return "[" + id + ",itemName=" + itemName + ",itemScore=" + itemScore
				+ ",=evaluateDate=" + evaluateDate + ",calculator="
				+ calculator + ",itemId=" + itemId + "]";
	}

}