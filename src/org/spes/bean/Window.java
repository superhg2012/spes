package org.spes.bean;

import java.util.HashSet;
import java.util.Set;

/**
 * Window entity. @author MyEclipse Persistence Tools
 */

public class Window implements java.io.Serializable {

	// Fields
	private Integer windowId;
	private ServiceCenter serviceCenter;
	private String windowName;
	private String windowBussiness;
	private String windowDesc;
	private String backup1;
	private String backup2;
	private String backup3;
	private String backup4;
	private String backup5;
	private Set users = new HashSet(0);

	// Constructors

	/** default constructor */
	public Window() {
	}

	/** full constructor */
	public Window(ServiceCenter serviceCenter, String windowName,
			String windowBussiness, String windowDesc, String backup1,
			String backup2, String backup3, String backup4, String backup5,
			Set users) {
		this.serviceCenter = serviceCenter;
		this.windowName = windowName;
		this.windowBussiness = windowBussiness;
		this.windowDesc = windowDesc;
		this.backup1 = backup1;
		this.backup2 = backup2;
		this.backup3 = backup3;
		this.backup4 = backup4;
		this.backup5 = backup5;
		this.users = users;
	}

	// Property accessors

	public Integer getWindowId() {
		return this.windowId;
	}

	public void setWindowId(Integer windowId) {
		this.windowId = windowId;
	}

	public ServiceCenter getServiceCenter() {
		return this.serviceCenter;
	}

	public void setServiceCenter(ServiceCenter serviceCenter) {
		this.serviceCenter = serviceCenter;
	}

	public String getWindowName() {
		return this.windowName;
	}

	public void setWindowName(String windowName) {
		this.windowName = windowName;
	}

	public String getWindowBussiness() {
		return this.windowBussiness;
	}

	public void setWindowBussiness(String windowBussiness) {
		this.windowBussiness = windowBussiness;
	}

	public String getWindowDesc() {
		return this.windowDesc;
	}

	public void setWindowDesc(String windowDesc) {
		this.windowDesc = windowDesc;
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

	public Set getUsers() {
		return this.users;
	}

	public void setUsers(Set users) {
		this.users = users;
	}

}