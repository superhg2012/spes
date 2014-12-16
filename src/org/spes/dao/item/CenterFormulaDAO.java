package org.spes.dao.item;

import java.util.List;

import org.spes.bean.CenterFormula;

public interface CenterFormulaDAO {

	/**
	 * ��������ָ��Id��ȡָ����㹫ʽ 
	 * hegang
	 * @param Id
	 *            ָ��Id
	 * @return ��ʽ�б�
	 */
	public List<CenterFormula> getFormulaByItemId(Integer itemId);

	/**
	 * ���湫ʽ ����ָ��Id
	 * 
	 * @param itemId
	 *            ָ��Id
	 * @return
	 */
	public boolean storeFormulaByItemId(String itemName, String formula,
			Integer itemId);

	/**
	 * ���¹�ʽ ����ָ��Id
	 * 
	 * @param itemId
	 *            ָ��Id
	 * @return
	 */
	public boolean updateFormulaByItemId(Integer formulaId, String itemName,
			String formula, Integer itemId);

	/**
	 * �������Բ�������ָ�깫ʽ
	 * hegang
	 * @param propertyName
	 *            ������
	 * @param value
	 *            ����ֵ
	 * @return ����ָ���б�
	 */
	public List findByProperty(String propertyName, Object value);
}