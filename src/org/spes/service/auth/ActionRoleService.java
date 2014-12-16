package org.spes.service.auth;

import java.util.List;

import org.spes.bean.ActionRole;

/**
 * Ȩ�޽�ɫӳ��ҵ��
 * 
 * @author HeGang
 * 
 */
public interface ActionRoleService {

	/**
	 * ��ȡ��ɫId��Ӧ��Ȩ��ID�б�
	 * 
	 * @param roleId
	 *            ��ɫId
	 * @return ��Ӧ��Ȩ��Id�б�
	 */
	public List<ActionRole> getActionByRoleId(Integer roleId);

	/**
	 * ͨ����ɫId��Ȩ��Id��ȡ�˵�
	 * 
	 * @param parentId
	 *            Ȩ�޸�Id
	 * @param roleId
	 *            ��ɫId
	 * @return ��ɫȨ��ӳ���
	 */
	public List<ActionRole> getActionByRole(String parentId, Integer roleId);

}
