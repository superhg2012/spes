package org.spes.service.result.impl;

import java.util.List;

import org.spes.dao.user.UserDAO;
import org.spes.service.result.StaffService;

public class StaffServiceImpl implements StaffService {
	
	private UserDAO userDao;

	public UserDAO getUserDao() {
		return userDao;
	}

	public void setUserDao(UserDAO userDao) {
		this.userDao = userDao;
	}
	
	public List GetStaffAndScoreByWindowId(Integer windowId,String from,String to,String type){
		return null;
	}
}
