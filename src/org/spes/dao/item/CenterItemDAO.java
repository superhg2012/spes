package org.spes.dao.item;

import java.util.ArrayList;
import java.util.List;

import org.spes.bean.CenterItem;

public interface CenterItemDAO {

	/**
	 * ��ѯ����ָ������
	 */
	public List<CenterItem> getAllParaData();

	/**
	 * ��ѯһ��ָ������������Ķ���ָ����Ϣ
	 */
	public List<CenterItem> getFirstLevelAndSecondLevelPara(int centerItemId);

	/**
	 * ��ѯһ��ָ�����ݣ�ͨ������Id
	 * 
	 */
	public CenterItem getFirstLevelPara(int centerItemId);

	/**
	 * ��ѯ��ǰһ��ָ�������ݿ����Ƿ���� True ������ false ����
	 */
	public boolean curParaInDatabase(String paraname);

	/**
	 * �ж� �����ݿ����Ƿ��������������ͬ��ָ�� True ������ false ����
	 */
	public boolean otherSameNameParaInDatabase(int centerItemId, String paraname);

	/**
	 * ��һ��ָ�����ݸ��£�����CenterItem���ݣ�����centerItemid()
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
	 * ɾ��һ��ָ����Ϣ(��������������ָ�� һ��ɾ��)
	 * 
	 */
	public boolean deleteFirstLevelPara(int itemId);

	/**
	 * ɾ������ָ����Ϣ����������ָ��һ��ɾ����
	 */
	public boolean deleteSecondLevelPara(int itemId);

	/**
	 * ɾ������ָ����Ϣ������ǰ����ָ����Ϣ��
	 * -1 ��ʾɾ������
	 * -2 ��ʾ�丸�����ָ�깫ʽ�� ���ڴ�ָ�꣬�޷�ɾ��
	 * 1 ��ʾɾ���ɹ�
	 */
	public int deleteCurParaInfo(int itemId);

	/**
	 * ��ȡ��ǰһ��ָ���µĶ���&����ָ����Ϣ�����ڶ���&����ָ����Ϣ�༭��
	 */
	public ArrayList<CenterItem> getCurSecondAndThridParameters(int firstItemId);
  /**
   * ��ȡ����ָ�����������ָ������
   */
	public List<CenterItem> getCurThirdParas(int itemId);
	/**
	 * ��������Id��ȡָ��
	 * 
	 * @param centerId
	 *            ����Id
	 * @return ����ָ���б�
	 */
	public List<CenterItem> findCenterItemById(Integer centerId);

	/**
	 * ����ָ������������Id��ȡ��������ָ��
	 * hegang
	 * @param itemName
	 *            ����ָ������
	 * @param centerId
	 *            ����Id
	 * @return ��������ָ���б�
	 */
	public List<CenterItem> findByItemNameAndCenterId(String itemName,
			Integer centerId);

	/**
	 * ��������ָ��������ָ��ID������Id��ȡ����ָ��
	 * 
	 * @param itemName
	 *            ����ָ����
	 * @param parentId
	 *            ��ָ��Id
	 * @param centerId
	 *            ����Id
	 * @return ָ���б�
	 */
	public List<CenterItem> findByItemNameAndItemIds(String itemName,
			Integer parentId, Integer centerId);
	
	/**
	 *��������Id��ָ��ȼ�����ָ��
	 * hgang
	 * @param centerId
	 *            ����Id
	 * @param itemGrade
	 *            ָ��ȼ�
	 * @return ����ָ���б�
	 */
	public List<CenterItem> findByCenterIdAndItemGrade(Integer centerId,
			Integer itemGrade);
	/**
	 * ����ָ�����Բ�������ָ�� 
	 * hegang
	 * @param propertyName
	 *            ������
	 * @param value
	 *            ����ֵ
	 * @return ����ָ���б�
	 */
	public List findByProperty(String propertyName, Object value);
	/**
	 * ����ָ��Id������Id����ָ��
	 * @param itemId
	 * @param centerId
	 * @return
	 */
	public List<CenterItem> findByItemIdAndCenterId(Integer itemId, Integer centerId);
}