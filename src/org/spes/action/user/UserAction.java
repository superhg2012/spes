package org.spes.action.user;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.mail.MessagingException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;
import net.sf.json.util.PropertyFilter;

import org.apache.struts2.ServletActionContext;
import org.spes.bean.Role;
import org.spes.bean.User;
import org.spes.bean.Window;
import org.spes.service.mail.MailSernderService;
import org.spes.service.user.UserService;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

public class UserAction extends ActionSupport {

	private static final long serialVersionUID = 1L;
	private UserService userService;
	private MailSernderService mailService;
	private String email; // 用户邮箱
	private String role;  // 用户角色
	private String remarks; // 用户备注
	private int userId;
	private Integer centerId;
	private String userName; // 用户登入名
	private String userPass; // 用户密码
	private String idCardNum; // 用户身份证号
	private String realName; // 真实姓名
	private String contact; // 用户联系电话
	private String gender;  // 用户性别
	private String address; // 用户地址
	private Integer start; // 起始下标
	private Integer limit; // 页面显示条数
	private String centerName;

	public String getCenterName() {
		return centerName;
	}

	public void setCenterName(String centerName) {
		this.centerName = centerName;
	}

	public String getRealName() {
		return realName;
	}

	public void setRealName(String realName) {
		this.realName = realName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getContact() {
		return contact;
	}

	public void setContact(String contact) {
		this.contact = contact;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public Integer getStart() {
		return start;
	}

	public void setStart(Integer start) {
		this.start = start;
	}

	public Integer getLimit() {
		return limit;
	}

	public void setLimit(Integer limit) {
		this.limit = limit;
	}

	public Integer getCenterId() {
		return centerId;
	}

	public void setCenterId(Integer centerId) {
		this.centerId = centerId;
	}


	public void setUserService(UserService userService) {
		this.userService = userService;
	}

	public String getUserName() {
		return userName;
	}

	public String getUserPass() {
		return userPass;
	}

	public void setUserPass(String userPass) {
		this.userPass = userPass;
	}

	public String getIdCardNum() {
		return idCardNum;
	}

	public void setIdCardNum(String idCardNum) {
		this.idCardNum = idCardNum;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}
	
	public MailSernderService getMailService() {
		return mailService;
	}

	public void setMailService(MailSernderService mailService) {
		this.mailService = mailService;
	}

	/**
	 * add a user
	 * 
	 * @throws IOException
	 */
	public void addUser() throws IOException {
		HttpServletResponse response = ServletActionContext.getResponse();
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/json");
		JSONObject json = new JSONObject();
		int result = userService.save(userName, userPass, idCardNum, realName,
				email, contact, gender, address, remarks, centerName);
		if (result > 0) {
			json.put("success", true);
		} else {
			json.put("success", false);
		}
		json.write(response.getWriter());
		response.getWriter().flush();
	}
	
	/***
	 * 内部添加用户，不需要审查
	 * @throws IOException 
	 */
	public void addUser2() throws IOException{
		HttpServletResponse response = ServletActionContext.getResponse();
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/json");
		JSONObject json = new JSONObject();
		Map<String, Object> session = ActionContext.getContext().getSession();
		String centerId = (String)session.get("centerId");
		int result = userService.save2(userName, userPass, idCardNum, realName,
				email, contact, gender, address, remarks, Integer.valueOf(centerId));
		if (result > 0) {
			json.put("success", true);
		} else {
			json.put("success", false);
		}
		json.write(response.getWriter());
		response.getWriter().flush();
	}

	/**
	 * get users of some center
	 * 
	 * @throws IOException
	 */
	public void getUserList() throws IOException {
		HttpServletResponse response = ServletActionContext.getResponse();
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/json");

		List<User> ulist = userService.getUserOfCenter(centerId);
		if (ulist != null && ulist.size() > 0) {
			List<User> sublist = null;
			if ((limit + start) > ulist.size()) {
				sublist = ulist.subList(start, ulist.size());
			} else {
				sublist = ulist.subList(start, limit + start);
			}

			
			JSONArray jsonArray = new JSONArray();
			for (int i = 0; i < sublist.size(); i++) {
				User user = sublist.get(i);
				JSONObject json = new JSONObject();
				json.put("userId", user.getUserId());
				json.put("userName", user.getUserName());
				json.put("realName", user.getBackup2());
				json.put("gender", user.getGender());
				json.put("email", user.getEmail());
				json.put("IdCardNum", user.getIdCardNum());
				json.put("contact", user.getContact());
				json.put("centerName", user.getServiceCenter().getCenterName());
//				json.put("roleId", user.getRole().getRoleId());
//				json.put("roleName", user.getRole().getRoleName());
				jsonArray.add(json);
			}

			
			JSONObject json = new JSONObject();
			json.put("total", ulist.size());
			json.put("data", jsonArray.toString());
			response.getWriter().write(json.toString());
		} else {
			response.getWriter().write("尚没有用户信息！");
		}
		response.getWriter().flush();
	}

	
	public void getUserList2() throws IOException{
		HttpServletResponse response = ServletActionContext.getResponse();
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/json");

		List<User> ulist = userService.getUserOfCenter(centerId);//审核通过了的用户
		if (ulist != null && ulist.size() > 0) {
			List<User> sublist = null;
			if ((limit + start) > ulist.size()) {
				sublist = ulist.subList(start, ulist.size());
			} else {
				sublist = ulist.subList(start, limit + start);
			}
			JSONArray jsonArray = new JSONArray();
			for (int i = 0; i < sublist.size(); i++) {
				User user = sublist.get(i);
				JSONObject json = new JSONObject();
				json.put("userId", user.getUserId());
				json.put("userName", user.getUserName());
				json.put("realName", user.getBackup2());
				if (user.getRole() != null) {
					json.put("roleId", user.getRole().getRoleId());
					json.put("roleName", user.getRole().getRoleName());
				} else {
					json.put("roleId", "");
					json.put("roleName", "");
				}
				if (user.getWindow() != null) {
					json.put("windowId", user.getWindow().getWindowId());
					json.put("windowName", user.getWindow().getWindowName());
				} else {
					json.put("windowId", "");
					json.put("windowName", "");
				}
				jsonArray.add(json);
			}
			
			JSONObject json = new JSONObject();
			json.put("total", ulist.size());
			json.put("data", jsonArray.toString());
			response.getWriter().write(json.toString());
		} else {
			response.getWriter().write("尚没有用户信息！");
		}
		response.getWriter().flush();
	}
	
	/**
	 * 超级管理员角色分配获取用户
	 * @throws IOException
	 */
	public void getUserList3() throws IOException{
		HttpServletResponse response = ServletActionContext.getResponse();
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/json");

		List<User> ulist = userService.getUserOfCenter(centerId);//审核通过了的用户
		if (ulist != null && ulist.size() > 0) {
			List<User> sublist = null;
			if ((limit + start) > ulist.size()) {
				sublist = ulist.subList(start, ulist.size());
			} else {
				sublist = ulist.subList(start, limit + start);
			}
			JSONArray jsonArray = new JSONArray();
			for (int i = 0; i < sublist.size(); i++) {
				User user = sublist.get(i);
				JSONObject json = new JSONObject();
				json.put("userId", user.getUserId());
				json.put("userName", user.getUserName());
				json.put("realName", user.getBackup2());
				json.put("centerName", user.getServiceCenter().getCenterName());
				if (user.getRole() != null) {
					json.put("roleId", user.getRole().getRoleId());
					json.put("roleName", user.getRole().getRoleName());
				} else {
					json.put("roleId", "");
					json.put("roleName", "");
				}
				jsonArray.add(json);
			}
			JSONObject json = new JSONObject();
			json.put("total", ulist.size());
			json.put("data", jsonArray.toString());
			response.getWriter().write(json.toString());
		} else {
			response.getWriter().write("尚没有用户信息！");
		}
		response.getWriter().flush();
	}
	
	
	
	/**
	 * delete user
	 * 
	 * @throws IOException
	 */
	public void deleteUser() throws IOException {
		HttpServletResponse response = ServletActionContext.getResponse();
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html");
		HttpServletRequest request = ServletActionContext.getRequest();
		String userID = request.getParameter("userId");
		String result = userService.deleteUser(Integer.parseInt(userID));
		JSONObject json = new JSONObject();
		if (result.equals("failure")) {
			json.put("respText", "用户删除成功！");
		} else {
			json.put("respText", "用户删除失败！");
		}
		json.write(response.getWriter());
		response.getWriter().flush();
	}

	/**
	 * update properties of user
	 */
	public void upDateUser() {
		HttpServletResponse response = ServletActionContext.getResponse();
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/json");
		HttpServletRequest request = ServletActionContext.getRequest();
		String jsonData = request.getParameter("jsonData");
		JSONObject jsonObject = JSONObject.fromObject(jsonData);
		JSONArray jsonArray = jsonObject.getJSONArray("user");
		for (int i = 0; i < jsonArray.size(); i++) {
			JSONObject json = (JSONObject) jsonArray.get(i);
			Integer userId = json.getInt("userId");
			User user = userService.getUserById(userId);
			user.setGender(json.getString("gender"));
			user.setContact(json.getString("contact"));
			user.setIdCardNum(json.getString("IdCardNum"));
			user.setEmail(json.getString("email"));
			if (null != user) {
				userService.updateUser(user);
			}
		}
	}

	/**
	 * update role of user
	 * @throws IOException 
	 */
	public void upDateUserRole() throws IOException {
		HttpServletResponse response = ServletActionContext.getResponse();
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/json");
		HttpServletRequest request = ServletActionContext.getRequest();
		String jsonData = request.getParameter("jsonData");
		JSONObject jsonObject = JSONObject.fromObject(jsonData);
		JSONArray jsonArray = jsonObject.getJSONArray("user");
		for (int index = 0; index < jsonArray.size(); index++) {
			JSONObject json = (JSONObject) jsonArray.get(index);
			Integer userId = json.getInt("userId");
			Integer roleId = json.getInt("roleId");
			String roleName = json.getString("roleName");
			User user = userService.getUserById(userId);
			if (null != user) {
				Role role = new Role();
				role.setRoleId(roleId);
				role.setRoleName(roleName);
				user.setRole(role);
				userService.updateUser(user);
				JSONObject jsonobj = new JSONObject();
				jsonobj.put("success", true);
				response.getWriter().write(jsonobj.toString());
			}
		}
	}
	
	
	public void upDateUserWindow() throws IOException{
		HttpServletResponse response = ServletActionContext.getResponse();
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/json");
		HttpServletRequest request = ServletActionContext.getRequest();
		String jsonData = request.getParameter("jsonData");
		JSONObject jsonObject = JSONObject.fromObject(jsonData);
		JSONArray jsonArray = jsonObject.getJSONArray("userOfWindow");
		for (int index = 0; index < jsonArray.size(); index++) {
			JSONObject json = (JSONObject) jsonArray.get(index);
			Integer userId = json.getInt("userId");
			Integer windowId = json.getInt("windowId");
			String windowName = json.getString("windowName");
			User user = userService.getUserById(userId);
			if (null != user) {
				Window window = new Window();
				window.setWindowId(windowId);
				window.setWindowName(windowName);
				user.setWindow(window);
				userService.updateUser(user);
			}
		}
		JSONObject jsonobj = new JSONObject();
		jsonobj.put("success", true);
		response.getWriter().write(jsonobj.toString());
	}
	
	/**
	 * update valid state of user
	 * @throws IOException
	 */
	public void getUnValidUserOfCenter() throws IOException{
		HttpServletResponse response = ServletActionContext.getResponse();
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/json");
		Map<String, Object> session = ActionContext.getContext().getSession();
		String centerId = (String)session.get("centerId");
		List<User> ulist = userService.getUnValidUserOfCenter(Integer.valueOf(centerId));
		if (null != ulist && ulist.size() > 0) {
			JSONArray jsonArray = new JSONArray();
			List<User> sublist = null;
			if ((limit + start) > ulist.size()) {
				sublist = ulist.subList(start, ulist.size());
			} else {
				sublist = ulist.subList(start, limit + start);
			}
			
			for (int index = 0; index < sublist.size(); index++) {
				User user = ulist.get(index);
				JSONObject json = new JSONObject();
				json.put("userId", user.getUserId());
				json.put("userName", user.getUserName());
				json.put("realName", user.getBackup2());
				json.put("gender", user.getGender());
				json.put("email", user.getEmail());
				json.put("valid", user.getBackup3());
				jsonArray.add(json);
			}
			JSONObject json = new JSONObject();
			json.put("root",jsonArray.toString());
			json.put("total",jsonArray.size());
			response.getWriter().write(json.toString());
			response.getWriter().flush();
		}
	}
	
	/**
	 * update valid state of user
	 * @throws IOException
	 * @throws MessagingException 
	 */
	public void upDateUserValid() throws IOException, MessagingException{
		HttpServletResponse response = ServletActionContext.getResponse();
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/json");
		HttpServletRequest request = ServletActionContext.getRequest();
		String userId = request.getParameter("userId");
		User user = userService.getUserById(Integer.valueOf(userId));
		if (null != user) {
			user.setBackup3("1"); //update valid state
			userService.updateUser(user);
			JSONObject jsonobj = new JSONObject();
			jsonobj.put("success", true);
			response.getWriter().write(jsonobj.toString());
			String email = user.getEmail();
			if (null != email && !email.equals("")) {
				mailService.sendEmail(email, user.getUserName(), user.getBackup2(),user.getUserPass());
			}
		}
	}
	
	public void checkUserExist() throws IOException {
		HttpServletResponse response = ServletActionContext.getResponse();
		response.setContentType("text/json");
		response.setCharacterEncoding("UTF-8");
		HttpServletRequest request = ServletActionContext.getRequest();
		String username = request.getParameter("userName");
		User user = userService.findByUserName(username);
//		System.out.println("--" + username);
		if (null != user) {
			response.getWriter().write("true");
		} else {
			response.getWriter().write("false");
		}
	}
	
	public void LoadUserInfo() throws IOException{
		ActionContext context = ActionContext.getContext();
		Map session = context.getSession();
		Integer userId = Integer.valueOf(session.get("userId").toString());
		User user = userService.getUserById(userId);
		JsonConfig config = new JsonConfig();
		config.setJsonPropertyFilter(new PropertyFilter(){

			public boolean apply(Object arg0, String arg1, Object arg2) {
				// TODO Auto-generated method stub
				if(arg1.equals("users") || arg1.equals("windows") || arg1.equals("window") || arg1.equals("serviceCenter") || arg1.equals("role")){
					return true;
				}else{
					return false;
				}
			}});
		JSONObject json = JSONObject.fromObject(user,config);
		HttpServletResponse response = ServletActionContext.getResponse();
		response.setContentType("text/json");
		response.setCharacterEncoding("utf-8");
		json.write(response.getWriter());
	}
	
	public void MofigyUserInfo() throws IOException{
//		User user = new User();
		Map session = ActionContext.getContext().getSession();
		Integer userId = Integer.valueOf(session.get("userId").toString());
//		user.setUserId(userId);
//		user.setUserName(userName);
		
		User user = userService.findbyUserId(userId);
		if(!this.realName.equals("")){
			user.setBackup2(realName);
		}
		
		if(!this.userPass.equals("")){
			user.setUserPass(this.userPass);
		}
		if(!this.gender.equals("")){
			user.setGender(this.gender);
		}
		if(!this.email.equals("")){
			user.setEmail(this.email);
		}
		if(!this.address.equals("")){
			user.setAddress(this.address);
		}
		if(!this.contact.equals("")){
			user.setContact(this.contact);
		}
		if(!this.remarks.equals("")){
			user.setRemarks(this.remarks);
		}
//		String result = userService.mergeUser(user);
		if (null != user) {
			userService.updateUser(user);
		}
		user = null;
		HttpServletResponse resp = ServletActionContext.getResponse();
		resp.setContentType("text/html");
		resp.setCharacterEncoding("utf-8");
		resp.getWriter().write("success");
	}
}
