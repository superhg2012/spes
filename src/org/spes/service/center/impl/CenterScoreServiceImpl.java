package org.spes.service.center.impl;

import java.sql.Timestamp;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Pattern;

import net.sf.json.JSONObject;

import org.spes.bean.CenterFormula;
import org.spes.bean.CenterItem;
import org.spes.bean.CenterParam;
import org.spes.bean.CenterScore;
import org.spes.common.FormulaParser;
import org.spes.dao.item.CenterFormulaDAO;
import org.spes.dao.item.CenterItemDAO;
import org.spes.dao.item.CenterParamDAO;
import org.spes.dao.item.CenterScoreDAO;
import org.spes.service.center.CenterParamService;
import org.spes.service.center.CenterScoreService;
import org.wltea.expression.ExpressionEvaluator;
import org.wltea.expression.datameta.Variable;

/**
 * ��������ָ�꿼�˵÷�
 * 
 * @author Administrator
 * 
 */
public class CenterScoreServiceImpl implements CenterScoreService {

	private CenterScoreDAO centerScoreDao = null;
	private CenterFormulaDAO centerFormulaDao = null;
	private CenterParamDAO centerParamDao = null;
	private CenterParamService centerParamService = null;
	private CenterItemDAO centerItemDao = null;

	private DecimalFormat dataFormat = new DecimalFormat("0.00");
	public CenterItemDAO getCenterItemDao() {
		return centerItemDao;
	}

	public void setCenterItemDao(CenterItemDAO centerItemDao) {
		this.centerItemDao = centerItemDao;
	}

	public CenterParamService getCenterParamService() {
		return centerParamService;
	}

	public void setCenterParamService(CenterParamService centerParamService) {
		this.centerParamService = centerParamService;
	}

	public void setCenterParamDao(CenterParamDAO centerParamDao) {
		this.centerParamDao = centerParamDao;
	}

	// ����ע��
	public void setCenterScoreDao(CenterScoreDAO centerScoreDao) {
		this.centerScoreDao = centerScoreDao;
	}

	public void setCenterFormulaDao(CenterFormulaDAO centerFormulaDao) {
		this.centerFormulaDao = centerFormulaDao;
	}

	public boolean isDigit(String str) {
		Pattern pattern = Pattern.compile("[0-9]*");
		return pattern.matcher(str).matches();
	}

	public String evaluate(Integer itemId, Integer centerId) {
		CenterFormula cf = centerFormulaDao.getFormulaByItemId(itemId).get(0); // �õ����µ�ָ�����۹�ʽ
		String calculator = null;
		String msg = "";
		List<Variable> variables = new ArrayList<Variable>();
		if (null != cf) {
			calculator = cf.getCalculator();
			String expression = calculator.trim();
			List<String> tokens = FormulaParser.parseExpression(expression);
			for (String token : tokens) {
				List<CenterParam> cplist = null;
				cplist = centerParamDao.findByItemNameAndCenterId(token,
						centerId);
				if (null != cplist && cplist.size() > 0) {
					CenterParam cp = cplist.get(0);
					variables.add(Variable.createVariable(cp.getItemName(), cp
							.getItemValue()));
				} else {
					msg = "����ָ�����������";
					return msg;
				}
			}
			// �滻ָ��ֵ��������ʽ�Ľ��
			Object result = ExpressionEvaluator.evaluate(expression, variables);
			Double itemScore = (Double) result;
			CenterScore cs = new CenterScore();
			cs.setItemId(itemId);
			cs.setCenterId(centerId);
			cs.setEvaluateDate(new Timestamp(new Date().getTime()));
			cs.setCalculator(calculator);
			cs.setItemName(cf.getItemName());
			cs.setItemScore(itemScore);
			centerScoreDao.save(cs);
//			System.out.println(cs);
			msg = "true";
			return msg;
		}
		msg = "ָ�����۹�ʽ������";
		return msg;

	}

	public void evaluateFirstLevel(Integer itemId, Integer centerId) {
		//
	}

