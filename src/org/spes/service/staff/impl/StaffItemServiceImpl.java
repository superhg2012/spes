package org.spes.service.staff.impl;

import java.util.ArrayList;
import java.util.List;

import org.spes.bean.StaffFormula;
import org.spes.bean.StaffItem;
import org.spes.common.FormulaParser;
import org.spes.dao.item.StaffFormulaDAO;
import org.spes.dao.item.StaffItemDAO;
import org.spes.service.staff.StaffItemService;

public class StaffItemServiceImpl implements StaffItemService {

	private StaffItemDAO staffItemDao = null;
	private StaffFormulaDAO staffFormulaDao = null;

	public void setStaffFormulaDao(StaffFormulaDAO staffFormulaDao) {
		this.staffFormulaDao = staffFormulaDao;
	}

	public void setStaffItemDao(StaffItemDAO staffItemDao) {
		this.staffItemDao = staffItemDao;
	}

	public List<StaffItem> getStaffItemByWinIdAndCenId(Integer windowId,
			Integer centerId) {
		return staffItemDao.findStaffItemByProperty(windowId, centerId);
	}

	public boolean curParaIsInDatabase(String paraname,int centerId,int windowId) {
		return staffItemDao.curParaInDatabase(paraname,centerId,windowId);
	}

	public boolean deleteAllFirstLevelPara(int centerId) {
		return staffItemDao.deleteFirstLevelPara(centerId);
	}

	public int deleteCurParaInfo(int itemId) {
		return staffItemDao.deleteCurParaInfo(itemId);
	}

	public boolean deleteSecondLevelPara(int itemId) {
		return staffItemDao.deleteSecondLevelPara(itemId);
	}

	public List<StaffItem> getAllParameterData(int centerId,int windowId) {
		return staffItemDao.getAllParaData(centerId,windowId);
	}

	public List<StaffItem> getCenterItemById(Integer centerId) {
		return null;
	}

	public List<StaffItem> getCenterItemsByFormula(Integer itemId,
			Integer centerId) {
		return null;
	}

	public List<StaffItem> getCurThirdParas(int itemId) {
		return staffItemDao.getCurThirdParas(itemId);
	}

	public List<StaffItem> getFirstLevelAndSecondLevelPara(int centerItemId,int centerId,int windowId) {
		return staffItemDao.getFirstLevelAndSecondLevelPara(centerItemId,centerId,windowId);
	}

	public StaffItem getFirstLevelparameter(int centerId) {
		return staffItemDao.getFirstLevelPara(centerId);
	}

	public ArrayList<StaffItem> getSecondAndThridParameters(int firstItemId) {
		return staffItemDao.getCurSecondAndThridParameters(firstItemId);
	}

	public boolean otherSameNameParaInDatabase(int centerItemId, String paraname,int centerId,int windowId) {
		return staffItemDao.otherSameNameParaInDatabase(centerItemId, paraname,centerId,windowId);
	}

	public int storeFirstLevelPara(StaffItem firstPara) {
		return staffItemDao.storeFirstLevelPara(firstPara);
	}

	public int storeSecondAndThirdPara(StaffItem item) {
		return staffItemDao.storeSecondAndThirdPara(item);
	}

	public int updateCenterItemByConcreteItem(StaffItem item) {
		return staffItemDao.updateCenterItemByConcreteItem(item);
	}

	public boolean updateCenterItemById(int centerItemId, String paraname,
			double weight, boolean enable, String paraType) {
		return staffItemDao.updateCenterItemById(centerItemId, paraname, weight, enable, paraType);
		
	}
	
	@SuppressWarnings("unchecked")
	public List<StaffItem> getStaffItemByFormula(Integer itemId,
			Integer windowId, Integer centerId) {
		List<StaffFormula> flist = staffFormulaDao.findByProperty("itemId", itemId);
		if (null != flist && flist.size() > 0) {
			StaffFormula sf = flist.get(0);
			String expression = sf.getCaclulator().trim();
			List<StaffItem> list = new ArrayList<StaffItem>();
			if (expression != null && !expression.equals("")) {
				List<String> tokens = FormulaParser.parseExpression(expression);
				for (String token : tokens) {
//					StaffItem si = staffItemDao.findStaffByItemName(token,windowId, centerId).get(0);
					List<StaffItem> slist = staffItemDao.findByItemNameAndIds(token, itemId, windowId, centerId);
					StaffItem si = null;
					if (null != slist && slist.size() > 0) {
						si = slist.get(0);
						list.add(si);
					}
				}
				return list;
			} else {
				//未设置公式
			}
		} else {
			//针对加分项减分项等主观评价指标
			List<StaffItem> ilist = staffItemDao.findStaffItemByIds(itemId, windowId, centerId);
			if (null != ilist && ilist.size() > 0) {
				return ilist;
			}
		}
		return null;
	}

	public List<StaffItem> getStaffItemByIdsAndItemGrade(Integer windowId,
			Integer centerId, Integer itemGrade) {
		return staffItemDao.findStaffItemByProperty(windowId, centerId,
				itemGrade);
	}

	@SuppressWarnings("unchecked")
	public List<StaffItem> getSonItemByParentId(Integer parentItemId) {
		return staffItemDao.findByProperty("parentId", parentItemId);
	}

}
