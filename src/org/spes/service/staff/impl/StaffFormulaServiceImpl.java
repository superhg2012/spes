package org.spes.service.staff.impl;

import java.util.List;

import org.spes.bean.StaffFormula;
import org.spes.dao.item.StaffFormulaDAO;
import org.spes.service.staff.StaffFormulaService;

public class StaffFormulaServiceImpl implements StaffFormulaService {

	private StaffFormulaDAO staffFormulaDao = null;


	public List<StaffFormula> getFormulaByItemId(Integer itemId) {
		return staffFormulaDao.getFormulaByItemId(itemId);
	}

	public boolean storeFormulaByItemId(String itemName, String formula,
			Integer itemId) {
		return staffFormulaDao.storeFormulaByItemId(itemName, formula, itemId);
	}

	public boolean updateFormulaByItemId(Integer formulaId, String itemName,
			String formula, Integer itemId) {
		return staffFormulaDao.updateFormulaByItemId(formulaId, itemName, formula, itemId);
	}

	public StaffFormulaDAO getStaffFormulaDao() {
		return staffFormulaDao;
	}

	public void setStaffFormulaDao(StaffFormulaDAO staffFormulaDao) {
		this.staffFormulaDao = staffFormulaDao;
	}
	
	
}
