package org.spes.dao.user;

import java.util.List;

import org.spes.bean.User;

public interface UserDAO {

	/**
	 * ����û�
	 * 
	 * @param transientInstance
	 *            �û�ʵ��
	 */
	public Integer save(User transientInstance);
	/**
	 * ɾ���û�
	 * 
	 * @param persistentInstance
	 *            Ҫɾ�����û�ʵ��
	 */
	public void delete(User persistentInstance);
	
	/**
	 * �����û���Ϣ
	 * 
	 * @param persistentInstance
	 *            Ҫ���µ��û�ʵ��
	 */
	public boolean updateUser(User persistentInstance);

	/**
	 * ���������������û�
	 * 
	 * @param propertyName
	 *            ������
	 * @param value
	 *            ����ֵ
	 * @return ����Ҫ����û��б�
	 */
	@SuppressWarnings("unchecked")
	public List findByProperty(String propertyName, Object value);

	/**
	 * ���������û��б�
	 * 
	 * @return �û��б�
	 */
	
	@SuppressWarnings("unchecked")
	public List findAll();

	/**
	 * ��ȡ�û�ʵ��
	 */
	public User findById(java.lang.Integer id);
	/**
	 * ͨ���û�������������û�
	 * 
	 * @param username�û���
	 * @param password����
	 * @return ����Ҫ����û�ʵ��
	 */
	public List<User> findByNameAndPass(String username, String password);

	/**
	 * ����û���������£���������������
	 * 
	 * @param user
	 * @return
	 */
	public User merge(User user);

	public List findByUserName(Object userName);

	/**
	 * ��������Id�봰��Id��ȡ�û�
	 * 
	 * @param windowId
	 *            ����ID
	 * @param centerId
	 *            ����Id
	 * @param roleId
	 *            �û���ɫId
	 * @return �û��б�
	 */
	public List<User> findUserByWindowId(Integer windowId, Integer centerId,
			Integer roleId);
	
	/**
	 * ����
	 * @param password
	 * @param userId
	 * @return
	 */
	public int changeUserPwd(String password, Integer userId);
	/**
	 * ͨ������Id��ȡ�����ĵ������û�
	 * 
	 * @param centerId
	 *            ����Id
	 * @return �û��б�
	 */
	public List<User> findUserByCenterId(Integer centerId);

	/**
	 * ͨ������Id����δ����˵��û�
	 * 
	 * @param centerId
	 *            ����Id
	 * @return �û��б�
	 */
	public List<User> findUnValidUserOfCenter(Integer centerId);
	
	
	public void saveOrUpdate(User u);
	

}