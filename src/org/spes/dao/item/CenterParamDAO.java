package org.spes.dao.item;

import java.util.List;

import org.spes.bean.CenterParam;

public interface CenterParamDAO {

	public void save(CenterParam transientInstance);

	public void delete(CenterParam persistentInstance);

	public List findByProperty(String propertyName, Object value);

	public List findAll();

	/**
	 * 依据指标Id与中心Id获取三级指标参数
	 * 
	 * @param itemId
	 *            指标Id
	 * 
	 * @param centerId
	 *            指标中心Id
	 * @return 三级指标得分对象
	 */
	public CenterParam findByCenterIdAndItemId(Integer itemId, Integer centerId);

	/**
	 * 依据指标名称与中心ID获取三级指标参数
	 * 
	 * @param itemName
	 *            指标Id
	 * @param centerId
	 *            指标中心Id
	 * @return 中心三级指标对象列表
	 */
	public List<CenterParam> findByItemNameAndCenterId(String itemName,
			Integer centerId);

	public CenterParam findCenterParamByParams(Integer itemId,
			Integer centerId, String checkType, String sheetName, String userName);
}