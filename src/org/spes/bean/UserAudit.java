package org.spes.bean;

import java.sql.Timestamp;

/**
 * UserAudit entity. @author MyEclipse Persistence Tools
 */

public class UserAudit implements java.io.Serializable {

	// Fields

	private Integer id;
	private Integer userId;
	private String userName;
	private String userPass;
	private String idCardNum;
	private String email;
	private String contact;
	private String gender;
	private String address;
	private Integer roleId;
	private Integer postId;
	private Integer windowId;
	private Integer centerId;
	private String valid;
	private Timestamp registtime;
	private String remarks;
	private String backup1;
	private String backup2;
	private String backup3;
	private String backup4;
	private String backup5;

	// Constructors

	/** default constructor */
	public UserAudit() {
	}

	/** minimal constructor */
	public UserAudit(Integer userId) {
		this.userId = userId;
	}

	/** full constructor */
	public UserAudit(Integer userId, String userName, String userPass,
			String idCardNum, String email, String contact, String gender,
			String address, Integer roleId, Integer postId, Integer windowId,
			Integer centerId, String valid, Timestamp registtime,
			String remarks, String backup1, String backup2, String backup3,
			String backup4, String backup5) {
		this.userId = userId;
		this.userName = userName;
		this.userPass = userPass;
		this.idCardNum = idCardNum;
		this.email = email;
		this.contact = contact;
		this.gender = gender;
		this.address = address;
		this.roleId = roleId;
		this.postId = postId;
		this.windowId = windowId;
		this.centerId = centerId;
		this.valid = valid;
		this.registtime = registtime;
		this.remarks = remarks;
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

	public Integer getUserId() {
		return this.userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public String getUserName() {
		return this.userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getUserPass() {
		return this.userPass;
	}

	public void setUserPass(String userPass) {
		this.userPass = userPass;
	}

	public String getIdCardNum() {
		return this.idCardNum;
	}

	public void setIdCardNum(String idCardNum) {
		this.idCardNum = idCardNum;
	}

	public String getEmail() {
		return this.email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getContact() {
		return this.contact;
	}

	public void setContact(String contact) {
		this.contact = contact;
	}

	public String getGender() {
		return this.gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getAddress() {
		return this.address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public Integer getRoleId() {
		return this.roleId;
	}

	public void setRoleId(Integer roleId) {
		this.roleId = roleId;
	}

	public Integer getPostId() {
		return this.postId;
	}

	public void setPostId(Integer postId) {
		this.postId = postId;
	}

	public Integer getWindowId() {
		return this.windowId;
	}

	public void setWindowId(Integer windowId) {
		this.windowId = windowId;
	}

	public Integer getCenterId() {
		return this.centerId;
	}

	public void setCenterId(Integer centerId) {
		this.centerId = centerId;
	}

	public String getValid() {
		return this.valid;
	}

	public void setValid(String valid) {
		this.valid = valid;
	}

	public Timestamp getRegisttime() {
		return this.registtime;
	}

	public void setRegisttime(Timestamp registtime) {
		this.registtime = registtime;
	}

	public String getRemarks() {
		return this.remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
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