package org.spes.action.user;

import java.io.IOException;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.apache.struts2.ServletActionContext;
import org.spes.service.user.UserService;

import com.opensymphony.xwork2.ActionContext;

public class PwdAction {

	private static final long serialVersionUID = 1440184197594089074L;
	private String newPass;
	private UserService userService = null;

	public String getNewPass() {
		return newPass;
	}

	public void setNewPass(String newPass) {
		this.newPass = newPass;
	}

	public UserService getUserService() {
		return userService;
	}

	public void setUserService(UserService userService) {
		this.userService = userService;
	}

	public void changePassword() throws IOException {
		HttpServletResponse response = ServletActionContext.getResponse();
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html");

		Map<String, Object> session = ActionContext.getContext().getSession();
		Object userid = session.get("userId");
		if(userid == null){
			response.getWriter().write("Please Log in first!");
			return;
		}
		Integer userId = (Integer) userid;
		int result = userService.modifyPassword(newPass, userId);
		JSONObject json = new JSONObject();
		if (result > 0) {
			json.put("resp", "�����޸ĳɹ���ת���½����...");
			json.put("success", true);
		} else {
			json.put("resp", "�����޸�ʧ�ܣ�");
			json.put("success", false);
		}
		response.getWriter().write(json.toString());
		response.getWriter().flush();
	}
}
