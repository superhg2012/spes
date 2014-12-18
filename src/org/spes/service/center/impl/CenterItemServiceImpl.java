package org.spes.service.center.impl;

import java.util.ArrayList;
import java.util.List;

import org.spes.bean.CenterFormula;
import org.spes.bean.CenterItem;
import org.spes.common.FormulaParser;
import org.spes.dao.item.CenterFormulaDAO;
import org.spes.dao.item.CenterItemDAO;
import org.spes.service.center.CenterItemService;

public class CenterItemServiceImpl implements CenterItemService {

	// 数据库底层DAO层数据
	private CenterItemDAO centerItemDao = null;
	private CenterFormulaDAO centerFormulaDao = null;

	public void setCenterFormulaDao(CenterFormulaDAO centerFormulaDao) {
		this.centerFormulaDao = centerFormulaDao;
	}

	public List<CenterItem> getAllParameterData() {
		return centerItemDao.getAllParaData();
	}

	public CenterItem getFirstLevelparameter(int centerId) {
		return centerItemDao.getFirstLevelPara(centerId);
	}

	public List<CenterItem> getFirstLevelAndSecondLevelPara(int centerItemId) {
		return centerItemDao.getFirstLevelAndSecondLevelPara(centerItemId);
	}

	public boolean updateCenterItemById(int centerItemId, String paraname,
			double weight, boolean enable, String paraType) {
		return centerItemDao.updateCenterItemById(centerItemId, paraname,
				weight, enable, paraType);
	}

	public int updateCenterItemByConcreteItem(CenterItem item) {
		return centerItemDao.updateCenterItemByConcreteItem(item);
	}

	public int storeFirstLevelParameter(CenterItem item) {
		return centerItemDao.storeFirstLevelPara(item);
	}

	public boolean curParaIsInDatabase(String paraname) {
		return centerItemDao.curParaInDatabase(paraname);
	}

	public boolean otherSameNameParaInDatabase(int centerItemId, String paraname) {
		return centerItemDao
				.otherSameNameParaInDatabase(centerItemId, paraname);
	}

	public boolean deleteAllFirstLevelPara(int centerId) {
		return centerItemDao.deleteFirstLevelPara(centerId);
	}

	public int deleteCurParaInfo(int itemId) {
		return centerItemDao.deleteCurParaInfo(itemId);
	}

	public boolean deleteSecondLevelPara(int itemId) {
		return centerItemDao.deleteSecondLevelPara(itemId);
	}

	public void setCenterItemDao(CenterItemDAO centerItemDao) {
		this.centerItemDao = centerItemDao;
	}

	public ArrayList<CenterItem> getSecondAndThridParameters(int firstItemId) {
		return this.centerItemDao.getCurSecondAndThridParameters(firstItemId);
	}

	public List<CenterItem> getCenterItemById(Integer centerId) {
		return centerItemDao.findCenterItemById(centerId);
	}

	public List<CenterItem> getCurThirdParas(int itemId) {
		return centerItemDao.getCurThirdParas(itemId);
	}
	
	public int storeFirstLevelPara(CenterItem firstPara) {
		return centerItemDao.storeFirstLevelPara(firstPara);
	}

	public int storeSecondAndThirdPara(CenterItem item) {
		return centerItemDao.storeSecondAndThirdPara(item);
	}
	

	public List<CenterItem> getCenterItemsByFormula(Integer itemId,
			Integer centerId) {
		List<CenterFormula> clist = centerFormulaDao.findByProperty("itemId",itemId);
		if (null != clist && clist.size() > 0) {
			CenterFormula cf = clist.get(0);
			String expression = cf.getCalculator().trim();
			if (null != expression) {
				List<String> tokens = FormulaParser.parseExpression(expression);
				List<CenterItem> list = new ArrayList<CenterItem>();
				for (String token : tokens) {
//					CenterItem ci = centerItemDao.findByItemNameAndCenterId(token, centerId).get(0);
					List<CenterItem> plist = centerItemDao.findByItemNameAndItemIds(token, itemId, centerId);
					CenterItem ci = null;
					if (null != plist && plist.size() > 0) {
						ci = plist.get(0);
						list.add(ci);
					}
				}
				return list;
			} else{
				//未设置考核公式
			}
		} else {
			//针对加分项减分项等主观评价指标
			List<CenterItem> ilist = centerItemDao.findByItemIdAndCenterId(itemId, centerId);
			if (null != ilist && ilist.size() > 0) {
				return ilist;
			}
		}
		return null;
	}

	public List<CenterItem> getItemByItemGradeAndId(Integer centerId,
			Integer itemGrade) {
		return centerItemDao.findByCenterIdAndItemGrade(centerId, itemGrade);
	}

	public List<CenterItem> getSonItemByParentId(Integer parentItemId) {
		return centerItemDao.findByProperty("parentId", parentItemId);
	}

	@Override
	public List<CenterItem> getAllParameterOfCenter(int centerId) {
		return centerItemDao.findByProperty("centerId", centerId);
	}

	@Override
	public List<CenterItem> getSecondLevelParameterOfFirst(int itemId,
			int centerId) {
		return centerItemDao.findByParentIdAndCenterId(itemId, centerId);
	}

	@Override
	public List<CenterItem> getThirdLevelParameterOfSecond(int itemId,
			int centerId) {
		return centerItemDao.findByParentIdAndCenterId(itemId, centerId);
	}
}
