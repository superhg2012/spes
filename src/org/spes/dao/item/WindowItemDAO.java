package org.spes.dao.item;

import java.util.ArrayList;
import java.util.List;

import org.spes.bean.WindowItem;

public interface WindowItemDAO {

	/**
	 * ��ѯ����ָ������
	 */
	public List<WindowItem> getAllParaData(int centerId);

	/**
	 * ��ѯһ��ָ������������Ķ���ָ����Ϣ
	 */
	public List<WindowItem> getFirstLevelAndSecondLevelPara(int centerItemId,
			int centerId);

	/**
	 * ��ѯһ��ָ�����ݣ�ͨ������Id
	 * 
	 */
	public WindowItem getFirstLevelPara(int centerItemId);

	/**
	 * ��ѯ��ǰһ��ָ�������ݿ����Ƿ���� True ������ false ����
	 */
	public boolean curParaInDatabase(String paraname, int centerId);

	/**
	 * �ж� �����ݿ����Ƿ��������������ͬ��ָ�� True ������ false ����
	 */
	public boolean otherSameNameParaInDatabase(int centerItemId,
			String paraname, int centerId);

	/**
	 * ��һ��ָ�����ݸ��£�����CenterItem���ݣ�����centerItemid()
	 */
	public boolean updateCenterItemById(int centerItemId, String paraname,
			double weight, boolean enable, String paraType);

	/**
	 * ������&����ָ�����ݸ��£�����CenterItem���ݣ�����centerItemʵ���� -1 ����ʧ�� -2 ����ָ���´���������ͬ��ָ������ 1
	 * ���³ɹ�
	 * 
	 */
	public int updateCenterItemByConcreteItem(WindowItem item);

	/**
	 * �洢һ��ָ�����ݣ��� ��-1 ����ɹ� -1 ����ʧ�� �洢һ��ָ������ ��-1 ����ɹ� -1 ����ʧ�ܡ� ���
	 * otherSameNameParaInDatabase ����������ݿ����Ƿ����ͬ��ָ��
	 */
	public int storeFirstLevelPara(WindowItem firstPara);

	/**
	 * �洢����&����ָ�� ����ʹ�ô˷��� -1 ����ʧ�� -2 ��ǰ����ָ���£�����������ͬ��ָ����޷����� 1 ����ɹ�
	 */
	public int storeSecondAndThirdPara(WindowItem item);

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
	 * ɾ������ָ����Ϣ������ǰ����ָ����Ϣ�� -1 ��ʾɾ������ -2 ��ʾ�丸�����ָ�깫ʽ�� ���ڴ�ָ�꣬�޷�ɾ�� 1 ��ʾɾ���ɹ�
	 */
	public int deleteCurParaInfo(int itemId);

	/**
	 * ��ȡ��ǰһ��ָ���µĶ���&����ָ����Ϣ�����ڶ���&����ָ����Ϣ�༭��
	 */
	public ArrayList<WindowItem> getCurSecondAndThridParameters(int firstItemId);

	/**
	 * ��ȡ����ָ�����������ָ������
	 */
	public List<WindowItem> getCurThirdParas(int itemId);


	/**
	 * ����ָ�������봰��Id��ȡ��������ָ��
	 * 
	 * @param itemName
	 *            ����ָ������
	 * @param centerId
	 *            ����Id
	 * @return ��������ָ���б�
	 */
	public List<WindowItem> findByItemNameAndCenterId(String itemName,
			Integer centerId);

	/**
	 * ����ָ�����ƣ���ָ��Id������Id��ȡָ��
	 * 
	 * @param itemName
	 *            ����ָ������
	 * @param parentId
	 *            ��ָ��Id
	 * @param centerId
	 *            ����Id
	 * @return
	 */
	public List<WindowItem> findByItemNameAndIds(String itemName,
			Integer parentId, Integer centerId);

	public List<WindowItem> findByItemGradeAndCenterId(Integer centerId,
			Integer itemGrade);

	public List findByProperty(String propertyName, Object value);

	public List<WindowItem> fingByParentIdAndCenterId(Integer parentId,
			Integer centerId);
	
	public List<WindowItem> findByItemIdAndCenterId(Integer itemId, Integer centerId);
}