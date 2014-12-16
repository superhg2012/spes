package org.spes.action.login;

import java.io.IOException;
import java.util.Map;

import org.spes.bean.User;
import org.spes.service.user.UserService;

import com.alcatel.omc.fwk.utilities.trace.TraceManager;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

/**
 * 用户登陆Action
 * @author HeGang
 */
public class LoginAction extends ActionSupport {

	private static final long serialVersionUID = 1L;
	private String userName = null;
	private String password = null;
	private String tip = "";
	
	public void setTip(String tip) {
		this.tip = tip;
	}
	
	public String getTip(){
		return this.tip;
	}

	private UserService userService = null;

	public void setUserService(UserService userService) {
		this.userService = userService;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public void setPassword(String password) {
		this.password = password;
	}


	public String execute() throws IOException{
		
		User user = userService.checkUser(userName, password);

		if(null != user){
			TraceManager.TrDebug("user :" + userName + " log in system");
			String valid = user.getBackup3();
			if(valid.equals("1")){
				Map<String, Object> session =  ActionContext.getContext().getSession();
				session.put("userId", user.getUserId());
				session.put("userName", user.getUserName());
				session.put("roleId", String.valueOf(user.getRole().getRoleId().intValue()));
				session.put("roleName", user.getRole().getRoleName());
				session.put("centerId", String.valueOf(user.getServiceCenter().getCenterId().intValue()));
				session.put("centerName", user.getServiceCenter().getCenterName());
				return SUCCESS;
			} else{
				setTip("用户尚未通过管理员审核!");
				return LOGIN;
			}
		} else {
			setTip("请确认用户名与密码是否正确!");
			return LOGIN;
		}
	}
}
