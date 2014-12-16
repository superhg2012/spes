package org.spes.service.window.impl;

import java.util.ArrayList;
import java.util.List;

import org.spes.bean.WindowFormula;
import org.spes.bean.WindowItem;
import org.spes.common.FormulaParser;
import org.spes.dao.item.WindowFormulaDAO;
import org.spes.dao.item.WindowItemDAO;
import org.spes.service.window.WindowItemService;

public class WindowItemServiceImpl implements WindowItemService {
    
	private WindowItemDAO windowItemDao = null;
	private WindowFormulaDAO windowFormulaDao = null;
 
	
	public WindowFormulaDAO getWindowFormulaDao() {
		return windowFormulaDao;
	}

	public void setWindowFormulaDao(WindowFormulaDAO windowFormulaDao) {
		this.windowFormulaDao = windowFormulaDao;
	}

	public WindowItemDAO getWindowItemDao() {
		return windowItemDao;
	}

	public void setWindowItemDao(WindowItemDAO windowItemDao) {
		this.windowItemDao = windowItemDao;
	}

	public boolean curParaIsInDatabase(String paraname,int centerId) {
		return windowItemDao.curParaInDatabase(paraname,centerId);
	}

	public boolean deleteAllFirstLevelPara(int centerId) {
		return windowItemDao.deleteFirstLevelPara(centerId);
	}

	public int deleteCurParaInfo(int itemId) {
		return windowItemDao.deleteCurParaInfo(itemId);
	}

	public boolean deleteSecondLevelPara(int itemId) {
		return windowItemDao.deleteSecondLevelPara(itemId);
	}

	public List<WindowItem> getAllParameterData(int centerId) {
		return windowItemDao.getAllParaData(centerId);
	}

//	public List<WindowItem> getCenterItemById(Integer centerId) {
//		return windowItemDao.findCenterItemById(centerId);
//	}

	public List<WindowItem> getCenterItemsByFormula(Integer itemId,
			Integer centerId) {
		return null;
	}

	public List<WindowItem> getCurThirdParas(int itemId) {
		return windowItemDao.getCurThirdParas(itemId);
	}

	public List<WindowItem> getFirstLevelAndSecondLevelPara(int centerItemId,int centerId) {
		return windowItemDao.getFirstLevelAndSecondLevelPara(centerItemId,centerId);
	}

	public WindowItem getFirstLevelparameter(int centerId) {
		return windowItemDao.getFirstLevelPara(centerId);
	}

	public ArrayList<WindowItem> getSecondAndThridParameters(int firstItemId) {
		return windowItemDao.getCurSecondAndThridParameters(firstItemId);
	}

	public boolean otherSameNameParaInDatabase(int centerItemId, String paraname,int centerId) {
		return windowItemDao.otherSameNameParaInDatabase(centerItemId, paraname,centerId);
	}

	public int storeFirstLevelPara(WindowItem firstPara) {
		return windowItemDao.storeFirstLevelPara(firstPara);
	}

	public int storeSecondAndThirdPara(WindowItem item) {
		return windowItemDao.storeSecondAndThirdPara(item);
	}

	public int updateCenterItemByConcreteItem(WindowItem item) {
		return windowItemDao.updateCenterItemByConcreteItem(item);
	}

	public boolean updateCenterItemById(int centerItemId, String paraname,
			double weight, boolean enable, String paraType) {
		return windowItemDao.updateCenterItemById(centerItemId, paraname, weight, enable, paraType);
		
	}

	public List<WindowItem> getWindowItems(Integer centerId) {
		return null;
	}

	@SuppressWarnings("unchecked")
	public List<WindowItem> getWindowItemsByFormula(Integer itemId,
			Integer centerId) {
//		System.out.println(itemId + "\t" + centerId);
		List<WindowFormula> wlist = windowFormulaDao.findByProperty("itemId",itemId);
//		System.out.println("[1]" + wlist.toString());
		if (null != wlist && wlist.size() > 0) {
			WindowFormula wf = wlist.get(0);
			String calculator = wf.getCalculator();
			List<WindowItem> list = new ArrayList<WindowItem>();
			if (null != calculator) {
				List<String> tokens = FormulaParser.parseExpression(calculator.trim());
//				System.out.println("tokens:" + tokens.toString());
				for (String token : tokens) {
					List<WindowItem> ilist = windowItemDao.findByItemNameAndIds(token, itemId, centerId);
//					System.out.println("[3]:" + ilist);
//					WindowItem wi = windowItemDao.findByItemNameAndCenterId(token, centerId).get(0);
					WindowItem wi = null;
					if (null != ilist && ilist.size() > 0) {
						wi = ilist.get(0);
						list.add(wi);
					}
				}
				return list;
			} else {
				//未设置评价公式
			}
				
		} else {
			//针对加分项减分项等主观评价指标
			List<WindowItem> ilist = windowItemDao.findByItemIdAndCenterId(itemId, centerId);
			if (null != ilist && ilist.size() > 0) {
				return ilist;
			}
		}
		return null;
	}
	
	public List<WindowItem> getWindowItemsOfFirstGrade(Integer centerId, Integer itemGrade){
		return windowItemDao.findByItemGradeAndCenterId(centerId, itemGrade);
	}

	public List<WindowItem> getSonItemByParentId(Integer itemId,
			Integer centerId) {
		return windowItemDao.fingByParentIdAndCenterId(itemId, centerId);
	}

}
