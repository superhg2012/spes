package org.spes.service.sys.impl;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.spes.bean.Consult;
import org.spes.bean.User;
import org.spes.dao.sys.ConsultDAO;
import org.spes.dao.user.UserDAO;
import org.spes.service.sys.ConsultService;

import com.opensymphony.xwork2.ActionContext;

public class ConsultServiceImpl implements ConsultService{
	private ConsultDAO consultDao;
	private UserDAO userDao;
	
	
	/**
	 * @return the userDao
	 */
	public UserDAO getUserDao() {
		return userDao;
	}

	/**
	 * @param userDao the userDao to set
	 */
	public void setUserDao(UserDAO userDao) {
		this.userDao = userDao;
	}

	/**
	 * @return the consultDao
	 */
	public ConsultDAO getConsultDao() {
		return consultDao;
	}

	/**
	 * @param consultDao the consultDao to set
	 */
	public void setConsultDao(ConsultDAO consultDao) {
		this.consultDao = consultDao;
	}

	public int addConsult(String title, String content) {
		Consult consult = new Consult();
		consult.setConsultTitle(title);
		consult.setConsultContent(content);
		consult.setBackup1("0");
		consult.setConsultTime(new Timestamp(new Date().getTime()));
		Map<String, Object> session = ActionContext.getContext().getSession();
		consult.setUserId((Integer)session.get("userId"));
		return consultDao.save(consult);
	}

	public String deleteConsult(Integer consultId) {
		Consult consult = consultDao.findById(consultId);
		String isOk = "";
		if (consult == null) {
			isOk = "failure";
		} else {
			consultDao.delete(consult);
			isOk = "success";
		}
		
		return isOk;
	}

	public int editConsult(Integer consultID, String title, String content) {
		Consult consult = consultDao.findById(consultID);
		consult.setConsultTitle(title);
		consult.setConsultContent(content);
		consult.setConsultTime(new Timestamp(new Date().getTime()));
		return consultDao.update(consult).getConsultId();
	}

	public List getConsultList() {
		return consultDao.findAll();
	}

	public List getConsultListByUser(Integer userId) {
		return consultDao.findByProperty("userId", userId);
	}

	public Consult getConsultById(Integer consultId) {
		return consultDao.findById(consultId);
	}

	public String getUserNameById(Integer userId) {
		User user = (User) userDao.findByProperty("userId", userId).get(0);
		return user.getBackup2();
	}

	
	public List getConsultListByUserOrderBy(String property, String direction) {
		Map<String, Object> session = ActionContext.getContext().getSession();
		Integer userId = (Integer)session.get("userId");
		List consultList = consultDao.findByUserIdOrderBy(userId, property, direction);
		return consultList;
	}
}
