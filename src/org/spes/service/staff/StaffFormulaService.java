package org.spes.service.staff;

import java.util.List;

import org.spes.bean.StaffFormula;

public interface StaffFormulaService {
	/**
	 * ��������ָ��Id��ȡָ����㹫ʽ
	 * 
	 * @param Id ָ��Id
	 * @return ��ʽ�б�
	 */
	public List<StaffFormula> getFormulaByItemId(Integer itemId);
	
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

}
