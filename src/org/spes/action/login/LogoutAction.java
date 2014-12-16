package org.spes.action.login;



import javax.servlet.http.HttpSession;

import org.apache.struts2.ServletActionContext;

import com.alcatel.omc.fwk.utilities.trace.TraceManager;
import com.opensymphony.xwork2.ActionSupport;

/**
 * 退出系统，清空session
 * 
 * @author HeGang
 *
 */
public class LogoutAction extends ActionSupport {

	private static final long serialVersionUID = 1L;

	public String execute() {
		
		HttpSession session = ServletActionContext.getRequest().getSession(false);
		if (session != null) {
			session.invalidate();
			TraceManager.TrDebug("Log out system...");
		} else {
			return INPUT;
		}
//		ActionContext.getContext().getSession().clear();//清空session
/*		if (null != session) {
			session.removeAttribute("userName");
			session.removeAttribute("role");
			session.invalidate();
		}
*/		return SUCCESS;
	}
}
