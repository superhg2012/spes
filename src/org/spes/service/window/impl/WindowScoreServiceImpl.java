package org.spes.service.window.impl;

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

import org.spes.bean.WindowFormula;
import org.spes.bean.WindowItem;
import org.spes.bean.WindowParam;
import org.spes.bean.WindowScore;
import org.spes.dao.item.WindowFormulaDAO;
import org.spes.dao.item.WindowItemDAO;
import org.spes.dao.item.WindowParamDAO;
import org.spes.dao.item.WindowScoreDAO;
import org.spes.service.window.WindowScoreService;
import org.wltea.expression.ExpressionEvaluator;
import org.wltea.expression.datameta.Variable;

public class WindowScoreServiceImpl implements WindowScoreService {

	private WindowScoreDAO windowScoreDao = null;
	private WindowParamDAO windowParamDao = null;
	private WindowFormulaDAO windowFormulaDao = null;
	private WindowItemDAO windowItemDao = null;
	
	public WindowItemDAO getWindowItemDao() {
		return windowItemDao;
	}

	public void setWindowItemDao(WindowItemDAO windowItemDao) {
		this.windowItemDao = windowItemDao;
	}

	public void setWindowFormulaDao(WindowFormulaDAO windowFormulaDao) {
		this.windowFormulaDao = windowFormulaDao;
	}

	public void setWindowParamDao(WindowParamDAO windowParamDao) {
		this.windowParamDao = windowParamDao;
	}

	public void setWindowScoreDao(WindowScoreDAO windowScoreDao) {
		this.windowScoreDao = windowScoreDao;
	}

	public void saveWindowParamScore(Set<WindowParam> set) {
		if (set.size() > 0) {
			for (WindowParam wp : set) {
				wp.setEvaluateDate(new Timestamp(new Date().getTime()));
				windowParamDao.save(wp);
			}
		}
	}

	@SuppressWarnings("unchecked")
	public void evaluateWindowItemScore(Set<WindowParam> set, Integer itemId) {
		List<WindowFormula> list = windowFormulaDao.findByProperty("itemId", itemId);
		if (null != list && list.size() > 0) {
			WindowFormula wf = list.get(0);
			String expression = wf.getCalculator().trim();
			List<Variable> variables = new ArrayList<Variable>();
			Integer windowId = -1;
			Integer centerId = -1;
			for (WindowParam wp : set) {
				variables.add(Variable.createVariable(wp.getItemName(), wp.getItemValue()));
				windowId = wp.getWindowId();
				centerId = wp.getCenterId();
			}
			Double result = (Double) ExpressionEvaluator.evaluate(expression,variables);
			WindowScore ws = new WindowScore();
			ws.setItemId(itemId);
			ws.setWindowId(windowId);
			ws.setCenterId(centerId);
			ws.setItemName(wf.getItemName());
			ws.setItemScore(result);
			ws.setEvaluateDate(new Timestamp(new Date().getTime()));
			ws.setCalculator(expression);
			windowScoreDao.save(ws);
		}
	}

//	@SuppressWarnings("deprecation")
//	public WindowScore getWindowScoreByIds(Integer itemId, Integer windowId,
//			Integer centerId) {
//		Timestamp ts = new Timestamp(new Date().getTime());
//		int currentYear = ts.getYear();
//		int currentMonth = ts.getMonth() + 1;
//		WindowScore ws = windowScoreDao.findWindowScoreByIds(centerId, windowId, itemId);
//		if (ws != null) {
//			Timestamp tts = ws.getEvaluateDate();
//			int year = tts.getYear();
//			int month = tts.getMonth() + 1;
//			if (currentYear - year > 0) {
//				currentMonth += 12;
//			}
//			int diffMonth = currentMonth - month;
////			System.out.println(diffMonth);
//			// 未考核
//			if (diffMonth > 0) {
//				return null;
//			} else {
//				return ws;
//			}
//		}
//		// 已考核
//		return null;
//	}

