package org.spes.service.staff.impl;

import java.sql.Timestamp;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import net.sf.json.JSONObject;

import org.spes.bean.StaffFormula;
import org.spes.bean.StaffItem;
import org.spes.bean.StaffParam;
import org.spes.bean.StaffScore;
import org.spes.dao.item.StaffFormulaDAO;
import org.spes.dao.item.StaffItemDAO;
import org.spes.dao.item.StaffParamDAO;
import org.spes.dao.item.StaffScoreDAO;
import org.spes.service.staff.StaffScoreService;
import org.wltea.expression.ExpressionEvaluator;
import org.wltea.expression.datameta.Variable;

public class StaffScoreServiceImpl implements StaffScoreService {

	private StaffScoreDAO staffScoreDao = null;
	private StaffParamDAO staffParamDao = null;
	private StaffFormulaDAO staffFormulaDao = null;
	private StaffItemDAO staffItemDao = null;

	public StaffItemDAO getStaffItemDao() {
		return staffItemDao;
	}

	public void setStaffItemDao(StaffItemDAO staffItemDao) {
		this.staffItemDao = staffItemDao;
	}

	public void setStaffParamDao(StaffParamDAO staffParamDao) {
		this.staffParamDao = staffParamDao;
	}

	public void setStaffFormulaDao(StaffFormulaDAO staffFormulaDao) {
		this.staffFormulaDao = staffFormulaDao;
	}

	public void setStaffScoreDao(StaffScoreDAO staffScoreDao) {
		this.staffScoreDao = staffScoreDao;
	}

	public void evaluateStaffItemScore(Set<StaffParam> set, Integer itemId) throws Exception {
		List<StaffFormula> list = staffFormulaDao.findByProperty("itemId", itemId);

		if (null != list && list.size() > 0) {
			StaffFormula sf = list.get(0);
			String expression = sf.getCaclulator().trim();
			List<Variable> variables = new ArrayList<Variable>();
			Integer windowId = -1;
			Integer centerId = -1;
			Integer userId = -1;
			for(StaffParam sp : set){
				userId = sp.getUserId();
				windowId = sp.getWindowId();
				centerId = sp.getCenterId();
				variables.add(Variable.createVariable(sp.getItemName(), sp.getItemValue()));
			}
			
			Double result = 0.0;
			try {
				result = (Double) ExpressionEvaluator.evaluate(expression, variables); 
			} catch (Exception e){
				throw new Exception("指标计算出现异常，请核查输入的指标值是否异常！");
			}

			StaffScore ss = new StaffScore();
			ss.setItemId(itemId);
			ss.setUserId(userId);
			ss.setItemScore(result);
			ss.setCenterId(centerId);
			ss.setWindowId(windowId);
			ss.setItemName(sf.getItemName());
			ss.setCalculator(sf.getCaclulator());
			ss.setEvaluateDate(new Timestamp(new Date().getTime()));

			staffScoreDao.save(ss);
		}
	}

	public void saveStaffParamScore(Set<StaffParam> set) {
		if (set.size() > 0) {
			for (StaffParam sp : set) {
				sp.setEvaluateDate(new Timestamp(new Date().getTime()));
				staffParamDao.save(sp);
			}
		}
	}

//	public StaffScore getStaffScoreByIds(Integer itemId, Integer userId,
//			Integer windowId, Integer centerId) {
//		Timestamp ts = new Timestamp(new Date().getTime());
//		int currentYear = ts.getYear();
//		int currentMonth = ts.getMonth() + 1;
//		StaffScore ss = staffScoreDao.findByIds(itemId, userId, windowId, centerId);
//		if (ss != null) {
//			Timestamp tts = ss.getEvaluateDate();
//			int year = tts.getYear();
//			int month = tts.getMonth() + 1;
//			if(currentYear - year > 0){
//				currentMonth += 12;
//			} 
//			int diffMonth = currentMonth - month;
////			System.out.println(diffMonth);
//			//未考核
//			if (diffMonth > 0) {
//				return null;
//			} else {
//				return ss; 
//			}
//		}
//		//已考核
//		return null;
//	}

