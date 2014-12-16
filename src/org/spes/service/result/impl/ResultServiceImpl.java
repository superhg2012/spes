package org.spes.service.result.impl;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import org.spes.bean.User;
import org.spes.dao.item.CenterScoreDAO;
import org.spes.dao.item.StaffScoreDAO;
import org.spes.dao.item.WindowScoreDAO;
import org.spes.dao.user.UserDAO;
import org.spes.service.result.ResultService;
import org.spes.service.util.DateModifier;

import com.alcatel.omc.fwk.utilities.trace.TraceManager;
import com.opensymphony.xwork2.ActionContext;
import org.spes.common.Constants;

public class ResultServiceImpl implements ResultService {
	private CenterScoreDAO centerDao;
	private WindowScoreDAO windowDao;
	private StaffScoreDAO staffDao;
	
	private UserDAO userDao;
	
	public UserDAO getUserDao() {
		return userDao;
	}
	
	public void setUserDao(UserDAO userDao) {
		this.userDao = userDao;
	}
	
	public CenterScoreDAO getCenterDao() {
		return centerDao;
	}
	
	public void setCenterDao(CenterScoreDAO centerDao) {
		this.centerDao = centerDao;
	}
	
	public WindowScoreDAO getWindowDao() {
		return windowDao;
	}
	
	public void setWindowDao(WindowScoreDAO windowDao) {
		this.windowDao = windowDao;
	}
	
	public StaffScoreDAO getStaffDao() {
		return staffDao;
	}
	public void setStaffDao(StaffScoreDAO staffDao) {
		this.staffDao = staffDao;
	}
	
	public List GetCenterResult(String from, String to,String type) {
		TraceManager.TrMethod("=======>> GetCenterResult");
		ActionContext context = ActionContext.getContext();
		String username = (String)context.getSession().get("userName");
		List<String> list = new ArrayList<String>();
		String fromDate = from;
		String toDate = to;
		if(Constants.TYPE_QUARTER.equals(type)){
			try {
				list = DateModifier.ModifyDateByQuarter(from, to);
			} catch (ParseException e) {
				TraceManager.TrException("季度日期转换错误", e);
			}
			fromDate = list.get(0);
			toDate = list.get(1);
		}
		if(Constants.TYPE_YEAR.equals(type)){
			try {
				list = DateModifier.ModifyDateByYear(from, to);
			} catch (ParseException e) {
				TraceManager.TrException("年日期转换错误", e);
			}
			fromDate = list.get(0);
			toDate = list.get(1);
		}
		if(Constants.TYPE_MONTH.equals(type)){
			try {
				list = DateModifier.ModifyDateByMonth(from, to);
			} catch (Exception e) {
				TraceManager.TrException("月日期转换错误", e);
			}
			fromDate = list.get(0);
			toDate = list.get(1);
		}
		list = null;
		Integer centerId = ((User)(userDao.findByUserName(username).get(0))).getServiceCenter().getCenterId();
		List scores = centerDao.findByCenterIdAndDates(fromDate, toDate, centerId, type);
		TraceManager.TrMethod("=======<< GetCenterResult");
		return scores;
	}
	
	public List GetWindowScore(Integer windowId,String from,String to,String type){
		List<String> list = new ArrayList<String>();
		String fromDate = from;
		String toDate = to;
		if("quarter".equals(type)){
			try {
				list = DateModifier.ModifyDateByQuarter(from, to);
			} catch (ParseException e) {
				TraceManager.TrException("季度日期转换错误", e);
			}
			fromDate = list.get(0);
			toDate = list.get(1);
		}
		if("year".equals(type)){
			try {
				list = DateModifier.ModifyDateByYear(from, to);
			} catch (ParseException e) {
				TraceManager.TrException("年日期转换错误", e);
			}
			fromDate = list.get(0);
			toDate = list.get(1);
		}
		if("month".equals(type)){
			try {
				list = DateModifier.ModifyDateByMonth(from, to);
			} catch (Exception e) {
				TraceManager.TrException("月日期转换错误", e);
			}
			fromDate = list.get(0);
			toDate = list.get(1);
		}
		list = null;
		return windowDao.FindByWindowIdAndTime(windowId, fromDate, toDate, type);
	}
	/*
	 * 返回某窗口下所有员工及总分
	 */
	public List GetStaffAndScoreByWindowId(Integer windowId,String from,String to,String type){
		List<String> list = new ArrayList<String>();
		String fromDate = from;
		String toDate = to;
		if("quarter".equals(type)){
			try {
				list = DateModifier.ModifyDateByQuarter(from, to);
			} catch (ParseException e) {
				System.out.println("季度日期转换错误");
				e.printStackTrace();
			}
			fromDate = list.get(0);
			toDate = list.get(1);
		}
		if("year".equals(type)){
			try {
				list = DateModifier.ModifyDateByYear(from, to);
			} catch (ParseException e) {
				System.out.println("年日期转换错误");
				e.printStackTrace();
			}
			fromDate = list.get(0);
			toDate = list.get(1);
		}
		if("month".equals(type)){
			try {
				list = DateModifier.ModifyDateByMonth(from, to);
			} catch (Exception e) {
				System.out.println("月日期转换错误");
				e.printStackTrace();
			}
			fromDate = list.get(0);
			toDate = list.get(1);
		}
		list = null;
		List result = staffDao.getStaffAndScoreByWindowIdAndTime(windowId, fromDate, toDate, type);
		return result;
	}
	
