package org.spes.service.auth;

import java.util.List;

import org.spes.bean.Role;

/**
 * 角色业务
 * 
 * @author HeGang
 * 
 */
public interface RoleService {

	/**
	 * 获取所有的用户角色
	 * 
	 * @return 用户角色列表
	 */
	public List<Role> getAllRoles();

	/**
	 * 通过高权限角色获取低权限角色
	 * 
	 * @param roleId
	 *            角色Id
	 * @return 角色列表
	 */
	public List<Role> getRolesByLevel(Integer roleId);
}
