package org.spes.action.user;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts2.ServletActionContext;
import org.spes.bean.User;
import org.spes.service.user.UserService;

import com.opensymphony.xwork2.ActionSupport;

public class CopyOfUserAction extends ActionSupport{

	private static final long serialVersionUID = 1L;
	private UserService userService;
	private String userLogin; // �û�������
	private String userPsw; // �û�����
	private String email; // �û�����
	private String sex; // �û��Ա�
	private String role; // �û���ɫ
	private String remarks; // �û���ע
	private String tip;
	private int userId;
	private Integer centerId;
	
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

	private Integer start;// ��ʼ�±�
	private Integer limit; // ҳ����ʾ����

	public Integer getCenterId() {
		return centerId;
	}

	public void setCenterId(Integer centerId) {
		this.centerId = centerId;
	}

	private Log log4j = (Log) LogFactory.getLog(CopyOfUserAction.class);

	public void setUserService(UserService userService) {
		this.userService = userService;
	}

	public String getTip() {
		return tip;
	}

	/**
	 * @param tip
	 *            the tip to set
	 */
	public void setTip(String tip) {
		this.tip = tip;
	}

	public String getUserPsw() {
		return userPsw;
	}

	public void setUserPsw(String userPsw) {
		this.userPsw = userPsw;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
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

	public String getUserLogin() {
		return userLogin;
	}

	public void setUserLogin(String userLogin) {
		this.userLogin = userLogin;
	}

	/**
	 * �û���¼
	 * 
	 * @throws IOException
	 * 
	 */
	@SuppressWarnings("unchecked")
/*	public String userLogin() throws IOException {
		User user = (User) userService.isLogin(userLogin, userPsw);
		if (user != null) {
			// ��ʹ��Cookie����û���Ϣ��ӵ�Cookie��
			Map session = ActionContext.getContext().getSession();
			session.put("username", user.getUserName());
			session.put("userID", String.valueOf(user.getUserId()));
			session.put("role", user.getRole());
			session.put("userPsw", user.getUserPass());
			session.put("userLogin", user.getUserName());
			return SUCCESS;
		} else {
			this.setTip("�û������������������������!");
			return LOGIN;
		}
	}*/

	
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
			for (int i=0;i<sublist.size();i++) {
				User user = sublist.get(i);
				JSONObject json= new JSONObject();
				json.put("userId", user.getUserId());
				json.put("userName", user.getUserName());
				json.put("roleName", user.getRole().getRoleName());
				json.put("centerName", user.getServiceCenter().getCenterName());
				jsonArray.add(json);
			}
			
			JSONObject json = new JSONObject();
			json.put("total", ulist.size());
			json.put("data", jsonArray.toString());
			response.getWriter().write(json.toString());
		} else {
			response.getWriter().write("��û���û���Ϣ��");
		}
		response.getWriter().flush();
	}
}
