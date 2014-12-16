package org.spes.service.auth.impl;

import java.util.List;

import org.spes.bean.Role;
import org.spes.dao.common.RoleDAO;
import org.spes.service.auth.RoleService;

public class RoleServiceImpl implements RoleService {

	private RoleDAO roleDAO = null;

	public RoleDAO getRoleDAO() {
		return roleDAO;
	}

	public void setRoleDAO(RoleDAO roleDAO) {
		this.roleDAO = roleDAO;
	}

	public List<Role> getAllRoles() {
		return roleDAO.findAll();
	}

	public List<Role> getRolesByLevel(Integer roleId) {
		return roleDAO.findByRoleId(roleId);
	}

}
