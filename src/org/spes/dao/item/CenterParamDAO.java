package org.spes.dao.item;

import java.util.List;

import org.spes.bean.CenterParam;

public interface CenterParamDAO {

	public void save(CenterParam transientInstance);

	public void delete(CenterParam persistentInstance);

	public List findByProperty(String propertyName, Object value);

	public List findAll();

	/**
	 * ����ָ��Id������Id��ȡ����ָ�����
	 * 
	 * @param itemId
	 *            ָ��Id
	 * 
	 * @param centerId
	 *            ָ������Id
	 * @return ����ָ��÷ֶ���
	 */
	public CenterParam findByCenterIdAndItemId(Integer itemId, Integer centerId);

	/**
	 * @param itemId
	 * @param centerId
	 * @param sheetId
	 * @return
	 */
	public CenterParam findThirdLevelParamByIds(Integer itemId, Integer centerId, Integer sheetId);
	
	
	/**
	 * ����ָ������������ID��ȡ����ָ�����
	 * 
	 * @param itemName
	 *            ָ��Id
	 * @param centerId
	 *            ָ������Id
	 * @return ��������ָ������б�
	 */
	public List<CenterParam> findByItemNameAndCenterId(String itemName,
			Integer centerId);

	public CenterParam findCenterParamByParams(Integer itemId,
			Integer centerId, String checkType, String sheetName, String userName);
}