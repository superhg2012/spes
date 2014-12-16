package org.spes.role.dao;

import java.util.List;

import org.spes.bean.ActionRole;

public interface ActionRoleDAO {

	public void save(ActionRole transientInstance);

	public void delete(ActionRole persistentInstance);

	public List findByProperty(String propertyName, Object value);

	public List findAll();
	
	public List<ActionRole> findByIds(String parentId, Integer roleId);

}