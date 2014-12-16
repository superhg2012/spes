package org.spes.service.user;

import java.util.List;

import org.spes.bean.User;

public interface UserService {
	/**
	 * �鿴�û��Ƿ����
	 * 
	 * @param userName
	 * @param userPass
	 * @return �û�����
	 */
	public User checkUser(String userName, String userPass);
	/**
	 * �޸��û�����
	 * 
	 * @param newpass
	 *            ������
	 * @param userId
	 *            �û�Id
	 * @return �޸ı��
	 */
	public int modifyPassword(String newpass, Integer userId);

	/**
	 * ����û�
	 * 
	 * @param userName
	 * @param userPass
	 * @param idCardNum
	 * @param realName
	 * @param email
	 * @param contact
	 * @param gender
	 * @param address
	 * @param remarks
	 * @return >=1 :����ɹ���<1�� ����ʧ�ܣ�
	 */
	public Integer save(String userName, String userPass, String idCardNum,
			String realName, String email, String contact, String gender,
			String address, String remarks,String centerName);
	
	/**
	 * ����û�2
	 * @param userName
	 * @param userPass
	 * @param idCardNum
	 * @param realName
	 * @param email
	 * @param contact
	 * @param gender
	 * @param address
	 * @param remarks
	 * @param centerName
	 * @return
	 */
	public Integer save2(String userName, String userPass, String idCardNum,
			String realName, String email, String contact, String gender,
			String address, String remarks,Integer centerId);

	/**
	 * ɾ���û�
	 * 
	 * @param userID
	 *            �����û�Idɾ���û�
	 * @return ɾ�����
	 */
	public String deleteUser(Integer userID);

	/**
	 * �����û�
	 * 
	 * @param user
	 */
	public void updateUser2(User user);
	/**
	 * �����û���Ϣ
	 * 
	 * @param persistentInstance
	 *            Ҫ���µ��û�ʵ��
	 */
	public boolean updateUser(User persistentInstance);

	public List<User> getUserList();

	/**
	 * ���ݴ���Id������ID�����û�
	 * 
	 * @param windowId
	 *            ����Id
	 * @param centerId
	 *            ����Id
	 * @return �û��б�
	 */
	public List<User> getUserOfWindow(Integer windowId, Integer centerId);

	/**
	 * ��������ID��ȡ�û��б�
	 * 
	 * @param centerId
	 *            ����Id
	 * @return �û��б�
	 */
	public List<User> getUserOfCenter(Integer centerId);

	/**
	 * ͨ���û�Id�����û�
	 * 
	 * @param userId
	 * @return �û�
	 */
	public User getUserById(Integer userId);
	
	/**
	 * ��������Id��ȡδ����˵��û�
	 * @param centerId
	 * @return
	 */
	public List<User> getUnValidUserOfCenter(Integer centerId);
	/**
	 * �����û�Id�����û�
	 * 
	 * @param id
	 *            �û�Id
	 * @return
	 */
	public User findbyUserId(int id);
	
	/**
	 * �����û�
	 * @param username
	 * @return
	 */
	public User findByUserName(String username);
	
	public String mergeUser(User user);

}
