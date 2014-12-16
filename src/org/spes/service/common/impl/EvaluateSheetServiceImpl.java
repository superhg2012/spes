package org.spes.service.common.impl;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

import org.spes.bean.Evaluatesheet;
import org.spes.dao.common.EvaluatesheetDAO;
import org.spes.dao.item.CenterScoreDAO;
import org.spes.dao.item.StaffScoreDAO;
import org.spes.dao.item.WindowScoreDAO;
import org.spes.service.common.EvaluateSheetService;

public class EvaluateSheetServiceImpl implements EvaluateSheetService{

	private EvaluatesheetDAO evaluateSheetDao = null;
	private CenterScoreDAO centerScoreDao = null;
	private StaffScoreDAO staffScoreDao = null;
	private WindowScoreDAO windowScoreDao = null;
	
	public void setCenterScoreDao(CenterScoreDAO centerScoreDao) {
		this.centerScoreDao = centerScoreDao;
	}


	public void setStaffScoreDao(StaffScoreDAO staffScoreDao) {
		this.staffScoreDao = staffScoreDao;
	}

	public void setWindowScoreDao(WindowScoreDAO windowScoreDao) {
		this.windowScoreDao = windowScoreDao;
	}



	public void setEvaluateSheetDao(EvaluatesheetDAO evaluateSheetDao) {
		this.evaluateSheetDao = evaluateSheetDao;
	}
	
	public List<Evaluatesheet> getEvaluateSheetList(String checkType, String sheetState){
		return evaluateSheetDao.findEvaluateSheet(checkType, sheetState);
	}

	public boolean createNewEvaluateSheet(String sheetName, String userName,
			String sheetState, String sheetType, String checkType) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH£ºmm£ºss");
		Calendar calendar = Calendar.getInstance();
		String createTime = sdf.format(calendar.getTime());
		
		String lastUpdate = createTime;
		
		sdf = new SimpleDateFormat("yyyy-MM-dd");
		String dtime = sdf.format(calendar.getTime());
		Evaluatesheet esheet = new Evaluatesheet();
		esheet.setSheetName(userName + "&" + dtime + "&" + sheetName);
		esheet.setUserName(userName);
		esheet.setSheetState(sheetState);
		esheet.setSheetType(sheetType);
		esheet.setCreateTime(createTime);
		esheet.setLastUpdate(lastUpdate);
		esheet.setCheckType(checkType);
		
		return evaluateSheetDao.save(esheet) > 0 ? true : false;
	}

	public boolean deleteEvaluateSheet(String sheetId) {
		List<Evaluatesheet> list= evaluateSheetDao.findByProperty("sheetId", Integer.valueOf(sheetId));
		Evaluatesheet esheet = list.get(0);
		centerScoreDao.deleteCenterScore(sheetId, esheet.getSheetName());
		staffScoreDao.deleteStaffScore(sheetId,esheet.getSheetName());
		windowScoreDao.deleteCenterScore(sheetId,esheet.getSheetName());
		return evaluateSheetDao.delete(sheetId);
	}

	public List<Evaluatesheet> getEvaluateSheet(Integer sheetId) {
		return evaluateSheetDao.findByProperty("sheetId", sheetId);
	}
	
	
}
