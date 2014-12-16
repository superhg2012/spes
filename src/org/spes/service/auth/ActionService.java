package org.spes.service.auth;

import java.util.List;

import org.spes.bean.Action;

/**
 * Ȩ��ҵ��
 * 
 * @author HeGang
 * 
 */
public interface ActionService {
	/**
	 * ͨ��Ȩ��Id��ȡȨ����ϸ��Ϣ
	 * 
	 * @param actionId
	 *            Ȩ��Id
	 * @return Ȩ�޶���
	 */
	public Action getActionById(Integer actionId);
	
	/**
	 * ͨ����Ȩ��Id��ȡȨ����ϸ��Ϣ
	 * @param parentId
	 * @return
	 */
	public List<Action> getActionByParentId(Integer parentId);
	
	public List<String> GetActionsByRoleId(Integer roleId);
}
