package org.spes.service.user;

import java.sql.Timestamp;
import java.util.List;

import org.spes.bean.Role;
import org.spes.bean.User;

public interface CopyOfUserService {

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
	 * @param userLogin
	 * @param userName
	 * @param userPass
	 * @param idCardNum
	 * @param email
	 * @param contact
	 * @param gender
	 * @param role
	 * @param registertime
	 * @param remarks
	 * @return ok :����ɹ���error�� ����ʧ�ܣ�exist:�Ѿ����ڸõ�¼����
	 */
	public String save(String userLogin, String userName, String userPass,
			String idCardNum, String email, String contact, Role gender,
			String address, Timestamp registtime, String remarks);

	public void delete(User user);

	public boolean isExistUsr(String userLogin);

	/**
	 * ͨ���û��������û�
	 * 
	 * @param userLogin
	 * @return �û�ʵ��
	 */
	public User findUserByUserLogin(String userLogin);

	/**
	 * �ж��û������Ƿ�ɹ�
	 * 
	 * @param userLogin
	 *            �û�������û���
	 * @param userPsw
	 *            �û����������
	 * @return true:��¼�ɹ���false������ʧ��
	 */
	public Object isLogin(String userLogin, String userPass);

	public boolean isUpdate(String userLogin, String userName, String userPass,
			String idCardNum, String email, String contact, Role gender,
			String address, Timestamp registtime, String remarks);

	/**
	 * �����û�Id�����û�
	 * 
	 * @param id
	 *            �û�Id
	 * @return
	 */
	public User findbyUserId(int id);

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
	 * @param centerId ����Id
	 * @return �û��б�
	 */
	public List<User> getUserOfCenter(Integer centerId);

}
