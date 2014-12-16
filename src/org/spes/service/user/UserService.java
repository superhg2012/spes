package org.spes.service.user;

import java.util.List;

import org.spes.bean.User;

public interface UserService {
	/**
	 * 查看用户是否存在
	 * 
	 * @param userName
	 * @param userPass
	 * @return 用户对象
	 */
	public User checkUser(String userName, String userPass);
	/**
	 * 修改用户密码
	 * 
	 * @param newpass
	 *            新密码
	 * @param userId
	 *            用户Id
	 * @return 修改标记
	 */
	public int modifyPassword(String newpass, Integer userId);

	/**
	 * 添加用户
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
	 * @return >=1 :保存成功；<1： 保存失败；
	 */
	public Integer save(String userName, String userPass, String idCardNum,
			String realName, String email, String contact, String gender,
			String address, String remarks,String centerName);
	
	/**
	 * 添加用户2
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
	 * 删除用户
	 * 
	 * @param userID
	 *            根据用户Id删除用户
	 * @return 删除标记
	 */
	public String deleteUser(Integer userID);

	/**
	 * 更新用户
	 * 
	 * @param user
	 */
	public void updateUser2(User user);
	/**
	 * 更新用户信息
	 * 
	 * @param persistentInstance
	 *            要更新的用户实例
	 */
	public boolean updateUser(User persistentInstance);

	public List<User> getUserList();

	/**
	 * 依据窗口Id与中心ID查找用户
	 * 
	 * @param windowId
	 *            窗口Id
	 * @param centerId
	 *            中心Id
	 * @return 用户列表
	 */
	public List<User> getUserOfWindow(Integer windowId, Integer centerId);

	/**
	 * 根据中心ID获取用户列表
	 * 
	 * @param centerId
	 *            中心Id
	 * @return 用户列表
	 */
	public List<User> getUserOfCenter(Integer centerId);

	/**
	 * 通过用户Id查找用户
	 * 
	 * @param userId
	 * @return 用户
	 */
	public User getUserById(Integer userId);
	
	/**
	 * 根据中心Id获取未经审核的用户
	 * @param centerId
	 * @return
	 */
	public List<User> getUnValidUserOfCenter(Integer centerId);
	/**
	 * 根据用户Id查找用户
	 * 
	 * @param id
	 *            用户Id
	 * @return
	 */
	public User findbyUserId(int id);
	
	/**
	 * 查找用户
	 * @param username
	 * @return
	 */
	public User findByUserName(String username);
	
	public String mergeUser(User user);

}
