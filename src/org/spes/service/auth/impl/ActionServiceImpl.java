package org.spes.service.auth.impl;


import java.util.List;

import org.spes.bean.Action;
import org.spes.role.dao.ActionDAO;
import org.spes.service.auth.ActionService;
/**
 * 权限业务实现类
 *
 * @see org.spes.service.auth.impl.ActionServiceImpl
 * @author HeGang
 *
 */
public class ActionServiceImpl implements ActionService {

	private ActionDAO actionDAO = null;

	public ActionDAO getActionDAO() {
		return actionDAO;
	}

	public void setActionDAO(ActionDAO actionDAO) {
		this.actionDAO = actionDAO;
	}

	public Action getActionById(Integer actionId) {
		return actionDAO.findById(actionId);
	}

	@SuppressWarnings("unchecked")
	public List<Action> getActionByParentId(Integer parentId) {
		return actionDAO.findByParentId(parentId);
	}
	
	public List<String> GetActionsByRoleId(Integer roleId){
		return actionDAO.findActionsByRoleId(roleId);
	}
}
