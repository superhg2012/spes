package org.spes.service.staff;

import java.util.List;

import org.spes.bean.StaffFormula;

public interface StaffFormulaService {
	/**
	 * 根据中心指标Id获取指标计算公式
	 * 
	 * @param Id 指标Id
	 * @return 公式列表
	 */
	public List<StaffFormula> getFormulaByItemId(Integer itemId);
	
	/**
	 * 保存公式 依据指标Id
	 * @param itemId 指标Id
 	 * @return
	 */
	public boolean storeFormulaByItemId(String itemName,String formula,Integer itemId);
	
	/**
	 * 更新公式 依据指标Id
	 * @param itemId 指标Id
 	 * @return
	 */
	public boolean updateFormulaByItemId(Integer formulaId,String itemName,String formula,Integer itemId);

}
