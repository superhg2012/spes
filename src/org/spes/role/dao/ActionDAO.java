package org.spes.role.dao;

import java.util.List;

import org.spes.bean.Action;

public interface ActionDAO {

	public void save(Action transientInstance);

	public void delete(Action persistentInstance);

	public List findByProperty(String propertyName, Object value);

	public List findAll();

	public Action findById(Integer id);

	public List findActionsByRoleId(Integer roleId);

	public List findByParentId(Object Id);
}