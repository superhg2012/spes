package org.spes.service.auth;

import java.util.List;

import org.spes.bean.ActionRole;

/**
 * 权限角色映射业务
 * 
 * @author HeGang
 * 
 */
public interface ActionRoleService {

	/**
	 * 获取角色Id对应的权限ID列表
	 * 
	 * @param roleId
	 *            角色Id
	 * @return 对应的权限Id列表
	 */
	public List<ActionRole> getActionByRoleId(Integer roleId);

	/**
	 * 通过角色Id与权限Id获取菜单
	 * 
	 * @param parentId
	 *            权限父Id
	 * @param roleId
	 *            角色Id
	 * @return 角色权限映射表
	 */
	public List<ActionRole> getActionByRole(String parentId, Integer roleId);

}
