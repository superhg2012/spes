package org.spes.service.sys.impl;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.spes.bean.ServiceCenter;
import org.spes.bean.Notice;
import org.spes.bean.User;
import org.spes.dao.common.ServiceCenterDAO;
import org.spes.dao.sys.NoticeDAO;
import org.spes.dao.user.UserDAO;
import org.spes.service.sys.NoticeService;

import com.opensymphony.xwork2.ActionContext;

public class NoticeServiceImpl implements NoticeService{

	private NoticeDAO noticeDao = null;
	private UserDAO userDao = null;
	private ServiceCenterDAO serviceCenterDao = null;
	


	/**
	 * @return the serviceCenterDao
	 */
	public ServiceCenterDAO getServiceCenterDao() {
		return serviceCenterDao;
	}


	/**
	 * @param serviceCenterDao the serviceCenterDao to set
	 */
	public void setServiceCenterDao(ServiceCenterDAO serviceCenterDao) {
		this.serviceCenterDao = serviceCenterDao;
	}


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


	public NoticeDAO getNoticeDao() {
		return noticeDao;
	}


	public void setNoticeDao(NoticeDAO noticeDao) {
		this.noticeDao = noticeDao;
	}


	public int addNotice(String title, String content) {
		Notice notice = new Notice();
		notice.setNoticeTitle(title);
		notice.setNoticeContent(content);
		notice.setNoticeTime(new Timestamp(new Date().getTime()));
		Map<String, Object> session = ActionContext.getContext().getSession();
		notice.setUserId((Integer)session.get("userId"));
		return noticeDao.save(notice);
	}


	public List<Notice> getNoticeList() {
		return noticeDao.findAll();
	}

	public int editNotice(Integer noticeID, String title, String content) {
		Notice notice =	(Notice)noticeDao.findByProperty("noticeId", noticeID).get(0);
		notice.setNoticeTitle(title);
		notice.setNoticeContent(content);
		notice.setNoticeTime(new Timestamp(new Date().getTime()));
		return noticeDao.update(notice);
	}


	public String deleteNotice(Integer noticeID) {
		Notice notice = noticeDao.findById(noticeID);
		String isOk = "";
		if (notice == null) {
			isOk = "failure";
		} else {
			noticeDao.delete(notice);
			isOk = "success";
		}
		
		return isOk;
		
	}


	public Notice getNoticeById(Integer noticeId) {
		Notice notice = noticeDao.findById(noticeId);
		return notice;
	}


	public String getUserNameByUserId(Integer userId) {
		User user = (User) userDao.findByProperty("userId", userId).get(0);
		return user.getBackup2();
	}


	public List<Notice> getNoticeOrderBy(String property, String dir) {
		Map<String, Object> session = ActionContext.getContext().getSession();
		Integer userId = (Integer)session.get("userId");
		return noticeDao.findAllSortByProperty(property, dir, userId);
	}

	/*
	 * the implement of the NoticeService
	 * the notice list published by others in his center and the super manager
	 */
	public List checkNoticeOrderBy(String property, String value) {
		Map<String, Object> session = ActionContext.getContext().getSession();
		Integer userId = (Integer)session.get("userId");
		User user = (User) userDao.findByProperty("userId", userId).get(0);//find the user
		Integer centerId = user.getServiceCenter().getCenterId();	//get the center id of the user in
		ServiceCenter center = serviceCenterDao.findCenterById(centerId);
		//the userIds in his center
		List<User> userList = userDao.findByProperty("serviceCenter", center);
		List<Integer> userIdList = new ArrayList<Integer>();
		for (int i = 0; i < userList.size(); i++) {
			if (!userList.get(i).getUserId().equals(userId)) {
				userIdList.add(userList.get(i).getUserId());
			}
		}
		System.out.println("list size is " + userIdList);
		
		return noticeDao.findCheckNoticeOrderBy(userId, userIdList, property, value);
	}


}
