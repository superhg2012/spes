package org.spes.dao.user;

import java.util.List;

import org.spes.bean.User;

public interface UserDAO {

	/**
	 * 添加用户
	 * 
	 * @param transientInstance
	 *            用户实例
	 */
	public Integer save(User transientInstance);
	/**
	 * 删除用户
	 * 
	 * @param persistentInstance
	 *            要删除的用户实例
	 */
	public void delete(User persistentInstance);
	
	/**
	 * 更新用户信息
	 * 
	 * @param persistentInstance
	 *            要更新的用户实例
	 */
	public boolean updateUser(User persistentInstance);

	/**
	 * 根据属性名查找用户
	 * 
	 * @param propertyName
	 *            属性名
	 * @param value
	 *            属性值
	 * @return 符合要求的用户列表
	 */
	@SuppressWarnings("unchecked")
	public List findByProperty(String propertyName, Object value);

	/**
	 * 返回所有用户列表
	 * 
	 * @return 用户列表
	 */
	
	@SuppressWarnings("unchecked")
	public List findAll();

	/**
	 * 获取用户实例
	 */
	public User findById(java.lang.Integer id);
	/**
	 * 通过用户名和密码查找用户
	 * 
	 * @param username用户名
	 * @param password密码
	 * @return 符合要求的用户实例
	 */
	public List<User> findByNameAndPass(String username, String password);

	/**
	 * 如果用户存在则更新，如果不存在则插入
	 * 
	 * @param user
	 * @return
	 */
	public User merge(User user);

	public List findByUserName(Object userName);

	/**
	 * 根据中心Id与窗口Id获取用户
	 * 
	 * @param windowId
	 *            窗口ID
	 * @param centerId
	 *            中心Id
	 * @param roleId
	 *            用户角色Id
	 * @return 用户列表
	 */
	public List<User> findUserByWindowId(Integer windowId, Integer centerId,
			Integer roleId);
	
	/**
	 * 更改
	 * @param password
	 * @param userId
	 * @return
	 */
	public int changeUserPwd(String password, Integer userId);
	/**
	 * 通过中心Id获取该中心的所有用户
	 * 
	 * @param centerId
	 *            中心Id
	 * @return 用户列表
	 */
	public List<User> findUserByCenterId(Integer centerId);

	/**
	 * 通过中心Id查找未经审核的用户
	 * 
	 * @param centerId
	 *            中心Id
	 * @return 用户列表
	 */
	public List<User> findUnValidUserOfCenter(Integer centerId);
	
	
	public void saveOrUpdate(User u);
	

}