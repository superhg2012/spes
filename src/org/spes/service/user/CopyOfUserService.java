package org.spes.service.user;

import java.sql.Timestamp;
import java.util.List;

import org.spes.bean.Role;
import org.spes.bean.User;

public interface CopyOfUserService {

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
	 * @return ok :保存成功；error： 保存失败；exist:已经存在该登录名了
	 */
	public String save(String userLogin, String userName, String userPass,
			String idCardNum, String email, String contact, Role gender,
			String address, Timestamp registtime, String remarks);

	public void delete(User user);

	public boolean isExistUsr(String userLogin);

	/**
	 * 通过用户名查找用户
	 * 
	 * @param userLogin
	 * @return 用户实例
	 */
	public User findUserByUserLogin(String userLogin);

	/**
	 * 判断用户登入是否成功
	 * 
	 * @param userLogin
	 *            用户输入的用户名
	 * @param userPsw
	 *            用户输入的密码
	 * @return true:登录成功，false：登入失败
	 */
	public Object isLogin(String userLogin, String userPass);

	public boolean isUpdate(String userLogin, String userName, String userPass,
			String idCardNum, String email, String contact, Role gender,
			String address, Timestamp registtime, String remarks);

	/**
	 * 根据用户Id查找用户
	 * 
	 * @param id
	 *            用户Id
	 * @return
	 */
	public User findbyUserId(int id);

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
	 * @param centerId 中心Id
	 * @return 用户列表
	 */
	public List<User> getUserOfCenter(Integer centerId);

}
