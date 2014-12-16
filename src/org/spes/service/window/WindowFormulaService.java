package org.spes.service.window;

import java.util.List;

import org.spes.bean.WindowFormula;

/**
 * 窗口指标公式业务接口
 * 
 * @author HeGang
 * 
 */
public interface WindowFormulaService {
	/**
	 * 根据中心指标Id获取指标计算公式
	 * 
	 * @param Id
	 *            指标Id
	 * @return 公式列表
	 */
	public List<WindowFormula> getFormulaByItemId(Integer itemId);

	/**
	 * 保存公式 依据指标Id
	 * 
	 * @param itemId
	 *            指标Id
	 * @return
	 */
	public boolean storeFormulaByItemId(String itemName, String formula,
			Integer itemId);

	/**
	 * 更新公式 依据指标Id
	 * 
	 * @param itemId
	 *            指标Id
	 * @return
	 */
	public boolean updateFormulaByItemId(Integer formulaId, String itemName,
			String formula, Integer itemId);

	/**
	 * 根据窗口指标ID，获取其评价公式
	 * hegang
	 * @param itemId
	 *            窗口指标ID
	 * @return 窗口指标公式列表
	 */
	public List<WindowFormula> getWindowFormulaByItemId(Integer itemId);

}
