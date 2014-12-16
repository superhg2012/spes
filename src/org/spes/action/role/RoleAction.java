package org.spes.action.role;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.struts2.ServletActionContext;
import org.spes.bean.Action;
import org.spes.bean.ActionRole;
import org.spes.bean.Role;
import org.spes.service.auth.ActionRoleService;
import org.spes.service.auth.ActionService;
import org.spes.service.auth.RoleService;

import com.opensymphony.xwork2.ActionSupport;

/**
 * Role Action
 * 
 * @author HeGang
 * 
 */
public class RoleAction extends ActionSupport {
	
	private static final long serialVersionUID = -2896305733222311095L;
	private RoleService roleService = null;
	private ActionService actionService = null;
	private ActionRoleService actionRoleService = null;

	private Integer roleId;
	private String parentId;
	
	public String getParentId() {
		return parentId;
	}

	public void setParentId(String parentId) {
		this.parentId = parentId;
	}
	
	public Integer getRoleId() {
		return roleId;
	}

	public void setRoleId(Integer roleId) {
		this.roleId = roleId;
	}

	
	public RoleService getRoleService() {
		return roleService;
	}

	public void setRoleService(RoleService roleService) {
		this.roleService = roleService;
	}

	public ActionService getActionService() {
		return actionService;
	}

	public void setActionService(ActionService actionService) {
		this.actionService = actionService;
	}

	public ActionRoleService getActionRoleService() {
		return actionRoleService;
	}

	public void setActionRoleService(ActionRoleService actionRoleService) {
		this.actionRoleService = actionRoleService;
	}

	/**
	 * 获取全部角色
	 * 
	 * @throws IOException
	 */
	public void getRoles() throws IOException {
		HttpServletResponse response = ServletActionContext.getResponse();
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/json");

		List<Role> roleList = roleService.getAllRoles();
		JSONArray jsonArr = new JSONArray();
		
		for (Role role : roleList) {
			JSONObject jsonObj = new JSONObject();
			jsonObj.put("roleId", role.getRoleId());
			jsonObj.put("roleName", role.getRoleName());
			jsonArr.add(jsonObj);
		}
		jsonArr.write(response.getWriter());
		jsonArr.clear();
	}

	/**
	 * 通过高权限角色获取低权限角色
	 * @throws IOException
	 */
	public void getRolesByRoleId() throws IOException{
		HttpServletResponse response = ServletActionContext.getResponse();
		response.setContentType("text/json");
		response.setCharacterEncoding("UTF-8");
		HttpServletRequest request = ServletActionContext.getRequest();
		String  roleId = request.getParameter("roleId");
		List<Role> roleList = roleService.getRolesByLevel(Integer.valueOf(roleId));
		
		if (null != roleList && roleList.size() > 0) {
			JSONArray jsonArray = new JSONArray();
			for (int index = 0; index < roleList.size(); index++) {
				Role role = roleList.get(index);
				JSONObject json = new JSONObject();
				json.put("roleId", role.getRoleId());
				json.put("roleName", role.getRoleName());
				jsonArray.add(json);
			}
			JSONObject json = new JSONObject();
			json.put("root", jsonArray.toString());
			response.getWriter().write(json.toString());
		}
	}
	
	public void getAllRoles() throws IOException {
		HttpServletResponse response = ServletActionContext.getResponse();
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/json");

		List<Role> roleList = roleService.getAllRoles();
		JSONArray jsonArr = new JSONArray();
		
		for (Role role : roleList) {
			JSONObject jsonObj = new JSONObject();
			jsonObj.put("roleId", role.getRoleId());
			jsonObj.put("roleName", role.getRoleName());
			jsonArr.add(jsonObj);
		}
		
		JSONObject json = new JSONObject();
		json.put("root", jsonArr.toString());
		json.write(response.getWriter());
	}
	
	
	/**
	 * 获取权限列表
	 * 
	 * @throws IOException
	 */
	public void getActions() throws IOException {
		
		HttpServletResponse response = ServletActionContext.getResponse();
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/json");
		
		List<ActionRole> actionRoleList = actionRoleService.getActionByRoleId(roleId);//根据角色Id获取角色权限映射
		List<Action> actionList = new ArrayList<Action>();
	    //获取角色对应的权限
		for (ActionRole actionRole : actionRoleList) {
			Integer actionId = actionRole.getActionId();
			Action action = actionService.getActionById(actionId);
			actionList.add(action);
		}
		
		JSONArray jsonArr = new JSONArray();
		for (Action action : actionList) {
			JSONObject json = new JSONObject();
			json.put("actionId", action.getActionId());
			json.put("actionName", action.getActionName());
			json.put("action", action.getBackup1()); // 英文ActionName
			json.put("parentId", action.getParentId());
			json.put("actionType", action.getActionType());
			json.put("url", action.getUrl());
			json.put("target", action.getTarget());
			json.put("actionDesc", action.getActionDesc());
			json.put("icon", action.getBackup2());
			
			jsonArr.add(json);
		}

		jsonArr.write(response.getWriter());
		jsonArr.clear();

	}
	
	/**
	 * 通过ActionId获取对应的菜单
	 * @throws IOException
	 */
	public void getActionByActionId() throws IOException{
		HttpServletResponse response = ServletActionContext.getResponse();
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/json");
		
		JSONArray jsonArr = new JSONArray();	
//		List<Action> actionList = actionService.getActionByParentId(Integer.valueOf(parentId));
		List<ActionRole> actionRoleList = actionRoleService.getActionByRole(parentId, roleId);
		List<Action> actionList = new ArrayList<Action>();		
		for(ActionRole ar :actionRoleList){
			Integer actionId = ar.getActionId();
			Action action = actionService.getActionById(actionId);
			actionList.add(action);
		}
		
		for (Action action : actionList) {
			JSONObject json = new JSONObject();
			json.put("actionId", action.getActionId());
			json.put("actionName", action.getActionName());
			json.put("action", action.getBackup1()); // 英文ActionName
			json.put("parentId", action.getParentId());
			json.put("actionType", action.getActionType());
			json.put("url", action.getUrl());
			json.put("target", action.getTarget());
			json.put("actionDesc", action.getActionDesc());
			json.put("icon",action.getBackup2());
			jsonArr.add(json);
		}
		
		jsonArr.write(response.getWriter());
		jsonArr.clear();
	}
}
