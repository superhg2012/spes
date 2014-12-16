package org.spes.bean;

import java.sql.Timestamp;

/**
 * Consult entity. @author MyEclipse Persistence Tools
 */

public class Consult implements java.io.Serializable {

	// Fields

	private Integer consultId;
	private String consultTitle;
	private String consultContent;
	private Timestamp consultTime;
	private Integer userId;
	private String backup1;
	private String backup2;
	private String backup3;
	private String backup4;
	private String backup5;

	// Constructors

	/** default constructor */
	public Consult() {
	}

	/** minimal constructor */
	public Consult(Integer userId) {
		this.userId = userId;
	}

	/** full constructor */
	public Consult(String consultTitle, String consultContent,
			Timestamp consultTime, Integer userId, String backup1,
			String backup2, String backup3, String backup4, String backup5) {
		this.consultTitle = consultTitle;
		this.consultContent = consultContent;
		this.consultTime = consultTime;
		this.userId = userId;
		this.backup1 = backup1;
		this.backup2 = backup2;
		this.backup3 = backup3;
		this.backup4 = backup4;
		this.backup5 = backup5;
	}

	// Property accessors

	public Integer getConsultId() {
		return this.consultId;
	}

	public void setConsultId(Integer consultId) {
		this.consultId = consultId;
	}

	public String getConsultTitle() {
		return this.consultTitle;
	}

	public void setConsultTitle(String consultTitle) {
		this.consultTitle = consultTitle;
	}

	public String getConsultContent() {
		return this.consultContent;
	}

	public void setConsultContent(String consultContent) {
		this.consultContent = consultContent;
	}

	public Timestamp getConsultTime() {
		return this.consultTime;
	}

	public void setConsultTime(Timestamp consultTime) {
		this.consultTime = consultTime;
	}

	public Integer getUserId() {
		return this.userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
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