	@SuppressWarnings("unchecked")
	public void evaluateStaffItemScore(
			Map<String, List<JSONObject>> staffScoreMap, Integer itemId,Integer userId, String sheetType, String sheetId) {
		
		DecimalFormat df = new DecimalFormat("0.00");
		List<StaffScore> cslist = new ArrayList<StaffScore>();
		for(Map.Entry<String, List<JSONObject>> entry :staffScoreMap.entrySet()){
			String itemType = entry.getKey();
			if (itemType.equals("定量")) {
				List<JSONObject> cplist = entry.getValue();
				// 二级指标得分分组
				Map<Integer, List<JSONObject>> map = new HashMap<Integer, List<JSONObject>>();
				for (int index = 0; index < cplist.size(); index++) {
					JSONObject json = cplist.get(index);
					Integer parentItemId = json.getInt("pItemId");
					if (map.containsKey(parentItemId)) {
						List<JSONObject> jlist = map.get(parentItemId);
						jlist.add(json);
						map.put(parentItemId, jlist);
					} else {
						List<JSONObject> jlist = new ArrayList<JSONObject>(0);
						jlist.add(json);
						map.put(parentItemId, jlist);
					}
				}//end for
				
				//
				for (Map.Entry<Integer, List<JSONObject>> entry2 : map.entrySet()) {
					Integer parentItemId = entry2.getKey();
					List<StaffFormula> list = staffFormulaDao.findByProperty("itemId", parentItemId);
					if (null != list && list.size() > 0) {
						StaffFormula cf = list.get(0);
						String expression = cf.getCaclulator().trim();
						List<Variable> variables = new ArrayList<Variable>();
						List<JSONObject> jlist = entry2.getValue();
						Integer centerId = jlist.get(0).getInt("centerId");
						Integer windowId = jlist.get(0).getInt("windowId");
						Double itemWeight = jlist.get(0).getDouble("itemWeight");
						
						Set<StaffParam> set = new HashSet<StaffParam>();
						for (int index = 0; index < jlist.size(); index++) {
							JSONObject json = jlist.get(index);
							
							StaffParam sp = new StaffParam();
							sp.setItemId(json.getInt("itemId"));
							sp.setItemName(json.getString("itemName"));
							sp.setItemValue(json.getDouble("itemValue"));
							sp.setUserId(userId);
							sp.setCenterId(json.getInt("centerId"));
							sp.setWindowId(json.getInt("windowId"));
							sp.setEvaluateDate(new Timestamp(new Date().getTime()));
							sp.setBackup2(sheetType);
							sp.setBackup3(sheetId);
							set.add(sp);
							
							String itemName = json.getString("itemName");
							Double itemValue = json.getDouble("itemValue");
							variables.add(Variable.createVariable(itemName,	itemValue));
						}
						
						Double results = (Double) ExpressionEvaluator.evaluate(expression, variables);
						StaffScore ss = new StaffScore();
						ss.setItemName(cf.getItemName());
						ss.setItemScore(results * itemWeight);
						ss.setCalculator(expression);
						ss.setEvaluateDate(new Timestamp(new Date().getTime()));
						ss.setItemId(parentItemId);
						ss.setUserId(userId);
						ss.setCenterId(centerId);
						ss.setWindowId(windowId);
						ss.setEvaluated("true");
						ss.setSheetType(sheetType);
						ss.setSheetId(sheetId);
						cslist.add(ss);
						// save results
						staffScoreDao.save(ss);//保存二级指标
						saveStaffParamScore(set);//保存三级指标
					} else {
						List<JSONObject> jlist = entry2.getValue();
						for (int index = 0; index < jlist.size(); index++) {
							JSONObject json = jlist.get(index);
							StaffScore ss = new StaffScore();
							ss.setItemId(parentItemId);
							ss.setItemName(json.getString("itemName"));
							ss.setCenterId(json.getInt("centerId"));
							ss.setWindowId(json.getInt("windowId"));
							ss.setEvaluateDate(new Timestamp(new Date().getTime()));
							Double itemWeight = json.getDouble("itemWeight");
							Double itemValue = json.getDouble("itemValue");
							Double result = itemWeight * itemValue;
							ss.setItemScore(Double.valueOf(df.format(result)));
							ss.setUserId(userId);
							ss.setEvaluated("true");
							ss.setSheetType(sheetType);
							ss.setSheetId(sheetId);
							cslist.add(ss);
							staffScoreDao.save(ss);//保存二级指标
						}
						
					}
				}
				
			} else if (itemType.equals("定性")) {
				List<JSONObject> jlist = entry.getValue();
				for (int index = 0; index < jlist.size(); index++) {
					JSONObject json = jlist.get(index);
					StaffScore ss = new StaffScore();
					ss.setItemId(json.getInt("itemId"));
					ss.setItemName(json.getString("itemName"));
					ss.setUserId(userId);
					ss.setCenterId(json.getInt("centerId"));
					ss.setWindowId(json.getInt("windowId"));
					ss.setEvaluateDate(new Timestamp(new Date().getTime()));
					Double itemValue = json.getDouble("itemValue");
					Double itemWeight = json.getDouble("itemWeight");
					Double result = itemValue * itemWeight;
					ss.setItemScore(Double.valueOf(df.format(result)));// 得分值
					ss.setEvaluated("true");
					ss.setSheetType(sheetType);
					ss.setSheetId(sheetId);
					cslist.add(ss);
					staffScoreDao.save(ss);
				}
			}//end else
		}//end for
		//计算1级指标得分,直接求二级指标得分之和
		StaffScore ws = new StaffScore();
		ws.setItemId(itemId);
		StaffItem ci = (StaffItem) staffItemDao.findByProperty("itemId",itemId).get(0);
		ws.setItemName(ci.getItemName());
		ws.setEvaluateDate(new Timestamp(new Date().getTime()));
		ws.setUserId(userId);
		ws.setCenterId(ci.getCenterId());
		ws.setWindowId(cslist.get(0).getWindowId());
		
		Double sumItemScore = 0d;
		for (StaffScore csObj : cslist) {
			sumItemScore += csObj.getItemScore()*ci.getItemWeight();
		}
		ws.setItemScore(Double.valueOf(df.format(sumItemScore)));// 得分值
		ws.setEvaluated("true");
		ws.setSheetType(sheetType);
		ws.setSheetId(sheetId);
		staffScoreDao.save(ws);
	}

	@Override
	public StaffScore getCheckedStaffItem(Integer itemId, Integer userId,
			Integer windowId, Integer centerId, String sheetId) {
		return staffScoreDao.findByIds(itemId, userId, windowId, centerId, sheetId);
	}

}