	public void evaluateWindowItemScore(
			Map<String, List<JSONObject>> windowScoreMap, Integer itemId, String sheetType, String sheetName, String sheetId) {
		
		DecimalFormat df = new DecimalFormat("0.00");
		List<WindowScore> cslist = new ArrayList<WindowScore>();
		for(Map.Entry<String, List<JSONObject>> entry : windowScoreMap.entrySet()){
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
					List<WindowFormula> list = windowFormulaDao.findByProperty("itemId", parentItemId);
					if (null != list && list.size() > 0) {
						WindowFormula cf = list.get(0);
						String expression = cf.getCalculator().trim();
						List<Variable> variables = new ArrayList<Variable>();
						List<JSONObject> jlist = entry2.getValue();
						Integer centerId = jlist.get(0).getInt("centerId");
						Integer windowId = jlist.get(0).getInt("windowId");
						Double itemWeight = jlist.get(0).getDouble("itemWeight");
						
						Set<WindowParam> set = new HashSet<WindowParam>();
						for (int index = 0; index < jlist.size(); index++) {
							JSONObject json = jlist.get(index);
							
							WindowParam wp = new WindowParam();
							wp.setItemId(json.getInt("itemId"));
							wp.setItemName(json.getString("itemName"));
							wp.setItemValue(json.getDouble("itemValue"));
							wp.setCenterId(json.getInt("centerId"));
							wp.setWindowId(json.getInt("windowId"));
							wp.setEvaluated("true");
							wp.setSheetType(sheetType);
							wp.setSheetId(sheetId);
							
							wp.setEvaluateDate(new Timestamp(new Date().getTime()));
							set.add(wp);
							
							String itemName = json.getString("itemName");
							Double itemValue = json.getDouble("itemValue");
							variables.add(Variable.createVariable(itemName,	itemValue));
						}
						
						Double results = (Double) ExpressionEvaluator.evaluate(expression, variables);
						WindowScore ws = new WindowScore();
						ws.setItemName(cf.getItemName());
						Double result = results * itemWeight;
						ws.setItemScore(Double.valueOf(df.format(result)));
						ws.setCalculator(expression);
						ws.setEvaluateDate(new Timestamp(new Date().getTime()));
						ws.setItemId(parentItemId);
						ws.setCenterId(centerId);
						ws.setWindowId(windowId);
						ws.setEvaluated("true");
						ws.setSheetType(sheetType);
						ws.setSheetId(sheetId);
						cslist.add(ws);
						//save results
						windowScoreDao.save(ws);//保存二级指标
						saveWindowParamScore(set);//保存三级指标
					} else {
						//针对加分项减分项等定量但无公式的二级指标
						List<JSONObject> jlist = entry2.getValue();
						for (int index = 0; index < jlist.size(); index++) {
							JSONObject json = jlist.get(index);
							WindowScore ws = new WindowScore();
							ws.setItemName(json.getString("itemName"));
							Double itemValue = json.getDouble("itemValue");
							Double itemWeight = json.getDouble("itemWeight");
							Double result = itemValue * itemWeight;
							ws.setItemScore(Double.valueOf(df.format(result)));
							ws.setEvaluateDate(new Timestamp(new Date().getTime()));
							ws.setItemId(parentItemId);
							ws.setCenterId(json.getInt("centerId"));
							ws.setWindowId(json.getInt("windowId"));
							ws.setEvaluated("true");
							ws.setSheetType(sheetType);
							ws.setSheetId(sheetId);
							cslist.add(ws);
							windowScoreDao.save(ws);// 保存二级指标
						}
					}
				}
				
			} else if (itemType.equals("定性")) {
				List<JSONObject> jlist = entry.getValue();
				for (int index = 0; index < jlist.size(); index++) {
					JSONObject json = jlist.get(index);
					WindowScore ws = new WindowScore();
					ws.setItemId(json.getInt("itemId"));
					ws.setItemName(json.getString("itemName"));
					ws.setCenterId(json.getInt("centerId"));
					ws.setWindowId(json.getInt("windowId"));
					ws.setEvaluateDate(new Timestamp(new Date().getTime()));
					Double itemValue = json.getDouble("itemValue");
					Double itemWeight = json.getDouble("itemWeight");
					Double result = itemValue * itemWeight;
					ws.setItemScore(Double.valueOf(df.format(result)));// 得分值
					ws.setEvaluated("true");
					ws.setSheetType(sheetType);
					ws.setSheetId(sheetId);
					cslist.add(ws);
					windowScoreDao.save(ws);
				}
			}//end else
		}//end for
		//计算1级指标得分,直接求二级指标得分之和
		WindowScore ws = new WindowScore();
		ws.setItemId(itemId);
		WindowItem ci = (WindowItem) windowItemDao.findByProperty("itemId",itemId).get(0);
		ws.setItemName(ci.getItemName());
		ws.setEvaluateDate(new Timestamp(new Date().getTime()));
		ws.setCenterId(ci.getCenterId());
		ws.setWindowId(cslist.get(0).getWindowId());
		
		Double sumItemScore = 0d;
		for (WindowScore csObj : cslist) {
			sumItemScore += csObj.getItemScore() * ci.getItemWeight();
		}
		ws.setItemScore(Double.valueOf(df.format(sumItemScore)));// 得分值
		ws.setEvaluated("true");
		ws.setSheetType(sheetType);
		ws.setSheetId(sheetId);
		windowScoreDao.save(ws);
		
	}

	@Override
	public WindowScore getCheckedWindowItems(Integer itemId, Integer windowId,
			Integer centerId, String sheetId) {
		return windowScoreDao.findWindowScoreByIds(centerId, windowId, itemId, sheetId);
	}

}
