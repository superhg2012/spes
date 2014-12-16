package org.spes.dao.common;

import java.util.List;

import org.spes.bean.Role;

public interface RoleDAO {

	public void save(Role transientInstance);

	public void delete(Role persistentInstance);

	public List findByProperty(String propertyName, Object value);

	public List findAll();
	
	public List findByRoleId(Integer roleId);

}