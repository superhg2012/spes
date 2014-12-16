package org.spes.service.center;

import java.util.ArrayList;
import java.util.List;

import org.spes.bean.CenterItem;

public interface CenterItemService {
	
	
	public List<CenterItem> getAllParameterOfCenter(int centerId);
	/**
	 * ��ȡ����ָ������
	 */
	public List<CenterItem> getAllParameterData();

	/**
	 * ��ѯһ��ָ������������Ķ���ָ����Ϣ
	 */
	public List<CenterItem> getFirstLevelAndSecondLevelPara(int centerItemId);

	/**
	 * /** ��ȡһ��ָ�����ݣ�ͨ������Id
	 */
	public CenterItem getFirstLevelparameter(int centerId);

	/**
	 * ��ѯ��ǰһ��ָ�������ݿ����Ƿ����
	 */
	public boolean curParaIsInDatabase(String paraname);

	/**
	 * �ж� �����ݿ����Ƿ��������������ͬ��ָ�� True ������ false ����
	 */
	public boolean otherSameNameParaInDatabase(int centerItemId, String paraname);

	/**
	 * (һ��ָ�����)����CenterItem���ݣ�����centerItemid
	 */
	public boolean updateCenterItemById(int centerItemId, String paraname,
			double weight, boolean enable, String paraType);

	/**
	 * ������&����ָ�����ݸ��£�����CenterItem���ݣ�����centerItemʵ����
	 *  -1 ����ʧ��
	 *  -2 ����ָ���´���������ͬ��ָ������
	 *  1 ���³ɹ�
	 *  
	 */
	public int updateCenterItemByConcreteItem(CenterItem item);

	/**
	 * �洢һ��ָ�����ݣ��� ��-1 ����ɹ� -1 ����ʧ�� �洢һ��ָ������ ��-1 ����ɹ� -1 ����ʧ�ܡ�
	 * ��� otherSameNameParaInDatabase ����������ݿ����Ƿ����ͬ��ָ��
	 */
	public int storeFirstLevelPara(CenterItem firstPara);

	/**
	 * �洢����&����ָ�� ����ʹ�ô˷���
	 * -1 ����ʧ��
	 * -2 ��ǰ����ָ���£�����������ͬ��ָ����޷�����
	 * 1 ����ɹ�
	 */
	public int storeSecondAndThirdPara(CenterItem item);

	/**
	 * ɾ��һ��ָ�꣬��������������ָ��һ��ɾ��
	 */
	public boolean deleteAllFirstLevelPara(int centerId);

	/**
	 * ɾ������ָ����Ϣ����������ָ��һ��ɾ����
	 */
	public boolean deleteSecondLevelPara(int itemId);
	/**
	 * ɾ������ָ����Ϣ������ǰ����ָ����Ϣ��
	 *  -1 ��ʾɾ������
	 * -2 ��ʾ�丸�����ָ�깫ʽ�� ���ڴ�ָ�꣬�޷�ɾ��
	 * 1 ��ʾɾ���ɹ�
	 */
	public int deleteCurParaInfo(int itemId);
	/**
	 * ��ȡ��ǰһ��ָ���µĶ���&����ָ����Ϣ�����ڶ���&����ָ����Ϣ�༭��
	 */
	public ArrayList<CenterItem> getSecondAndThridParameters(int firstItemId);

	  /**
	   * ��ȡ����ָ�����������ָ������
	   */
	public List<CenterItem> getCurThirdParas(int itemId);

	/**
	 * ��������Id��ȡ����ָ��
	 * 
	 * @param centerId
	 *            ����ID
	 * @return �����б�
	 */
	public List<CenterItem> getCenterItemById(Integer centerId);

	/**
	 * ��������Id������ָ��ȼ���ȡ����ָ��
	 * 
	 * @param centerId
	 *            ����Id
	 * @param itemGrade
	 *            ����ָ��ȼ�
	 * @return
	 */
	public List<CenterItem> getItemByItemGradeAndId(Integer centerId,
			Integer itemGrade);

	/**
	 * ͨ��������ʽ��ȡ����ָ��
	 * 
	 * @param itemId
	 *            ����ָ��Id
	 * @param centerId
	 *            ����Id
	 * @return ����ָ���б�
	 */
	public List<CenterItem> getCenterItemsByFormula(Integer itemId,
			Integer centerId);
	
	public List<CenterItem> getSonItemByParentId(Integer parentItemId);
}