	public Integer evaluateCenterItemScore(Set<CenterParam> set, Integer itemId) {
		List<CenterFormula> list = centerFormulaDao.findByProperty("itemId", itemId);
		Integer result = -1;
		if (null != list && list.size() > 0) {
            CenterFormula cf = list.get(0);
            String expression = cf.getCalculator().trim();
            List<Variable> variables = new ArrayList<Variable>();
            Integer centerId = -1;
            for(CenterParam cp : set){
            	variables.add(Variable.createVariable(cp.getItemName(), cp.getItemValue()));
            	centerId = cp.getCenterId();
            }
            // compute results;
            Double results = (Double) ExpressionEvaluator.evaluate(expression,variables);
            
            CenterScore cs = new CenterScore();
            cs.setItemName(cf.getItemName());
            cs.setItemScore(results);
            cs.setCalculator(expression);
            cs.setEvaluateDate(new Timestamp(new Date().getTime()));
            cs.setItemId(itemId);
            cs.setCenterId(centerId);

            //save results
           result =  centerScoreDao.save(cs);
		}
		return result;
	}

	public Integer evaluateCenterItemScore2(CenterParam cp, Integer itemId) {
		CenterScore cs = new CenterScore();
		cs.setItemId(itemId);
		cs.setItemName(cp.getItemName());
		cs.setEvaluateDate(new Timestamp(new Date().getTime()));
		cs.setCenterId(cp.getCenterId());
		cs.setItemScore(cp.getItemValue());
		return centerScoreDao.save(cs);
	}

