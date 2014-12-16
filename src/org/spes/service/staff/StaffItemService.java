package org.spes.service.staff;

import java.util.ArrayList;
import java.util.List;

import org.spes.bean.StaffItem;

public interface StaffItemService {

	/**
	 * ���ݴ���Id������Id��ȡ������ͨ��Ա������ָ��
	 * 
	 * @param windowId
	 *            ����Id
	 * @param centerId
	 *            ����Id
	 * @return ������ͨ��Ա����ָ��
	 */
	public List<StaffItem> getStaffItemByWinIdAndCenId(Integer windowId,
			Integer centerId);

	/**
	 * ���ݴ���Id��ָ��Id������Id��ȡ���ڹ�����Ա��������ָ��
	 * 
	 * @param itemId
	 *            ָ��Id
	 * @param windowId
	 *            ����Id
	 * @param centerId
	 *            ����Id
	 * @return ���ڹ�����Ա����ָ��
	 */
	public List<StaffItem> getStaffItemByFormula(Integer itemId,
			Integer windowId, Integer centerId);
	//////////////////////zhoushaojun start////////////////////////
	/**
	 * ��ȡ����ָ������
	 */
	public List<StaffItem> getAllParameterData(int centerId,int windowId);

	/**
	 * ��ѯһ��ָ������������Ķ���ָ����Ϣ
	 */
	public List<StaffItem> getFirstLevelAndSecondLevelPara(int centerItemId,int centerId,int windowId);

	/**
	 * /** ��ȡһ��ָ�����ݣ�ͨ������Id
	 */
	public StaffItem getFirstLevelparameter(int staffItemId);

	/**
	 * ��ѯ��ǰһ��ָ�������ݿ����Ƿ����
	 */
	public boolean curParaIsInDatabase(String paraname,int centerId,int windowId);

	/**
	 * �ж� �����ݿ����Ƿ��������������ͬ��ָ�� True ������ false ����
	 */
	public boolean otherSameNameParaInDatabase(int centerItemId, String paraname,int centerId,int windowId);

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
	public int updateCenterItemByConcreteItem(StaffItem item);

	/**
	 * �洢һ��ָ�����ݣ��� ��-1 ����ɹ� -1 ����ʧ�� �洢һ��ָ������ ��-1 ����ɹ� -1 ����ʧ�ܡ�
	 * ��� otherSameNameParaInDatabase ����������ݿ����Ƿ����ͬ��ָ��
	 */
	public int storeFirstLevelPara(StaffItem firstPara);

	/**
	 * �洢����&����ָ�� ����ʹ�ô˷���
	 * -1 ����ʧ��
	 * -2 ��ǰ����ָ���£�����������ͬ��ָ����޷�����
	 * 1 ����ɹ�
	 */
	public int storeSecondAndThirdPara(StaffItem item);

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
	public ArrayList<StaffItem> getSecondAndThridParameters(int firstItemId);

	  /**
	   * ��ȡ����ָ�����������ָ������
	   */
	public List<StaffItem> getCurThirdParas(int itemId);
	
	//////////////////////zhoushaojun end/////////////////////////
	
	/**
	 * ������Ա��ָ��Id����ȡ��Աָ��
	 * 
	 * @param parentItemId
	 *            ��ָ��Id
	 * @return ���ڹ�����Ա����ָ��
	 */
	public List<StaffItem> getSonItemByParentId(Integer parentItemId);
	
	public List<StaffItem> getStaffItemByIdsAndItemGrade(Integer windowId, Integer centerId, Integer itemGrade );
}
