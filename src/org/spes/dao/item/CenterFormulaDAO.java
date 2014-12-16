package org.spes.dao.item;

import java.util.List;

import org.spes.bean.CenterFormula;

public interface CenterFormulaDAO {

	/**
	 * 根据中心指标Id获取指标计算公式 
	 * hegang
	 * @param Id
	 *            指标Id
	 * @return 公式列表
	 */
	public List<CenterFormula> getFormulaByItemId(Integer itemId);

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
	 * 根据属性查找中心指标公式
	 * hegang
	 * @param propertyName
	 *            属性名
	 * @param value
	 *            属性值
	 * @return 中心指标列表
	 */
	public List findByProperty(String propertyName, Object value);
}