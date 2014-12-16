package org.spes.service.window.impl;

import java.util.List;

import org.spes.bean.WindowFormula;
import org.spes.dao.item.WindowFormulaDAO;
import org.spes.service.window.WindowFormulaService;

public class WindowFormulaServiceImpl implements WindowFormulaService {

	private WindowFormulaDAO windowFormulaDao = null;

	public WindowFormulaDAO getWindowFormulaDao() {
		return windowFormulaDao;
	}

	public void setWindowFormulaDao(WindowFormulaDAO windowFormulaDao) {
		this.windowFormulaDao = windowFormulaDao;
	}

	public List<WindowFormula> getFormulaByItemId(Integer itemId) {
		return windowFormulaDao.getFormulaByItemId(itemId);
	}

	public boolean storeFormulaByItemId(String itemName, String formula,
			Integer itemId) {
		return windowFormulaDao.storeFormulaByItemId(itemName, formula, itemId);
	}

	public boolean updateFormulaByItemId(Integer formulaId, String itemName,
			String formula, Integer itemId) {
		return windowFormulaDao.updateFormulaByItemId(formulaId, itemName, formula, itemId);
	}

	public List<WindowFormula> getWindowFormulaByItemId(Integer itemId) {
		return windowFormulaDao.findByProperty("itemId", itemId);
	}
}
