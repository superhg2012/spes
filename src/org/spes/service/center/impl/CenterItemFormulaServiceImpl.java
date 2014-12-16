package org.spes.service.center.impl;

import java.util.List;

import org.spes.bean.CenterFormula;
import org.spes.dao.item.CenterFormulaDAO;
import org.spes.service.center.CenterItemFormulaService;

public class CenterItemFormulaServiceImpl implements CenterItemFormulaService {

	private CenterFormulaDAO centerFormulaDao = null;

	public List<CenterFormula> getFormulaByItemId(Integer itemId) {
		return centerFormulaDao.getFormulaByItemId(itemId);
	}
	public boolean storeFormulaByItemId(String itemName, String formula,
			Integer itemId) {
		return centerFormulaDao.storeFormulaByItemId(itemName, formula, itemId);
	}
	public boolean updateFormulaByItemId(Integer formulaId, String itemName,
			String formula, Integer itemId) {
		return centerFormulaDao.updateFormulaByItemId(formulaId, itemName, formula, itemId);
	}
	public CenterFormulaDAO getCenterFormulaDao() {
		return centerFormulaDao;
	}

	public void setCenterFormulaDao(CenterFormulaDAO centerFormulaDao) {
		this.centerFormulaDao = centerFormulaDao;
	}

	public List<CenterFormula> getCenterItemFormula(Integer itemId) {
		return centerFormulaDao.getFormulaByItemId(itemId);
	}
}
