package org.spes.service.sys.impl;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.spes.bean.Consult;
import org.spes.bean.Reply;
import org.spes.bean.User;
import org.spes.dao.sys.ConsultDAO;
import org.spes.dao.sys.ReplyDAO;
import org.spes.dao.user.UserDAO;
import org.spes.service.sys.ConsultService;
import org.spes.service.sys.ReplyService;

import com.opensymphony.xwork2.ActionContext;

public class ReplyServiceImpl implements ReplyService{
	
	private ReplyDAO replyDao;
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
	 * @return the replyDao
	 */
	public ReplyDAO getReplyDao() {
		return replyDao;
	}

	/**
	 * @param replyDao the replyDao to set
	 */
	public void setReplyDao(ReplyDAO replyDao) {
		this.replyDao = replyDao;
	}

	public int addReply(String content, String consultId) {
		Reply reply = new Reply();
		reply.setReplyContent(content);
		reply.setReplyTime(new Timestamp(new Date().getTime()));
		Map<String, Object> session = ActionContext.getContext().getSession();
		reply.setUserId((Integer)session.get("userId"));
		reply.setConsultId(Integer.parseInt(consultId));
		Consult consult = consultDao.findById(Integer.parseInt(consultId));
		consult.setBackup1("1");
		consultDao.update(consult);
		return replyDao.save(reply);
	}

	public String deleteReply(Integer replyId) {
		Reply reply = replyDao.findById(replyId);
		String isOk = "";
		if (reply == null) {
			isOk = "failure";
		} else {
			replyDao.delete(reply);
			isOk = "success";
		}
		
		return isOk;
	}

	public int editReply(Integer replyID, String content) {
		Reply reply = replyDao.findById(replyID);
		reply.setReplyContent(content);
		reply.setReplyTime(new Timestamp(new Date().getTime()));
		return replyDao.update(reply).getReplyId();
	}

	public List getReplyList() {
		return replyDao.findAll();
	}

	public List getUnsolvedConsult() {
		return consultDao.findByProperty("backup1", "0");
	}

	/**
	 * @return the consultService
	 */
	public String getUserNameById(Integer userId) {
		User user = (User) userDao.findByProperty("userId", userId).get(0);
		return user.getBackup2();
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

	public Reply getReplybyConsultId(Integer consultId) {
		List rls = replyDao.findByProperty("consultId", consultId);
		
		if (rls.size() != 0) {
			return (Reply)rls.get(0);
		} else {
			return null;
		}
		
	}

	public List getAllConsultOrder() {
		return consultDao.findAllOrderByBackup1();
	}

}
