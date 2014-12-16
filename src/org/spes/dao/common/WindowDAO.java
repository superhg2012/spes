package org.spes.dao.common;

import java.util.List;

import org.spes.bean.Window;

public interface WindowDAO {

	public void save(Window transientInstance);

	public void delete(Window persistentInstance);

	public List findByProperty(String propertyName, Object value);

	public List findAll();
	
	public boolean updateWindow(Window window);

	/**
	 * ͨ���û��������ҿ��Բ鿴�Ĵ���
	 * 
	 * @param userId
	 *            �û�ID
	 * @return ����List
	 */
	public List findByUserId(Integer userId);

	/**
	 * ͨ������Id���������ڸ����ĵĴ���
	 * 
	 * @param centerId
	 *            ����Id
	 * @return �����б�
	 */
	public List<Window> findByCenterId(Integer centerId);

	// /////////////////////zhoushaojun start/////////////////////
	/**
	 * ͨ��center������centerId���в�������
	 * 
	 * @param centerId
	 * @return
	 */
	public Window findWindowById(int centerId);

	/**
	 * ͨ�����ĵ����Ʋ�ѯ���ĵ�Id����֤����Ϊ1�� -1 ��ʾ���ʧ�� ���� ��ʾ����ɹ�
	 */
	public int findWindowIdByName(String name, int centerId);

	// /////////////////////zhoushaojun end/////////////////////

}