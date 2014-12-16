package org.spes.service.user.impl;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import org.spes.bean.Role;
import org.spes.bean.ServiceCenter;
import org.spes.bean.User;
import org.spes.dao.common.RoleDAO;
import org.spes.dao.common.ServiceCenterDAO;
import org.spes.dao.user.UserDAO;
import org.spes.service.user.UserService;

public class UserServiceImpl implements UserService {

	private UserDAO userDao = null; // UserDAO
	private RoleDAO roleDao = null; // RoleDAO
	private ServiceCenterDAO centerDao = null;

	public ServiceCenterDAO getCenterDao() {
		return centerDao;
	}

	public void setCenterDao(ServiceCenterDAO centerDao) {
		this.centerDao = centerDao;
	}

	public void setUserDao(UserDAO userDao) {
		this.userDao = userDao;
	}

	public void setRoleDao(RoleDAO roleDao) {
		this.roleDao = roleDao;
	}

	public User checkUser(String userName, String userPass) {
		List<User> ulist = userDao.findByNameAndPass(userName, userPass);
		if (null != ulist && ulist.size() > 0) {
			return ulist.get(0);
		}
		return null;
	}

	public String deleteUser(Integer userId) {
		List<User> ulist = userDao.findByProperty("userId", userId);
		// User user = userDao.findbyUserId(userId);
		User user = ulist.get(0);
		String isOk = "";
		if (null != user) {
			userDao.delete(user);
			isOk = "success";
		} else {
			isOk = "failure";
		}
		return isOk;
	}

	/*
	 * 按照用户登录名查找用户信息
	 * 
	 * @retrun 该用户的所有信息
	 */
	@SuppressWarnings("unchecked")
	public User findUserByUserLogin(String userLogin) {
		List user = userDao.findByProperty("userLogin", userLogin);
		return (user.size() == 1) ? ((User) user.get(0)) : (null);
	}

	public boolean isUpdate(String userName, String userPass, String idCardNum,String realName,
			String email, String contact, String gender, String address,String remarks) {
		User user = (User) userDao.findByProperty("userName", userName);
		user.setUserName(userName);
		user.setUserPass(userPass);
		user.setIdCardNum(idCardNum);
		user.setContact(contact);
		user.setEmail(email);
		user.setRemarks(remarks);
		return (userDao.merge(user) == null) ? (false) : (true);

	}

	public Integer save(String userName, String userPass, String idCardNum,String realName,
			String email, String contact, String gender, String address,String remarks,String centerName) {
		Integer result = -1;
		List<User> list = userDao.findByProperty("userName", userName);
		List<ServiceCenter> centerlist = centerDao.findByProperty("centerName", centerName);
		ServiceCenter center = null;
		if(centerlist!=null && centerlist.size()>0){
			 center = centerlist.get(0);	
		}
		
		if (list.size() == 0) {
			// 获取用户注册的时间，即当前时间。
			Date now = new Date();
			User user = new User();
			user.setUserName(userName);
			user.setUserPass(userPass);
			user.setIdCardNum(idCardNum);
			user.setBackup2(realName);//用户真实姓名
			user.setContact(contact);
			user.setGender(gender);
			user.setEmail(email);
			user.setRemarks(remarks);
			user.setBackup3("0");//未审核
			user.setRegisttime(new Timestamp(now.getTime()));
			user.setServiceCenter(center);
			
			Role role = new Role();
			role.setRoleId(new Integer(5));
			role.setRoleName("窗口普通人员");
			user.setRole(role);
			result = userDao.save(user);
		}
		return result;
	}

	public int modifyPassword(String newpass, Integer userId) {
		return userDao.changeUserPwd(newpass, userId);
	}

	public List<User> getUserOfWindow(Integer windowId, Integer centerId) {
		Role role = (Role) roleDao.findByProperty("roleName", "窗口普通人员").get(0);
		return userDao.findUserByWindowId(windowId, centerId, role.getRoleId());
	}

	public List<User> getUserOfCenter(Integer centerId) {
//		System.out.println(userDao.findUserByCenterId(centerId));
		return userDao.findUserByCenterId(centerId);
	}

	public boolean isUpdate(String userName, String userPass, String idCardNum,
			String email, String contact, Role gender, String address,
			Timestamp registtime, String remarks) {
		return false;
	}

	public List<User> getUserList() {
		return userDao.findAll();
	}

	public void updateUser2(User user) {
		userDao.merge(user);
	}

	public User getUserById(Integer userId) {
		return (User) userDao.findByProperty("userId", userId).get(0);
	}

	public List<User> getUnValidUserOfCenter(Integer centerId) {
		return userDao.findUnValidUserOfCenter(centerId);
	}
	
	public boolean updateUser(User persistentInstance) {
		return userDao.updateUser(persistentInstance);
	}

	public User findbyUserId(int id) {
		return userDao.findById(id);
	}

	public User findByUserName(String username) {
		List<User> ulist = userDao.findByUserName(username);
		if (null != ulist && ulist.size() > 0) {
			return ulist.get(0);
		}
		return null;
	}

	public Integer save2(String userName, String userPass, String idCardNum,
			String realName, String email, String contact, String gender,
			String address, String remarks, Integer centerId) {
		Integer result = -1;
		List<User> list = userDao.findByProperty("userName", userName);
		List<ServiceCenter> centerlist = centerDao.findByProperty("centerId", centerId);
		ServiceCenter center = null;
		if(centerlist!=null && centerlist.size()>0){
			 center = centerlist.get(0);	
		}
		
		if (list.size() == 0) {
			// 获取用户注册的时间，即当前时间。
			Date now = new Date();
			User user = new User();
			user.setUserName(userName);
			user.setUserPass(userPass);
			user.setIdCardNum(idCardNum);
			user.setBackup2(realName);//用户真实姓名
			user.setContact(contact);
			user.setGender(gender);
			user.setEmail(email);
			user.setRemarks(remarks);
			user.setBackup3("1");//未审核
			user.setRegisttime(new Timestamp(now.getTime()));
			user.setServiceCenter(center);
			
			Role role = new Role();
			role.setRoleId(new Integer(5));
			role.setRoleName("窗口普通人员");
			user.setRole(role);
			result = userDao.save(user);
		}
		return result;
	}

	public String mergeUser(User user) {
		// TODO Auto-generated method stub
		User user1 ;
		String result = "success";
		try {
			user1=  userDao.merge(user);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
			result = "faile";
		}
		return result;
	}
}
