package org.spes.bean;

/**
 * ServiceCenterAudit entity. @author MyEclipse Persistence Tools
 */

public class ServiceCenterAudit implements java.io.Serializable {

	// Fields

	private Integer id;
	private Integer centerId;
	private String centerName;
	private String province;
	private String city;
	private String county;
	private String organcode;
	private String linkman;
	private String contact;
	private String email;
	private String valid;
	private String legalrepresent;
	private String remarks;
	private String backup1;
	private String backup2;
	private String backup3;
	private String backup4;
	private String backup5;

	// Constructors

	/** default constructor */
	public ServiceCenterAudit() {
	}

	/** full constructor */
	public ServiceCenterAudit(Integer centerId, String centerName,
			String province, String city, String county, String organcode,
			String linkman, String contact, String email, String valid,
			String legalrepresent, String remarks, String backup1,
			String backup2, String backup3, String backup4, String backup5) {
		this.centerId = centerId;
		this.centerName = centerName;
		this.province = province;
		this.city = city;
		this.county = county;
		this.organcode = organcode;
		this.linkman = linkman;
		this.contact = contact;
		this.email = email;
		this.valid = valid;
		this.legalrepresent = legalrepresent;
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

	public Integer getCenterId() {
		return this.centerId;
	}

	public void setCenterId(Integer centerId) {
		this.centerId = centerId;
	}

	public String getCenterName() {
		return this.centerName;
	}

	public void setCenterName(String centerName) {
		this.centerName = centerName;
	}

	public String getProvince() {
		return this.province;
	}

	public void setProvince(String province) {
		this.province = province;
	}

	public String getCity() {
		return this.city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getCounty() {
		return this.county;
	}

	public void setCounty(String county) {
		this.county = county;
	}

	public String getOrgancode() {
		return this.organcode;
	}

	public void setOrgancode(String organcode) {
		this.organcode = organcode;
	}

	public String getLinkman() {
		return this.linkman;
	}

	public void setLinkman(String linkman) {
		this.linkman = linkman;
	}

	public String getContact() {
		return this.contact;
	}

	public void setContact(String contact) {
		this.contact = contact;
	}

	public String getEmail() {
		return this.email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getValid() {
		return this.valid;
	}

	public void setValid(String valid) {
		this.valid = valid;
	}

	public String getLegalrepresent() {
		return this.legalrepresent;
	}

	public void setLegalrepresent(String legalrepresent) {
		this.legalrepresent = legalrepresent;
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