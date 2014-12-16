package org.spes.service.center;

import java.util.List;

import org.spes.bean.CenterFormula;

public interface CenterItemFormulaService {

	/**
	 * ��������ָ��Id��ȡָ����㹫ʽ
	 * 
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
}
