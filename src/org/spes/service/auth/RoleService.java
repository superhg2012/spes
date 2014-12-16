package org.spes.service.auth;

import java.util.List;

import org.spes.bean.Role;

/**
 * ��ɫҵ��
 * 
 * @author HeGang
 * 
 */
public interface RoleService {

	/**
	 * ��ȡ���е��û���ɫ
	 * 
	 * @return �û���ɫ�б�
	 */
	public List<Role> getAllRoles();

	/**
	 * ͨ����Ȩ�޽�ɫ��ȡ��Ȩ�޽�ɫ
	 * 
	 * @param roleId
	 *            ��ɫId
	 * @return ��ɫ�б�
	 */
	public List<Role> getRolesByLevel(Integer roleId);
}
