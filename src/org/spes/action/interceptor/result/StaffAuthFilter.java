package org.spes.action.interceptor.result;

import java.util.List;
import java.util.Map;

import org.spes.action.result.StaffResultDisplay;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.AbstractInterceptor;

public class StaffAuthFilter extends AbstractInterceptor {

	@Override
	public String intercept(ActionInvocation arg0) throws Exception {
		// TODO Auto-generated method stub
		StaffResultDisplay srd = (StaffResultDisplay)arg0.getAction();
		Map session = ActionContext.getContext().getSession();
		Integer roleId = Integer.valueOf(session.get("roleId").toString());
		List actions = srd.getActionService().GetActionsByRoleId(roleId);
		for(int i = 0; i<actions.size(); i++){
			if(((List)actions.get(i)).get(0) != null && ((List)actions.get(i)).get(0).equals(ActionContext.getContext().getName())){
				return arg0.invoke();
			}
		}
		System.out.println("无权限访问");
		return null;
	}

}
