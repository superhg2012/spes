package org.spes.service.auth.impl;

import java.util.List;

import org.spes.bean.ActionRole;
import org.spes.role.dao.ActionRoleDAO;
import org.spes.service.auth.ActionRoleService;

public class ActionRoleServiceImpl implements ActionRoleService {

	private ActionRoleDAO actionRoleDAO = null;

	public ActionRoleDAO getActionRoleDAO() {
		return actionRoleDAO;
	}

	public void setActionRoleDAO(ActionRoleDAO actionRoleDAO) {
		this.actionRoleDAO = actionRoleDAO;
	}

	public List<ActionRole> getActionByRoleId(Integer roleId) {
		return actionRoleDAO.findByProperty("roleId", roleId);
	}

	public List<ActionRole> getActionByRole(String parentId, Integer roleId) {
		return actionRoleDAO.findByIds(parentId, roleId);
	}

}
