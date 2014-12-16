package org.spes.service.window;

import java.util.List;

import org.spes.bean.WindowFormula;

/**
 * ����ָ�깫ʽҵ��ӿ�
 * 
 * @author HeGang
 * 
 */
public interface WindowFormulaService {
	/**
	 * ��������ָ��Id��ȡָ����㹫ʽ
	 * 
	 * @param Id
	 *            ָ��Id
	 * @return ��ʽ�б�
	 */
	public List<WindowFormula> getFormulaByItemId(Integer itemId);

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
	 * ���ݴ���ָ��ID����ȡ�����۹�ʽ
	 * hegang
	 * @param itemId
	 *            ����ָ��ID
	 * @return ����ָ�깫ʽ�б�
	 */
	public List<WindowFormula> getWindowFormulaByItemId(Integer itemId);

}