	public Integer evaluateCenterItemScore(List<JSONObject> cplist, String flag) {
		if (flag.equals("FIXED")) {// ����
			// ����ָ��÷�
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
			}

			for (Map.Entry<Integer, List<JSONObject>> entry : map.entrySet()) {
				Integer parentItemId = entry.getKey();
				List<CenterFormula> list = centerFormulaDao.findByProperty("itemId", parentItemId);
				if (null != list && list.size() > 0) {
					CenterFormula cf = list.get(0);
					String expression = cf.getCalculator().trim();
					List<Variable> variables = new ArrayList<Variable>();
					List<JSONObject> jlist = entry.getValue();
					Integer centerId = jlist.get(0).getInt("centerId");
					Double itemWeight = jlist.get(0).getDouble("itemWeight");
					
					Set<CenterParam> set = new HashSet<CenterParam>();
					for (int index = 0; index < jlist.size(); index++) {
						JSONObject json = jlist.get(index);
						
						CenterParam cp = new CenterParam();
						cp.setItemId(json.getInt("itemId"));
						cp.setItemName(json.getString("itemName"));
						cp.setItemValue(json.getDouble("itemValue"));
						cp.setCenterId(json.getInt("centerId"));
						cp.setEvaluateDate(new Timestamp(new Date().getTime()));
						set.add(cp);
						
						String itemName = json.getString("itemName");
						Double itemValue = json.getDouble("itemValue");
						variables.add(Variable.createVariable(itemName,	itemValue));
					}
					
					Double results = (Double) ExpressionEvaluator.evaluate(expression, variables);
					CenterScore cs = new CenterScore();
					cs.setItemName(cf.getItemName());
					cs.setItemScore(Double.valueOf(dataFormat.format(results * itemWeight)));
					cs.setCalculator(expression);
					cs.setEvaluateDate(new Timestamp(new Date().getTime()));
					cs.setItemId(parentItemId);
					cs.setCenterId(centerId);
					// save results
					centerScoreDao.save(cs);
					centerParamService.saveCenterParamScore(set);
					
				}
			}
		} else {
			// ����ָ��÷�= ����ָ���� * ����ָ��Ȩ��
			for (int index = 0; index < cplist.size(); index++) {
				JSONObject json = cplist.get(index);
				CenterScore cs = new CenterScore();
				cs.setItemId(json.getInt("itemId"));
				cs.setItemName(json.getString("itemName"));
				cs.setEvaluateDate(new Timestamp(new Date().getTime()));
				cs.setCenterId(json.getInt("centerId"));
				Double itemValue = json.getDouble("itemValue");
				Double itemWeight = json.getDouble("itemWeight");
				cs.setItemScore(itemValue * itemWeight);// �÷�ֵ
				centerScoreDao.save(cs);
			}
		}
		return null;
	}

	/**
	 * 
	 */
	public Integer evaluateCenterItemScore(
			Map<String, List<JSONObject>> itemscoreMap, Integer itemId) {

		DecimalFormat df = new DecimalFormat("0.00");
		List<CenterScore> cslist = new ArrayList<CenterScore>();
		for(Map.Entry<String, List<JSONObject>> entry : itemscoreMap.entrySet()){
			String itemType = entry.getKey();
			if (itemType.equals("����")) {
				List<JSONObject> cplist = entry.getValue();
				// ����ָ��÷�
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
				}

				for (Map.Entry<Integer, List<JSONObject>> entry2 : map.entrySet()) {
					Integer parentItemId = entry2.getKey();
					List<CenterFormula> list = centerFormulaDao.findByProperty("itemId", parentItemId);
					if (null != list && list.size() > 0) {
						CenterFormula cf = list.get(0);
						String expression = cf.getCalculator().trim();
						List<Variable> variables = new ArrayList<Variable>();
						List<JSONObject> jlist = entry2.getValue();
						Integer centerId = jlist.get(0).getInt("centerId");
						Double itemWeight = jlist.get(0).getDouble("itemWeight");
						
						Set<CenterParam> set = new HashSet<CenterParam>();
						for (int index = 0; index < jlist.size(); index++) {
							JSONObject json = jlist.get(index);
							
							CenterParam cp = new CenterParam();
							cp.setItemId(json.getInt("itemId"));
							cp.setItemName(json.getString("itemName"));
							cp.setItemValue(json.getDouble("itemValue"));
							cp.setCenterId(json.getInt("centerId"));
							cp.setEvaluateDate(new Timestamp(new Date().getTime()));
							set.add(cp);
							
							String itemName = json.getString("itemName");
							Double itemValue = json.getDouble("itemValue");
							variables.add(Variable.createVariable(itemName,	itemValue));
						}
						
						Double results = (Double) ExpressionEvaluator.evaluate(expression, variables);
//						DataFormat df = new DataFormat("0.00");
						CenterScore cs = new CenterScore();
						cs.setItemName(cf.getItemName());
						cs.setItemScore(results * itemWeight);
						cs.setCalculator(expression);
						cs.setEvaluateDate(new Timestamp(new Date().getTime()));
						cs.setItemId(parentItemId);
						cs.setCenterId(centerId);
						cs.setEvaluated("true");
						cslist.add(cs);
						// save results
						centerScoreDao.save(cs);//�������ָ��
						centerParamService.saveCenterParamScore(set);//��������ָ��
					} else {
						List<JSONObject> jlist = entry2.getValue();
						for (int index = 0; index < jlist.size(); index++) {
							JSONObject json = jlist.get(index);
							CenterScore cs = new CenterScore();
							cs.setItemName(json.getString("itemName"));
							Double itemWeight = json.getDouble("itemWeight");
							Double itemValue = json.getDouble("itemValue");
							Double result = itemWeight * itemValue;
							cs.setItemScore(Double.valueOf(df.format(result)));
							cs.setEvaluateDate(new Timestamp(new Date().getTime()));
							cs.setItemId(parentItemId);
							cs.setCenterId(json.getInt("centerId"));
							cs.setEvaluated("true");
							cslist.add(cs);
							// save results
							centerScoreDao.save(cs);//�������ָ��
						}
					}
				}
			} else if (itemType.equals("����")) {
				List<JSONObject> jlist = entry.getValue();
				for (int index = 0; index < jlist.size(); index++) {
					JSONObject json = jlist.get(index);
					CenterScore cs = new CenterScore();
					cs.setItemId(json.getInt("itemId"));
					cs.setItemName(json.getString("itemName"));
					cs.setEvaluateDate(new Timestamp(new Date().getTime()));
					cs.setCenterId(json.getInt("centerId"));
					Double itemValue = json.getDouble("itemValue");
					Double itemWeight = json.getDouble("itemWeight");
					Double result = itemValue * itemWeight;
					cs.setItemScore(Double.valueOf(df.format(result)));// �÷�ֵ
					cs.setEvaluated("true");
					cslist.add(cs);
					centerScoreDao.save(cs);
				}

			}//end else
		}//end for
		//����1��ָ��÷�,ֱ�������ָ��÷�֮��
		CenterScore cs = new CenterScore();
		cs.setItemId(itemId);
		CenterItem ci = (CenterItem) centerItemDao.findByProperty("itemId",itemId).get(0);
		cs.setItemName(ci.getItemName());
		cs.setEvaluateDate(new Timestamp(new Date().getTime()));
		cs.setCenterId(ci.getCenterId());
		Double sumItemScore = 0d;
		for (CenterScore csObj : cslist) {
			sumItemScore += csObj.getItemScore() * ci.getItemWeight();
		}
		cs.setItemScore(Double.valueOf(df.format(sumItemScore)));// �÷�ֵ
		cs.setEvaluated("true");
		centerScoreDao.save(cs);
		return null;
	}

	public Integer evaluateCenterItemScore(
			Map<String, List<JSONObject>> itemscoreMap, Integer itemId,
			String checkType, String sheetName) {
		DecimalFormat df = new DecimalFormat("0.00");
		List<CenterScore> cslist = new ArrayList<CenterScore>();
		for(Map.Entry<String, List<JSONObject>> entry : itemscoreMap.entrySet()){
			String itemType = entry.getKey();
			if (itemType.equals("����")) {
				List<JSONObject> cplist = entry.getValue();
				// ����ָ��÷�
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
				}

				for (Map.Entry<Integer, List<JSONObject>> entry2 : map.entrySet()) {
					Integer parentItemId = entry2.getKey();
					List<CenterFormula> list = centerFormulaDao.findByProperty("itemId", parentItemId);
					if (null != list && list.size() > 0) {
						CenterFormula cf = list.get(0);
						String expression = cf.getCalculator().trim();
						List<Variable> variables = new ArrayList<Variable>();
						List<JSONObject> jlist = entry2.getValue();
						Integer centerId = jlist.get(0).getInt("centerId");
						Double itemWeight = jlist.get(0).getDouble("itemWeight");
						
						Set<CenterParam> set = new HashSet<CenterParam>();
						for (int index = 0; index < jlist.size(); index++) {
							JSONObject json = jlist.get(index);
							
							CenterParam cp = new CenterParam();
							cp.setItemId(json.getInt("itemId"));
							cp.setItemName(json.getString("itemName"));
							cp.setItemValue(json.getDouble("itemValue"));
							cp.setCenterId(json.getInt("centerId"));
							cp.setEvaluateDate(new Timestamp(new Date().getTime()));
							cp.setEvaluated("true");
							cp.setSheetType(checkType);
							cp.setSheetId(sheetName);
							set.add(cp);
							
							String itemName = json.getString("itemName");
							Double itemValue = json.getDouble("itemValue");
							variables.add(Variable.createVariable(itemName,	itemValue));
						}
						
						Double results = (Double) ExpressionEvaluator.evaluate(expression, variables);
//						DataFormat df = new DataFormat("0.00");
						CenterScore cs = new CenterScore();
						cs.setItemName(cf.getItemName());
						cs.setItemScore(results * itemWeight);
						cs.setCalculator(expression);
						cs.setEvaluateDate(new Timestamp(new Date().getTime()));
						cs.setItemId(parentItemId);
						cs.setCenterId(centerId);
						cs.setEvaluated("true");
						cs.setSheetType(checkType);
						cs.setSheetId(sheetName);
						cslist.add(cs);
						// save results
						centerScoreDao.save(cs);//�������ָ��
						centerParamService.saveCenterParamScore(set);//��������ָ��
					} else {
						List<JSONObject> jlist = entry2.getValue();
						for (int index = 0; index < jlist.size(); index++) {
							JSONObject json = jlist.get(index);
							CenterScore cs = new CenterScore();
							cs.setItemName(json.getString("itemName"));
							Double itemWeight = json.getDouble("itemWeight");
							Double itemValue = json.getDouble("itemValue");
							Double result = itemWeight * itemValue;
							cs.setItemScore(Double.valueOf(df.format(result)));
							cs.setEvaluateDate(new Timestamp(new Date().getTime()));
							cs.setItemId(parentItemId);
							cs.setCenterId(json.getInt("centerId"));
							cs.setEvaluated("true");
							cs.setSheetType(checkType);
							cs.setSheetId(sheetName);
							cslist.add(cs);
							centerScoreDao.save(cs);//�������ָ��
						}
					}
				}
			} else if (itemType.equals("����")) {
				List<JSONObject> jlist = entry.getValue();
				for (int index = 0; index < jlist.size(); index++) {
					JSONObject json = jlist.get(index);
					CenterScore cs = new CenterScore();
					cs.setItemId(json.getInt("itemId"));
					cs.setItemName(json.getString("itemName"));
					cs.setCenterId(json.getInt("centerId"));
					cs.setEvaluateDate(new Timestamp(new Date().getTime()));
					Double itemValue = json.getDouble("itemValue");
					Double itemWeight = json.getDouble("itemWeight");
					Double result = itemValue * itemWeight;
					cs.setItemScore(Double.valueOf(df.format(result)));// �÷�ֵ
					cs.setEvaluated("true");
					cs.setSheetType(checkType);
					cs.setSheetId(sheetName);
					cslist.add(cs);
					centerScoreDao.save(cs);
				}

			}//end else
		}//end for
		//����1��ָ��÷�,ֱ�������ָ��÷�֮��
		CenterScore cs = new CenterScore();
		cs.setItemId(itemId);
		CenterItem ci = (CenterItem) centerItemDao.findByProperty("itemId",itemId).get(0);
		cs.setItemName(ci.getItemName());
		cs.setEvaluateDate(new Timestamp(new Date().getTime()));
		cs.setCenterId(ci.getCenterId());
		Double sumItemScore = 0d;
		for (CenterScore csObj : cslist) {
			sumItemScore += csObj.getItemScore() * ci.getItemWeight();
		}
		cs.setItemScore(Double.valueOf(df.format(sumItemScore)));// �÷�ֵ
		cs.setEvaluated("true");
		cs.setSheetType(checkType);
		cs.setSheetId(sheetName);
		Integer ret = centerScoreDao.save(cs);
		return ret;
	}
	
	
    public CenterScore getCheckedCenterItems(final Integer itemId, final Integer centerId, final String sheetId){
    	return centerScoreDao.findByCenterIdAndItemId(centerId, itemId, sheetId);
    }

	public CenterScore getCenterScoreByIds(Integer itemId, Integer centerId) {
		return  centerScoreDao.findByCenterIdAndItemId(centerId, itemId);
	}
    
	public CenterScore getCenterScoreByParams(Integer itemId, Integer centerId,
			String checkType, String sheetName, String userName) {
		return centerScoreDao.findByParams(centerId, itemId, checkType, sheetName, userName);
	}

	@Override
	public Integer evaluateCenterItemScore(
			Map<String, List<JSONObject>> itemscoreMap, Integer itemId,
			String sheetType, Integer sheetId) {
		DecimalFormat df = new DecimalFormat("0.00");
		List<CenterScore> cslist = new ArrayList<CenterScore>();
		for(Map.Entry<String, List<JSONObject>> entry : itemscoreMap.entrySet()){
			String itemType = entry.getKey();
			if (itemType.equals("����")) {
				List<JSONObject> cplist = entry.getValue();
				// ����ָ��÷�
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
				}

				for (Map.Entry<Integer, List<JSONObject>> entry2 : map.entrySet()) {
					Integer parentItemId = entry2.getKey();
					List<CenterFormula> list = centerFormulaDao.findByProperty("itemId", parentItemId);
					if (null != list && list.size() > 0) {
						CenterFormula cf = list.get(0);
						String expression = cf.getCalculator().trim();
						List<Variable> variables = new ArrayList<Variable>();
						List<JSONObject> jlist = entry2.getValue();
						Integer centerId = jlist.get(0).getInt("centerId");
						Double itemWeight = jlist.get(0).getDouble("itemWeight");
						
						Set<CenterParam> set = new HashSet<CenterParam>();
						for (int index = 0; index < jlist.size(); index++) {
							JSONObject json = jlist.get(index);
							
							CenterParam cp = new CenterParam();
							cp.setItemId(json.getInt("itemId"));
							cp.setItemName(json.getString("itemName"));
							cp.setItemValue(json.getDouble("itemValue"));
							cp.setCenterId(json.getInt("centerId"));
							cp.setEvaluateDate(new Timestamp(new Date().getTime()));
							cp.setEvaluated("true");
							cp.setSheetType(sheetType);//month
							cp.setSheetId(sheetId.toString());
							set.add(cp);
							
							String itemName = json.getString("itemName");
							Double itemValue = json.getDouble("itemValue");
							variables.add(Variable.createVariable(itemName,	itemValue));
						}
						
						Double results = (Double) ExpressionEvaluator.evaluate(expression, variables);
//						DataFormat df = new DataFormat("0.00");
						CenterScore cs = new CenterScore();
						cs.setItemName(cf.getItemName());
						cs.setItemScore(results * itemWeight);
						cs.setCalculator(expression);
						cs.setEvaluateDate(new Timestamp(new Date().getTime()));
						cs.setItemId(parentItemId);
						cs.setCenterId(centerId);
						cs.setEvaluated("true");
						cs.setSheetType(sheetType);
						cs.setSheetId(sheetId.toString());
						cslist.add(cs);
						// save results
						centerScoreDao.save(cs);//�������ָ��
						centerParamService.saveCenterParamScore(set);//��������ָ��
					} else {
						List<JSONObject> jlist = entry2.getValue();
						for (int index = 0; index < jlist.size(); index++) {
							JSONObject json = jlist.get(index);
							CenterScore cs = new CenterScore();
							cs.setItemName(json.getString("itemName"));
							Double itemWeight = json.getDouble("itemWeight");
							Double itemValue = json.getDouble("itemValue");
							Double result = itemWeight * itemValue;
							cs.setItemScore(Double.valueOf(df.format(result)));
							cs.setEvaluateDate(new Timestamp(new Date().getTime()));
							cs.setItemId(parentItemId);
							cs.setCenterId(json.getInt("centerId"));
							cs.setEvaluated("true");
							cs.setSheetType(sheetType);
							cs.setSheetId(sheetId.toString());
							cslist.add(cs);
							centerScoreDao.save(cs);//�������ָ��
						}
					}
				}
			} else if (itemType.equals("����")) {
				List<JSONObject> jlist = entry.getValue();
				for (int index = 0; index < jlist.size(); index++) {
					JSONObject json = jlist.get(index);
					CenterScore cs = new CenterScore();
					cs.setItemId(json.getInt("itemId"));
					cs.setItemName(json.getString("itemName"));
					cs.setCenterId(json.getInt("centerId"));
					cs.setEvaluateDate(new Timestamp(new Date().getTime()));
					Double itemValue = json.getDouble("itemValue");
					Double itemWeight = json.getDouble("itemWeight");
					Double result = itemValue * itemWeight;
					cs.setItemScore(Double.valueOf(df.format(result)));// �÷�ֵ
					cs.setEvaluated("true");
					cs.setSheetType(sheetType);
					cs.setSheetId(sheetId.toString());
					cslist.add(cs);
					centerScoreDao.save(cs);
				}

			}//end else
		}//end for
		//����1��ָ��÷�,ֱ�������ָ��÷�֮��
		CenterScore cs = new CenterScore();
		cs.setItemId(itemId);
		CenterItem ci = (CenterItem) centerItemDao.findByProperty("itemId",itemId).get(0);
		cs.setItemName(ci.getItemName());
		cs.setEvaluateDate(new Timestamp(new Date().getTime()));
		cs.setCenterId(ci.getCenterId());
		Double sumItemScore = 0d;
		for (CenterScore csObj : cslist) {
			sumItemScore += csObj.getItemScore() * ci.getItemWeight();
		}
		cs.setItemScore(Double.valueOf(df.format(sumItemScore)));// �÷�ֵ
		cs.setEvaluated("true");
		cs.setSheetType(sheetType);
		cs.setSheetId(sheetId.toString());
		Integer ret = centerScoreDao.save(cs);
		return ret;
	}

}
