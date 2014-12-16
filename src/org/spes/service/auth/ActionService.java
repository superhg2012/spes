package org.spes.service.auth;

import java.util.List;

import org.spes.bean.Action;

/**
 * 权限业务
 * 
 * @author HeGang
 * 
 */
public interface ActionService {
	/**
	 * 通过权限Id获取权限详细信息
	 * 
	 * @param actionId
	 *            权限Id
	 * @return 权限对象
	 */
	public Action getActionById(Integer actionId);
	
	/**
	 * 通过父权限Id获取权限详细信息
	 * @param parentId
	 * @return
	 */
	public List<Action> getActionByParentId(Integer parentId);
	
	public List<String> GetActionsByRoleId(Integer roleId);
}
