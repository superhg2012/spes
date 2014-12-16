package org.spes.dao.item;

import java.util.ArrayList;
import java.util.List;

import org.spes.bean.StaffItem;

public interface StaffItemDAO {

	public void save(StaffItem transientInstance);

	public void delete(StaffItem persistentInstance);

	public List findByProperty(String propertyName, Object value);

	public List findAll();

	/**
	 * ͨ������Id������ID���ԣ���ȡ������Ա����ָ��
	 * hegang
	 * @param windowId
	 *            ����ID
	 * @param centerId
	 *            ����ID
	 * @return ������ͨ��Ա����ָ���б�
	 */
	public List<StaffItem> findStaffItemByProperty(Integer windowId,
			Integer centerId);

	/**
	 * ͨ������Id������ID,ָ��ȼ����ԣ���ȡ������Ա����ָ��
	 * hegang
	 * @param windowId
	 * @param centerId
	 * @param itemGRade
	 * @return ������ͨ��Ա����ָ���б�
	 */
	public List<StaffItem> findStaffItemByProperty(Integer windowId,
			Integer centerId, Integer itemGrade);

	/**
	 * ͨ��ָ�����ƵȲ�����ȡ��Ա����ָ��
	 * hegang
	 * @param itemName
	 * @param windowId
	 * @param centerId
	 * @return
	 */
	public List<StaffItem> findStaffByItemName(String itemName,
			Integer windowId, Integer centerId);

	/**
	 * ͨ��ָ�����ƵȲ�����ȡ��Ա����ָ��
	 * hegang
	 * @param itemName
	 * @param parentId
	 * @param windowId
	 * @param centerId
	 * @return
	 */
	public List<StaffItem> findByItemNameAndIds(String itemName,
			Integer parentId, Integer windowId, Integer centerId);	
	

	/**
	 * ͨ��ָ��Id ����Id ����Id��ȡ��Աָ��
	 * hegang
	 * @param itemId
	 * @param windowId
	 * @param centerId
	 * @return
	 */
	public List<StaffItem> findStaffItemByIds(Integer itemId, Integer windowId,
			Integer centerId);
	
	// //////////////////////zhoushaojun start///////////////////////////////

	/**
	 * ��ѯ����ָ������
	 */
	public List<StaffItem> getAllParaData(int centerId, int windowId);

	/**
	 * ��ѯһ��ָ������������Ķ���ָ����Ϣ
	 */
	public List<StaffItem> getFirstLevelAndSecondLevelPara(int centerItemId,
			int centerId, int windowId);

	/**
	 * ��ѯһ��ָ�����ݣ�ͨ������Id
	 * 
	 */
	public StaffItem getFirstLevelPara(int staffItemId);

	/**
	 * ��ѯ��ǰһ��ָ�������ݿ����Ƿ���� True ������ false ����
	 */
	public boolean curParaInDatabase(String paraname, int centerId, int windowId);

	/**
	 * �ж� �����ݿ����Ƿ��������������ͬ��ָ�� True ������ false ����
	 */
	public boolean otherSameNameParaInDatabase(int centerItemId,
			String paraname, int centerId, int windowId);

	/**
	 * ��һ��ָ�����ݸ��£�����CenterItem���ݣ�����centerItemid()
	 */
	public boolean updateCenterItemById(int staffItemId, String paraname,
			double weight, boolean enable, String paraType);

	/**
	 * ������&����ָ�����ݸ��£�����CenterItem���ݣ�����centerItemʵ���� -1 ����ʧ�� -2 ����ָ���´���������ͬ��ָ������ 1
	 * ���³ɹ�
	 * 
	 */
	public int updateCenterItemByConcreteItem(StaffItem item);

	/**
	 * �洢һ��ָ�����ݣ��� ��-1 ����ɹ� -1 ����ʧ�� �洢һ��ָ������ ��-1 ����ɹ� -1 ����ʧ�ܡ� ���
	 * otherSameNameParaInDatabase ����������ݿ����Ƿ����ͬ��ָ��
	 */
	public int storeFirstLevelPara(StaffItem firstPara);

	/**
	 * �洢����&����ָ�� ����ʹ�ô˷��� -1 ����ʧ�� -2 ��ǰ����ָ���£�����������ͬ��ָ����޷����� 1 ����ɹ�
	 */
	public int storeSecondAndThirdPara(StaffItem item);

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
	public ArrayList<StaffItem> getCurSecondAndThridParameters(int firstItemId);

	/**
	 * ��ȡ����ָ�����������ָ������
	 */
	public List<StaffItem> getCurThirdParas(int itemId);
	// //////////////////////zhoushaojun end////////////////////////////////

}