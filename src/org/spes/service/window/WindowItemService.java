package org.spes.service.window;

import java.util.ArrayList;
import java.util.List;

import org.spes.bean.WindowItem;

/**
 * ����ָ��ҵ�񷽷�
 * 
 * @author HeGang
 * 
 */
public interface WindowItemService {
	/**
	 * ��ȡ����ָ������
	 */
	public List<WindowItem> getAllParameterData(int centerId);

	/**
	 * ��ѯһ��ָ������������Ķ���ָ����Ϣ
	 */
	public List<WindowItem> getFirstLevelAndSecondLevelPara(int centerItemId,int centerId);

	/**
	 * /** ��ȡһ��ָ�����ݣ�ͨ������Id
	 */
	public WindowItem getFirstLevelparameter(int centerId);

	/**
	 * ��ѯ��ǰһ��ָ�������ݿ����Ƿ����
	 */
	public boolean curParaIsInDatabase(String paraname,int centerId);

	/**
	 * �ж� �����ݿ����Ƿ��������������ͬ��ָ�� True ������ false ����
	 */
	public boolean otherSameNameParaInDatabase(int centerItemId, String paraname,int centerId);

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
	public int updateCenterItemByConcreteItem(WindowItem item);

	/**
	 * �洢һ��ָ�����ݣ��� ��-1 ����ɹ� -1 ����ʧ�� �洢һ��ָ������ ��-1 ����ɹ� -1 ����ʧ�ܡ�
	 * ��� otherSameNameParaInDatabase ����������ݿ����Ƿ����ͬ��ָ��
	 */
	public int storeFirstLevelPara(WindowItem firstPara);

	/**
	 * �洢����&����ָ�� ����ʹ�ô˷���
	 * -1 ����ʧ��
	 * -2 ��ǰ����ָ���£�����������ͬ��ָ����޷�����
	 * 1 ����ɹ�
	 */
	public int storeSecondAndThirdPara(WindowItem item);

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
	public ArrayList<WindowItem> getSecondAndThridParameters(int firstItemId);

	  /**
	   * ��ȡ����ָ�����������ָ������
	   */
	public List<WindowItem> getCurThirdParas(int itemId);

	/**
	 * ͨ��������ʽ��ȡ����ָ��
	 * 
	 * @param itemId
	 *            ����ָ��Id
	 * @param centerId
	 *            ����Id
	 * @return ����ָ���б�
	 */
	public List<WindowItem> getCenterItemsByFormula(Integer itemId,
			Integer centerId);

	public List<WindowItem> getWindowItems(Integer centerId);

	public List<WindowItem> getWindowItemsByFormula(Integer itemId,
			Integer centerId);
	
	public List<WindowItem> getWindowItemsOfFirstGrade(Integer centerId, Integer itemGrade);
	
	public List<WindowItem> getSonItemByParentId(Integer itemId, Integer centerId);
}
