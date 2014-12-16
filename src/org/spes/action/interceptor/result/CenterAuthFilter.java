package org.spes.action.interceptor.result;

import java.util.List;
import java.util.Map;

import org.spes.action.result.CenterResultDisplay;
import org.spes.bean.Role;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.AbstractInterceptor;

public class CenterAuthFilter extends AbstractInterceptor {

	private static final long serialVersionUID = 1L;

	@Override
	public String intercept(ActionInvocation arg0) throws Exception {
		CenterResultDisplay crd = (CenterResultDisplay)arg0.getAction();
		ActionContext context = ActionContext.getContext();
		Map session = context.getSession();
		Integer roleId = Integer.valueOf(session.get("roleId").toString());
		List actions = crd.getActionService().GetActionsByRoleId(roleId);
		for(int i = 0; i<actions.size(); i++){
			if(((List)actions.get(i)).get(0)!=null && ((List)actions.get(i)).get(0).equals(context.getName())){
				return arg0.invoke();
			}
		}
		System.out.println("无权限访问");
		return null;
	}
}