	public List GetStaffAndScoreBUIdAndTime(Integer userId,String from,String to,String type){
		
		List<String> list = new ArrayList<String>();
		String fromDate = from;
		String toDate = to;
		if("quarter".equals(type)){
			try {
				list = DateModifier.ModifyDateByQuarter(from, to);
			} catch (ParseException e) {
				System.out.println("季度日期转换错误");
				e.printStackTrace();
			}
			fromDate = list.get(0);
			toDate = list.get(1);
		}
		if("year".equals(type)){
			try {
				list = DateModifier.ModifyDateByYear(from, to);
			} catch (ParseException e) {
				e.printStackTrace();
			}
			fromDate = list.get(0);
			toDate = list.get(1);
		}
		if("month".equals(type)){
			try {
				list = DateModifier.ModifyDateByMonth(from, to);
			} catch (Exception e) {
				System.out.println("月日期转换错误");
				e.printStackTrace();
			}
			fromDate = list.get(0);
			toDate = list.get(1);
		}
		list = null;
		List result = staffDao.getStaffAndScoreByUserIdAndTime(userId, fromDate, toDate, type);
		return result;
	}
	
	public List GetStaffAndScoreByUserIdAndTime(Integer userId,String from,String to,String type){
		List<String> list = new ArrayList<String>();
		String fromDate = from;
		String toDate = to;
		if("quarter".equals(type)){
			try {
				list = DateModifier.ModifyDateByQuarter(from, to);
			} catch (ParseException e) {
				e.printStackTrace();
			}
			fromDate = list.get(0);
			toDate = list.get(1);
		}
		if("year".equals(type)){
			try {
				list = DateModifier.ModifyDateByYear(from, to);
			} catch (ParseException e) {
				System.out.println("年日期转换错误");
				e.printStackTrace();
			}
			fromDate = list.get(0);
			toDate = list.get(1);
		}
		if("month".equals(type)){
			try {
				list = DateModifier.ModifyDateByMonth(from, to);
			} catch (Exception e) {
				System.out.println("月日期转换错误");
				e.printStackTrace();
			}
			fromDate = list.get(0);
			toDate = list.get(1);
		}
		list = null;
		return staffDao.getStaffAndScoreByIdAndTime(userId, fromDate, toDate, type);
	}
	
	
	/**
	 * 生成下载文件，未完成
	 */
	public String GenerateDownloadFile(String from, String to, String type, String cws){
		List<String> list = new ArrayList<String>();
		String fromDate = from;
		String toDate = to;
		if("quarter".equals(type)){
			try {
				list = DateModifier.ModifyDateByQuarter(from, to);
			} catch (ParseException e) {
				System.out.println("季度日期转换错误");
				e.printStackTrace();
			}
			fromDate = list.get(0);
			toDate = list.get(1);
		}
		if("year".equals(type)){
			try {
				list = DateModifier.ModifyDateByYear(from, to);
			} catch (ParseException e) {
				System.out.println("年日期转换错误");
				e.printStackTrace();
			}
			fromDate = list.get(0);
			toDate = list.get(1);
		}
		if("month".equals(type)){
			try {
				list = DateModifier.ModifyDateByMonth(from, to);
			} catch (Exception e) {
				System.out.println("月日期转换错误");
				e.printStackTrace();
			}
			fromDate = list.get(0);
			toDate = list.get(1);
		}
		list = null;
		//List 
		return null;//该方法未完成
	}
	
	public String GenerateDownloadFile(String from, String to, String type) {
		return null;
	}
	
}
