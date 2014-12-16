package org.spes.dao.item;

import java.util.List;

import org.spes.bean.WindowFormula;

public interface WindowFormulaDAO {

	/**
	 * ��������ָ��Id��ȡָ����㹫ʽ
	 * 
	 * @param Id ָ��Id
	 * @return ��ʽ�б�
	 */
	public List<WindowFormula> getFormulaByItemId(Integer itemId);
	/**
	 * ���湫ʽ ����ָ��Id
	 * @param itemId ָ��Id
 	 * @return
	 */
	public boolean storeFormulaByItemId(String itemName,String formula,Integer itemId);
	
	/**
	 * ���¹�ʽ ����ָ��Id
	 * @param itemId ָ��Id
 	 * @return
	 */
	public boolean updateFormulaByItemId(Integer formulaId,String itemName,String formula,Integer itemId);

	/**
	 * �������Բ��Ҵ��ڹ�ʽ
	 *  hegang
	 * @param propertyName
	 * @param value
	 * @return
	 */
	public List findByProperty(String propertyName, Object value);
}