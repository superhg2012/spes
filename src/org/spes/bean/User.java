package org.spes.bean;

import java.sql.Timestamp;

import org.hibernate.annotations.Parameter;

import com.googlecode.jsonplugin.annotations.JSON;

/**
 * User entity. @author MyEclipse Persistence Tools
 */

public class User implements java.io.Serializable {

	// Fields

	private Integer userId;
	private ServiceCenter serviceCenter;
	private Window window;
	private Role role;
	private Post post;
	private String userName;
	private String userPass;
	private String idCardNum;
	private String email;
	private String contact;
	private String gender;
	private String address;
	private Timestamp registtime;
	private String remarks;
	private String backup1;
	private String backup2;
	private String backup3;
	private String backup4;
	private String backup5;

	// Constructors

	/** default constructor */
	public User() {
	}

	/** full constructor */
	public User(ServiceCenter serviceCenter, Window window, Role role,
			Post post, String userName, String userPass, String idCardNum,
			String email, String contact, String gender, String address,
			Timestamp registtime, String remarks, String backup1,
			String backup2, String backup3, String backup4, String backup5) {
		this.serviceCenter = serviceCenter;
		this.window = window;
		this.role = role;
		this.post = post;
		this.userName = userName;
		this.userPass = userPass;
		this.idCardNum = idCardNum;
		this.email = email;
		this.contact = contact;
		this.gender = gender;
		this.address = address;
		this.registtime = registtime;
		this.remarks = remarks;
		this.backup1 = backup1;
		this.backup2 = backup2;
		this.backup3 = backup3;
		this.backup4 = backup4;
		this.backup5 = backup5;
	}

	// Property accessors

	public Integer getUserId() {
		return this.userId;
	}
	public void setUserId(Integer userId) {
		this.userId = userId;
	}
    
	public ServiceCenter getServiceCenter() {
		return this.serviceCenter;
	}

	public void setServiceCenter(ServiceCenter serviceCenter) {
		this.serviceCenter = serviceCenter;
	}

	
	public Window getWindow() {
		return this.window;
	}

	public void setWindow(Window window) {
		this.window = window;
	}

	public Role getRole() {
		return this.role;
	}

	public void setRole(Role role) {
		this.role = role;
	}

	public Post getPost() {
		return this.post;
	}

	public void setPost(Post post) {
		this.post = post;
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